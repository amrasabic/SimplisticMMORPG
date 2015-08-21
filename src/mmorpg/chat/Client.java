package mmorpg.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mmorpg.gui.ClientGUI;

/**
 * Represents the client socket that connects to the server. When the client is
 * connected on server socket, client can play the game and chat with another
 * clients that are connected on the server.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class Client {

	// Declaring properties of the client.
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket socket;
	private ClientGUI clientGUI;

	private String server;
	private String username;
	private int port;

	/**
	 * Constructor that represents client.
	 * 
	 * @param server
	 * @param username
	 * @param cg
	 */
	public Client(String server, String username, ClientGUI cg) {
		this.server = server;
		this.port = Server.PORT;
		this.username = username;
		this.clientGUI = cg;
	}

	/**
	 * Start dialog
	 * 
	 * @return - boolean value true if connection succeeded else returns false
	 */
	public boolean start() {

		try {
			socket = new Socket(server, port);
		} catch (Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}
		// display message on chat (text area) that connection succeeded
		String msg = "Connection accepted " + socket.getInetAddress() + ":"
				+ socket.getPort();
		display(msg);

		try {
			reader = new ObjectInputStream(socket.getInputStream());
			writer = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// creates the Thread to listen from the server
		new ListenFromServer().start();

		try {
			writer.writeObject(username);
		} catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	/**
	 * Display
	 * <p>
	 * Write message to chat area.
	 * 
	 * @param msg
	 *            - String value message
	 */
	private void display(String msg) {
		clientGUI.append(msg + "\n");
	}

	/**
	 * Send message
	 * <p>
	 * Send message to server.
	 * 
	 * @param msg
	 *            - String value message
	 */
	public void sendMessage(Message msg) {
		try {
			writer.writeObject(msg);
		} catch (IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	/**
	 * Disconnect
	 * <p>
	 * Close reader, writer and socket.
	 */
	private void disconnect() {
		try {
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			System.out.println("Disconnect on client side failed.. "
					+ "Check method disconnect.");
			display("Disconnect failed.. ");
		}

	}

	/**
	 * Listen from Server
	 * <p>
	 * Listen for message from server and append it to chat area.
	 * 
	 * @author Adis Cehajic & Amra Sabic
	 *
	 */
	class ListenFromServer extends Thread {

		public void run() {
			while (true) {
				try {
					String msg = (String) reader.readObject();
					clientGUI.append(msg);
				} catch (IOException e) {
					display("Server has close the connection: " + e);
				} catch (ClassNotFoundException e2) {
					System.out.println("Should not happen with strings, check reader. What has been sent?");
					display("Not supposed to happen. Stream reader not working right. " + e2);
				}
			}
		}
	}
}