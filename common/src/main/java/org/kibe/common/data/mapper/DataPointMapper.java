package org.kibe.common.data.mapper;

import org.kibe.common.data.DataPoint;
import org.bson.Document;
import org.bson.types.ObjectId;


public class DataPointMapper implements Mapper<DataPoint> {

    @Override
    public DataPoint mapToObject(Document doc) {
        return new DataPoint.DataPointBuilder()
                .setDateTime(doc.getDate(DataPoint.DATETIME_FIELD))
                .setTemperature(doc.getDouble(DataPoint.TEMPERATURE_FIELD))
                .setHumidity(doc.getDouble(DataPoint.HUMIDITY_FIELD))
                .setLight(doc.getDouble(DataPoint.LIGHT_FIELD))
                .setSoil(doc.getDouble(DataPoint.SOIL_MOISTURE_FIELD))
                .setPUID(doc.getString(DataPoint.PU_ID_FIELD))
                .build();
    }

    @Override
    public Document mapToDoc(DataPoint dp) {
        final ObjectId objectId = (dp.get_id() == null) ? new ObjectId() : dp.get_id();
        return new Document(DataPoint.ID_FIELD, objectId)
                .append(DataPoint.PU_ID_FIELD, dp.getPUID())
                .append(DataPoint.DATETIME_FIELD, dp.getDate())
                .append(DataPoint.TEMPERATURE_FIELD, dp.getTemperature())
                .append(DataPoint.HUMIDITY_FIELD, dp.getHumidity())
                .append(DataPoint.LIGHT_FIELD, dp.getLight())
                .append(DataPoint.SOIL_MOISTURE_FIELD, dp.getSoilMoisture());
    }
}
