package gray.light.owner.router;

import gray.light.owner.handler.OwnerProjectQueryHandler;
import gray.light.owner.handler.OwnerQueryHandler;
import gray.light.owner.handler.WorksProjectQueryHandler;
import gray.light.support.RouterFunctionSupport;
import gray.light.support.web.RequestParamTables;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 所属者的查询请求路由
 *
 * @author XyParaCrim
 */
public class OwnerQueryRouter {

    /**
     * 获取所属者详细信息
     *
     * @param handler 处理方法
     * @return 路由方法
     */
    @Bean
    public RouterFunction<ServerResponse> getOwnerDetails(OwnerQueryHandler handler) {
        return RouterFunctionSupport.
                get("/owner").
                requireParam(RequestParamTables.ownerId()).
                handle(handler::ownerDetails).
                build();
    }

    /**
     * 获取指定所属者的所有所属者项目
     *
     * @param handler 处理
     * @return 路由方法
     */
    @Bean
    public RouterFunction<ServerResponse> queryOwnerProject(OwnerProjectQueryHandler handler) {
        return RouterFunctionSupport.
                get("/owner/owner-project").
                requireParam(RequestParamTables.page()).
                requireParam(RequestParamTables.ownerId()).
                handle(handler::queryOwnerProject).
                build();
    }

    /**
     * 查询指定所属者的works项目
     *
     * @param handler works处理
     * @return 路由方法
     */
    @Bean
    public RouterFunction<ServerResponse> queryOwnerWorks(WorksProjectQueryHandler handler) {
        return RouterFunctionSupport.
                get("/owner/works").
                requireParam(RequestParamTables.page()).
                requireParam(RequestParamTables.ownerId()).
                handle(handler::queryWorksProject).
                build();
    }

}
