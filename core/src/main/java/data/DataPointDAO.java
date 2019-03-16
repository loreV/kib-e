package data;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import data.mapper.DataPointMapper;
import data.mapper.Mapper;
import model.DataPoint;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
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
        final Bson betweenRangeDocument = and(gte(DataPoint.DATETIME_FIELD, start), lt(DataPoint.DATETIME_FIELD, to));
        return Lists.newArrayList(collection.find(betweenRangeDocument))
                .stream().map(dataPointMapper::mapToObject).collect(toList());
    }

    public ObjectId persist(final DataPoint dataPoint) {
        final Document docFromDP = dataPointMapper.mapToDoc(dataPoint);
        collection.insertOne(docFromDP);
        return (ObjectId) docFromDP.get(DataPoint.ID_FIELD);
    }
}
