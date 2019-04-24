package org.kibe.onboard.configuration;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.kibe.common.exception.ConfigurationException;
import org.kibe.onboard.configuration.module.BoardToIntelCommModule;
import org.kibe.onboard.configuration.module.ControllerModule;
import org.kibe.onboard.configuration.module.DataListenerModule;
import org.kibe.onboard.configuration.module.I2CommunicationModule;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;

public class ConfigurationManager {

    private final Logger LOG = getLogger(ConfigurationManager.class.getName());

    /**
     * Communication modules
     */
    private final BoardToIntelCommModule boardToIntelCommModule;
    private final I2CommunicationModule i2commModule;
    private final ControllerModule controllerModule;

    /**
     * Encapsulates data listeners
     */
    private final DataListenerModule dataListenerModule;

    @Inject
    public ConfigurationManager(
            final BoardToIntelCommModule boardToIntelCommModule,
            final I2CommunicationModule i2CommunicationModule,
            final ControllerModule controllerModule,
            final DataListenerModule dataListenerModule) {
        this.boardToIntelCommModule = boardToIntelCommModule;
        this.i2commModule = i2CommunicationModule;
        this.controllerModule = controllerModule;
        this.dataListenerModule = dataListenerModule;
    }

    public void init() throws ConfigurationException {
        try {
            boardToIntelCommModule.init();
        } catch (IOException | TimeoutException e) {
            throw new ConfigurationException(format("Error while initializing queue connection. Error: %s", e.getMessage()));
        }
        i2commModule.init(dataListenerModule.getDataListener());
        controllerModule.init();
        LOG.info("Initialization completed");
    }

}
