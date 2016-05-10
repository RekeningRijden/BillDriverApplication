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
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.fromJson(responseString, new TypeToken<List<Invoice>>() {
        }.getType());
    }
}


