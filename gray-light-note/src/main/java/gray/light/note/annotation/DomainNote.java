package gray.light.note.annotation;

import gray.light.note.config.NoteAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自动配置Note领域
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(NoteAutoConfiguration.class)
public @interface DomainNote {
}
