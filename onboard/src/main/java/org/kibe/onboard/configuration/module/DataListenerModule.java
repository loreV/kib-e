package org.kibe.onboard.configuration.module;

import com.google.inject.Inject;
import org.kibe.onboard.communication.board.DataCommReceiver;
import org.kibe.onboard.communication.board.DataListener;
import org.kibe.onboard.communication.board.DataPointParser;

import java.util.Collections;
import java.util.List;

public class DataListenerModule {

    private final DataListener dataCommReceiver;
    private final DataPointParser dataPointParser;

    @Inject
    public DataListenerModule(
            final DataCommReceiver dataCommReceiver,
            final DataPointParser dataPointParser){
        this.dataCommReceiver = dataCommReceiver;
        this.dataPointParser = dataPointParser;
    }

    public List<DataListener> getDataListener() {
        return Collections.singletonList(dataCommReceiver);
    }

    public DataPointParser getDataPointParser() {
        return dataPointParser;
    }

}
