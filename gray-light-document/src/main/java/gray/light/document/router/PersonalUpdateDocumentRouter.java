package gray.light.document.router;

import gray.light.document.handler.WorksDocumentUpdateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 定义关于文档更新的路由请求（可写）
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class PersonalUpdateDocumentRouter {

    private final WorksDocumentUpdateHandler updateHandler;

    /**
     * 根据post的表单，创建作品文档
     *
     * @return 请求处理方法
     */
    @Bean
    public RouterFunction<ServerResponse> addWorksDocument() {
        return RouterFunctions.route(RequestPredicates.POST("/owner/works/docs"),
                updateHandler::addWorksDocumentByForm);
    }

}
