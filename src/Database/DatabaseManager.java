package Database;

import java.sql.*;

import java.util.ArrayList;

public class DatabaseManager {
	
	private Connection connection;
	
	private boolean connectToDatabase() {
		boolean isTrue = false;
		if(connection != null)
		{
			isTrue = true;
		} else {
			try {
				//Class.forName("com.mysql.jdbc.Driver");
				Class.forName("org.mariadb.jdbc.Driver");
		        System.out.println("Driver loading success!");
		        //String url = "jdbc:mysql://localhost:3306/";//"jdbc:mysql://hosting2120117.online.pro:3306/";
				String url = "jdbc:mariadb://localhost:3306/";
		        String dbName = "bank_system";		
		        String userName = "root";
		        String password = "";
		        try {
			          // Connection
			            connection = DriverManager.getConnection(url + dbName, userName, password);
			            createTableIfNotExists();
			            isTrue = true;
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				} catch (Exception e) {
		            e.printStackTrace();
		            
	        }
		}
		return isTrue;
	}
	
	private void createTableIfNotExists() {
		// user logged
		try {
	        Statement statement = connection.createStatement();
	        String query = "CREATE TABLE IF NOT EXISTS user_logged (" +
	                "user_id VARCHAR(255) NOT NULL," +
	                "token VARCHAR(255) NOT NULL," +
	                "login_time VARCHAR(255) NOT NULL," +
	                "expire VARCHAR(255) NOT NULL" +
	                ");";
	        statement.executeUpdate(query);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		try {
			Statement statement = connection.createStatement();
			String query = "CREATE TABLE IF NOT EXISTS customers (" +
					"id INT AUTO_INCREMENT PRIMARY KEY," +
					"name VARCHAR(255) NOT NULL," +
					"lastname VARCHAR(255) NOT NULL," +
					"username VARCHAR(255) NOT NULL," +
					"password VARCHAR(255) NOT NULL," +
					"email VARCHAR(255) NOT NULL," +
					"genre VARCHAR(255) NOT NULL," +
					"balance VARCHAR(255) NOT NULL," +
					"savings VARCHAR(255) NOT NULL," +
					"last_login VARCHAR(255) NOT NULL," +
					"date_registration VARCHAR(255) NOT NULL" +
					");";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// employeers
		try {
	        Statement statement = connection.createStatement();
	        String query = "CREATE TABLE IF NOT EXISTS employeers (" +
	                "id INT AUTO_INCREMENT PRIMARY KEY," +
	                "name VARCHAR(255) NOT NULL," +
	                "lastname VARCHAR(255) NOT NULL," +
	                "username VARCHAR(255) NOT NULL," +
	                "password VARCHAR(255) NOT NULL," +
	                "email VARCHAR(255) NOT NULL," +
	                "genre VARCHAR(255) NOT NULL," +
	                "position VARCHAR(255) NOT NULL," +
	                "last_login VARCHAR(255) NOT NULL," +
	                "date_registration VARCHAR(255) NOT NULL" +
	                ");";
	        statement.executeUpdate(query);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    }
	
	public boolean registerUser(String username, String password, boolean isEmployeer) {
		boolean isOK = false; 
		String userName = "", userPass = "";
		if(connectToDatabase() == true)
    	{
			try {
				if(isEmployeer == true)
				{
					String query = "SELECT * FROM employeers WHERE username = ? AND password = ?";
					try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		                // Set the values for the parameters
		                preparedStatement.setString(1, username);
		                preparedStatement.setString(2, password);

		                // Execute the query
		                ResultSet resultSet = preparedStatement.executeQuery();

		                while (resultSet.next()) {
		                	userName = resultSet.getString("username");
		                    userPass = resultSet.getString("password");
		                }
		                
		                
		            }
					
				} else {
		            String query = "SELECT * FROM customers WHERE username = ? AND password = ?";
		            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		                // Set the values for the parameters
		                preparedStatement.setString(1, username);
		                preparedStatement.setString(2, password);

		                // Execute the query
		                ResultSet resultSet = preparedStatement.executeQuery();

		                // Append retrieved data to the list
		                while (resultSet.next()) {
		                	userName = resultSet.getString("username");
		                    userPass = resultSet.getString("password");
		                }
		                
		                
		            }
				}
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            //JOptionPane.showMessageDialog(this, "Error fetching data.");
	        }
			
			if(userName == "" && userPass == "") {	
		        try {
		            // Hash the password before storing it
		            String hashedPassword = hashPassword(password);
		            String sql;
		            if(isEmployeer == true)
		            {
		            	sql = "INSERT INTO employeers (username, password) VALUES (?, ?);";
		            } else {
		            	sql = "INSERT INTO customers (username, password) VALUES (?, ?);";
		            }
		            PreparedStatement preparedStatement = connection.prepareStatement(sql);
		            preparedStatement.setString(1, username);
		            preparedStatement.setString(2, hashedPassword);
		            preparedStatement.executeUpdate();
		            System.out.println("User registered successfully.");
		            isOK = true;
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			}
			
    	}  
		
        return isOK;
        
    }
	
	public String[] userData(String username, String password, boolean isEmployeer)
	{
		ArrayList<String> userDataList = new ArrayList<>();
		
		if(connectToDatabase() == true)
    	{
			try {
				if(isEmployeer == true)
				{
					String query = "SELECT * FROM employeers WHERE username = ? AND password = ?";
					try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		                // Set the values for the parameters
		                preparedStatement.setString(1, username);
		                preparedStatement.setString(2, password);

		                // Execute the query
		                ResultSet resultSet = preparedStatement.executeQuery();

		                // Append retrieved data to the list
		                while (resultSet.next()) {
		                	String userId = resultSet.getString("id");
		                    String userName = resultSet.getString("name");
		                    String userLastname = resultSet.getString("lastname");
		                    String userNickName = resultSet.getString("username");
		                    String userPass = resultSet.getString("password");
		                    String userEmail = resultSet.getString("email");
		                    String userPosition = resultSet.getString("position");
	
		                    //data.append("Username: ").append(username).append(", Password: ").append(password).append("\n");
		                    userDataList.add(userId + "," + userName + "," + userLastname + "," + userNickName + "," + userPass + "," + userEmail + "," + userPosition);
		                }
		                
		                
		            }
				} else {
		            String query = "SELECT * FROM customers WHERE username = ? AND password = ?";
		            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		                // Set the values for the parameters
		                preparedStatement.setString(1, username);
		                preparedStatement.setString(2, password);

		                // Execute the query
		                ResultSet resultSet = preparedStatement.executeQuery();

		                // Append retrieved data to the list
		                while (resultSet.next()) {
		                	String userId = resultSet.getString("id");
		                    String userName = resultSet.getString("name");
		                    String userLastname = resultSet.getString("name");
		                    String userNickName = resultSet.getString("username");
		                    String userPass = resultSet.getString("password");
		                    String userEmail = resultSet.getString("email");
		                    String userBalance = resultSet.getString("balance");
	
		                    //data.append("Username: ").append(username).append(", Password: ").append(password).append("\n");
		                    userDataList.add(userId + "," + userName + "," + userLastname + "," + userNickName + "," + userPass + "," + userEmail + "," + userBalance);
		                }
		                
		                
		            }
				}
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            //JOptionPane.showMessageDialog(this, "Error fetching data.");
	        }
    	}
		// Convert the ArrayList to a String array
        String[] userData = userDataList.toArray(new String[0]);
		return userData;	
	}
	
	public boolean loginUser(String username, String password, boolean isEmployeer) {
		
		boolean userExist = false;
		
		if(connectToDatabase() == true)
    	{
			try {
        	
	            // Hash the provided password for comparison
	            String hashedPassword = hashPassword(password);
	            String mSql = (isEmployeer == true) ? "employeers" : "customers";
	            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + mSql + " WHERE username = ? AND password = ?;");
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, hashedPassword);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	            	userExist = true;
                } else {
                    // userExist = false;
                }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            //JOptionPane.showMessageDialog(this, "Error fetching data.");
	        }
    	}
		
        return userExist;
        
    }
	
	public String hashPassword(String password) {
        //return Integer.toString(password.hashCode());
		return password;
    }

	public void logoutUser() {
        // Implement logout logic if needed
        System.out.println("Logout successful.");
    }

	public void closeDatabaseConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
}
