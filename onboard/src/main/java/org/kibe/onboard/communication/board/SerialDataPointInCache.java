package org.kibe.onboard.communication.board;

import org.kibe.common.data.DataPoint;

public interface SerialDataPointInCache {
    DataPoint get();
    void set(DataPoint value);
}
