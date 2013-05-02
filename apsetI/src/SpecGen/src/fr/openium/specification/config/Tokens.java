package fr.openium.specification.config;

import java.util.ArrayList;

public class Tokens {

	public static final String AND = " & ";
	public static final String OR = " x ";
	public static final String ASSIGN = " := ";
	public static final String NOT = " # ";
	public static final String PLUS = " + ";
	public static final String MINUS = " - ";
	public static final String LPAREN = "(";
	public static final String RPAREN = ")";
	public static final String LSBRACKETS = "[";
	public static final String RSBRACKETS = "]";
	public static final String SEMICOLON = ";";
	public static final String ARROW = "->";
	public static final String QUOTE = "\"";
	public static final String EMPTY = "*";
	public static final String IN = "in(";
	public static final String COMA = ",";

	// KeyWords:
	public static final String LABEL = "[label=";
	public static final String FAIL = "fail";
	public static final String PASS = "pass";
	public static final String INC = "inconclusive";
	public static final String INTENT = "intent";

	public static ArrayList<String> SETOperators = new ArrayList<String>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5622284113961254429L;

		{
			add(AND);
			add(OR);
			add(PLUS); // union
			add(MINUS); // intersection

		}
	};
}
