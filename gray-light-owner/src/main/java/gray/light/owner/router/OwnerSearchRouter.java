package gray.light.owner.router;

import gray.light.owner.handler.OwnerSearchHandler;
import gray.light.support.RouterFunctionSupport;
import gray.light.support.web.RequestParamTables;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static gray.light.owner.meta.OwnerServiceRequestPaths.OF_OWNER_OF_SEARCH;

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
                get(OF_OWNER_OF_SEARCH).
                requireParam(RequestParamTables.search()).
                handle(handler::handleGlobalSearchOwner).
                build();
    }

}
