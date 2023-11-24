package UIDesign;

import Core.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.EventQueue;

import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Window.Type;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel cardPanel;
    private CardLayout cardLayout;
    
    private JLabel userError;
	private JLabel passwordError;
	private JLabel loginMessage;
	private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    
    private JCheckBox loginChckbxEmployyer;
    
	private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JTextField registerEmailField;
    private JTextField registerNameFextField;
    private JTextField registerLastnameTextField;
    
    private JRadioButton registerRdbtnMale;
    private JRadioButton registerRdbtnFemale;
    private JCheckBox registerChckbxEmployeer; 
    
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Images/bank_icon.jpg")));
		setBackground(new Color(255, 255, 255));
		Init();
	}
	
	public void Init() {
		
		setResizable(false);
		
		setTitle("Bank System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        //getContentPane().add(cardPanel);
        setContentPane(cardPanel);
        
        cardPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

		//JPanel panel = new JPanel();
        // Add your components to the panel
        //getContentPane().add(panel);
        
        welcomeTitleTextInit();
        addloginPanel();
        addRegisterPanel();
        addEmployeerPanel();
        addCustomerPanel();

        setVisible(true);
        
        // welcome Screen
		Main main = new Main();
		main.Init(this); 
		
	}
	
	/* Initialize on Load */
	private void centerFrameOnScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
	
	private void welcomeTitleTextInit()
	{
		JPanel welcomePanel = new JPanel();
		getContentPane().add(welcomePanel);
		welcomePanel.setLayout(null);
		
		JLabel welcomeLabel = new JLabel("Welcome Bank System");
		welcomeLabel.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 36));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setBounds(95, 11, 400, 80);
		welcomePanel.add(welcomeLabel);
		
		// Create a JLabel to display the image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //imageLabel.setIcon(new ImageIcon(MainWindow.class.getResource("/Images/bank_icon.jpg")));
        //ImageIcon imageIcon = new ImageIcon(MainWindow.class.getResource("/Images/bank_icon.jpg"));
        // Check if the image size is greater than zero
        //if (imageIcon.getIconWidth() > 0 && imageIcon.getIconHeight() > 0) {
        imageLabel.setIcon(new ImageIcon(MainWindow.class.getResource("/Images/bank_icon.jpg")));
        imageLabel.setBounds(153, 32, 280, 285);
        //} else {
        //    System.err.println("Error: Image not loaded or has zero size.");
        //}
        // Set the position and size of the JLabel
        //imageLabel.setBounds(215, 164, 100, 100);
        // Add the image label to the panel
        welcomePanel.add(imageLabel);
	}
	
	private void addloginPanel()
	{
		JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        
        cardPanel.add(loginPanel, "loginPanel");
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        userLabel.setBounds(119, 155, 80, 25);
        loginPanel.add(userLabel);

        loginUsernameField = new JTextField(20);
        loginUsernameField.setBounds(209, 155, 165, 25);
        // Add a DocumentListener to the usernameField
        loginUsernameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleUsernameChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleUsernameChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }
        });
        
           
        loginPanel.add(loginUsernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setBounds(119, 185, 80, 25);
        loginPanel.add(passwordLabel);

        loginPasswordField = new JPasswordField(20);
        loginPasswordField.setBounds(209, 185, 165, 25);
        loginPanel.add(loginPasswordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(119, 215, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkInputLoginRegister("login");
            }
        });
        loginPanel.add(loginButton);

        JButton switchToRegisterButton = new JButton("Switch to Register");
        switchToRegisterButton.setBounds(119, 251, 255, 25);
        switchToRegisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showPanel("registerPanel");
            }
        });
        loginPanel.add(switchToRegisterButton);

        
        
        userError = new JLabel("");
        userError.setHorizontalAlignment(SwingConstants.LEFT);
        userError.setForeground(new Color(255, 0, 0));
        userError.setBounds(384, 155, 165, 25);
        loginPanel.add(userError);
        
        passwordError = new JLabel("");
        passwordError.setHorizontalAlignment(SwingConstants.LEFT);
        passwordError.setForeground(new Color(255, 0, 0));
        passwordError.setBounds(384, 185, 165, 25);
        loginPanel.add(passwordError);
        
        loginMessage = new JLabel("");
        loginMessage.setHorizontalAlignment(SwingConstants.CENTER);
        loginMessage.setForeground(new Color(255, 0, 0));
        loginMessage.setBounds(119, 127, 255, 25);
        loginPanel.add(loginMessage);
        
        JLabel lblBankSystem = new JLabel("Bank System");
        lblBankSystem.setHorizontalAlignment(SwingConstants.LEFT);
        lblBankSystem.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 28));
        lblBankSystem.setBounds(90, 0, 220, 70);
        loginPanel.add(lblBankSystem);
        
        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon(MainWindow.class.getResource("/Images/bank_icon.jpg"));

        // Resize the image to 128x128 pixels
        ImageIcon resizedIcon = resizeImageIcon(originalIcon, 80, 80);
        imageLabel.setIcon(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBounds(0, 0, 80, 80);
        loginPanel.add(imageLabel);
        
        loginChckbxEmployyer = new JCheckBox("employeer");
        loginChckbxEmployyer.setBounds(277, 216, 97, 23);
        loginPanel.add(loginChckbxEmployyer);
	}
	
	private void addRegisterPanel() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(null);
        
        cardPanel.add(registerPanel, "registerPanel");

        JLabel registerUserLabel = new JLabel("Username:");
        registerUserLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        registerUserLabel.setBounds(36, 170, 80, 25);
        registerPanel.add(registerUserLabel);

        registerUsernameField = new JTextField(20);
        registerUsernameField.setBounds(126, 170, 165, 25);
        registerPanel.add(registerUsernameField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(74, 263, 100, 25);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkInputLoginRegister("register");
            }
        });
        registerPanel.add(registerButton);

        JButton switchToLoginButton = new JButton("Switch to Login");
        switchToLoginButton.setBounds(90, 299, 150, 25);
        switchToLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showPanel("loginPanel");
            }
        });
        registerPanel.add(switchToLoginButton);

        JLabel lblBankSystem = new JLabel("Bank System");
        lblBankSystem.setHorizontalAlignment(SwingConstants.LEFT);
        lblBankSystem.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 28));
        lblBankSystem.setBounds(90, 0, 220, 70);
        registerPanel.add(lblBankSystem);
        
        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon(MainWindow.class.getResource("/Images/bank_icon.jpg"));

        // Resize the image to 128x128 pixels
        ImageIcon resizedIcon = resizeImageIcon(originalIcon, 80, 80);
        imageLabel.setIcon(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBounds(0, 0, 80, 80);
        registerPanel.add(imageLabel);
        
        JLabel registerPasswordLabel = new JLabel("Password:");
        registerPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        registerPasswordLabel.setBounds(36, 197, 80, 25);
        registerPanel.add(registerPasswordLabel);
        
        registerPasswordField = new JPasswordField(20);
        registerPasswordField.setBounds(126, 197, 165, 25);
        registerPanel.add(registerPasswordField);
        
        registerEmailField = new JPasswordField(20);
        registerEmailField.setBounds(126, 227, 165, 25);
        registerPanel.add(registerEmailField);
        
        JLabel registerEmailLabel = new JLabel("Email:");
        registerEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        registerEmailLabel.setBounds(36, 227, 80, 25);
        registerPanel.add(registerEmailLabel);
        
        JLabel registerNameLabel = new JLabel("Name:");
        registerNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        registerNameLabel.setBounds(301, 170, 80, 25);
        registerPanel.add(registerNameLabel);
        
        registerNameFextField = new JTextField(20);
        registerNameFextField.setBounds(391, 170, 165, 25);
        registerPanel.add(registerNameFextField);
        
        JLabel registerLastnameLabel = new JLabel("Lastname:");
        registerLastnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        registerLastnameLabel.setBounds(301, 197, 80, 25);
        registerPanel.add(registerLastnameLabel);
        
        registerLastnameTextField = new JTextField(20);
        registerLastnameTextField.setBounds(391, 197, 165, 25);
        registerPanel.add(registerLastnameTextField);
        
        JLabel registerGenreLabel = new JLabel("Genre:");
        registerGenreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        registerGenreLabel.setBounds(301, 227, 80, 25);
        registerPanel.add(registerGenreLabel);
        
        registerRdbtnMale = new JRadioButton("male");
        registerRdbtnMale.setBounds(391, 228, 87, 23);
        registerPanel.add(registerRdbtnMale);
        
        registerRdbtnFemale = new JRadioButton("female");
        registerRdbtnFemale.setBounds(491, 228, 87, 23);
        registerPanel.add(registerRdbtnFemale);
        
        registerChckbxEmployeer = new JCheckBox("employeer");
        registerChckbxEmployeer.setBounds(194, 264, 97, 23);
        registerPanel.add(registerChckbxEmployeer);
    }
	
	private void addEmployeerPanel() {
		
		//setSizeWindow("forViewData");
		
		 JPanel employeerPanel = new JPanel();
	        
	     cardPanel.add(employeerPanel, "employeerPanel");
	     employeerPanel.setLayout(null);
	     
	     JComboBox comboBox = new JComboBox();
	     comboBox.setBounds(0, 0, 41, 33);
	     employeerPanel.add(comboBox);
	}
	
	private void addCustomerPanel() {
		//setSizeWindow("forViewData");
		
		JPanel customerPanel = new JPanel();
		customerPanel.setBorder(UIManager.getBorder("CheckBox.border"));
		customerPanel.setLayout(null);
		
		cardPanel.add(customerPanel, "customerPanel");
		
		JLabel lblNewLabel_2 = new JLabel("VISA Card");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBackground(new Color(51, 255, 255));
		lblNewLabel_2.setBounds(56, 119, 129, 23);
		customerPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("152,32 Eur");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(165, 134, 129, 23);
		customerPanel.add(lblNewLabel_3);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBackground(new Color(0, 255, 255));
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(45, 112, 253, 61);
		customerPanel.add(btnNewButton_1);
		
		JLabel lblSavings = new JLabel("Savings:");
		lblSavings.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSavings.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSavings.setBounds(626, 70, 117, 23);
		customerPanel.add(lblSavings);
		
		JLabel lblNewLabel_1_1 = new JLabel("352,50 Eur");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(760, 70, 179, 23);
		customerPanel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("12 3257,25 Eur");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(453, 70, 179, 23);
		customerPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Balance:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(319, 70, 117, 23);
		customerPanel.add(lblNewLabel);
		
		JButton btnCustomerLogout = new JButton("logout");
		btnCustomerLogout.setBounds(848, 11, 100, 30);
		customerPanel.add(btnCustomerLogout);
		
		JLabel customerUserNameLbl = new JLabel("Peter Novák");
		customerUserNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		customerUserNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		customerUserNameLbl.setBounds(424, 9, 179, 30);
		customerPanel.add(customerUserNameLbl);
		
		JButton btnCustomerMessages = new JButton("messages");
		btnCustomerMessages.setBounds(628, 11, 100, 30);
		customerPanel.add(btnCustomerMessages);
		
		JButton btnCustomerSettings = new JButton("settings");
		btnCustomerSettings.setBounds(738, 11, 100, 30);
		customerPanel.add(btnCustomerSettings);
		
		JButton btnNewButton = new JButton("To Pay");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(10, 11, 139, 43);
		customerPanel.add(btnNewButton);
		
		JLabel lblNewLabel_3_1 = new JLabel("152,32 Eur");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3_1.setBounds(462, 134, 117, 23);
		customerPanel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Classic Account");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2_1.setBackground(new Color(51, 255, 255));
		lblNewLabel_2_1.setBounds(340, 118, 129, 23);
		customerPanel.add(lblNewLabel_2_1);
		
		JButton btnNewButton_1_1 = new JButton("");
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1_1.setBackground(new Color(204, 0, 153));
		btnNewButton_1_1.setBounds(330, 112, 253, 61);
		customerPanel.add(btnNewButton_1_1);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("15 152,32 Eur");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3_1_1.setBounds(736, 134, 129, 23);
		customerPanel.add(lblNewLabel_3_1_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Businees Account");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2_1_1.setBackground(new Color(51, 255, 255));
		lblNewLabel_2_1_1.setBounds(626, 119, 129, 23);
		customerPanel.add(lblNewLabel_2_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("");
		btnNewButton_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1_1_1.setBackground(new Color(0, 255, 0));
		btnNewButton_1_1_1.setBounds(616, 112, 253, 61);
		customerPanel.add(btnNewButton_1_1_1);
		
		JLabel lblForRepayment = new JLabel("For Repayment:");
		lblForRepayment.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForRepayment.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblForRepayment.setBounds(10, 70, 117, 23);
		customerPanel.add(lblForRepayment);
		
		JLabel lblNewLabel_1_2 = new JLabel("-12 3257,25 Eur");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(144, 70, 179, 23);
		customerPanel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_2_2 = new JLabel("Construction loan");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2_2.setBackground(new Color(51, 255, 255));
		lblNewLabel_2_2.setBounds(56, 207, 235, 23);
		customerPanel.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_3_2 = new JLabel("152,32 Eur");
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3_2.setBounds(165, 222, 129, 23);
		customerPanel.add(lblNewLabel_3_2);
		
		JButton btnNewButton_1_2 = new JButton("");
		btnNewButton_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1_2.setBackground(new Color(51, 51, 255));
		btnNewButton_1_2.setBounds(45, 200, 253, 61);
		customerPanel.add(btnNewButton_1_2);
		
		JButton btnLoan = new JButton("Loan");
		btnLoan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLoan.setBounds(165, 11, 139, 43);
		customerPanel.add(btnLoan);
	}
	
	private void setSizeWindow(String type) {
		if(type == "forLogin")
		{
			setBounds(0, 0, 600, 400);
		}
		else if(type == "forViewData")
		{
			setBounds(0, 0, 1024, 800);
		}
	}
	/* ******************* */
	
	private void handleUsernameChange() {
        // This method is called when the content of the usernameField changes
        String newUsername = loginUsernameField.getText();
        System.out.println("Username changed: " + newUsername);
        // Add your logic to respond to the username change here
    }
	
	/* call from Main.java */
	public void showPanel(String namePanel)
	{
		
		switch(namePanel)
		{
			case "welcomePanel":
				setSizeWindow("forLogin");	
				centerFrameOnScreen();
				cardLayout.show(cardPanel, "welcomePanel");
				break;
			case "loginPanel":
				//setSizeWindow("forLogin");	
				cardLayout.show(cardPanel, "loginPanel");
				break;
			case "registerPanel":
				//setSizeWindow("forLogin");	
				cardLayout.show(cardPanel, "registerPanel");
				break;
			case "employeerPanel":
				setSizeWindow("forViewData");	
				centerFrameOnScreen(); // center window on screen
				cardLayout.show(cardPanel, "employeerPanel");
				break;
			case "customerPanel":
				setSizeWindow("forViewData");
				centerFrameOnScreen();	// center window on screen
				cardLayout.show(cardPanel, "customerPanel");
				break;
		}

	}
	
	private void checkInputLoginRegister(String type)
	{
		
		if(type == "login")
		{
			String username = loginUsernameField.getText();
	        String password = new String(loginPasswordField.getPassword());
	        
	        Login login = new Login(); 
			String[] getError = login.checkLogin(username,password,loginChckbxEmployyer.isSelected(), this);
			
			if(getError.length > 0 )
			{
				userError.setText(getError[0]);
				passwordError.setText(getError[1]);
				loginMessage.setText(getError[2]);
			}
		}
		else if(type == "register")
		{
			String username = registerUsernameField.getText();
	        String password = new String(registerPasswordField.getPassword());
			String email = registerEmailField.getText();
			String name = registerNameFextField.getText();
			String lastname = registerLastnameTextField.getText();
	        
	        Register register = new Register(); 
			String[] getError = register.checkRegister(username,password,email,name,lastname,registerChckbxEmployeer.isSelected(),this);
			 
			if(getError.length > 0 )
			{
				userError.setText(getError[0]);
				passwordError.setText(getError[1]);
				loginMessage.setText(getError[2]);
			}
		}
		
		
	}
	
	/* ******************** */
	
	/* **************************** */
	
	/* help */
	
	private ImageIcon resizeImageIcon(ImageIcon originalIcon, int width, int height) {
        Image originalImage = originalIcon.getImage();

        // Create a BufferedImage to hold the resized image
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Create a Graphics object to draw on the BufferedImage
        Graphics2D g2d = resizedImage.createGraphics();

        // Draw the original image onto the BufferedImage, scaling it to the specified width and height
        g2d.drawImage(originalImage, 0, 0, width, height, null);

        // Dispose of the Graphics object to free up resources
        g2d.dispose();

        // Create a new ImageIcon from the resized BufferedImage
        return new ImageIcon(resizedImage);
    }
}