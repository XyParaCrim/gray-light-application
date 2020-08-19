package gray.light.owner.service;

import gray.light.definition.entity.Scope;
import gray.light.owner.customizer.ProjectDetailsCustomizer;
import gray.light.owner.entity.OwnerProject;
import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.repository.ProjectDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 定义针对Git项目细节的服务，例如：同步情况等
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class WritableProjectDetailsService {

    private final ProjectDetailsRepository projectDetailsRepository;

    private final WritableOwnerProjectService writableOwnerProjectService;

    public boolean addProjectDetails(ProjectDetails projectDetails) {
        return projectDetailsRepository.save(projectDetails);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public boolean  addBookProjectDetailsSafely(OwnerProject project, Scope scope, ProjectDetails uncommited) {
        if (!writableOwnerProjectService.addProject(project)) {
            throw new RuntimeException("Failed to add owner-project of " + scope.getName() + ": " + project);
        }

        ProjectDetailsCustomizer.completeNewBookFromOwner(uncommited, project.getId());
        if (!addProjectDetails(uncommited)) {
            throw new RuntimeException("Failed to add project details: " + project);
        }

        return true;
    }

    /**
     * 批量更新文档状态
     *
     * @param projectDetails 文档
     * @return 是否更新成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateStatus(List<ProjectDetails> projectDetails) {
        return projectDetailsRepository.batchUpdateProjectDetailsStatus(projectDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean batchUpdateStatusAndVersion(List<ProjectDetails> projectDetails) {
        return projectDetailsRepository.batchUpdateProjectDetailsStatusAndVersion(projectDetails);
    }

}
