package mmorpg.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import mmorpg.chat.Client;
import mmorpg.chat.Server;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Represents the login frame where client can enter username and select the
 * game character. After clicking the login button client starts the game, if
 * the server is started.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class LogIn implements ActionListener {

	// Declaration of parameters.
	private JFrame frame = new JFrame();
	private JTextField txtUsername;

	private JButton left = new JButton("<");
	private JButton right = new JButton(">");
	private JButton logIn = new JButton("LOGIN");
	private JPanel panel = new JPanel();

	private static String username;
	public static String type;
	private ClientGUI clientGUI;

	private static ArrayList<String> chars = new ArrayList<String>(10);
	public static JTextField charName = new JTextField();

	private StartGame startGame;
	private final JLabel label = new JLabel("New label");

	/**
	 * Create the application.
	 */
	public LogIn(AppGameContainer appgc) {
		startGame = new StartGame(appgc);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		addValuesToArrayList();

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 100, 0));
		frame.getContentPane().setFont(new Font("Snap ITC", Font.BOLD, 15));
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(100, 100, 350, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.EAST, right, -57,
				SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, left, 39,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, logIn, 6,
				SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, logIn, -10,
				SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, right, 154,
				SpringLayout.EAST, left);
		springLayout.putConstraint(SpringLayout.EAST, left, -6,
				SpringLayout.WEST, panel);
		springLayout.putConstraint(SpringLayout.NORTH, left, 107,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, left, -84,
				SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, right, 107,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, right, -84,
				SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 95,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -107,
				SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, logIn, 115,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, logIn, -126,
				SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		left.setForeground(Color.BLACK);
		left.setBackground(new Color(0, 100, 0));
		frame.getContentPane().add(left);
		left.setFont(new Font("Snap ITC", Font.PLAIN, 15));
		panel.setBackground(new Color(34, 139, 34));

		panel.setBorder(BorderFactory.createBevelBorder(0));
		springLayout.putConstraint(SpringLayout.NORTH, panel, 58,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -60,
				SpringLayout.SOUTH, frame.getContentPane());

		frame.getContentPane().add(panel);
		right.setForeground(Color.BLACK);
		right.setBackground(new Color(0, 100, 0));
		right.setFont(new Font("Snap ITC", Font.PLAIN, 15));
		frame.getContentPane().add(right);

		txtUsername = new JTextField();
		txtUsername.setForeground(Color.BLACK);
		springLayout.putConstraint(SpringLayout.NORTH, txtUsername, 21,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtUsername, -6,
				SpringLayout.NORTH, panel);
		txtUsername.setFont(new Font("Arial Black", Font.PLAIN, 13));
		springLayout.putConstraint(SpringLayout.WEST, txtUsername, 111,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtUsername, 0,
				SpringLayout.EAST, logIn);
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setText("username");

		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		panel.setLayout(null);

		charName = new JTextField();
		charName.setForeground(Color.BLACK);
		charName.setBackground(new Color(34, 139, 34));
		charName.setBounds(10, 7, 121, 24);
		charName.setFont(new Font("Snap ITC", Font.PLAIN, 14));
		charName.setHorizontalAlignment(SwingConstants.CENTER);
		charName.setEditable(false);

		charName.setText(chars.get(0));

		panel.add(charName);
		charName.setColumns(10);
		label.setBackground(new Color(34, 139, 34));
		label.setBounds(38, 60, 64, 96);

		//label.setBounds(51, 83, 36, 48); // size for little images
		
		panel.add(label);
		logIn.setForeground(Color.BLACK);
		logIn.setFont(new Font("Arial Black", Font.PLAIN, 12));
		label.setIcon(new ImageIcon("res/" + chars.get(0) + "/" + chars.get(0)
				+ ".png"));

		panel.add(label);

		frame.getContentPane().add(logIn);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// add listeners
		logIn.addActionListener(this);
		txtUsername.addActionListener(this);
		left.addActionListener(this);
		right.addActionListener(this);
	}

	/**
	 * Action performed
	 * <p>
	 * Functionality for any of buttons on chat pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		type = charName.getText();
		if (o == logIn) {
			System.out.println(ServerGUI.isRunning);
			username = txtUsername.getText().trim();
			if (username.equals("")) {
				return;
			} else {
				clientGUI = new ClientGUI(username, type);
				ClientGUI.client = new Client(Server.SERVER, username,
						clientGUI);
				// test if we can start the Client
				if (!ClientGUI.client.start()) {
					return;
				}
				// Starting the thread that starts the game.
				startGame.start();
				frame.dispose();
			}
		}

		ClientGUI.connected = true;

		if (o == left) {
			String tmp = charName.getText();
			for (int i = 0; i < chars.size() - 1; i++) {
				if (tmp.equals(chars.get(9))) {
					charName.setText(chars.get(0));
					label.setIcon(new ImageIcon("res/" + chars.get(0) + "/"
							+ chars.get(0) + ".png"));
				} else if (tmp.equals(chars.get(i))) {
					charName.setText(chars.get(i + 1));
					label.setIcon(new ImageIcon("res/" + chars.get(i + 1) + "/"
							+ chars.get(i + 1) + ".png"));
				}
			}
			type = charName.getText();
		}

		if (o == right) {
			String tmp = charName.getText();
			for (int i = chars.size() - 1; i > 0; i--) {
				if (tmp.equals(chars.get(0))) {
					charName.setText(chars.get(9));
					label.setIcon(new ImageIcon("res/" + chars.get(9) + "/"
							+ chars.get(9) + ".png"));
				} else if (tmp.equals(chars.get(i))) {
					charName.setText(chars.get(i - 1));
					label.setIcon(new ImageIcon("res/" + chars.get(i - 1) + "/"
							+ chars.get(i - 1) + ".png"));
				}
			}
			type = charName.getText();
		}

	}

	/**
	 * Inserting into the array names of the game characters.
	 */
	private static void addValuesToArrayList() {
		chars.add("Queen");
		chars.add("King");
		chars.add("Pirate");
		chars.add("Monk");
		chars.add("Boy");
		chars.add("Girl");
		chars.add("Lady");
		chars.add("Man");
		chars.add("Granny");
		chars.add("Grandpa");
	}

	/**
	 * Represents the thread that starts the main game frame. In main frame
	 * client can play with selected character.
	 * 
	 * @author Adis Cehajic & Amra Sabic
	 *
	 */
	private class StartGame extends Thread {
		// Declaring the frame.
		private AppGameContainer appgc;

		public StartGame(AppGameContainer appgc) {
			this.appgc = appgc;
		}

		public void run() {
			try {
				// Setting the frame properties and starting the frame.
				appgc.setDisplayMode(800, 600, false);
				appgc.setShowFPS(false);
				appgc.start();
			} catch (SlickException e1) {
				e1.printStackTrace();
			}
		}
	}
}
