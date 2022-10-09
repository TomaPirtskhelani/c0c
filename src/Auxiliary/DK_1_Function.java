package Auxiliary;

import java.util.ArrayList;
import java.util.HashSet;

import Grammar.Grammar;
import Grammar.Nonterminal;
import Grammar.Production;

public class DK_1_Function {
	//Add nonterminal productions + their lookaheads
	public static void makeEpsilonMoves(ArrayList<Production> productions, ArrayList<Integer> indices,
														ArrayList<ArrayList<String>> lookaheads, Grammar G) {
		if(productions == null || indices == null || lookaheads == null || G == null) {
			System.out.println("Auxiliary.DK_1_Function.makeEpsilonMoves null exeption");
			return;
		}
		ArrayList<Production> newProductions = new ArrayList<>();
		ArrayList<ArrayList<String>> newLookaheads = new ArrayList<>();
		//Do not duplicate productions
		HashSet<String> check = new HashSet<>();
		for(int i = 0; i < productions.size(); i++) {
			check.add(productions.get(i).toString() + indices.get(i).toString());
		}
		for(int i = 0; i < productions.size(); i++) {
			String step = Function.firstTermFromInd(productions.get(i).getRight(), indices.get(i).intValue(), G);
			if(step != null && Function.isNonterminal(step, G)) {
				ArrayList<String> lookaheadTerminals = findLookaheadTerminals(productions.get(i), indices.get(i), lookaheads.get(i), G);
				for(Production p : G.getProductions()) {
					if(p.getLeft().equals(step)) {
						if(!check.contains(p.toString() + "0")) {
							newProductions.add(p);
							newLookaheads.add(lookaheadTerminals);
							check.add(p.toString() + "0");
						}
					}
				}
			}
		}
		for(int i = 0; i < newProductions.size(); i++) {
			productions.add(newProductions.get(i));
			indices.add(0);
			lookaheads.add(newLookaheads.get(i));
		}
		if(newProductions.size() > 0)makeEpsilonMoves(productions, indices, lookaheads, G);
	}
	//Find lookahead terminals for this position
	public static ArrayList<String> findLookaheadTerminals(Production production, Integer index,
																ArrayList<String> lookahead, Grammar G){
		if(production == null || lookahead == null || G == null) {
			System.out.println("Auxiliary.DK_1_Function.findLookaheadTerminals null exeption");
			return null;
		}
		ArrayList<String> LocalLookahead = new ArrayList<>();
		String step = Function.firstTermFromInd(production.getRight(), index.intValue(), G);
		//1) Check what terminal comes after this step in this production
		String v = Function.firstTermFromInd(production.getRight(), index + step.length(), G);
		if(v != null && v.equals("epsilon")) {
			for(String str : lookahead) {
				LocalLookahead.add(str);
			}
		} else {
			if(v != null && Function.isTerminal(v, G)) {
				LocalLookahead.add(v);
			}
		}
		//2) Check self refering rules E -> E + T
		for(Production p : G.getProductions()) {
			if(p.getLeft().equals(step)) {
				String str = Function.firstTermFromInd(p.getRight(), 0, G);
				if(str != null && str.equals(step)) {
					str = Function.firstTermFromInd(p.getRight(), str.length(), G);
					if(str != null) {
						if(Function.isTerminal(str, G)) {
							LocalLookahead.add(str);
						}
					}
				}
			}
		}
		//3) not direct terminals
		v = Function.firstTermFromInd(production.getRight(), index + step.length(), G);
		if(v != null && Function.isNonterminal(v, G)) {
			for(String str : terminalsFromNonterminal(new Nonterminal(v), new HashSet<String>(), G)) {
				LocalLookahead.add(str);
			}
		}
		return LocalLookahead;
	}
	//Find first terminals that can be derived from this nonterminal *for epsilon symbols this implementation is not complete
	public static HashSet<String> terminalsFromNonterminal(Nonterminal nonterminal, HashSet<String> check, Grammar G){
		if(nonterminal == null || check == null || G == null) {
			System.out.println("Auxiliary.DK_1_Function.terminalsFromNonterminal null exeption");
			return null;
		}
		check.add(nonterminal.getString());
		HashSet<String> terminals = new HashSet<>();
		for(Production p : G.getProductions()) {
			if(p.getLeft().equals(nonterminal.getString())) {
				String str = Function.firstTermFromInd(p.getRight(), 0, G);
				if(str != null) {
					if(Function.isTerminal(str, G)) {
						terminals.add(str);
					} else {
						if(!check.contains(str)) {
							for(String s : terminalsFromNonterminal(new Nonterminal(str), check, G)) {
								terminals.add(s);
							}
						}
					}
				}
			}
		}
		return terminals;
	}
	//Return true if the dotted rule is completed
	public static boolean isCompletedRule(Production production, Integer index) {
		if(production == null) {
			System.out.println("Auxiliary.DK_1_Function.isCompletedRule null exeption");
			return false;
		}
		return (production.getRight().length() == index);
	}
	//Return true if a1 and a2 have a common symbol
	public static boolean commonLookahead(ArrayList<String> a1, ArrayList<String> a2) {
		for(String str : a1) {
			if(a2.contains(str)) {
				return true;
			}
		}
		return false;
	}
	//Return true if a1 immediately follows the dot
	public static boolean lookaheadFollowsTheDot(Production production, Integer index,
																		ArrayList<String> lookahead, Grammar G) {
		String str = Function.firstTermFromInd(production.getRight(), index, G);
		if(str != null && Function.isTerminal(str, G)) {
			if(lookahead.contains(str)) {
				return true;
			}
		}
		return false;
	}
}
