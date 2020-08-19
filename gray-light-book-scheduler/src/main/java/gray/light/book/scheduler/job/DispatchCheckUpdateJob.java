package gray.light.book.scheduler.job;

import gray.light.owner.entity.ProjectDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 使用amqp分发更新任务，zookeeper标记更新轮次
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class DispatchCheckUpdateJob extends AbstractDispatchCheckUpdateJob {

    private static final String DEFAULT_PARENT_PATH_KEY = "/schedule";

    private static final String DEFAULT_ROUTER_KEY = "check-task";

    private static final String PARENT_PATH_KEY = "parent_path_key";

    private static final String ROUTER_KEY = "dispatch_job_key";

    private final AmqpTemplate amqpTemplate;

    private final CuratorFramework client;

    @Override
    protected Optional<UUID> signNextRound(JobExecutionContext context) {
        UUID nextRoundSign = UUID.randomUUID();
        try {
            client.create().creatingParentContainersIfNeeded().forPath(parentPath(context) + "/" + nextRoundSign.toString());
        } catch (Exception e) {
            log.error("Failed to sign next round to zookeeper: {}",
                    client.getZookeeperClient().getCurrentConnectionString(), e);

            return Optional.empty();
        }

        return Optional.of(nextRoundSign);
    }

    @Override
    protected void dispatch(List<ProjectDetails> details, UUID nextUuid, JobExecutionContext context) {
        // TODO: 发生消息考虑不使用序列化，直接发送JSON，只发送关键信息
        amqpTemplate.convertAndSend(routeKey(context), details);
    }

    @Override
    protected boolean doneLastRound(UUID roundId, JobExecutionContext context) throws Exception {
        String zPath = parentPath(context) + "/" + roundId.toString();

        return client.checkExists().forPath(zPath) == null;
    }

    private String parentPath(JobExecutionContext context) {
        return getOrDefault(getFromContext(PARENT_PATH_KEY, context), DEFAULT_PARENT_PATH_KEY);
    }

    private String routeKey(JobExecutionContext context) {
        return getOrDefault(getFromContext(ROUTER_KEY, context), DEFAULT_ROUTER_KEY);
    }

    private String getOrDefault(String setting, String defaultString) {
        return setting == null ? defaultString : setting;
    }

    /**
     * 返回该任务需要数据映射表
     *
     * @param fetcher 获取项目详细的函数
     * @return 返回该任务需要数据映射表
     */
    public static JobDataMap requiredDataMap(FetchProjectDetailsByStatus fetcher) {
        JobDataMap dataMap = AbstractDispatchCheckUpdateJob.requiredDataMap(fetcher);
        dataMap.put(PARENT_PATH_KEY, new ValueSlot<String>());
        dataMap.put(ROUTER_KEY, new ValueSlot<String>());

        return  dataMap;
    }
}
