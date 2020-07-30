package gray.light.book.service;

import gray.light.book.repository.BookTrash;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 提供回收
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class TrashBookService {

    private final BookTrash trash;

    /**
     * 删除指定project的所有目录和章节（因为book本身就是一个项目，项目
     * 之间的关系需要自行维护）
     *
     * @param projectId 指定project
     */
    public void deleteBookByOwnerProject(Long projectId) {
        trash.push(projectId);
    }

}
