package fr.openium.specification;

import java.util.ArrayList;
import java.util.HashMap;

import fr.openium.components.ContentProviderComponent;
import fr.openium.specification.config.Extra;
import fr.openium.specification.xml.ManifestData;
import fr.openium.specification.xml.ManifestData.Intent;

/**
 * Component format present in AndroidManifetsFile
 * */

public abstract class IAbstractComponent {
	/**
	 * the name of the component it is used as its ID
	 * */
	protected final String mName;
	protected final boolean mIsExported;
	protected ArrayList<Intent> mIntents = new ArrayList<ManifestData.Intent>();
	protected boolean mHasIntentFilter = false;
	protected boolean mHasInternalIntent = false;
	protected boolean mAccessContentProvider;
	protected ArrayList<ContentProviderComponent> mProviderComponents = new ArrayList<ContentProviderComponent>();
	protected HashMap<String, Extra> mReceiveExtra = new HashMap<String, Extra>();

	public IAbstractComponent(String name, boolean exported) {
		mName = name;
		mIsExported = exported;
	}

	public IAbstractComponent(String name) {
		mName = name;
		mIsExported = false;
	}

	public String getName() {
		return mName;
	}

	public boolean isExported() {
		return mIsExported;
	}

	/** set the List of Handled Intents */
	public void addIntent(Intent intent) {
		mIntents.add(intent);

	}

	public boolean hasIntentFilter() {
		return mHasIntentFilter;
	}

	public void setHasIntentFilter(boolean mHasIntentFilter) {
		this.mHasIntentFilter = mHasIntentFilter;
	}

	public void setIntent(ArrayList<Intent> intent) {
		mIntents.addAll(intent);

	}

	public ArrayList<Intent> getIntent() {
		// TODO Auto-generated method stub
		return mIntents;
	}

	public abstract String getComponentType();

	/**
	 * Information about the content provider accessed through teh component
	 */
	public void setAccessToCP(boolean access) {
		mAccessContentProvider = access;

	}

	public boolean getAccessToCp() {
		return mAccessContentProvider;
	}

	public boolean hasInternalIntent() {
		return mHasInternalIntent;
	}

	public ArrayList<ContentProviderComponent> getContentProviders() {
		return mProviderComponents;
	}

	public void setContentProviders(ContentProviderComponent cp) {
		mProviderComponents.add(cp);

	}

}
