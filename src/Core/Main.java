package Core;
import UIDesign.MainWindow;
//import DatabaseManager.DatabaseManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Main {
	
	public String[] userData = {};
	
	private MainWindow mainWindow;
	private Timer timer;
	private int timeWelcomeScreen;
	private int m_time;
	
	public void Init(MainWindow m_mainWindow)
	{
		mainWindow = m_mainWindow;
		
		timeWelcomeScreen = 2;
		welcomeTitleText();
		
	}
	
	void welcomeTitleText()
	{
		
		mainWindow.showPanel("welcomePanel");
		
		timer = new Timer(1000, new ActionListener(){
		     @Override
		     public void actionPerformed(ActionEvent e)
		     {
		          TimerAction(e);
		     }
		});
		timer.start();
	}
	
	private void initializeUser()
	{
		if(userData.length == 0)
		{
			mainWindow.showPanel("loginPanel");
		}
	}
	
	private void TimerAction(ActionEvent e)
	{
		
        if (m_time >= timeWelcomeScreen)
        {
        	if (timer.isRunning())
    		{
        		timer.stop();
        		initializeUser();
    		}
        } else {
        	m_time++;
        }
        
	}
	
	public void userLoggedIn(String[] mUserData, boolean isEmployeer, MainWindow m_mainWindow)
	{
		mainWindow = m_mainWindow;
		userData = mUserData;
		
		if(isEmployeer == true)
		{
			mainWindow.showPanel("employeerPanel");
		} else {
			mainWindow.showPanel("customerPanel");
		}
		
		
		for(int j = 0; j < userData.length; ++j) {
	        System.out.println(userData[j]);
	      }
		
	}
	
}
