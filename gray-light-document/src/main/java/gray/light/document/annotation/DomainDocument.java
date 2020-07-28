package gray.light.document.annotation;

import gray.light.document.config.DocumentAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 关于document领域的自动配置
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DocumentAutoConfiguration.class)
public @interface DomainDocument {
}
