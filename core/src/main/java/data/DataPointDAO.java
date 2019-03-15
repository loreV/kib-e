package data;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import data.mapper.DataPointMapper;
import data.mapper.Mapper;
import model.DataPoint;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DataPointDAO {


    private final MongoCollection<Document> collection;
    private final Mapper<DataPoint> dataPointMapper;

    @Inject
    public DataPointDAO(final DataSource dataSource,
                        final DataPointMapper dataPointMapper) {
        this.collection = dataSource.getDB().getCollection(DataPoint.COLLECTION_NAME);
        this.dataPointMapper = dataPointMapper;
    }

    public List<DataPoint> getAllDataPoints(){
        return Lists.newArrayList(collection.find(new Document())).stream().map(dataPointMapper::mapToObject).collect(toList());
    }

    public List<DataPoint> getAllDataPointsBetweenDates(final Date start, final Date to){
        return Lists.newArrayList(collection.find(new Document())).stream().map(dataPointMapper::mapToObject).collect(toList());
    }

    public void upsert(final DataPoint dataPoint) {
        final FindOneAndReplaceOptions upsert = new FindOneAndReplaceOptions().upsert(true);
        collection.findOneAndReplace(new Document(DataPoint.ID_FIELD, dataPoint.get_id()), dataPointMapper.mapToDoc(dataPoint), upsert);
    }

    public ObjectId persist(final DataPoint dataPoint) {
        final Document docFromDP = dataPointMapper.mapToDoc(dataPoint);
        collection.insertOne(docFromDP);
        return (ObjectId) docFromDP.get(DataPoint.ID_FIELD);
    }
}
