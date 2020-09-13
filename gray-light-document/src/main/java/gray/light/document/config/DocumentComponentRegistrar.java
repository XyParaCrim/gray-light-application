package gray.light.document.config;

import floor.domain.DomainComponentRegistrarWithPermission;
import floor.domain.EnableDomainPermission;
import gray.light.document.annotation.DomainDocument;
import gray.light.document.handler.WorksDocumentQueryHandler;
import gray.light.document.handler.WorksDocumentUpdateHandler;
import gray.light.document.router.DocumentQueryRouter;
import gray.light.document.router.DocumentUpdateRouter;
import gray.light.document.service.ReadableDocumentService;
import gray.light.document.service.WritableDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * 根据{@link DomainDocument}的配置选项，进行有选择装载组件
 *
 * @author XyParaCrim
 */
@Slf4j
public class DocumentComponentRegistrar extends DomainComponentRegistrarWithPermission<DomainDocument> {

    @Override
    protected EnableDomainPermission enableDomainPermission(DomainDocument domainDocument) {
        return domainDocument.permission();
    }

    @Override
    protected Class<DomainDocument> annotationType() {
        return DomainDocument.class;
    }

    @Override
    protected void registerQueryComponents(BeanDefinitionRegistry beanDefinitionRegistry) {
        registerComponent(ReadableDocumentService.class, beanDefinitionRegistry);
        registerComponent(WorksDocumentQueryHandler.class, beanDefinitionRegistry);
        registerComponent(DocumentQueryRouter.class, beanDefinitionRegistry);
    }

    @Override
    protected void registerUpdateComponents(BeanDefinitionRegistry beanDefinitionRegistry) {
        registerComponent(WritableDocumentService.class, beanDefinitionRegistry);
        registerComponent(WorksDocumentUpdateHandler.class, beanDefinitionRegistry);
        registerComponent(DocumentUpdateRouter.class, beanDefinitionRegistry);
    }

    @Override
    protected void registerSearchComponents(BeanDefinitionRegistry beanDefinitionRegistry) {
        log.warn("Search is temporarily not supported");
    }
}
