package gray.light.owner.annotation;

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

    /**
     * 是否装载Blog所有服务
     *
     * @return 是否装载Blog所有服务
     */
    boolean value() default true;

    /**
     * 是否只提供可读服务
     *
     * @return 是否只提供可读服务
     */
    boolean onlyRead() default false;

    /**
     * 是否提供搜索服务
     *
     * @return 是否提供搜索服务
     */
    boolean searchService() default true;

}
