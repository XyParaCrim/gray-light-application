package gray.light.book.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * book结构项目的回收表
 *
 * @author XyParaCrim
 */
@Mapper
public interface BookTrash {

    /**
     * 将指定项目的所有chapter置入回收表，再将
     * 其所有catalogs和chapters删除.
     * 因为chapters和catalogs是联级关系，
     * 会根据它们的外键进行删除
     *
     * @param projectId book项目ID
     */
    void push(@Param("projectId") Long projectId);

}
