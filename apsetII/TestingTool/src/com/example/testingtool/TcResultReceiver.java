package com.example.testingtool;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class TcResultReceiver extends ResultReceiver {
	private Receiver mReceiver;

	public TcResultReceiver(Handler handler) {
		super(handler);
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if (mReceiver != null) {
			mReceiver.onReceiveResult(resultCode, resultData);
		}
	}

	public interface Receiver {
		public void onReceiveResult(int resultCode, Bundle resultData);

	}

	public void setReceiver(Receiver rec) {
		mReceiver = rec;

	}
}
