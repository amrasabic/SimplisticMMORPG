package mmorpg.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mmorpg.chat.Client;
import mmorpg.chat.Message;
import mmorpg.game.Play;


/**
 * Represents GUI for the chat that contains textfield where client can enter
 * message and sent it to all clients that are connected on the server.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class ClientGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	// Declaration of parameters
	private JLabel label;
	private JTextField field;
	private JTextArea area;
	private JPanel chatGUI;
	private JPanel southPanel;
	private JPanel centerPanel;
	private JScrollPane scrollChat;
	
	public static Client client;
	public static String username;
	public static boolean connected;

	public static String typeOfCharacter;

	/**
	 * Constructor
	 * @param username
	 * @param typeOfCharacter
	 */
	public ClientGUI(String username, String typeOfCharacter) {

		this.typeOfCharacter = typeOfCharacter;
		this.username = username;
		getContentPane().setLayout(new BorderLayout());

		chatGUI = new JPanel();
		chatGUI.setLayout(new BorderLayout());
		southPanel = new JPanel(new GridLayout(3, 1));
		southPanel.setBackground(new Color(0, 100, 0));
		southPanel.setForeground(Color.BLACK);

		label = new JLabel("Enter your message: ", SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		southPanel.add(label);
		
		field = new JTextField();
		field.setText("");
		field.setEditable(true);
		field.setBackground(Color.WHITE);
		field.addActionListener(this);
		southPanel.add(field);

		chatGUI.add(southPanel, BorderLayout.SOUTH);

		centerPanel = new JPanel(new GridLayout(1, 1));
		area = new JTextArea("Simplistic MMORPG Chat \n", 25, 25);
		area.setFont(new Font("Monospaced", Font.BOLD, 13));
		area.setForeground(Color.WHITE);
		area.setBackground(new Color(34, 139, 34));
		area.setEditable(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		
		scrollChat = new JScrollPane(area,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		centerPanel.add(scrollChat);

		chatGUI.add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(chatGUI, BorderLayout.CENTER);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(300, 627);
		setLocation(((Play.getScreenWorkingWidth() - 800) / 2) + 815, 150);
		setResizable(false);
		setVisible(true);
		
		field.requestFocus();
	}
	
	/**
	 * Append text to text area by client
	 * @param str
	 */
	public void append(String str) {
		area.append(str);
		area.setCaretPosition(area.getText().length() - 1);
	}

	/**
	 * Connection failed
	 * <p>
	 * When connection fails, reset GUI interface.
	 */
	public void connectionFailed() {

		label.setText("Enter your message: ");
		field.setText("");
		// reset port number and host name as a construction time
		// don't react to a <CR> after the user name
		field.removeActionListener(this);
		connected = false;
	}

	/**
	 * Action performed
	 * <p>
	 * Functionality for chat text field.
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(connected) {
			client.sendMessage(new Message(Message.MESSAGE, field.getText()));				
			field.setText("");
			return;
		}
	}
}
