package mmorpg.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mmorpg.gui.ServerGUI;

/**
 * Represents server socket that enables client socket to connect. When
 * connected client can play the game and chat with other clients that are
 * connected on the server.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class Server {
	// Declaration of constants
	public static final int PORT = 1902;
	public static final String SERVER = "localhost";
	// Declaration of parameters
	private static int uniqueId;
	private ArrayList<Clients> clients;

	private ServerGUI serverGUI;
	private SimpleDateFormat dateFormat;
	private int port = Server.PORT;

	private boolean keepRunning;

	/**
	 * Constructor
	 * 
	 * @param port
	 * @param serverGUI
	 */
	public Server(ServerGUI serverGUI) {
		this.serverGUI = serverGUI;
		this.port = PORT;
		dateFormat = new SimpleDateFormat("HH:mm:ss");
		clients = new ArrayList<Clients>();
	}

	/**
	 * Method to start Server
	 */
	public void start() {
		keepRunning = true;
		try {
			ServerSocket serverSocket = new ServerSocket(port);

			while (keepRunning) {
				display("Server waiting for Clients on port " + port + ".");
				Socket socket = serverSocket.accept();

				if (!keepRunning) {
					break;
				}
				Clients c = new Clients(socket);
				clients.add(c);
				c.start();
			}
			try {
				serverSocket.close();
				for (int i = 0; i < clients.size(); ++i) {
					Clients tc = clients.get(i);
					try {
						tc.reader.close();
						tc.writer.close();
						tc.socket.close();
					} catch (IOException e) {
						display("Failed to close.." + e);
					}
				}
			} catch (Exception e) {
				String msg = dateFormat.format(new Date())
						+ " Failed to close..\n";
				display(msg);
			}
		} catch (IOException e) {
			String msg = dateFormat.format(new Date())
					+ " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}

	/**
	 * Stop Server method for GUI
	 */
	public void stop() {
		keepRunning = false;
		try {
			new Socket("localhost", port);
		} catch (Exception e) {
			System.out.println("Failed to stop server GUI." + e);
		}
	}

	/**
	 * Display an event (not a message) to the GUI
	 */
	private void display(String msg) {
		String time = dateFormat.format(new Date()) + " " + msg;
		serverGUI.appendEvent(time + "\n");
	}

	/**
	 * Broadcast
	 * <p>
	 * Send message to all Clients.
	 * 
	 * @param message
	 */
	private synchronized void broadcast(String message) {

		String time = dateFormat.format(new Date());
		// add time and new line (\n) to the message
		String messageLf = time + " " + message + "\n";
		serverGUI.appendRoom(messageLf); // append in the room window

		for (int i = clients.size(); --i >= 0;) {
			Clients ct = clients.get(i);
			// try to write to the Client if it fails remove it from the list
			if (!ct.writeMsg(messageLf)) {
				clients.remove(i);
				display("Disconnected Client " + ct.username
						+ " removed from list.");
			}
		}
	}

	/**
	 * Remove client
	 * <p>
	 * for a client who log off using the LOGOUT message
	 * 
	 * @param id
	 */
	synchronized void remove(int id) {
		for (int i = 0; i < clients.size(); ++i) {
			Clients ct = clients.get(i);
			if (ct.id == id) {
				clients.remove(i);
				return;
			}
		}
	}

	/**
	 * Thread for each client
	 * 
	 * @author Adis Cehajic & Amra Sabic
	 */
	private class Clients extends Thread {

		private Socket socket;
		private ObjectInputStream reader;
		private ObjectOutputStream writer;
		// unique ID
		private int id;
		private String username;
		private Message msg;
		private String date;

		/**
		 * Constructor
		 * 
		 * @param socket
		 */
		Clients(Socket socket) {
			id = ++uniqueId;
			this.socket = socket;
			System.out.println("Create client thread.");
			try {
				writer = new ObjectOutputStream(socket.getOutputStream());
				reader = new ObjectInputStream(socket.getInputStream());
				// read the user name
				username = (String) reader.readObject();
				display(username + " just connected.");
			} catch (IOException e) {
				display("Failed to create new stream: " + e);
				return;
			} catch (ClassNotFoundException e) {
				System.out.println("Should not happen with sending String.");
			}
			date = new Date().toString() + "\n";
		}

		/**
		 * Run server
		 */
		public void run() {
			boolean keepGoing = true;
			// while logged in
			while (keepGoing) {
				try {
					msg = (Message) reader.readObject();
				} catch (IOException e) {
					display(username + " Exception reading message: " + e);
					break;
				} catch (ClassNotFoundException e2) {
					break;
				}
				// the message part of the ChatMessage
				String message = msg.getMessage();
				broadcast(username + ": " + message);
			}
			// remove this Client from the list of connected Clients
			remove(id);
			close();
		}

		/**
		 * Close everything
		 */
		private void close() {
			// try to close the connection
			try {
				if (writer != null) {
					writer.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {
				display("Failed to close.. \n");
			}
		}

		/*
		 * Write a String to the Client output stream
		 */
		private boolean writeMsg(String msg) {
			// if Client is still connected send the message to it
			if (!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				writer.writeObject(msg);
			} catch (IOException e) {
				display("Failed to send message to " + username);
				display(e.toString());
			}
			return true;
		}
	}

}
