package main;

import java.util.ArrayList;

public class Voyager extends Entity {
	//attributes
	public int id;
	public String power;
	public boolean awakened;
	public ArrayList<String> inventory;
	public int[] relations;
	
	//constructor
	public Voyager(String name, int id, int totalVoyagers) {
		this.name = name;
		this.id = id;
		awakened = false;
		inventory = new ArrayList<>();
		super.level = 1;
		super.exp = 0;
		
		//set all relationship stats to 0
		relations = new int[totalVoyagers];
		for (int i = 0; i < totalVoyagers; i++) {
			relations[i] = 0;
		}
	}
	
}
