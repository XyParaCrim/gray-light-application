package gray.light.document.service;

import gray.light.document.repository.WorksDocumentRepository;
import gray.light.owner.definition.entity.OwnerProject;
import gray.light.owner.definition.entity.ProjectDetails;
import gray.light.owner.definition.entity.ProjectStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import perishing.constraint.jdbc.Page;

import java.util.List;

/**
 * 提供文档只读服务
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class ReadableDocumentService {

    private final WorksDocumentRepository worksDocumentRepository;

    /**
     * 获取指定项目状态的详细
     *
     * @param status 项目状态
     * @param page   分页
     * @return 获取指定项目状态的详细
     */
    @Transactional(readOnly = true)
    public List<ProjectDetails> findProjectDetailsByStatus(ProjectStatus status, Page page) {
        return worksDocumentRepository.findProjectDetailsByStatus(status, page.nullable());
    }

    /**
     * 获取所有状态为同步的项目
     *
     * @return 获取所有状态为同步的项目
     */
    public List<ProjectDetails> findSyncProject() {
        return worksDocumentRepository.findProjectDetailsByStatus(ProjectStatus.SYNC, Page.unlimited().nullable());
    }

    /**
     * 获取所有状态为初始化的项目
     *
     * @return 获取所有状态为初始化的项目
     */
    public List<ProjectDetails> findInitProject() {
        return worksDocumentRepository.findProjectDetailsByStatus(ProjectStatus.WAIT_PERSISTENCE, Page.unlimited().nullable());
    }

    /**
     * 查询works的所有文档
     *
     * @param worksId works-id
     * @param page 分页
     * @return 文档项目
     */
    @Transactional(readOnly = true)
    public List<OwnerProject> findDocumentByWorks(Long worksId, Page page) {
        return worksDocumentRepository.findOwnerProjectByWorksId(worksId, page.nullable());
    }

}
