package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	static Scanner in = new Scanner(System.in);
	public static ArrayList<Voyager> voyagers = new ArrayList<>();
	//public static ArrayList<Voyager> undiscovered = new ArrayList<>();
	public static ArrayList<Voyager> wanderers = new ArrayList<>();
	//public static ArrayList<Voyager> party = new ArrayList<>();
	public static ArrayList<Voyager> voyagersInRealm[] = new ArrayList[24];
	public static int currentRealm = 0;


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
		nextRealm();
	}
	
	
	public static void getVoyagers() {
		String input = "default input";
		int id = 1;
		ArrayList<String> voyagerNames = new ArrayList<>();
		
		//saves names in voyagerNames array list
		while (input != "") {
			System.out.print("Voyager " + id + ": ");
			input = in.nextLine();
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
		////System.out.println("\nHere are the Voyagers:"); //debug
		id = 0;
		for (String name : voyagerNames) {
			////System.out.println("Voyager " + id + ": " + name); //debug
			voyagers.add(new Voyager(name, id, voyagerNames.size()));
			id++;
		}
		wanderers = voyagers;
	}
	
	
	//randomly assigns voyagers to realms
	public static void sortVoyagers() {
		Random r = new Random();
		int realm = 0;
		int voyager;
		
		while (!voyagers.isEmpty()) {
			voyager = r.nextInt(voyagers.size());
			voyagersInRealm[realm].add(voyagers.get(voyager));
			realm = r.nextInt(23);
			voyagers.remove(voyager);
		}
		
		//debug
		int i = 0;
		for (ArrayList<Voyager> voyagers : voyagersInRealm) {
			System.out.print("Realm " + (i+1) + ": ");
			for (Voyager person : voyagers) {
				System.out.print(person.name + " ");
			}
			System.out.println();
			i++;
		}
	}
	
	
	public static void nextRealm() {
		System.out.println("REALM " + (currentRealm+1) + "\n"
				+ "--------------");
		
		if (voyagersInRealm[currentRealm].size() > 1) {
			System.out.println("The voyagers in this realm are");
		} else if (voyagersInRealm[currentRealm].size() == 1) {
			
		} else {
			
		}
		
	}
}
