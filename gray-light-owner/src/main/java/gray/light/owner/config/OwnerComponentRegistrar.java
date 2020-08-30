package gray.light.owner.config;

import floor.domain.DomainComponentRegistrarWithPermission;
import floor.domain.EnableDomainPermission;
import gray.light.owner.annotation.DomainOwner;
import gray.light.owner.handler.*;
import gray.light.owner.index.operation.OwnerSearchOperation;
import gray.light.owner.router.OwnerQueryRouter;
import gray.light.owner.router.OwnerSearchRouter;
import gray.light.owner.router.OwnerUpdateRouter;
import gray.light.owner.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * 根据{@link DomainOwner}的配置选项，进行有选择装载组件
 *
 * @author XyParaCrim
 */
@Slf4j
public class OwnerComponentRegistrar extends DomainComponentRegistrarWithPermission<DomainOwner> {

    @Override
    protected EnableDomainPermission enableDomainPermission(DomainOwner domainOwner) {
        return domainOwner.permission();
    }

    @Override
    protected Class<DomainOwner> annotationType() {
        return DomainOwner.class;
    }

    /**
     * 注册只读权限的组件
     *
     * @param registry bean注册器
     */
    @Override
    protected void registerQueryComponents(BeanDefinitionRegistry registry) {
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
    @Override
    protected void registerUpdateComponents(BeanDefinitionRegistry registry) {
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
    @Override
    protected void registerSearchComponents(BeanDefinitionRegistry registry) {
        registerComponent(OwnerSearchOperation.class, registry);
        registerComponent(SearchOwnerService.class, registry);
        registerComponent(OwnerSearchHandler.class, registry);
        registerComponent(OwnerSearchRouter.class, registry);
    }

}
