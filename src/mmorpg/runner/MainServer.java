package mmorpg.runner;

import mmorpg.gui.ServerGUI;

/**
 * Enables user to start and to stop the server. If the server is started user
 * can play the game.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class MainServer {

	public static void main(String[] arg) {
		// Calling constructor of class ServerGUI and starting the server.
		new ServerGUI();
	}
}
