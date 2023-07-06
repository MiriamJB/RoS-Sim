package main;

public class Enemy extends Entity{
	//constructor
	public Enemy(String name, int level) {
		super.name = name;
		super.level = level;
		super.exp = level*level;
	}
}
