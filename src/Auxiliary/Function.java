package Auxiliary;

import java.util.ArrayList;
import java.util.HashSet;

import Grammar.Grammar;
import Grammar.Nonterminal;
import Grammar.Production;
import Grammar.Terminal;

public class Function {
	//Checks whether a string equals another string starting from some index
	public static boolean isSubstringFromInd(String str, int ind, String substr) {
		if(str == null || substr == null) {
			System.out.println("Auxiliary.Function.isSubstring null exeption");
			return false;
		}
		if(str.length() - ind < substr.length()) {
			return false;
		}
		for(int i = 0; i < substr.length(); i++) {
			if(str.charAt(ind + i) != substr.charAt(i)) {
				return false;
			}
		}
		return true;
	}
	//Splits the string by some substring and places the parts in a ArrayList
	public static ArrayList<String> splitBySubstring(String str, String substr){
		if(str == null || substr == null) {
			System.out.println("Auxiliary.Function.splitBySubstring null exeption");
			return new ArrayList<String>();
		}
		ArrayList<String> parts = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for(int ind = 0; ind < str.length(); ind++) {
			if(isSubstringFromInd(str, ind, substr)) {
				if(sb.length() != 0) {
					parts.add(new String(sb.toString()));
				}
				sb.setLength(0);
				ind = ind + substr.length() - 1;
			} else {
				sb.append(str.charAt(ind));
			}
		}
		if(sb.length() != 0) {
			parts.add(new String(sb.toString()));
		}
		return parts;
	}
	//Split by Nonterminals
	public static ArrayList<String> splitByNonterminals(String str, ArrayList<Nonterminal> nonterminals){
		//Replace nonterminals with "#"
		if(str == null || nonterminals == null) {
			System.out.println("Auxiliary.Function.splitByNonterminals null exeption");
			return new ArrayList<String>();
		}
		StringBuilder sb = new StringBuilder(str);
		for(Nonterminal n : nonterminals) {
			for(int i = 0; i < sb.length(); i++) {
				if(isSubstringFromInd(sb.toString(), i, n.getString())) {
					sb.replace(i, i + n.getString().length(), "#");
				}
			}
		}
		return splitBySubstring(sb.toString(), "#");
	}
	//Remove Simple terminals from the string
	public static ArrayList<String> extractTerminalsFromCompoundString(String str, ArrayList<Terminal> terminals) {
		if(str == null || terminals == null) {
			System.out.println("Auxiliary.Function.extractTerminalsFromCompoundString null exeption");
			return new ArrayList<String>();
		}
		StringBuilder sb = new StringBuilder(str);
		for(Terminal n : terminals) {
			if(n.getString().length() >= str.length())continue;
			for(int i = 0; i < sb.length(); i++) {
				if(isSubstringFromInd(sb.toString(), i, n.getString())) {
					sb.replace(i, i + n.getString().length(), "#");
				}
			}
		}
		return splitBySubstring(sb.toString(), "#");
	}
	//Checks whether a string is a terminal in grammar G
	public static boolean isTerminal(String str, Grammar G) {
		if(str == null || G == null) {
			System.out.println("Auxiliary.Function.isTerminal null exeption");
			return false;
		}
		for(Terminal t : G.getTerminals()) {
			if(t.getString().equals(str)) {
				return true;
			}
		}
		return false;
	}
	//Checks whether a string is a nonterminal in grammar G
	public static boolean isNonterminal(String str, Grammar G) {
		if(str == null || G == null) {
			System.out.println("Auxiliary.Function.isNonterminal null exeption");
			return false;
		}
		for(Nonterminal n : G.getNonterminals()) {
			if(n.getString().equals(str)) {
				return true;
			}
		}
		return false;
	}
	//Find the first terminal or nonterminal in the string from some index
	public static String firstTermFromInd(String str, int ind, Grammar G) {
		if(str == null || G == null) {
			System.out.println("Auxiliary.Function.firstTermFromInd null exeption");
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(int i = ind; i < str.length(); i++) {
			sb.append(str.charAt(i));
			if(isTerminal(sb.toString(), G) || isNonterminal(sb.toString(), G)){
				return sb.toString();
			}
		}
		return null;
	}
	//Return HashSet of possible steps to proceed
	public static HashSet<String> possibleSteps(ArrayList<Production> productions, ArrayList<Integer> indices, Grammar G){
		if(productions == null || indices == null || G == null) {
			System.out.println("Auxiliary.Function.firstTermFromInd null exeption");
			return null;
		}
		HashSet<String> steps = new HashSet<String>();
		for(int i = 0; i < productions.size(); i++) {
			String step = Function.firstTermFromInd(productions.get(i).getRight(), indices.get(i).intValue(), G);
			if(step != null)steps.add(step);
		}
		return steps;
	}
}
