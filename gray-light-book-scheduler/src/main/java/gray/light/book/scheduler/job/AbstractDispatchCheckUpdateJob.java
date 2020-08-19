package gray.light.book.scheduler.job;

import gray.light.owner.definition.entity.ProjectDetails;
import gray.light.owner.definition.entity.ProjectStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.*;

/**
 * 分发需要检测更新仓库任务
 *
 * @author XyParaCrim
 */
@Slf4j
abstract class AbstractDispatchCheckUpdateJob implements Job {

    @Setter
    private FetchProjectDetailsByStatus fetcher;

    @Override
    public void execute(JobExecutionContext context) {
        List<ProjectDetails> projectDetails;
        if (processLastRound(context) &&
                !(projectDetails = fetchSynchronizedProject()).isEmpty()) {

            signNextRound(context).
                    ifPresent(uuid -> {
                        dispatch(projectDetails, uuid, context);
                        afterDispatchCurrentRound(uuid, context);
                    });

        }
    }


    /**
     * 是否需要进行下一轮任务的分配
     *
     * @param context 任务上下文
     * @return 是否需要进行下一轮任务的分配
     */
    private boolean processLastRound(JobExecutionContext context) {
        UUID roundId = getFromContext(CHECK_ROUND_KEY, context);
        if (roundId == null) {
            return true;
        }

        boolean completed = false;
        try {
            completed = doneLastRound(roundId, context);
        } catch (Exception e) {
            log.error("Failed to check update sign of last round: {}", roundId.toString(), e);
        }

        if (completed) {
            resetRound(context);
            return true;
        } else {
            skipRound(context);
            return false;
        }
    }

    private List<ProjectDetails> fetchSynchronizedProject() {
        List<ProjectStatus> synchronizedStatus = Arrays.asList(ProjectStatus.SYNC, ProjectStatus.FAILURE_CHECK);

        try {
            return fetcher.fetch(synchronizedStatus);
        } catch (Exception e) {
            log.error("Failed to fetch synchronized project details, caused by fetcher", e);
        }

        return Collections.emptyList();
    }

    private void resetRound(JobExecutionContext context) {
        setToContext(CHECK_ROUND_KEY, null, context);
        setToContext(SKIP_COUNT_KEY, 0, context);
    }

    private void skipRound(JobExecutionContext context) {
        Integer count = getFromContext(SKIP_COUNT_KEY, context);
        setToContext(SKIP_COUNT_KEY, count == null ? 1 : count + 1, context);
    }

     protected void afterDispatchCurrentRound(UUID roundId, JobExecutionContext context) {
         setToContext(CHECK_ROUND_KEY, roundId, context);
     }

     @SuppressWarnings("unchecked")
     protected <T> T getFromContext(String key, JobExecutionContext context) {
         ValueSlot<T> slot = (ValueSlot<T>) context.getMergedJobDataMap().get(key);

        return slot.getValue();
     }

    @SuppressWarnings("unchecked")
    protected <T> void setToContext(String key, T value, JobExecutionContext context) {
        ValueSlot<T> slot = (ValueSlot<T>) context.getMergedJobDataMap().get(key);

        slot.setValue(value);
    }

    // 需要实现的方法

    /**
     * 标志新一轮任务分配
     *
     * @param context 任务上下文
     * @return 新一轮的UUID
     */
    protected abstract Optional<UUID> signNextRound(JobExecutionContext context);

    /**
     * 根据项目列表，分发需要更新的项目任务
     *
     * @param details 需要更新的项目
     * @param nextUuid 这一轮更新任务的Id
     * @param context 任务上下文
     */
    protected abstract void dispatch(List<ProjectDetails> details, UUID nextUuid, JobExecutionContext context);

    /**
     * 确定是否完成上一轮的任务
     *
     * @param lastUUID 上一轮的ID
     * @param context 任务上下文
     * @return 是否完成上一轮的任务
     * @throws Exception 检测时发生错误
     */
    protected abstract boolean doneLastRound(UUID lastUUID, JobExecutionContext context) throws Exception;

    // 储存在JobContext里信息的key

    private static final String CHECK_ROUND_KEY = "check_round_key";

    private static final String SKIP_COUNT_KEY = "skip_count";

    protected static JobDataMap requiredDataMap(FetchProjectDetailsByStatus fetcher) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("fetcher", fetcher);
        dataMap.put(CHECK_ROUND_KEY, new ValueSlot<UUID>());
        dataMap.put(SKIP_COUNT_KEY, new ValueSlot<Integer>());

        return dataMap;
    }

    static class ValueSlot<T> {

        @Getter
        @Setter
        T value = null;
    }

}
