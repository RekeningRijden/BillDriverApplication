package main.core.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import main.core.exception.CommunicationException;
import main.domain.Car;
import main.domain.Driver;
import main.domain.Invoice;
import main.domain.Ownership;
import main.domain.Position;
import main.domain.TrackingPeriod;
import main.pagination.InvoicePagination;

/**
 * @author Sam
 */
public final class Communicator {

    private Communicator() {
        //Utility class constructor cannot be called.
    }

    /**
     * The test url of the Movementsystem api.
     */
    private static final String BASE_URL_TEST = "http://localhost:8080/AdministrationSystem/api/users/";

    /**
     * The production url of the Movementsystem api.
     */
    private static final String BASE_URL_PRODUCTION = "http://administration.s63a.marijn.ws/api/users/";

    /**
     * Gets all known cartrackers from the Movementsystem Api
     *
     * @param id
     * @return All known cartrackers
     * @throws IOException When trying to execute the http request or converts
     *                     the response to a String
     */
    public static InvoicePagination getAllInvoices(Long id, int pageIndex, int pageSize) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION + id + "/invoices?pageIndex=" + pageIndex + "&pageSize=" + pageSize);
        HttpResponse response = httpClient.execute(get);
        checkResponse(response);

        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(responseString, new TypeToken<InvoicePagination>() {
        }.getType());
    }

    /**
     * Get a invoice
     *
     * @param id
     * @param invoiceId
     * @return
     * @throws IOException
     */
    public static Invoice getInvoice(Long id, Long invoiceId) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION + id + "/invoices/" + invoiceId);
        HttpResponse response = httpClient.execute(get);
        checkResponse(response);

        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        System.out.println("response: " + responseString);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(responseString, Invoice.class);
    }

    /**
     * Get a driver
     *
     * @param driverId
     * @return
     * @throws IOException
     */
    public static Driver getDriver(Long driverId) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION + driverId);
        HttpResponse response = httpClient.execute(get);
        checkResponse(response);

        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        System.out.println("response: " + responseString);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(responseString, Driver.class);
    }

    /**
     * Get a list of cars
     *
     * @param driverId
     * @return
     * @throws IOException
     */
    public static List<Car> getCars(Long driverId) throws IOException {

        List<Ownership> owners = getOwnerships(driverId);

        List<Car> cars = new ArrayList<>();
        for (Ownership o : owners) {
            Car existingCar = null;
            for (Car c : cars) {
                if (o.getCar().getId() == c.getId()) {
                    existingCar = c;
                    if (o.getEndDate() == null) {
                        existingCar.setCurrentOwnership(o);
                    } else {
                        existingCar.getPastOwnerships().add(o);
                    }
                }
            }

            if (existingCar == null) {
                Car c = o.getCar();
                if (o.getEndDate() == null) {
                    c.setCurrentOwnership(o);
                } else {
                    c.getPastOwnerships().add(o);
                }
                cars.add(c);
            }
        }
        return cars;
    }

    /**
     * Update a invoice
     *
     * @param userId
     * @param invoice
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static Long updateInvoice(Long userId, Invoice invoice) throws IOException, JSONException {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut post = new HttpPut(BASE_URL_PRODUCTION + userId + "/invoices/" + invoice.getId());

        String jsonBody = gson.toJson(invoice);
        StringEntity postingString = new StringEntity(jsonBody, StandardCharsets.UTF_8);
        System.out.println(jsonBody);
        post.setEntity(postingString);
        post.setHeader(HTTP.CONTENT_TYPE, "application/json");

        HttpResponse response = httpClient.execute(post);
        checkResponse(response);

        //Response
        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(responseString);
        System.out.println("JSON Response: " + json);
        return json.getLong("id");
    }

    /**
     * Update a user
     *
     * @param user
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static Driver updateUser(Driver user) throws IOException, JSONException {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(BASE_URL_PRODUCTION + user.getId());

        String jsonBody = gson.toJson(user);
        StringEntity postingString = new StringEntity(jsonBody, StandardCharsets.UTF_8);
        System.out.println(jsonBody);
        post.setEntity(postingString);
        post.setHeader(HTTP.CONTENT_TYPE, "application/json");

        HttpResponse response = httpClient.execute(post);
        checkResponse(response);

        //Response
        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(responseString, Driver.class);
    }

    /**
     * Get the cartracker id by invoice
     *
     * @param id
     * @param invoiceId
     * @return
     * @throws IOException
     * @throws org.codehaus.jettison.json.JSONException
     */
    public static Long getCartrackerId(Long id, Long invoiceId) throws IOException, JSONException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION + id + "/invoices/" + invoiceId + "/cartracker");
        HttpResponse response = httpClient.execute(get);
        checkResponse(response);

        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(responseString);
        System.out.println("JSON Response: " + json);
        return json.getLong("cartrackerId");
    }

    /**
     * Gets the positions by cartracker id
     *
     * @param id cartracker id
     * @return
     * @throws IOException
     * @throws org.codehaus.jettison.json.JSONException
     */
    public static List<Position> getPositions(Long id, Date date) throws IOException, JSONException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String temp = formatter.format(date);

        String startDate = temp.substring(0, temp.length() - 2) + "01";
        String endDate = temp.substring(0, temp.length() - 2) + c.get(Calendar.DAY_OF_MONTH);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("http://movement.s63a.marijn.ws/api/trackers/" + id + "/movements/_byperiod?startDate=" + startDate + "&endDate" + endDate);
        HttpResponse response = httpClient.execute(get);
        checkResponse(response);

        List<Position> positions = new ArrayList<>();
        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        List<TrackingPeriod> periods = gson.fromJson(responseString, new TypeToken<List<TrackingPeriod>>() {
        }.getType());

        for (TrackingPeriod t : periods) {
            positions.addAll(t.getPositions());
        }
        return positions;
    }

    public static List<Ownership> getOwnerships(Long driverId) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION + driverId + "/ownerships");
        HttpResponse response = httpClient.execute(get);
        checkResponse(response);

        String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        System.out.println("response: " + responseString);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(responseString, new TypeToken<List<Ownership>>() {
        }.getType());
    }

    /**
     * Check if a received response is not null and has the statusCode code of 200.
     *
     * @param response to check.
     * @throws CommunicationException if the response was null or the statusCode was not 200.s
     */
    private static void checkResponse(HttpResponse response) throws CommunicationException {
        if (response == null) {
            throw new CommunicationException("Response was null");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new CommunicationException("Response did not have statusCode 200, instead the code was: " + statusCode);
        }
    }
}
