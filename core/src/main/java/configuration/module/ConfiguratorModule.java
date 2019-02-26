package configuration.module;

import com.google.inject.AbstractModule;
import data.DataSource;
import data.MongoDataSource;

public class ConfiguratorModule extends AbstractModule {

    @Override
    protected void configure(){
        bind(DataSource.class).to(MongoDataSource.class);
    }
}
