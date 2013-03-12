package fr.openium.Junit.Builder;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.openium.Junit.pairwise.PairWise;
import fr.openium.iosts.IostsConcreteTestCase;
import fr.openium.specification.config.Actions;
import fr.openium.specification.config.RVString;
import fr.openium.specification.config.Type;
import fr.openium.testcases.intent.TestCaseIntent;
import fr.openium.testcases.verdict.Fail;
import fr.openium.testcases.verdict.Inconclusive;
import fr.openium.testcases.verdict.Pass;

/**
 * 
 * @author Stassia
 * 
 *         generate Junit code for availability vulnerability
 * 
 */
public class TcGenerationTool extends AbstractTcGenerationTool {

	public TcGenerationTool() {
		super();
		mtcListFile = createFile(testcasenamelist, testcasenamelist);

	}

	public boolean generateTc(IostsConcreteTestCase concreteTc) {

		if (!istestProjectNameWritten) {
			mTestcaseName.append(concreteTc.getPackageName() + DOT + TEST);
			mTestcaseName.append('\n');
			istestProjectNameWritten = true;
		}
		/** Clean TestCaseNameList */
		// mTestcaseName.setLength(0);
		/** Generate extraTc */
		/** parse String xml */
		/** Value and Type of extra must be choosen in RV Value */
		/***/
		/** Reduction de nombre de variable Extra_key value choix random */
		// exemple 12.
		// random(extra_key, 2);

		// ** Reduction de nombre de variable Extra value choix random */
		// exemple 10.

		/** Path value */
		// mPath = concreteTc.getTestCaseIntent().getPathSet();

		/** */
		/** Create FileName component name */
		String name = getShortName(concreteTc.getName());
		File testCaseFile = createFile(name, concreteTc.getType());

		/** Write package */

		/** Write import package */

		/** create the class */
		createTestCaseClass(concreteTc);

		/** write the list of allTC */
		writeTcList();
		/** Write the code generated into the file */
		boolean generate = writeInFile(testCaseFile, mCode);
		initmCode();
		return generate;

	}

	private void createTestCaseClass(IostsConcreteTestCase concreteTc) {

		String name = getShortName(concreteTc.getName());
		String testcaseName = name + TEST;
		String type = concreteTc.getType();
		addln();
		/** PAckage */
		// All package test will be created inside packagename.test.intent
		writePackage(concreteTc.getPackageName() + DOT + TEST + DOT + INTENT);
		/** Import */
		addImport();
		/** import the componnent package */
		// add()
		add(IMPORT);
		add(concreteTc.getName());
		add(SEMICOLON);

		if (type.equals(ACTIVITY_TYPE)) {
			add(PUBLIC_CLASS);
			add(testcaseName);
			add(EXTENDS);
			add(ACTIVITYINSTRUMENTATIONTESTCASE + "<" + name + ">");
			add(BEGIN);

			/** Attribute */
			addln();
			add(INSTRUMENTATION);
			add(M_INSTRUMENTATION);
			add(SEMICOLON);
			add(ACTIVITYMONITOR);
			add(MONITOR);
			add(SEMICOLON);
			add(ACTIVITY);
			add(M_ACTIVITY);
			add(SEMICOLON);
			add(INTENT);
			add(M_INTENT);
			add(SEMICOLON);
			add(VIEWS);
			add(M_VIEWS);
			add(SEMICOLON);

			/** Constructor */
			addln();
			add(PUBLIC);
			add(testcaseName);
			add(BRACKETS);
			add(BEGIN);
			/**
			 * By Default we have:
			 * 
			 * super(ActivityName.class);
			 * */
			add(SUPER);
			add(LBRACKETS);
			add(QUOTE + concreteTc.getPackageName() + QUOTE + COMA + name
					+ ".class");
			add(RBRACKETS);
			add(SEMICOLON);
			/** End of Constructor */
			add(END);
			/**
			 * SETUP by Default
			 * 
			 * mInstrumentation = getInstrumentation();
			 * 
			 * mIntent = new Intent();
			 * 
			 * mViews = new ArrayList<View>();
			 * 
			 */
			add(OVERRIDE);
			addln();
			add(PROTECTED);
			add(VOID);
			add(SETUP);
			add(BEGIN);
			add(M_INSTRUMENTATION);
			add(EQUAL);
			add(GETINSTRUMENTATION);
			add(SEMICOLON);
			add(M_INTENT);
			add(EQUAL);
			add(NEWINTENT);
			add(SEMICOLON);
			/** End of Setup methode */
			add(MONITOR);
			add(EQUAL);
			add(M_INSTRUMENTATION + ADDMONITOR);

			add(LBRACKETS + getShortName(concreteTc.getName()) + CLASS
					+ GETNAME + COMA + NULL + COMA + FALSE + RBRACKETS);
			add(SEMICOLON);
			add(END);

			/** Add test Template methode */
			/** Apply pairWise Tesing for variable selection */

			// en fonction des variables tirées ==> on determine quel paramètre
			// à setter.

			/**
			 * methode 1 intent simple : action
			 * 
			 * methode 2 : action categorie
			 * 
			 * methode 3: action categorie data
			 * 
			 * methode 4: action categorie data
			 * 
			 * methode 5: action categorie data permission
			 * 
			 * dans les 5 cas avec ou sans extra
			 * 
			 * */

			/** without pairwise */
			generateActivityTCMethods(concreteTc);

			/** with pairwise */
			/** add additionnal Method */

			addDisplayMethod();
			addRetrieveView();
			addGetViewMethod();

			if (Actions.A_output.contains(mAction)) {
				addresponseMethod();
			} else
				addNotresponseMethod();

			/** End of Class */
			// add(END);

		} else if (type.equalsIgnoreCase(SERVICE_TYPE)) {

			add(PUBLIC_CLASS);
			add(testcaseName);
			add(EXTENDS);
			add(SERVICETESTCASE + "<" + name + ">");
			add(BEGIN);

			/** Attribute */
			addln();

			add(SERVICE);
			add(M_SERVICE);
			add(SEMICOLON);
			add(INTENT);
			add(M_INTENT);
			add(SEMICOLON);

			add(ACTIVITYMANAGER);
			add(M_ACTIVITYMANAGER);
			add(SEMICOLON);

			/** constructor */
			addln();
			add(PUBLIC);
			add(testcaseName);
			add(BRACKETS);
			add(BEGIN);
			/**
			 * By Default we have:
			 * 
			 * super(ActivityName.class);
			 * */
			add(SUPER);
			add(LBRACKETS);
			add(name + ".class");
			add(RBRACKETS);
			add(SEMICOLON);
			/** End of Constructor */
			add(END);

			/** SetUp */
			add(PROTECTED);
			add(VOID);
			add(SETUP);
			add(THROWS);
			add(EXCEPTION);
			add(BEGIN);
			add(M_INTENT);
			add(EQUAL);
			add(NEWINTENT);
			add(SEMICOLON);
			add(M_ACTIVITYMANAGER);
			add(EQUAL);
			add(GETACTIVITYMANAGER);
			add(SEMICOLON);
			/** End of Setup methode */
			add(END);

			/** with pairwise */
			generateServiceTCMethods(concreteTc);
			/** with pairwise */

			/** End of teh Class */
			// add(END);

		}
		add(END);

	}

	private void generateActivityTCMethods(IostsConcreteTestCase concreteTc) {
		System.out.println(concreteTc.getName() + ".....");

		// testscase is generated from testCaseIntent
		TestCaseIntent tcInt = concreteTc.getTestCaseIntent();
		// apply pairWise
		PairWise pairWise = new PairWise();
		List<List> variable = new ArrayList<List>();
		variable.add(tcInt.getActionSet());
		variable.add(tcInt.getCategorySet());

		boolean hasData = false;

		/**
		 * if component has Data add host and scheme mime type parameter
		 */
		if (!tcInt.getHostSet().isEmpty() || !tcInt.getSchemeSet().isEmpty()) {
			variable.add(tcInt.getHostSet());
			variable.add(tcInt.getSchemeSet());
			variable.add(tcInt.getPathSet());
			hasData = true;
		}
		/*
		 * if (tcInt.getData().isEmpty()) { tcInt.setHostSet(" ");
		 * tcInt.setSchemeSet(" "); tcInt.setPathSet(" "); }
		 */

		// each key is represented by one parameter

		// variable.add(mExtras.get(0).getType());

		variable.add(Type.TYPESET);
		for (int h = 0; h < mExtras.size(); h++) { //

			variable.add(RVString.RANDOM_STRING);
		}

		pairWise.setSequence(variable);
		List<String> sequence = pairWise.test();
		int i = 0;
		String regex = "";
		for (int r = 0; r < variable.size(); r++) {
			regex = regex + ("([[.][^\\:]]+):");
		}
		// "([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+)";
		// ajout regex en fonction du nombre de clé d'extra
		/*
		 * for (int j = 0; j < mExtras.size(); j++) { regex = regex +
		 * ":([[\\w][\\.]?[\\s]?]+)"; }
		 */
		regex = regex.substring(0, regex.length() - 1);
		if (hasData) {
			for (String seq : sequence) {
				Pattern inPattern = Pattern.compile(regex);
				Matcher matcher = inPattern.matcher(seq);
				while (matcher.find()) {
					String action = matcher.group(1);
					mAction = action;
					String category = matcher.group(2);
					String host = matcher.group(3);
					String scheme = matcher.group(4);
					String path = matcher.group(5);
					String type = matcher.group(6);
					// à partir de 7, il s'agit des valeurs des extras
					// recuperer la liste de valeurs d'extra
					// extra.size - 6

					ArrayList<String> extraValue = new ArrayList<String>();
					for (int x = 7; x < variable.size(); x++) {
						extraValue.add(matcher.group(x));
					}

					writeActivityTest(concreteTc, "" + i, action, category,
							host, scheme, path, type, extraValue);
					i++;
				}

			}
		} else {
			for (String seq : sequence) {
				Pattern inPattern = Pattern.compile(regex);
				Matcher matcher = inPattern.matcher(seq);
				while (matcher.find()) {
					String action = matcher.group(1);
					mAction = action;
					String category = matcher.group(2);
					String type = matcher.group(3);
					ArrayList<String> extraValue = new ArrayList<String>();
					for (int x = 4; x < variable.size(); x++) {
						extraValue.add(matcher.group(x));
					}
					writeActivityTest(concreteTc, "" + i, action, category,
							null, null, null, type, extraValue);
					i++;
				}
			}

		}
		/** setting action is mandatory */

		/** setting or not category */

		/** setting or not data */

		/** always setting extra */
		/*
		 * int i = 0; for (String action : tcInt.getActionSet()) { for (String
		 * category : tcInt.getCategorySet()) { if (!tcInt.getData().isEmpty())
		 * {
		 * 
		 * for (IntentData data : tcInt.getData()) {
		 * writeActivityTest(concreteTc, "" + i, action, category,
		 * data.getHost() + ":" + data.getScheme() + "/" + data.getPath(),
		 * mExtras);
		 * 
		 * i++; } } else { writeActivityTest(concreteTc, "" + i, action,
		 * category, "", mExtras); i++; } }
		 * 
		 * }
		 */

	}

	private void writeActivityTest(IostsConcreteTestCase concreteTc,
			String methodName, String action, String category, String host,
			String scheme, String path, String mimeType,
			ArrayList<String> extraValue) {

		mTestMethodNumber++;
		addln();

		/** Header */
		add(PUBLIC_VOID);
		String method = TEST + methodName;
		add(method);
		/** add tcList */
		addtoTClist(concreteTc, method);

		add(BRACKETS);
		add(BEGIN);

		/** Filling */
		/** action */
		add(M_INTENT + SETACTION + LBRACKETS + QUOTE + action + QUOTE
				+ RBRACKETS);
		add(SEMICOLON);
		/** category */
		if (!category.equalsIgnoreCase(null)) {
			add(M_INTENT + ADDCATEGORY + LBRACKETS + QUOTE + category + QUOTE
					+ RBRACKETS);
			add(SEMICOLON);
		}
		/** Data */
		if (host != null && scheme != null && path != null) {
			add(M_INTENT + SETDATA + LBRACKETS + URISPARSE + LBRACKETS + QUOTE
					+ scheme + ":" + "//" + host + ":" + path + QUOTE
					+ RBRACKETS + RBRACKETS);
			add(SEMICOLON);

		}
		if (mimeType != null) {
			add(M_INTENT + SETTYPE + LBRACKETS + QUOTE + mimeType + QUOTE
					+ RBRACKETS);
			add(SEMICOLON);

		}
		/** Extra */
		if (extraValue != null && mExtras != null) {
			for (int x = 0; x < mExtras.size() - 1; x++) {
				add(M_INTENT + PUTEXTRA + LBRACKETS + QUOTE
						+ mExtras.get(x).getKey() + QUOTE + COMA + QUOTE
						+ (String) extraValue.get(x) + QUOTE + RBRACKETS);
				add(SEMICOLON);

			}

		}

		/** setIntent */
		add(SETACTIVITYINTENT);
		add(SEMICOLON);

		/** Start Activity Under test */
		/*
		 * add(M_INSTRUMENTATION + STARTACTIVITYSYNC); add(SEMICOLON);
		 *//** Assign Activity */
		/*
		 * add(M_ACTIVITY + EQUAL + M_INSTRUMENTATION + WAITORMONITOR);
		 * add(SEMICOLON);
		 */

		/** Call Assert */
		setOutpuAssert(concreteTc);
		/** Assertion for ActivityNotNull */
		// add(ASSERTNOTNULL + LBRACKETS + M_ACTIVITY + RBRACKETS);
		/** end of method */
		// add(SEMICOLON);
		add(END);

	}

	private void generateServiceTCMethods(IostsConcreteTestCase concreteTc) {

		System.out.println(concreteTc.getName() + ".....");
		TestCaseIntent tcInt = concreteTc.getTestCaseIntent();
		PairWise pairWise = new PairWise();
		List<List> variable = new ArrayList<List>();
		variable.add(tcInt.getActionSet());
		variable.add(tcInt.getCategorySet());
		boolean hasData = false;
		if (!tcInt.getData().isEmpty()) {
			variable.add(tcInt.getHostSet());
			variable.add(tcInt.getSchemeSet());
			variable.add(tcInt.getPathSet());
			hasData = true;
		}
		// Pour chaque clé, un parametre
		variable.add(mExtras.get(0).getType());
		// variable.add(mExtras.get(0).ge)

		for (int h = 0; h < mExtras.size(); h++) { //
			// variable.add((ArrayList<String>) mExtras.get(h).getValue());
			variable.add(RVString.RANDOM_STRING);
		}

		pairWise.setSequence(variable);
		List<String> sequence = pairWise.test();
		int i = 0;
		String regex = "";
		for (int r = 0; r < variable.size(); r++) {
			regex = regex + ("([[.][^\\:]]+):");
		}
		// "([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+):([[\\w][\\.]?[\\s]?]+)";
		// ajout regex en fonction du nombre de clé d'extra
		/*
		 * for (int j = 0; j < mExtras.size(); j++) { regex = regex +
		 * ":([[\\w][\\.]?[\\s]?]+)"; }
		 */
		regex = regex.substring(0, regex.length() - 1);

		if (hasData) {
			for (String seq : sequence) {
				Pattern inPattern = Pattern.compile(regex);
				Matcher matcher = inPattern.matcher(seq);
				while (matcher.find()) {
					String action = matcher.group(1);
					mAction = action;
					String category = matcher.group(2);
					String host = matcher.group(3);
					String scheme = matcher.group(4);
					String path = matcher.group(5);
					String type = matcher.group(6);
					// à partir de 7, il s'agit des valeurs des extras
					// recuperer la liste de valeurs d'extra
					// extra.size - 6

					ArrayList<String> extraValue = new ArrayList<String>();
					for (int x = 7; x < variable.size(); x++) {
						extraValue.add(matcher.group(x));
					}

					writeServiceTest(concreteTc, "" + i, action, category,
							host, scheme, path, type, extraValue);
					i++;
				}
			}

		} else {
			for (String seq : sequence) {
				Pattern inPattern = Pattern.compile(regex);
				Matcher matcher = inPattern.matcher(seq);
				while (matcher.find()) {
					String action = matcher.group(1);
					mAction = action;
					String category = matcher.group(2);
					String type = matcher.group(3);
					ArrayList<String> extraValue = new ArrayList<String>();
					for (int x = 4; x < variable.size(); x++) {
						extraValue.add(matcher.group(x));
					}
					writeServiceTest(concreteTc, "" + i, action, category,
							null, null, null, type, extraValue);
					i++;
				}
			}

		}

	}

	private void writeServiceTest(IostsConcreteTestCase concreteTc,
			String methodName, String action, String category, String host,
			String scheme, String path, String type,
			ArrayList<String> extraValue) {
		mTestMethodNumber++;
		addln();
		/** Header */
		add(PUBLIC_VOID);
		String method = TEST + "START" + methodName;
		add(method);
		addtoTClist(concreteTc, method);
		add(BRACKETS);
		add(BEGIN);

		/** Filling */

		/** SetClass */

		/** action */
		add(M_INTENT + SETACTION + LBRACKETS + QUOTE + action + QUOTE
				+ RBRACKETS);
		add(SEMICOLON);
		/** category */
		if (!category.equalsIgnoreCase(null)) {
			add(M_INTENT + ADDCATEGORY + LBRACKETS + QUOTE + category + QUOTE
					+ RBRACKETS);
			add(SEMICOLON);
		}
		/** Data */
		if (host != null && scheme != null && path != null) {
			add(M_INTENT + SETDATA + LBRACKETS + URISPARSE + LBRACKETS + QUOTE
					+ scheme + ":" + "//" + host + ":" + path + QUOTE
					+ RBRACKETS + RBRACKETS);
			add(SEMICOLON);
		}
		/** Extra */
		/** Extra */
		if (extraValue != null && mExtras != null) {
			for (int x = 0; x < mExtras.size() - 1; x++) {
				add(M_INTENT + PUTEXTRA + LBRACKETS + QUOTE
						+ mExtras.get(x).getKey() + QUOTE + COMA + QUOTE
						+ (String) extraValue.get(x) + QUOTE + RBRACKETS);
				add(SEMICOLON);

			}

		}

		/*** Assert Verdict */
		setOutpuAssert(concreteTc);

		add(END);
	}

	private void initmCode() {
		mCode.setLength(0);

	}

	/** the assert insertion according to concrete testcase output */
	private void setOutpuAssert(IostsConcreteTestCase concreteTC) {
		solveOutput(concreteTC);
		/**
		 * Princip:
		 */
		/**
		 * case display, assertDisplay
		 */
		/**
		 * case Exception, try and catch the exception
		 */
		// String type = concreteTC.getType();
		StringBuilder inTry = new StringBuilder();
		if (concreteTC.getType().equals(ACTIVITY_TYPE)) {
			// inTry.append(M_INSTRUMENTATION + STARTACTIVITYSYNC + SEMICOLON
			// + "\n");
			inTry.append(M_ACTIVITY + EQUAL + GETACTIVITY + SEMICOLON + "\n");
			inTry.append(ASSERTNOTNULL + LBRACKETS + M_ACTIVITY + RBRACKETS
					+ SEMICOLON + "\n");
		} else if (concreteTC.getType().equals(SERVICE_TYPE)) {
			/** Start Service Under test */
			inTry.append(STARTSERVICE + SEMICOLON + "\n");
			inTry.append(ASSERTNOTNULL + LBRACKETS + GETSERVICE + RBRACKETS
					+ SEMICOLON + "\n");
		}

		/** Get Pass OutPut */
		/** represented by assertTrue(value) */
		Pass passVerdict = concreteTC.getTestCaseIntent().getPass();
		/** for EachVerdictSet assert Pass */
		ArrayList<Object> passOutput = passVerdict.getVerdictSet();
		boolean isDisplay = true;
		StringBuilder inCatch = new StringBuilder();
		for (int i = 0; i < passOutput.size(); i++) {
			String out;
			if (passOutput.get(i) instanceof String && isDisplay) {
				out = (String) passOutput.get(i);
				if (out.contains(DISPLAY)) {
					addln();
					inTry.append(ISDISPLAY + SEMICOLON + "\n");
					isDisplay = false;
				}

			} else if ((passOutput.get(i) instanceof String)
					&& Actions.Exceptions.contains((String) passOutput.get(i))) {
				String verdict = ASSERTTRUE + LBRACKETS + QUOTE
						+ passVerdict.getVerdictName() + QUOTE + COMA + TRUE
						+ RBRACKETS + SEMICOLON + "\n";
				setCatch((String) passOutput.get(i), inCatch, verdict);
			}
		}

		/** Get Fail OutPut */
		/** represented by fail */
		Fail failVerdict = concreteTC.getTestCaseIntent().getFail();
		ArrayList<Object> failOutput = failVerdict.getVerdictSet();
		for (Object output : failOutput) {
			if ((output instanceof String)
					&& Actions.Exceptions.contains((String) output)) {
				String verdict = "\n" + FAILNOTVULNERABLE + SEMICOLON + "\n";
				setCatch((String) output, inCatch, verdict);
			}

		}

		/** Get Inconclusive OutPut */
		/** represented by assertTrue("Inconclusive", value) */
		Inconclusive inconclusiveVerdict = concreteTC.getTestCaseIntent()
				.getInconclusive();
		ArrayList<Object> inconclusiveOutput = inconclusiveVerdict
				.getVerdictSet();
		for (Object output : inconclusiveOutput) {
			if ((output instanceof String)
					&& Actions.Exceptions.contains(output)) {
				String verdict = "\n" + ASSERTTRUE + LBRACKETS + QUOTE
						+ inconclusiveVerdict.getVerdictName().toString()
						+ QUOTE + COMA + TRUE + RBRACKETS + SEMICOLON + "\n";
				setCatch((String) output, inCatch, verdict);
			}
		}

		/** put all pass assrt into try */
		tryAndCatch(inTry);
		add(inCatch);

	}

	private void addDisplayMethod() {
		/**
		 * Read code from /SpecGen/src/fr/openium/Junit/Util/isDisplay and just
		 * copy to mCode
		 */
		String absolutePath = FILE.getAbsolutePath();
		String display = absolutePath + "/src/fr/openium/Junit/Util/isDisplay";
		writeMethod(display);

	}

	private void addGetViewMethod() {
		/**
		 * Read code from /SpecGen/src/fr/openium/Junit/Util/isDisplay and just
		 * copy to mCode
		 */
		String absolutePath = FILE.getAbsolutePath();
		String display = absolutePath + "/src/fr/openium/Junit/Util/getView";
		writeMethod(display);

	}

	private void addresponseMethod() {
		/**
		 * Read code from /SpecGen/src/fr/openium/Junit/Util/isDisplay and just
		 * copy to mCode
		 */
		String absolutePath = FILE.getAbsolutePath();
		String display = absolutePath + "/src/fr/openium/Junit/Util/response";
		writeMethod(display);

	}

	private void addNotresponseMethod() {
		/**
		 * Read code from /SpecGen/src/fr/openium/Junit/Util/isDisplay and just
		 * copy to mCode
		 */
		String absolutePath = FILE.getAbsolutePath();
		String display = absolutePath
				+ "/src/fr/openium/Junit/Util/notresponse";
		writeMethod(display);

	}

	private void addImport() {
		String absolutePath = FILE.getAbsolutePath();
		String imp = absolutePath + "/src/fr/openium/Junit/Util/import";
		writeMethod(imp);

	}

	private void addRetrieveView() {
		/**
		 * Read code from /SpecGen/src/fr/openium/Junit/Util/RetrievView and
		 * just copy to mCode
		 */
		String absolutePath = FILE.getAbsolutePath();
		String retrieveView = absolutePath
				+ "/src/fr/openium/Junit/Util/RetrieveView";
		writeMethod(retrieveView);

	}

	private void writeMethod(String from) {
		StringBuilder method = getText(new File(from));
		mCode.append(method);

	}

	private StringBuilder getText(File file) {
		StringBuilder sb = new StringBuilder();
		FileInputStream fIn;
		try {
			fIn = new FileInputStream(file);
			DataInputStream dIn = new DataInputStream(fIn);
			BufferedReader br = new BufferedReader(new InputStreamReader(dIn));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			dIn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;
	}

	private void tryAndCatch(StringBuilder inTry) {
		addln();
		add(TRY);
		add(BEGIN);
		add(inTry);
		add(END);
		inTry.setLength(0);

	}

	private void setCatch(String exceptionName, StringBuilder inCatch,
			String verdict) {
		String exceptionShortName = exceptionName.substring(0, 2);
		inCatch.append(CATCH + LBRACKETS + exceptionName + " "
				+ exceptionShortName + RBRACKETS + BEGIN + "\n"
				// Log(verdictName, exceptionShortName + GETMESSAGE) + SEMICOLON
				+ "\n" + verdict + END + "\n");
	}

	public int getTestMethodNumber() {
		return mTestMethodNumber;
	}

}
