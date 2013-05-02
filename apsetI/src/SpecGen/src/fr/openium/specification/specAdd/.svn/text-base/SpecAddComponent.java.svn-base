package fr.openium.specification.specAdd;

import java.util.HashMap;

import fr.openium.specification.IAbstractComponent;
import fr.openium.specification.config.Extra;

public class SpecAddComponent extends IAbstractComponent {

	public SpecAddComponent(String name) {
		super(name);

	}

	@Override
	public String getComponentType() {

		return null;
	}

	public void setHasInternalIntent(boolean b) {
		mHasInternalIntent = b;
	}

	public void setExtra(Extra extra) {
		mReceiveExtra.put(extra.getKey(), extra);

	}

	public HashMap<String, Extra> getReceivedExtra() {
		return mReceiveExtra;
	}

}
