package gray.light.owner.service;

import gray.light.definition.search.hit.LongIdHit;
import gray.light.owner.entity.Owner;
import gray.light.owner.search.operation.OwnerSearchOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.Collections;
import java.util.List;

/**
 * 所属者的搜索服务
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class SearchOwnerService {

    private final OwnerSearchOperation ownerSearchOperation;

    private final ReadableOwnerService readableOwnerService;

    /**
     * 全局搜索搜索者
     *
     * @param words 搜索词
     * @return 搜索到的所属者
     */
    public List<Owner> globalSearchOwner(String words) {
        SearchHits<LongIdHit> searchHits = ownerSearchOperation.simpleSearchOwner(words);

        return searchHits.hasSearchHits() ?
                readableOwnerService.findBatchOwner(LongIdHit.searchHitsToBatchId(searchHits)) :
                Collections.emptyList();
    }

}
