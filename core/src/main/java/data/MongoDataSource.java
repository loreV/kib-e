package data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

@Singleton
public class MongoDataSource implements DataSource {

    private final String databaseName;
    private final MongoClient mongoClient;

    @Inject
    public MongoDataSource(@Named("mongoURI") final String mongoURI,
                           @Named("database") final String databaseName) {
        final MongoClientURI connectionString = new MongoClientURI(mongoURI);
        this.mongoClient = new MongoClient(connectionString);
        this.databaseName = databaseName;
    }

    public MongoClient getClient() {
        return mongoClient;
    }

    public MongoDatabase getDB() {
        return mongoClient.getDatabase(databaseName);
    }

    @Override
    public String getDBName() {
        return databaseName;
    }


}
