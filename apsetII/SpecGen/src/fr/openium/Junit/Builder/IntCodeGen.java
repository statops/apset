package fr.openium.Junit.Builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.openium.Junit.pairwise.PairWise;
import fr.openium.iosts.IostsConcreteTestCase;
import fr.openium.specification.config.Actions;
import fr.openium.specification.config.Extra;
import fr.openium.specification.config.RVString;
import fr.openium.specification.config.Type;
import fr.openium.testcases.intent.TestCaseIntent;
import fr.openium.testcases.verdict.Fail;
import fr.openium.testcases.verdict.Inconclusive;
import fr.openium.testcases.verdict.Pass;

public class IntCodeGen extends AbstractTcGenerationTool {

	public IntCodeGen() {
		super();

	}

	/**
	 * @param the
	 *            concrete Test Case
	 * 
	 * @return true if the file is created
	 */

	public boolean generateTc(IostsConcreteTestCase concreteTc) {
		initmCode();
		mExtras.clear();
		mManifestData = concreteTc.getManifest();
		for (String key : extra_key) {
			mExtras.add(new Extra(key, Type.TYPESET, RVString.RANDOM_STRING));
		}

		String name = getShortName(concreteTc.getName());
		File testCaseFile = createFile(name, concreteTc.getType());

		/** add test name package */
		if (!istestProjectNameWritten) {
			mTestcaseName.append(concreteTc.getPackageName() + DOT + TEST);
			mTestcaseName.append('\n');
			istestProjectNameWritten = true;
		}

		/** Write package */

		/** Write import package */

		/** create the class */
		createTestCaseClass(concreteTc);

		/** write the list of allTC */
		writeTcList();
		/** Write the code generated into the file */
		return writeInFile(testCaseFile, mCode);

	}

	/*
	 * private void random(final ArrayList<String> extra_key, final int limit) {
	 * 
	 * ArrayList<String> temp = new ArrayList<String>();
	 * 
	 * Random randomgenerator = new Random(); for (int idx = 1; idx <= limit;
	 * ++idx) { int randomInt = randomgenerator.nextInt(extra_key.size());
	 * temp.add(extra_key.get(randomInt)); } extra_key.clear();
	 * extra_key.addAll(temp);
	 * 
	 * }
	 */

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

		if (type.equals(ACTIVITY_TYPE)) {
			add("@SuppressWarnings(\"unchecked\")");
			addln();
			add(PUBLIC_CLASS);
			add(testcaseName);
			add(EXTENDS);
			add(ACTIVITYINSTRUMENTATIONTESTCASE);
			add(BEGIN);

			/** Attribute */
			addln();
			add(INSTRUMENTATION);
			add(M_INSTRUMENTATION);
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

			add(CONTENTRESOLVER);
			add(M_ContentResolver);
			add(SEMICOLON);

			add(CONTENTVALUE);
			add(M_CONTENTVALUES);
			add(SEMICOLON);

			add(URI);
			add(M_URI);
			add(SEMICOLON);

			add(ACTIVITYMONITOR);
			add(MONITOR);
			add(SEMICOLON);

			add(ColumnNameNull);

			/**
			 * 
			 */

			add("private static final String TARGET_PACKAGE_ID =" + QUOTE
					+ mManifestData.getPackage() + QUOTE + SEMICOLON);
			add("private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME ="
					+ QUOTE + concreteTc.getName() + QUOTE + SEMICOLON);
			add("private static Class<?> " + name + SEMICOLON);
			addln();
			add("static { try {"
					+ name
					+ "= Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME); }catch (ClassNotFoundException e) {throw new RuntimeException(e);}}");

			/** Constructor */
			addln();
			add("@SuppressWarnings(\"unchecked\")");
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
			add(QUOTE + concreteTc.getPackageName() + QUOTE + COMA + name);
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

			add(M_ContentResolver);
			add(EQUAL);
			add(GETCONTENTRESOLVER);
			add(SEMICOLON);

			add(M_CONTENTVALUES);
			add(EQUAL);
			add(NEWCONTENTVALUE);
			add(SEMICOLON);

			/** End of Setup methode */

			add(MONITOR);
			add(EQUAL);
			add(M_INSTRUMENTATION + ADDMONITOR);

			add(LBRACKETS + getShortName(concreteTc.getName()) + GETNAME + COMA
					+ NULL + COMA + FALSE + RBRACKETS);
			add(SEMICOLON);

			add(END);

			/** Add test Template methode */
			/** Apply pairWise Tesing for variable selection */

			// en fonction des variables tirŽes ==> on determine quel
			// param�tre
			// ˆ setter.

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

			add("@SuppressWarnings(\"unchecked\")");
			addln();
			add(PUBLIC_CLASS);
			add(testcaseName);
			add(EXTENDS);
			add(SERVICETESTCASE);
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

			add(CONTENTRESOLVER);
			add(M_ContentResolver);
			add(SEMICOLON);

			add(CONTENTVALUE);
			add(M_CONTENTVALUES);
			add(SEMICOLON);

			add(URI);
			add(M_URI);
			add(SEMICOLON);

			add(ColumnNameNull);

			/** constructor */
			addln();
			add("@SuppressWarnings(\"unchecked\")");
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
			add(name);
			add(RBRACKETS);
			add(SEMICOLON);
			/** End of Constructor */
			add(END);

			add("private static final String TARGET_PACKAGE_ID =" + QUOTE
					+ mManifestData.getPackage() + QUOTE + SEMICOLON);
			add("private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME ="
					+ QUOTE + concreteTc.getName() + QUOTE + SEMICOLON);
			add("private static Class<?> " + name + SEMICOLON);
			addln();
			add("static { try {"
					+ name
					+ "= Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME); }catch (ClassNotFoundException e) {throw new RuntimeException(e);}}");

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

			add(M_ContentResolver);
			add(EQUAL);
			add("getContext().getContentResolver()");
			add(SEMICOLON);

			add(M_CONTENTVALUES);
			add(EQUAL);
			add(NEWCONTENTVALUE);
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

		variable.add(Type.TYPESET);
		adaptExtra();
		for (int h = 0; h < mExtras.size(); h++) {
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
		// ajout regex en fonction du nombre de clŽ d'extra
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

	}

	/**
	 * Limit the number of handled extra due to pairwise tool's limitation
	 * 
	 * According to earlier experimenetation, the average value adapted to the
	 * pairwise tool is 30
	 * 
	 */
	private void adaptExtra() {
		Random rd = new Random();

		if (mExtras.size() > 30) {

			do {
				int index = rd.nextInt(mExtras.size() - 1);
				mExtras.remove(index);
			} while (mExtras.size() > 30);

		}

	}

	private void writeActivityTest(IostsConcreteTestCase concreteTc,
			String methodName, String action, String category, String host,
			String scheme, String path, String mimeType,
			ArrayList<String> extraValue) {

		mTestMethodNumber++;
		addln();
		String data = getSQLInjection();
		/** Header */
		add(PUBLIC_VOID);
		String method = TEST + methodName;
		add(method);
		/** add tcList */
		addtoTClist(concreteTc, method);

		add(BRACKETS);
		add(BEGIN);
		add(M_URI);
		add(EQUAL);

		if (mManifestData.getProviders().size() != 0) {
			add(URISPARSE + LBRACKETS + QUOTE + "content" + ":" + "//"
					+ random(mManifestData.getCPauthorities()) + "/"
					+ getpathInjection() + QUOTE + RBRACKETS);
			add(SEMICOLON);
			addln();
			add(Cursor);
			add(TRY);
			add(BEGIN);
			addln();
			add("cursor" + EQUAL + QueryColumn);
			add(SEMICOLON);
			add(ColumnName);
			// add(SEMICOLON);
			/*
			 * add("for (String colomn:columnName) {"); addln();
			 * add(M_CONTENTVALUES+PUT+LBRACKETS+" colomn,"+
			 * QUOTE+data+QUOTE+RBRACKETS); add(SEMICOLON); add(END);
			 */
			add("cursor.close();");

			addln();

			// add(ADDVALUES);
			putValues();
			addln();
			// end try
			add(END);
			add(CATCH);
			add(" (Exception e2) {");
			addln();
			add("e2.printStackTrace();");
			addln();
			add(ASSERTTRUE + LBRACKETS + TRUE + RBRACKETS);
			add(SEMICOLON);
			add(END);

			/** Filling */
			/** action */
			add(M_INTENT + SETACTION + LBRACKETS + QUOTE + action + QUOTE
					+ RBRACKETS);
			add(SEMICOLON);
			/** category */
			if (!category.equalsIgnoreCase(null)) {
				add(M_INTENT + ADDCATEGORY + LBRACKETS + QUOTE + category
						+ QUOTE + RBRACKETS);
				add(SEMICOLON);
			}
			/** Data */
			if (host != null && scheme != null && path != null) {
				add(M_INTENT + SETDATA + LBRACKETS + URISPARSE + LBRACKETS
						+ QUOTE + scheme + ":" + "//" + host + ":" + path
						+ QUOTE + RBRACKETS + RBRACKETS);
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
					String value = (String) extraValue.get(x);
					try {
						int valueInt = Integer.parseInt(value);
						add(M_INTENT + PUTEXTRA + LBRACKETS + QUOTE
								+ mExtras.get(x).getKey() + QUOTE + COMA
								+ valueInt + RBRACKETS);
						add(SEMICOLON);
					} catch (NumberFormatException e) {
						add(M_INTENT + PUTEXTRA + LBRACKETS + QUOTE
								+ mExtras.get(x).getKey() + QUOTE + COMA
								+ QUOTE + value + QUOTE + RBRACKETS);
						add(SEMICOLON);
					}

				}

			}

			/** setIntent */
			add(SETACTIVITYINTENT);
			add(SEMICOLON);

			/** Call Assert */
			setOutpuAssert(concreteTc, data, M_CONTENTVALUES);
			/** Assertion for ActivityNotNull */
			// add(ASSERTNOTNULL + LBRACKETS + M_ACTIVITY + RBRACKETS);
			/** end of method */
			// add(SEMICOLON);

		} else {
			add(NULL);
			add(SEMICOLON);
			add(ASSERTTRUE + LBRACKETS + TRUE + RBRACKETS + SEMICOLON);
			addln();
		}
		add(END);

	}

	private String getSQLInjection() {
		return random(RVString.SQL_Injections);
	}

	private String getpathInjection() {

		return random(RVString.RANDOM_TABLE);// + "/"
		// + random(RVString.RANDOM_PATH);
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
		// Pour chaque clŽ, un parametre
		variable.add(mExtras.get(0).getType());

		adaptExtra();

		for (int h = 0; h < mExtras.size(); h++) {
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
		// ajout regex en fonction du nombre de clŽ d'extra
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
					// ˆ partir de 7, il s'agit des valeurs des extras
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

		/*
		 * TestCaseIntent tcInt = concreteTc.getTestCaseIntent();
		 *//** setting action is mandatory */
		/*

		*//** always setting extra */
		/*
		 * int i = 0; for (String action : tcInt.getActionSet()) { for (String
		 * category : tcInt.getCategorySet()) { if (!tcInt.getData().isEmpty())
		 * for (IntentData data : tcInt.getData()) {
		 * writeServiceTest(concreteTc, "" + i, action, category, data.getHost()
		 * + ":" + data.getScheme() + "/" + data.getPath(), mExtras); i++; }
		 * else { writeServiceTest(concreteTc, "" + i, action, category, "",
		 * mExtras); i++; } }
		 * 
		 * }
		 */
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
		String data = getSQLInjection();

		add(M_URI);
		add(EQUAL);

		if (mManifestData.getProviders().size() != 0) {

			add(URISPARSE + LBRACKETS + QUOTE + "content" + ":" + "//"
					+ random(mManifestData.getCPauthorities()) + "/"
					+ getpathInjection() + QUOTE + RBRACKETS);
			add(SEMICOLON);
			addln();
			add(Cursor);
			add(TRY);
			add(BEGIN);
			addln();
			add("cursor" + EQUAL + QueryColumn);
			add(SEMICOLON);
			add(ColumnName);
			/*
			 * add(SEMICOLON); add("for (String colomn:columnName) {"); addln();
			 * add(M_CONTENTVALUES + PUT + LBRACKETS + " colomn," + QUOTE + data
			 * + QUOTE + RBRACKETS); add(SEMICOLON); add(END);
			 */
			add("cursor.close();");
			// add(END);
			addln();
			// add(ADDVALUES);
			putValues();
			addln();
			// end try
			add(END);
			add(CATCH);
			add(" (Exception e2) {");
			addln();
			add("e2.printStackTrace();");
			addln();
			add(ASSERTTRUE + LBRACKETS + TRUE + RBRACKETS);
			add(SEMICOLON);
			add(END);
		}

		/** Filling */

		/** SetClass */
		// add(M_INTENT + SETCLASS + LBRACKETS + GETCONTEXT + COMA + concreteTc
		// + CLASS + RBRACKETS);
		// add(SEMICOLON);

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

		setOutpuAssert(concreteTc, data, M_CONTENTVALUES);
		add(END);
	}

	private void initmCode() {
		mCode.setLength(0);

	}

	/** the assert insertion according to concrete testcase output */
	private void setOutpuAssert(IostsConcreteTestCase concreteTC,
			String injection, String mContentvalues) {
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
			inTry.append(M_URI + EQUAL + INSERT + SEMICOLON + "\n");
			inTry.append(M_ACTIVITY + EQUAL + GETACTIVITY + SEMICOLON + "\n");
			inTry.append(ASSERTNOTNULL + LBRACKETS + M_ACTIVITY + RBRACKETS
					+ SEMICOLON + "\n");

			/**
			 * compare data
			 * */

			inTry.append(getText(new File(checkIntegrity())));
			inTry.append("}");
			inTry.append("\n");

		} else if (concreteTC.getType().equals(SERVICE_TYPE)) {
			/** Start Service Under test */
			inTry.append(INSERT + SEMICOLON + "\n");
			inTry.append(STARTSERVICE + SEMICOLON + "\n");
			inTry.append(ASSERTNOTNULL + LBRACKETS + GETSERVICE + RBRACKETS
					+ SEMICOLON + "\n");
			/**
			 * compare data
			 * */
			inTry.append(getText(new File(checkIntegrity())));
			inTry.append("}");
			inTry.append("\n");

		}

		/** Get Pass OutPut */
		/** represented by assertTrue(value) */
		Pass passVerdict = concreteTC.getTestCaseIntent().getPass();
		/** for EachVerdictSet assert Pass */
		ArrayList<Object> passOutput = passVerdict.getVerdictSet();

		StringBuilder inCatch = new StringBuilder();
		for (int i = 0; i < passOutput.size(); i++) {
			String out;
			if (passOutput.get(i) instanceof String) {
				out = (String) passOutput.get(i);
				if (out.contains(DISPLAY)) {
					addln();
					inTry.append(ISDISPLAY + SEMICOLON + "\n");
				}
			} else if ((passOutput.get(i) instanceof String)
					&& Actions.Exceptions.contains(passOutput.get(i))) {
				out = (String) passOutput.get(i);
				/** if Activity Start Activity Under test */
				String verdict = ASSERTTRUE + LBRACKETS + QUOTE
						+ passVerdict.getVerdictName() + QUOTE + COMA + TRUE
						+ RBRACKETS + SEMICOLON + "\n";
				setCatch(out, inCatch, verdict);
			}

		}
		/** put all pass assrt into try */
		tryAndCatch(inTry);
		/** Get Fail OutPut */
		/** represented by fail */
		Fail failVerdict = concreteTC.getTestCaseIntent().getFail();
		ArrayList<Object> failOutput = failVerdict.getVerdictSet();
		for (Object output : failOutput) {
			if ((output instanceof String)
					&& Actions.Exceptions.contains(output)) {
				String verdict = ASSERTTRUE + LBRACKETS + QUOTE
						+ passVerdict.getVerdictName() + QUOTE + COMA + TRUE
						+ RBRACKETS + SEMICOLON + "\n";
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

		// Add all non expected exception as Fail
		String verdict = "\n" + FAILNOTVULNERABLE + SEMICOLON + "\n";
		setCatch("Exception", inCatch, verdict);
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

	private void tryAndCatch(StringBuilder inTry) {
		addln();
		add(TRY);
		add(BEGIN);
		add(inTry);
		add(END);

	}

	private void setCatch(String exceptionName, StringBuilder inCatch,
			String verdict) {
		String exceptionShortName = exceptionName.substring(0, 2);
		inCatch.append(CATCH + LBRACKETS + exceptionName + " "
				+ exceptionShortName + RBRACKETS + BEGIN + "\n"
				// + Log(verdictName, exceptionShortName + GETMESSAGE) +
				// SEMICOLON
				+ "\n" + verdict + END + "\n");
	}

	public int getTestMethodNumber() {
		return mTestMethodNumber;
	}

	private String checkIntegrity() {
		String absolutePath = FILE.getAbsolutePath();
		String imp = absolutePath
				+ "/src/fr/openium/Junit/Util/integrityChecking";
		return imp;
	}

	private void putValues() {
		String absolutePath = FILE.getAbsolutePath();
		String imp = absolutePath + "/src/fr/openium/Junit/Util/putValues";
		writeMethod(imp);
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

}
