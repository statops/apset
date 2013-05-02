package fr.openium.Junit.Templates;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityTestCase;
import android.view.View;

public class activityTest extends ActivityTestCase {
	private Instrumentation mInstrumentation;
	private Activity mActivity;
	private Intent mIntent;
	private ArrayList<View> mViews;

	public activityTest() {

	}

	protected void setUp() {
		// mInstrumentation = Context.getInstrumentation();
		mIntent = new Intent();
		mViews = new ArrayList<View>();
	}
}