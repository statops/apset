package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import fr.openium.components.ActivityComponent;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;

public class ActivitysimpleTest extends TestCase {

	private ManifestData mManifestTestApp;
	private static final String PACKAGE_NAME = "com.test1"; //$NON-NLS-1$
	private static final String ACTIVITY_NAME = PACKAGE_NAME + ".activity1"; //$NON-NLS-1$

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File file = new File("");
		String absolutePath = file.getAbsolutePath();
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/activitysimple.xml");
		mManifestTestApp = AndroidManifestParser.parse(manifestStream);
		assertNotNull(mManifestTestApp);
	}

	public void testPackageName() {
		assertEquals(PACKAGE_NAME, mManifestTestApp.getPackage());
	}

	public void testActivities() {
		assertEquals(1, mManifestTestApp.getActivities().size());
		ActivityComponent activity = mManifestTestApp.getActivities().get(0);
		assertEquals(ACTIVITY_NAME, activity.getName());

	}
}
