package gray.light.document.handler;

import gray.light.definition.entity.Scope;
import gray.light.document.service.ReadableDocumentService;
import gray.light.owner.service.OverallOwnerService;
import gray.light.support.web.RequestParamTables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerResponse;
import perishing.constraint.jdbc.Page;
import perishing.constraint.treasure.chest.collection.FinalVariables;
import reactor.core.publisher.Mono;

import static gray.light.support.web.ResponseToClient.allRightFromValue;

/**
 * 处理作品文档的查询请求
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class WorksDocumentQueryHandler {

    private final OverallOwnerService overallOwnerService;

    private final ReadableDocumentService readableDocumentService;

    /**
     * 查询指定所属者的document
     *
     * @param variables 请求参数
     * @return 回复
     */
    public Mono<ServerResponse> queryDocumentByOwnerId(FinalVariables<String> variables) {
        Page page = RequestParamTables.page().get(variables);
        Long ownerId = RequestParamTables.ownerId().get(variables);


        return allRightFromValue(overallOwnerService.projects(ownerId, Scope.DOCUMENT, page));
    }

    /**
     * 查询指定works的document
     *
     * @param variables 请求参数
     * @return 回复
     */
    public Mono<ServerResponse> queryDocumentByWorksId(FinalVariables<String> variables) {
        Page page = RequestParamTables.page().get(variables);
        Long worksId = RequestParamTables.worksId().get(variables);


        return allRightFromValue(readableDocumentService.findDocumentByWorks(worksId, page));
    }


}
