package gray.light.application.book.scheduler;

import gray.light.book.scheduler.job.DispatchCheckUpdateJob;
import gray.light.owner.annotation.DomainOwner;
import gray.light.owner.service.ProjectDetailsService;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * book的定时任务
 *
 * @author XyParaCrim
 */
@DomainOwner
@Configuration
public class BookSchedulerConfiguration {

    @Bean
    public JobDetail bookDispatchJobDetail(ProjectDetailsService projectDetailsService) {
        return JobBuilder.
                newJob(DispatchCheckUpdateJob.class).
                usingJobData(DispatchCheckUpdateJob.requiredDataMap(projectDetailsService::findProjectDetailsByStatsList)).
                storeDurably().
                build();
    }

    @Bean
    public Trigger bookDispatchTrigger(JobDetail bookDispatchJobDetail) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.
                simpleSchedule().
                withIntervalInMinutes(1).
                repeatForever();

        return TriggerBuilder.
                newTrigger().
                forJob(bookDispatchJobDetail).
                withSchedule(scheduleBuilder).
                build();
    }

}
