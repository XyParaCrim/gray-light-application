package gray.light.note.router;

import gray.light.note.handler.NoteQueryHandler;
import gray.light.owner.router.OwnerRequestPredicates;
import gray.light.support.web.RequestParamTables;
import gray.light.support.web.RequestSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.*;

import java.util.function.Predicate;

/**
 * 定义关于笔记查询的路由请求（只读）
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class PersonalQueryNoteRouter {

    private final NoteQueryHandler queryNote;


    /**
     * 查询所属者的所有笔记
     *
     * @return 请求处理方法
     */
    @Bean
    public RouterFunction<ServerResponse> queryNoteRequiredOwner() {
        RequestPredicate predicate = RequestPredicates.GET("/owner/note").and(OwnerRequestPredicates.ownerId());
        HandlerFunction<ServerResponse> handler = RequestSupport.routerFunction(queryNote::queryNoteByOwnerId,
                RequestParamTables.page(), RequestParamTables.ownerId());

        return RouterFunctions.route(predicate, handler);
    }

    /**
     * 获取指定笔记的树形结构
     *
     * @return 请求处理方法
     */
    @Bean
    public RouterFunction<ServerResponse> getNoteTree() {
        return RouterFunctions.route(
                RequestPredicates.GET("/owner/note/tree").
                        and(RequestPredicates.queryParam("id", Predicate.not(StringUtils::isEmpty))),
                queryNote::queryNoteTree
        );
    }

}
