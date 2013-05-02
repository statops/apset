package fr.openium.components;

import java.util.ArrayList;

import fr.openium.specification.IAbstractComponent;

/**
 * Provider Info Obtained form the Android MAnifest
 * 
 */
public class ContentProviderComponent extends IAbstractComponent {

	private ArrayList<String> mAuthorities = new ArrayList<String>();

	public ContentProviderComponent(String name) {
		super(name);
	}

	public ArrayList<String> getAuthorities() {
		return mAuthorities;
	}

	public void setAuthorities(String authorities) {
		this.mAuthorities.add(authorities);
	}

	@Override
	public String getComponentType() {
		// TODO Auto-generated method stub
		return "provider";
	}

}
