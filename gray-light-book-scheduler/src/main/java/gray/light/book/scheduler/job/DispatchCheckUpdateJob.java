package gray.light.book.scheduler.job;

import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.entity.ProjectStatus;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * 分发需要检测更新仓库任务
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class DispatchCheckUpdateJob implements Job {

    private static final String CHECK_ROUND_KEY = "check_round_key";

    private static final String SKIP_COUNT_KEY = "skip_count";

    private static final String PARENT_PATH_KEY = "parent_path_key";

    private final CuratorFramework client;

    private final AmqpAdmin amqpAdmin;

    private final AmqpTemplate amqpTemplate;

    @Setter
    private Function<List<ProjectStatus>, List<ProjectDetails>> fetchDetails;

    @Override
    public void execute(JobExecutionContext context) {
        if (processLastRound(context)) {
            List<ProjectDetails> projectDetails = fetchDetails.
                    apply(Arrays.asList(ProjectStatus.SYNC, ProjectStatus.FAILURE_CHECK));

            UUID nextRound;
            try {
                nextRound = signNextRound((String) context.get(PARENT_PATH_KEY));
            } catch (Exception e) {
                log.error("Failed to sign next round to zookeeper: {}",
                        client.getZookeeperClient().getCurrentConnectionString(), e);

                return;
            }

            // 发生消息
            // projectDetails.forEach(amqpTemplate::convertAndSend);
            // 发送毒药
        }
    }

    private boolean processLastRound(JobExecutionContext context) {
        UUID roundId = (UUID) context.get(CHECK_ROUND_KEY);
        if (roundId == null) {
            return true;
        }

        String zPath = PARENT_PATH_KEY + "/" + roundId.toString();
        boolean completed = false;

        try {
            completed = doneLastRound(zPath);
        } catch (Exception e) {
            log.error("Failed to check update sign of last round: {}", zPath, e);
        }

        if (completed) {
            resetRound(context);
            return true;
        } else {
            skipRound(context);
            return false;
        }
    }

    private void resetRound(JobExecutionContext context) {
        context.put(CHECK_ROUND_KEY, null);
        context.put(SKIP_COUNT_KEY, 0);
    }

    private void skipRound(JobExecutionContext context) {
        Integer count = (Integer) context.get(SKIP_COUNT_KEY);
        context.put(SKIP_COUNT_KEY, count == null ? 1 : count + 1);
    }

    private boolean doneLastRound(String path) throws Exception {
        return client.checkExists().forPath(path) == null;
    }

    private UUID signNextRound(String parentPath) throws Exception {
        UUID nextRoundSign = UUID.randomUUID();
        client.create().creatingParentContainersIfNeeded().forPath(parentPath + "/" + nextRoundSign.toString());

        return nextRoundSign;
    }

}
