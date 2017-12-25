package com.sqli.test.romanrunner;

public interface Player {
	public void startGame(Circenses circenses);
	public Player forward() throws ObstacleHitedException;
	public int score();
	public Player right();
	public Player left();
}
