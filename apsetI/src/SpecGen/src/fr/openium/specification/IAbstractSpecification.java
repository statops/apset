package fr.openium.specification;

import java.util.ArrayList;

import fr.openium.components.ContentProviderComponent;

public abstract class IAbstractSpecification {

	protected ArrayList<String> mID;

	protected ArrayList<ContentProviderComponent> mProviders = new ArrayList<ContentProviderComponent>();

	protected ArrayList<IAbstractComponent> mComponents = new ArrayList<IAbstractComponent>();

	protected String mPackageName;

	public IAbstractSpecification() {

	}

	public abstract ArrayList<IAbstractIntent> getIntents();

	public abstract ArrayList<IAbstractComponent> getComponents();

	public abstract ArrayList<ContentProviderComponent> getProviderComponents();

}
