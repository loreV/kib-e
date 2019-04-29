package org.kibe.onboard.communication.board;

import org.kibe.onboard.communication.entity.DataPoint;

public interface DataListener {
    void notifyForData(DataPoint dataPoint);
}
