package fr.openium.iosts;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import fr.openium.specification.config.Tokens;

public class IostsVulnerabilities extends Iosts {
	public static final String ACTIVITY_IN_APPLICATION = "in(A.name,ApplicationComponent)";
	public static final String SERVICE_IN_APPLICATION = "in(S.name,ApplicationComponent)";
	public static final String ACTIVITY_IS_ENABLE = "A.isEnable";
	public static final String SERVICE_IS_ENABLE = "S.isEnable";

	public IostsVulnerabilities() {
		super();
	}

	@Override
	public String getGuard(String transition) {
		// System.out.println("tr : " + transition);
		String guard = null;
		StringTokenizer st = new StringTokenizer(transition);
		while (st.hasMoreTokens()) {
			String action = st.nextToken().substring(1);
			action = action.substring(0, action.length() - 1);
			guard = st.nextToken(Tokens.RSBRACKETS).substring(2);
			break;
		}
		return guard;
	}

	/**
	 * Action is always intent with parameter vector (action, category, type,
	 * uri and extra). Action must never be null.
	 */
	@Override
	public String getAction(String transition) {
		String action = new StringTokenizer(transition).nextToken()
				.substring(1);
		return action.substring(0, action.length() - 1);
	}

	@Override
	public String getAssignments(String transition) {
		StringTokenizer st = new StringTokenizer(transition);
		String assgn = null;
		while (st.hasMoreTokens()) {
			String action = st.nextToken().substring(1);
			action = action.substring(0, action.length() - 1);
			String guard = st.nextToken(Tokens.RSBRACKETS).substring(2);
			try {
				assgn = st.nextToken(Tokens.QUOTE).substring(2);
			} catch (NoSuchElementException ns) {
				assgn = "*";
			}
			break;
		}
		return assgn;
	}

	@Override
	public IostsTransition getIostsTransition(String transition) {
		return new IostsTransition(transition, new IostsVulnerabilities());
	}

	@Override
	public void setType(String type) {
		mType = type;

	}

	@Override
	public void setInitLocation(IostsLocation iosts) {
		mInit = iosts;

	}

}
