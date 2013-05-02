package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import junit.framework.TestCase;
import fr.openium.components.ServiceComponent;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;

public class MultipleServiceTest extends TestCase {

	private ManifestData mManifestTestApp;
	private static final String PACKAGE_NAME = "com.test4"; //$NON-NLS-1$
	private static final ArrayList<String> SERVCIE_NAME = new ArrayList() {
		{
			add(PACKAGE_NAME + ".service1");
			add(PACKAGE_NAME + ".service2");
			add(PACKAGE_NAME + ".service3");
		}
	};
	private String absolutePath;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File file = new File("");
		absolutePath = file.getAbsolutePath();
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/multipleservice.xml");
		mManifestTestApp = AndroidManifestParser.parse(manifestStream);
		assertNotNull(mManifestTestApp);
	}

	public void testPackageName() {
		assertEquals(PACKAGE_NAME, mManifestTestApp.getPackage());
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