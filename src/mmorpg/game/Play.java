package mmorpg.game;

import mmorpg.gui.ClientGUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Represents second panel that contains map and the players.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class Play extends BasicGameState {

	// Declaring static variables for position of the player.
	public static float positionX = getRandomPosition(-1590);
	public static float positionY = getRandomPosition(-1750);

	// Declaring variables.
	private Image worldMap;
	private boolean quit = false;

	private float shiftX = 400;
	private float shiftY = 300;

	private Player player;

	/**
	 * Constructor.
	 * 
	 * @param state
	 */
	public Play(int state) {
	}

	/**
	 * Adding map and player on panel.
	 */
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		player = new Player(ClientGUI.typeOfCharacter);
		worldMap = new Image("res/world.png");
		player.drawMan();
	}

	/**
	 * Drawing map and player on the map.
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setBackground(Color.blue);
		worldMap.draw(positionX, positionY);
		g.drawString(ClientGUI.username,
				(shiftX - (ClientGUI.username.length() * 2)), shiftY - 25);
		player.getMan().draw(shiftX, shiftY);

		// Calling method quit.
		quit(g, quit);
	}

	/**
	 * Returns the width of the screen.
	 * 
	 * @return
	 */
	public static int getScreenWorkingWidth() {
		return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds().width;
	}

	/**
	 * Enables to stop and quit the game.
	 * 
	 * @param g
	 * @param quit
	 */
	public void quit(Graphics g, boolean quit) {
		if (quit) {
			g.drawString("Resume (R)", 375, 150);
			g.drawString("Quit Game (Q)", 365, 200);
			if (!quit) {
				g.clear();
			}
		}
	}

	/**
	 * Updating the frame.
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();

		// Calling method moving that moves player.
		if (!quit) {
			player.moving(gc, delta);
		}

		actionEscapeKey(input);

		quit = pauseMenu(sbg, input, quit);
	}

	/**
	 * Sets action on ESC key. If the ESC key is pressed it opens menu with
	 * options to resume game, quit game or to go to main menu.
	 * 
	 * @param input
	 */
	public void actionEscapeKey(Input input) {
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			quit = true;
		}
	}

	/**
	 * Sets action on R, M and Q keys. If the R key is pressed it resumes the
	 * game, if the Q key is pressed it quits the game and if the M key is
	 * pressed it opens main panel.
	 * 
	 * @param sbg
	 * @param input
	 * @param quit
	 * @return
	 */
	public boolean pauseMenu(StateBasedGame sbg, Input input, boolean quit) {
		if (quit) {
			if (input.isKeyDown(Input.KEY_R)) {
				quit = false;
			}
			if (input.isKeyDown(Input.KEY_Q)) {
				System.exit(0);
			}
		}
		return quit;
	}

	/**
	 * Returns random number in inputed range.
	 * 
	 * @param number
	 *            - Range.
	 * @return
	 */
	public static float getRandomPosition(int number) {
		return (float) Math.random() * (number);
	}

	/**
	 * Returns the id of the panel.
	 */
	public int getID() {
		return 1;
	}
}
