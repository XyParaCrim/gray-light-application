package gray.light.document.service;

import gray.light.definition.entity.Scope;
import gray.light.document.customizer.WorksDocumentCustomizer;
import gray.light.document.entity.WorksDocument;
import gray.light.document.repository.WorksDocumentRepository;
import gray.light.owner.entity.OwnerProject;
import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.service.ProjectDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 提供对于文档之间的关系功能，例如：文件树、查询、删除等等
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class WritableDocumentService {

    private final WorksDocumentRepository worksDocumentRepository;

    private final ProjectDetailsService projectDetailsService;

    /**
     * 为works添加新文档，仅更新数据库
     *
     * @param documentOwnerProject 添加的新文档项目
     * @param projectId            要添加的项目Id
     * @param uncommited           未提交的项目详细
     * @return 是否创建并保存成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean addDocumentToWorks(Long projectId, OwnerProject documentOwnerProject, ProjectDetails uncommited) {
        if (projectDetailsService.addBookProjectDetailsSafely(documentOwnerProject, Scope.DOCUMENT, uncommited)) {

            WorksDocument document = WorksDocumentCustomizer.generate(documentOwnerProject, projectId);
            if (worksDocumentRepository.save(document)) {
                return true;
            }
            throw new RuntimeException("Failed to apply relation between works and document: " + documentOwnerProject);
        }

        return false;
    }

}
