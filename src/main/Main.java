package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	static Scanner in = new Scanner(System.in);
	public static ArrayList<Voyager> voyagers = new ArrayList<>();
	public static ArrayList<Voyager> party = new ArrayList<>();
	public static ArrayList<Voyager> voyagersInRealm[] = new ArrayList[24];
	public static int currentRealm = 0;
	public static ArrayList<Enemy> enemies = new ArrayList<>();
	static String[] items = {"heartbeet root", "manana", "set of armor", "weapon"};
	public static Random r = new Random();


	public static void main(String[] args) {
		//Initialize voyagersInRealm array lists
		for (int i = 0; i < 24; i++) {
	        voyagersInRealm[i] = new ArrayList<Voyager>();
	    }
		
		System.out.println("Welcome, hopelessly lost souls, to the ROOM OF SWORDS SIMULATOR\n\n"
				+ "Enter the names of the Voyagers in this recursion, or\n"
				+ "simply press ENTER to get a default set of voyagers.\n");
		
		getVoyagers();		
		sortVoyagers();
		generateEnemies();
		nextRealm();
	}
	
	
	public static void getVoyagers() {
		String input = "default input";
		int id = 1;
		ArrayList<String> voyagerNames = new ArrayList<>();
		
		//saves names in voyagerNames array list
		while (input != "") {
			System.out.print("Voyager " + id + ": ");
			//input = in.nextLine();
			input = "";////////////////////////////////////////////////////////////////////////////////////////////////TEMP CHANGE
			voyagerNames.add(input);
			id++;	
		}
		voyagerNames.remove("");
		System.out.println();
		
		//default voyagers
		if (voyagerNames.isEmpty()) {
			voyagerNames.add("Gyrus");
			voyagerNames.add("Kodya");
			voyagerNames.add("Tori");
			voyagerNames.add("Sylvia");
			voyagerNames.add("Neph");
			voyagerNames.add("Don");
			voyagerNames.add("Anon");
			voyagerNames.add("Feather");
			voyagerNames.add("Oli");
			voyagerNames.add("Ragan");
			voyagerNames.add("Aniju");
			voyagerNames.add("Maria");
			voyagerNames.add("Bronzo");
			voyagerNames.add("Vee");
			voyagerNames.add("Alistair");
			voyagerNames.add("Vela");
			voyagerNames.add("Cib");
			voyagerNames.add("Iro");
			voyagerNames.add("Amelia");
			voyagerNames.add("Yumeji");
			voyagerNames.add("Mimi");
			voyagerNames.add("Isaac");
			voyagerNames.add("Vincent");
			voyagerNames.add("Clowny");
			voyagerNames.add("Emilio");
			voyagerNames.add("Bo");
			voyagerNames.add("Ayara");
		}
		
		//makes a new Voyager for each name and stores them in the voyagers array list
		id = 0;
		for (String name : voyagerNames) {
			voyagers.add(new Voyager(name, id, voyagerNames.size()));
			id++;
		}
	}
	
	
	//randomly assigns voyagers to realms
	public static void sortVoyagers() {
		int realm = 0;
		int voyager;
		ArrayList<Voyager> unsortedVoyagers = new ArrayList<>();
		unsortedVoyagers.addAll(voyagers);
		
		while (!unsortedVoyagers.isEmpty()) {
			voyager = r.nextInt(unsortedVoyagers.size());
			voyagersInRealm[realm].add(unsortedVoyagers.get(voyager));
			realm = r.nextInt(23);
			unsortedVoyagers.remove(voyager);
		}
		
		//debug, shows which voyagers are in each realm
//		int i = 0;
//		for (ArrayList<Voyager> voyagers : voyagersInRealm) {
//			System.out.print("Realm " + (i+1) + ": ");
//			for (Voyager person : voyagers) {
//				System.out.print(person.name + " ");
//			}
//			System.out.println();
//			i++;
//		}
	}
	
	
	public static void generateEnemies() {
		enemies.add(new Enemy("slime", 1));
		enemies.add(new Enemy("goblin", 2));
		enemies.add(new Enemy("ogre", 3));
		enemies.add(new Enemy("cyclops", 4));
		enemies.add(new Enemy("giant", 4));
		enemies.add(new Enemy("roc", 5));
		enemies.add(new Enemy("hyrda", 7));
		enemies.add(new Enemy("dragon", 8));
		enemies.add(new Enemy("minotaur", 5));
		enemies.add(new Enemy("manticore", 6));
		enemies.add(new Enemy("giant spider", 2));
		enemies.add(new Enemy("swarm of bats", 2));
		enemies.add(new Enemy("siren", 1));
		enemies.add(new Enemy("bat", 1));
		enemies.add(new Enemy("monster pit", 3));
		enemies.add(new Enemy("gremlin", 2));
		enemies.add(new Enemy("skeleton", 1));
		enemies.add(new Enemy("wolf", 1));
		enemies.add(new Enemy("gelatinous cube", 1));
		enemies.add(new Enemy("pixie", 1));
	}
	
	
	public static void nextRealm() {
		System.out.println("REALM " + (currentRealm+1) + "\n"
				+ "--------------");
		
		//lists the party that entered the realm
		if (party.size() > 0) {
			String s = ""; //to change "enter" to "enters" if needs be
			if (party.size() == 1)
				s = "s";
			System.out.println(list(party) + " enter" + s + " the realm.");
		}
		
		//lists the voyagers that are already in the realm
		if (voyagersInRealm[currentRealm].size() > 0) {
			String are = "are"; //to change "are" to "is" if needs be
			if (voyagersInRealm[currentRealm].size() == 1)
				are = "is";
			System.out.println(list(voyagersInRealm[currentRealm]) + " " + are + " in this realm.");
		}
		
		recruit();
		action();
		
	}
	
	
	public static void recruit() {
		//TODO: add recruitment mechanic besides just lumping everyone together
		party.addAll(voyagersInRealm[currentRealm]);
		voyagersInRealm[currentRealm].clear();
	}
	
	public static void action() {
		int event;
		int variable;
				
		for (Voyager voyager : voyagers) {
			if (party.contains(voyager)) {
				variable = 5;
				if (party.size() < 2)
					variable = 3;
				event = r.nextInt(variable);
				
				if (event < 1) { findItem(voyager); } //0
				else if (event < 3) { encounter(voyager); } //1,2
				else { interact(voyager); } //3,4
			}
		}
	}
	
	public static void findItem(Voyager voyager) {
		String item = items[r.nextInt(4)];
		System.out.println(voyager.name + " finds a " + item + ".");
		voyager.inventory.add(item);
	}

	public static void encounter(Voyager voyager) {
		Enemy enemy = enemies.get(r.nextInt(enemies.size()-1));
		System.out.print(voyager.name + " encounters a " + enemy.name + " and ");
		
		int defeats = 1;
		int injure = 1;
		int calls = 1;
		int runs = 1;
		int dies = 1;
		
		int levelDif = voyager.level - enemy.level;
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
			System.out.print("defeats it.");
		} else if (event < defeats + injure) {
			System.out.print("defeats it, but is injured.");
		} else if (event < defeats + injure + calls) {
			System.out.print("calls for help.");
		} else if (event < defeats + injure + calls + runs) {
			System.out.print("runs away.");
		} else {
			System.out.print("dies.");
			voyagersInRealm[currentRealm].add(voyager);
			party.remove(voyager);
			//TODO:create a method for death
		}
		//TODO: add in healing mechanics & injured state
		System.out.println();
	}
	
	//old method, delete later
	public static void interactOLD(Voyager voyager) {
		//assigns the random number range based on the party size
		int variable = 6;
		if (party.size() < 3)
			variable = 3;
		else if (party.size() < 4)
			variable = 5;
		
		
		int rr = r.nextInt(variable);
		
		if(rr < 3) {
			//interact2(getUniqueVoyagers(2, voyager));
		} else if (rr < 5) {
			System.out.println("INTERACT 3");
			//interact3(getUniqueVoyagers(3, voyager));
		} else {
			System.out.println("INTERACT 4");
			//interact4(getUniqueVoyagers(4, voyager));
		}
	}
	
	public static void interact(Voyager A) {
		Voyager B = getUniqueVoyager(A);
		int relation = A.relations[B.id];

		int gift = 5;
		int stories = 20;
		int train = relation/2 + 10; //10-15
		int mentor = 0;
		int argument = 5;
		int prank = 3;
		int supplies = 2*(relation^(1/3)) + 7; //3-7-11
		int rescue = relation/3 + 5; //2-5-8
		int steal = 2^((relation-3)/3) + 1; //21-3-1
		int gossip = 5;
		int fight = 5;
		
		if (relation > 0) {
			gift = (int) (15*Math.sin(relation/4)+5); //5-20-14
			stories = -2*(relation^(2/3))+20; //20-10
			mentor = (relation^2)/10; //0-10
			prank = (int) (-2*Math.cos(relation/2)+5); //3-7-4
			gossip = (relation^2)/10 + 5; //5-15
		} else if (relation < 0) {
			gift = (int) (-1*Math.sqrt(-2*relation)+5); //0-5
			stories = -4*(relation^(2/3))+20; //1-20
			argument = (relation^2)/7 + 5; //20-5
			prank = (int) (-5*Math.cos(relation/2)+10); //8-15-5
			gossip = relation/3 + 5; //2-5
			fight = (relation^2)/5 + 5; //25-5
		}
		
		int total = gift + stories + train + mentor + argument + prank + supplies + rescue + steal + gossip + fight;
		int event = r.nextInt(total);
		
		if (event < gift) {
			System.out.println(A.name + " gives " + B.name + " a gift.");
		} else if (event < gift + stories) {
			System.out.println(A.name + " and " + B.name + " share stories.");
		} else if (event < gift + stories + train) {
			System.out.println(A.name + " and " + B.name + " train together.");
		} else if (event < gift + stories + train + mentor) {
			System.out.println(A.name + " mentors " + B.name + ".");
		} else if (event < gift + stories + train + mentor + argument) {
			System.out.println(A.name + " and " + B.name + " get into an argument.");
		} else if (event < gift + stories + train + mentor + argument + prank) {
			System.out.println(A.name + " pranks "  + B.name + ".");
		} else if (event < gift + stories + train + mentor + argument + prank + supplies) {
			System.out.println(A.name + " and "  + B.name + " look for supplies.");
		} else if (event < gift + stories + train + mentor + argument + prank + supplies + rescue) {
			System.out.println(B.name + " rushes into battle without thinking, and "  + A.name + " has to come to the rescue.");
		} else if (event < gift + stories + train + mentor + argument + prank + supplies + rescue + steal) {
			System.out.println(A.name + " steals an item from " + B.name + ".");
		} else if (event < gift + stories + train + mentor + argument + prank + supplies + rescue + steal + gossip) {
			System.out.println(A.name + " and "  + B.name + " share gossip about the others.");
		} else {
			System.out.println(A.name + " and "  + B.name + " get into a fight.");
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
	
	//gets the specified number of voyagers without repeats
	public static ArrayList<Voyager> getUniqueVoyagers(int total) {
		ArrayList<Voyager> output = new ArrayList<>();
		ArrayList<Voyager> party2 = new ArrayList<>();
		party2.addAll(party);
		
		Voyager B;
		while (output.size() < total) {
			B = party2.get(r.nextInt(party2.size()));
			output.add(B);
			party2.remove(B);
		}
		
		return output;
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
