package gray.light.owner.client;

import gray.light.owner.meta.OwnerServiceRequestPaths;
import gray.light.support.web.ResponseFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

/**
 * 调用owner服务的feign的客户端
 *
 * @author XyParaCrim
 */
@ReactiveFeignClient(name = "owner-application")
public interface OwnerServiceClient {

    /**
     * 请求获取指定Owner信息
     *
     * @param ownerId owner id
     * @return 回复
     */
    @GetMapping(OwnerServiceRequestPaths.OF_OWNER)
    Mono<ResponseFormat> getOwnerDetails(@RequestParam(value = "ownerId") Long ownerId);

}
