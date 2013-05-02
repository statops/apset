package fr.openium.specification;

import java.util.ArrayList;
import java.util.HashMap;

import fr.openium.specification.config.Extra;
import fr.openium.specification.xml.ManifestData;
import fr.openium.specification.xml.ManifestData.IntentData;

/**
 * The class representing an Intent
 */

public abstract class IAbstractIntent {

	public final static String ACTION = "action";
	public final static String CATEGORY = "category";
	public final static String URI = "uri";
	public final static String EXTRA = "extra";
	public final static String TYPE = "type";

	/** The action of the Intent */
	protected final String mAction;
	/** The category of the Intent */
	protected ArrayList<String> mCategories = new ArrayList<String>();

	/** The data of the Intent */
	protected ArrayList<IntentData> mData = new ArrayList<ManifestData.IntentData>();

	/** Extra of the Intent (Empty during the specification) */
	/** not Empty during the SpecAdd Definition */

	protected HashMap<String, Extra> mExtra = new HashMap<String, Extra>();

	/** the type of the Intent */
	protected String mIntentType;
	/**
	 * If the intent is described in the Manifest file
	 * */
	protected static final String MANIFESTINTENT = "0";

	/**
	 * If the intent is described from Additional Specification andn intern
	 * intent
	 * */
	protected static final String SPECADDINTENT = "1";

	public IAbstractIntent(String action) {
		mAction = action;

	}

	public String getAction() {
		return mAction;
	}

	public void setCategory(String category) {
		mCategories.add(category);
	}

	public ArrayList<String> getCategory() {
		return mCategories;
	}

	public HashMap<String, Extra> getExtra() {
		return mExtra;
	}

	public void setExtra(Extra extra) {

		mExtra.put(extra.getKey(), extra);
	}

	public ArrayList<IntentData> getData() {
		return mData;
	}

	public void setData(IntentData data) {
		this.mData.add(data);
	}

}
