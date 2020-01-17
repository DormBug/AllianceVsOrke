package com.war.orke.annotation.processor;

import com.google.common.io.Resources;
import com.war.orke.annotation.InjectSQLQuery;
import org.apache.commons.io.Charsets;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;

public class InjectSQLQueryAnnotationProcessor implements BeanPostProcessor {

    private static final String DEFAULT_JDBC_REPO_PACKAGE = "com.war.orke.jdbcRepository";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        String name = clazz.getName();
        if (name.startsWith(DEFAULT_JDBC_REPO_PACKAGE)) {
            ReflectionUtils.doWithFields(clazz, field -> process(bean, field));
        }
        return bean;
    }

    private void process(Object bean, Field field) throws IllegalAccessException {
        if (!field.isAnnotationPresent(InjectSQLQuery.class)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        Type type = field.getType();
        if (type.getTypeName().equals(String.class.getTypeName())) {
            String path = field.getDeclaredAnnotation(InjectSQLQuery.class).value();
            field.set(bean, load(path));
        }
    }

    private String load(String path) {
        try {
            if (path == null) {
                return null;
            }
            URL url = Resources.getResource(path);
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            throw new BeanCreationException(e.getMessage(), e);
        }
    }
}
