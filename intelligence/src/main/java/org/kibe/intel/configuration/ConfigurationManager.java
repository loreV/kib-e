package org.kibe.intel.configuration;

import com.google.inject.Inject;
import org.kibe.intel.configuration.module.CommunicationModule;
import org.kibe.intel.controller.DataPointController;
import org.kibe.intel.controller.SystemController;
import org.kibe.intel.data.DataSource;
import org.apache.logging.log4j.Logger;

import javax.inject.Named;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.port;

public class ConfigurationManager {

    private final Logger LOG = getLogger(ConfigurationManager.class.getName());

    private final SystemController systemController;
    private final DataSource dataSource;

    private static final String PORT_PROPERTY = "web-port";

    /**
     * Controllers
     */
    private final DataPointController dataPointDAO;
    private final CommunicationModule communicationModule;

    @Inject
    public ConfigurationManager(final @Named(PORT_PROPERTY) String port,
                                final DataPointController dataPointController,
                                final SystemController systemController,
                                final CommunicationModule communicationModule,
                                final DataSource dataSource) {
        this.dataPointDAO = dataPointController;
        this.systemController = systemController;
        this.dataSource = dataSource;
        this.communicationModule = communicationModule;
        port(Integer.valueOf(port));
    }

    public void init() {
        try {
            startCommunication();
            startController();
            testConnectionWithDB();
            LOG.info(format("Configuration completed. Listening on port %s", port()));
        } catch (IOException | TimeoutException e) {
            LOG.error(format("Error while initializing queue connection. Error: %s", e.getMessage()));
        }
    }

    private void startCommunication() throws IOException, TimeoutException {
        communicationModule.init();
    }

    private void testConnectionWithDB() {
        dataSource.getClient().listDatabases();
    }

    private void startController() {
        dataPointDAO.init();
        systemController.init();
    }
}
