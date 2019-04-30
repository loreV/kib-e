package org.kibe.onboard.communication.board;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.kibe.onboard.communication.entity.DataPoint;

public class DataPointParser {

    private final Gson gsonBuilder;

    public DataPointParser(){
        gsonBuilder = new GsonBuilder().create();
    }

    DataPoint parse(final String jsonObject){
        return gsonBuilder.fromJson(jsonObject, DataPoint.class);
    }
}
