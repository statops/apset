package fr.openium.iosts;

import fr.openium.specification.config.Actions;
import fr.openium.specification.config.Tokens;

/**
 * @author Stassia
 * @version alpha 1
 * 
 *          This class represent a simple iosts transition.
 * 
 *          Next step : guard, assignment, and Action must be represented by
 *          more detailed object
 * 
 * 
 * */
public class IostsTransition {

	/** Source and target for an IOsts */
	private final IostsLocation mSource;
	private final IostsLocation mTarget;

	/** Source and target for Iosts Abstract testCase */
	private final IostsLocation[] mSourceTab;
	private final IostsLocation[] mTargetTab;
	private String mLabel = null;

	private String mGuardString;
	private String mActionString;
	private String mAssignmentsString;

	public IostsTransition(IostsLocation a, String label, IostsLocation b) {
		mSource = a;
		mTarget = b;
		mLabel = label;
		mSourceTab = null;
		mTargetTab = null;

	}

	public IostsTransition(IostsLocation a, IostsLocation b) {
		mSource = a;
		mTarget = b;
		mSourceTab = null;
		mTargetTab = null;

	}

	/** transistion set for combined Iosts */
	public IostsTransition(IostsLocation sourceA, IostsLocation sourceB,
			IostsLocation targetA, IostsLocation targetB) {
		mSourceTab = new IostsLocation[] { sourceA, sourceB };
		mTargetTab = new IostsLocation[] { targetA, targetB };
		mSource = null;
		mTarget = null;

	}

	/**
	 * Here, transition include source, target, label
	 * */

	public IostsTransition(String transition, Iosts iosts) {
		/** useless variables */
		mSourceTab = null;
		mTargetTab = null;

		String source = transition.substring(0,
				transition.indexOf(Tokens.ARROW, 0));
		String target = transition.substring(
				transition.indexOf(Tokens.ARROW, 0) + 2,
				transition.indexOf(Tokens.LSBRACKETS, 0));
		String label = transition.substring(
				transition.indexOf(Tokens.LABEL) + 7,
				transition.indexOf(Tokens.RSBRACKETS + Tokens.SEMICOLON));
		mSource = new IostsLocation(source);
		mTarget = new IostsLocation(target);
		mLabel = label;

		if (iosts instanceof IostsVulnerabilities) {
			IostsVulnerabilities iVul = (IostsVulnerabilities) iosts;
			setAction(iVul.getAction(mLabel));
			setGuard(iVul.getGuard(mLabel));
			setAssignments(iVul.getAssignments(mLabel));

		} else if (iosts instanceof IostsSpecification) {
			IostsSpecification ioSpec = (IostsSpecification) iosts;
			setAction(ioSpec.getAction(mLabel));
			setGuard(ioSpec.getGuard(mLabel));
			setAssignments(ioSpec.getAssignments(mLabel));

		} else {
			mLabel = null;
		}

	}

	public IostsLocation getSource() {
		return mSource;
	}

	public void setLabel() {
		mLabel = mActionString + ", " + mGuardString + " ,"
				+ mAssignmentsString;
	}

	public IostsLocation getTarget() {
		return mTarget;
	}

	public String getLabel() {
		if (mLabel != null) {
			return mLabel;
		} else {
			setLabel();
			return mLabel;
		}
	}

	public String toString() {
		if (mSource != null && mTarget != null) {
			return mSource.getName() + Tokens.ARROW + mTarget.getName()
					+ Tokens.LABEL + Tokens.QUOTE + getLabel() + Tokens.QUOTE
					+ Tokens.RSBRACKETS + Tokens.SEMICOLON;
		} else if (mSourceTab != null && mTargetTab != null) {
			return mSourceTab[0].getName() + mSourceTab[1].getName()
					+ Tokens.ARROW + mTargetTab[0].getName()
					+ mTargetTab[1].getName() + Tokens.LABEL + Tokens.QUOTE
					+ getLabel() + Tokens.QUOTE + Tokens.RSBRACKETS
					+ Tokens.SEMICOLON;

		}
		return null;

	}

	public String getGuardString() {
		return mGuardString;
	}

	public void setGuard(String mGuard) {
		// String format
		this.mGuardString = Tokens.LSBRACKETS + mGuard + Tokens.RSBRACKETS;
		// peeled format

	}

	public String getActionString() {
		return mActionString;
	}

	public void setAction(String mAction) {
		// String format
		this.mActionString = mAction;
		// peeled format

	}

	public String getAssignmentsString() {
		return mAssignmentsString;
	}

	public void setAssignments(String mAssignments) {
		// String format
		this.mAssignmentsString = mAssignments;
		// peeled format
	}

	/**
	 * Combine the Action String of 2 transitions
	 * 
	 * @param a
	 *            : the action string of a transition A
	 * @param b
	 *            : the action string of a transition B
	 * */
	public void combineActionString(String a, String b) {

		String indicator = b.substring(0, 1);

		if (a.contains(IostsSpecification.INPUT + Tokens.INTENT)
				|| b.contains(IostsSpecification.OUTPUT + Tokens.INTENT)) {
			setAction(indicator + a.substring(1));
		} else {
			String aa = a.substring(1).replaceAll("\\s", "");
			String bb = b.substring(1).replaceAll("\\s", "");
			if (Actions.Exceptions.contains(bb)) {
				setAction(indicator + b.substring(1));
			} else if (aa.equalsIgnoreCase(bb)) {
				setAction(indicator + a.substring(1));
			} else {
				setAction(indicator + a.substring(1) + Tokens.AND
						+ b.substring(1));
			}
		}

	}

	/**
	 * Combine the Guard String of 2 transitions
	 * 
	 * @param a
	 *            : the guard string of a transition A
	 * @param b
	 *            : the guard string of a transition B
	 * */
	public void combineGuardString(String a, String b) {
		if (a.contains(Tokens.EMPTY) && b.equals(a)) {
			setGuard(Tokens.EMPTY);
		} else if (b.contains(Tokens.EMPTY)) {
			setGuard(a.substring(1, a.length() - 1));
		} else if (a.contains(Tokens.EMPTY)) {
			setGuard(b.substring(1, b.length() - 1));
		} else {
			setGuard(a + Tokens.AND + b);
		}

	}

	/**
	 * Combine the Assignment String of 2 transitions
	 * 
	 * @param a
	 *            : the assignment string of a transition A
	 * @param b
	 *            : the assignment string of a transition B
	 * */
	public void combineAssignString(String a, String b) {
		if (a.contains(Tokens.EMPTY) && b.equals(a)) {
			setAssignments(Tokens.EMPTY);
		} else if (b.contains(Tokens.EMPTY)) {
			setAssignments(a);
		} else if (a.contains(Tokens.EMPTY)) {
			setAssignments(b);
		} else {
			setAssignments(a + Tokens.AND + b);
		}

	}

	/**
	 * Combine the Action of 2 transitions
	 * 
	 * @param a
	 *            : the action of a transition A
	 * @param b
	 *            : the action of a transition B
	 * */
	public void combineAction(TransitionAction a, TransitionAction b) {

	}

	/**
	 * Combine the Guard of 2 transitions
	 * 
	 * @param a
	 *            : the guard of a transition A
	 * @param b
	 *            : the guard of a transition B
	 * */
	public void combineGuard(TransitionGuard a, TransitionGuard b) {

	}

	/**
	 * Combine the Assignment of 2 transitions
	 * 
	 * @param a
	 *            : the assignment of a transition A
	 * @param b
	 *            : the assignment of a transition B
	 * */
	public void combineAssignment(TransitionAssignment a, TransitionAssignment b) {

	}

	public class TransitionGuard {

		// get the corresponding

		public TransitionGuard(String Guard) {

		}

	}

	public class TransitionAction {

		public TransitionAction(String action) {

		}

	}

	public class TransitionAssignment {

		public TransitionAssignment(String assign) {

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mActionString == null) ? 0 : mActionString.hashCode());
		result = prime
				* result
				+ ((mAssignmentsString == null) ? 0 : mAssignmentsString
						.hashCode());
		result = prime * result
				+ ((mGuardString == null) ? 0 : mGuardString.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IostsTransition)) {
			return false;
		}
		IostsTransition other = (IostsTransition) obj;
		if (mActionString == null) {
			if (other.mActionString != null) {
				return false;
			}
		} else if (!mActionString.equals(other.mActionString)) {
			return false;
		}
		if (mAssignmentsString == null) {
			if (other.mAssignmentsString != null) {
				return false;
			}
		} else if (!mAssignmentsString.equals(other.mAssignmentsString)) {
			return false;
		}
		if (mGuardString == null) {
			if (other.mGuardString != null) {
				return false;
			}
		} else if (!mGuardString.equals(other.mGuardString)) {
			return false;
		}
		return true;
	}

	public IostsLocation[] getSourceTab() {
		return mSourceTab;
	}

	public IostsLocation[] getTargetTab() {
		return mTargetTab;
	}

	public boolean isInputAction() {
		if (mActionString.substring(0, 1).equals(IostsSpecification.OUTPUT)
				&& mSourceTab != null && mTargetTab != null) {
			return true;
		} else {
			return false;
		}
	}
}