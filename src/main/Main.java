package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Main {
	static Scanner in = new Scanner(System.in);
	public static ArrayList<Voyager> voyagers = new ArrayList<>();
	public static ArrayList<Voyager> dead = new ArrayList<>();
	public static ArrayList<Voyager> party = new ArrayList<>();
	private static ArrayList<Voyager> hub = new ArrayList<>();
	private static ArrayList<Voyager> tempHub = new ArrayList<>();
	private static ArrayList<Voyager> tempParty = new ArrayList<>();
	@SuppressWarnings("unchecked")
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
		
		for (int i = 1; i < 24; i++) {
			nextRealm();
			currentRealm++;
			System.out.print("\nPress ENTER to continue...");
			in.nextLine();
			System.out.println("\n");
			
			hubRealm();
			System.out.print("\n\nPress ENTER to continue...");
			in.nextLine();
			System.out.println("\n");
		}
		
		nextRealm();
		System.out.println("\n"
			+ "  -------------------------------------------------------\n\n"
			+ "    CONGRATULATIONS! All 24 swords have been collected!  \n\n"
			+ "  -------------------------------------------------------");
		printVoyagerStats();
	}


	public static void getVoyagers() {
		String input = "default input";
		int id = 1;
		ArrayList<String> voyagerNames = new ArrayList<>();
		
		//saves names in voyagerNames array list
		boolean enoughVoyagers = false;
		while (!enoughVoyagers) {
			while (input != "") {
				System.out.print("Voyager " + id + ": ");
				input = in.nextLine();
				//input = "";
				voyagerNames.add(input);
				id++;	
			}
			voyagerNames.remove("");
			if (voyagerNames.size() == 1) {
				System.out.println("There must be at least 2 voyagers.\n");
				input = "default input";
				id--;
			} else
				enoughVoyagers = true;
		}
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
				+ "--------------------------------------------------------");
		
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
		
		recruit();
		action();
		while (party.isEmpty()) {
			recruit();
			String s = "s";
			if (party.size() > 1)
				s = "";
			System.out.println(list(party) + " reset" + s + ".\n");
			action();
		}
		bossFight();
	}


	public static void recruit() {
		//TODO: possibly add recruitment mechanic besides just lumping everyone together
		party.addAll(voyagersInRealm[currentRealm]);
		voyagersInRealm[currentRealm].clear();
	}
	
	
	public static void action() {
		int event;
		int variable;
		
		tempParty = new ArrayList<>(party);
		while (!tempParty.isEmpty()) {
			Voyager voyager = tempParty.get(r.nextInt(tempParty.size()));
			variable = 5;
			if (party.size() < 2)
				variable = 3;
			event = r.nextInt(variable);
			
			if (event < 1) { 
				findItem(voyager); 
			} else if (event < 3) { //1,2
				Enemy enemy = enemies.get(r.nextInt(enemies.size()-1));
				new Encounter(voyager, enemy, party);
			} else {
				Voyager[] voyagers = {voyager, getUniqueVoyager(voyager)};
				new RealmInteraction(voyagers, party);
				if (party.size() > 5)
					tempParty.remove(voyagers[1]);
			}
			
			tempParty.remove(voyager);
			checkForDeaths(party);
			System.out.println();
		}
	}
	
	public static void findItem(Voyager voyager) {
		String item = items[r.nextInt(4)];
		System.out.println(voyager.name + " finds a " + item + ".");
		voyager.inventory.add(item);
		voyager.totalItems++;
	}
	
	
	//TODO: BOSS FIGHT!!!
	private static void bossFight() {
		if (party.size() < 3) {
			String s = "";
			if (party.size() == 1)
				s = "s";
			System.out.println(list(party) + " fight" + s + " the boss and obtain" + s + " the boss sword.");
		} else {
			System.out.println("Everyone fights the boss and obtains the boss sword.");
		}
		
		for (Voyager v : party) {
			v.getEXP(80/party.size());
		}
		
		System.out.println();
	}

	
	private static void hubRealm() {
		System.out.println("HUB REALM \n"
				+ "--------------------------------------------------------");
		
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
		
		//action cycle
		//TODO: solo event option?
		tempHub = new ArrayList<>(hub);
		while (tempHub.size() > 1) {
			System.out.println();
			
			ArrayList<Voyager> voyagers = getUniqueVoyagers(2, tempHub);
			new HubInteraction(voyagers, hub);
			
			for (Voyager v : voyagers) {
				tempHub.remove(v);
			}
		}
		
		//check to see if anyone died during the action phase
		checkForDeaths(hub);
		
		//TODO: include more group actions
		//bring back any voyagers that got reset in cleared realms
		if (!voyagersInRealm[currentRealm-1].isEmpty()) {
			Voyager lead = hub.get(r.nextInt(hub.size()));
			
			String leadsATeam = " leads a team";
			if (hub.size() < 3)
				leadsATeam = " goes";
			
			System.out.println("\n" + lead.name + leadsATeam + " into realm " + (currentRealm-1) + " and brings " + list(voyagersInRealm[currentRealm-1]) + " back to the hub realm.");
			
			for (Voyager v : voyagersInRealm[currentRealm-1]) {
				lead.changeRelation(v, 1);
				v.changeRelation(lead, 2);
			}
			
			hub.addAll(voyagersInRealm[currentRealm-1]);
			voyagersInRealm[currentRealm-1].clear();
		}
		
		//form a new party restricted by the number of swords & people available
		//TODO: choose voyagers based on strength
		if (hub.size() < currentRealm) {
			party.addAll(hub);
			hub.clear();
		} else {
			ArrayList<Voyager> temp = new ArrayList<>();
			temp.addAll(getUniqueVoyagers(currentRealm, hub));
			party.addAll(temp);
			hub.removeAll(temp);
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
	public static ArrayList<Voyager> getUniqueVoyagers(int total, ArrayList<Voyager> list) {
		ArrayList<Voyager> output = new ArrayList<>();
		ArrayList<Voyager> party2 = new ArrayList<>();
		party2.addAll(list);
		
		Voyager B;
		while (output.size() < total) {
			B = party2.get(r.nextInt(party2.size()));
			output.add(B);
			party2.remove(B);
		}
		
		return output;
	}
	
	
	public static ArrayList<Voyager> splitArrayList(ArrayList<Voyager> list) {
		ArrayList<Voyager> output = new ArrayList<>();
		int limit = list.size()/2; //will take the lower half if the size is odd
		
		while (output.size() < limit) {
			output.add(list.get(r.nextInt(list.size())));
		}
		
		return output;
	}
	
	
	public static void reset(Voyager voyager, ArrayList<Voyager> group) {
		if (group == party)
			voyagersInRealm[currentRealm].add(voyager);
		else
			voyagersInRealm[currentRealm-1].add(voyager);
			
		group.remove(voyager);
		voyager.reset();
		
		//halves everyone's relation to the reset voyager
		for (Voyager v : voyagers)
			v.relations[voyager.id] /= 2;
	}
	
	public static void checkForDeaths(ArrayList<Voyager> group) {
		for (Voyager v : group)
			if (v.isDead())
				dead.add(v); //have to add to a separate ArrayList before removing
		
		for (Voyager v : dead)
			reset(v, group);

		dead.clear();
	}
	
	
	public static void printVoyagerStats() {
		//ranking of all the voyagers by level and exp
		System.out.println("\n\nPress ENTER to view voyager stats...");
		in.nextLine();
		Collections.sort(voyagers, new SortVoyagersByLevel());
		System.out.println("      Name  Lv.  Exp.  Power\n"
				+ "  --------------------------------------------");
		for (Voyager v : voyagers)
			System.out.printf("%10s  %2d   %3d   %s\n", v.name, v.level, v.exp, v.power);
		
		//display voyagers by kills/deaths
		System.out.println("\n\nPress ENTER to view voyager rankings by kills/deaths...");
		in.nextLine();
		Collections.sort(voyagers, new SortVoyagersByKills());
		System.out.println("      Name  Kills  Deaths  Voyager Kills\n"
				+ "  ---------------------------------------");
		for (Voyager v : voyagers)
			System.out.printf("%10s   %2d     %d       %d\n", v.name, v.kills, v.deaths, v.voyagerKills);
		
		//display voyagers by items found
		System.out.println("\n\nPress ENTER to view voyager rankings by inventory...");
		in.nextLine();
		Collections.sort(voyagers, new SortVoyagersByItems());
		System.out.println("              Items    Items  Items   Items\n"
						 + "      Name  Collected  Used   Gifted  Stolen\n"
						 + "  -------------------------------------------");
		for (Voyager v : voyagers)
			System.out.printf("%10s     %2d      %2d      %2d      %2d\n", v.name, v.totalItems, v.itemsUsed, v.itemsGifted, v.itemsStolen);
		
		//display voyager(s) by overall relationships
		System.out.println("\n\nPress ENTER to view voyager rankings by relations...");
		in.nextLine();
		Collections.sort(voyagers, new SortVoyagersByRelation());
		System.out.println("            Average   Highest   Lowest\n"
						 + "      Name  Relation  Relation  Relation\n"
						 + "  ---------------------------------------");
		for (Voyager v : voyagers)
			System.out.printf("%10s %9f    %2d        %2d\n", v.name, v.getAverageRelation(), v.getHighestRelation(), v.getLowestRelation());
		
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

class SortVoyagersByLevel implements Comparator<Voyager> {
	@Override
	public int compare(Voyager v1, Voyager v2) {
		if (v2.level - v1.level != 0)
			return v2.level - v1.level;
		else
			return v2.exp - v1.exp;
	}
}

class SortVoyagersByRelation implements Comparator<Voyager> {
	@Override
	public int compare(Voyager v1, Voyager v2) {
		double ar = v2.getAverageRelation() - v1.getAverageRelation();
		if (ar < 0)
			return -1;
		else if (ar > 0)
			return 1;
		else
			return v2.getHighestRelation() - v1.getHighestRelation();
	}
}

class SortVoyagersByKills implements Comparator<Voyager> {
	@Override
	public int compare(Voyager v1, Voyager v2) {
		if (v2.kills - v1.kills != 0)
			return v2.kills - v1.kills;
		else
			return v1.deaths - v2.deaths;
	}
}

class SortVoyagersByItems implements Comparator<Voyager> {
	@Override
	public int compare(Voyager v1, Voyager v2) {
		return v2.totalItems - v1.totalItems;
	}
}