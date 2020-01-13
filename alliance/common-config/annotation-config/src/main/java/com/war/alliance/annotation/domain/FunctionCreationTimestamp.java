package com.war.alliance.annotation.domain;

import com.war.alliance.annotation.realization.FunctionCreationValueGeneration;
import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ValueGenerationType(generatedBy = FunctionCreationValueGeneration.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionCreationTimestamp {
}

