package gray.light.document.router;

import gray.light.document.handler.WorksDocumentQueryHandler;
import gray.light.support.RouterFunctionSupport;
import gray.light.support.web.RequestParamTables;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static gray.light.document.definition.meta.DocumentServiceRequestPaths.OF_WORKS_OF_DOCS;

/**
 * 定义关于文档查询的路由请求（只读）
 *
 * @author XyParaCrim
 */
public class DocumentQueryRouter {

    /**
     * 查询指定所属者的所有作品文档
     *
     * @return 请求方法
     */
    @Bean
    public RouterFunction<ServerResponse> queryWorksDocumentRequiredOwner(WorksDocumentQueryHandler queryHandler) {
        return RouterFunctionSupport.
                get(OF_WORKS_OF_DOCS).
                requireParam(RequestParamTables.page()).
                requireParam(RequestParamTables.ownerId()).
                handle(queryHandler::queryDocumentByOwnerId).
                build();
    }

    /**
     * 查询指定作品Id的所有文档
     *
     * @return 请求方法
     */
    @Bean
    public RouterFunction<ServerResponse> queryWorksDocumentRequiredWorks(WorksDocumentQueryHandler queryHandler) {
        return RouterFunctionSupport.
                get(OF_WORKS_OF_DOCS).
                requireParam(RequestParamTables.page()).
                requireParam(RequestParamTables.worksId()).
                handle(queryHandler::queryDocumentByWorksId).
                build();
    }

    /**
     * 查询文档仓库的结构树
     *
     * @return Response of Publisher
     */
/*    @Bean
    public RouterFunction<ServerResponse> queryDocumentStructureTree(BookQueryHandler bookQueryHandler) {
        return RouterFunctionSupport.
                get(OF_WORKS_OF_DOCS_OF_TREE).
                requireParam(RequestParamTables.id()).
                handle(bookQueryHandler::queryBookStructureTree).
                build();
    }*/

}
