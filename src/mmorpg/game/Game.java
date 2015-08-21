package mmorpg.game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Creates the states - panels on frame and sets menu panel on first position.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class Game extends StateBasedGame {

	// Declaring constants for the state.
	public static final int PLAY = 1;

	/**
	 * Constructor that adds states on frame.
	 * 
	 * @param gameName - Title of the game.
	 */
	public Game(String gameName) {
		super(gameName);
		this.addState(new Play(PLAY));
	}

	/**
	 * Creating states and entering into first state.
	 */
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(PLAY).init(gc, this);
		this.enterState(PLAY);
	}
}
