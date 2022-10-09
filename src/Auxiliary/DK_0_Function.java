package Auxiliary;

import java.util.ArrayList;
import java.util.HashSet;

import Grammar.Grammar;
import Grammar.Production;

public class DK_0_Function {
	//Add nonterminal productions
	public static void makeEpsilonMoves(ArrayList<Production> productions, ArrayList<Integer> indices, Grammar G) {
		ArrayList<Production> newProductions = new ArrayList<>();
		if(productions == null || indices == null || G == null) {
			System.out.println("Auxiliary.DK_0_Function.makeEpsilonMoves null exeption");
			return;
		}
		//Do not duplicate productions
		HashSet<String> check = new HashSet<>();
		for(int i = 0; i < productions.size(); i++) {
			check.add(productions.get(i).toString() + indices.get(i).toString());
		}
		for(int i = 0; i < productions.size(); i++) {
			String step = Function.firstTermFromInd(productions.get(i).getRight(), indices.get(i).intValue(), G);
			if(step != null && Function.isNonterminal(step, G)) {
				for(Production p : G.getProductions()) {
					if(p.getLeft().equals(step)) {
						if(!check.contains(p.toString() + "0")) {
							newProductions.add(p);
							check.add(p.toString() + "0");								
						}
					}
				}
			}
		}
		for(int i = 0; i < newProductions.size(); i++) {
			productions.add(newProductions.get(i));
			indices.add(0);
		}
		if(newProductions.size() > 0)makeEpsilonMoves(productions, indices, G);
	}
	//Check how many production rules are done
	public static int countEndedRules(ArrayList<Production> productions, ArrayList<Integer> indices) {
		if(productions == null || indices == null) {
			System.out.println("DK_0_Function.countEndedRules null exeption");
			return 0;
		}
		int count = 0;
		for(int i = 0; i < productions.size(); i++) {
			if(productions.get(i).getRight().length() == indices.get(i).intValue()) {
				count++;
			}
		}
		return count;
	}
	//Return false if after the dot a terminal appears
	public static boolean checkTerminalAfterDot(ArrayList<Production> productions, ArrayList<Integer> indices, Grammar G) {
		if(productions == null || indices == null || G == null) {
			System.out.println("DK_0_Function.checkDottedRule null exeption");
			return true;
		}
		for(int i = 0; i < productions.size(); i++) {
			String str = Function.firstTermFromInd(productions.get(i).getRight(), indices.get(i).intValue(), G);
			if(str != null && Function.isTerminal(str, G)) {
				return false;
			}
		}
		return true;
	}
}
