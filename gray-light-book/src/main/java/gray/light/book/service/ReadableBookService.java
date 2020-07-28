package gray.light.book.service;


import gray.light.book.entity.BookCatalog;
import gray.light.book.entity.BookChapter;
import gray.light.book.repository.BookCatalogRepository;
import gray.light.book.repository.BookChapterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perishing.constraint.jdbc.Page;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

/**
 * 提供只读服务
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class ReadableBookService {

    private final BookCatalogRepository bookCatalogRepository;

    private final BookChapterRepository bookChapterRepository;

    /**
     * 根据文档仓库Id，返回属于此仓库的所有目录与章节
     *
     * @param documentId 文档仓库ID
     * @return 属于此仓库的所有目录与章节
     */
    @Transactional(readOnly = true)
    public Tuple2<List<BookCatalog>, List<BookChapter>> catalogAndChapter(long documentId) {
        List<BookCatalog> catalogs = bookCatalogs(documentId, Page.unlimited());
        List<BookChapter> chapters = bookChapters(documentId, Page.unlimited());

        return Tuples.of(catalogs, chapters);
    }

    /**
     * 查询指定项目ID的所有目录
     *
     * @param ownerProjectId 指定项目ID
     * @param page 分页
     * @return 查询指定项目ID的所有目录
     */
    public List<BookCatalog> bookCatalogs(Long ownerProjectId, Page page) {
        return bookCatalogRepository.findByOwnerProjectId(ownerProjectId, page.nullable());
    }

    /**
     * 查询指定项目ID的所有章节
     *
     * @param ownerProjectId 项目ID
     * @param page 分页
     * @return 查询指定项目ID的所有章节
     */
    public List<BookChapter> bookChapters(Long ownerProjectId, Page page) {
        return bookChapterRepository.findByOwnerProjectId(ownerProjectId, page.nullable());
    }
}
