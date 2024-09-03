package com.playground.config;

import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.service.annotation.HttpExchange;
import com.playground.PlaygroundApplication;

public class HttpInterfaceClassFinder {
  public Set<BeanDefinition> findBeanDefinitions(Environment environment) {
    ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false, environment) {
      @Override
      protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().hasAnnotation(HttpExchange.class.getName());
      }
    };

    scanner.addIncludeFilter(new AnnotationTypeFilter(HttpExchange.class));

    return scanner.findCandidateComponents(PlaygroundApplication.class.getPackage().getName());
  }
}
