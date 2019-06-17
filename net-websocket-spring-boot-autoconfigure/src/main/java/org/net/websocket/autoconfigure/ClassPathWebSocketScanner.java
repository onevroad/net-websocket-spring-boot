package org.net.websocket.autoconfigure;

import org.net.websocket.annotation.MessageListener;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class ClassPathWebSocketScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathWebSocketScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void registerFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(MessageListener.class));

//        this.addExcludeFilter((metadataReader, metadataReaderFactory) -> !metadataReader.getAnnotationMetadata().hasAnnotation("org.net.websocket.annotation.MessageListener"));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            this.logger.warn("No WebSocket handler was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            this.processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        Iterator it = beanDefinitions.iterator();

        while (it.hasNext()) {
            BeanDefinitionHolder holder = (BeanDefinitionHolder) it.next();
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Creating WebSocketFactoryBean with name '" + holder.getBeanName() + "' and '" + definition.getBeanClassName() + "' handlerInterface");
            }
            this.logger.info("Creating WebSocketFactoryBean with name '" + holder.getBeanName() + "' and '" + definition.getBeanClassName() + "' handlerInterface");

        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return super.isCandidateComponent(beanDefinition);
    }
}
