package Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Customer {
	
	public void GetDataFromDatabase()
	{
		try {
            // Replace "your_api_url" with the actual URL of your REST API
            String apiUrl = "your_api_url";

            // Create an instance of HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Create a GET request to the API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful (HTTP status code 200)
            if (response.statusCode() == 200) {
                // Print the response body (you may want to parse and display it in a more meaningful way)
                System.out.println("Response from the API:\n" + response.body());
            } else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
