package gray.light.owner.router;

import gray.light.owner.business.OwnerProjectFo;
import gray.light.owner.handler.WorksProjectUpdateHandler;
import gray.light.owner.meta.OwnerServiceRequestPaths;
import gray.light.support.RouterFunctionSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 所属者的查询更新路由
 *
 * @author XyParaCrim
 */
public class OwnerUpdateRouter {

    /**
     * 为指定所属者添加一个works
     *
     * @param handler 关于owner-project的请求与回复转换操作
     * @return 路由方法
     */
    @Bean
    public RouterFunction<ServerResponse> newWorks(WorksProjectUpdateHandler handler) {
        return RouterFunctionSupport.
                post(OwnerServiceRequestPaths.OF_OWNER_OF_WORKS, OwnerProjectFo.class).
                handle(handler::addWorksProject).
                build();
    }

}
