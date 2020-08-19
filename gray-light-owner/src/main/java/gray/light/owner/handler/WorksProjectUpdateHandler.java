package gray.light.owner.handler;

import gray.light.definition.entity.Scope;
import gray.light.owner.business.OwnerProjectFo;
import gray.light.support.web.RequestParamVariables;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 关于Works范围的更新处理方法
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class WorksProjectUpdateHandler {

    private static final Scope SCOPE = Scope.WORKS;

    private final OwnerProjectUpdateHandler ownerProjectHandler;

    /**
     * 通过指定请求，添加works项目到owner-project中
     *
     * @param variables 参数表
     * @return 回复发布者
     */
    public Mono<ServerResponse> addWorksProject(RequestParamVariables variables) {
        OwnerProjectFo projectForm = variables.getBodyObject(OwnerProjectFo.class);

        return ownerProjectHandler.addOwnerProjectWithNormalize(projectForm, SCOPE);
    }

}
