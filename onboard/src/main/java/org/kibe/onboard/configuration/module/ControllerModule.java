package org.kibe.onboard.configuration.module;

import com.google.inject.name.Named;
import org.kibe.onboard.controller.CommunicationController;

import static spark.Spark.port;

public class ControllerModule {

    private static final String PORT_PROPERTY = "web-port";

    private final CommunicationController testCommController;

    public ControllerModule(final @Named(PORT_PROPERTY) String port,
                            final CommunicationController testCommunicationController){
        port(Integer.parseInt(port));
        this.testCommController = testCommunicationController;
    }

    public void init(){
        this.testCommController.init();
    }



}
