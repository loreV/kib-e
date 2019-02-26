package data;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import model.DataPoint;
import org.bson.Document;
import org.bson.types.ObjectId;
import proc.StatProcessor;

import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static java.util.stream.Collectors.toList;

public class DataPointDAO {

    private final String COLLECTION_NAME = "core.DataPoint";

    private static final String ID_FIELD = "_id";
    private static final String COUNT_FIELD = "count";
    private static final String UUID_FIELD = "uuid";
    private static final String DATETIME_FIELD = "datetime";
    private static final String TEMPERATURE_FIELD = "temperature";
    private static final String HUMIDIY_FIELD = "humidity";
    private static final String LIGHT_FIELD = "light";
    private static final String SOIL_MOISTURE_FIELD = "soil";

    private final MongoCollection<Document> collection;
    private final StatProcessor statProcessor;

    @Inject
    public DataPointDAO(final DataSource dataSource,
                        final StatProcessor statProcessor) {
        this.collection = dataSource.getDB().getCollection(COLLECTION_NAME);
        this.statProcessor = statProcessor;
    }

    public List<DataPoint> getAllDataPoints(){
        return Lists.newArrayList(collection.find(new Document())).stream().map(this::getDPFromDoc).collect(toList());
    }

    public DataPoint getDatapoint(final String id) {
        return getDPFromDoc(Objects.requireNonNull(collection.find(new Document(ID_FIELD, id)).first()));
    }

    public DataPoint getDataPointsByUUID(final String elemIdField){
        return getDPFromDoc(Objects.requireNonNull(collection.find(new Document(UUID_FIELD, elemIdField)).first()));
    }

    public void upsert(final DataPoint dataPoint) {
        final FindOneAndReplaceOptions upsert = new FindOneAndReplaceOptions().upsert(true);
        collection.findOneAndReplace(new Document(ID_FIELD, dataPoint.get_id()), getDocFromDP(dataPoint), upsert);
    }

    public ObjectId persist(final DataPoint dataPoint) {
        final Document docFromDP = getDocFromDP(dataPoint);
        collection.insertOne(docFromDP);
        return (ObjectId) docFromDP.get(ID_FIELD);
    }

    public void delete(final String id){
        collection.deleteOne(eq(ID_FIELD, id));
    }

    private Document getDocFromDP(final DataPoint dp) {
        final ObjectId objectId = (dp.get_id() == null) ? new ObjectId() : dp.get_id();
        return new Document(ID_FIELD, objectId)
                .append(DATETIME_FIELD, dp.getDate())
                .append(TEMPERATURE_FIELD, dp.getTemperature())
                .append(HUMIDIY_FIELD, dp.getHumidity())
                .append(LIGHT_FIELD, dp.getLight())
                .append(SOIL_MOISTURE_FIELD, dp.getSoil());
    }

    private DataPoint getDPFromDoc(final Document doc) {
        return new DataPoint.DataPointBuilder()
                .setDateTime(doc.getDate(DATETIME_FIELD))
                .setTemperature(doc.getLong(TEMPERATURE_FIELD))
                .setHumidity(doc.getLong(HUMIDIY_FIELD))
                .setLight(doc.getLong(LIGHT_FIELD))
                .setSoil(doc.getLong(SOIL_MOISTURE_FIELD))
                .build();
    }

}
