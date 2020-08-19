package gray.light.book.service;

import gray.light.book.entity.BookCatalog;
import gray.light.book.entity.BookChapter;
import gray.light.book.repository.BookCatalogRepository;
import gray.light.book.repository.BookChapterRepository;
import gray.light.owner.definition.entity.ProjectDetails;
import gray.light.owner.service.ProjectDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 提供可写服务
 *
 * @author XyParaCrim
 */
@CommonsLog
@RequiredArgsConstructor
public class WritableBookService {

    private final BookCatalogRepository bookCatalogRepository;

    private final BookChapterRepository bookChapterRepository;

    private final ProjectDetailsService projectDetailsService;


    @Transactional(rollbackFor = Exception.class)
    public boolean batchSaveBookCatalogs(List<BookCatalog> catalogs) {
        return bookCatalogRepository.batchSave(catalogs);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean batchSaveBookChapters(List<BookChapter> chapters) {
        return bookChapterRepository.batchSave(chapters);
    }

    /**
     * 批量更新文档，以及文档包含的目录、文章
     *
     * @param projectDetails 文档
     * @param catalogs  目录
     * @param chapters  文章
     * @return 是否更新成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSyncBookFromPending(List<ProjectDetails> projectDetails, List<BookCatalog> catalogs, List<BookChapter> chapters) {
        if (projectDetails.isEmpty()) {
            return true;
        }

        if (!projectDetailsService.batchUpdateStatusAndVersion(projectDetails)) {
            return false;
        }

        if (catalogs.isEmpty()) {
            return true;
        }

        if (!batchSaveBookCatalogs(catalogs)) {
            return false;
        }

        if (chapters.isEmpty()) {
            return true;
        }

        return batchSaveBookChapters(chapters);
    }

    /**
     * 删除指定project的所有目录和章节
     *
     * @param projectId 指定project
     * @return 是否删除成功
     */
    public boolean deleleAllFromOwnerProject(Long projectId) {
        return bookCatalogRepository.deleteByOwnerProjectId(projectId) &&
                bookChapterRepository.deleteByOwnerProjectId(projectId);
    }

}
