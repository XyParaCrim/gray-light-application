package gray.light.document.annotation;

import gray.light.document.config.DocumentJobConfiguration;
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
@Import(DocumentJobConfiguration.AutoUpdateDocumentJob.class)
public @interface JobUpdateDocument {
}
