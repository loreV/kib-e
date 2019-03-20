package org.kibe.onboard.app.configuration;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

import javax.inject.Named;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.port;

public class ConfigurationManager {

    private final Logger LOG = getLogger(ConfigurationManager.class.getName());

    private static final String PORT_PROPERTY = "port";


    @Inject
    public ConfigurationManager(final @Named(PORT_PROPERTY) String port) {
        port(Integer.valueOf(port));
    }

    public void init() {
        startCommunication();
        LOG.info(format("Configuration completed. Listening on port %s", port()));
    }


    private void startCommunication() {
        // TODO
    }
}
