package gray.light.note.scheduler.config;

import gray.light.book.scheduler.job.AutoCheckBookUpdateJob;
import gray.light.book.scheduler.job.AutoUpdateBookJob;
import gray.light.note.service.ReadableNoteService;
import org.quartz.*;
import org.springframework.context.annotation.Bean;

/**
 * 配置笔记的定时任务
 *
 * @author XyParaCrim
 */
public class NoteSchedulerConfiguration {

    public static class AutoCheckNoteConfiguration {

        @Bean("checkNoteJobDetail")
        public JobDetail checkNoteJobDetail(ReadableNoteService service) {
            return JobBuilder.
                    newJob(AutoCheckBookUpdateJob.class).
                    usingJobData(AutoCheckBookUpdateJob.requiredDataMap(service::findSyncProjectDetails)).
                    storeDurably().
                    build();
        }

        @Bean
        public Trigger checkNoteTrigger(JobDetail checkNoteJobDetail) {
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.
                    simpleSchedule().
                    withIntervalInHours(1).
                    repeatForever();

            return TriggerBuilder.
                    newTrigger().
                    forJob(checkNoteJobDetail).
                    withSchedule(scheduleBuilder).
                    build();
        }

    }

    public static class AutoUpdateNoteConfiguration {

        @Bean("updateNoteJobDetail")
        public JobDetail updateNoteJobDetail(ReadableNoteService service) {
            return JobBuilder.
                    newJob(AutoUpdateBookJob.class).
                    usingJobData(AutoUpdateBookJob.requiredDataMap(service::findInitProjectDetails)).
                    storeDurably().
                    build();
        }

        @Bean
        public Trigger updateNoteTrigger(JobDetail updateNoteJobDetail) {
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.
                    simpleSchedule().
                    withIntervalInHours(1).
                    repeatForever();

            return TriggerBuilder.
                    newTrigger().
                    forJob(updateNoteJobDetail).
                    withSchedule(scheduleBuilder).
                    build();
        }

    }

}
