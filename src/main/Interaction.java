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
	}
	
	private static void mentor() {
		System.out.println(A.name + " mentors " + B.name + ".");
		A.changeRelation(B, 2);
		B.changeRelation(A, 3);
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
	}
	
	private static void rescue() {
		System.out.println(B.name + " rushes into battle without thinking, and "  + A.name + " has to come to the rescue.");
		A.changeRelation(B, -1);
		B.changeRelation(A, 1);
	}
	
	private static void steal() {
		System.out.println(A.name + " steals an item from " + B.name + ".");
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
		int levelDif = A.level - B.level;
		Boolean iA, iB, kA, kB, nA, nB;
		
		int injureA = (int) (Math.pow(-levelDif + 10, 3)/120 + 2); //1-60
		int injureB = (int) (Math.pow(levelDif + 10, 3)/120 + 2);
		int killA = (int) (Math.pow(-levelDif + 10, 2)/10); //0-36
		int killB = (int) (Math.pow(levelDif + 10, 2)/10);
		int nothingA = 2*(B.relations[A.id]) + 40;
		int nothingB = 2*(B.relations[A.id]) + 40;//TODO: figure how to incorporate levelDif
		
		int totalA = injureA + killA + nothingA;
		int totalB = injureB + killB + nothingB;
		int eventA = r.nextInt(totalA);
		int eventB = r.nextInt(totalB);
		
		//TODO: add fight repercussions mechanics
		
		A.changeRelation(B, -2);
		B.changeRelation(A, -2);
	}
	
	//gets a voyager that is not the one in the parameter
	public static Voyager getUniqueVoyager(Voyager A) {
		ArrayList<Voyager> party2 = new ArrayList<>();
		party2.addAll(party);
		party2.remove(A);
		Voyager B = party2.get(r.nextInt(party2.size()));
		return B;
	}

}
