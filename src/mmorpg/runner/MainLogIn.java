package mmorpg.runner;

import mmorpg.game.Game;
import mmorpg.gui.LogIn;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Opens the login dialog where user can enter username and chose game
 * character.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class MainLogIn {

	public static void main(String[] args) {
		// Calling constructor of class LogIn and starting the game.
		try {
			new LogIn(new AppGameContainer(new Game("Game")));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
