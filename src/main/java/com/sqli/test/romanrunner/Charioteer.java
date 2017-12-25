package com.sqli.test.romanrunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.rules.ExpectedException;

public class Charioteer implements Player{
	String name;
	Circenses myCircenses;
	int position = 0;
	int score = 0;
	Map<Integer,String> coinPositions = new HashMap<Integer, String>();
	String direction = "left";
	boolean changedDirection = false;
	boolean dead = false;
	int deathPosition = 0;

	public int getDeathPosition() {
		return deathPosition;
	}

	public void setDeathPosition(int deathPosition) {
		this.deathPosition = deathPosition;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Charioteer(String name) {
		super();
		this.name = name;
	}
	public Circenses getMyCircenses() {
		return myCircenses;
	}

	public void setMyCircenses(Circenses myCircenses) {
		this.myCircenses = myCircenses;
	}

	@Override
	public void startGame(Circenses circenses) {
		setMyCircenses(circenses);
		//setting the name of the player in the beginning
		if(direction == "left") {
			myCircenses.getPath().set(0, "|"+name.toUpperCase().charAt(0)+" |");
		}else {
			myCircenses.getPath().set(0, "| "+name.toUpperCase().charAt(0)+"|");
		}
	}
	

	@Override
	public Charioteer forward() throws ObstacleHitedException{
		//if the player already earned some coins we mark an x in the path at the position of that coin.
		if(!coinPositions.isEmpty()) {
			
			coinPositions.forEach((pos,dir) ->{
				if(dir == "left") {
					myCircenses.getPath().set(pos, "|x |");
				}else{
					myCircenses.getPath().set(pos, "| x|");
				}
			});
		}
		
		
		//we mark the beginning of the path and 
		if(!changedDirection) {
			if(position == 0) {
				StringBuffer strbuffer = new StringBuffer(myCircenses.getPath().get(0));
				if(direction == "left") {
					strbuffer.replace(1, 2,"@");
				}else {
					strbuffer.replace(2, 3,"@");
				}
				myCircenses.path.set(0,strbuffer.toString());
			}
		}
		
		
		//moving forward 
		position++;
		StringBuffer strbuffer = new StringBuffer(myCircenses.getPath().get(position));
		
		//if we counter a coin the score levels up by 10 points and we store the position of the coin
		if(strbuffer.subSequence(1, 2).equals("o")) {
			score+=10;
			coinPositions.put(position,"left");
		}else if(strbuffer.subSequence(2, 3).equals("o")) {
			coinPositions.put(position,"right");
			score+=10;
		}
		
		//if we counter an obstacle we kill the player and we decrease 5 points from the score
		if(strbuffer.subSequence(1, 2).equals("_")) {
			strbuffer.replace(1, 2,"D");
			dead = true;
			score -= 5;
			myCircenses.getPath().set(position,strbuffer.toString());
			deathPosition = position;
		}else if(strbuffer.subSequence(2, 3).equals("_")) {
			strbuffer.replace(2, 3,"D");
			dead = true;
			score -= 5;
			myCircenses.getPath().set(position,strbuffer.toString());
			deathPosition = position;
		}
		
		
		if(!dead) {
			//we move the player to the next position
			if(direction == "left") {
				strbuffer.replace(1, 2, String.valueOf(name.toUpperCase().charAt(0)));
			}else {
				strbuffer.replace(2, 3, String.valueOf(name.toUpperCase().charAt(0)));
			}
			myCircenses.getPath().set(position,strbuffer.toString());
		}else {
			//if the player is and tries to move we throw an exception
			if(position > deathPosition) {
				throw new ObstacleHitedException("player is dead! TEST 10");
			}
		}

	
		//deleting the previous trace of the player
		deletePreviousTrace();
		

		
		//adding 100 points if he reaches the finish line
		if(myCircenses.getPath().get(position).equals("|"+name.toUpperCase().charAt(0)+"#|") || myCircenses.getPath().get(position).equals("|#"+name.toUpperCase().charAt(0)+"|") ) {
			//System.out.println(myCircenses.getPath().get(position));
			this.score += 100;
		}
		
		return this;
	}

	@Override
	public int score() {
		return score;
	}

	@Override
	public Charioteer right() {
		direction = "right";
		changedDirection = true;
		if(position == 0) {
			myCircenses.path.set(0,"|@"+name.toUpperCase().charAt(0)+"|");
		}else {
			 StringBuffer strbuffer = new StringBuffer(myCircenses.getPath().get(position));
			 strbuffer.replace(2, 3,String.valueOf(name.toUpperCase().charAt(0)));
			 myCircenses.getPath().set(position,strbuffer.toString());
			 //clearing the previous trace 
			 strbuffer = new StringBuffer(myCircenses.getPath().get(position));
			 strbuffer.replace(1, 2," ");
			 myCircenses.getPath().set(position,strbuffer.toString());
		}
		
		return this;
	}

	@Override
	public Charioteer left() {
		direction = "left";
		changedDirection = true;
		if(position == 0) {
			myCircenses.path.set(0,"|"+name.toUpperCase().charAt(0)+"@|");
		}else {
			StringBuffer strbuffer = new StringBuffer(myCircenses.getPath().get(position));
			strbuffer.replace(1, 2,String.valueOf(name.toUpperCase().charAt(0)));
			myCircenses.getPath().set(position,strbuffer.toString());
			//clearing the previous trace 
			strbuffer = new StringBuffer(myCircenses.getPath().get(position));
			 strbuffer.replace(2, 3," ");
			myCircenses.getPath().set(position,strbuffer.toString());
		}
		return this;
	}
	
	public void deletePreviousTrace() {
		StringBuffer strbuffer;
		if(direction == "left") {
			if(myCircenses.getPath().get(position -1).matches("(.)"+name.toUpperCase().charAt(0)+"(..)")) {
				 strbuffer = new StringBuffer(myCircenses.getPath().get(position -1));
				 strbuffer.replace(1, 2," ");
				 myCircenses.getPath().set(position -1,strbuffer.toString());
			}
		}else {
			if(myCircenses.getPath().get(position -1).matches("(..)"+name.toUpperCase().charAt(0)+"(.)")) {
				 strbuffer = new StringBuffer(myCircenses.getPath().get(position -1));
				 strbuffer.replace(2, 3," ");
				 myCircenses.getPath().set(position -1,strbuffer.toString());
			}
		}
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}



	public Map<Integer, String> getCoinPositions() {
		return coinPositions;
	}

	public void setCoinPositions(Map<Integer, String> coinPositions) {
		this.coinPositions = coinPositions;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public boolean isChangedDirection() {
		return changedDirection;
	}

	public void setChangedDirection(boolean changedDirection) {
		this.changedDirection = changedDirection;
	}
}
