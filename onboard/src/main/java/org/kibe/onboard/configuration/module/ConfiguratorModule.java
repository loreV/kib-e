package org.kibe.onboard.configuration.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ConfiguratorModule extends AbstractModule {
    @Override
    protected void configure() {
        final Properties properties = new Properties();
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            properties.load(Objects.requireNonNull(contextClassLoader.getResourceAsStream("onboard.properties")));
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
