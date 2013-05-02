package test;

import java.io.File;

import junit.framework.TestCase;
import fr.openium.specification.specAdd.SpecAddComponent;
import fr.openium.specification.specAdd.SpecAddData;
import fr.openium.specification.specAdd.SpecAddParser;
import fr.openium.specification.specAdd.SpecAddData.SpecAddIntent;

public class SpeccAddParserTest extends TestCase {
	private SpecAddData mSpecAddData;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File f = new File("");
		String absPath = f.getAbsolutePath();
		File specAdd = new File(absPath + "/data/specAdd/specInfojeune.xml");
		mSpecAddData = SpecAddParser.parse(specAdd);
		assertNotNull(mSpecAddData);

	}

	public void testComponentName() {

		/**
		 * Package Name
		 */
		assertEquals("fr.crauvergne.eij", mSpecAddData.getPackageName());

		// there are 2 components
		assertEquals(2, mSpecAddData.getComponents().size());

		/**
		 * the activity Name
		 * */

		assertEquals("fr.crauvergne.eij.ActivityDashboard", mSpecAddData
				.getComponents().get(0).getName());
		/**
		 * the service Name
		 */
		assertEquals("fr.crauvergne.eij.ServiceEij", mSpecAddData
				.getComponents().get(1).getName());

	}

	public void testSpecAddIntent() {

		/**
		 * the intents sent of activity
		 * */
		// first Intent
		// the targetName
		SpecAddIntent temp = (SpecAddIntent) mSpecAddData.getIntents().get(0);
		assertEquals("fr.crauvergne.eij.ActivityList", temp
				.getTargetComponent().getName());
		assertEquals("fr.crauvergne.eij.ActivityDashboard", temp
				.getComponentSource().getName());

		// exchanged extra (3)
		assertEquals(3, temp.getExtra().size());
		assertEquals("@string/intent_extra_command_data_type", temp.getExtra()
				.get("@string/intent_extra_command_data_type").getKey());
		assertEquals("string/*",
				temp.getExtra().get("@string/intent_extra_command_data_type")
						.getType());
		assertEquals("string/*",
				temp.getExtra().get("@string/intent_extra_command_data_uri")
						.getType());
		assertEquals("image",
				temp.getExtra().get("@string/intent_extra_activity_title")
						.getType());

		// th second Intent
		temp = (SpecAddIntent) mSpecAddData.getIntents().get(1);
		assertEquals("fr.crauvergne.eij.ActivityOnSortCeSoir", temp
				.getTargetComponent().getName());
		assertEquals("fr.crauvergne.eij.ActivityDashboard", temp
				.getComponentSource().getName());

		assertEquals("string/*",
				temp.getExtra().get("@string/intent_extra_activity_title")
						.getType());

	}

	public void testReceivedExtra() {

		/**
		 * the received extra of service
		 * */
		SpecAddComponent temp = (SpecAddComponent) mSpecAddData.getComponents()
				.get(1);
		assertEquals(1, temp.getReceivedExtra().size());
		assertEquals("@string/intent_extra_command_id", temp.getReceivedExtra()
				.get("@string/intent_extra_command_id").getKey());
		assertEquals("string/*",
				temp.getReceivedExtra().get("@string/intent_extra_command_id")
						.getType());

	}

	public void testProviderAccess() {

		/**
		 * provider access of the activity
		 * */

		assertEquals(true, mSpecAddData.getComponents().get(0).getAccessToCp());
		assertEquals("fr.crauvergne.eij.database.ContentProviderEIJ",
				mSpecAddData.getComponents().get(0).getContentProviders()
						.get(0).getName());
		assertEquals("fr.crauvergne.eij.contentprovidereij", mSpecAddData
				.getComponents().get(0).getContentProviders().get(0)
				.getAuthorities().get(0));

		/**
		 * provider access of the service
		 * */

		assertEquals(true, mSpecAddData.getComponents().get(1).getAccessToCp());
		assertEquals("fr.crauvergne.eij.database.ContentProviderEIJ",
				mSpecAddData.getComponents().get(1).getContentProviders()
						.get(0).getName());
		assertEquals("fr.crauvergne.eij.contentprovidereij", mSpecAddData
				.getComponents().get(1).getContentProviders().get(0)
				.getAuthorities().get(0));

	}
}
