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
				
			        
			            String apiUrl = "http://localhost/bank-system/login/login.php";
			          //  String apiUrl = "http://localhost/bank-system/viewdata/";
			          // String apiUrl = "http://localhost/bank-system/login/login.php;"; 
			           //   String apiUrl = "http://localhost/bank-system/login/index.php;"; 

			            try {
			                URL url = new URL(apiUrl);
			                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			                // Set the request method to POST
			                connection.setRequestMethod("POST");
			                connection.setRequestProperty("Content-Type", "application/json");
			                connection.setDoOutput(true);

			                // Create the JSON payload
			                String jsonInputString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

			                // Send the request
			                try (OutputStream os = connection.getOutputStream()) {
			                    byte[] input = jsonInputString.getBytes("utf-8");
			                    os.write(input, 0, input.length);
			                }

			                // Read the response
			                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
			                    StringBuilder response = new StringBuilder();
			                    String responseLine;
			                    while ((responseLine = br.readLine()) != null) {
			                        response.append(responseLine.trim());
			                    }

			                    // Parse the JSON response
			                    String jsonResponse = response.toString();
			                    
			                    String status = jsonResponse.contains("status") ? jsonResponse.split("\"status\":\"")[1].split("\"")[0] : null;
			                    //System.out.println("Server Response: " + jsonResponse2);
                               
			                    // Extract the token from the JSON response   
			                    //String state =  jsonResponse.contains("success") ? jsonResponse.split("\"success\":\"")[1].split("\"")[0] : null;
			                    String token = jsonResponse.contains("token") ? jsonResponse.split("\"token\":\"")[1].split("\"")[0] : null;
			                    System.out.println("Login acces " + status);
			                    System.out.println("Received Token: " + token);

			                    // Store and use the token for subsequent requests
			                    if(status == "error" || token == null || token == "Unauthorized access")
			                    {
			                    	System.out.println("chyba");
			                    } else {
			                    	System.out.println("Load data");
			                    	
			                    	String[] userdata = userData(username,password,isEmployeer,token);
			    					Main main = new Main();
			    					main.userLoggedIn(token,userdata,isEmployeer,mainWindow);
			    				
			                    }
			                }

			                // Close the connection
			                connection.disconnect();
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
					main.userLoggedIn("",userdata,isEmployeer,mainWindow);
				} else {
					message = "User or Pass is invalid!";
				}
				databaseManager.closeDatabaseConnection();
			}
			
		} 
		
		String[] errors = {errorUser,errorPass,message};
		return errors;
		
	}
	/*
	private static String extractToken(String jsonResponse) {
        // Implement your JSON parsing logic here
        // For simplicity, we assume the token is directly present in the JSON response
        return jsonResponse.contains("token") ? jsonResponse.split("\"token\":\"")[1].split("\"")[0] : null;
    }
	*/
	public String[] userData(String username, String password, boolean isEmployeer,String token )
	{
		ArrayList<String> userDataList = new ArrayList<>();
		
		String apiUrl = "http://localhost/bank-system/viewdata/viewdata.php";
		
		try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            // Read the response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Parse and print the JSON response
                String jsonResponse = response.toString();
                System.out.println("Server Response: " + jsonResponse);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		// Convert the ArrayList to a String array
        String[] userData = userDataList.toArray(new String[0]);
		return userData;	
	}
	
}