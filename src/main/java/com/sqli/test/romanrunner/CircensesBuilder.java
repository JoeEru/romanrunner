package com.sqli.test.romanrunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircensesBuilder {
	List<String> path = new ArrayList<>();
	String direction = "left";
	boolean fillBothTracks = false;
	
	
	public boolean isFillBothTracks() {
		return fillBothTracks;
	}
	public void setFillBothTracks(boolean fillBothTracks) {
		this.fillBothTracks = fillBothTracks;
	}
	public List<String> getPath() {
		return path;
	}
	public void setPath(List<String> path) {
		this.path = path;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public CircensesBuilder() {
		path.add("|  |");
	}
    public CircensesBuilder addCoin() {
    	
    	if(!fillBothTracks) {
    		if(direction == "left") {
    			path.add("|o |");
    		}else {
    			path.add("| o|");
    		}
    	}else {
    		if(direction == "left") {
    			StringBuffer strbuffer = new StringBuffer(path.get(path.size()-1));
    			strbuffer.replace(1, 2, "o");
    			path.set(path.size()-1,strbuffer.toString());
    			//System.out.println("changed left "+path.get(path.size()-1));
    		}else {
    			StringBuffer strbuffer = new StringBuffer(path.get(path.size()-1));
    			strbuffer.replace(2, 3, "o");
    			//System.out.println("before changed right "+path.get(path.size()-1));
    			path.set(path.size()-1,strbuffer.toString());
    			//System.out.println("changed right "+path.get(path.size()-1));
    		}
    		fillBothTracks = false;
    	}
    	

        return this;
    }

    public CircensesBuilder addEmptySlot() {
    	path.add("|  |");
        return this;
    }

    public CircensesBuilder addObstacle() {
    	if(!fillBothTracks) {
    		if(direction == "left") {
    			path.add("|_ |");
    		}else {
    			path.add("| _|");
    		}
    	}else{
    		if(direction == "left") {
    			StringBuffer strbuffer = new StringBuffer(path.get(path.size()-1));
    			strbuffer.replace(1, 2, "_");
    			path.set(path.size()-1,strbuffer.toString());
    			//System.out.println("changed left "+path.get(path.size()-1));
    		}else {
    			StringBuffer strbuffer = new StringBuffer(path.get(path.size()-1));
    			strbuffer.replace(2, 3, "_");
    			path.set(path.size()-1,strbuffer.toString());
    			//System.out.println("changed right "+path.get(path.size()-1));
    		}
    		fillBothTracks = false;
    	}
    	
    	
        return this;
    }

    public Circenses build() {
        path.add("|##|");
        //Collections.reverse(path);
        return new Circenses(path);
    }

    public CircensesBuilder right() {
        fillBothTracks = true;
    	direction = "right";
        return this;
    }

    public CircensesBuilder left() {
    	fillBothTracks = true;
    	direction = "left";
        return this;
    }

}
