package gray.light.source.handler;

import gray.light.source.service.SourceService;
import gray.light.support.web.RequestParam;
import gray.light.support.web.RequestParamTables;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.ServerResponse;
import perishing.constraint.treasure.chest.collection.FinalVariables;
import perishing.constraint.web.flux.ResponseBuffet;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SourceHandler {

    public static final RequestParam<String> LOCAL_PATH = RequestParamTables.paramTable("path");

    public static final RequestParam<String> SOURCE_SUFFIX = RequestParamTables.paramTable("suffix");

    public static final RequestParam<String> LINK_SUFFIX = RequestParamTables.paramTable("link");

    private final SourceService sourceService;


    public Mono<ServerResponse> uploadLocalFile(FinalVariables<String> params) {
        String path = LOCAL_PATH.get(params);
        String suffix = SOURCE_SUFFIX.get(params);

        return ResponseBuffet.allRight(sourceService.uploadFile(path, suffix));
    }

    public Mono<ServerResponse> deleteLocalFile(FinalVariables<String> params) {
        String link = LINK_SUFFIX.get(params);
        sourceService.deleteFile(link);

        return ResponseBuffet.allRight();
    }

}
