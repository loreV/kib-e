package org.kibe.onboard.communication.board;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import org.kibe.common.data.DataPoint;

import java.io.IOException;

public class RPISerialDataPointIn {

    final static Serial serial = SerialFactory.createInstance();
    private final SerialDataPointInCache lastDataPointRetrieved;
    private final Gson gson;

    @Inject
    public RPISerialDataPointIn(final SerialDataPointInCache lastDataPointRetrieved){
        this.lastDataPointRetrieved = lastDataPointRetrieved;
        gson = new Gson();
    }

    public void init(){
        // create and register the serial data listener
        serial.addListener((SerialDataEventListener) event -> {
            // NOTE! - It is extremely important to get the data received from the
            // serial port.  If it does not get get from the receive buffer, the
            // buffer will continue to grow and consume memory.
            try {
                lastDataPointRetrieved.set(gson.fromJson(event.getAsciiString(), DataPoint.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
