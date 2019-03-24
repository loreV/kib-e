package org.kibe.onboard.communication;

import org.kibe.common.communication.Topic;

public interface Sender {
    void send(Topic topic, String object);
}
