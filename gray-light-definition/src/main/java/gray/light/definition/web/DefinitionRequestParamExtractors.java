package gray.light.definition.web;

import gray.light.definition.entity.Scope;
import gray.light.support.error.ExtractRequestParamException;
import gray.light.support.web.RequestParamExtractor;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Optional;

/**
 * 定义应用系统的参数提取器
 *
 * @author XyParaCrim
 */
public final class DefinitionRequestParamExtractors {

    /**
     * {@link Scope}类型的提取器方法
     *
     * @param request 请求
     * @param key 参数名
     * @return 转换而来的scope对象
     */
    public static Scope extractScope(ServerRequest request, String key) {
        return SCOPE_EXTRACTOR.extract(request, key);
    }

    private static final RequestParamExtractor<Scope> SCOPE_EXTRACTOR = (request, key) -> {
        Optional<String> param = request.queryParam(key);
        if (param.isEmpty()) {
            throw new ExtractRequestParamException("Missing value of parameter: " + key);
        }

        for (Scope scope : Scope.SUPPORTED) {
            if (param.get().equals(scope.getName())) {
                return scope;
            }
        }

        throw new ExtractRequestParamException("Unsupported scope value: " + param.get());
    };


}
