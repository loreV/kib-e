package org.kibe.onboard.configuration;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.logging.log4j.Logger;
import org.kibe.common.exception.ConfigurationException;
import org.kibe.onboard.configuration.module.BoardToIntelCommModule;
import org.kibe.onboard.configuration.module.DataListenerModule;
import org.kibe.onboard.configuration.module.I2CommunicationModule;
import org.kibe.onboard.controller.CommunicationController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.port;

public class ConfigurationManager {

    private final Logger LOG = getLogger(ConfigurationManager.class.getName());

    private static final String PORT_PROPERTY = "web-port";

    private final BoardToIntelCommModule boardToIntelCommModule;
    private final CommunicationController restController;
    private final I2CommunicationModule i2commModule;

    /**
     * Encapsulates data listeners
     */
    private final DataListenerModule dataListenerModule;

    @Inject
    public ConfigurationManager(
            final BoardToIntelCommModule boardToIntelCommModule,
            final CommunicationController restController,
            final I2CommunicationModule i2CommunicationModule,
            final DataListenerModule dataListenerModule,
            // TODO: move this away from this generic config class
            final @Named(PORT_PROPERTY) String port) {
        this.boardToIntelCommModule = boardToIntelCommModule;
        this.restController = restController;
        this.i2commModule = i2CommunicationModule;
        this.dataListenerModule = dataListenerModule;
        port(Integer.parseInt(port));
    }

    public void init() throws ConfigurationException {
        try {
            boardToIntelCommModule.init();
        } catch (IOException | TimeoutException e) {
            throw new ConfigurationException(format("Error while initializing queue connection. Error: %s", e.getMessage()));
        }
        i2commModule.init(dataListenerModule.getDataListener());
        restController.init();
    }

}
