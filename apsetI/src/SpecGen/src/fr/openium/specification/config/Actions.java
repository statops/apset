package fr.openium.specification.config;

import java.util.ArrayList;

public final class Actions {

	public static ArrayList<String> A_output = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8204001512925142731L;

		{
			add("android.intent.action.PICK");
			add("android.intent.action.CHOOSER");
			add("android.intent.action.GET_CONTENT");
			add("android.intent.action.INSERT_OR_EDIT");
			add("android.intent.action.PICK_ACTIVITY");
		}
	};

	public static ArrayList<String> A_quiescence = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2883087049728994221L;

		{
			add("android.intent.action.VIEW");
			add("android.intent.action.ATTACH_DATA");
			add("android.intent.action.EDIT");
			add("android.intent.action.CALL");
			add("android.intent.action.SEND");
			add("android.intent.action.SEND_MULTIPLE");
			add("android.intent.action.APP_ERROR");
			add("android.intent.action.INSERT_OR_EDIT");
			add("android.intent.action.RUN");
			add("android.intent.action.SYNC");
			add("android.intent.action.SEARCH");
			add("android.intent.action.WEB_SEARCH");
			add("android.intent.action.FACTORY_TEST");

		}
	};

	public static ArrayList<String> A_input = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6390406019361566714L;

		{
			add("android.intent.action.VIEW");
			add("android.intent.action.ATTACH_DATA");
			add("android.intent.action.EDIT");
			add("android.intent.action.PICK");
			add("android.intent.action.GET_CONTENT");
			add("android.intent.action.DIAL");
			add("android.intent.action.CALL");
			add("android.intent.action.SEND_MULTIPLE");
			add("android.intent.action.INSERT_OR_EDIT");
			add("android.intent.action.INSERT_OR_EDIT");
			add("android.intent.action.PICK_ACTIVITY");
			add("android.intent.action.SEARCH");
			add("android.intent.action.WEB_SEARCH");
		}
	};

	public static ArrayList<String> Exceptions = new ArrayList<String>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5507373302111141497L;

		{
			// add("ResourceNotFoundException");
			add("ClassCastException");
			add("NullPointerException");
			// add("IOException");
			add("IllegalArgumentException");
			add("ActivityNotFoundException");
			add("RuntimeException");
			add("Exception");

		}
	};

	public static final String QUISCIENCE = "q";

	/** to be completed during manifest Data generation */
	public static ArrayList<String> A_c = new ArrayList<String>();

}
