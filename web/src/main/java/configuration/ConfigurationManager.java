package configuration;

import com.google.inject.Inject;
import controller.DataPointController;
import data.DataSource;
import org.apache.logging.log4j.Logger;

import javax.inject.Named;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.port;

public class ConfigurationManager {

    private final Logger LOG = getLogger(ConfigurationManager.class.getName());

    private static final String PORT_PROPERTY = "port";
    private static final String STARTUP_IMPORT = "startupImport";

    private boolean startupImport;
    private final DataSource dataSource;


    /**
     * Controllers
     */
    private final DataPointController dataPointDAO;

    @Inject
    public ConfigurationManager(final @Named(PORT_PROPERTY) String port,
                                final @Named(STARTUP_IMPORT) String startupImport,
                                final DataPointController dataPointController,
                                final DataSource dataSource) {
        this.dataPointDAO = dataPointController;
        this.startupImport = Boolean.valueOf(startupImport);
        this.dataSource = dataSource;
        port(Integer.valueOf(port));
    }

    public void init() {
        startController();
        testConnectionWithDB();

        LOG.info(format("Configuration completed. Listening on port %s", port()));
    }

    private void testConnectionWithDB() {
        dataSource.getClient().listDatabases();
    }

    private void startController() {
        dataPointDAO.init();
    }
}