package gray.light.definition.web;

import gray.light.definition.entity.Scope;
import gray.light.support.web.RequestParam;
import gray.light.support.web.RequestParamTables;

public final class DefinitionRequestParamTables {

    public static final String SCOPE_FIELD = "scope";

    public static final RequestParam<Scope> SCOPE = RequestParamTables.
            paramTable(SCOPE_FIELD, DefinitionRequestParamExtractors::extractScope);

    public static RequestParam<Scope> scope() {
        return SCOPE;
    }
}
