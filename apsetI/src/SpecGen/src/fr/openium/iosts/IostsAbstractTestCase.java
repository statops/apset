package fr.openium.iosts;

import java.util.ArrayList;

/**
 * Iosts Representation of an abstract test case
 */
public class IostsAbstractTestCase extends Iosts {
	private IostsLocation[] mInitLocation;

	public IostsAbstractTestCase() {
		super();
	}

	public IostsAbstractTestCase(String name, String type) {
		super(name);
		setType(type);
	}

	@Override
	public String getGuard(String transition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAction(String transition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAssignments(String transition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IostsTransition getIostsTransition(String transition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		mType = type;

	}

	@Override
	public void setInitLocation(IostsLocation iosts) {
		// Do nothing, initial location is a couple of location

	}

	public void setInitLocation(IostsLocation initA, IostsLocation initB) {
		mInitLocation = new IostsLocation[] { initA, initB };
	}

	public IostsLocation[] getInitCombinedLocation() {
		return mInitLocation;
	}

	public void addTranstion(IostsTransition tr) {
		mTransitions.add(tr);

	}

	public void addTranstion(ArrayList<IostsTransition> tr) {
		mTransitions.addAll(tr);

	}
}
