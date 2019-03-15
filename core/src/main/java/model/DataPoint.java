package model;

import org.bson.types.ObjectId;

import java.util.Date;

public class DataPoint {

    public static final String COLLECTION_NAME = "core.DataPoint";

    public static final String PU_ID_FIELD = "puId";
    public static final String ID_FIELD = "_id";
    public static final String DATETIME_FIELD = "datetime";
    public static final String TEMPERATURE_FIELD = "temperature";
    public static final String HUMIDITY_FIELD = "humidity";
    public static final String LIGHT_FIELD = "light";
    public static final String SOIL_MOISTURE_FIELD = "soilMoisture";

    private ObjectId _id;
    private Date datetime;
    private float temperature;
    private float humidity;
    private float light;
    private float soilMoisture;
    private String puId;

    private DataPoint() {
    }

    public ObjectId get_id() {
        return _id;
    }

    public Date getDate() {
        return datetime;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getLight() {
        return light;
    }

    public float getSoilMoisture() {
        return soilMoisture;
    }

    public String getPUID() {
        return puId;
    }

    public static final class DataPointBuilder {
        private ObjectId _id;
        private Date dateTime;
        private float temperature;
        private float humidity;
        private float light;
        private float soil;
        private String puid;

        public DataPointBuilder() {
        }


        public DataPointBuilder setDateTime(final Date dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public DataPointBuilder setTemperature(final float temperature) {
            this.temperature = temperature;
            return this;
        }

        public DataPointBuilder setHumidity(final float humidity) {
            this.humidity = humidity;
            return this;
        }

        public DataPointBuilder setLight(final float light) {
            this.light = light;
            return this;
        }

        public DataPointBuilder setSoil(final float soil) {
            this.soil = soil;
            return this;
        }

        public DataPointBuilder setPUID(final String puid) {
            this.puid = puid;
            return this;
        }

        public DataPoint build() {
            DataPoint dataPoint = new DataPoint();
            dataPoint._id = this._id;
            dataPoint.datetime = this.dateTime;
            dataPoint.temperature = this.temperature;
            dataPoint.humidity = this.humidity;
            dataPoint.light = this.light;
            dataPoint.soilMoisture = this.soil;
            dataPoint.puId = this.puid;
            return dataPoint;
        }

    }
}
