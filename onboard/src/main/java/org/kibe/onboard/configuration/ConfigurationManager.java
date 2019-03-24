package org.kibe.onboard.configuration;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.logging.log4j.Logger;
import org.kibe.onboard.configuration.module.CommunicationModule;
import org.kibe.onboard.controller.CommunicationController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.port;

public class ConfigurationManager {

    private final Logger LOG = getLogger(ConfigurationManager.class.getName());

    private static final String PORT_PROPERTY = "web-port";

    private final CommunicationModule communicationModule;
    private final CommunicationController communicationController;

    @Inject
    public ConfigurationManager(
            final CommunicationModule communicationModule,
            final CommunicationController communicationController,
            final @Named(PORT_PROPERTY) String port) {
        this.communicationModule = communicationModule;
        this.communicationController = communicationController;
        port(Integer.parseInt(port));
    }

    public void init() {
        initializeCommunication();
        initializeController();
    }

    private void initializeCommunication(){
        try {
            communicationModule.init();
        } catch (IOException | TimeoutException e) {
            LOG.error(format("Error while initializing queue connection. Error: %s", e.getMessage()));
        }
    }

    private void initializeController() {
        communicationController.init();
    }

}
