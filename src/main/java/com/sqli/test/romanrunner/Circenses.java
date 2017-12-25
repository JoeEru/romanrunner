package com.sqli.test.romanrunner;

import java.util.ArrayList;
import java.util.List;

public class Circenses {
	List<String> path = new ArrayList<>();

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public Circenses(List<String> path) {
		super();
		this.path = path;
	}
	
	public String draw() {		
		return this.toString();
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = path.size() -1; i >= 0; i--) {
			if(i != 0 ) {
				str+= path.get(i) + "\n";
			}else {
				str+= path.get(i);
			}
		}
		return str;
	}

	


	
	
}
