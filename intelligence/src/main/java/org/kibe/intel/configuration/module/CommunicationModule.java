package org.kibe.intel.configuration.module;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.kibe.common.communication.Topic;
import org.kibe.intel.communication.RabbitMQReceiver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Singleton
public class CommunicationModule {

    private static final String QUEUE_HOST_PROPERTY = "queue-host";
    private static final String QUEUE_PORT_PROPERTY = "queue-port";

    private final String host;
    private final String queuePort;

    private final RabbitMQReceiver receiver;

    private Connection connection;
    private Channel channel;


    @Inject
    public CommunicationModule(final RabbitMQReceiver receiver,
                               @Named(QUEUE_PORT_PROPERTY) final String queuePort,
                               @Named(QUEUE_HOST_PROPERTY) final String host) {
        this.receiver = receiver;
        this.queuePort = queuePort;
        this.host = host;
    }

    public void init() throws IOException, TimeoutException {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(Integer.parseInt(queuePort));
        connection = factory.newConnection();
        channel = connection.createChannel();
        final String queueName = channel.queueDeclare().getQueue();
        for (final Topic value : Topic.values()) {
            channel.exchangeDeclare(value.name(), BuiltinExchangeType.FANOUT);
            channel.queueBind(queueName, value.name(), "");
            channel.basicConsume(queueName, true, receiver, consumerTag -> { });
        }
    }

    public void shutDown() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

}
