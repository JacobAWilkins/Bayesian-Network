/*H****************************************************************
* FILENAME :        bnet.java
*
* DESCRIPTION :
*       Determines the probability of particular actions using a bayesian network 
*
* NOTES :
*       The bayesian network is described in REAMDE.md
*
*       Copyright 2019, Jacob Wilkins.  All rights reserved.
* 
* AUTHOR :    Jacob Wilkins        START DATE :    27 Apr 19
*
*H*/

import java.util.HashMap;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class bnet {
	
	public static HashMap<Character, ArrayList<Boolean>> C1;
	public static HashMap<Character, ArrayList<Boolean>> C2;
	
	public static void main(String[] args) {
		
		// Holds conditions before given
		C1 = new HashMap<Character, ArrayList<Boolean>>();
		// Holds conditions after given
		C2 = new HashMap<Character, ArrayList<Boolean>>();
		// Determines if the argument "given" has been specified
		Boolean given = false;
		
		// Make sure number of arguments is between 1 and 6
		int len = args.length;
		if (len < 1 || len > 6) {
			System.out.println("Incorrect number of arguments (Needs to be between 1 and 6)\nExiting...");
			System.exit(0);
		}
		
		// Get C1 and C2 values
		for (int i = 0; i < len; i++) {
			ArrayList<Boolean> bool = new ArrayList<Boolean>();
			String arg = args[i];
			arg = arg.toUpperCase();
			
			// Determine if the argument "given" has been specified
			if (arg.equals("GIVEN")) {
				given = true;
				continue;
			}
			
			// Determine if element is true or false
			if (arg.charAt(1) == 'T') { bool.add(true); } 
			else { bool.add(false); }
			
			// Add elements to C2 if given is specified, otherwise add them to C1
			if (!given) { C1.put(arg.charAt(0), bool); } 
			else { C2.put(arg.charAt(0), bool); }
		}
		
		// Make sure number of conditions is between 1 and 5
		if (C1.keySet().size() < 1 || C1.keySet().size() > 5) {
			System.out.println("Incorrect number of conditions (Needs to be between 1 and 5)\nExiting...");
			System.exit(0);
		}
		// Make sure number of conditions after given is between 1 and 4
		if (given && C2.keySet().size() < 1 || C2.keySet().size() > 4) {
			System.out.println("Incorrect number of given conditions (Needs to be between 1 and 4)\nExiting...");
			System.exit(0);
		}
		
		// Add remaining conditions to C1 and C2
		C1.putAll(C2);
		completeConditions();
		
		// Calculate numerator (final probability if no given argument specified)
		double numer = 0.0;
		for (int b = 0; b < C1.get('B').size(); b++) {
			for (int e = 0; e < C1.get('E').size(); e++) {
				for (int a = 0; a < C1.get('A').size(); a++) {
					for (int j = 0; j < C1.get('J').size(); j++) {
						for (int m = 0; m < C1.get('M').size(); m++) {
							double temp = computeProbability(C1.get('B').get(b), C1.get('E').get(e), C1.get('A').get(a), C1.get('J').get(j), C1.get('M').get(m));
							numer = numer + temp;
						}
					}
				}
			}
		}
		
		// Calculate denominator if there was a given statement
		double denom = 0.0;
		if (given) {
			for (int b = 0; b < C2.get('B').size(); b++) {
				for (int e = 0; e < C2.get('E').size(); e++) {
					for (int a = 0; a < C2.get('A').size(); a++) {
						for (int j = 0; j < C2.get('J').size(); j++) {
							for (int m = 0; m < C2.get('M').size(); m++) {
								double temp = computeProbability(C2.get('B').get(b), C2.get('E').get(e), C2.get('A').get(a), C2.get('J').get(j), C2.get('M').get(m));
								denom = denom + temp;
							}
						}
					}
				}
			}
		}
		
		// Calculate probability
		double prob = 0.0;
		if (given) {
			prob = numer / denom;
		} else {
			prob = numer;
		}
		
		// Format for printing probability
		DecimalFormat pf = new DecimalFormat("0.##########");
		
		// Print probability
		System.out.println("Probability =  " + pf.format(prob) + "\n");
		
	}
	
	// Adds remaining conditions to C1 and C2
	// If condition not specified, adds both true and false condition
	private static void completeConditions() {
		
		ArrayList<Boolean> bool;
		
		// Add values to C1 if not specified
		if (!(C1.containsKey('B'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C1.put('B', bool);
		}
		if (!(C1.containsKey('E'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C1.put('E', bool);
		}
		if (!(C1.containsKey('A'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C1.put('A', bool);
		}
		if (!(C1.containsKey('J'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C1.put('J', bool);
		}
		if (!(C1.containsKey('M'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C1.put('M', bool);
		}
		
		// Add values to C2 if not specified
		if (!(C2.containsKey('B'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C2.put('B', bool);
		}
		if (!(C2.containsKey('E'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C2.put('E', bool);
		}
		if (!(C2.containsKey('A'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C2.put('A', bool);
		}
		if (!(C2.containsKey('J'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C2.put('J', bool);
		}
		if (!(C2.containsKey('M'))) {
			bool = new ArrayList<Boolean>();
			bool.add(true);
			bool.add(false);
			C2.put('M', bool);
		}
		
	}
	
	// Computes joint probability of the 5 elements
	private static double computeProbability(Boolean b, Boolean e, Boolean a, Boolean j, Boolean m) {
		
		// Probability for each element
		double bProb = 0.0, eProb = 0.0, aProb = 0.0, jProb = 0.0, mProb = 0.0;
		
		// Calculate probability of element b
		if (b == true) { bProb = 0.001; }
		else { bProb = 0.999; }
		
		// Calculate probability of element e
		if (e == true) { eProb = 0.002; }
		else { eProb = 0.998; }
		
		// Calculate probability of element a
		if (b == true && e == true) { aProb = 0.95; }
		else if (b == true && e == false) { aProb = 0.94; }
		else if (b == false && e == true) { aProb = 0.29; }
		else if (b == false && e == false) { aProb = 0.001; }
		if (a == false) { aProb = 1 - aProb; }
		
		// Calculate probability of element j
		if (a == true) { jProb = 0.90; }
		else { jProb = 0.05; }
		if (j == false) { jProb = 1 - jProb; }
		
		// Calculate probability of element m
		if (a == true) { mProb = 0.70; }
		else { mProb = 0.01; }
		if (m == false) { mProb = 1 - mProb; }
		
		// Calculate joint probability
		double jointProb = bProb * eProb * aProb * jProb * mProb;
		
		return jointProb;

	}
	
}
