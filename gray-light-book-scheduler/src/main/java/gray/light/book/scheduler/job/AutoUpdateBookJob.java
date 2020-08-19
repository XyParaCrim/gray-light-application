package gray.light.book.scheduler.job;

import gray.light.book.DocumentRepositoryVisitor;
import gray.light.book.scheduler.step.BatchCloneRemoteRepositoryStep;
import gray.light.book.scheduler.step.BatchUpdateDocumentRepositoriesStep;
import gray.light.book.scheduler.step.UploadDocumentStep;
import gray.light.book.scheduler.step.VisitDocumentRepositoryStep;
import gray.light.book.service.LocalCacheBookService;
import gray.light.book.service.SourceBookService;
import gray.light.book.service.WritableBookService;
import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.entity.ProjectStatus;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.List;
import java.util.function.Supplier;

/**
 * 从远程仓库克隆文档仓库，并且将其上传到文件服务器，最后更新数据库
 *
 * @author XyParaCrim
 */
@Slf4j
@RequiredArgsConstructor
public class AutoUpdateBookJob implements Job {

    @Setter
    private Supplier<List<ProjectDetails>> initStatusProjectDetails;

    private final WritableBookService writableBookService;

    private final SourceBookService bookSourceService;

    private final LocalCacheBookService localCacheBookService;

    public static JobDataMap requiredDataMap(Supplier<List<ProjectDetails>> findSyncProject) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("initStatusProjectDetails", findSyncProject);

        return dataMap;
    }

    @Override
    public void execute(JobExecutionContext context) {

        List<ProjectDetails> emptyDocument = initStatusProjectDetails.get();

        // 克隆远程仓库到本地
        BatchCloneRemoteRepositoryStep.Result cloneResult = batchCloneRemoteRepository(emptyDocument);

        // 浏览仓库文件
        VisitDocumentRepositoryStep.Result visitResult = batchWalkGitTree(cloneResult.getSuccess());

        // 上传文件到文件服务器
        UploadDocumentStep.Result uploadResult = batchUploadDocument(visitResult.getVisitors());

        // 批量更新数据到数据库
        updateRepositories(uploadResult.getVisitors(), emptyDocument);
    }

    /**
     * 根据状态为空文档的仓库地址，克隆文档仓库到本地，如果其中有文档在克隆期间发生
     * 异常，则将文档状态设置为{@link ProjectStatus#INVALID}无效状态，并将其
     * 从文档迭代器中移除
     *
     * @param emptyDocument 状态为空的文档
     */
    private BatchCloneRemoteRepositoryStep.Result batchCloneRemoteRepository(List<ProjectDetails> emptyDocument) {
        BatchCloneRemoteRepositoryStep batchCloneRemoteRepository = new BatchCloneRemoteRepositoryStep(localCacheBookService);

        return batchCloneRemoteRepository.execute(emptyDocument);
    }

    /**
     * 遍历本地文档仓库，获取目录、章节等信息，并将其储存在{@link DocumentRepositoryVisitor}里返回。
     * 如果其中有遍历期间发生异常，则将文档状态设置为{@link ProjectStatus#INVALID}无效状态，并将其
     * 从文档迭代器中移除
     *
     * @param emptyDocument 状态为空的文档
     * @return 文档仓库遍历的结果
     */
    private VisitDocumentRepositoryStep.Result batchWalkGitTree(List<ProjectDetails> emptyDocument) {
        VisitDocumentRepositoryStep batchWalkGitTree = new VisitDocumentRepositoryStep(localCacheBookService);

        return batchWalkGitTree.execute(emptyDocument);
    }

    /**
     * 将文档仓库遍历的结果上传到文件服务器
     *
     * @param visitors 文件遍历器
     * @return 文档上传的结果
     */
    private UploadDocumentStep.Result batchUploadDocument(List<DocumentRepositoryVisitor> visitors) {
        UploadDocumentStep batchUploadDocument = new UploadDocumentStep(bookSourceService);

        return batchUploadDocument.execute(visitors);
    }

    /**
     * 更新文档状态
     *
     * @param visitors      文件遍历器
     * @param emptyDocument 文档
     */
    private void updateRepositories(List<DocumentRepositoryVisitor> visitors, List<ProjectDetails> emptyDocument) {
        BatchUpdateDocumentRepositoriesStep updateRepositories = new BatchUpdateDocumentRepositoriesStep(writableBookService);

        updateRepositories.execute(visitors, emptyDocument);
    }

}
