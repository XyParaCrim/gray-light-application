package gray.lighjt.document.scheduler.annotation;

import gray.lighjt.document.scheduler.config.DocumentSchedulerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 允许启动自动更新任务文档的定时任务
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DocumentSchedulerConfiguration.AutoUpdateDocumentJob.class)
public @interface JobUpdateDocument {
}
