package mmorpg.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import mmorpg.chat.Server;

/**
 * Represents the GUI for the server where client can start and stop the server.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class ServerGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// Declaration of parameters
	private JButton startStop;
	private JTextArea chat;
	private JTextArea events;
	private JPanel south;
	private JPanel center;

	private int port = Server.PORT;
	private Server server;

	public static boolean isRunning = false;
	
	/**
	 * Constructor
	 */
	public ServerGUI() {

		server = null;

		south = new JPanel();
		south.setForeground(Color.WHITE);
		south.setBackground(new Color(0, 128, 0));
		JLabel label = new JLabel("Default server port number: " + port);
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setForeground(Color.WHITE);
		south.add(label);

		startStop = new JButton("Start");
		startStop.setFont(new Font("Tahoma", Font.BOLD, 11));
		startStop.addActionListener(this);
		south.add(startStop);
		getContentPane().add(south, BorderLayout.SOUTH);

		// the event and chat room
		center = new JPanel(new GridLayout(2, 1));

		chat = new JTextArea(80, 80);
		chat.setForeground(Color.WHITE);
		chat.setFont(new Font("Monospaced", Font.BOLD, 13));
		chat.setBackground(new Color(0, 100, 0));
		chat.setEditable(false);
		appendRoom("Chat room.\n");
		JScrollPane scrollChat = new JScrollPane(chat,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		center.add(scrollChat);
		chat.setLineWrap(true);
		chat.setWrapStyleWord(true);

		events = new JTextArea(80, 80);
		events.setForeground(Color.WHITE);
		events.setFont(new Font("Monospaced", Font.BOLD, 13));
		events.setBackground(new Color(0, 100, 0));
		events.setEditable(false);
		appendEvent("Server event log.\n");
		JScrollPane scrollEvent = new JScrollPane(events,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		events.setLineWrap(true);
		events.setWrapStyleWord(true);
		center.add(scrollEvent);

		getContentPane().add(center);

		setTitle("Chat Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(300, 400);
		setVisible(true);
	}

	/**
	 * Append for chat room
	 * 
	 * @param str
	 */
	public void appendRoom(String str) {
		chat.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);
	}

	/**
	 * Append for event log
	 * 
	 * @param str
	 */
	public void appendEvent(String str) {
		events.append(str);
		events.setCaretPosition(chat.getText().length() - 1);
	}

	/**
	 * Server stop/start
	 */
	public void actionPerformed(ActionEvent e) {
		// if running stop
		if (server != null) {
			server.stop();
			server = null;
			startStop.setText("Start");
			return;
		}
		// create a new Server
		server = new Server(this);
		// and start it as a thread
		new ServerRunning().start();
		startStop.setText("Stop");
	}

	/**
	 * A thread to run the Server
	 */
	class ServerRunning extends Thread {

		public void run() {
			isRunning = true;

			server.start();
			// if server stops running append event msg and set server to null
			appendEvent("Server crashed.\n");
			startStop.setText("Start");
			server = null;
		}
	}
}
