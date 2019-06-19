package org.net.websocket.autoconfigure;

import org.net.websocket.annotation.WebSocketListener;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Arrays;
import java.util.Set;

public class ClassPathWebSocketScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathWebSocketScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void registerFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(WebSocketListener.class));

        this.addExcludeFilter((metadataReader, metadataReaderFactory) -> !metadataReader.getAnnotationMetadata().hasAnnotation("org.net.websocket.annotation.WebSocketListener"));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            this.logger.warn("No WebSocket handler was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        }
        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return super.isCandidateComponent(beanDefinition);
    }
}
