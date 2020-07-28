package gray.light.document.router;

import gray.light.document.handler.WorksDocumentQueryHandler;
import gray.light.owner.router.OwnerRequestPredicates;
import gray.light.support.web.RequestParamTables;
import gray.light.support.web.RequestSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.*;

/**
 * 定义关于文档查询的路由请求（只读）
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class PersonalQueryDocumentRouter {

    private final WorksDocumentQueryHandler queryHandler;

    /**
     * 查询指定所属者的所有作品文档
     *
     * @return 请求方法
     */
    @Bean
    public RouterFunction<ServerResponse> queryWorksDocumentRequiredOwner() {
        RequestPredicate predicate = RequestPredicates.GET("/owner/works/docs").
                and(OwnerRequestPredicates.ownerId());
        HandlerFunction<ServerResponse> handler = RequestSupport.routerFunction(queryHandler::queryDocumentByOwnerId,
                RequestParamTables.ownerId(), RequestParamTables.page());

        return RouterFunctions.route(predicate, handler);
    }

    /**
     * 查询指定作品Id的所有文档
     *
     * @return 请求方法
     */
    @Bean
    public RouterFunction<ServerResponse> queryWorksDocumentRequiredWorks() {
        RequestPredicate predicate = RequestPredicates.GET("/owner/works/docs").
                and(OwnerRequestPredicates.worksId());
        HandlerFunction<ServerResponse> handler = RequestSupport.routerFunction(queryHandler::queryDocumentByWorksId,
                RequestParamTables.worksId(), RequestParamTables.page());

        return RouterFunctions.route(predicate, handler);
    }

}
