package fr.openium;

import java.io.File;
import java.io.FileNotFoundException;

import fr.openium.Junit.pairwise.PairWise;
import fr.openium.mvc.Model;

/**
 * @author Stassia ZAFIMIHARISOA R.
 */
public class Main {

	private static String mProjectPath;
	private static String mAdbPath;
	private static String mSdkPath;
	private static String mTestProjectPath;
	private static String mVulnerabilityType;
	private static int mMaxTest;
	private static boolean mDone;
	private static String mTestingToolPath;
	static Model md;

	public Main() {
		md = new Model();

	}

	public static void main(String[] args) throws ClassNotFoundException,
			IllegalAccessException, InstantiationException,
			FileNotFoundException {

		/*
		 * ClassLoader parentClassLoader = Loader.class.getClassLoader(); Loader
		 * classLoader = new Loader(parentClassLoader); Class myObjectClass =
		 * classLoader.loadClass("reflection.MyObject");
		 */

		if (args.length == 14) {
			for (int i = 0; i < args.length - 1;) {
				if (args[i].equals("-f")) {
					mProjectPath = args[i + 1];
				}

				if (args[i].equals("-v")) {
					mVulnerabilityType = args[i + 1];
				}
				if (args[i].equals("-n")) {
					mMaxTest = Integer.parseInt(args[i + 1]);
					PairWise.setNbTestMax(mMaxTest);
				}

				if (args[i].equals("-adb")) {
					mAdbPath = args[i + 1];
				}

				if (args[i].equals("-t")) {
					mTestProjectPath = args[i + 1];
				}
				if (args[i].equals("-sdk")) {
					mSdkPath = args[i + 1];
				}
				if (args[i].equals("-tester")) {
					mTestingToolPath = args[i + 1];
				}

				i++;
			}

			System.out.println("Project Path : " + mProjectPath);
			System.out.println("Vulnerability type :" + mVulnerabilityType);
			generateJunitTestCase();
			if (md.installAll()) {
				if (md.launchTesting()) {
					System.out.println("Waiting for Result");
					md.showResult();
				}
			} else {
				System.out.println("Installation failed");
			}

		} else {

			if (args.length == 1 && mDone) {

				if (args[0].equals("-s")) {
					md.showResult();
				} else {
					System.out.println("Please insert parameter as follow: "
							+ "\n" + "-s" + "\n" + "to show result: ");

				}

			} else {
				System.out.println("Please insert parameters as follow: "
						+ "\n" + "\n" + "-f android project path" + "\n" + "\n"
						/* + "-ti android Intent test package path" + "\n" */
						+ "-v vulnerability type /  "
						+ "availability or integrity" + "\n" + "\n"
						+ "-adb android sdk/platformtool/adb" + "\n" + "\n"
						+ "-n number of test" + "\n" + "\n"
						/*
						 * + "-apk the apkProjectName.apk" + "\n" +
						 * "-apktest the apkTestProjectName.apk"
						 */
						+ "-sdk android sdk" + "\n" + "\n"
						+ "-t android test project path" + "\n");
			}

		}

	}

	private static void generateJunitTestCase() throws FileNotFoundException {
		md = new Model();
		// md.addObserver(Main.class);
		md.setSdk(mSdkPath);
		md.setTestProjectPath(mTestProjectPath);
		// md.setApkProjectPath(mProjectPath + "/bin/" + mApkProject);
		md.setProjectPath(mProjectPath);
		// md.setTestApkPath(mTestApkProject);
		md.setAdb(mAdbPath);
		md.setTestingToolPath(mTestingToolPath);
		boolean done = md.generate(new File(mProjectPath
				+ "/AndroidManifest.xml"), new File(mProjectPath
				+ "/res/values/strings.xml"), mVulnerabilityType, mAdbPath);
		if (done) {
			System.out.println("Java files are available on" + "\n"
					+ "JUNIT/Activity" + "\n" + "JUNIT/Service ");
			mDone = true;

		} else {
			System.out.println("Test Failed");
			mDone = false;
		}

	}
}
