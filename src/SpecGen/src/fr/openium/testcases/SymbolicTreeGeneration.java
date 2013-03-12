package fr.openium.testcases;

import java.util.ArrayList;

import fr.openium.iosts.Iosts;
import fr.openium.iosts.IostsAbstractTestCase;
import fr.openium.iosts.IostsLocation;
import fr.openium.iosts.IostsSpecification;
import fr.openium.iosts.IostsTransition;
import fr.openium.iosts.IostsVulnerabilities;

/***
 * 
 * @author Stassia
 * 
 */
public class SymbolicTreeGeneration extends IAbstractSymbolicTestCaseGeneration {

	private IostsAbstractTestCase mTestCase;

	public SymbolicTreeGeneration(IostsSpecification spec,
			IostsVulnerabilities vuln) {
		super(spec, vuln);

	}

	public SymbolicTreeGeneration() {
		super();
	}

	@Override
	public Iosts generateAbstractTestCases() {
		mTestCase = new IostsAbstractTestCase(mPartialSpecification.getName(),
				mPartialSpecification.getType());
		IostsAbstractTestCase sp = combine(mPartialSpecification,
				mVulnerabilities);
		return sp;
	}

	/**
	 * 
	 * combine methode = synchrone product + closure
	 */

	private IostsAbstractTestCase combine(
			IostsSpecification partialSpecification,
			IostsVulnerabilities vulnerabilities) {

		/**
		 * Refinement of Specification, i.e determinisation = delete the same
		 * traces
		 */
		refine(partialSpecification);
		/**
		 * Vulnerability pattern must be consistent with specification, i.e
		 * input/output actions of pass verdict and specifications models
		 */

		/** From manifest Data , usless parameters of the intent are deleted */

		// from initial statte detect the action.
		IostsLocation specInit = partialSpecification.getInitLocation();
		IostsLocation vulnInit = vulnerabilities.getInitLocation();
		IostsAbstractTestCase aTC = new IostsAbstractTestCase(
				partialSpecification.getName(), partialSpecification.getType());
		// init location
		aTC.setInitLocation(partialSpecification.getInitLocation(),
				vulnerabilities.getInitLocation());
		// Each trace is combined with vuln.
		/** LEVEL 1 */
		for (IostsTransition spec : partialSpecification.getTransitions()) {
			// beginfrom initial state
			// and generate a TC
			// add it to the list of abstract TC.
			IostsTransition tr = null;
			if (spec.getSource().equals(specInit)) {
				/**
				 * String locationName = spec.getSource().getName() +
				 * Operators.AND + vulnInit.getName();
				 */
				// aTC.setInitLocation(new IostsLocation(locationName));
				for (IostsTransition vuln : vulnerabilities.getTransitions()) {
					if (vuln.getSource().equals(vulnInit)) {
						tr = new IostsTransition(spec.getSource(),
								vuln.getSource(), spec.getTarget(),
								vuln.getTarget());
						// process on Action Combination
						tr.combineActionString(spec.getActionString(),
								vuln.getActionString());
						// process on guard combination
						tr.combineGuardString(spec.getGuardString(),
								vuln.getGuardString());
						// process on assignment combination
						tr.combineAssignString(spec.getAssignmentsString(),
								vuln.getAssignmentsString());
						// add the transition
						aTC.addTranstion(tr);
					}

				}

			}
		}

		/** LEVEL 2 */
		ArrayList<IostsTransition> tempTransitions = new ArrayList<IostsTransition>();
		IostsTransition trTemp = null;
		for (IostsTransition tr : aTC.getTransitions()) {
			// departure: targeLocation
			IostsLocation specLocation = tr.getTargetTab()[0];
			IostsLocation vulnLocation = tr.getTargetTab()[1];
			for (IostsTransition spec : partialSpecification.getTransitions()) {
				if (spec.getSource().equals(specLocation)) {
					for (IostsTransition vuln : vulnerabilities
							.getTransitions()) {
						if (vuln.getSource().equals(vulnLocation)) {
							trTemp = new IostsTransition(spec.getSource(),
									vuln.getSource(), spec.getTarget(),
									vuln.getTarget());
							trTemp.combineActionString(spec.getActionString(),
									vuln.getActionString());
							trTemp.combineGuardString(spec.getGuardString(),
									vuln.getGuardString());
							trTemp.combineAssignString(
									spec.getAssignmentsString(),
									vuln.getAssignmentsString());
							tempTransitions.add(trTemp);

						}

					}

				}

			}

		}
		aTC.addTranstion(tempTransitions);

		return aTC;
	}

	public static void refine(IostsSpecification partialSpecification) {

		IostsLocation begin = partialSpecification.getInitLocation();
		ArrayList<IostsTransition> transitions = partialSpecification
				.getTransitions();
		IostsLocation doneFlag = null;
		/**
		 * Build traces trace. We know that for all our specification, the
		 * initial and final location is the beginning location . (for the
		 * moment)
		 */
		ArrayList<Trace> traces = new ArrayList<SymbolicTreeGeneration.Trace>();
		for (IostsTransition tr : transitions) {
			if (tr.getSource().equals(begin)) {
				Trace tempTrace = new Trace(tr);
				for (IostsTransition tr2 : transitions) {
					if (tr2.getSource().equals(tr.getTarget())
							&& !tr.getTarget().equals(doneFlag)) {
						tempTrace.setTransition(tr2);
						doneFlag = tr2.getSource();
						// return;
					}

				}
				traces.add(tempTrace);
			}
		}

		// delete repeated trace
		ArrayList<Trace> tracesTemp = new ArrayList<SymbolicTreeGeneration.Trace>();
		tracesTemp.add(traces.get(0));
		for (Trace trace : traces) {
			if (!tracesTemp.contains(trace)) {
				tracesTemp.add(trace);
			}

		}
		partialSpecification.deletAllTransition();
		for (Trace trace : tracesTemp) {
			for (IostsTransition tr : trace.getTransitions()) {
				partialSpecification.setTransitions(tr);
			}

		}

	}

	/**
	 * For each transition Action, guard and assign must be solved
	 **/
	private IostsAbstractTestCase solve(IostsAbstractTestCase tc) {
		for (IostsTransition tr : tc.getTransitions()) {

			// solve action

			// solve guard

			// solve assign
		}
		return null;

	}

	@Override
	public void generateConcreteTestCases() {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateJunitTestCases() {
		// TODO Auto-generated method stub

	}

	private static class Trace {

		private ArrayList<IostsTransition> mTransitions = new ArrayList<IostsTransition>();

		public Trace() {

		}

		public Trace(IostsTransition tr) {
			mTransitions.add(tr);
		}

		public void setTransition(IostsTransition tr) {
			mTransitions.add(tr);
		}

		public ArrayList<IostsTransition> getTransitions() {
			return mTransitions;
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
					+ ((mTransitions == null) ? 0 : mTransitions.hashCode());
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
			if (!(obj instanceof Trace)) {
				return false;
			}
			Trace other = (Trace) obj;
			if (mTransitions == null) {
				if (other.mTransitions != null) {
					return false;
				}
			} else if (!mTransitions.equals(other.mTransitions)) {
				return false;
			}
			return true;
		}

	}

}
