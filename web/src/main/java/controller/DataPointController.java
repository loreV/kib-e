package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import data.DataPointDAO;
import model.DataPoint;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.eclipse.jetty.http.HttpStatus;
import response.returned.RESTResponse;
import response.returned.Status;
import spark.Request;
import spark.Response;
import util.JsonUtil;

import java.util.Collections;
import java.util.List;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.*;


public class DataPointController {

    private final Logger LOG = getLogger(DataPointController.class.getName());

    private final Gson gsonBuilder;
    private static final String IdParam = ":id";
    private static final String PREFIX = "/datapoint";
    private static final String ACCEPT_APPLICATION_JSON = "application/json";

    private final DataPointDAO dataPointDAO;

    @Inject
    public DataPointController(final DataPointDAO dataPointDAO) {
        this.dataPointDAO = dataPointDAO;
        gsonBuilder = new GsonBuilder().create();
    }


    private RESTResponse getById(final Request request, final Response response) {
        return new RESTResponse.Builder().code(HttpStatus.OK_200).messages(Collections.emptyList()).status(Status.OK).result(dataPointDAO.getDatapoint(request.params(IdParam))).build();
    }

    private RESTResponse getByElemId(final Request request, final Response response) {
        return new RESTResponse.Builder().code(HttpStatus.OK_200).messages(Collections.emptyList()).status(Status.OK).result(dataPointDAO.getDatapoint(request.params(IdParam))).build();
    }

    private RESTResponse getAll(final Request request, final Response response) {
        return new RESTResponse.Builder().code(HttpStatus.OK_200).messages(Collections.emptyList()).status(Status.OK).result(dataPointDAO.getAllDataPoints()).build();
    }

    private RESTResponse deleteById(final Request request, final Response response) {
        dataPointDAO.delete(request.params(IdParam));
        return new RESTResponse.Builder().code(HttpStatus.OK_200).messages(Collections.emptyList()).status(Status.OK).result(new Object()).build();
    }

    private RESTResponse updateDataObject(final Request request, final Response response) {
        try {
            final List<DataPoint> dataPoints = gsonBuilder.fromJson(request.body(), List.class);
            dataPoints.forEach(dataPointDAO::upsert);
            response.status(HttpStatus.OK_200);
            return new RESTResponse.Builder().code(HttpStatus.OK_200).messages(Collections.emptyList()).status(Status.OK).result(new Object()).build();
        } catch (final JsonSyntaxException e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            return new RESTResponse.Builder().code(HttpStatus.OK_200)
                    .messages(Collections.singletonList(e.getMessage()))
                    .status(Status.ERROR).result(new Object()).build();
        }
    }

    private RESTResponse persist(final Request request, final Response response) {
        DataPoint dataPoint = gsonBuilder.fromJson(request.body(), DataPoint.class);
        ObjectId persist = dataPointDAO.persist(dataPoint);
        return new RESTResponse.Builder().code(HttpStatus.OK_200).messages(Collections.singletonList(persist.toString())).build();
    }


    public void init() {
        get(format("%s/", PREFIX), this::getAll, JsonUtil.json());
        // Get by ID
        get(format("%s/:%s", PREFIX, IdParam), this::getById, JsonUtil.json());
        // Delete
        delete(format("%s/:%s", PREFIX, IdParam), this::deleteById);
        // Update
        put(format("%s/", PREFIX), ACCEPT_APPLICATION_JSON, this::updateDataObject, JsonUtil.json());
        // Create
        post(format("%s/", PREFIX), ACCEPT_APPLICATION_JSON, this::persist, JsonUtil.json());

        LOG.info("DataPoint CONTROLLER Started");
    }


}
