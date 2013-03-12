package fr.openium.specification.config;

import java.util.ArrayList;

public class Extra {

	private final String mKey;
	private Object mValue;
	private final ArrayList<String> mType;
	private final String mSpecAddType;

	public Extra(String key, String type) {
		mKey = key;
		mType = null;
		mValue = null;
		mSpecAddType = type;

	}

	public Extra(String key, ArrayList<String> type) {
		mKey = key;
		mType = type;
		// by default
		mValue = null;
		mSpecAddType = null;

	}

	public Extra(String key, ArrayList<String> type, Object value) {
		mKey = key;
		mType = type;
		mValue = value;
		mSpecAddType = null;

	}

	public String getKey() {
		return mKey;
	}

	public ArrayList<String> getType() {
		return mType;
	}

	public Object getValue() {
		return mValue;
	}

	public void setValue(Object value) {
		this.mValue = value;
	}

	public String getSpecAddType() {
		return mSpecAddType;
	}

}
