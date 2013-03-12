package fr.openium.iosts;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Iosts {

	protected String mName;
	protected String mPackage;
	/**
	 * the type :
	 * 
	 * - components : activity or services
	 * 
	 * - vulnerabilities
	 * 
	 * */
	protected String mType;
	protected IostsLocation mInit;

	/** Variables */
	protected static HashMap<String, Object> mVariables = new HashMap<String, Object>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2508246138810732471L;

		{
			put("vAction", null);
			put("vCategory", null);
			put("vData", null);
			put("vExtra", null);
			put("vActivity", null);
			put("vService", null);
			put("vHierarchyViewer", null);
		}
	};

	public Iosts(String name) {
		setName(name);

	}

	protected Iosts() {

	}

	protected ArrayList<IostsTransition> mTransitions = new ArrayList<IostsTransition>();

	/**
	 * @param transition
	 *            obtain from iostsparser
	 * @return - the string value of the guard - null if there is no guard
	 */
	public abstract String getGuard(String transition);

	/**
	 * @param transition
	 *            obtain from iostsparser
	 * @return the string value of the action - cannot be null;
	 */
	public abstract String getAction(String transition);

	/**
	 * @param transition
	 *            obtain from iostsparser
	 * @return the string value of the assignment - null when there is no
	 *         Assignments
	 */
	public abstract String getAssignments(String transition);

	/**
	 * The method getIostsTransitions returns the source and destination
	 * location of each transition, action name and type,the guard and the
	 * assignments associated to the transitions.
	 * 
	 * @param transition
	 *            in String
	 * @return the object IostsTransition
	 */
	public abstract IostsTransition getIostsTransition(String transition);

	/**
	 * 
	 * 
	 * @param iosts
	 *            the iosts representation
	 * 
	 * @return the lis of all locations
	 * 
	 * */
	public ArrayList<IostsLocation> getIostsLocations() {

		// retrieved from All transitions
		ArrayList<IostsLocation> locations = new ArrayList<IostsLocation>();
		for (IostsTransition transition : mTransitions) {
			IostsLocation source = transition.getSource();
			IostsLocation target = transition.getTarget();
			locations.add(source);
			locations.add(target);

		}

		ArrayList<IostsLocation> temp = new ArrayList<IostsLocation>();
		for (IostsLocation loc : locations) {
			if (!temp.contains(loc)) {
				temp.add(loc);
			}
		}
		return temp;
	}

	/**
	 * @param transition
	 *            obtain from iostsparser
	 * 
	 * @return the transition
	 * 
	 * */
	protected void setTransitionFromString(IostsLocation source,
			IostsLocation target, String transition) {
		IostsTransition tr = new IostsTransition(source, transition, target);
		tr.setAction(getAction(transition));
		tr.setGuard(getGuard(transition));
		tr.setAssignments(getAssignments(transition));
		mTransitions.add(tr);

	}

	/**
	 * @param transition
	 *            obtain from iostsparser
	 * 
	 * @return the transition
	 * 
	 * */
	protected void setTransition(IostsLocation source, IostsLocation target,
			String action, String guard, String assigment) {
		IostsTransition tr = new IostsTransition(source, target);
		tr.setAction(action);
		tr.setAssignments(guard);
		tr.setGuard(assigment);
		mTransitions.add(tr);

	}

	public String getName() {
		return mName;
	}

	protected void setName(String mName) {
		this.mName = mName;
	}

	/** @return the component type */
	public String getType() {
		return mType;
	}

	protected abstract void setType(String mType);

	protected abstract void setInitLocation(IostsLocation iosts);

	public IostsLocation getInitLocation() {
		return mInit;
	}

	/**
	 * @author Stassia
	 * @return all transitions in the iosts object such as each transition
	 *         include location source and target location the action , guard,
	 *         and Assignment of the corresponding transitions.
	 * */
	public ArrayList<IostsTransition> getTransitions() {
		return mTransitions;
	}

	public String getPackageName() {
		return mPackage;
	}

	public void setPackageName(String name) {
		mPackage = name;
		;
	}
}
