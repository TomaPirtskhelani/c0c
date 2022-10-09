package DK;

import java.util.ArrayList;
import java.util.HashSet;

import Auxiliary.DK_0_Function;
import Auxiliary.Function;
import Grammar.Grammar;
import Grammar.Production;

public class DK_0 {
	//DK(0) test
	public static boolean DK_test(Grammar G) {
		ArrayList<Production> productions = new ArrayList<>();
		ArrayList<Integer> indices = new ArrayList<>();
		for(Production p : G.getProductions()) {
			if(p.getLeft().equals(G.getStart().toString())) {
				productions.add(new Production(p.toString()));
				indices.add(0);
			}
		}		
		DK_0_Function.makeEpsilonMoves(productions, indices, G);
		//Avoid cycles
		//Avoid cycles
		HashSet<String> states = new HashSet<>();
		return check(productions, indices, G, states);
	}
	//Check whether the DK_0 test fails or not
	private static boolean check(ArrayList<Production> productions, ArrayList<Integer> indices, Grammar G, HashSet<String> states) {
		states.add(productions.toString() + indices.toString());
		int count = DK_0_Function.countEndedRules(productions, indices);
		//if More than 2 production rules are completed return false
		if(count > 1) {
			return false;
		}
		//Check if the dot is followed by a terminal return false
		if(count == 1) {
			System.out.println("Accept " + productions + " " + DK_0_Function.checkTerminalAfterDot(productions, indices, G));
			if(DK_0_Function.checkTerminalAfterDot(productions, indices, G)) {
				if(productions.size() > 1) {
					return proceed(productions, indices, G, states);
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
		return proceed(productions, indices, G, states);
	}
	//Move the dot forward
	private static boolean proceed(ArrayList<Production> productions, ArrayList<Integer> indices, Grammar G,
																						HashSet<String> states) {
		boolean indicator = true;
		ArrayList<Production> localProductions;
		ArrayList<Integer> localIndices;
		for(String step : Function.possibleSteps(productions, indices, G)) {
			localProductions = new ArrayList<>();
			localIndices = new ArrayList<>();
			for(int i = 0; i < productions.size(); i++) {
				if(step.equals(Function.firstTermFromInd(productions.get(i).getRight(), indices.get(i), G))) {
					localProductions.add(productions.get(i));
					localIndices.add(indices.get(i) + step.length());
				}
			}
			DK_0_Function.makeEpsilonMoves(localProductions, localIndices, G);
			//System.out.println(localProductions + " " + localIndices + " - " + step);
			if(localProductions.size() > 0) {
				if(!states.contains(localProductions.toString() + localIndices.toString())) {
					indicator = indicator & check(localProductions, localIndices, G, states);
				}
			}
		}
		return indicator;
	}
}
