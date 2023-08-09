package main;

import java.util.ArrayList;
import java.util.Random;

public class HubInteraction extends Interaction {

	public HubInteraction(ArrayList<Voyager> v, ArrayList<Voyager> p) {
		r = new Random();
		party = p;
		A = v.get(0);
		B = v.get(1);
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
		System.out.println(A.name + " and "  + B.name + " go on a supply run.");	
		A.changeRelation(B, 1);
		B.changeRelation(A, 1);
		
		String item = items[r.nextInt(2)];
		System.out.println("They come back with " + item + "s.");
		A.inventory.add(item);
		B.inventory.add(item);
	}
	
	protected void rescue() {
		System.out.println(A.name + " and " + B.name + " try fixing some machinery in the hub.");
		A.changeRelation(B, 1);
		B.changeRelation(A, 1);
	}

}
