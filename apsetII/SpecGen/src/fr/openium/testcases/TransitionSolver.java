package fr.openium.testcases;

import fr.openium.specification.xml.ManifestData;

public class TransitionSolver {

	private IAbstractSymbolicTestCaseGeneration mTC;
	private ManifestData mManifestData;

	public TransitionSolver(IAbstractSymbolicTestCaseGeneration tc,
			ManifestData manifest) {
		mTC = tc;
		mManifestData = manifest;

	}

	public void translalateAction(String action) {

	}

}
