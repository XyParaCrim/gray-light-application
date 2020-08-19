package gray.light.owner.service;

import gray.light.definition.entity.Scope;
import gray.light.owner.customizer.ProjectDetailsCustomizer;
import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.entity.ProjectStatus;
import gray.light.owner.repository.ProjectDetailsRepository;
import lombok.RequiredArgsConstructor;
import perishing.constraint.jdbc.Page;

import java.util.List;

/**
 * 提供项目记录数据读取服务
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class ReadableProjectDetailsService {

    private final ProjectDetailsRepository projectDetailsRepository;

    /**
     * 查询指定范围和状态的项目详细
     *
     * @param status 项目状态
     * @param scope 特定范围
     * @param page 分页
     * @return 查询指定范围和状态的项目详细
     */
    public List<ProjectDetails> findScopeProjectDetails(ProjectStatus status, Scope scope, Page page) {
        return projectDetailsRepository.findByStatusAndScopeAndType(status, scope.getName(), ProjectDetailsCustomizer.OWNER_TYPE, page.nullable());
    }

    /**
     * 查询指定状态的项目详细
     *
     * @param projectStatuses 查询的项目状态
     * @return 指定状态的项目详细
     */
    public List<ProjectDetails> findProjectDetailsByStatsList(List<ProjectStatus> projectStatuses) {
        return projectDetailsRepository.findByStatusList(projectStatuses);
    }

}
