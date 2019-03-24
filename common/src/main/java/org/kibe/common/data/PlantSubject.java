package org.kibe.common.data;

import org.bson.types.ObjectId;

import java.util.Date;

public class PlantSubject {
    public static final String COLLECTION_NAME = "core.Plants";
    public static final String ID_FIELD = "_id";
    public static final String PUID_FIELD = "puId";
    public static final String DATETIME_FIELD = "datetime";
    public static final String PLANT = "plant";

    private ObjectId _id;
    private String puid;
    private Date datetime;
    private Plant plant;

    public PlantSubject(final ObjectId _id,
                        final String puid,
                        final Date datetime,
                        final Plant plant) {
        this._id = _id;
        this.puid = puid;
        this.datetime = datetime;
        this.plant = plant;
    }

    public ObjectId get_id() {
        return _id;
    }

    public String getPuid() {
        return puid;
    }

    public Date getDatetime() {
        return datetime;
    }

    public Plant getPlant() {
        return plant;
    }
}