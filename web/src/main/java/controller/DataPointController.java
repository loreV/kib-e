package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import static java.lang.String.format;
import static org.apache.logging.log4j.LogManager.getLogger;
import static spark.Spark.get;
import static spark.Spark.post;


public class DataPointController {

    private final Logger LOG = getLogger(DataPointController.class.getName());

    private final Gson gsonBuilder;
    private static final String FROM_PARAM = "from";
    private static final String TO_PARAM = "to";
    private static final String PREFIX = "/datapoint";
    private static final String ACCEPT_APPLICATION_JSON = "application/json";
    private static final String UTC_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private final DataPointDAO dataPointDAO;
    private final SimpleDateFormat simpleDateFormatter;

    @Inject
    public DataPointController(final DataPointDAO dataPointDAO) {
        this.dataPointDAO = dataPointDAO;
        gsonBuilder = new GsonBuilder().create();
        simpleDateFormatter = new SimpleDateFormat(UTC_DATE_PATTERN);
    }


    private RESTResponse getByElemByDateRange(final Request request, final Response response) {
        try {
            final Date from = simpleDateFormatter.parse(request.params(FROM_PARAM));
            final Date to =  simpleDateFormatter.parse(request.params(TO_PARAM));
            return new RESTResponse.Builder().code(HttpStatus.OK_200)
                    .messages(Collections.emptyList()).status(Status.OK).result(
                            dataPointDAO.getAllDataPointsBetweenDates(from, to)).build();
        } catch (final ParseException e) {
            return new RESTResponse.Builder().code(HttpStatus.BAD_REQUEST_400)
                    .messages(Collections.singletonList("Error parsing date field"))
                    .status(Status.ERROR).build();
        }
    }

    private RESTResponse getAll(final Request request, final Response response) {
        return new RESTResponse.Builder().code(HttpStatus.OK_200)
                .messages(Collections.emptyList()).status(Status.OK)
                .result(dataPointDAO.getAllDataPoints()).build();
    }

    private RESTResponse persist(final Request request, final Response response) {
        final DataPoint dataPoint = gsonBuilder.fromJson(request.body(), DataPoint.class);
        final ObjectId persist = dataPointDAO.persist(dataPoint);
        return new RESTResponse.Builder().code(HttpStatus.OK_200).messages(Collections.singletonList(persist.toString())).build();
    }


    public void init() {
        // get all
        get(format("%s/", PREFIX), this::getAll, JsonUtil.json());
        // get elem by date
        get(format("%s/from/:%s/to/:%s", PREFIX, FROM_PARAM, TO_PARAM), this::getByElemByDateRange, JsonUtil.json());
        // persist
        post(format("%s/", PREFIX), ACCEPT_APPLICATION_JSON, this::persist, JsonUtil.json());

        LOG.info("DataPoint CONTROLLER Started");
    }


}
