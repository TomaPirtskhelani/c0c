package Grammar;

import java.util.ArrayList;

import Auxiliary.Function;

public class Production {
	//Attributes
	private String left;
	private String right;
	//Constructor
	public Production() {
		this.left = new String();
		this.right = new String();
	}
	public Production(String left, String right) {
		this.left = new String(left);
		this.right = new String(right);
	}
	public Production(String str) {
		//Remove transition symbol " -> " from the string
		ArrayList<String> parts = Function.splitBySubstring(str, " -> ");
		if(parts.size() < 2) {
			System.out.println("Production.Constructor error ' -> ' not found ");
		}
		Production p = new Production(parts.get(0), parts.get(1));
		this.left = p.getLeft();
		this.right = p.getRight();
	}
	//Functions
	public String toString() {
		return this.left + " -> " + this.right;
	}
	//Getters and Setters
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
}
