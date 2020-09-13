package gray.light.document.handler;

import gray.light.definition.entity.Scope;
import gray.light.document.service.ReadableDocumentService;
import gray.light.owner.client.OwnerServiceClient;
import gray.light.support.web.RequestParamTables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerResponse;
import perishing.constraint.jdbc.Page;
import perishing.constraint.treasure.chest.collection.FinalVariables;
import perishing.constraint.web.flux.ResponseBuffet;
import reactor.core.publisher.Mono;

/**
 * 处理作品文档的查询请求
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class WorksDocumentQueryHandler {

    private final OwnerServiceClient ownerServiceClient;

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

        return ownerServiceClient.
                queryOwnerScopeWorks(page.getPer(), page.getPage(), ownerId, Scope.DOCUMENT.getName()).
                flatMap(ResponseBuffet::forward);
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


        return ResponseBuffet.allRight(readableDocumentService.findDocumentByWorks(worksId, page));
    }


}
