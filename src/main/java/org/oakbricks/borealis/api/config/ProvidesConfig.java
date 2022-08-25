package org.oakbricks.borealis.api.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ProvidesConfig {
    String value();
}
