package fr.openium.testcases;

import java.util.HashMap;

public abstract class Variables {
	private String mName;

	private HashMap<String, Object> mValues = new HashMap<String, Object>();

	public Variables(String name) {
		setName(name);
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;

	}

}
