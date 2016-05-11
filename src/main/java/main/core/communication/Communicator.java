package main.core.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import main.domain.Invoice;
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
     * @throws IOException When trying to execute the http request or converts the response to a String
     */
    public static List<Invoice> getAllInvoices(Long id) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(BASE_URL_PRODUCTION + id + "/invoices");
        HttpResponse response = httpClient.execute(get);

        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(responseString, new TypeToken<List<Invoice>>() {
        }.getType());
    }
    
    public static Long updateInvoice(Long userId, Invoice invoice) throws IOException, JSONException{
        Gson gson = new Gson();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut post = new HttpPut(BASE_URL_PRODUCTION + userId + "/invoices/" + invoice.getId());

        String jsonBody = gson.toJson(invoice);
        StringEntity postingString = new StringEntity(jsonBody, "UTF-8");
        System.out.println(jsonBody);
        post.setEntity(postingString);
        post.setHeader(HTTP.CONTENT_TYPE, "application/json");

        HttpResponse response = httpClient.execute(post);

        //Response
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        JSONObject json = new JSONObject(responseString);
        System.out.println("JSON Response: " + json);
        return json.getLong("id");
    }
}


