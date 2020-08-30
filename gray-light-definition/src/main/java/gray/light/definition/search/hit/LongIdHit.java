package gray.light.definition.search.hit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 一个只包含long类型Id的类，用于一些相似的搜索结果
 *
 * @author XyParaCrim
 */
@Data
@AllArgsConstructor
public class LongIdHit {

    private Long id;

    /**
     * 将搜索后的命中转换成Id列表
     *
     * @param hits 搜索命中
     * @return Id列表
     */
    public static List<Long> searchHitsToBatchId(@NonNull SearchHits<LongIdHit> hits) {
        return hits.stream().
                map(hit -> hit.getContent().getId()).
                collect(Collectors.toList());
    }

}
