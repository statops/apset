package fr.openium.Junit.Builder;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import fr.openium.Junit.xml.StringXmlParser;
import fr.openium.iosts.IostsConcreteTestCase;
import fr.openium.specification.config.Extra;
import fr.openium.specification.config.RVString;
import fr.openium.specification.config.Type;
import fr.openium.specification.xml.ManifestData;

/**
 * 
 * @author Stassia
 * 
 * 
 *         generate Java JUnit code from IostsConcrets
 * 
 */
public abstract class AbstractTcGenerationTool {
	protected static final String BEGIN = "{";
	protected static final String END = "}";
	protected static final String SEMICOLON = ";";
	protected static final String BRACKETS = "()";
	protected static final String LBRACKETS = "(";
	protected static final String RBRACKETS = ")";
	protected static final String EQUAL = "=";
	protected static final String COMA = ",";
	protected static final String QUOTE = "\"";
	protected static final String DOT = ".";
	protected static final String DIEZ = "#";
	/** KeyWords */
	protected static final String CLASS = ".class";
	protected static final String PUBLIC_CLASS = "public class";
	protected static final String EXTENDS = "extends";
	protected static final String PROTECTED = "protected";
	protected static final String VOID = "void";
	protected static final String SETUP = "setUp()";
	protected static final String PUBLIC = "public";
	protected static final String PUBLIC_VOID = "public void";
	protected static final String TEST = "test";

	protected static final String NULL = "null";
	protected static final String FALSE = "false";
	protected static final String TRUE = "true";
	protected static final String INTENT_FLAG_ACTIVITY_NEW_TASK = "Intent.FLAG_ACTIVITY_NEW_TASK";
	protected static final String TRY = "try";
	protected static final String CATCH = "catch";
	protected static final String THROWS = "throws";
	protected static final String EXCEPTION = "Exception";
	protected static final String DISPLAY = "display";
	protected static final String PACKAGE = "package";
	protected static final String IMPORT = "import";

	/** Native Method */
	protected static final String GETINSTRUMENTATION = "getInstrumentation()";
	protected static final String NEWINTENT = "new Intent()";
	protected static final String GETNAME = ".getName()";
	protected static final String STARTACTIVITYSYNC = ".startActivitySync(mIntent)";
	protected static final String SETACTIVITYINTENT = "setActivityIntent(mIntent)";
	protected static final String WAITORMONITOR = "waitForMonitor(monitor)";
	protected static final String GETACTIVITYMANAGER = "(ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE)";
	protected static final String GETCONTEXT = "getContext()";
	protected static final String STARTSERVICE = "startService(mIntent)";
	protected static final String GETSERVICE = "getService()";
	protected static final String GETACTIVITY = "getActivity()";
	protected static final String BINDSERVICE = "bindService(mIntent)";
	protected static final String ISDISPLAY = "isDisplay()";
	protected static final String GETMESSAGE = ".getMessage()";
	/** Assert Methods */
	protected static final String ASSERTNOTNULL = "assertNotNull";
	protected static final String ASSERT = "assert";
	protected static final String ASSERTTRUE = "assertTrue";
	// private static final String FAIL = "fail";
	protected static final String ASSERTTRUENOTVULNERABLE = "assertTrue(\"NOT_VULNERABLE\",true)";
	protected static final String FAILNOTVULNERABLE = "fail(\"NOT_VULNERABLE\")";
	protected static final String FAILVULNERABLE = "fail(\"VULNERABLE\")";

	/** Intent filling */
	protected static final String SETACTION = ".setAction";
	protected static final String ADDCATEGORY = ".addCategory";
	protected static final String SETDATA = ".setData";
	protected static final String SETTYPE = ".setType";
	protected static final String SETFLAGS = ".setFlags";
	protected static final String PUTEXTRA = ".putExtra";
	protected static final String SETCLASS = ".setClass";
	protected static final String URISPARSE = "Uri.parse";

	/** Mandatory Class Attributes */
	protected static final String INSTRUMENTATION = "Instrumentation";
	protected static final String ACTIVITY = "Activity";
	protected static final String SERVICE = "Service";
	protected static final String INTENT = "Intent";
	protected static final String VIEWS = "ArrayList<View>";
	protected static final String ACTIVITYMANAGER = "ActivityManager";

	/** Mandatory Class Attributes name */
	protected static final String M_INSTRUMENTATION = "mInstrumentation";
	protected static final String M_ACTIVITY = "mActivity";
	protected static final String M_SERVICE = "mService";
	protected static final String M_INTENT = "mIntent";
	protected static final String M_CONTEXT = "mContext";
	protected static final String M_VIEWS = "mViews";
	protected static final String M_ACTIVITYMANAGER = "mActivityManager";

	/** Junit generation TC path */
	// /SpecGen/JUNIT
	protected static final File FILE = new File("");
	public final static String ACTIVITYPATH = "JUNIT/activity/";
	public static final String SERVICEPATH = "JUNIT/service/";
	public static final String TCLISTPATH = "JUNIT/testcasenamelist/";

	protected static final String ACTIVITYINSTRUMENTATIONTESTCASE = "ActivityInstrumentationTestCase2";
	protected static final String SERVICETESTCASE = "ServiceTestCase";
	protected static final String SUPER = "super";
	protected static final String ACTIVITYMONITOR = "Instrumentation.ActivityMonitor";
	protected static final String MONITOR = "monitor";
	protected static final String ADDMONITOR = ".addMonitor";

	protected static final String OVERRIDE = "@Override";

	/** The source of Code */
	protected StringBuilder mCode = new StringBuilder();

	/**
	 * The List of testcases
	 * 
	 */

	protected StringBuilder mTestcaseName = new StringBuilder();

	/** Component Type */
	protected static final String ACTIVITY_TYPE = "activity";
	protected static final String SERVICE_TYPE = "service";

	/** StringXmlPath */

	protected File mStringXmlFile;// =
									// "/Users/Stassia/Documents/workspace/SpecGen/res/strings.xml";
	// private String mStringXmlFile =
	// "Users/STASSIA/Desktop/tESTrESULT/SpecGen/res/strings.xml";

	/** Pairwise sequence */
	protected ArrayList<Extra> mExtras = new ArrayList<Extra>();
	protected ArrayList<String> mPath = new ArrayList<String>();

	/**
	 * Number of testMethod to launch
	 */
	protected int mTestMethodNumber = 0;
	protected File mtcListFile;
	protected boolean istestProjectNameWritten = false;

	protected static final String GETCONTENTRESOLVER = "getInstrumentation().getContext().getContentResolver()";
	protected static final String NEWCONTENTVALUE = "new ContentValues()";
	protected static final String Cursor = "Cursor cursor=null;";
	protected static final String ColumnName = "columnName=cursor.getColumnNames();";
	protected static final String ColumnNameNull = "String [] columnName=null;";

	protected static final String QueryColumn = "mContentResolver.query(mUri, null, null, null, null)";
	protected static final String INSERT = "mContentResolver.insert(mUri, mValues);";

	/** Assert Methods */

	protected static final String URI = "Uri";
	protected static final String CONTENTRESOLVER = "ContentResolver";
	protected static final String CONTENTVALUE = "ContentValues";

	/** Mandatory Class Attributes name */

	protected static final String M_URI = " mUri";
	protected static final String M_ContentResolver = "mContentResolver";
	protected static final String M_CONTENTVALUES = "mValues";

	protected String mAction = "";
	protected ManifestData mManifestData;

	public AbstractTcGenerationTool() {
		mtcListFile = createFile(testcasenamelist, testcasenamelist);
		File activity = new File(FILE.getAbsolutePath() + "/" + ACTIVITYPATH);
		File service = new File(FILE.getAbsolutePath() + "/" + SERVICEPATH);
		resetFile(service);
		resetFile(activity);
	}

	protected void resetFile(File file) {

		if (!file.exists()) {
			file.mkdir();
		} else {
			deleteAllFile(file);
		}
	}

	public AbstractTcGenerationTool(int status) {
		if (status == 1) {

			mtcListFile = createFile(testcasenamelist, testcasenamelist);

		} else {
			deleteAllFile(new File(FILE.getAbsolutePath() + "/" + ACTIVITYPATH));

			deleteAllFile(new File(FILE.getAbsolutePath() + "/" + SERVICEPATH));

		}
	}

	private void deleteAllFile(File directory) {
		File[] files = directory.listFiles();
		if (files != null)
			for (File file : files) {
				if (file.exists())
					if (!file.delete()) {
						System.out.println("Failed to delete " + file);
					}
			}
	}

	/**
	 * Write to JavaFile
	 */
	public boolean writeInFile(File file, StringBuilder text) {
		try {
			FileUtils.write(file, text.toString(), "UTF-8");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	protected static final String testcasenamelist = "testname";

	/**
	 * Generate java file
	 * 
	 * @param name
	 *            of the file
	 * @return a file
	 * */

	protected File createFile(String name, String type) {
		File file = null;
		if (type.equals(ACTIVITY_TYPE)) {
			file = new File(ACTIVITYPATH + name + TEST + ".java");
		} else if (type.equals(SERVICE_TYPE)) {
			file = new File(SERVICEPATH + name + TEST + ".java");
		} else if (type.equals(testcasenamelist)) {
			file = new File(TCLISTPATH + testcasenamelist);
		}
		return file;

	}

	/**
	 * Adds a string to the code's source (without newline).
	 */
	protected void add(String line) {

		if (line.equals(SEMICOLON) || line.equals(END) || line.equals(BEGIN)) {
			addln(line);
		} else if (line.equals(LBRACKETS) || line.equals(BRACKETS)) {
			mCode.append(line);
		} else {
			mCode.append(line);
			addspace();
		}

	}

	protected void add(StringBuilder in) {
		mCode.append(in);

	}

	/**
	 * Adds a string to the code's source (with newline).
	 */
	protected void addln(String line) {
		mCode.append(line + "\n");
	}

	/**
	 * Adds a newline to the code's source.
	 */
	protected void addln() {
		mCode.append('\n');
	}

	/** Adds a space */
	protected void addspace() {
		mCode.append(" ");
	}

	/**
	 * Write the List of testcaseName
	 * 
	 */
	/**
	 * @param the
	 *            concrete Test Case s
	 * @return true if the file is created
	 */
	protected ArrayList<String> extra_key = new ArrayList<String>();

	protected void random(ArrayList<String> extra_key, int limit) {

		ArrayList<String> temp = new ArrayList<String>();
		String value = "";
		Random randomgenerator = new Random();
		for (int idx = 1; idx <= limit; ++idx) {
			int randomInt = randomgenerator.nextInt(extra_key.size());
			value = extra_key.get(randomInt);
			if (value.contains("\"")) {
				value.replaceAll("\"", "");
			}
			value.replaceAll("/s", "");
			temp.add(value);
		}
		extra_key.clear();
		extra_key.addAll(temp);

	}

	protected void writeTcList() {
		writeInFile(mtcListFile, mTestcaseName);
	}

	protected void writePackage(String packageName) {
		add(PACKAGE);
		add(packageName);
		add(SEMICOLON);
		addln();
	}

	private File getStringXmlFilePath() {
		return mStringXmlFile;
	}

	public void setStringXmlFilePath(File file) {
		mStringXmlFile = file;
		extra_key = StringXmlParser.parse(mStringXmlFile);
		mExtras.clear();
		for (String key : extra_key) {
			mExtras.add(new Extra(key, Type.TYPESET, RVString.RANDOM_STRING));
		}

	}

	protected void addtoTClist(IostsConcreteTestCase concreteTc, String method) {
		mTestcaseName.append(concreteTc.getPackageName() + DOT + TEST + DOT
				+ INTENT + DOT + getShortName(concreteTc.getName()) + TEST
				+ DIEZ + method);
		mTestcaseName.append('\n');
	}

	/**
	 * getShortName
	 * 
	 * exemple a.b.z ==> z
	 */
	protected String getShortName(String name) {
		StringTokenizer st = new StringTokenizer(name, ".");
		String value = st.nextToken();
		// will end in the last token
		while (st.hasMoreTokens()) {
			value = st.nextToken();
		}
		return value;

	}

	protected void addImport() {
		String absolutePath = FILE.getAbsolutePath();
		String imp = absolutePath + "/src/fr/openium/Junit/Util/import";
		writeMethod(imp);

	}

	protected void writeMethod(String from) {
		StringBuilder method = getText(new File(from));
		mCode.append(method);
	}

	public StringBuilder getText(File file) {
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

	protected void solveOutput(IostsConcreteTestCase concreteTC) {
		// for pass
		// concreteTC.getTestCaseIntent().getPass()
		// for fail

		// forinconclusive

	}

	protected String getRandomInt() {

		return random(RVString.Integer);
	}

	protected String random(ArrayList<String> randomValue) {
		Random rd = new Random();
		int randomInt = rd.nextInt(randomValue.size());
		return randomValue.get(randomInt);
	}

}
