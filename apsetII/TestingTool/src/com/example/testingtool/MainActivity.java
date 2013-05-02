package com.example.testingtool;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements Observer,
		OnItemClickListener {

	private Button button;
	private Button buttonStop;
	TextView tex;
	private ServiceHelper mModel;
	private ArrayList<TestDetail> mResult = new ArrayList<TestDetail>();
	private ListView mListView;
	private TestingToolAdapter mAdapter;
	private static int mNumberFailedTest = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.listview);
		mFail = (TextView) findViewById(R.id.fail);
		mPAss = (TextView) findViewById(R.id.pass);
		button = (Button) findViewById(R.id.launchtest);
		buttonStop = (Button) findViewById(R.id.stoptest);
		mModel = ServiceHelper.getInstance(MainActivity.this);
		mModel.addObserver(this);
		mAdapter = new TestingToolAdapter(this, mResult);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);

	}

	@Override
	protected void onStart() {
		super.onStart();

		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					/**
					 * traceview
					 * 
					 * */

					Debug.startMethodTracing("TestingToolTrace");
					mModel.launchTest();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (TransformerException e) {
					e.printStackTrace();
				}
			}
		});

		buttonStop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, DetailActivity.class);
				startActivity(i);
			}
		});

		if (getIntent().getAction().equalsIgnoreCase("intent.action.test")) {
			// lancer les test
			try {
				mModel.launchTest();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	};

	@Override
	protected void onStop() {
		super.onStop();
		Debug.stopMethodTracing();
	};

	public void showTestResult() {

	}

	/**
	 * Affichage des erreurs
	 * 
	 * @param t
	 */
	public void showError(Throwable t) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Exception!").setMessage(t.toString())
				.setPositiveButton("OK", null).show();
	}

	private TextView mFail;
	private TextView mPAss;

	@Override
	public void update(Observable arg0, Object arg1) {
		mResult.clear();
		mResult.addAll((ArrayList<TestDetail>) arg1);
		/** reset */
		mNumberFailedTest = 0;
		/*
		 * Refresh *
		 */
		for (TestDetail result : mResult) {
			if (result.getVerdict().equals(TestDetail.FAIL)) {
				mNumberFailedTest++;
			}
		}
		mFail.setText("failures: " + mNumberFailedTest);
		int pass = (mResult.size() - mNumberFailedTest);
		mPAss.setText("Pass:  " + pass);
		mAdapter.updateResults(mResult);

	}

	public static class TestingToolAdapter extends BaseAdapter {

		static class ViewHolder {
			TextView mTextView;
			ImageView mImageView;
		}

		private ArrayList<TestDetail> mResultDetail;
		private LayoutInflater mLayoutInflater;

		public TestingToolAdapter(Context ctx, ArrayList<TestDetail> mapp) {
			mResultDetail = mapp;
			mLayoutInflater = LayoutInflater.from(ctx);

		}

		public void updateResults(ArrayList<TestDetail> mapp) {
			mResultDetail = mapp;
			// Triggers the list update
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mResultDetail.size();
		}

		@Override
		public String getItem(int position) {
			return mResultDetail.get(position).getName();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				// testResult
				convertView = mLayoutInflater.inflate(R.layout.testresult,
						parent, false);
				holder = new ViewHolder();
				holder.mTextView = (TextView) convertView
						.findViewById(R.id.result);
				holder.mImageView = (ImageView) convertView
						.findViewById(R.id.verdictImage);
				convertView.setTag(holder);
			} else
				holder = (ViewHolder) convertView.getTag();
			holder.mTextView.setText(getItem(position));
			holder.mImageView.setImageResource(getItemImage(position));
			return convertView;
		}

		private int getItemImage(int position) {
			if (mResultDetail.get(position).isFail()) {
				mNumberFailedTest++;
				return R.drawable.fail;

			} else if (mResultDetail.get(position).isPass())
				return R.drawable.pass;
			else
				return R.drawable.inconclusive;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i = new Intent(this, DetailActivity.class);
		i.putExtra("detail", mResult.get(arg2).getDetail());
		startActivity(i);
	}

}
