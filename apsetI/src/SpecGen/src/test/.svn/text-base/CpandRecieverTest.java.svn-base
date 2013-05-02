package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import fr.openium.components.ContentProviderComponent;
import fr.openium.components.ReceiverComponent;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;

public class CpandRecieverTest extends TestCase {

	private ManifestData mManifestTestApp;
	private static final String PACKAGE_NAME = "com.test4";
	private static final String CONTENTPROVIDER_NAME = PACKAGE_NAME
			+ ".testProvider";
	private static final String BROADCASTRECEIVER_NAME = PACKAGE_NAME
			+ ".testReceiver";
	private static final String CONTENTPROVIDER_AUTHORITIES = "com.test4.CPTest";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File file = new File("");
		String absolutePath = file.getAbsolutePath();
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/cpreceiveractservice.xml");
		mManifestTestApp = AndroidManifestParser.parse(manifestStream);
		assertNotNull(mManifestTestApp);
	}

	public void testNames() {
		assertEquals(1, mManifestTestApp.getProviders().size());
		assertEquals(1, mManifestTestApp.getReceivers().size());

		ContentProviderComponent cp = mManifestTestApp.getProviders().get(0);
		ReceiverComponent rc = mManifestTestApp.getReceivers().get(0);

		assertEquals(CONTENTPROVIDER_NAME, cp.getName());
		assertEquals(BROADCASTRECEIVER_NAME, rc.getName());

	}

	public void testCPAuthorities() {

		ContentProviderComponent cp = mManifestTestApp.getProviders().get(0);

		assertEquals(CONTENTPROVIDER_AUTHORITIES, cp.getAuthorities().get(0));

	}
}
