package gray.light.blog.admin.router;

import gray.light.blog.customizer.BlogCustomizer;
import gray.light.blog.customizer.TagCustomizer;
import gray.light.blog.entity.Blog;
import gray.light.blog.entity.Tag;
import gray.light.blog.search.indices.BlogIndex;
import gray.light.blog.search.options.BlogSearchOptions;
import gray.light.blog.service.TagService;
import gray.light.blog.service.WritableBlogService;
import gray.light.support.web.RequestParamTables;
import gray.light.support.web.RequestSupport;
import gray.light.support.web.ResponseToClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.*;
import perishing.constraint.io.FileSupport;
import perishing.constraint.treasure.chest.collection.FinalVariables;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 编写一些关于博客的本地后台操作
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class BlogAdminLocalOptionsRouter {

    /**
     * 创建一个无限大容量的线程池，空闲一分钟关闭
     */
    private static final ExecutorService CACHED_EXECUTOR = new ThreadPoolExecutor(0,
            Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new FindMarkdownThreadFactory());

    /**
     * 简单的线程池
     */
    private static final class FindMarkdownThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "find-markdown-" + threadNumber.getAndIncrement());
        }
    }

    private List<Tag> tags;

    private final TagService tagService;

    private final BlogSearchOptions blogSearchOptions;

    private final WritableBlogService writableBlogService;

    /**
     * 从数据库拉取所有的标签对象
     */
    @PostConstruct
    public void fetchAllTags() {
        tags = new ArrayList<>(tagService.allTags());
        log.warn("fetch all tags in db: {}", tags.size());
    }

    /**
     * 从tags中，至少随机提取atLeast个标签
     *
     * @param atLeast 至少随机提取个数
     * @param tags 标签
     * @return 随机提取标签
     */
    private static Collection<Tag> randomTags(int atLeast, List<Tag> tags) {
        int size = ThreadLocalRandom.current().nextInt(10) + atLeast;

        Set<Tag> randomTags = new HashSet<>(size);
        while (randomTags.size() < size) {
            randomTags.add(
                    tags.get(ThreadLocalRandom.current().nextInt(tags.size()))
            );
        }

        return randomTags;
    }

    // routers

    /**
     * 请求创建简单的标签，通过标签名，使用{@link TagCustomizer#wrap(String)}进行
     * 创建简单标签
     *
     * @return 处理请求的方法
     */
    @Bean
    public RouterFunction<ServerResponse> createSimpleTagsByName() {
        RequestPredicate predicate = RequestPredicates.POST("/owner/blog/admin/tags");

        return RouterFunctions.route(predicate, request -> {
            Optional<String> tags = request.queryParam("tags");
            if (tags.isEmpty()) {
                return ResponseToClient.badRequestCauseMissingParameter("tags");
            }

            for (String tag : tags.get().split(",")) {
                try {
                    tagService.createTag(TagCustomizer.wrap(tag));
                } catch (Exception e) {
                    log.error("Failed to create simple tag, most likely because of the same name: {}", tag);
                }
            }

            return ResponseToClient.allRight();
        });
    }

    /**
     * 接收请求里的location和ownerId，扫描文件夹里的所有markdown文件，
     * 然后将markdown转换为博客实体，存储到数据库，并且为他随机添加标签，
     * 最后索引到Elasticsearch
     *
     * @return 处理请求方法
     */
    @Bean
    public RouterFunction<ServerResponse> createBlogsFromLocation () {
        RequestPredicate predicate = RequestPredicates.POST("/owner/blog/admin/scan-blogs");

        return RouterFunctions.route(predicate, request -> RequestSupport.extract(
                request,
                this::scanLocationMarkdown,
                RequestParamTables.location(),
                RequestParamTables.ownerId()
        ));
    }

    private Mono<ServerResponse> scanLocationMarkdown(FinalVariables<String> variables) {
        Path path = RequestParamTables.location().get(variables);
        Long ownerId = RequestParamTables.ownerId().get(variables);

        CACHED_EXECUTOR.execute(new ScanMarkdownAndPersistent(path, ownerId));

        return ResponseToClient.allRight();
    }


    @RequiredArgsConstructor
    private class ScanMarkdownAndPersistent implements Runnable {

        final Path location;

        final Long ownerId;

        @Override
        public void run() {
            collectMarkdown().forEach(this::persistentBlog);
        }

        List<Path> collectMarkdown() {
            try {
                return FileSupport.collectFilesFromFileTree(location, "md", true);
            } catch (IOException e) {
                log.error("Failed to walk files tree for scanning markdown", e);
            }

            return Collections.emptyList();
        }

        void persistentBlog(Path markdown) {
            byte[] content;
            try {
                content = Files.readAllBytes(markdown);


                Blog blog = BlogCustomizer.ofLocal(markdown, ownerId);
                writableBlogService.addBlog(blog, content);

                Collection<Tag> randomTags = randomTags(10, tags);
                writableBlogService.addTags(blog, new ArrayList<>(randomTags));

                BlogIndex blogIndex = BlogIndex.
                        builder().
                        title(blog.getTitle()).
                        id(blog.getId()).
                        tags(tags.stream().map(Tag::getName).collect(Collectors.toList())).
                        content(new String(content)).
                        build();
                blogSearchOptions.addDocument(blogIndex);

            } catch (IOException e) {
                log.error("Failed to read markdown: ", e);
            } catch (Exception e) {
                log.error("Failed to persistent blog: ", e);
            }
        }
    }

}
