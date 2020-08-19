package gray.light.owner.handler;

import gray.light.definition.entity.Scope;
import gray.light.owner.service.ReadableOwnerProjectService;
import gray.light.support.web.RequestParamTables;
import gray.light.support.web.RequestParamVariables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerResponse;
import perishing.constraint.jdbc.Page;
import reactor.core.publisher.Mono;

import static gray.light.support.web.ResponseToClient.allRightFromValue;

/**
 * 关于所属者项目查询处理方法
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class OwnerProjectQueryHandler {

    private final ReadableOwnerProjectService readableOwnerProjectService;

    /**
     * 查询指定所属者项目
     *
     * @param variables 参数表
     * @return 回复
     */
    public Mono<ServerResponse> queryOwnerProject(RequestParamVariables variables) {
        Long ownerId = RequestParamTables.ownerId().get(variables);
        Page page = RequestParamTables.page().get(variables);

        return allRightFromValue(readableOwnerProjectService.projects(ownerId, page));
    }

    // 包级帮助方法

    /**
     * 查询所属者的指定scope的项目
     *
     * @param ownerId 所属者Id
     * @param scope 范围
     * @param page 分页
     * @return Response of Publisher
     */
    Mono<ServerResponse> queryOwnerProject(Long ownerId, Scope scope, Page page) {
        return allRightFromValue(readableOwnerProjectService.projects(ownerId, scope, page));
    }

}
