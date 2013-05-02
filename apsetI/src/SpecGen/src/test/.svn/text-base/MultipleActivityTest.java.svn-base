package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import junit.framework.TestCase;
import fr.openium.components.ActivityComponent;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;

public class MultipleActivityTest extends TestCase {

	private ManifestData mManifestTestApp;
	private static final String PACKAGE_NAME = "com.test3"; //$NON-NLS-1$
	private static final ArrayList<String> ACTIVITY_NAME = new ArrayList() {
		{
			add(PACKAGE_NAME + ".activity1");
			add(PACKAGE_NAME + ".activity2");
			add(PACKAGE_NAME + ".activity3");
		}
	};
	private String absolutePath;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File file = new File("");
		absolutePath = file.getAbsolutePath();
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/multipleactivity.xml");
		mManifestTestApp = AndroidManifestParser.parse(manifestStream);
		assertNotNull(mManifestTestApp);
	}

	public void testPackageName() {
		assertEquals(PACKAGE_NAME, mManifestTestApp.getPackage());
	}

	public void testActivitiesName() {
		assertEquals(3, mManifestTestApp.getActivities().size());

		ActivityComponent activity1 = mManifestTestApp.getActivities().get(0);
		ActivityComponent activity2 = mManifestTestApp.getActivities().get(1);
		ActivityComponent activity3 = mManifestTestApp.getActivities().get(2);
		assertEquals(ACTIVITY_NAME.get(0), activity1.getName());
		assertEquals(ACTIVITY_NAME.get(1), activity2.getName());
		assertEquals(ACTIVITY_NAME.get(2), activity3.getName());

	}

	public void testActivitiesIntent() {
		assertEquals(3, mManifestTestApp.getActivities().size());

		ActivityComponent activity1 = mManifestTestApp.getActivities().get(0);
		ActivityComponent activity2 = mManifestTestApp.getActivities().get(1);
		ActivityComponent activity3 = mManifestTestApp.getActivities().get(2);
		/**
		 * test the number of Intent
		 */
		assertEquals(0, activity1.getIntent().size());
		assertEquals(2, activity2.getIntent().size());
		assertEquals(5, activity3.getIntent().size());

		/**
		 * Test the name of the action, category and data of each intent
		 */

		/**
		 * activity 1
		 */
		try {
			assertEquals("tests", activity1.getIntent().get(0).getAction());
		} catch (IndexOutOfBoundsException i) {
			assertTrue(true);
		}
		/**
		 * activity 2
		 * */
		/** first Intent */
		assertEquals("com.jeuxvideo.action.A1", activity2.getIntent().get(0)
				.getAction());
		assertEquals("android.intent.category.C1", activity2.getIntent().get(0)
				.getCategory().get(0));
		assertEquals("H1", activity2.getIntent().get(0).getData().get(0)
				.getHost());
		assertEquals("S1", activity2.getIntent().get(0).getData().get(0)
				.getScheme());

		/** second Intent */
		assertEquals("com.jeuxvideo.action.A2", activity2.getIntent().get(1)
				.getAction());
		assertTrue(activity2.getIntent().get(1).getCategory().isEmpty());
		assertEquals("H2", activity2.getIntent().get(1).getData().get(0)
				.getHost());
		assertEquals("S2", activity2.getIntent().get(1).getData().get(0)
				.getScheme());

		/**
		 * activity 3
		 */
		/**
		 * first Intent
		 */
		assertEquals("com.jeuxvideo.action.A31", activity3.getIntent().get(0)
				.getAction());
		assertEquals("android.intent.category.C31", activity3.getIntent()
				.get(0).getCategory().get(0));
		assertEquals("android.intent.category.C32", activity3.getIntent()
				.get(0).getCategory().get(1));
		assertEquals("H31", activity3.getIntent().get(0).getData().get(0)
				.getHost());
		assertEquals("S31", activity3.getIntent().get(0).getData().get(0)
				.getScheme());
		assertEquals("H32", activity3.getIntent().get(0).getData().get(1)
				.getHost());
		assertEquals("S32", activity3.getIntent().get(0).getData().get(1)
				.getScheme());

		/**
		 * Second and Third Intent
		 */
		assertEquals("com.jeuxvideo.action.A32", activity3.getIntent().get(1)
				.getAction());
		assertEquals("com.jeuxvideo.action.A33", activity3.getIntent().get(2)
				.getAction());

		/**
		 * Fourth and fith Intent
		 */
		assertEquals("com.jeuxvideo.action.A34", activity3.getIntent().get(3)
				.getAction());
		assertEquals("android.intent.category.C34", activity3.getIntent()
				.get(3).getCategory().get(0));
		assertEquals("android.intent.category.C35", activity3.getIntent()
				.get(3).getCategory().get(1));
		assertEquals("H34", activity3.getIntent().get(3).getData().get(0)
				.getHost());
		assertEquals("S34", activity3.getIntent().get(3).getData().get(0)
				.getScheme());

		assertEquals("com.jeuxvideo.action.A35", activity3.getIntent().get(4)
				.getAction());
		assertEquals("android.intent.category.C34", activity3.getIntent()
				.get(4).getCategory().get(0));
		assertEquals("android.intent.category.C35", activity3.getIntent()
				.get(4).getCategory().get(1));
		assertEquals("H34", activity3.getIntent().get(3).getData().get(0)
				.getHost());
		assertEquals("S34", activity3.getIntent().get(3).getData().get(0)
				.getScheme());

	}
}