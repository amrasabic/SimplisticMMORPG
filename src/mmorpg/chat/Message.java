package mmorpg.chat;

import java.io.*;

/**
 * Represents the message that user sends trough the chat to the another user.
 * The message contains two parameters. First is the type of the message and the
 * other is the content of the message.
 * 
 * @author Adis Cehajic & Amra Sabic
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -3946784058689643681L;

	// Declaration of constant for the type of message.
	public static final int MESSAGE = 0;

	// Declaration of message properties.
	private int type;
	private String message;

	/**
	 * Constructor for creating object message.
	 * 
	 * @param type - Type of the message.
	 * @param message - Message content.
	 */
	public Message(int type, String message) {
		this.type = type;
		this.message = message;
	}

	/*
	 * Get methods.
	 */
	public int getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}
}
