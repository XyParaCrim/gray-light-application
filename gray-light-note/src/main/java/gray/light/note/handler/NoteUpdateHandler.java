package gray.light.note.handler;

import gray.light.definition.entity.Scope;
import gray.light.note.business.NoteBo;
import gray.light.note.business.NoteFo;
import gray.light.note.service.WritableNoteService;
import gray.light.owner.definition.customizer.OwnerProjectCustomizer;
import gray.light.owner.definition.customizer.ProjectDetailsCustomizer;
import gray.light.owner.definition.entity.OwnerProject;
import gray.light.owner.definition.entity.ProjectDetails;
import gray.light.owner.service.OverallOwnerService;
import gray.light.support.error.NormalizingFormException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static gray.light.support.web.ResponseToClient.allRightFromValue;
import static gray.light.support.web.ResponseToClient.failWithMessage;

/**
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class NoteUpdateHandler {

    private final WritableNoteService writableNoteService;

    private final OverallOwnerService overallOwnerService;

    /**
     * 为所属者添加一个笔记
     *
     * @param request 服务请求
     * @return Response of Publisher
     */
    public Mono<ServerResponse> createNote(ServerRequest request) {
        return request.
                bodyToMono(NoteFo.class).
                flatMap(noteFo -> {
                    try {
                        noteFo.normalize();
                    } catch (NormalizingFormException e) {
                        log.error(e.getMessage());
                        return failWithMessage(e.getMessage());
                    }

                    OwnerProject noteProject = OwnerProjectCustomizer.fromForm(noteFo.getNote(), Scope.NOTE);
                    ProjectDetails uncommited = ProjectDetailsCustomizer.uncommitProjectDetails(noteFo.getSource());

                    if (writableNoteService.createNote(noteProject, uncommited)) {
                        Optional<OwnerProject> savedProject = overallOwnerService.findProject(noteProject.getId());
                        if (savedProject.isPresent()) {
                            return allRightFromValue(NoteBo.of(savedProject.get()));
                        }
                    }

                    return failWithMessage("Failed to create note");

                });
    }


}
