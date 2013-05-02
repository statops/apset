package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import junit.framework.TestCase;
import fr.openium.components.ActivityComponent;
import fr.openium.components.ServiceComponent;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;

public class MultipleActService extends TestCase {

	private ManifestData mManifestTestApp;
	private static final String PACKAGE_NAME = "com.test4"; //$NON-NLS-1$
	private static final ArrayList<String> SERVCIE_NAME = new ArrayList() {
		{
			add(PACKAGE_NAME + ".service1");
			add(PACKAGE_NAME + ".service2");
			add(PACKAGE_NAME + ".service3");
		}
	};
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
				+ "/data/multipleactservice.xml");
		mManifestTestApp = AndroidManifestParser.parse(manifestStream);
		assertNotNull(mManifestTestApp);
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

	public void testServicesName() {
		assertEquals(3, mManifestTestApp.getServices().size());

		ServiceComponent service1 = mManifestTestApp.getServices().get(0);
		ServiceComponent service2 = mManifestTestApp.getServices().get(1);
		ServiceComponent service3 = mManifestTestApp.getServices().get(2);
		assertEquals(SERVCIE_NAME.get(0), service1.getName());
		assertEquals(SERVCIE_NAME.get(1), service2.getName());
		assertEquals(SERVCIE_NAME.get(2), service3.getName());

	}

	public void testServicesIntent() {
		assertEquals(3, mManifestTestApp.getServices().size());

		ServiceComponent service1 = mManifestTestApp.getServices().get(0);
		ServiceComponent service2 = mManifestTestApp.getServices().get(1);
		ServiceComponent service3 = mManifestTestApp.getServices().get(2);
		/**
		 * test the number of Intent
		 */
		assertEquals(0, service1.getIntent().size());
		assertEquals(1, service2.getIntent().size());
		assertEquals(3, service3.getIntent().size());

		/**
		 * Test the name of the action, category and data of each intent
		 */

		/**
		 * service 1
		 */
		try {
			assertEquals("tests", service1.getIntent().get(0).getAction());
		} catch (IndexOutOfBoundsException i) {
			assertTrue(true);
		}
		/**
		 * service 2
		 * */
		/** first Intent */
		assertEquals("action.test2", service2.getIntent().get(0).getAction());
		assertTrue(service2.getIntent().get(0).getCategory().isEmpty());
		assertTrue(service2.getIntent().get(0).getData().isEmpty());

		/**
		 * service 3
		 */
		/**
		 * first Intent
		 */
		assertEquals("action.test3", service3.getIntent().get(0).getAction());
		assertEquals("android.intent.category.DEFAULT", service3.getIntent()
				.get(0).getCategory().get(0));
		assertEquals("*/*", service3.getIntent().get(0).getData().get(0)
				.getMimeType());

		/**
		 * Second and Third Intent
		 */
		assertEquals("action.test4", service3.getIntent().get(1).getAction());
		assertEquals("action.test5", service3.getIntent().get(2).getAction());

		assertEquals("android.intent.category.DEFAULT", service3.getIntent()
				.get(1).getCategory().get(0));
		assertEquals("android.intent.category.ALTERNATIVE", service3
				.getIntent().get(1).getCategory().get(1));

		assertEquals("android.intent.category.DEFAULT", service3.getIntent()
				.get(2).getCategory().get(0));
		assertEquals("android.intent.category.ALTERNATIVE", service3
				.getIntent().get(2).getCategory().get(1));

		assertEquals("image/*", service3.getIntent().get(1).getData().get(0)
				.getMimeType());
		assertEquals("image/*", service3.getIntent().get(2).getData().get(0)
				.getMimeType());

	}
}
