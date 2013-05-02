package fr.openium.iosts;

import java.util.StringTokenizer;

public class IostsSpecification extends Iosts {
	public static final String INPUT = "?";
	public static final String OUTPUT = "!";
	public static final String INPUT_ACTION = "intent(action,data,category,vextra)";
	public static final String A_0 = "in(action,Ao)";
	public static final String A_Q = "in(action,Aq)";
	public static final String A_C = "in(action,Ac)";
	public static final String OUTPUT_ACTIVITY_ACTION = "display (Activity A)";
	public static final String ACTIVITY_RESPONSE = "A.resp#null";
	public static final String ACTIVITY_NORESPONSE = "A.resp=null";
	public static final String OUTPUT_SERVICE_ACTION = "running (Service S)";
	public static final String SERVICE_RESPONSE = "S.resp#null";
	public static final String SERVICE_NORESPONSE = "S.resp=null";

	public IostsSpecification() {
		super();
	}

	@Override
	public String getGuard(String transition) {

		System.out.println("tr : " + transition);
		String guard = null;
		StringTokenizer st = new StringTokenizer(transition);
		while (st.hasMoreTokens()) {
			String action = st.nextToken("[").replaceAll("\\s", "")
					.substring(1);
			action = action.substring(0, action.length() - 2);
			guard = st.nextToken("]").replaceAll("\\s", "").substring(1);
			// substring(2);
			break;
		}
		return guard;
	}

	@Override
	public String getAction(String transition) {
		String action = new StringTokenizer(transition).nextToken("[")
				.substring(1);
		return action.substring(0, action.length() - 2);
	}

	@Override
	public String getAssignments(String transition) {
		StringTokenizer st = new StringTokenizer(transition);
		String assgn = null;
		while (st.hasMoreTokens()) {
			String action = st.nextToken("[").substring(1);
			action = action.substring(0, action.length() - 1);
			String guard = st.nextToken("]").substring(1);// .substring(2);
			assgn = st.nextToken("\"").replaceAll("\\s", "").substring(2);
			break;
		}
		return assgn;

	}

	@Override
	public IostsTransition getIostsTransition(String transition) {
		return new IostsTransition(transition, new IostsSpecification());
	}

	public void addTransition(IostsTransition tr) {
		mTransitions.add(tr);
	}

	@Override
	public void setType(String type) {
		mType = type;

	}

	@Override
	public void setInitLocation(IostsLocation iosts) {
		mInit = iosts;

	}

	public void deletAllTransition() {
		mTransitions.clear();
	}

	public void setTransitions(IostsTransition tr) {
		mTransitions.add(tr);
	}
}
