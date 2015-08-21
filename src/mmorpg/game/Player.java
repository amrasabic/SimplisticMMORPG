package mmorpg.game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Represents the player that plays the game. Player can move through the map
 * and chat with other players that are on the same map.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class Player {

	// Declaring variables.
	private SpriteSheet manUp;
	private SpriteSheet manDown;
	private SpriteSheet manLeft;
	private SpriteSheet manRight;
	private Animation man;
	private Animation movingUp;
	private Animation movingDown;
	private Animation movingLeft;
	private Animation movingRight;

	// Declaring string variable that contains name of the character.
	private String path;

	/**
	 * Constructor that has only path variable.
	 * 
	 * @param path
	 */
	public Player(String path) {
		this.path = path;
	}

	/**
	 * Enables player to move through the map. Player uses LEFT, RIGHT, UP and
	 * DOWN keys to move. If the keys are pressed the animation for that key is
	 * started.
	 * 
	 * @param gc
	 * @param delta
	 */
	public void moving(GameContainer gc, int delta) {
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_UP)) {
			man = movingUp;
			movingUp.start();
			Play.positionY += delta * .1f;
			if (Play.positionY > -40) {
				Play.positionY -= delta * .1f;
			}
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			man = movingDown;
			movingDown.start();
			Play.positionY -= delta * .1f;
			if (Play.positionY < -1750) {
				Play.positionY += delta * .1f;
			}
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			man = movingLeft;
			movingLeft.start();
			Play.positionX += delta * .1f;
			if (Play.positionX > 20) {
				Play.positionX -= delta * .1f;
			}
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			man = movingRight;
			movingRight.start();
			Play.positionX -= delta * .1f;
			if (Play.positionX < -1590) {
				Play.positionX += delta * .1f;
			}
		} else {
			movingUp.stop();
			movingDown.stop();
			movingLeft.stop();
			movingRight.stop();
		}
	}

	/**
	 * Draws player and creates the animation of the player for each direction.
	 * 
	 * @throws SlickException
	 */
	public void drawMan() throws SlickException {
		manUp = new SpriteSheet("res/" + path + "/" + path + "Up.png", 32, 48);
		movingUp = new Animation(manUp, 150);
		movingUp.stop();
		manDown = new SpriteSheet("res/" + path + "/" + path + "Down.png", 32,
				48);
		movingDown = new Animation(manDown, 150);
		movingDown.stop();
		manLeft = new SpriteSheet("res/" + path + "/" + path + "Left.png", 32,
				48);
		movingLeft = new Animation(manLeft, 150);
		manRight = new SpriteSheet("res/" + path + "/" + path + "Right.png",
				32, 48);
		movingRight = new Animation(manRight, 150);

		man = movingDown;
	}

	/**
	 * Returns the animation of the player.
	 * 
	 * @return - Animation of the player.
	 */
	public Animation getMan() {
		return man;
	}

	/**
	 * Returns the path of the player image.
	 * 
	 * @return
	 */
	public String getPath() {
		return path;
	}
}
