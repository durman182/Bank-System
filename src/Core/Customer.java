package Core;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
