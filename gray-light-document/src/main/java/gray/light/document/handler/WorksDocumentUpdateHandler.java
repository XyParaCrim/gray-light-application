package gray.light.document.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理作品文档的更新请求
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class WorksDocumentUpdateHandler {

/*    private final WritableDocumentService writableDocumentService;

    private final ReadableOwnerProjectService readableOwnerProjectService;

    *//**
     * 根据请求表单，为所属者的项目添加一个文档
     *
     * @param variables 参数表
     * @return Response of Publisher
     *//*
    public Mono<ServerResponse> addWorksDocumentByForm(RequestParamVariables variables) {
        DocumentFo form = variables.getBodyObject(DocumentFo.class);

        return createWorksDocumentWithNormalize(form);
    }

    private Mono<ServerResponse> createWorksDocumentWithNormalize(DocumentFo documentFo) {
        try {
            documentFo.normalize();
        } catch (NormalizingFormException e) {
            log.error(e.getMessage());
            return ResponseBuffet.failByInternalError(e.getMessage());
        }

        Long worksId = documentFo.getWorksId();
        OwnerProject documentProject = OwnerProjectCustomizer.fromForm(documentFo.getDocument(), Scope.DOCUMENT);
        ProjectDetails uncommited = ProjectDetailsCustomizer.uncommitProjectDetails(documentFo.getSource());

        return addWorksDocument(worksId, documentProject, uncommited);
    }

    private Mono<ServerResponse> addWorksDocument(Long worksId, OwnerProject documentProject, ProjectDetails uncommited) {
        CompletableFuture<Boolean> addProcessing = addWorksDocumentProcessing(worksId, documentProject, uncommited);

        return Mono.fromFuture(addProcessing).
                flatMap(
                        success -> {
                            if (success) {
                                Optional<OwnerProject> savedProject = readableOwnerProjectService.findProject(documentProject.getId());
                                if (savedProject.isPresent()) {
                                    return ResponseBuffet.allRight(DocumentBo.of(savedProject.get(), worksId));
                                }
                            }

                            return ResponseBuffet.failByInternalError("Failed to add works document");
                        }

                );
    }

    private CompletableFuture<Boolean> addWorksDocumentProcessing(Long worksId, OwnerProject documentProject, ProjectDetails uncommited) {
        return CompletableFuture.supplyAsync(() -> writableDocumentService.addDocumentToWorks(worksId, documentProject, uncommited));
    }*/
}
