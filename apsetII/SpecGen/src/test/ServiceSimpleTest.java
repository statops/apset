package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import fr.openium.components.ServiceComponent;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;

public class ServiceSimpleTest extends TestCase {

	private ManifestData mManifestTestApp;
	private static final String PACKAGE_NAME = "com.test2"; //$NON-NLS-1$
	private static final String SERVICE_NAME = PACKAGE_NAME + ".service1"; //$NON-NLS-1$
	private String absolutePath;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File file = new File("");
		absolutePath = file.getAbsolutePath();
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/servicesimple.xml");
		mManifestTestApp = AndroidManifestParser.parse(manifestStream);
		assertNotNull(mManifestTestApp);
	}

	public void testPackageName() {
		assertEquals(PACKAGE_NAME, mManifestTestApp.getPackage());
	}

	public void testServices() {
		assertEquals(1, mManifestTestApp.getServices().size());
		ServiceComponent service = mManifestTestApp.getServices().get(0);
		assertEquals(SERVICE_NAME, service.getName());

	}

}
