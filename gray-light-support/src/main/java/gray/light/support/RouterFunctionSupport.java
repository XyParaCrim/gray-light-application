package gray.light.support;

import gray.light.support.web.RequestParam;
import gray.light.support.web.RequestParamVariables;
import gray.light.support.web.RequestSupport;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.*;
import perishing.constraint.web.KnownBusinessException;
import perishing.constraint.web.flux.ResponseBuffet;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 处理路由方法
 *
 * @author XyParaCrim
 */
@Slf4j
@SuppressWarnings("unused")
public final class RouterFunctionSupport {

    private static final int DEFAULT_PARAM_COUNT = 16;

    /**
     * 返回一个Get类型请求的路由构建器
     *
     * @param pattern 请求路径模式
     * @return 返回一个Get类型请求的路由构建器
     */
    public static RouterBuilder<?> get(String pattern) {
        return fromPredicate(RequestPredicates.GET(pattern));
    }

    /**
     * 返回一个Post类型请求的路由构建器
     *
     * @param pattern 请求路径模式
     * @return 返回一个Post类型请求的路由构建器
     */
    public static <T> RouterBuilder<T> post(String pattern, Class<T> bodyClass) {
        return new RouterBuilder<T>(RequestPredicates.POST(pattern)).
                extractBody(bodyClass);
    }

    /**
     * 返回一个路由构建器
     *
     * @return 返回一个路由构建器
     */
    public static <T> RouterBuilder<T> fromPredicate(RequestPredicate predicate) {
        return new RouterBuilder<>(predicate);
    }

    /**
     * 装载路由方法
     *
     * @param predicate       请求谓语
     * @param handlerFunction 请求处理函数
     * @return 路由函数
     */
    public static RouterFunction<ServerResponse> routeWithHandlingKnownException(
            RequestPredicate predicate, HandlerFunction<ServerResponse> handlerFunction) {

        return RouterFunctions.route(predicate, handlingKnownException(handlerFunction));
    }

    private static HandlerFunction<ServerResponse> handlingKnownException(
            HandlerFunction<ServerResponse> handlerFunction) {
        return request -> {
            try {
                return handlerFunction.handle(request);
            } catch (KnownBusinessException known) {
                return ResponseBuffet.failByKnownException(known);
            }
        };
    }


    /**
     * 路由函数构建器
     */
    @RequiredArgsConstructor
    public static class RouterBuilder<T> {

        @NonNull
        private final RequestPredicate predicate;

        private ArrayList<RequestParam<?>> params;

        private Class<T> bodyClass;

        private HandlerFunction<ServerResponse> noParamsHandler;

        private Function<RequestParamVariables, Mono<ServerResponse>> paramsHandler;

        private BiFunction<ServerRequest, RequestParamVariables, Mono<ServerResponse>> mixedHandler;

        public RouterBuilder<T> and(RequestPredicate other) {
            predicate.and(other);
            return this;
        }

        public RouterBuilder<T> negate() {
            predicate.negate();
            return this;
        }

        public RouterBuilder<T> or(RequestPredicate other) {
            predicate.or(other);
            return this;
        }

        public RouterBuilder<T> requireParam(String param) {
            predicate.and(RequestPredicates.queryParam(param, StringUtils::hasText));
            return this;
        }

        public RouterBuilder<T> requireParam(RequestParam<?> requestParam) {
            Objects.requireNonNull(requestParam);
            predicate.and(RequestPredicates.queryParam(requestParam.getKey(), StringUtils::hasText));
            return extractParam(requestParam);
        }

        public RouterBuilder<T> extractParam(RequestParam<?> requestParam) {
            if (Objects.nonNull(requestParam)) {
                ensureParams();
                params.add(requestParam);
            }
            return this;
        }

        public RouterBuilder<T> extractBody(Class<T> bodyClass) {
            this.bodyClass = bodyClass;
            return this;
        }

        public RouterBuilder<T> handle(HandlerFunction<ServerResponse> handler) {
            this.noParamsHandler = Objects.requireNonNull(handler);
            return this;
        }

        public RouterBuilder<T> handle(Function<RequestParamVariables, Mono<ServerResponse>> handler) {
            this.paramsHandler = Objects.requireNonNull(handler);
            return this;
        }

        public RouterBuilder<T> handle(BiFunction<ServerRequest, RequestParamVariables, Mono<ServerResponse>> handler) {
            this.mixedHandler = Objects.requireNonNull(handler);
            return this;
        }

        public RouterFunction<ServerResponse> build() {
            if (params == null || params.isEmpty()) {
                return routeWithHandlingKnownException(predicate, noParamsHandler);
            }

            if (mixedHandler != null) {
                return routeWithHandlingKnownException(predicate, request -> {
                    RequestParamVariables variables = RequestSupport.extractVariables(request, params);

                    if (bodyClass == null) {
                        return mixedHandler.apply(request, variables);
                    }

                    return request.bodyToMono(bodyClass).flatMap(body -> {
                        variables.setBodyObject(bodyClass, body);

                        return mixedHandler.apply(request, variables);
                    });

                });
            }

            if (paramsHandler != null) {
                return routeWithHandlingKnownException(predicate, request -> {
                    RequestParamVariables variables = RequestSupport.extractVariables(request, params);

                    if (bodyClass == null) {
                        return paramsHandler.apply(variables);
                    }

                    return request.bodyToMono(bodyClass).flatMap(body -> {
                        variables.setBodyObject(bodyClass, body);

                        return paramsHandler.apply(variables);
                    });

                });
            }

            throw new IllegalArgumentException("Error handler to process server request");
        }


        private void ensureParams() {
            if (params == null) {
                params = new ArrayList<>(DEFAULT_PARAM_COUNT);
            }
        }

    }

}
