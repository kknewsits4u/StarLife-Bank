package com.starLife.in.helper;

public class Message {
	
	private String type;
	private String message;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Message(String type, String message) {
		super();
		this.type = type;
		this.message = message;
	}
	public Message() {
		super();
		
	}
	@Override
	public String toString() {
		return "Message [type=" + type + ", message=" + message + "]";
	}
	
	

}
