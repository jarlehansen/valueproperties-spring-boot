package com.github.jarlehansen.valueproperties;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PropertiesLocatorConfiguration implements EnvironmentAware, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Environment environment;
    private Set<ValueProperty> properties = Collections.emptySet();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void findValueAnnotations() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnablePropertiesLocator.class);
        if (beans.size() > 0) {
            Object bean = beans.values().iterator().next();
            EnablePropertiesLocator annotation = AnnotationUtils.findAnnotation(bean.getClass(), EnablePropertiesLocator.class);
            String[] profiles = annotation.profiles();
            if (profiles.length == 0 || environment.acceptsProfiles(profiles)) {
                Reflections reflections = new Reflections(new ConfigurationBuilder()
                        .forPackages(bean.getClass().getPackage().getName())
                        .addScanners(new FieldAnnotationsScanner()));
                Set<Field> fields = reflections.getFieldsAnnotatedWith(Value.class);
                properties = fields.stream().map(f -> new ValueProperty(f.getAnnotation(Value.class).value())).collect(Collectors.toSet());
            }
        }
    }

    @Bean
    public PropertiesLocatorController propertiesLocatorController() {
        return new PropertiesLocatorController(properties);
    }
}
