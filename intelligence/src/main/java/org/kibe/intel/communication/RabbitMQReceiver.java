package org.kibe.intel.communication;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.apache.logging.log4j.Logger;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;

@Singleton
public class RabbitMQReceiver implements DeliverCallback {

    private final Logger LOG = getLogger(RabbitMQReceiver.class.getName());

    // TODO here all the different queue task should go.
    @Inject
    public RabbitMQReceiver(){ }

    @Override
    public void handle(final String callerTag, final Delivery message) {
        LOG.info(format("CallerTag: %s, Message: %s", callerTag, new String(message.getBody())));
    }
}
