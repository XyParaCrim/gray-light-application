package gray.light.note.service;

import gray.light.definition.entity.Scope;
import gray.light.owner.entity.OwnerProject;
import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.entity.ProjectStatus;
import gray.light.owner.service.OverallOwnerService;
import gray.light.owner.service.ProjectDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perishing.constraint.jdbc.Page;

import java.util.List;

/**
 * 笔记只读服务
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class ReadableNoteService {

    private final OverallOwnerService overallOwnerService;

    private final ProjectDetailsService projectDetailsService;

    /**
     * 获取指定所属者的笔记
     *
     * @param ownerId 所属者Id
     * @param page    分页
     * @return 获取指定所属者的笔记
     */
    public List<OwnerProject> noteProject(Long ownerId, Page page) {
        return overallOwnerService.projects(ownerId, Scope.NOTE, page);
    }

    /**
     * 查询指定范围和note的项目详细
     *
     * @param status 项目状态
     * @param page   分页
     * @return 查询指定范围和note的项目详细
     */
    public List<ProjectDetails> findProjectDetailsByStatus(ProjectStatus status, Page page) {
        return projectDetailsService.findScopeProjectDetails(status, Scope.NOTE, page);
    }

    /**
     * 查询指定同步note的项目详细
     *
     * @return 查询指定同步note的项目详细
     */
    public List<ProjectDetails> findSyncProjectDetails() {
        return projectDetailsService.findScopeProjectDetails(ProjectStatus.SYNC, Scope.NOTE, Page.unlimited().nullable());
    }

    /**
     * 查询指定初始化note的项目详细
     *
     * @return 查询指定同步note的项目详细
     */
    public List<ProjectDetails> findInitProjectDetails() {
        return projectDetailsService.findScopeProjectDetails(ProjectStatus.INIT, Scope.NOTE, Page.unlimited().nullable());
    }

}
