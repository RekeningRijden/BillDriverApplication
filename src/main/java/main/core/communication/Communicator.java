package main.core.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import main.domain.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.protocol.HTTP;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author Sam
 */
public class Communicator {

    /**
     * The production url of the Movementsystem api.
     */
    private static final String BASE_URL_PRODUCTION = "http://administration.s63a.marijn.ws/api/users/"; // or for test http://localhost:8080/AdministrationSystem/api/users/

    private static final String CHARACTER_SET = "UTF-8";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private Communicator() {
        // empty constructor
    }

    /**
     * Gets all known cartrackers from the Movementsystem Api
     *
     * @param id
     * @return All known cartrackers
     * @throws IOException When trying to execute the http request or converts
     * the response to a String
     */
    public static List<Invoice> getAllInvoices(Long id) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION + id + "/invoices");
        HttpResponse response = httpClient.execute(get);

        String responseString = EntityUtils.toString(response.getEntity(), CHARACTER_SET);
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
        return gson.fromJson(responseString, new TypeToken<List<Invoice>>() {
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

        String responseString = EntityUtils.toString(response.getEntity(), CHARACTER_SET);
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
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

        String responseString = EntityUtils.toString(response.getEntity(), CHARACTER_SET);
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
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
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION + driverId + "/ownerships");
        HttpResponse response = httpClient.execute(get);

        String responseString = EntityUtils.toString(response.getEntity(), CHARACTER_SET);
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
        List<Ownership> owners = gson.fromJson(responseString, new TypeToken<List<Ownership>>() {
        }.getType());

        List<Car> cars = new ArrayList<>();
        for (Ownership o : owners) {
            boolean carExists = false;
            for (Car c : cars) {
                if (o.getCar() == c) {
                    carExists = true;
                }
            }
            if (!carExists) {
                cars.add(o.getCar());
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
    public static Long updateInvoice(Long userId, Invoice invoice) {
        try {
            Gson gson = new Gson();
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut post = new HttpPut(BASE_URL_PRODUCTION + userId + "/invoices/" + invoice.getId());

            String jsonBody = gson.toJson(invoice);
            StringEntity postingString = new StringEntity(jsonBody, CHARACTER_SET);
            post.setEntity(postingString);
            post.setHeader(HTTP.CONTENT_TYPE, "application/json");

            HttpResponse response = httpClient.execute(post);

            //Response
            String responseString = EntityUtils.toString(response.getEntity(), CHARACTER_SET);
            JSONObject json = new JSONObject(responseString);
            return json.getLong("id");
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Driver updateUser(Driver user) {
        try {
            Gson gson = new Gson();
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(BASE_URL_PRODUCTION + user.getId());

            String jsonBody = gson.toJson(user);
            StringEntity postingString = new StringEntity(jsonBody, CHARACTER_SET);
            post.setEntity(postingString);
            post.setHeader(HTTP.CONTENT_TYPE, "application/json");

            HttpResponse response = httpClient.execute(post);

            //Response
            String responseString = EntityUtils.toString(response.getEntity(), CHARACTER_SET);
            gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
            return gson.fromJson(responseString, Driver.class);
        } catch (IOException ex) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get the cartracker id by invoice
     *
     * @param id
     * @param invoiceId
     * @return
     * @throws org.codehaus.jettison.json.JSONException
     */
    public static Long getCartrackerId(Long id, Long invoiceId) throws JSONException {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(BASE_URL_PRODUCTION + id + "/invoices/" + invoiceId + "/cartracker");
            HttpResponse response = httpClient.execute(get);

            String responseString = EntityUtils.toString(response.getEntity(), CHARACTER_SET);
            JSONObject json = new JSONObject(responseString);
            return json.getLong("cartrackerId");
        } catch (IOException ex) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * gets the positions by cartracker id
     *
     * @param id cartracker id
     * @return
     * @throws IOException
     * @throws org.codehaus.jettison.json.JSONException
     */
    public static List<Position> getPositions(Long id) {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet("http://movement.s63a.marijn.ws/api/trackers/" + id + "/movements");
            HttpResponse response = httpClient.execute(get);

            List<Position> positions = new ArrayList<>();
            String responseString = EntityUtils.toString(response.getEntity(), CHARACTER_SET);
            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
            List<TrackingPeriod> periods = gson.fromJson(responseString, new TypeToken<List<TrackingPeriod>>() {
            }.getType());

            for (TrackingPeriod t : periods) {
                positions.addAll(t.getPositions());
            }
            return positions;
        } catch (IOException ex) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
}
