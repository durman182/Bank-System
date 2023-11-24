package Core;

import Database.DatabaseManager;
import UIDesign.MainWindow;

public class Register {
	public String[] checkRegister(String username,String password,String email,String name,String lastname,boolean isEmployeer,MainWindow mainWindow)
	{
		String errorUser = "";
		String errorPass = "";
		String errorEmail = "";
		String errorName = "";
		String errorLastname = "";
		String message = "";
		if(username.length() < 1)
		{
			errorUser = "*Field is Empty!";
		}
		else if(username.length() < 3)
		{
			errorUser = "*Characters is a few!";
		}
		
		if(name.length() < 1)
		{
			errorUser = "*Field is Empty!";
		}
		else if(name.length() < 3)
		{
			errorUser = "*Characters is a few!";
		}
		
		if(password.length() < 1)
		{
			errorPass = "*Field is Empty!";
		}
		
		if(errorUser == "" && errorPass == "")
		{
			
			// if user exist, that data send to Main.java
			DatabaseManager databaseManager = new DatabaseManager();
			if(databaseManager.registerUser(username,password,isEmployeer) == true)
			{
				String[] userdata = databaseManager.userData(username,password,isEmployeer);
				Main main = new Main();
				main.userLoggedIn(userdata,isEmployeer,mainWindow);
			} else {
				message = "User or Pass is invalid!";
			}
			databaseManager.closeDatabaseConnection();
			
		} 
		
		String[] errors = {errorUser,errorPass,message};
		return errors;
		
	}
}
