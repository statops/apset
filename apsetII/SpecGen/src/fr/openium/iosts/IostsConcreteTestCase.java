package fr.openium.iosts;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.openium.introspection.MReflection;
import fr.openium.specification.IAbstractComponent;
import fr.openium.specification.IAbstractIntent;
import fr.openium.specification.config.Actions;
import fr.openium.specification.config.Category;
import fr.openium.specification.config.RVString;
import fr.openium.specification.config.Type;
import fr.openium.specification.xml.ManifestData;
import fr.openium.specification.xml.ManifestData.Intent;
import fr.openium.testcases.intent.TestCaseIntent;

public class IostsConcreteTestCase extends Iosts {

	private final IostsAbstractTestCase mTc;
	private final ManifestData mManifestData;

	private final IostsLocation[] mInitLocation;

	private TestCaseIntent mTestCaseIntent;
	private static final String FAIL = "fail";
	private static final String PASS = "pass";
	private static final String INCONCLUSIVE = "inconclusive";

	public IostsConcreteTestCase(IostsAbstractTestCase tc, ManifestData manifest) {
		super(tc.getName());
		mTc = tc;
		mManifestData = manifest;
		/** assign the name of component in the manifestData element */
		setType(mTc.getType());
		setPackageName(manifest.getPackage());
		mInitLocation = mTc.getInitCombinedLocation();
		mTestCaseIntent = new TestCaseIntent(mName);
		setVariables();
	}

	public ManifestData getManifest() {
		return mManifestData;
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
		// TODO Auto-generated method stub

	}

	/**
	 * the core of the generation of parameters and variable set during testing:
	 * 
	 * Dtc = Itc U Vtc
	 * 
	 * Such as:
	 * 
	 * - Dtc : TestCaseDomain
	 * 
	 * - Itc = the different set that parameters of the intent will take values
	 * 
	 * - Vtc = the differenr set tha the output will take values
	 * 
	 * @param the
	 *            abstract test cas == complete specification
	 * @param the
	 *            manifestData
	 * @return must generate all variables set for input and output transitions
	 * 
	 */

	private void setVariables() {
		/** Aim: Construct each transistion of concrete testCase */
		/** Stepe 1 , define the init location */
		// already done in constructor
		boolean input = false;
		for (IostsTransition tr : mTc.getTransitions()) {
			/**
			 * Define the ActionSet (action, cate , extra .... ) of each
			 * transition
			 */
			/** it corresponds to start of an Intent */
			if (tr.isInputAction()) {
				/**
				 * Step 2: for each transition: translate each expressions by
				 * matching with manifestData informations for Input action.
				 */
				concretiseTransitionInputSet(tr, input);

			} else {

				// Verdict
				concretiseTransitionOutputSet(tr);
				/** Define pass set **/

				/** Define the fail set */

				/** define the inconclusive set */

				/** Define the assigment */

			}

		}

	}

	private void concretiseTransitionOutputSet(IostsTransition tr) {

		/** identify component */

		/**
		 * there are two kind of output :
		 * 
		 * - display and something
		 * 
		 * - exception
		 * */

		/** Case of exception , lead to fail, inconclusive or pass */
		String ouput = tr.getActionString().substring(1);

		if (Actions.Exceptions.contains(ouput)
				&& (tr.getTargetTab()[0].getName().equals(FAIL) || tr
						.getTargetTab()[1].getName().equals(FAIL))) {
			mTestCaseIntent.setFail(ouput);

		} else if (Actions.Exceptions.contains(ouput)
				&& (tr.getTargetTab()[0].getName().equals(INCONCLUSIVE) || tr
						.getTargetTab()[1].getName().equals(INCONCLUSIVE))) {
			mTestCaseIntent.setInconclusive(ouput);

		} else if (Actions.Exceptions.contains(ouput)
				&& (tr.getTargetTab()[0].getName().equals(PASS) || tr
						.getTargetTab()[1].getName().equals(PASS))) {
			mTestCaseIntent.setPass(ouput);

		}

		else if (tr.getTargetTab()[0].getName().equals(PASS)
				|| tr.getTargetTab()[1].getName().equals(PASS)) {

			// manage Pass Ouput
			/** case of activity */
			if (mType.equals(IostsParser.ACTIVITY_TYPE)) {
				String Guard = tr.getGuardString();
				String Assign = tr.getAssignmentsString();
				mTestCaseIntent.setPass(ouput, Guard, Assign);
			}
			/** case of service */
			else if (mType.equals(IostsParser.SERVICE_TYPE)) {

			}

		}

	}

	private void concretiseTransitionInputSet(IostsTransition tr, boolean input) {

		if (!input) {

			IAbstractComponent component = mManifestData.getComponents(mName,
					mType);
			/** insert to the set the valid value of intent */
			for (Intent intent : component.getIntent()) {
				mTestCaseIntent.setActionSet(intent.getAction());
				mTestCaseIntent.setCategorySet(intent.getCategory());
				mTestCaseIntent.setUri(intent.getData());
			}
			translateGuard(tr.getGuardString());
		} else {
			// Nothing for the moment

		}

	}

	private void translateGuard(String guardString) {
		Pattern inPattern = Pattern.compile(GUARDREGEX);
		Matcher matcher = inPattern.matcher(guardString);
		while (matcher.find()) {
			String param = matcher.group(1);
			for (int i = 2; i < matcher.groupCount() + 1; i++) {
				String set = matcher.group(i);
				in(param, set);
			}
		}

	}

	private void in(String parameter, String set) {
		/**
		 * Case of action
		 * 
		 * It's better to reduce the number of action set, so at this level we
		 * reduce it to one random value of in an arraylist
		 * */

		if (parameter.equals(IAbstractIntent.ACTION)) {
			if (set.equals(this.AC)) {
				mTestCaseIntent.setActionSet(random(Actions.A_input));
			} else if (set.equals(this.AO)) {
				mTestCaseIntent.setActionSet(random(Actions.A_output));
			} else if (set.equals(this.AQ)) {
				mTestCaseIntent.setActionSet(random(Actions.A_quiescence));
			} else {
				mTestCaseIntent.setActionSet(random(Actions.A_c));
			}

		}

		/**
		 * Case of Category
		 * 
		 * It's better to reduce the number of category set, s o at this level
		 * we reduce it to one random value of in an arraylist if C , and add
		 * all RV (string)
		 */
		if (parameter.equals(IAbstractIntent.CATEGORY)) {
			if (set.equals(this.C)) {
				mTestCaseIntent.setCategorySet(random(Category.CATEGORYSET));
			}
			if (set.equals(this.RVSTRING)) {
				mTestCaseIntent.setCategorySet(RVString.RANDOM_STRING);
			}
		}

		/** Case of Type */

		if (parameter.equals(IAbstractIntent.TYPE)) {
			if (set.equals(this.T)) {
				mTestCaseIntent.setTypeSet(random(Type.TYPESET));
			}
			if (set.equals(this.RVSTRING)) {
				mTestCaseIntent.setTypeSet(RVString.RANDOM_TYPE);
			}
		}

		/** Case of URI */

		if (parameter.equals(IAbstractIntent.URI)) {

			if (set.equals(this.RVSTRING)) {
				mTestCaseIntent.setPathSet(RVString.RANDOM_STRING);
			}
			if (set.equals(this.INJ)) {
				mTestCaseIntent.setPathSet(RVString.SQL_Injections);
				mTestCaseIntent.setPathSet(RVString.XML_Injection);

			}
		}

		/** Case of Extra */
		// performed during Execution phase "pairwise testing"

	}

	private final String AO = "Ao";
	private final String AC = "Ac";
	private final String AQ = "Aq";
	private final String AI = "Ai";
	private final String C = "C";
	private final String T = "T";
	private final String U = "U";
	private final String RVSTRING = "RV(String)";
	private final String INJ = "INJ";
	// Remarques,Limitations ˆ 3 ensembles + 3:ˆ voir ulterieurement.
	private final String GUARDREGEX = "in\\((\\w+),([A-Za-z\\(\\)]+)[+\\-]?([A-Za-z\\(\\)]*)\\)";

	/*
	 * private final ArrayList<String> SETLIST = new ArrayList<String>() { {
	 * add(AO); add(AC); add(AQ); add(AI); add(C); add(T); add(U);
	 * add(RVSTRING); add(INJ);
	 * 
	 * } };
	 */
	/*
	 * private void in(String fonction) {
	 *//** doing a mapping if action ==> add AcionSet **/
	/*
		*//** if category ==> add Category set **/
	/*
		*//** etc */
	/*
	 * String parameter = fonction.substring( fonction.indexOf(Tokens.LPAREN) +
	 * 1, fonction.indexOf(Tokens.COMA)); String set =
	 * fonction.substring(fonction.indexOf(Tokens.COMA) + 1, fonction.length() -
	 * 2); in(parameter, set);
	 * 
	 * }
	 */
	private String random(ArrayList<String> list) {
		Random rd = new Random();
		int randomInt = rd.nextInt(list.size());
		return list.get(randomInt);
	}

	public TestCaseIntent getTestCaseIntent() {
		return mTestCaseIntent;
	}

	/**
	 * Detect the link with the content Provider through introspection
	 * 
	 * @param jar
	 *            the jar file of the application to reflect
	 * @return true if the component is connected to the CP
	 */
	public boolean isConnectetsoCP(File jar) {
		if (!jar.exists()) {
			throw new NullPointerException("the jar file:" + jar.getName()
					+ " do not exist");
		}
		MReflection ref;
		ref = new MReflection(jar, getPackageName());
		try {
			return ref.isConnectedtoContentProvider(getName());
		} catch (ClassNotFoundException e) {
			return true;
		}
	}
}
