package fr.openium.components;

import fr.openium.specification.IAbstractComponent;

/**
 * Activity info obtained from the manifest.
 */
public class ActivityComponent extends IAbstractComponent {

	public ActivityComponent(String name, boolean exported) {
		super(name, exported);
	}

	@Override
	public String getComponentType() {
		// TODO Auto-generated method stub
		return "activity";
	}
}