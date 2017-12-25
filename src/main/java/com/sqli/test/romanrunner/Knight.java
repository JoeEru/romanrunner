package com.sqli.test.romanrunner;

import java.util.HashMap;
import java.util.Map;

public class Knight implements Player{
	
	String name;
	Circenses myCircenses;
	int position = 0;
	int score = 0;
	Map<Integer,String> coinPositions = new HashMap<Integer, String>();
	String direction = "left";
	boolean changedDirection = false;
	boolean hitAnobstalce = false;
	

	public Circenses getMyCircenses() {
		return myCircenses;
	}

	public void setMyCircenses(Circenses myCircenses) {
		this.myCircenses = myCircenses;
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




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Knight(String name) {
		super();
		this.name = name;
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
	public Player forward() throws ObstacleHitedException {
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
		
		//if we counter a coin the score levels up by 20 points and we store the position of the coin
		if(strbuffer.subSequence(1, 2).equals("o")) {
			score+=20;
			coinPositions.put(position,"left");
		}else if(strbuffer.subSequence(2, 3).equals("o")) {
			coinPositions.put(position,"right");
			score+=20;
		}
		
		//if we counter an obstacle we decrease 10 points from the score
		if(strbuffer.subSequence(1, 2).equals("_") ) {
			score -= 10;
			hitAnobstalce = true;
		}else if(strbuffer.subSequence(2, 3).equals("_") ) {
			score -=10;
			hitAnobstalce = true;
//			strbuffer.replace(1, 2, " ");
//			myCircenses.getPath().set(position,strbuffer.toString());
		}
		
		

		//we move the player to the next position
		if(direction == "left") {
			strbuffer.replace(1, 2, String.valueOf(name.toUpperCase().charAt(0)));
		}else {
			strbuffer.replace(2, 3, String.valueOf(name.toUpperCase().charAt(0)));
		}
		myCircenses.getPath().set(position,strbuffer.toString());
		

	
		//deleting the previous trace of the player
		deletePreviousTrace();
		

		
		//adding 100 points if he reaches the finish line
		if(myCircenses.getPath().get(position).equals("|"+name.toUpperCase().charAt(0)+"#|") || myCircenses.getPath().get(position).equals("|#"+name.toUpperCase().charAt(0)+"|") ) {
			//System.out.println(myCircenses.getPath().get(position));
			this.score += 100;
		}
		
		//System.out.println("the score for the knight player is = " + score);
		
		return this;
	}

	@Override
	public int score() {
		// TODO Auto-generated method stub
		return score;
	}

	@Override
	public Knight right() {
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
	public Knight left() {
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
				 //System.out.println("previous slot : " + myCircenses.getPath().get(position -1));
				 if(hitAnobstalce) {
					 strbuffer.replace(1, 2,"_");
				 }else {
					 strbuffer.replace(1, 2," ");
				 }
				 myCircenses.getPath().set(position -1,strbuffer.toString());
			}
		}else {
			if(myCircenses.getPath().get(position -1).matches("(..)"+name.toUpperCase().charAt(0)+"(.)")) {
				 strbuffer = new StringBuffer(myCircenses.getPath().get(position -1));
				 if(hitAnobstalce) {
					 strbuffer.replace(2, 3,"_");
				 }else {
					 strbuffer.replace(1, 2," ");
				 }
				 myCircenses.getPath().set(position -1,strbuffer.toString());
			}
		}
	}

}
