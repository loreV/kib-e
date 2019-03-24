package org.kibe.intel.configuration.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.kibe.intel.data.DataSource;
import org.kibe.intel.data.MongoDataSource;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ConfiguratorModule extends AbstractModule {
    @Override
    protected void configure() {
        final Properties properties = new Properties();
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            properties.load(Objects.requireNonNull(contextClassLoader.getResourceAsStream("core.properties")));
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        bind(DataSource.class).to(MongoDataSource.class);
    }
}