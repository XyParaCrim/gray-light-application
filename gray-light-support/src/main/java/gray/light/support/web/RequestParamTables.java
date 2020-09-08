package gray.light.support.web;

import perishing.constraint.jdbc.Page;
import perishing.constraint.treasure.chest.CollectionsTreasureChest;

import java.nio.file.Path;

/**
 * 常用的请求参数类型转换
 *
 * @author XyParaCrim
 */
public final class RequestParamTables {

    public static final String PAGE_FIELD = "page";

    public static final String PAGE_COUNT_FIELD = "count";

    public static final String OWNER_ID_FIELD = "ownerId";

    public static final RequestParam<Long> OWNER_ID = paramTable("ownerId", RequestParamExtractors::extractLong);

    public static final RequestParam<Long> WORKS_ID = paramTable("worksId", RequestParamExtractors::extractLong);

    public static final RequestParam<Long> ID = paramTable("id", RequestParamExtractors::extractLong);

    public static final RequestParam<Page> PAGE = paramTable(PAGE_FIELD, RequestParamExtractors::extractPage);

    public static final RequestParam<Page> TAGS = paramTable("tag", RequestParamExtractors::extractPage);

    public static final RequestParam<Path> LOCATION = paramTable("location", RequestParamExtractors::extractPath);

    public static final RequestParam<String> SEARCH = paramTable("search", RequestParamExtractors::extract);

    public static  <T> RequestParam<T> paramTable(String name, RequestParamExtractor<T> extractor) {
        return new RequestParam<>(CollectionsTreasureChest.entry(name, extractor));
    }

    public static RequestParam<String> paramTable(String name) {
        return paramTable(name, RequestParamExtractors::extract);
    }

    public static RequestParam<Long> ownerId() {
        return OWNER_ID;
    }

    public static RequestParam<Long> worksId() {
        return WORKS_ID;
    }

    public static RequestParam<Long> id() {
        return ID;
    }

    public static RequestParam<Page> page() {
        return PAGE;
    }

    public static RequestParam<Path> location() {
        return LOCATION;
    }

    public static RequestParam<String> search() {
        return SEARCH;
    }

}
