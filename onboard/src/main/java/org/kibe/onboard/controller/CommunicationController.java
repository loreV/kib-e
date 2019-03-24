package org.kibe.onboard.controller;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.kibe.common.JsonUtil;
import org.kibe.common.communication.Topic;
import org.kibe.common.response.RESTResponse;
import org.kibe.onboard.configuration.module.CommunicationModule;
import spark.Request;
import spark.Response;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.post;

public class CommunicationController {

    private final Logger LOG = getLogger(CommunicationController.class.getName());

    private static final String PREFIX = "/comm";
    private final CommunicationModule communicationModule;

    @Inject
    public CommunicationController(final CommunicationModule communicationModule){
        this.communicationModule = communicationModule;
    }

    private RESTResponse run(final Request request, final Response response) {
        communicationModule.send(Topic.TEST, request.body());
        return new RESTResponse.Builder().code(HttpStatus.OK_200).build();
    }

    public void init() {
        post(format("%s", PREFIX), this::run, JsonUtil.json());
        LOG.info("Comm CONTROLLER Started");
    }
}
