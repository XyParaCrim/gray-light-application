package gray.light.book.scheduler.job;

import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.entity.ProjectStatus;

import java.util.List;

/**
 * 定义根据项目状态获取项目详细信息的方法接口
 *
 * @author XyParaCrim
 * @see AbstractDispatchCheckUpdateJob
 */
@FunctionalInterface
public interface FetchProjectDetailsByStatus {

    /**
     * 根据项目状态获取项目详细信息
     *
     * @param statusList 匹配的状态
     * @return 根据项目状态获取项目详细信息
     */
    List<ProjectDetails> fetch(List<ProjectStatus> statusList);

}
