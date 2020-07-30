package gray.lighjt.document.scheduler.config;

import gray.light.book.scheduler.job.AutoCheckBookUpdateJob;
import gray.light.book.scheduler.job.AutoUpdateBookJob;
import gray.light.document.service.ReadableDocumentService;
import org.quartz.*;
import org.springframework.context.annotation.Bean;

/**
 * 配置文档的定时任务
 *
 * @author XyParaCrim
 */
public class DocumentSchedulerConfiguration {

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
