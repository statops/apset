package fr.openium.testcases.verdict;

import fr.openium.iosts.IostsVulnerabilities;

/**
 * 
 * @author Stassia
 * 
 */
public class Pass extends Verdict {

	public Pass(String name) {
		super(name);
	}

	private static final String PASS = "pass";

	@Override
	public String getVerdictName() {
		return PASS;
	}

	public void setGuard(String group) {

		if (group.equals(IostsVulnerabilities.ACTIVITY_IN_APPLICATION)
				|| group.equals(IostsVulnerabilities.SERVICE_IN_APPLICATION)) {
			mIsInApplicationsComponentList = true;
		} else if (group.equals(IostsVulnerabilities.ACTIVITY_IS_ENABLE
				+ "=true")
				|| group.equals(IostsVulnerabilities.SERVICE_IS_ENABLE
						+ "=true")) {
			mComponentEnable = true;

		}

		/** mComponentEnable */

	}

	public void setAssign(String assign) {
		if (assign.equalsIgnoreCase(IostsVulnerabilities.SERVICE_IS_ENABLE
				+ "#null")) {
			mNeedResponse = true;
		}
	}
}
