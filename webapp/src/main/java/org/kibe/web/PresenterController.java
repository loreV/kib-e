package org.kibe.web;

import org.apache.logging.log4j.Logger;
import org.kibe.common.JsonUtil;
import spark.Request;
import spark.Response;

import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.*;

public class PresenterController {
    private static final String PREFIX = "kibe";
    private final Logger LOG = getLogger(PresenterController.class.getName());

    private String serveContents(Request request, Response response) {
        return "";
    }

    public void init() {
        // get all
        staticFiles.location("/webapp"); // Static files
        get("", this::serveContents, JsonUtil.json());
        // get elem by date
    }
}
