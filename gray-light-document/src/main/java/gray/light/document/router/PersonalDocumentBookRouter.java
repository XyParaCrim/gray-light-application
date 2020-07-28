package gray.light.document.router;

import gray.light.book.handler.BookQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Predicate;

/**
 * 引用Book结构仓库服务
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class PersonalDocumentBookRouter {

    private final BookQueryHandler bookQueryHandler;

    /**
     * 查询文档仓库的结构树
     *
     * @return Response of Publisher
     */
    @Bean
    public RouterFunction<ServerResponse> getFavoriteDocumentRepositoryTree() {
        return RouterFunctions.route(
                RequestPredicates.GET("/owner/works/docs/tree").
                        and(RequestPredicates.queryParam("id", Predicate.not(StringUtils::isEmpty))),
                bookQueryHandler::queryBookTree
        );
    }
}
