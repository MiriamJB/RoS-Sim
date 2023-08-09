package main;

import java.util.ArrayList;
import java.util.Random;

public class Voyager extends Entity {
	//attributes
	public int id;
	public String power;
	public boolean awakened;
	public ArrayList<String> inventory;
	public int[] relations;
	public Status status;
	public int kills;
	public int voyagerKills;
	public int deaths;
	Random r = new Random();
	
	public String[] powers = {"pyrokinetic (fire)", "hydrokinetic (water)", "cryokinetic (ice)", 
			"electrokinetic (electricity)", "chronokinetic (time)", "aerokinetic (air)", "geokinetic (earth)", 
			"photokinetic (light)", "chlorokinetic (plants)", "telumkinetic (weapons)", "audiokinetic (sound)",
			"kinetikinetic (kinetic energy)", "erebokinetic (darkness)", "essekinetic (reality)", "gerontokinetic (age)",
			"gyrokinetic (gravity)", "hypnokinetic (sleep)", "pathokinetic (empath)",  "telepathic",
			"lumokinetic (light)", "mnemokinetic (memory)", "atokinetic (atoms)", "myokinetic (muscle)", 
			"vitakinetic (healing)", "opinokinetic (senses)", "telekinetic (teleportation)", "invisibility"};
	
	public enum Status {
		INJURED, DEAD, ALIVE;
	}
	
	//constructor
	public Voyager(String name, int id, int totalVoyagers) {
		this.name = name;
		this.id = id;
		awakened = false;
		power = "N/A";
		status = Status.ALIVE;
		inventory = new ArrayList<>();
		level = 1;
		exp = 0;
		kills = 0;
		deaths = 0;
		
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
		//System.out.println(name + " gained " + expGained + " exp."); //debug
		
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
		
		if (!awakened && levelUp > 0)
			checkAwaken();
	}
	
	private void checkAwaken() {
		int awakenProbablility = r.nextInt(level*level);
		if (awakenProbablility > 10) {
			awakened = true;
			if (power == "N/A")
				power = powers[r.nextInt(powers.length)];
			System.out.println(name + " has awakened " + power + " powers!");
		}
	}

	public int getLevel() {
		int lv = level;
		
		if (inventory.contains("set of armor"))
			lv++;
		if (inventory.contains("weapon"))
			lv++;
		if (awakened)
			lv++;
		
		if (lv > 10)
			return 10;
		
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
	
	public double getAverageRelation() {
		double averageRelation = 0;
		int nonZeros = 0;
		for (int i : relations) {
			averageRelation += i;
			if (i != 0)
				nonZeros++;
		}
		
		if (nonZeros == 0)
			return 0;
		
		averageRelation = averageRelation/nonZeros;
		return averageRelation;
	}
	
	public int getHighestRelation() {
		int highest = -10;
		for (int i : relations) {
			if (i > highest)
				highest = i;
		}			
		return highest;
	}
	
	public int getLowestRelation() {
		int lowest = 10;
		for (int i : relations) {
			if (i < lowest)
				lowest = i;
		}			
		return lowest;
	}
	
	public void reset() {
		awakened = false;
		status = Status.ALIVE;
		inventory.clear();
		level = 1;
		exp = 0;
		deaths++;

		//set all relationship stats to 0
		for (int i = 0; i < relations.length; i++) {
			relations[i] = 0;
		}		
	}
	
}
