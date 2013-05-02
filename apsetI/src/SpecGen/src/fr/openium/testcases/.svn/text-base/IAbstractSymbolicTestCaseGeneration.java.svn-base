package fr.openium.testcases;

import fr.openium.iosts.Iosts;
import fr.openium.iosts.IostsSpecification;
import fr.openium.iosts.IostsVulnerabilities;

/**
 * Combination of IostsSpec with IostsVulneerabilitie to generate test cases:
 * 
 * - abstract :
 * 
 * - concret :
 * 
 * - Junit :
 * */
public abstract class IAbstractSymbolicTestCaseGeneration {
	protected IostsSpecification mPartialSpecification;
	protected IostsVulnerabilities mVulnerabilities;

	public IAbstractSymbolicTestCaseGeneration(IostsSpecification spec,
			IostsVulnerabilities vuln) {
		mPartialSpecification = spec;
		mVulnerabilities = vuln;
	}

	public IAbstractSymbolicTestCaseGeneration() {

	}

	public abstract Iosts generateAbstractTestCases();

	public abstract void generateConcreteTestCases();

	public abstract void generateJunitTestCases();

}
