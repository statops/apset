package fr.openium.automaticOperation;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunManager {

	public void install() {

	}

	/**
	 * 
	 * @param adb
	 *            path
	 * @param deviceFile
	 *            path
	 * @param localDirectory
	 *            path
	 * @return true if pull succed
	 * @return false if pull failed
	 */
	private boolean mError = false;

	public boolean pull(String adb, String deviceFile, String localDirectory) {
		String option = "pull";
		StringBuilder Bline = new StringBuilder();
		String line;
		try {
			ProcessBuilder proc = new ProcessBuilder(adb, option, deviceFile,
					localDirectory);
			Process p = proc.start();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = br2.readLine()) != null) {
				Bline.append(line);
				System.out.println(line);
			}
			return true;
		} catch (Exception e) {
			System.err.println("Error");
			mError = true;
			return false;
		}

	}

	public boolean getError() {
		return mError;
	}

	/**
	 * android push operation
	 * 
	 * @param adb
	 *            path
	 * @param localfile
	 *            path
	 * @param devicefile
	 *            path
	 */
	public void push(String adb, String localfile, String devicefile) {
		String option = "push";
		String line;
		try {
			ProcessBuilder proc = new ProcessBuilder(adb, option, localfile,
					devicefile);
			Process p = proc.start();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = br2.readLine()) != null)
				System.out.println(line);
		} catch (Exception e) {
			System.err.println("Error");
		}

	}

	public boolean install(String adb, String apkFile) {
		String option = "install";
		StringBuilder Bline = new StringBuilder();
		String line;
		try {
			ProcessBuilder proc = new ProcessBuilder(adb, option, apkFile);
			Process p = proc.start();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = br2.readLine()) != null) {
				Bline.append(line);
				System.err.println(line);
			}
		} catch (Exception e) {
			System.err.println("Error");
			e.printStackTrace();
		}
		if (Bline.toString().contains("Success"))
			return true;
		else
			return false;
		// return Bline.toString();
	}

	public boolean uninstall(String adb, String projectpackage) {
		String option = "uninstall";
		StringBuilder Bline = new StringBuilder();
		String line;
		try {
			ProcessBuilder proc = new ProcessBuilder(adb, option,
					projectpackage);
			Process p = proc.start();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			while ((line = br2.readLine()) != null) {
				Bline.append(line);
				System.err.println(line);
			}
		} catch (Exception e) {
			System.err.println("Error");
			System.err.println("Error");
			e.printStackTrace();
		}

		if (Bline.toString().contains("Success"))
			return true;
		else
			return false;
	}

	public boolean launch(String adb, String action, String component) {
		String option = "shell am start -a " + action + " -n";
		StringBuilder Bline = new StringBuilder();
		String line;
		try {
			ProcessBuilder proc = new ProcessBuilder(adb, option, component);
			Process p = proc.start();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			while ((line = br2.readLine()) != null) {
				Bline.append(line);
				System.err.println(line);
			}
		} catch (Exception e) {
			System.err.println("Error");
			e.printStackTrace();
		}

		if (Bline.toString().contains("Success"))
			return true;
		else
			return false;
	}

	public void shell(String adb) {
		String option = "shell";
		StringBuilder Bline = new StringBuilder();
		String line;
		try {
			ProcessBuilder proc = new ProcessBuilder(adb, option);
			Process p = proc.start();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = br2.readLine()) != null) {
				Bline.append(line);
				System.out.println(line);
			}
		} catch (Exception e) {
			System.err.println("Error");
			mError = true;
		}

	}

}
