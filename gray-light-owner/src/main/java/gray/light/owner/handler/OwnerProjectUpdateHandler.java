package gray.light.owner.handler;

import gray.light.definition.entity.Scope;
import gray.light.owner.business.OwnerProjectFo;
import gray.light.owner.customizer.OwnerProjectCustomizer;
import gray.light.owner.entity.OwnerProject;
import gray.light.owner.service.ReadableOwnerProjectService;
import gray.light.owner.service.WritableOwnerProjectService;
import gray.light.support.web.ResponseToClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static gray.light.support.web.ResponseToClient.failWithMessage;

/**
 * 关于所属者项目更新处理方法
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class OwnerProjectUpdateHandler {

    private final WritableOwnerProjectService writableOwnerProjectService;

    private final ReadableOwnerProjectService readableOwnerProjectService;

    // 包级帮助方法

    /**
     * 通过指定owner-project表单，添加新owner-project
     *
     * @param ownerProjectForm 通过指定owner-project表单
     * @param scope 添加到范围
     * @return 回复发布者
     */
    Mono<ServerResponse> addOwnerProjectWithNormalize(OwnerProjectFo ownerProjectForm, Scope scope) {
        ownerProjectForm.normalize();

        return addOwnerProject(OwnerProjectCustomizer.fromForm(ownerProjectForm, scope));
    }

    /**
     * 添加指定owner-project
     *
     * @param project 指定owner-project
     * @return 回复发布者
     */
    private Mono<ServerResponse> addOwnerProject(OwnerProject project) {
        CompletableFuture<Optional<OwnerProject>> addProcessing = addOwnerProjectProcessing(project);

        return Mono.fromFuture(addProcessing).
                flatMap(
                        addedProject ->
                                addedProject.
                                        map(ResponseToClient::allRightFromValue).
                                        orElseGet(() -> failWithMessage("Failed to add favorite " + project.getScope() + " project."))
                );
    }

    private CompletableFuture<Optional<OwnerProject>> addOwnerProjectProcessing(OwnerProject project) {
        return CompletableFuture.supplyAsync(
                () ->
                        writableOwnerProjectService.addProject(project) ?
                                readableOwnerProjectService.findProject(project.getId()) :
                                Optional.empty()
        );
    }

}
