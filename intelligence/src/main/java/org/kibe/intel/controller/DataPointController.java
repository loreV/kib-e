package org.kibe.intel.controller;

import com.google.inject.Inject;
import org.kibe.intel.data.DataPointDAO;
import org.kibe.common.data.DataPoint;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.eclipse.jetty.http.HttpStatus;
import org.kibe.common.response.RESTResponse;
import org.kibe.common.response.Status;
import org.kibe.intel.helper.GsonBeanHelper;
import spark.Request;
import spark.Response;
import org.kibe.common.JsonUtil;

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

    private final GsonBeanHelper gsonBuilder;

    private static final String FROM_PARAM = "from";
    private static final String TO_PARAM = "to";
    private static final String PREFIX = "/datapoint";
    private static final String ACCEPT_APPLICATION_JSON = "application/json";
    private static final String UTC_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private final DataPointDAO dataPointDAO;
    private final SimpleDateFormat simpleDateFormatter;

    @Inject
    public DataPointController(final DataPointDAO dataPointDAO,
                               final GsonBeanHelper gsonBeanHelper) {
        this.dataPointDAO = dataPointDAO;
        gsonBuilder = gsonBeanHelper;
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
        final DataPoint dataPoint = gsonBuilder.getGsonBuilder().fromJson(request.body(), DataPoint.class);
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
