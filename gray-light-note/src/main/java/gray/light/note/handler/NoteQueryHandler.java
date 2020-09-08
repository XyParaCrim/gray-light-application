package gray.light.note.handler;


import gray.light.book.handler.BookQueryHandler;
import gray.light.note.service.ReadableNoteService;
import gray.light.support.web.RequestParamTables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import perishing.constraint.jdbc.Page;
import perishing.constraint.treasure.chest.collection.FinalVariables;
import perishing.constraint.web.flux.ResponseBuffet;
import reactor.core.publisher.Mono;

/**
 * 处理作品笔记的查询请求
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class NoteQueryHandler {

    private final ReadableNoteService readableNoteService;

    private final BookQueryHandler bookQueryHandler;

    /**
     * 查询指定所属者的note
     *
     * @param variables 参数
     * @return 回复
     */
    public Mono<ServerResponse> queryNoteByOwnerId(FinalVariables<String> variables) {
        Page page = RequestParamTables.page().get(variables);
        Long ownerId = RequestParamTables.ownerId().get(variables);

        return ResponseBuffet.allRight(readableNoteService.noteProject(ownerId, page));
    }


    /**
     * 查询笔记仓库的结构树
     *
     * @param request 服务请求
     * @return Response of Publisher
     */
    public Mono<ServerResponse> queryNoteTree(ServerRequest request) {
        // TODO 验证
        return bookQueryHandler.queryBookTree(request);
    }
}
