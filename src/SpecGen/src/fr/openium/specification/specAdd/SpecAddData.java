package fr.openium.specification.specAdd;

import java.util.ArrayList;

import fr.openium.components.ContentProviderComponent;
import fr.openium.specification.IAbstractComponent;
import fr.openium.specification.IAbstractIntent;
import fr.openium.specification.IAbstractSpecification;

public final class SpecAddData extends IAbstractSpecification {

	protected ArrayList<IAbstractIntent> mSpecAddIntents = new ArrayList<IAbstractIntent>();

	public SpecAddData() {
		super();
	}

	/**
	 * We will need this Id to match the to the appropriate Component with
	 * Manifest Data it will be the component name
	 * */

	public void setID(String id) {
		mID.add(id);
	}

	public ArrayList<String> getId() {
		ArrayList<String> id = new ArrayList<String>();
		for (IAbstractIntent intent : mSpecAddIntents) {
			SpecAddIntent temp = (SpecAddIntent) intent;
			id.add(temp.getComponentSource().getName());
		}
		return id;
	}

	public void setProviders(ContentProviderComponent cp) {
		if (!mProviders.contains(cp)) {
			mProviders.add(cp);
		}

	}

	@Override
	public ArrayList<IAbstractIntent> getIntents() {
		return mSpecAddIntents;
	}

	public void setSpecAddIntent(SpecAddIntent intent) {
		mSpecAddIntents.add(intent);

	}

	/**
	 * Return the lis of all component describe in the SpecAdd. It corresponds
	 * then to the Component source of each SpecIntent
	 * */
	@Override
	public ArrayList<IAbstractComponent> getComponents() {
		return mComponents;
	}

	public void setComponents(SpecAddComponent comp) {
		mComponents.add(comp);
	}

	@Override
	public ArrayList<ContentProviderComponent> getProviderComponents() {

		for (IAbstractIntent intent : mSpecAddIntents) {
			SpecAddIntent temp = (SpecAddIntent) intent;
			SpecAddComponent component = temp.getComponentSource();
			if (!mProviders.contains(component.getContentProviders())) {
				mProviders.addAll(component.getContentProviders());
			}

		}
		return mProviders;
	}

	public void setPackageName(String name) {
		mPackageName = name;
	}

	public String getPackageName() {
		return mPackageName;

	}

	public static class SpecAddIntent extends IAbstractIntent {

		private SpecAddComponent mComponentSource;
		private SpecAddComponent mTargetComponent;

		public SpecAddIntent(String action) {
			super(action);
			// TODO Auto-generated constructor stub
		}

		public SpecAddIntent() {
			super(null);
		}

		public SpecAddComponent getComponentSource() {
			return mComponentSource;
		}

		public void setComponentSource(SpecAddComponent mComponentSource) {
			this.mComponentSource = mComponentSource;
		}

		public SpecAddComponent getTargetComponent() {
			return mTargetComponent;
		}

		public void setTargetComponent(SpecAddComponent mTargetComponent) {
			this.mTargetComponent = mTargetComponent;
		}

		public String toString() {
			return mComponentSource.getName()
					.concat("  target " + mTargetComponent.getName())
					.concat("\n" + "intent :" + "explicit")
					.concat("\n" + "extra" + getExtra().toString());

		}

	}

}
