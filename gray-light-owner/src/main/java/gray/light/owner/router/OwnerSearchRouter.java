package gray.light.owner.router;

import gray.light.owner.handler.OwnerSearchHandler;
import gray.light.support.RouterFunctionSupport;
import gray.light.support.web.RequestParamTables;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 定义所属者的搜索请求
 *
 * @author XyParaCrim
 */
public class OwnerSearchRouter {

    /**
     * 全局搜索所属者
     *
     * @param handler 处理方法
     * @return 路由方法
     */
    public RouterFunction<ServerResponse> globalSearchOwner(OwnerSearchHandler handler) {
        return RouterFunctionSupport.
                get("/owner/search").
                requireParam(RequestParamTables.search()).
                handle(handler::handleGlobalSearchOwner).
                build();
    }

}
