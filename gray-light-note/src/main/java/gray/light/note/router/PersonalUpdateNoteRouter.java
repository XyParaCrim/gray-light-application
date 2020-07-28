package gray.light.note.router;

import gray.light.note.handler.NoteUpdateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 个人笔记提供的http请求
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class PersonalUpdateNoteRouter {

    private final NoteUpdateHandler noteUpdateHandler;

    /**
     * 请求添加一个笔记
     *
     * @return 请求处理方法
     */
    @Bean
    public RouterFunction<ServerResponse> addNote() {
        return RouterFunctions.route(RequestPredicates.POST("/owner/note"),
                noteUpdateHandler::createNote);
    }

}
