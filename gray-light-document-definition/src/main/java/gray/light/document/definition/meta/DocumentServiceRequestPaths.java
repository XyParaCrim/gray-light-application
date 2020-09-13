package gray.light.document.definition.meta;

import gray.light.owner.meta.OwnerServiceRequestPaths;

/**
 * 定义文档服务请求路径
 *
 * @author XyParaCrim
 */
public final class DocumentServiceRequestPaths {

    public static final String OF_WORKS = OwnerServiceRequestPaths.OF_OWNER + "/works";

    public static final String OF_WORKS_OF_DOCS = OF_WORKS + "/docs";

    public static final String OF_WORKS_OF_DOCS_OF_TREE = OF_WORKS_OF_DOCS + "/tree";

}
