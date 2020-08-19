package gray.light.owner.handler;

import gray.light.definition.entity.Scope;
import gray.light.support.web.RequestParamTables;
import gray.light.support.web.RequestParamVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.ServerResponse;
import perishing.constraint.jdbc.Page;
import reactor.core.publisher.Mono;

/**
 * 关于Works范围的请求处理方法
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class WorksProjectQueryHandler {

    private static final Scope SCOPE = Scope.WORKS;

    private final OwnerProjectQueryHandler ownerProjectHandler;

    /**
     * 查询所属者的works
     *
     * @param variables 参数表
     * @return 回复
     */
    public Mono<ServerResponse> queryWorksProject(RequestParamVariables variables) {
        Long ownerId = RequestParamTables.ownerId().get(variables);
        Page page = RequestParamTables.page().get(variables);

        return ownerProjectHandler.queryOwnerProject(ownerId, SCOPE, page);
    }


}
