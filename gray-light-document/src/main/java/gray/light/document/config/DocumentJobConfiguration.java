package gray.light.document.config;

import gray.light.book.job.AutoCheckBookUpdateJob;
import gray.light.book.job.AutoUpdateBookJob;
import gray.light.book.service.WritableBookService;
import gray.light.book.service.SourceBookService;
import gray.light.book.service.LocalCacheBookService;
import gray.light.document.service.ReadableDocumentService;
import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.entity.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import perishing.constraint.jdbc.Page;

import java.util.List;
import java.util.function.Supplier;

/**
 * 配置文档的定时任务
 *
 * @author XyParaCrim
 */
public class DocumentJobConfiguration {

    public static class AutoCheckDocumentUpdateJob {

        @Bean("checkDocumentUpdateJobDetail")
        public JobDetail checkDocumentUpdateJobDetail(ReadableDocumentService readableDocumentService) {
            return JobBuilder.
                    newJob(AutoCheckBookUpdateJob.class).
                    usingJobData(AutoCheckBookUpdateJob.requiredDataMap(readableDocumentService::findSyncProject)).
                    storeDurably().
                    build();
        }

        @Bean
        public Trigger checkDocumentUpdateTrigger(JobDetail checkDocumentUpdateJobDetail) {
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.
                    simpleSchedule().
                    withIntervalInHours(1).
                    repeatForever();

            return TriggerBuilder.
                    newTrigger().
                    forJob(checkDocumentUpdateJobDetail).
                    withSchedule(scheduleBuilder).
                    build();
        }

    }


    public static class AutoUpdateDocumentJob {

        @Bean("updateDocumentJobDetail")
        public JobDetail updateDocumentJobDetail(ReadableDocumentService readableDocumentService) {
            return JobBuilder.
                    newJob(AutoUpdateBookJob.class).
                    usingJobData(AutoUpdateBookJob.requiredDataMap(readableDocumentService::findInitProject)).
                    storeDurably().
                    build();
        }

        @Bean
        public Trigger updateDocumentTrigger(JobDetail updateDocumentJobDetail) {
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.
                    simpleSchedule().
                    withIntervalInHours(1).
                    repeatForever();

            return TriggerBuilder.
                    newTrigger().
                    forJob(updateDocumentJobDetail).
                    withSchedule(scheduleBuilder).
                    build();
        }

    }

}
