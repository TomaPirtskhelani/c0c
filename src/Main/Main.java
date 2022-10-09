package Main;

import java.util.HashSet;

import Auxiliary.DK_1_Function;
import DK.DK_0;
import DK.DK_1;
import Grammar.Grammar;
import Grammar.Nonterminal;

public class Main {

	public static void main(String[] args) {
		Grammar g = new Grammar("C:\\Users\\Student\\Desktop\\CFG\\C0 - Experiment.txt");
		g.Print();
		
		
		System.out.println();		
		System.out.println();		
	/*	
		System.out.println("DK_0 Start...");
		System.out.println("The DK(0)_test Passed = " + DK_0.DK_test(g));
	*/	
		
		System.out.println();		
		System.out.println();		
		
		System.out.println("DK_1 Start...");
		System.out.println("The DK(1)_test Passed = " + DK_1.DK_test(g));
		
	}

}
