package main;

import java.util.ArrayList;
import java.util.Random;

public class Encounter {
	//attributes
	private static Voyager voyager;
	private static Enemy enemy;
	private static ArrayList<Voyager> party;
	private static Random r;
	
	private static int defeats;
	private static int injure;
	private static int calls;
	private static int runs;
	private static int dies;
	
	//constructor
	public Encounter(Voyager v, Enemy e, ArrayList<Voyager> p) {
		voyager = v;
		enemy = e;
		party = p;
		r = new Random();
		
		System.out.print(voyager.name + " encounters a " + enemy.name + " and ");
		
		defeats = 1;
		injure = 1;
		calls = 1;
		runs = 1;
		dies = 1;
		
		int levelDif = voyager.getLevel() - enemy.level;
		if (levelDif > 0) {
			defeats = 20*levelDif;
			injure += 10/levelDif;
			calls += 4/levelDif;
			runs += 2/levelDif;
		} else if (levelDif == 0) {
			defeats = 15;
			injure = 10;
			calls = 4;
			runs = 4;
		} else {
			injure += 20/-levelDif;
			calls = 10*-levelDif;
			runs = 15*-levelDif;
			dies = 10*-levelDif;
		}
		
		int odds = defeats + injure + calls + runs + dies;
		int event = r.nextInt(odds);
		
		if(event < defeats) {
			defeatEnemy();
		} else if (event < defeats + injure) {
			injure();
		} else if (event < defeats + injure + calls) {
			callForHelp();
		} else if (event < defeats + injure + calls + runs) {
			runAway();
		} else {
			death();
		}
	}
	
	//methods
	private static void defeatEnemy() {
		System.out.println("defeats it.");
		voyager.getEXP(enemy.exp);
		voyager.kills++;
	}
	
	private static void injure() {
		System.out.println("defeats it, but is injured.");
		voyager.kills++;
		if (!voyager.isInjured()) {
			voyager.getEXP(enemy.exp);
			voyager.setInjured();
		} else {
			//will die if already injured
			System.out.println(voyager.name + " dies from the injury.");
			voyager.status = Voyager.Status.DEAD;
		}
	}
	
	private static void callForHelp() {
		System.out.print("calls for help");
		
		int[] odds = new int[party.size()];
		int i = 0;
		int relation;
		
		//get the odds of any one person in the party coming to the rescue
		for (Voyager v : party) {
			if (voyager.id != v.id) {
				relation = v.relations[voyager.id];
				odds[i] = (int) (Math.pow((relation+10), 2) / 20 + 1); //1-41
			} else {
				odds[i] = 11*(party.size()-1) + 1; //1-252
			}
			i++;
		}
		
		for (i = 1; i < odds.length; i++) {
			odds[i] += odds[i-1];
		}
		
		int total = odds[odds.length-1];
		int rr = r.nextInt(total);
		int rescueID = -1;
		
		//determine who comes to the rescue based off of "rr" variable
		for (i = 0; i < odds.length; i++) {
			if (rr < odds[i] && rescueID == -1) {
				rescueID = i;
			}
		}
		
		Voyager rescue = party.get(rescueID);
		if (rescue.id == voyager.id) {
			System.out.print(", but no one responds. ");
			encounterWithoutHelp();
		} else {
			System.out.print(". " + rescue.name + " comes to " + voyager.name + "'s aid. ");
			encounterWithHelp(rescue);
		}
	}
	
	private static void encounterWithoutHelp() {
		int odds = defeats + injure + runs + dies;
		int event = r.nextInt(odds);
		
		System.out.print(voyager.name + " ");
		
		if(event < defeats) {
			System.out.print("fights the " + enemy.name + " and ");
			defeatEnemy();
		} else if (event < defeats + injure) {
			System.out.print("fights the " + enemy.name + " and ");
			injure();
		} else if (event < defeats + injure + runs) {
			runAway();
		} else {
			death();
		}
	}
	
	private static void encounterWithHelp(Voyager rescue) {
		defeats = 1;
		runs = 1;
		dies = 1;
		
		int levelDif = voyager.getLevel() + rescue.getLevel() - enemy.level;
		if (levelDif > 0) {
			defeats = 20*levelDif;
			runs += 2/levelDif;
		} else if (levelDif == 0) {
			defeats = 15;
			runs = 4;
		} else {
			runs = 15*-levelDif;
			dies = 10*-levelDif;
		}
		
		int odds = defeats + runs + dies;
		int event = r.nextInt(odds);
		
		if(event < defeats) {
			System.out.println("Together, they defeat the " + enemy.name + ".");
			voyager.getEXP((int) Math.ceil((double)enemy.exp/2));
			rescue.getEXP((int) Math.ceil((double)enemy.exp/2));
			voyager.changeRelation(rescue, 2);
			voyager.kills++;
			rescue.kills++;
		} else if (event < defeats + runs) {
			System.out.println("However, they both run away.");
			voyager.changeRelation(rescue, 1);
		} else {
			System.out.println("However, they both die.");
			voyager.status = Voyager.Status.DEAD;
			rescue.status = Voyager.Status.DEAD;
		}
	}
	
	private static void runAway() {
		System.out.println("runs away.");
	}
	
	private static void death() {
		System.out.println("dies.");
		voyager.status = Voyager.Status.DEAD;
	}
	
	

}
