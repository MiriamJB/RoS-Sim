package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	static Scanner in = new Scanner(System.in);
	public static ArrayList<Voyager> voyagers = new ArrayList<>();
	public static ArrayList<Voyager> party = new ArrayList<>();
	private static ArrayList<Voyager> dead = new ArrayList<>();
	private static ArrayList<Voyager> hub = new ArrayList<>();
	public static ArrayList<Voyager> voyagersInRealm[] = new ArrayList[25];
	public static int currentRealm = 1;
	public static ArrayList<Enemy> enemies = new ArrayList<>();
	static String[] items = {"heartbeet root", "manana", "set of armor", "weapon"};
	public static Random r = new Random();


	public static void main(String[] args) {
		//Initialize voyagersInRealm array lists
		for (int i = 0; i < 25; i++) {
	        voyagersInRealm[i] = new ArrayList<Voyager>();
	    }
		
		System.out.println("Welcome, hopelessly lost souls, to the ROOM OF SWORDS SIMULATOR\n\n"
				+ "Enter the names of the Voyagers in this recursion, or\n"
				+ "simply press ENTER to get a default set of voyagers.\n");
		
		getVoyagers();		
		sortVoyagers();
		generateEnemies();
		
		for (int i = 1; i < 25; i++) {
			nextRealm();
			currentRealm++;
			System.out.println();
			in.nextLine();
			hubRealm();
			System.out.println();
			in.nextLine();

		}
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
		
		//add one to the hub realm
		voyager = r.nextInt(unsortedVoyagers.size());
		voyagersInRealm[realm].add(unsortedVoyagers.get(voyager));
		unsortedVoyagers.remove(voyager);
		realm = 1;
		
		while (!unsortedVoyagers.isEmpty()) {
			voyager = r.nextInt(unsortedVoyagers.size());
			voyagersInRealm[realm].add(unsortedVoyagers.get(voyager));
			unsortedVoyagers.remove(voyager);
			realm = r.nextInt(23)+1;
		}
		
		//debug, shows which voyagers are in each realm
//		int i = 0;
//		for (ArrayList<Voyager> voyagers : voyagersInRealm) {
//			System.out.print("Realm " + (i) + ": ");
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
		System.out.println("REALM " + currentRealm + "\n"
				+ "----------------------------");
		
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
		
		System.out.println();
		
		if (party.size() == 0)//TODO: TEMP CHANGE/////////////////////////////////////////////////////////////////
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
				else if (event < 3) { //1,2
					Enemy enemy = enemies.get(r.nextInt(enemies.size()-1));
					new Encounter(voyager, enemy, party);
				} else { 
					new Interaction(voyager, party); } //3,4
				
				checkForDeaths();
				System.out.println();
			}
		}
	}
	
	public static void findItem(Voyager voyager) {
		String item = items[r.nextInt(4)];
		System.out.println(voyager.name + " finds a " + item + ".");
		voyager.inventory.add(item);
	}

	
	private static void hubRealm() {
		System.out.println("HUB REALM \n"
				+ "----------------------------");
		
		//lists the party that entered the realm
		if (party.size() > 0) {
			String s = ""; //to change "enter" to "enters" if needs be
			if (party.size() == 1)
				s = "s";
			System.out.println(list(party) + " enter" + s + " the hub realm.");
		}
		
		//reveals who is in the hub realm
		if (currentRealm == 2) {
			System.out.println(voyagersInRealm[0].get(0).name + " is in the hub realm.");
			hub.add(voyagersInRealm[0].get(0));
			voyagersInRealm[0].clear();
		}
		
		hub.addAll(party);
		party.clear();
		
		//TODO: hub realm interactions
		
		//form a new party restricted by the number of swords & people available
		if (hub.size() < currentRealm) {
			party.addAll(hub);
			hub.clear();
		} else {
			ArrayList<Voyager> temp = new ArrayList<>();
			temp.addAll(getUniqueVoyagers(currentRealm));
			party.addAll(temp);
			hub.removeAll(temp);
		}
		
		
	}
	
	
	//gets the specified number of voyagers without repeats
	public static ArrayList<Voyager> getUniqueVoyagers(int total) {
		ArrayList<Voyager> output = new ArrayList<>();
		ArrayList<Voyager> party2 = new ArrayList<>();
		party2.addAll(hub);
		
		Voyager B;
		while (output.size() < total) {
			B = party2.get(r.nextInt(party2.size()));
			output.add(B);
			party2.remove(B);
		}
		
		return output;
	}
	
	
	public static void reset(Voyager voyager) {
		voyagersInRealm[currentRealm].add(voyager);
		party.remove(voyager);
		voyager.reset();
		
		//halves everyone's relation to the reset voyager
		for (Voyager v : voyagers)
			v.relations[voyager.id] /= 2;
	}
	
	public static void checkForDeaths() {
		for (Voyager v : party) {
			if (v.isDead())
				dead.add(v);
		}
		
		for (Voyager v : dead) {
			reset(v);
		}
		
		dead.clear();
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
