package gray.light.book.scheduler.job;

import gray.light.book.entity.BookChapter;
import gray.light.book.service.*;
import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.entity.ProjectStatus;
import gray.light.owner.service.ProjectDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import perishing.constraint.jdbc.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;

/**
 * 检查文档仓库是否更新，自动拉取最新版本，并存储到数据库
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class AutoCheckBookUpdateJob implements Job {

    // 需要传递的参数

    public static final String SYNC_STATUS_PROJECT_DETAILS = "fetchSyncDetails";

    @Setter
    private Supplier<List<ProjectDetails>> fetchSyncDetails;

    private final TrashBookService trashBookService;

    private final ProjectDetailsService projectDetailsService;

    private final LocalCacheBookService localCacheBookService;

    private final ReadableBookService readableBookService;

    private final SourceBookService bookSourceService;

    @Override
    public void execute(JobExecutionContext context) {
        // TDOD
/*        List<ProjectDetails> syncDetails;
        try {
            syncDetails = fetchSyncDetails.get();
        } catch (Exception e) {
            log.error("Failed to fetch sync book details: {}", context.toString(), e);
            return;
        }

        List<ProjectDetails> doneDetails = new ArrayList<>(syncDetails.size());
        for (ProjectDetails projectDetails : syncDetails) {
            checkUpdate(projectDetails, doneDetails);
        }

        while (iterator.hasNext()) {
            ProjectDetails document = iterator.next();

            //localCacheBookService.updateRepository(document);

            switch (document.getStatus()) {
                case SYNC:
                case INVALID:
                    iterator.remove();
                    break;
                case PENDING:
                    break;
                default:
                    throw new IllegalStateException("Unrecognized document status: " + document.getStatus());
            }
        }

        // 删除旧文件
        markBookPending(syncDetails);*/
    }

    private void checkUpdate(ProjectDetails syncDetails, List<ProjectDetails> doneDetails) throws InterruptedException {
        localCacheBookService.updateRepository(syncDetails);
        switch (syncDetails.getStatus()) {
            case SYNC:
            case RETRY_PERSISTENCE:
            case FAILURE_CHECK:
                doneDetails.add(syncDetails);
                break;
            case PENDING:
        }
    }

    private void markBookPending(List<ProjectDetails> pendingDetails) {
        if (pendingDetails.isEmpty()) {
            log.info("No new book updates were found in this check");
            return;
        }

        log.info("Found new book updates: {}", pendingDetails.size());

        for (ProjectDetails details : pendingDetails) {
            List<BookChapter> chapters = readableBookService.bookChapters(details.getOriginId(), Page.unlimited());
            chapters.forEach(chapter -> bookSourceService.deleteChapter(chapter.getDownloadLink()));

            trashBookService.deleteBookByOwnerProject(details.getOriginId());
            details.setVersion("");

            // TODO 应该为Pending，为了触发Update，则暂时为INIT
            // details.setStatus(ProjectStatus.INIT);
        }

        projectDetailsService.batchUpdateStatusAndVersion(pendingDetails);
    }

    public static JobDataMap requiredDataMap(Supplier<List<ProjectDetails>> syncStatusProjectDetails) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(SYNC_STATUS_PROJECT_DETAILS, syncStatusProjectDetails);

        return dataMap;
    }

}
