package Grammar;

public class Nonterminal {
	//Attributes
	private String string;
	//Constructor
	public Nonterminal() {
		this.string = new String();
	}
	public Nonterminal(String str) {
		this.string = new String(str);
	}
	//Functions
	public String toString() {
		return this.string;
	}
	//Getters and Setters
	public String getString() {
		return string;
	}
	public void setString(String str) {
		this.string = str;
	}
}
