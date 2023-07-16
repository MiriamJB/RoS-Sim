package main;

import java.util.ArrayList;
import java.util.Random;

public class Interaction {
	private static Voyager A;
	private static Voyager B;
	static ArrayList<Voyager> party;
	private static Random r;
	private static int relation;
	private static String[] gifts = {"flower crown", "hug", "handmade gift"};
	static String[] items = {"heartbeet root", "manana", "set of armor", "weapon"};
	
	private static int gift;
	private static int stories;
	private static int train;
	private static int mentor;
	private static int argument;
	private static int prank;
	private static int supplies;
	private static int rescue;
	private static int steal;
	private static int gossip;
	private static int fight;
	
	//constructor
	public Interaction(Voyager v, ArrayList<Voyager> p) {
		r = new Random();
		party = p;
		A = v;
		B = getUniqueVoyager(A);
		relation = A.relations[B.id];
		
		gift = 5;
		stories = 20;
		train = relation/2 + 10; //10-15
		mentor = 0;
		argument = 5;
		prank = 3;
		supplies = (int) (2*Math.pow(relation, 1/3) + 7); //3-7-11
		rescue = relation/3 + 5; //2-5-8
		steal = (int) (Math.pow(2, (relation-3)/3) + 1); //21-3-1
		gossip = 5;
		fight = 5;
		
		if (relation > 0) {
			gift = (int) (15*Math.sin(relation/4)+5); //5-20-14
			stories = (int) (-2*(Math.pow(relation, 2/3)) + 20); //20-10
			mentor = (int) (Math.pow(relation, 2) / 10); //0-10
			prank = (int) (-2*Math.cos(relation/2) + 5); //3-7-4
			gossip = (int) (Math.pow(relation,2)/10 + 5); //5-15
		} else if (relation < 0) {
			gift = (int) (-1*Math.sqrt(-2*relation) + 5); //0-5
			stories = (int) (-4*Math.pow(relation, 2/3) + 20); //1-20
			argument = (int) (Math.pow(relation, 2) / 7 + 5); //20-5
			prank = (int) (-5*Math.cos(relation/2)+10); //8-15-5
			gossip = relation/3 + 5; //2-5
			fight = (int) (Math.pow(relation, 2) / 5 + 5); //25-5
		}
		
		int total = gift + stories + train + mentor + argument + prank + supplies + rescue + steal + gossip + fight;
		int event = r.nextInt(total);
		
		if (event < gift) {
			gift();
		} else if (event < gift + stories) {
			stories();
		} else if (event < gift + stories + train) {
			train();
		} else if (event < gift + stories + train + mentor) {
			mentor();
		} else if (event < gift + stories + train + mentor + argument) {
			argument();
		} else if (event < gift + stories + train + mentor + argument + prank) {
			prank();
		} else if (event < gift + stories + train + mentor + argument + prank + supplies) {
			supplies();
		} else if (event < gift + stories + train + mentor + argument + prank + supplies + rescue) {
			rescue();
		} else if (event < gift + stories + train + mentor + argument + prank + supplies + rescue + steal) {
			steal();
		} else if (event < gift + stories + train + mentor + argument + prank + supplies + rescue + steal + gossip) {
			gossip();
		} else {
			fight();
		}
	}
	
	//methods
	private static void gift() {
		System.out.print(A.name + " gives " + B.name + " a ");
		
		String gift = "";		
		if (A.inventory.isEmpty()) {
			gift = gifts[r.nextInt(gifts.length)];
		} else {
			gift = A.inventory.get(r.nextInt(A.inventory.size()));
			A.inventory.remove(gift);
			B.inventory.add(gift);
		}
		
		System.out.println(gift + ".");
		A.changeRelation(B, 1);
		B.changeRelation(A, 2);
	}
	
	private static void stories() {
		System.out.println(A.name + " and " + B.name + " share stories.");
		A.changeRelation(B, 1);
		B.changeRelation(A, 1);
	}
	
	private static void train() {
		System.out.println(A.name + " and " + B.name + " train together.");
		A.changeRelation(B, 1);
		B.changeRelation(A, 1);
		A.getEXP(A.level+B.level);
		B.getEXP(A.level+ B.level);
	}
	
	private static void mentor() {
		System.out.println(A.name + " mentors " + B.name + ".");
		A.changeRelation(B, 2);
		B.changeRelation(A, 3);
		B.getEXP(B.level*B.level);
	}
	
	private static void argument() {
		System.out.println(A.name + " and " + B.name + " get into an argument.");
		A.changeRelation(B, -1);
		B.changeRelation(A, -1);
	}
	
	private static void prank() {
		System.out.println(A.name + " pranks "  + B.name + ".");
		B.changeRelation(A, -1);
	}
	
	private static void supplies() {
		System.out.println(A.name + " and "  + B.name + " look for supplies.");	
		A.changeRelation(B, 1);
		B.changeRelation(A, 1);
		
		String item = items[r.nextInt(items.length)];
		System.out.println(A.name + " finds a " + item + ".");
		A.inventory.add(item);
		
		item = items[r.nextInt(items.length)];
		System.out.println(B.name + " finds a " + item + ".");
		B.inventory.add(item);
	}
	
	private static void rescue() {
		System.out.println(B.name + " rushes into battle without thinking, and "  + A.name + " has to come to the rescue.");
		A.changeRelation(B, -1);
		B.changeRelation(A, 1);
		A.getEXP(B.getLevel());
	}
	
	private static void steal() {
		if (B.inventory.isEmpty()) {
			System.out.println(A.name + " tries to steal an item from " + B.name + ", but is unsuccessful.");
		} else {
			String item = B.inventory.get(r.nextInt(B.inventory.size())); 
			System.out.println(A.name + " steals a " + item + " from " + B.name + ".");
			A.inventory.add(item);
			B.inventory.remove(item);
		}
		A.changeRelation(B, -1);
		B.changeRelation(A, -2);
	}
	
	private static void gossip() {
		System.out.println(A.name + " and "  + B.name + " share gossip about the others.");
		
		for (int i = 0; i < party.size(); i++) {
			Voyager v = party.get(i);
			if (v.id != A.id && v.id != B.id) {
				A.changeRelation(v, -1);
				B.changeRelation(v, -1);
			}
		}
	}
	
	private static void fight() {
		System.out.println(A.name + " and "  + B.name + " get into a fight.");
		
		int levelDif = A.getLevel() - B.getLevel() + 10; //1-20 (1-9 = B is stronger, 10 = same, 11-20 = A is stronger)
		Boolean iA = false, iB = false, kA = false, kB = false;
		
		int totalA = (int) (Math.pow(B.relations[A.id] - 10, 2)/(5*Math.pow((levelDif/10+9.1)-11, 2)));
		int totalB = (int) (Math.pow(A.relations[B.id] - 10, 2)/(5*Math.pow((levelDif/10-9.1)+11, 2)));
		int killA = totalA/3;
		int killB = totalB/3;
		int injureA = totalA - killA;
		int injureB = totalB - killB;
		int nothingA = 20;
		int nothingB = 20;
		totalA += nothingA;
		totalB += nothingB;
		
		int eventA = r.nextInt(totalA);
		int eventB = r.nextInt(totalB);
		
		A.changeRelation(B, -2);
		B.changeRelation(A, -2);
		
		//calculate if either person in the fight got injured/killed
		if (eventA < injureA) { iA = true; }
		else if (eventA < injureA + killA) { kA = true; }
		
		if (eventB < injureB) { iB = true; }
		else if (eventB < injureB + killB) { kB = true; }
		
		//output injured
		if (iA || iB) {
			ArrayList<Voyager> injure = new ArrayList<>();
			if (iA) { 
				injure.add(A);
				B.getEXP(A.getLevel());
			}
			if (iB) { 
				injure.add(B);
				A.getEXP(B.getLevel());
			}
			
			String was = " was";
			if (injure.size() > 1) { was = " were"; }
			
			System.out.println(list(injure) + was + " injured.");
			
			for (Voyager v : injure) { v.setInjured(); }
		}
		
		//output killed
		if (kA || kB) {
			ArrayList<Voyager> kill = new ArrayList<>();
			if (kA) { 
				kill.add(A);
				B.getEXP(A.getLevel()*2);
			}
			if (kB) { 
				kill.add(B);
				A.getEXP(B.getLevel()*2);	
			}
			
			String s = "s";
			if (kill.size() > 1) { s = ""; }
			
			System.out.println(list(kill) + " die" + s + ".");
			
			for (Voyager v : kill) { v.status = Voyager.Status.DEAD; }
		}
		
	}
	
	//gets a voyager that is not the one in the parameter
	public static Voyager getUniqueVoyager(Voyager A) {
		ArrayList<Voyager> party2 = new ArrayList<>();
		party2.addAll(party);
		party2.remove(A);
		Voyager B = party2.get(r.nextInt(party2.size()));
		return B;
	}
	
	//prints out everything in the array in a nice list that cures my OCD
	public static String list(ArrayList<? extends Thing> list) {
		//return empty if the list is empty
		if (list.size() <= 0)
			return "";
		
		//get first value
		String output = list.get(0).name;
		
		//get next values
		if (list.size() > 1) {
			for (int i = 1; i < list.size()-1; i++)
				output += ", " + list.get(i).name;
			if (list.size() > 2)
				output += ",";
			output += " and " + list.get(list.size()-1).name;
		}
		
		return output;
	}

}
