package main;

import java.util.ArrayList;
import java.util.Random;

public class RealmInteraction extends Interaction {

	public RealmInteraction(Voyager v, ArrayList<Voyager> p) {
		r = new Random();
		party = p;
		A = v;
		B = getUniqueVoyager(A);
		relation = A.relations[B.id];
		
		chooseInteraction();
		
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
			fight(party);
		}
	}
		
	protected void supplies() {
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
	
	protected void rescue() {
		System.out.println(B.name + " rushes into battle without thinking, and "  + A.name + " has to come to the rescue.");
		A.changeRelation(B, -1);
		B.changeRelation(A, 1);
		A.getEXP(B.getLevel());
	}
}
