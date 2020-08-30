package gray.light.owner.service;

import gray.light.owner.entity.Owner;
import gray.light.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import perishing.constraint.treasure.chest.TreasureChest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 提供所属者项目数据读取服务
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class ReadableOwnerService {

    private final OwnerRepository ownerRepository;

    /**
     *  查询指定id的所属者
     *
     * @param ownerId 指定所属者id
     * @return 返回查询结果
     */
    public Optional<Owner> findOwner(Long ownerId) {
        return ownerRepository.findById(ownerId);
    }

    /**
     * 批量查询所属者
     *
     * @param ownerIdList 所属者ID列表
     * @return 批量查询所属者
     */
    public List<Owner> findBatchOwner(List<Long> ownerIdList) {
        if (TreasureChest.hasElementCollection(ownerIdList)) {
            // 如果列表元素为一，走findOwner
            if (ownerIdList.size() == 1) {
                Optional<Owner> foundOwner = findOwner(TreasureChest.firstFromList(ownerIdList));
                if (foundOwner.isPresent()) {
                    return Collections.singletonList(foundOwner.get());
                }
            } else {
                return ownerRepository.findBatch(ownerIdList);
            }
        }

        return Collections.emptyList();
    }

}
