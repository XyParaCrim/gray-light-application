package gray.light.owner.client;

import gray.light.definition.web.DefinitionRequestParamTables;
import gray.light.owner.business.OwnerDetailsBo;
import gray.light.owner.entity.OwnerProject;
import gray.light.owner.meta.OwnerServiceRequestPaths;
import gray.light.support.web.PageChunk;
import gray.light.support.web.RequestParamTables;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import perishing.constraint.web.flux.ResponseFormat;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 调用owner服务的feign的客户端
 *
 * @author XyParaCrim
 */
@ReactiveFeignClient(name = "owner-application")
public interface OwnerServiceClient {

    /**
     * 请求获取指定Owner信息
     *
     * @param ownerId owner id
     * @return 回复
     */
    @GetMapping(OwnerServiceRequestPaths.OF_OWNER)
    Mono<ResponseFormat<OwnerDetailsBo>> getOwnerDetails(@RequestParam(RequestParamTables.OWNER_ID_FIELD) Long ownerId);

    /**
     * 获取指定所属者的所有所属者项目
     *
     * @param page 页数
     * @param count 数量
     * @param ownerId 所属者ID
     * @return 路由方法
     */
    @GetMapping(OwnerServiceRequestPaths.OF_OWNER_OF_PROJECT)
    Mono<ResponseFormat<PageChunk<OwnerProject>>> queryOwnerProject(@RequestParam(RequestParamTables.PAGE_FIELD) int page,
                                                                    @RequestParam(RequestParamTables.PAGE_COUNT_FIELD) int count,
                                                                    @RequestParam(RequestParamTables.OWNER_ID_FIELD) long ownerId);

    /**
     * 查询指定所属者的works项目
     *
     * @param page 页数
     * @param count 数量
     * @param ownerId 所属者ID
     * @return 路由方法
     */
    @GetMapping(OwnerServiceRequestPaths.OF_OWNER_OF_WORKS)
    Mono<ResponseFormat<PageChunk<OwnerProject>>> queryOwnerWorks(@RequestParam(RequestParamTables.PAGE_FIELD) int page,
                                                             @RequestParam(RequestParamTables.PAGE_COUNT_FIELD) int count,
                                                             @RequestParam(RequestParamTables.OWNER_ID_FIELD) long ownerId);

    /**
     * 查询指定所属者的范围项目
     *
     * @param page 页数
     * @param count 数量
     * @param ownerId 所属者ID
     * @param scope 项目所属范围
     * @return 路由方法
     */
    @GetMapping(OwnerServiceRequestPaths.OF_OWNER_OF_PROJECT_OF_SCOPE)
    Mono<ResponseFormat<PageChunk<OwnerProject>>> queryOwnerScopeWorks(@RequestParam(RequestParamTables.PAGE_FIELD) int page,
                                                                       @RequestParam(RequestParamTables.PAGE_COUNT_FIELD) int count,
                                                                       @RequestParam(RequestParamTables.OWNER_ID_FIELD) long ownerId,
                                                                       @RequestParam(DefinitionRequestParamTables.SCOPE_FIELD) String scope);
}
