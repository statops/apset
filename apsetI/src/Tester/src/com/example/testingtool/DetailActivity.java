package com.example.testingtool;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testingtool.service.binder.RunTest;

public class DetailActivity extends Activity implements Observer {
	private TextView mTextView;
	private ServiceHelper mServiceHelper;
	private ArrayList<TestDetail> mResult = new ArrayList<TestDetail>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_view);
		mTextView = (TextView) findViewById(R.id.detail);
		mServiceHelper = ServiceHelper.getInstance(DetailActivity.this);
		mServiceHelper.addObserver(this);
		try {
			mServiceHelper.launchTest();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		boolean b = getIntent().getBooleanExtra("RESULT", false);
		if (b) {
			mResult.clear();
			mResult = RunTest.getDetail();
			/** reset */
			mNumberFailedTest = 0;
			for (TestDetail result : mResult) {
				if (result.getVerdict().equals(TestDetail.FAIL)) {
					mNumberFailedTest++;
				}
			}
			int pass = (mResult.size() - mNumberFailedTest);
			mTextView.setText("failures: " + mNumberFailedTest + "  Pass:  "
					+ pass);

		} else {
			mTextView.setText("waiting result");

		}

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	/*
	 * private String loadDetail(String stringExtra) { return stringExtra; }
	 */

	private static int mNumberFailedTest = 0;

	@Override
	public void update(Observable observable, Object data) {

		mResult.clear();
		mResult.addAll((ArrayList<TestDetail>) data);
		/** reset */
		mNumberFailedTest = 0;
		for (TestDetail result : mResult) {
			if (result.getVerdict().equals(TestDetail.FAIL)) {
				mNumberFailedTest++;
			}
		}
		int pass = (mResult.size() - mNumberFailedTest);
		mTextView
				.setText("failures: " + mNumberFailedTest + "  Pass:  " + pass);

	}

}
