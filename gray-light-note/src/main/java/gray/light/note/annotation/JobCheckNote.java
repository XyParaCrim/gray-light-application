package gray.light.note.annotation;

import gray.light.note.config.NoteJobConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 允许启动自动检测笔记更新的定时任务
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(NoteJobConfiguration.AutoCheckNoteConfiguration.class)
public @interface JobCheckNote {
}
