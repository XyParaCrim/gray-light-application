package gray.light.owner.handler;

import gray.light.owner.entity.Owner;
import gray.light.owner.service.SearchOwnerService;
import gray.light.support.web.RequestParamTables;
import gray.light.support.web.RequestParamVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.ServerResponse;
import perishing.constraint.web.flux.ResponseBuffet;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 关于所属者搜索的请求处理
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class OwnerSearchHandler {

    private final SearchOwnerService searchOwnerService;

    /**
     * 处理全局搜索搜索者请求
     *
     * @param variables 参数
     * @return 回复
     */
    public Mono<ServerResponse> handleGlobalSearchOwner(RequestParamVariables variables) {
        String words = RequestParamTables.search().get(variables);
        List<Owner> searchResult = searchOwnerService.globalSearchOwner(words);

        return ResponseBuffet.allRight(searchResult);
    }

}
