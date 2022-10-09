package Grammar;

public class Terminal {
	//Attributes
	private String string;
	//Constructor
	public Terminal() {
		this.string = new String();
	}
	public Terminal(String str) {
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
