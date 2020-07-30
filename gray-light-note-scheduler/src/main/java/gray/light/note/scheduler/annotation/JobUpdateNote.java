package gray.light.note.scheduler.annotation;

import gray.light.note.scheduler.config.NoteSchedulerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 允许启动自动更新笔记的定时任务
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(NoteSchedulerConfiguration.AutoUpdateNoteConfiguration.class)
public @interface JobUpdateNote {
}
