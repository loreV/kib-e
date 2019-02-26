package model;

import org.bson.types.ObjectId;

import java.util.Date;

public class DataPoint {

    private ObjectId _id;
    private Date datetime;
    private float temperature;
    private float humidity;
    private float light;
    private float soil;

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

    public float getSoil() {
        return soil;
    }

    public static final class DataPointBuilder {
        private ObjectId _id;
        private Date dateTime;
        private float temperature;
        private float humidity;
        private float light;
        private float soil;

        public static DataPointBuilder aHike() {
            return new DataPointBuilder();
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

        public DataPoint build() {
            DataPoint dataPoint = new DataPoint();
            dataPoint._id = this._id;
            dataPoint.datetime = this.dateTime;
            dataPoint.temperature = this.temperature;
            dataPoint.humidity = this.humidity;
            dataPoint.light = this.light;
            dataPoint.soil = this.soil;
            return dataPoint;
        }

    }
}
