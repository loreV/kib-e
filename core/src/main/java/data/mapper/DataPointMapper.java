package data.mapper;

import model.DataPoint;
import org.bson.Document;
import org.bson.types.ObjectId;

import static model.DataPoint.*;


public class DataPointMapper implements Mapper<DataPoint> {

    @Override
    public DataPoint mapToObject(Document doc) {
        return new DataPoint.DataPointBuilder()
                .setDateTime(doc.getDate(DATETIME_FIELD))
                .setTemperature(doc.getLong(TEMPERATURE_FIELD))
                .setHumidity(doc.getLong(HUMIDITY_FIELD))
                .setLight(doc.getLong(LIGHT_FIELD))
                .setSoil(doc.getLong(SOIL_MOISTURE_FIELD))
                .setPUID(doc.getString(PU_ID_FIELD))
                .build();
    }

    @Override
    public Document mapToDoc(DataPoint dp) {
        final ObjectId objectId = (dp.get_id() == null) ? new ObjectId() : dp.get_id();
        return new Document(ID_FIELD, objectId)
                .append(PU_ID_FIELD, dp.getPUID())
                .append(DATETIME_FIELD, dp.getDate())
                .append(TEMPERATURE_FIELD, dp.getTemperature())
                .append(HUMIDITY_FIELD, dp.getHumidity())
                .append(LIGHT_FIELD, dp.getLight())
                .append(SOIL_MOISTURE_FIELD, dp.getSoilMoisture());
    }
}
