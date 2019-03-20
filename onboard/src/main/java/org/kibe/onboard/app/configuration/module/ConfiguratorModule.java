package org.kibe.onboard.app.configuration.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.IOException;
import java.util.Properties;

public class ConfiguratorModule extends AbstractModule {
    @Override
    protected void configure() {
        final Properties properties = new Properties();
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            properties.load(contextClassLoader.getResourceAsStream("core.properties"));
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
