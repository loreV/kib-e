package org.kibe.onboard.communication.board;

import org.kibe.common.data.DataPoint;

public class LastDataPointRetrieved implements SerialDataPointInCache {

    private static final String CONFIGURATION_ERROR = "The latest dataPoint is null. Is the application correctly configured?";
    private volatile DataPoint latestDataPoint;

    public LastDataPointRetrieved(){}

    public DataPoint get() {
        if(latestDataPoint == null){
            throw new IllegalStateException(CONFIGURATION_ERROR);
        }
        return latestDataPoint;
    }

    public synchronized void set(final DataPoint latestDataPoint) {
        this.latestDataPoint = latestDataPoint;
    }
}
