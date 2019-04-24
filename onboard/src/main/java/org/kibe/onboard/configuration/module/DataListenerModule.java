package org.kibe.onboard.configuration.module;

import com.google.inject.Inject;
import org.kibe.onboard.communication.board.DataCommReceiver;
import org.kibe.onboard.communication.board.DataListener;

import java.util.Collections;
import java.util.List;

public class DataListenerModule {

    private final DataListener dataCommReceiver;

    @Inject
    public DataListenerModule(final DataCommReceiver dataCommReceiver){
        this.dataCommReceiver = dataCommReceiver;
    }

    public List<DataListener> getDataListener() {
        return Collections.singletonList(dataCommReceiver);
    }
}
