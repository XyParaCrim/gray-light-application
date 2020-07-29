package gray.light.document.config;

import gray.light.book.handler.BookQueryHandler;
import gray.light.document.handler.WorksDocumentQueryHandler;
import gray.light.document.handler.WorksDocumentUpdateHandler;
import gray.light.document.router.PersonalDocumentBookRouter;
import gray.light.document.router.PersonalQueryDocumentRouter;
import gray.light.document.router.PersonalUpdateDocumentRouter;
import gray.light.document.service.ReadableDocumentService;
import gray.light.document.service.WritableDocumentService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Import;

/**
 * 关于作品文档的所有配置
 *
 * @author XyParaCrim
 */
@MapperScan(DocumentAutoConfiguration.MAPPER_PACKAGE)
@Import({
        DocumentAutoConfiguration.ReadableDocumentConfiguration.class,
        DocumentAutoConfiguration.WritableDocumentConfiguration.class,
        DocumentAutoConfiguration.OptionalBookConfiguration.class
})
public class DocumentAutoConfiguration {

    public static final String MAPPER_PACKAGE = "gray.light.document.repository";

    @ConditionalOnBean(BookQueryHandler.class)
    @Import(PersonalDocumentBookRouter.class)
    public static class OptionalBookConfiguration {

    }

    /**
     * 只依赖数据库
     */
    @Import({ PersonalQueryDocumentRouter.class, WorksDocumentQueryHandler.class, ReadableDocumentService.class })
    public static class ReadableDocumentConfiguration {

    }

    /**
     * 只依赖数据库
     */
    @Import({PersonalUpdateDocumentRouter.class, WorksDocumentUpdateHandler.class, WritableDocumentService.class })
    public static class WritableDocumentConfiguration {

    }

}
