package org.kibe.onboard.communication.board;

import com.pi4j.io.i2c.I2CDevice;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;

public class DataCommRunner implements Runnable, DataEntrySubject {

    private final Logger LOG = getLogger(DataCommRunner.class.getName());

    private static final int BYTE_SIZE_BLOCK = 32;
    private static final int OFFSET_SIZE = 0;

    private final I2CDevice i2cDevice;
    private final List<DataListener> dataListeners;

    public DataCommRunner(final I2CDevice i2CDevice) {
        this.i2cDevice = i2CDevice;
        dataListeners = new ArrayList<>();
    }

    @Override
    public void run() {
        while(true){
            byte[] readBuffer = new byte[32];
            if(Thread.currentThread().isInterrupted()){
                break;
            }
            try {
                i2cDevice.write(new byte[0]);
                int dataRead = i2cDevice.read(readBuffer, OFFSET_SIZE, BYTE_SIZE_BLOCK);
                LOG.info(format("Data read (%d bytes): %s", dataRead, new String(readBuffer)));
            } catch (IOException e) {
                LOG.error("Error when reading data from sensor board: %s");
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LOG.error("Data comm runner thread was interrupted");
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

    @Override
    public void notifyListeners() {
//        dataListeners.forEach();
    }
}
