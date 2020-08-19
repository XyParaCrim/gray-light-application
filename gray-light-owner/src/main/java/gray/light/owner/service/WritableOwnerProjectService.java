package gray.light.owner.service;

import gray.light.owner.entity.OwnerProject;
import gray.light.owner.repository.OwnerProjectRepository;
import lombok.RequiredArgsConstructor;

/**
 * 提供所属者项目更改服务
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class WritableOwnerProjectService {

    private final OwnerProjectRepository projectRepository;

    /**
     * 添加指定所属者项目
     *
     * @param project 指定所属者项目
     * @return 是否添加成功
     */
    public boolean addProject(OwnerProject project) {
        return projectRepository.save(project);
    }

}
