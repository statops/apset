package com.example.testingtool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.testingtool.TcResultReceiver.Receiver;
import com.example.testingtool.service.binder.RunTest;

public class ServiceHelper extends Observable implements Receiver {
	private ArrayList<String> mTestName = new ArrayList<String>();
	private ArrayList<TestDetail> mTestDetail = new ArrayList<TestDetail>();
	private Context mContext;
	boolean mBounded;
	private static ServiceHelper mInstance = null;
	private InputStream mTc;

	private ServiceHelper(Context context) {
		mContext = context;
		mTcResultReceiver = new TcResultReceiver(new Handler());
		mTcResultReceiver.setReceiver(this);
	}

	public static ServiceHelper getInstance(Context ctx) {
		if (null == mInstance) {
			synchronized (ServiceHelper.class) {
				if (null == mInstance) {
					mInstance = new ServiceHelper(ctx);
				}
			}
		}
		return mInstance;

	}

	private ArrayList<String> getTestcases() throws FileNotFoundException {
		mTc = mContext.getResources().openRawResource(R.raw.testname);
		ArrayList<String> testcases = new ArrayList<String>();
		Scanner scan = new Scanner(mTc);
		if (scan.hasNext())
			mTestPackage = scan.nextLine();
		while ((scan.hasNext())) {
			testcases.add(scan.nextLine());
		}
		return testcases;
	}

	public static String mTestPackage;
	public TcResultReceiver mTcResultReceiver;

	public void launchTest() throws FileNotFoundException,
			ParserConfigurationException, TransformerException {
		mTestDetail.clear();
		mTestName = getTestcases();
		// Lancer Intent Service
		Intent service = new Intent(mContext, RunTest.class);
		service.putExtra("TestCase", mTestName);
		service.putExtra("package", mTestPackage);
		service.putExtra("receiver", mTcResultReceiver);
		mContext.startService(service);

	}

	// Recupération des resultats du service
	private void deleteAllFiles() {
		String commandLine = "rm " + TESTRESULT + "*";
		Process mExec;
		try {
			mExec = Runtime.getRuntime().exec(commandLine);
			try {
				mExec.waitFor();
				if (mExec.exitValue() != 255) {
				} else {
				}
			} catch (InterruptedException ne) {
			}

		} catch (Exception ex) {

		}

	}

	private final static String TESTRESULT = "/mnt/sdcard/testResults/";

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultCode == 1) {
			ArrayList<TestDetail> serializable = (ArrayList<TestDetail>) resultData
					.getSerializable("result");
			mTestDetail = serializable;
			/**
			 * Ecrire dans un fichier que c'est fini
			 * 
			 */
			ok();
			setChanged();
			notifyObservers(mTestDetail);
		}

	}

	private void ok() {

		File JUnitOutput = new File(TESTRESULT, "ok");
		if (!JUnitOutput.exists()) {
			try {
				JUnitOutput.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				Log.i("[Creating ok file]", "fail");
			}
		}

		FileWriter write;
		try {
			write = new FileWriter(JUnitOutput.getAbsoluteFile());
			BufferedWriter out = new BufferedWriter(write);
			out.write("1");
			out.close();
			Log.i("[Writting Ok file]", "OK");
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.i("[Writting ok file]", "fail");

		}

	}
}
