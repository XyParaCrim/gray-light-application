package gray.light.owner.search.operation;

import gray.light.definition.search.hit.LongIdHit;
import gray.light.owner.indices.OwnerIndex;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * 所属者相关的搜索操作
 *
 * @author XyParaCrim
 */
public class OwnerSearchOperation {

    private final ElasticsearchRestTemplate elasticsearchOperations;

    private final IndexCoordinates indexCoordinates;

    public OwnerSearchOperation(ElasticsearchRestTemplate elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.indexCoordinates = elasticsearchOperations.getIndexCoordinatesFor(OwnerIndex.class);
    }

    /**
     * 简单地对所属者进行搜索
     *
     * @param words 搜索词
     * @return 返回所有匹配结果
     */
    public SearchHits<LongIdHit> simpleSearchOwner(String words) {
        NativeSearchQuery query = new NativeSearchQueryBuilder().
                withQuery(OwnerIndex.matchNameAndOrganizationQueryBuilder(words)).
                withSourceFilter(OwnerIndex.idSourceFilter()).
                build();

        return elasticsearchOperations.search(query, LongIdHit.class, indexCoordinates);
    }

}
