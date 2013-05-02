package fr.openium.ic.Junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JStatement;
import com.sun.codemodel.JVar;

import fr.openium.Junit.Builder.AbstractTcGenerationTool;
import fr.openium.Junit.xml.StringXmlParser;
import fr.openium.ic.Db.DatabaseStructure;
import fr.openium.ic.Db.DatabaseStructureParser;
import fr.openium.iosts.IostsSpecification;
import fr.openium.specification.config.RVString;
import fr.openium.specification.xml.ManifestData;

public class apkJTc extends AbstractTcGenerationTool {

	public final static String M_VALUES = "mValueToCheck";
	public final static String M_PACKAGEPATH = "/src/fr/openium/ic";
	public final static String GENERATEDPATH = "/JUNIT/integrity";
	public final static int STATUS = 1;

	public apkJTc() {
		super(STATUS);
		mtcListFile = new File(new File("") + TCLISTPATH + "ic"
				+ testcasenamelist);
	}

	public boolean generateTc(IostsSpecification spec, ManifestData man) {
		mManifestData = man;
		return generateTc(man, spec.getName());
	}

	/**
	 * 
	 * @param The
	 *            manifest data
	 * @param componentName
	 * @return -true if junit code is well generated
	 */
	public boolean generateTc(ManifestData man, String componentName) {

		String testname;
		StringTokenizer tokenn = new StringTokenizer(componentName, ".");
		do {
			testname = tokenn.nextToken();
		} while (tokenn.hasMoreTokens());

		JCodeModel cm = new JCodeModel();
		cm.directClass("android.view.View");
		cm.directClass("java.util.StringTokenizer");
		cm.directClass("java.util.Random");

		JDefinedClass dclass;
		try {
			// dclass = cm._class("test.ActivityTest");
			dclass = cm._class(man.getPackage() + DOT + TEST + DOT + INTENT
					+ DOT + testname + "CP" + TEST);

			// dclass = cm._class("test.ActivityTest");
			JClass ActivityInstrumentationTestCase2 = cm
					.ref("android.test.ActivityInstrumentationTestCase2");

			// l'activité à tester
			JClass ActivitytoTest = cm.ref(componentName);

			// extends
			JClass mextends = cm.ref(ActivityInstrumentationTestCase2
					.fullName());
			dclass._extends(mextends);

			JClass ContentProvider = cm.ref(ContentResolver.class.getName());
			JClass Solo = cm.ref(com.jayway.android.robotium.solo.Solo.class
					.getName());
			JClass Context = cm.ref(android.content.Context.class.getName());
			JClass Uri = cm.ref(android.net.Uri.class.getName());
			JClass Instrumentation = cm.ref(android.app.Instrumentation.class
					.getName());
			JClass Arraylist = cm.ref(ArrayList.class.getName());
			JClass String = cm.ref(String.class.getName());
			JClass ContentValue = cm.ref(ContentValues.class.getName());
			JClass Random = cm.ref(java.util.Random.class.getName());
			JClass StringTokinezer = cm.ref(StringTokenizer.class.getName());

			JClass ArraylistString = cm.ref(Arraylist.fullName())
					.narrow(String);
			JClass View = cm.ref(android.view.View.class.getName());
			JClass ArraylistView = cm.ref(Arraylist.fullName()).narrow(View);
			// class fields
			JVar mContentResolver = dclass.field(JMod.PRIVATE, ContentProvider,
					"mContentResolver");
			JVar mSolo = dclass.field(JMod.PRIVATE, Solo, "mSolo");
			JVar mContext = dclass.field(JMod.PRIVATE, Context, M_CONTEXT);
			JVar mInstrumentation = dclass.field(JMod.PRIVATE, Instrumentation,
					M_INSTRUMENTATION);
			JVar mValueToCheck = dclass.field(JMod.PRIVATE, ArraylistString,
					M_VALUES);
			JVar mUri = dclass.field(JMod.PRIVATE, Uri, M_URI);
			// JVar mColumnName = dclass.field(JMod.PRIVATE, ArrayOfString,
			// "mColumnName");
			JVar mContentValue = dclass.field(JMod.PRIVATE, ContentValue,
					"mContentValues");
			/**
			 * the target package ID
			 */
			JVar TARGET_PACKAGE_ID = dclass.field(JMod.PRIVATE + JMod.STATIC
					+ JMod.FINAL, String, "TARGET_PACKAGE_ID");
			TARGET_PACKAGE_ID.init(JExpr.lit(man.getPackage()));

			/**
			 * the Class simple name
			 */

			JVar LAUNCHER_ACTIVITY_FULL_CLASSNAME = dclass.field(JMod.PRIVATE
					+ JMod.STATIC + JMod.FINAL, String,
					"LAUNCHER_ACTIVITY_FULL_CLASSNAME");
			LAUNCHER_ACTIVITY_FULL_CLASSNAME.init(JExpr.lit(componentName));

			/***
			 * the class under test definition
			 */

			String Component_UnderTest = "private static Class<?> mComponent;static {try {mComponent = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);} catch (ClassNotFoundException e) {throw new RuntimeException(e);}}";
			dclass.direct(Component_UnderTest);

			// Le constructeur
			JMethod constructor = dclass.constructor(JMod.PUBLIC);
			constructor.body().directStatement(
					SUPER + "(" + TARGET_PACKAGE_ID.name() + COMA
							+ "mComponent);");

			// le setup
			JMethod setup = dclass.method(JMod.PROTECTED, cm.VOID, "setUp");
			setup._throws(cm.ref(Exception.class.getName()));

			// setup.body().
			// JExpression cp = mContext.invoke("getContentResolver");
			JBlock setupBlock = setup.body();
			setupBlock.directStatement(mInstrumentation.name()
					+ " = getInstrumentation();");

			setupBlock.directStatement(mContext.name()
					+ " = mInstrumentation.getTargetContext();");
			setupBlock.directStatement(mContentResolver.name()
					+ " = mContext.getContentResolver();");

			setupBlock.directStatement(mSolo.name()
					+ " = new Solo(mInstrumentation, getActivity());");
			setupBlock.directStatement(mValueToCheck.name()
					+ " = new ArrayList<String>();");

			setupBlock.directStatement(mContentValue.name()
					+ " = new ContentValues();");
			setupBlock.directStatement(mUri.name() + " = null;");

			setupBlock.directStatement("super.setUp();");
			// methode test generique

			JMethod test = dclass.method(JMod.PUBLIC, cm.VOID, "testIntegrity");
			JBlock testBlock = test.body();
			testBlock.directStatement("init();");
			@SuppressWarnings("unused")
			JVar views = testBlock.decl(ArraylistView, "views");
			testBlock.directStatement("views = new ArrayList<View>();");
			testBlock
					.directStatement("views = mSolo.getViews();assertTrue(!views.isEmpty());");
			testBlock
					.directStatement("for (String value : mValueToCheck) assertFalse(\"VULNERABLE\", mSolo.searchText(value));");

			mTestcaseName.append(man.getPackage() + DOT + TEST + DOT + INTENT
					+ DOT + testname + "CP" + TEST + DIEZ + "testIntegrity");
			mTestcaseName.append('\n');

			// les methodes de test
			JMethod setValues = dclass
					.method(JMod.PUBLIC, cm.VOID, "setValues");
			JBlock setValue = setValues.body();
			setValue.directStatement("mValueToCheck = new ArrayList<String>();");
			// recuperation des valeurs parsés
			InputStream is = null;
			try {
				is = new FileInputStream(new File("").getAbsolutePath()
						+ M_PACKAGEPATH + "/xmlTemplate/Injections.xml");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			ArrayList<String> values = (ArrayList<String>) StringXmlParser
					.parse(is);
			for (String value : values) {
				StringTokenizer token = new StringTokenizer(value, ";");
				do {
					setValue.directStatement(M_VALUES + ".add(\""
							+ token.nextToken().replaceAll("\\s", "") + "\");");
				} while (token.hasMoreTokens());
			}
			// Methode init
			JMethod init = dclass.method(JMod.PUBLIC, cm.VOID, "init");
			JBlock initBlock = init.body();
			File initFile = new File(new File("").getAbsolutePath()
					+ M_PACKAGEPATH + "/Util/init");
			initBlock.add(readExternalFile(initFile));
			for (DatabaseStructure db : mdb) {

				initBlock.directStatement("initDatabase(\""
						+ db.getAuth().replaceAll("\\s", "") + "\"," + "\""
						+ db.getUri().replaceAll("\\s", "") + "\"," + "\""
						+ db.getCol().replaceAll("\\s", "") + "\");");
			}

			// Methode initdatabase
			JMethod initDatabase = dclass.method(JMod.PRIVATE, cm.VOID,
					"initDatabase");

			initDatabase.param(String, "Auth");
			initDatabase.param(String, "uri");
			initDatabase.param(String, "column");
			JVar col = initDatabase.body().decl(StringTokinezer, "col");
			File initDatabaseFile = new File(new File("").getAbsolutePath()
					+ M_PACKAGEPATH + "/Util/initDatabase");
			initDatabase.body().add(readExternalFile(initDatabaseFile));

			// methode Set contentvalue
			JMethod setContentValue = dclass.method(JMod.PRIVATE, cm.VOID,
					"setContentValues");
			setContentValue.param(ArraylistString, "text");
			File setContentFile = new File(new File("").getAbsolutePath()
					+ M_PACKAGEPATH + "/Util/setContentValue");
			setContentValue.body().add(readExternalFile(setContentFile));

			// methode randpm
			JMethod random = dclass.method(JMod.PRIVATE, String, "random");

			JVar value = random.body().decl(String, "value");
			JVar rd = random.body().decl(Random, "rd");

			random.param(ArraylistString, "list");
			random.body().add(
					readExternalFile(new File(new File("").getAbsolutePath()
							+ M_PACKAGEPATH + "/Util/random")));
			random.body()._return(value);

			File file = new File(new File("").getAbsolutePath() + GENERATEDPATH);
			file.mkdir();
			try {
				cm.build(file);
			} catch (IOException e) {

				e.printStackTrace();
				return false;
			}
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
			return false;
		}

		writeTcList();
		return true;
	}

	public boolean generateTc() {
		generateTc(new ManifestData(), "act");
		return false;

	}

	private JStatement readExternalFile(File file) {
		JBlock ablock = new JBlock();
		return ablock.directStatement(getText(file).toString());

	}

	ArrayList<DatabaseStructure> mdb = new ArrayList<DatabaseStructure>();

	public void setDb(String path) {
		InputStream is;
		try {
			is = new FileInputStream(path);
			mdb = DatabaseStructureParser.parse(is);
		} catch (FileNotFoundException e) {
			// set a default value according to CP parameters
			for (String auth : mManifestData.getCPauthorities())
				mdb.add(new DatabaseStructure(auth,
						random(RVString.RANDOM_TABLE),
						random(RVString.RANDOM_PATH)));
			e.printStackTrace();
		}

	}
}