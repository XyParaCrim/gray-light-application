package gray.light.document.annotation;

import floor.domain.EnableDomainPermission;
import gray.light.document.config.DocumentAutoConfiguration;
import gray.light.document.config.DocumentComponentRegistrar;
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
@Import({DocumentAutoConfiguration.class, DocumentComponentRegistrar.class})
public @interface DomainDocument {

    /**
     * 配置领域服务的权限
     *
     * @return 领域服务的权限
     */
    EnableDomainPermission permission() default @EnableDomainPermission;

}
