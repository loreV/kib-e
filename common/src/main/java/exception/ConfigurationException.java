package exception;

import static java.lang.String.format;

public class ConfigurationException extends Exception {

    private static final String message = "Error configuring the application runtime: %s";

    public ConfigurationException(final String causeMessage) {
        super(format(message, causeMessage));
    }
}
