package main;

import java.util.ArrayList;

public class Voyager extends Entity {
	//attributes
	public int id;
	public String power;
	public boolean awakened;
	public ArrayList<String> inventory;
	public int[] relations;
	public Status status;
	
	public enum Status {
		INJURED, DEAD, ALIVE;
	}
	
	//constructor
	public Voyager(String name, int id, int totalVoyagers) {
		this.name = name;
		this.id = id;
		awakened = false;
		status = Status.ALIVE;
		inventory = new ArrayList<>();
		level = 1;
		exp = 0;
		
		//set all relationship stats to 0
		relations = new int[totalVoyagers];
		for (int i = 0; i < totalVoyagers; i++) {
			relations[i] = 0;
		}
	}
	
	//methods
	public boolean isDead() {
		if (status == Status.DEAD)
			return true;
		return false;
	}
	
	public boolean isInjured() {
		if (status == Status.INJURED)
			return true;
		return false;
	}
	
	public void setInjured() {
		if (inventory.contains("heartbeet root")) {
			System.out.println(name + " eats a heartbeet root to recover.");
			inventory.remove("heartbeet root");
		} else {
			status = Status.INJURED;
		}
	}
	
	public void getEXP(int expGained) {
		super.exp += expGained;
		System.out.println(name + " gained " + expGained + " exp."); //debug
		
		//calculates if there should be a level up (can't go over level 10)
		int levelUp = 0;
		while (exp >= level*level && level < 10) {
			exp -= level*level;
			level++;
			levelUp++;
		}
		
		//prints out a level up message
		if (levelUp == 1) {
			System.out.println(name + " leveled up! " + name + " is now level " + level + ".");
		} else if (levelUp > 1) {
			System.out.println(name + " leveled up " + levelUp + " times! " + name + " is now level " + level + ".");
		}
		
		//TODO: add mechanics for becoming awakened
	}
	
	public int getLevel() {
		int lv = level;
		
		if (inventory.contains("set of armor"))
			lv++;
		if (inventory.contains("weapon"))
			lv++;
		
		return lv;
	}
	
	public void changeRelation(Voyager voyager, int change) {
		relations[voyager.id] += change;
		
		//keeps the relationship status between -10 and 10
		if (relations[voyager.id] > 10) {
			relations[voyager.id] = 10;
		} else if (relations[voyager.id] < -10) {
			relations[voyager.id] = -10;
		}
	}
	
	public void reset() {
		awakened = false;
		status = Status.ALIVE;
		inventory.clear();
		level = 1;
		exp = 0;
		
		//set all relationship stats to 0
		for (int rel : relations) {
			rel = 0;
		}
	}
	
}
