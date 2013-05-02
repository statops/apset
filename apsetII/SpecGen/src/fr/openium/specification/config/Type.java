package fr.openium.specification.config;

import java.util.ArrayList;

public final class Type {

	public static ArrayList<String> TYPESET = new ArrayList<String>() {
		// To be completed
		/**
		 * 
		 */
		private static final long serialVersionUID = 5082471377920550849L;

		{
			add("String");
			add("image/*");
			add("text/*");

		}
	};

	public static ArrayList<String> MIMETYPE = new ArrayList<String>();

}
