package gray.light.owner.handler;


import gray.light.owner.business.OwnerDetailsBo;
import gray.light.owner.entity.Owner;
import gray.light.owner.service.ReadableOwnerService;
import gray.light.support.web.RequestParamTables;
import gray.light.support.web.RequestParamVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.ServerResponse;
import perishing.constraint.web.flux.ResponseBuffet;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 关于所属者的请求查询处理方法
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class OwnerQueryHandler {

    private final ReadableOwnerService readableOwnerService;

    /**
     * 获取所属者详细
     *
     * @param variables 参数表
     * @return 回复
     */
    public Mono<ServerResponse> ownerDetails(RequestParamVariables variables) {
        Long ownerId = RequestParamTables.ownerId().get(variables);
        Optional<Owner> queryResult = readableOwnerService.findOwner(ownerId);

        return queryResult.isEmpty() ? ResponseBuffet.failByInternalError("The user does not exist: " + ownerId) :
                ResponseBuffet.allRight(OwnerDetailsBo.of(queryResult.get()));
    }

}
