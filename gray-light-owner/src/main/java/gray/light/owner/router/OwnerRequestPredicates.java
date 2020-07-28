package gray.light.owner.router;

import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;

/**
 * 关于所属者请求谓语
 *
 * @author XyParaCrim
 */
public final class OwnerRequestPredicates {

    private static final RequestPredicate OWNER_ID_PREDICATE = RequestPredicates.queryParam("ownerId", StringUtils::hasText);

    private static final RequestPredicate WORKS_ID_PREDICATE = RequestPredicates.queryParam("worksId", StringUtils::hasText);

    public static RequestPredicate ownerId() {
        return OWNER_ID_PREDICATE;
    }

    public static RequestPredicate worksId() {
        return WORKS_ID_PREDICATE;
    }

}
