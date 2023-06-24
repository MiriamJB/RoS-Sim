package main;

public abstract class Entity {
	public String name;
	public int level;
	public int exp;
	public s status;
	
	enum s {
		RECRUTED, WANDERING, UNDISCOVERED, SHADOWED, ENEMY;
	}
}
