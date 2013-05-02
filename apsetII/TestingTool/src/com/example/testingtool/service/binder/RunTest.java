package com.example.testingtool.service.binder;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.testingtool.TestDetail;

public class RunTest extends IntentService {
	public RunTest() {
		super("RunTest");
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public String getTime() {
		SimpleDateFormat mDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return mDateFormat.format(new Date());
	}

	private static Process mExec;
	private final String TAG = "runtest";
	private String mStdOut = "null";

	public String launchTest(String testName, String testPackage) {
		String commandLine = "am instrument -e junitOutputDirectory /mnt/sdcard/testResults -e junitSingleFileName "
				+ testName
				+ ".xml"
				+ " -e junitSplitLevel none -e class "
				+ testName
				+ " -w "
				+ testPackage
				+ "/pl.polidea.instrumentation.PolideaInstrumentationTestRunner";
		return exec(commandLine);

	}

	private Intent mIntent;
	private ArrayList<String> mTestCaseName;

	@Override
	protected void onHandleIntent(Intent intent) {

		mIntent = intent;
		mTestCaseName = mIntent.getStringArrayListExtra("TestCase");
		String packag = mIntent.getStringExtra("package");
		for (String tc : mTestCaseName) {

			try {
				TestDetail td = new TestDetail(tc, launchTest(tc, packag),
						packag);
				addResult(td);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		exec("setprop apset 1111");
		ResultReceiver rsul = intent.getParcelableExtra("receiver");
		Bundle b = new Bundle();
		b.putSerializable("result", mTestDetail);
		rsul.send(1, b);
	}

	private static ArrayList<TestDetail> mTestDetail = new ArrayList<TestDetail>();

	private void addResult(TestDetail test) {
		mTestDetail.add(test);
	}

	public static ArrayList<TestDetail> getDetail() {
		return mTestDetail;

	}

	/**
	 * launch command line
	 * 
	 * @param the
	 *            command line to execute
	 * 
	 * @return the Std output
	 * */
	public String exec(String commandLine) {
		StringBuilder stdOut = new StringBuilder();
		StringBuilder stdErr = new StringBuilder();
		try {
			mExec = Runtime.getRuntime().exec(commandLine);
			InputStreamReader r = new InputStreamReader(mExec.getInputStream());
			final char buf[] = new char[1024];
			int read = 0;
			while ((read = r.read(buf)) != -1) {
				if (stdOut != null)
					stdOut.append(buf, 0, read);
			}
			try {
				mExec.waitFor();
				if (mExec.exitValue() != 255) {
				} else {
				}

			} catch (InterruptedException ne) {
				ne.printStackTrace();
			}
			Log.e(TAG, "stdOut:  " + stdOut.toString());
			mStdOut = stdOut.toString();
			r = new InputStreamReader(mExec.getErrorStream());
			read = 0;
			while ((read = r.read(buf)) != -1) {
				if (stdErr != null)
					stdErr.append(buf, 0, read);
			}

			Log.e(TAG, "stdErr:  " + stdErr.toString());
			stdErr.toString();
		} catch (Exception ex) {
			if (stdErr != null)
				stdErr.append("\n" + ex);
		}

		return mStdOut;
	}

}
