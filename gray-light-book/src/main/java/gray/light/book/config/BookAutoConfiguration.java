package gray.light.book.config;

import floor.file.storage.FileStorage;
import floor.repository.RepositoryDatabase;
import gray.light.book.handler.BookQueryHandler;
import gray.light.book.service.LocalCacheBookService;
import gray.light.book.service.ReadableBookService;
import gray.light.book.service.WritableBookService;
import gray.light.book.service.SourceBookService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Import;

/**
 * 关于book结构Git项目的所有配置
 *
 * @author XyParaCrim
 */
@MapperScan(BookAutoConfiguration.MAPPER_PACKAGE)
@Import({
        BookAutoConfiguration.CommonConfiguration.class,
        BookAutoConfiguration.BookSourceConfiguration.class,
        BookAutoConfiguration.BookRepositoryCacheConfiguration.class
})
public class BookAutoConfiguration {

    public static final String MAPPER_PACKAGE = "gray.light.book.repository";

    /**
     * 只依赖数据库
     */
    @Import({ BookQueryHandler.class, ReadableBookService.class, WritableBookService.class})
    public static class CommonConfiguration {

    }

    @ConditionalOnBean(FileStorage.class)
    @Import(SourceBookService.class)
    public static class BookSourceConfiguration {

    }

    @ConditionalOnBean(RepositoryDatabase.class)
    @Import(LocalCacheBookService.class)
    public static class BookRepositoryCacheConfiguration {

    }

}
