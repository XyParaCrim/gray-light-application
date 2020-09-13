package gray.light.document.router;

import gray.light.document.definition.business.DocumentFo;
import gray.light.document.handler.WorksDocumentUpdateHandler;
import gray.light.support.RouterFunctionSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static gray.light.document.definition.meta.DocumentServiceRequestPaths.OF_WORKS_OF_DOCS;

/**
 * 定义关于文档更新的路由请求（可写）
 *
 * @author XyParaCrim
 */
public class DocumentUpdateRouter {

    /**
     * 根据post的表单，创建作品文档
     *
     * @return 请求处理方法
     */
/*
    @Bean
    public RouterFunction<ServerResponse> addWorksDocument(WorksDocumentUpdateHandler updateHandler) {
        return RouterFunctionSupport.
                post(OF_WORKS_OF_DOCS, DocumentFo.class).
                handle(updateHandler::addWorksDocumentByForm).
                build();
    }
*/

}
