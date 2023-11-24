package Core;

import UIDesign.MainWindow;
import Database.DatabaseManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Login {
	
	public String[] checkLogin(String username,String password, boolean isEmployeer, MainWindow mainWindow)
	{
		String errorUser = "";
		String errorPass = "";
		String message = "";
		
		if(username.length() < 1)
		{
			errorUser = "*Field is Empty!";
		}
		else if(username.length() < 3)
		{
			errorUser = "*Characters is a few!";
		}
		
		if(password.length() < 1)
		{
			errorPass = "*Field is Empty!";
		}
		
		if(errorUser == "" && errorPass == "")
		{
			// Rest API
			
			if(isEmployeer == false) {
				try {
				// Replace with the actual base URL of your REST API
		            String baseUrl = "http://localhost/bank-system/";
	
		            // Perform login
		            String token = performLogin(baseUrl, username, password);
	
		            if (token != null) {
		                System.out.println("Login successful. Token: " + token);
	
		                // Use the obtained token to perform data retrieval
		                String data = viewData(baseUrl, token);
	
		                if (data != null) {
		                    System.out.println("Data retrieved: " + data);
		                } else {
		                    System.out.println("Error retrieving data.");
		                }
		            } else {
		                System.out.println("Login failed.");
		            }

		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			} else {
			
				// if user exist, that data send to Main.java
				DatabaseManager databaseManager = new DatabaseManager();
				if(databaseManager.loginUser(username,password,isEmployeer) == true)
				{
					String[] userdata = databaseManager.userData(username,password,isEmployeer);
					Main main = new Main();
					main.userLoggedIn(userdata,isEmployeer,mainWindow);
				} else {
					message = "User or Pass is invalid!";
				}
				databaseManager.closeDatabaseConnection();
			}
			
		} 
		
		String[] errors = {errorUser,errorPass,message};
		return errors;
		
	}
		
		private String performLogin(String baseUrl, String username, String password) throws IOException {
			
	        String loginUrl = baseUrl + "/login/login.php";
	        String credentials = username + ":" + password;
	        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
	        
	        //URL url = new URL(loginUrl);
	        
	        URL loginUrl = new URL(loginUrl);

            // Create the connection
            HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Prepare the request payload (replace with your actual request format)
            String payload = "username=" + username + "&password=" + password;

            // Write the payload to the request body
            connection.getOutputStream().write(payload.getBytes("UTF-8"));

	        /*
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
	        connection.setDoOutput(true);
	        try (OutputStream os = connection.getOutputStream()) {
	            os.write(new byte[0]);
	        }
	        */
	        int responseCode = connection.getResponseCode();
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	            String line;
	            StringBuilder response = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }

	            // Check if the request was successful (HTTP status code 2xx)
	            if (responseCode >= 200 && responseCode < 300) {
	                return response.toString();
	            } else {
	                System.out.println("Login failed. Status code: " + responseCode);
	                return null;
	            }
	        }
	   
	    }
	
		private String viewData(String baseUrl, String token) throws IOException {
	        String viewDataUrl = baseUrl + "/data";
	        URL url = new URL(viewDataUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");

	        // Set the authorization header with the obtained token
	        connection.setRequestProperty("Authorization", "Bearer " + token);

	        // Get the response code
	        int responseCode = connection.getResponseCode();

	        // Read and return the response
	        //String
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	            String line;
	            StringBuilder response = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }

	            // Check if the request was successful (HTTP status code 2xx)
	            if (responseCode >= 200 && responseCode < 300) {
	                return response.toString();
	            } else {
	                System.out.println("Error retrieving data. Status code: " + responseCode);
	                return null;
	            }
	        }
	    }
		
		public void test() {
		    authenticate("client", "secret", "admin", "admin");
		}

		private void authenticate(String client, String secret, String username, String password) {
		    HttpClient httpClient = HttpClient.newHttpClient();
		    HttpRequest request = HttpRequest.newBuilder()
		            .uri(URI.create("http://localhost:8080/oauth/token"))
		            .header("Authorization", getBasicAuthHeader(client, secret))
		            .header("Content-Type", "application/x-www-form-urlencoded")
		            .POST(HttpRequest.BodyPublishers.ofString(getAuthRequestBody(username, password)))
		            .build();
		    try {
		        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		        if (response.statusCode() == HttpStatus.OK.value()) {
		            String responseText = response.body();
		            System.out.println("Response: " + responseText);
		            // Using Jackson to convert JSON responce to a map:
		            Map<String, Object> map = new ObjectMapper().readValue(responseText, Map.class);
		            String accessToken = (String) map.get("access_token");
		            System.out.println("Access token: " + accessToken);
		        } else {
		            System.out.println("Error: " + response.statusCode());
		        }
		    } catch (IOException | InterruptedException e) {
		        e.printStackTrace();
		    }
		}

		private String getBasicAuthHeader(String client, String secret) {
		    String str = client + ":" + secret;
		    return "Basic " + Base64.getEncoder().encodeToString(str.getBytes());
		}

		private String getAuthRequestBody(String username, String password) {
		    return "grant_type=password&username=" + username + "&password=" + password;
		}
}