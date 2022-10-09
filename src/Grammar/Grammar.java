package Grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import Auxiliary.Function;

public class Grammar {
	//Attributes
	private Nonterminal start;
	private ArrayList<Terminal> terminals;
	private ArrayList<Nonterminal> nonterminals;
	private ArrayList<Production> productions;
	//Constructors
	public Grammar() {
		this.start = new Nonterminal();
		this.terminals = new ArrayList<>();
		this.nonterminals = new ArrayList<>();
		this.productions = new ArrayList<>();
	}
	public Grammar(String filePath) {
		//Read the grammar from the file
		Scanner in = new Scanner(System.in);
		try {
			in = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Add productions
		this.productions = new ArrayList<Production>();
		this.addProductions(in);
		//Add start
		this.start = new Nonterminal(this.productions.get(0).getLeft());
		//Add nonterminals
		this.nonterminals = new ArrayList<Nonterminal>();
		this.addNonterminals();
		//Add terminals
		this.terminals = new ArrayList<Terminal>();
		this.addTerminals();
	}
	//Functions
	private void addProductions(Scanner in) {
		while(in.hasNext()) {
			String str = in.nextLine();
	        Production p = new Production(str);
	        for(String right : Function.splitBySubstring(p.getRight(), " | ")) {
	        	this.productions.add(new Production(p.getLeft(), right));
	        }
		}
	}
	private void addNonterminals() {
		HashSet<String> check = new HashSet<>();
		for(Production p : this.productions) {
			if(!check.contains(p.getLeft())) {
				this.nonterminals.add(new Nonterminal(p.getLeft()));
				check.add(p.getLeft());
			}
		}
		
	}
	private void addTerminals() {
		HashSet<String> check = new HashSet<>();
		for(Production p : this.productions) {
			ArrayList<String> parts = Function.splitByNonterminals(p.getRight(), this.nonterminals);
			for(String str : parts) {
				if(!check.contains(str)) {
					this.terminals.add(new Terminal(str));
					check.add(str);
				}	
			}
		}
		//Extract terminals from compound string
		check = new HashSet<>();
		ArrayList<Terminal> simpleTerminals = new ArrayList<>();
		for(Terminal t : this.terminals) {
			ArrayList<String> parts = Function.extractTerminalsFromCompoundString(t.getString(), this.terminals);
			for(String str : parts) {
				if(!check.contains(str)) {
					simpleTerminals.add(new Terminal(str));
					check.add(str);
				}
			}
		}
		setTerminals(simpleTerminals);
	}
	//Print the Grammar
	public void Print() {
		System.out.println("Start:");
		System.out.println(this.start);
		System.out.println("Terminals:");
		for(Terminal t : this.terminals) {
			System.out.println(t);
		}
		System.out.println("Nonerminals:");
		for(Nonterminal n : this.nonterminals) {
			System.out.println(n);
		}
		System.out.println("Productions:");
		for(Production p : this.productions) {
			System.out.println(p);
		}
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Production p : this.productions) {
			sb.append(p.toString() + "\n");
		}
		return sb.toString();
	}
	//Getters and Setters
	public Nonterminal getStart() {
		return start;
	}
	public void setStart(Nonterminal start) {
		this.start = start;
	}
	public ArrayList<Terminal> getTerminals() {
		return terminals;
	}
	public void setTerminals(ArrayList<Terminal> terminals) {
		this.terminals = terminals;
	}
	public ArrayList<Nonterminal> getNonterminals() {
		return nonterminals;
	}
	public void setNonterminals(ArrayList<Nonterminal> nonterminals) {
		this.nonterminals = nonterminals;
	}
	public ArrayList<Production> getProductions() {
		return productions;
	}
	public void setProductions(ArrayList<Production> productions) {
		this.productions = productions;
	}
}
