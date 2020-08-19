package gray.light.owner.config;

import gray.light.owner.annotation.DomainOwner;
import gray.light.owner.handler.*;
import gray.light.owner.router.OwnerQueryRouter;
import gray.light.owner.router.OwnerUpdateRouter;
import gray.light.owner.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 根据{@link DomainOwner}的配置选项，进行有选择装载组件
 *
 * @author XyParaCrim
 */
@Slf4j
public class OwnerComponentRegistrar implements ImportBeanDefinitionRegistrar {

    private final AtomicBoolean configured = new AtomicBoolean();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        if (configured.get() || !configured.compareAndSet(false, true)) {
            log.warn("Error DomainOwner repeat registration: {}", metadata.getClassName());
        }

        AnnotationAttributes attrs = AnnotatedElementUtils.getMergedAnnotationAttributes(
                ClassUtils.resolveClassName(metadata.getClassName(), null),
                DomainOwner.class);
        DomainOwner domainOwner = domainOwnerProperties(attrs, metadata.getClassName());

        // 如果DomainOwner#value为true，说明支持所属者领域
        if (domainOwner.value()) {
            registerQueryComponents(registry);

            // 根据配置，选择加载只读、可写和搜索组件

            if (!domainOwner.onlyRead()) {
                registerUpdateComponents(registry);
            }

            if (domainOwner.searchService()) {
                registerSearchComponents(registry);
            }

        }
    }

    /**
     * 注册只读权限的组件
     *
     * @param registry bean注册器
     */
    private void registerQueryComponents(BeanDefinitionRegistry registry) {
        registerComponent(ReadableOwnerService.class, registry);
        registerComponent(ReadableOwnerProjectService.class, registry);
        registerComponent(ReadableProjectDetailsService.class, registry);

        registerComponent(OwnerQueryHandler.class, registry);
        registerComponent(OwnerProjectQueryHandler.class, registry);
        registerComponent(WorksProjectQueryHandler.class, registry);

        registerComponent(OwnerQueryRouter.class, registry);
    }

    /**
     * 注册可写权限的组件
     *
     * @param registry bean注册器
     */
    private void registerUpdateComponents(BeanDefinitionRegistry registry) {
        registerComponent(WritableOwnerService.class, registry);
        registerComponent(WritableOwnerProjectService.class, registry);
        registerComponent(WritableProjectDetailsService.class, registry);

        registerComponent(OwnerUpdateHandler.class, registry);
        registerComponent(OwnerProjectUpdateHandler.class, registry);
        registerComponent(WorksProjectUpdateHandler.class, registry);

        registerComponent(OwnerUpdateRouter.class, registry);
    }

    /**
     * 注册搜索组件
     *
     * @param registry bean注册器
     */
    private void registerSearchComponents(BeanDefinitionRegistry registry) {

    }

    private void registerComponent(Class<?> type, BeanDefinitionRegistry registry) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(type);
        registry.registerBeanDefinition(type.getName(), rootBeanDefinition);
    }


    private DomainOwner domainOwnerProperties(AnnotationAttributes attrs, String className) {
        return AnnotationUtils.synthesizeAnnotation(attrs,
                DomainOwner.class, ClassUtils.resolveClassName(className, null));
    }
}
