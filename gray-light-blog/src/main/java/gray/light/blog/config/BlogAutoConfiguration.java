package gray.light.blog.config;

import cache.RedisChannelCache;
import cache.StringChannelCache;
import floor.file.storage.FileStorage;
import gray.light.blog.handler.BlogQueryHandler;
import gray.light.blog.handler.BlogSearchHandler;
import gray.light.blog.handler.BlogUpdateHandler;
import gray.light.blog.handler.TagHandler;
import gray.light.blog.router.PersonalBlogRouter;
import gray.light.blog.router.PersonalSearchBlogRouter;
import gray.light.blog.search.BlogSearchOptions;
import gray.light.blog.service.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 博客相关的自动配置
 *
 * @author XyParaCrim
 */
@Import({
        PersonalBlogRouter.class,
        BlogQueryHandler.class,
        TagHandler.class,
        ReadableBlogService.class,
        TagService.class,
        BlogAutoConfiguration.WritableConfiguration.class,
        BlogAutoConfiguration.SearchConfiguration.class
})
@MapperScan(BlogAutoConfiguration.MAPPER_PACKAGE)
public class BlogAutoConfiguration {

    public static final String MAPPER_PACKAGE = "gray.light.blog.repository";

    @Import({
            BlogUpdateHandler.class,
            WritableBlogService.class,
            BlogSourceService.class
    })
    @ConditionalOnBean(FileStorage.class)
    public static class WritableConfiguration {

    }

    @Import({
            BlogSearchHandler.class,
            SearchBlogService.class,
            BlogSearchOptions.class,
            PersonalSearchBlogRouter.class
    })
    public static class SearchConfiguration {

        @Bean
        public StringChannelCache searchChannelCache(RedisTemplate<String, String> redisTemplate) {
            return new RedisChannelCache(redisTemplate);
        }

    }

}
