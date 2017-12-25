package com.sqli.test.romanrunner;

public class ObstacleHitedException extends Exception{
	String message;

	public ObstacleHitedException(String message) {
		super();
		this.message = message;
		System.err.println(message);
	}
	
}
