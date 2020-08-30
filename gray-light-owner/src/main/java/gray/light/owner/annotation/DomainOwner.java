package gray.light.owner.annotation;

import floor.domain.EnableDomainPermission;
import gray.light.owner.config.OwnerAutoConfiguration;
import gray.light.owner.config.OwnerComponentRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自动配置owner领域
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({OwnerComponentRegistrar.class, OwnerAutoConfiguration.class})
public @interface DomainOwner {

    EnableDomainPermission permission() default @EnableDomainPermission;

}
