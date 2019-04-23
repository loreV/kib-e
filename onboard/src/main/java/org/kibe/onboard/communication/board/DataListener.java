package org.kibe.onboard.communication.board;

import org.kibe.common.data.DataPoint;

public interface DataListener {
    void notifyForData(DataPoint dataPoint);
}
