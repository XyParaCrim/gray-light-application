package gray.light.owner.indices;

import floor.search.SearchSupport;
import lombok.Builder;
import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.query.SourceFilter;

/**
 * 所属者搜索索引
 *
 * @author XyParaCrim
 */
@Data
@Builder
@Document(useServerConfiguration = true, indexName = OwnerIndex.OWNER_INDEX)
public class OwnerIndex {

    public static final String OWNER_INDEX = "owner-index-1";

    @Id
    private Long id;


    private String name;


    private String organization;


    /**
     * 返回一个搜索所属者名字和组织的查询构建器
     *
     * @param word 搜索词
     * @return 返回一个搜索所属者名字和组织的查询构建器
     */
    public static QueryBuilder matchNameAndOrganizationQueryBuilder(String word) {
        return SearchSupport.matchFields(word, "name", "organization");
    }

    public static SourceFilter idSourceFilter() {
        return new OwnerSourceFilter();
    }


    /**
     * 返回所属者索引source字段
     */
    private static class OwnerSourceFilter implements SourceFilter {

        @Override
        public String[] getIncludes() {
            return new String[] { "id" };
        }

        @Override
        public String[] getExcludes() {
            return null;
        }
    }

}
