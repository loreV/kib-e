package org.kibe.onboard.communication.board;

import com.pi4j.io.i2c.I2CDevice;
import org.apache.logging.log4j.Logger;
import org.kibe.onboard.communication.entity.DataPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;

public class DataCommRunner implements Runnable, DataEntrySubject {

    private final Logger LOG = getLogger(DataCommRunner.class.getName());
    private static final String IO_ERROR_WHEN_READING_MESSAGE = "Error when reading data from sensor board: %s";

    /**
     *  Read Const
     */
    private static final int BYTE_SIZE_BLOCK = 32;
    private static final int OFFSET_SIZE = 0;
    private static final int READ_RATE_MS = 5000;

    private final I2CDevice i2cDevice;
    private final List<DataListener> dataListeners;
    private final DataPointParser dpParser;

    public DataCommRunner(final I2CDevice i2CDevice, final DataPointParser dataPointParser) {
        this.i2cDevice = i2CDevice;
        this.dpParser = dataPointParser;
        dataListeners = new ArrayList<>();
    }

    @Override
    public void run() {
        while(true){
            if(Thread.currentThread().isInterrupted()){ break; }
            try {
                i2cDevice.write(new byte[0]);
                final DataPoint dataPoint = readDataPoint();
                notifyListeners(dataPoint);
            } catch (final IOException e) {
                LOG.error(format(IO_ERROR_WHEN_READING_MESSAGE, e.getMessage()));
            }
            if(!hasSleptUninterruptedly()) {
                break;
            }
        }
    }

    @Override
    public void registerListener(DataListener dataListener) {
        dataListeners.add(dataListener);
    }

    @Override
    public void unregister(DataListener dataListener) {
        dataListeners.remove(dataListener);
    }

    private DataPoint readDataPoint() throws IOException {
        byte[] readBuffer = new byte[32];
        i2cDevice.read(readBuffer, OFFSET_SIZE, BYTE_SIZE_BLOCK);
        final String dpJson = new String(readBuffer);
        return dpParser.parse(dpJson);
    }

    private void notifyListeners(DataPoint dataPoint) {
        dataListeners.forEach(dataListener -> dataListener.notifyForData(dataPoint));
    }

    private boolean hasSleptUninterruptedly() {
        try {
            Thread.sleep(READ_RATE_MS);
        } catch (final InterruptedException e) {
            LOG.error(this.getClass().getName() + "runner thread was interrupted");
            return false;
        }
        return true;
    }
}
