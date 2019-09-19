package org.kibe.web.configuration;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.kibe.web.PresenterController;

import static org.apache.logging.log4j.LogManager.getLogger;


public class ConfigurationManager {

    private final Logger LOG = getLogger(ConfigurationManager.class.getName());

    /**
     * Encapsulates data listeners
     */
    private final PresenterController presenter;

    @Inject
    public ConfigurationManager(final PresenterController presenter) {
        this.presenter = presenter;
    }

    public void init() {
        presenter.init();
    }

}
