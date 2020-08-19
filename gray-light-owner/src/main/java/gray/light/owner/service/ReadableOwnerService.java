package gray.light.owner.service;

import gray.light.owner.entity.Owner;
import gray.light.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;

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

}
