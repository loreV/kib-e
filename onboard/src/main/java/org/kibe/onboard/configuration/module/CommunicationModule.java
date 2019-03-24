package org.kibe.onboard.configuration.module;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.logging.log4j.Logger;
import org.kibe.common.communication.Topic;
import org.kibe.onboard.communication.Sender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;

@Singleton
public class CommunicationModule implements Sender{

    private final Logger LOG = getLogger(CommunicationModule.class.getName());

    private static final String NO_ROUTING_KEY = "";
    private static final String COMM_ERROR_MESSAGE = "Communication error: could not send message with topic to %s. Underlying error: %s";

    private static final String QUEUE_HOST_PROPERTY = "queue-host";
    private static final String QUEUE_PORT_PROPERTY = "queue-port";

    private final String host;
    private final String queuePort;

    private Sender sender;
    private Connection connection;
    private Channel channel;

    @Inject
    public CommunicationModule(@Named(QUEUE_PORT_PROPERTY) final String queuePort,
                               @Named(QUEUE_HOST_PROPERTY) final String host) {
        this.queuePort = queuePort;
        this.host = host;
    }

    public void init() throws IOException, TimeoutException {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(Integer.parseInt(queuePort));
        connection = factory.newConnection();
        channel = connection.createChannel();
        for (final Topic value : Topic.values()) {
            channel.exchangeDeclare(value.name(), BuiltinExchangeType.FANOUT);
        }
    }

    @Override
    public void send(final Topic topic, final String body) {
        try {
            channel.basicPublish(topic.name(), NO_ROUTING_KEY,null, body.getBytes());
        } catch (final IOException e) {
            LOG.error(format(COMM_ERROR_MESSAGE,
                    topic.name(),
                    e.getMessage()));
        }
    }

    public void shutDown() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public Sender getSender(){
        return sender;
    }
}
