package main;

public class Voyager extends Entity {
	//attributes
	public static int id;
	public String power;
	public boolean awakened;
	public int[] relations;
	
	//constructor
	public Voyager(String name, int id, int totalVoyagers) {
		this.name = name;
		this.id = id;
		awakened = false;
		super.level = 1;
		super.exp = 0;
		super.status = s.WANDERING;
		
		//set all relationship stats to 0
		relations = new int[totalVoyagers];
		for (int i = 0; i < totalVoyagers; i++) {
			relations[i] = 0;
		}
	}
}
