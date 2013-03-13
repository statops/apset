/**
 * 
 */
package fr.openium.mvc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.openium.Junit.Builder.TcGenerationTool;
import fr.openium.Junit.Builder.TcGenerationToolIntegrity;
import fr.openium.Junit.pairwise.PairWise;
import fr.openium.Junit.xml.JunitResultXmlparser;
import fr.openium.automaticOperation.AntManager;
import fr.openium.automaticOperation.FileManager;
import fr.openium.automaticOperation.RunManager;
import fr.openium.iosts.Iosts;
import fr.openium.iosts.IostsAbstractTestCase;
import fr.openium.iosts.IostsConcreteTestCase;
import fr.openium.iosts.IostsParser;
import fr.openium.iosts.IostsSpecification;
import fr.openium.iosts.IostsVulnerabilities;
import fr.openium.results.JunitResultData;
import fr.openium.specification.dot.DotToGraphviz;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;
import fr.openium.specification.xml.StreamException;
import fr.openium.testcases.SymbolicTreeGeneration;
import graphiz.api.GraphViz;

public class Model extends Observable {

	private File mManifestfile;
	private File mStringfile;
	private File mJUNIT;
	private File mTestProject;
	private Boolean MaxTest = false;
	private static StringBuilder mLog = new StringBuilder();
	private String mAdbCommand;

	private String mApkProjectName;
	private String mTestApkName;

	private static ManifestData mManifestData = null;
	public static Iosts mIostsActivityVulnerability = new IostsVulnerabilities();
	public static Iosts mIostsServiceVulnerability = new IostsVulnerabilities();
	private static ArrayList<GraphViz> mGraphVizs = new ArrayList<GraphViz>();
	private static ArrayList<IostsSpecification> mIostsSpecifications = new ArrayList<IostsSpecification>();
	private static ArrayList<IostsAbstractTestCase> mIostsAbstractTestCases = new ArrayList<IostsAbstractTestCase>();
	private static ArrayList<IostsConcreteTestCase> mIostsConcreteTestCases = new ArrayList<IostsConcreteTestCase>();

	public Model() {

	}

	public void openfile(String choice) {
		setChanged();
		notifyObservers(choice);
	}

	public void setManifestXMLFile(File selectedFile) {
		mManifestfile = selectedFile;
	}

	public void setStringXMLFile(File selectedFile) {
		mStringfile = selectedFile;
	}

	public void setAdb(String adbpath) {
		mAdbCommand = adbpath;
	}

	public void setJUNITFile(File selectedFile) {
		mJUNIT = selectedFile;
	}

	public void setTestProject(File selectedFile) {
		mTestProject = selectedFile;
		if (mTestProjectPath == null) {
			mTestProjectPath = mTestProject.getPath();
		}
	}

	public File getStringFile() {
		return mStringfile;
	}

	public void generate() throws FileNotFoundException {

		generate(mManifestfile, mStringfile, mVulnerabityType, mAdbCommand);
		setChanged();
		notifyObservers(MainPanel.JUNITPATHFILE);

	}

	public void reset() {
		mManifestfile = null;
		mStringfile = null;
		mJUNIT = null;
		mTestProject = null;
		MaxTest = false;
		mLog.setLength(0);
		mAdbCommand = null;

		mApkProjectName = null;
		mTestApkName = null;

		mManifestData = null;
		mIostsAbstractTestCases.clear();
		mIostsConcreteTestCases.clear();
		mGraphVizs.clear();
		mIostsSpecifications.clear();
		setChanged();
		notifyObservers(MainPanel.RESET);
	}

	public boolean generate(File manifest, File string, String vulnerability,
			String adbPath) throws FileNotFoundException {

		mManifestData = null;
		mIostsAbstractTestCases.clear();
		mIostsConcreteTestCases.clear();
		mGraphVizs.clear();
		mIostsSpecifications.clear();
		InputStream manifestStream = null;
		try {

			manifestStream = new FileInputStream(manifest);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (NullPointerException nu) {
			mGenerateStatus = false;
			return false;

		}

		// @output : ManifestData
		try {
			mManifestData = AndroidManifestParser.parse(manifestStream);
		} catch (SAXException e) {
			mGenerateStatus = false;
			e.printStackTrace();
		} catch (IOException e) {
			mGenerateStatus = false;
			e.printStackTrace();
		} catch (StreamException e) {
			mGenerateStatus = false;
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			mGenerateStatus = false;
			e.printStackTrace();
		}

		// @ input: ManifestData
		DotToGraphviz test = new DotToGraphviz();

		/**
		 * @ output 1:
		 * 
		 * - the dotFile
		 * 
		 * - GifFile from Dotfile
		 */
		mGraphVizs = test.createIostsDot(mManifestData);
		// test.createGifFile(gv);

		/**
		 * @ouput 2 : IostsSet of Specifcation
		 * 
		 * */
		mIostsSpecifications = IostsParser
				.getIostsSpecSetFromData(mManifestData);

		/**
		 * gnerate dotfile from iosts spec if component is an activity, it has
		 * been created in absoluthpath +/dotFile/activities if component is a
		 * service, it has been created in absoluthpath +/dotFile/services
		 * */
		IostsParser.generateSpecDotFile(mIostsSpecifications);

		/**
		 * Generation of IostsVulnerabilities
		 * 
		 * 
		 * */

		File file = new File("");
		String absoluthpath = file.getAbsolutePath();

		if (vulnerability.equals(MainPanel.AVAILABILITY)) {
			// generation of availability for activity

			InputStream activityAvailabilityDotfile = new FileInputStream(
					absoluthpath
							+ "/src/fr/openium/vulnerabilities/dot/activity/availability.dot");
			mIostsActivityVulnerability = IostsParser.parseDotIosts(
					activityAvailabilityDotfile, mIostsActivityVulnerability);

			// generation of availability for service
			InputStream serviceAvailabilityDotfile = new FileInputStream(
					absoluthpath
							+ "/src/fr/openium/vulnerabilities/dot/service/availability.dot");
			mIostsServiceVulnerability = IostsParser.parseDotIosts(
					serviceAvailabilityDotfile, mIostsServiceVulnerability);
		} else if (vulnerability.equals(MainPanel.INTEGRITY)) {
			InputStream activityAvailabilityDotfile = new FileInputStream(
					absoluthpath
							+ "/src/fr/openium/vulnerabilities/dot/activity/integrity.dot");

			mIostsActivityVulnerability = IostsParser.parseDotIosts(
					activityAvailabilityDotfile, mIostsActivityVulnerability);

			// generation of availability for service
			InputStream serviceAvailabilityDotfile = new FileInputStream(
					absoluthpath
							+ "/src/fr/openium/vulnerabilities/dot/service/integrity.dot");

			mIostsServiceVulnerability = IostsParser.parseDotIosts(
					serviceAvailabilityDotfile, mIostsServiceVulnerability);
		}
		/**
		 * Generation of abstract test cases from specifcation and vulnerability
		 */
		mLog.append("Abstract TestCase .....OK");
		System.out.println(".....Abstract TestCase .....");
		SymbolicTreeGeneration tCgen;
		for (IostsSpecification spec : mIostsSpecifications) {
			// System.out.println(spec.getName());
			if (spec != null) {

				if (spec.getType().equals(IostsParser.SERVICE_TYPE)) {
					tCgen = new SymbolicTreeGeneration(spec,
							(IostsVulnerabilities) mIostsServiceVulnerability);

				} else if (spec.getType().equals(IostsParser.ACTIVITY_TYPE)) {
					tCgen = new SymbolicTreeGeneration(spec,
							(IostsVulnerabilities) mIostsActivityVulnerability);
				} else {
					tCgen = null;
				}

				if (tCgen != null) {
					IostsAbstractTestCase tc = (IostsAbstractTestCase) tCgen
							.generateAbstractTestCases();
					mIostsAbstractTestCases.add(tc);
				}
			}
		}

		/** Output 2 : dotFile */
		IostsParser.generateAbstractTestCaseDotFile(mIostsAbstractTestCases);

		/**
		 * Generation of Concrete test cases for each abstractTestCase
		 */
		System.out.println(".....Concretisation .....");
		for (IostsAbstractTestCase absTC : mIostsAbstractTestCases) {
			System.out.println(absTC.getName());
			IostsConcreteTestCase concreteTestCase = new IostsConcreteTestCase(
					absTC, mManifestData);
			mIostsConcreteTestCases.add(concreteTestCase);

		}

		/**
		 * Generation of JUNIT test cases
		 */

		System.out.println(".....Junit TestCase .....");
		if (vulnerability.equals(MainPanel.AVAILABILITY)) {
			TcGenerationTool generator = new TcGenerationTool();
			generator.setStringXmlFilePath(string);
			for (IostsConcreteTestCase concreteTC : mIostsConcreteTestCases) {
				generator.generateTc(concreteTC);
			}
			System.out.println("Number of test ....."
					+ generator.getTestMethodNumber());
			if (generator.getTestMethodNumber() == 0) {
				mGenerateStatus = false;
				setMessage("JUnit Code Generation failed");
			} else {
				mGenerateStatus = true;
				setMessage("JUnit Code Generation Finished");
			}
			System.out.println("END");
		} else if (vulnerability.equals(MainPanel.INTEGRITY)) {
			TcGenerationToolIntegrity generator = new TcGenerationToolIntegrity();
			generator.setStringXmlFilePath(string);
			for (IostsConcreteTestCase concreteTC : mIostsConcreteTestCases) {
				generator.generateTc(concreteTC);
			}
			mLog.append('\n');
			mLog.append("Junit TestCase .....OK");

			System.out.println("Number of test ....."
					+ generator.getTestMethodNumber());
			System.out.println("END");
			mLog.append('\n');
			mLog.append("Number of test ....."
					+ generator.getTestMethodNumber());
			mLog.append('\n');

		}

		// set Junit file and copy activity test and service test
		if (mTestIntentPackage == null) {
			String pack = mManifestData.getPackage();
			pack = pack.replaceAll("\\.", "/");
			mTestIntentPackage = new File(mTestProject.getPath() + "/src/"
					+ pack + "/test/Intent");
			if (!mTestIntentPackage.exists()) {

			}
		}
		File f = new File("");
		mJUNIT = new File(f.getAbsolutePath() + "/"
				+ TcGenerationTool.ACTIVITYPATH);
		boolean b = FileManager.copyDirectorytoDirectory(mJUNIT,
				mTestIntentPackage);
		System.out.println("Copy file from " + mJUNIT.getPath() + " to "
				+ mTestIntentPackage.getPath() + ": " + b);
		mJUNIT = new File(f.getAbsolutePath() + "/"
				+ TcGenerationTool.SERVICEPATH);
		FileManager.copyDirectorytoDirectory(mJUNIT, mTestIntentPackage);
		System.out.println("Copy file from " + mJUNIT.getPath() + " to "
				+ mTestIntentPackage.getPath() + ": " + b);

		// Copy testname list into Testing project

		System.out.println("Copy file testname "
				+ FileManager.copyFiletoFile(new File(f.getAbsolutePath()
						+ "/JUNIT/testcasenamelist/testname"), new File(
						mTestingToolPath + "/res/raw/testname")));

		return true;
	}

	private String mTestingToolPath;
	private static boolean mInstallStatus;
	private String mMessage;

	public boolean installAll() {
		AntManager ant = new AntManager();
		ArrayList<Boolean> done = new ArrayList<Boolean>();
		ant.setStdErr(null);
		mMessage = "";
		// Check if Emulator is connected
		if (!checkEmulator()) {
			setInstallStatus(false);
			setMessage("Emulator is not Connected");
			setChanged();
			notifyObservers(MainPanel.INSTALL);
			return false;
		}

		// Install the application under test
		ant.clean(mProjectPath + "/build.xml", mSdkPath);
		ant.debug(mProjectPath + "/build.xml", mSdkPath);
		ant.installd(mProjectPath + "/build.xml", mSdkPath);

		if (ant.getStdErr() != null) {
			setInstallStatus(false);
			setMessage("Project build/installation failed");
			setChanged();
			notifyObservers(MainPanel.INSTALL);
			return false;
		}
		ant.setStdErr(null);

		// Install the Test
		ant.clean(mTestProjectPath + "/build.xml", mSdkPath);
		ant.debug(mTestProjectPath + "/build.xml", mSdkPath);
		ant.installd(mTestProjectPath + "/build.xml", mSdkPath);
		if (ant.getStdErr() != null) {
			setInstallStatus(false);
			setMessage("Test Project build/installation failed");
			setChanged();
			notifyObservers(MainPanel.INSTALL);
			return false;
		}

		ant.setStdErr(null);

		// Install the TestingTool
		ant.clean(mTestingToolPath + "/build.xml", mSdkPath);
		ant.debug(mTestingToolPath + "/build.xml", mSdkPath);
		ant.installd(mTestingToolPath + "/build.xml", mSdkPath);
		if (ant.getStdErr() != null) {
			setMessage("Testing tool build/installation failed");
			setInstallStatus(false);
			setChanged();
			notifyObservers(MainPanel.INSTALL);
			return false;
		}

		if (done.contains(false)) {
			// launchTesting();
			setInstallStatus(false);
		} else {
			// launchTesting();
			setInstallStatus(true);
		}

		setMessage("All Android Project build/installation Succed");
		setChanged();
		notifyObservers(MainPanel.INSTALL);
		return true;

	}

	private boolean checkEmulator() {
		checkAdb();
		RunManager run = new RunManager();
		run.shell(mAdbCommand);
		if (run.getError())
			return false;
		else
			return true;
	}

	public boolean launchTesting() {
		checkAdb();
		String value = new AntManager()
				.exec(mAdbCommand
						+ " shell am start -a intent.action.test -n com.example.testingtool/.DetailActivity");
		setChanged();
		notifyObservers(MainPanel.LAUNCH);
		return true;
	}

	public void tesLaunch(String apkProject) {
		ArrayList<Boolean> done = new ArrayList<Boolean>();
		RunManager run = new RunManager();
		// install the appli
		boolean value = run.install(mAdbCommand, apkProject);
		System.out.println("install the appli:  " + value);
		done.add(value);
		System.out.println("done:  " + done.get(0));
	}

	public Boolean getMaxTest() {
		return MaxTest;
	}

	public void setMaxTest(Boolean maxTest) {
		MaxTest = maxTest;
	}

	public void setNbMaxTest(String time) {
		try {
			PairWise.setNbTestMax(Integer.parseInt(time));
		} catch (NumberFormatException e) {
		}
	}

	public String getLog() {
		return mLog.toString();
	}

	private static String mVulnerabityType = null;

	public void setVulnerabiltyType(String nom) {

		mVulnerabityType = nom;
		setChanged();
		notifyObservers(mVulnerabityType);

	}

	private String XMLResultPath = "/XMLResult";

	private ArrayList<JunitResultData> mResults = new ArrayList<JunitResultData>();

	public void showResult() {

		/** creation d'un thread periodique jusqu'ˆ ce que getresult vrai */
		getResult();
		System.out.println("VUL: " + getVulNumber());
		System.out.println("NVUL: " + getNvulNumber());
		setChanged();
		notifyObservers(MainPanel.SHOWRESULT);

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
			}
			dIn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;
	}

	public static boolean mfileStatus;

	public boolean getResult() {
		checkAdb();
		mfileStatus = false;
		CheckOk update = new CheckOk("count");
		update.start();
		RunManager run = new RunManager();
		File f = new File("");
		int count = 0;

		while (true) {

			if (mfileStatus) {
				System.out.println(" Ok file  " + mfileStatus);
				break;
			}
			// pause de x temps;
			for (int i = 0; i < 20000; i++) {
				System.out.print("-" + count++);
			}
			// ˆ limiter par un max de temps.

			// System.out.print(" file status:  " + mfileStatus);
			if (mEmulatorStatus) {
				System.out.println(" Device problem  " + mEmulatorStatus);
				break;
			}
		}
		if (mEmulatorStatus) {
			return false;
		}
		String testResult = "/mnt/sdcard/testResults";
		// pull operation
		mfileStatus = run.pull(mAdbCommand, testResult,
				(new File("")).getAbsolutePath() + XMLResultPath);
		System.out.println("pull xml  " + mfileStatus);
		mResults.clear();
		File result = new File(f.getAbsolutePath() + XMLResultPath);
		File[] resultFile = result.listFiles();
		for (File junit : resultFile) {
			if (junit.getPath().equalsIgnoreCase(
					f.getAbsolutePath() + XMLResultPath + "/ok"))
				System.out.println("delete: " + result.delete());
			else if (junit.isFile()) {

				mResults.add(JunitResultXmlparser.parse(junit));
			}
		}
		if (mfileStatus)
			return true;
		else {
			System.out.print(" file status:  " + mfileStatus);
			System.out.print(" pull failed  ");
			return false;
		}
	}

	/**
	 * set the adb value
	 */
	private void checkAdb() {
		if (mAdbCommand == null) {
			mAdbCommand = mSdkPath + "/platform-tools/adb";
		}

	}

	public boolean getEmulatorStatus() {
		return mEmulatorStatus;
	}

	public int getVulNumber() {
		int vul = 0;
		for (JunitResultData ju : mResults) {
			if (ju.isVul()) {
				vul++;
			}
		}

		return vul;
	}

	public int getNvulNumber() {
		int nvul = 0;
		for (JunitResultData ju : mResults) {
			if (!ju.isVul()) {
				nvul++;
			}
		}
		return nvul;
	}

	public String getProjectPath() {
		return mApkProjectName;
	}

	public void setApkProjectPath(String mProjectPath) {
		this.mApkProjectName = mProjectPath;
	}

	public String getTestApkPath() {
		return mTestApkName;
	}

	public void setTestApkPath(String mTestApkPath) {
		this.mTestApkName = mTestApkPath;
	}

	private String mSdkPath;

	public void setSdk(String mSdkPath) {
		this.mSdkPath = mSdkPath;
	}

	private String mTestProjectPath = null;
	private File mTestIntentPackage = null;
	private String mProjectPath;

	public void setTestProjectPath(String mTestProjectPath) {
		this.mTestProjectPath = mTestProjectPath;
		this.mTestProject = new File(mTestProjectPath);

	}

	private boolean mEmulatorStatus;

	private void setTestIntentProjectFile(File testprojectpath) {
		this.mTestIntentPackage = testprojectpath;
	}

	public void setProjectPath(String mTestProjectPath) {
		this.mProjectPath = mTestProjectPath;

	}

	public String getTestingToolPath() {
		return mTestingToolPath;
	}

	public void setTestingToolPath(String mTestingToolPath) {
		this.mTestingToolPath = mTestingToolPath;
	}

	class CheckOk extends Thread {
		RunManager run = new RunManager();
		File f = new File("");
		String ok = "/mnt/sdcard/testResults/ok";
		boolean present = false;
		boolean itsTime = false;
		long time = System.currentTimeMillis();
		boolean checkFile;

		public CheckOk(String name) {
			super(name);
		}

		@Override
		public void run() {
			System.out.println("befor While" + mfileStatus);
			while (true) {
				boolean b = run.pull(mAdbCommand, ok,
						(new File("")).getAbsolutePath() + XMLResultPath
								+ "/ok");
				if (!b) {

					mEmulatorStatus = run.getError();

				}
				System.out.println("pull: " + b);
				File result = new File(f.getAbsolutePath() + XMLResultPath
						+ "/ok");
				if (result.exists()) {
					// if (getText(result).toString().contains("1"))
					mfileStatus = true;
					System.out.println("In While " + mfileStatus);

					break;
				} else {
					mfileStatus = false;
					System.out.println("In While " + mfileStatus);

				}

				if (mEmulatorStatus)
					break;
			}

		}
	}

	boolean mGenerateStatus = false;

	public boolean getGenerateStatus() {
		return mGenerateStatus;
	}

	public void isUnchecked(String nom) {
		setChanged();
		notifyObservers("N" + nom);

	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public boolean isInstalled() {
		return mInstallStatus;
	}

	public void setInstallStatus(boolean mInstallStatus) {
		this.mInstallStatus = mInstallStatus;
	}

}
