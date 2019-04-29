package org.kibe.onboard.configuration.module;

import com.google.inject.Inject;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import org.apache.logging.log4j.Logger;
import org.kibe.common.exception.ConfigurationException;
import org.kibe.onboard.communication.board.DataCommRunner;
import org.kibe.onboard.communication.board.DataListener;
import org.kibe.onboard.communication.board.DataPointParser;

import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.apache.logging.log4j.LogManager.getLogger;

public class I2CommunicationModule {

    private final Logger LOG = getLogger(I2CommunicationModule.class.getName());


    private static final String COMMUNICATION_BUS = "I2CommBus";
    private static final String COMMUNICATION_PORT = "I2CommPort";

    private final Executor executor;

    private String communicationBusNumber;
    private String communicationPort;

    @Inject
    public I2CommunicationModule(@Named(COMMUNICATION_BUS) String communicationBusNumber,
                                 @Named(COMMUNICATION_PORT) String communicationPort) throws ConfigurationException {
        this.communicationBusNumber = communicationBusNumber;
        this.communicationPort = communicationPort;
        executor = Executors.newSingleThreadExecutor();
        scanForBus();
    }

    private void scanForBus() throws ConfigurationException {
        try {
            PlatformManager.setPlatform(Platform.RASPBERRYPI);
        } catch (final PlatformAlreadyAssignedException e) {
            throw new ConfigurationException(e.getMessage());
        }

        // TODO: check whether this is run as root.
        for (int number = I2CBus.BUS_0; number <= I2CBus.BUS_17; ++number) {
            try {
                @SuppressWarnings("unused")
                I2CBus bus = I2CFactory.getInstance(number);
                LOG.info("Supported I2C bus " + number + " found");
            } catch (IOException exception) {
                LOG.info("I/O error on I2C bus " + number + " occurred");
            } catch (I2CFactory.UnsupportedBusNumberException exception) {
                LOG.info("Unsupported I2C bus " + number + " required");
            }
        }
    }

    public void init(final DataPointParser dataPointParser,
                     final List<DataListener> dataListeners) throws ConfigurationException {
        I2CDevice devicePort;
        try {
            final I2CBus communicationBus = I2CFactory.getInstance(Integer.valueOf(communicationBusNumber));
            devicePort = communicationBus.getDevice(Integer.valueOf(communicationPort));
        } catch (I2CFactory.UnsupportedBusNumberException | IOException e) {
            throw new ConfigurationException("Unsupported bus number");
        }
        final DataCommRunner dataCommandRunner = new DataCommRunner(devicePort, dataPointParser);
        dataListeners.forEach(dataCommandRunner::registerListener);
        executor.execute(dataCommandRunner);
    }



}
