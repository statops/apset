package fr.openium.components;

import fr.openium.specification.IAbstractComponent;

/** Service Info Obtained from Android Manifest */
public class ServiceComponent extends IAbstractComponent {

	public ServiceComponent(String name, boolean exported) {
		super(name, exported);
	}

	@Override
	public String getComponentType() {

		return "service";
	}

}
