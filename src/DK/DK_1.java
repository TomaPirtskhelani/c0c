package DK;

import java.util.ArrayList;
import java.util.HashSet;

import Auxiliary.DK_1_Function;
import Auxiliary.Function;
import Grammar.Grammar;
import Grammar.Production;
import Grammar.Terminal;

public class DK_1 {
	//DK(0) test
	public static boolean DK_test(Grammar G) {
		ArrayList<Production> productions = new ArrayList<>();
		ArrayList<Integer> indices = new ArrayList<>();
		ArrayList<ArrayList<String>> lookaheads = new ArrayList<>();
		for(Production p : G.getProductions()) {
			if(p.getLeft().equals(G.getStart().toString())) {
				productions.add(new Production(p.toString()));
				indices.add(0);
				lookaheads.add(new ArrayList<String>());
				ArrayList<String> lookahead = lookaheads.get(lookaheads.size() - 1);
				for(Terminal t : G.getTerminals()) {
					lookahead.add(t.getString());
				}
			}
		}
		DK_1_Function.makeEpsilonMoves(productions, indices, lookaheads, G);
		HashSet<String> states = new HashSet<>();
		System.out.println(productions);
		System.out.println(lookaheads);
		System.out.println();
		
		return check(productions, indices, lookaheads, G, states);
	}
	private static boolean check(ArrayList<Production> productions, ArrayList<Integer> indices,
							ArrayList<ArrayList<String>> lookaheads, Grammar G, HashSet<String> states) {
		// + lookahead 
		states.add(productions.toString() + indices.toString());
		boolean isAnyCompleteRule = false;
		for(int i = 0; i < productions.size(); i++) {
			if(DK_1_Function.isCompletedRule(productions.get(i), indices.get(i))) {
				isAnyCompleteRule = true;
				for(int j = 0; j < productions.size(); j++) {
					if(i == j)continue;
					if(DK_1_Function.isCompletedRule(productions.get(j), indices.get(j))) {
						if(DK_1_Function.commonLookahead(lookaheads.get(i), lookaheads.get(j))) {
							System.out.println("Accept " + productions + " " + "False1");
							return false;
						}
					} else {
						if(DK_1_Function.lookaheadFollowsTheDot(productions.get(j), indices.get(j), lookaheads.get(i), G)) {
							System.out.println("Production: " + productions.get(i) + " dots " + indices.get(i) + " lookahead " + lookaheads.get(i));
							System.out.println("Production: " + productions.get(j) + " dots " + indices.get(j) + " lookahead " + lookaheads.get(j));
							System.out.println("Accept " + productions + " " + "False2");
							return false;
						}
					}
				}
			}
		}
		if(isAnyCompleteRule) {
			System.out.println("Accept " + productions + " " + "True");
			return true;
		} else {
			return proceed(productions, indices, lookaheads, G, states);
		}
	}
	//Move the dot forward
	private static boolean proceed(ArrayList<Production> productions, ArrayList<Integer> indices,
							ArrayList<ArrayList<String>> lookaheads, Grammar G, HashSet<String> states) {
		boolean indicator = true;
		ArrayList<Production> localProductions;
		ArrayList<Integer> localIndices;
		ArrayList<ArrayList<String>> localLookaheads;
		for(String step : Function.possibleSteps(productions, indices, G)) {
			localProductions = new ArrayList<>();
			localIndices = new ArrayList<>();
			localLookaheads = new ArrayList<>();
			for(int i = 0; i < productions.size(); i++) {
				if(step.equals(Function.firstTermFromInd(productions.get(i).getRight(), indices.get(i), G))) {
					localProductions.add(productions.get(i));
					localIndices.add(indices.get(i) + step.length());
					localLookaheads.add(lookaheads.get(i));
				}
			}
			DK_1_Function.makeEpsilonMoves(productions, indices, lookaheads, G);
			//System.out.println(localProductions + " " + localIndices + " - " + step);
			if(localProductions.size() > 0) {
				if(!states.contains(localProductions.toString() + localIndices.toString())) {
					indicator = indicator & check(localProductions, localIndices, localLookaheads, G, states);
				}
			}
		}
		return indicator;
	}
}
