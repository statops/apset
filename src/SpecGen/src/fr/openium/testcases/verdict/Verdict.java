package fr.openium.testcases.verdict;

import java.util.ArrayList;

import fr.openium.specification.config.Actions;

/***
 * 
 * @author Stassia
 * 
 */
public abstract class Verdict {

	public Verdict(String name) {
		mID = name;
	}

	/** Guard **/
	protected boolean mComponentEnable = false;
	/** Guard */
	protected boolean mIsInApplicationsComponentList = false;
	/** Assign */
	protected boolean mNeedResponse = false;
	/** verdict for corresponding component */
	protected final String mID;

	public ArrayList<Object> mExpectedOutput = new ArrayList<Object>();

	/** the verdict name */
	public abstract String getVerdictName();

	/** the set of output that lead to the verdict */
	public ArrayList<Object> getVerdictSet() {
		return mExpectedOutput;
	}

	/** Add an outpout object to the verdict output set */
	public void add(Object o) {
		ArrayList<String> temp = new ArrayList<String>();
		for (Object tp : mExpectedOutput) {
			if ((tp instanceof String)
					&& Actions.Exceptions.contains((String) tp)) {
				temp.add((String) tp);
			}
		}
		
		// eviter les repetitions (exception)
		if (o instanceof String && Actions.Exceptions.contains((String) o)) {
			String value = (String) o;
			if (!temp.contains(value)) {
				mExpectedOutput.add(o);
			}
		} else {
			mExpectedOutput.add(o);
		}

	}

}
