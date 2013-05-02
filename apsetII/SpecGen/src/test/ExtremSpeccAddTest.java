package test;

import java.io.File;

import junit.framework.TestCase;
import fr.openium.specification.specAdd.SpecAddData;
import fr.openium.specification.specAdd.SpecAddParser;
import fr.openium.specification.specAdd.SpecAddData.SpecAddIntent;

public class ExtremSpeccAddTest extends TestCase {
	private SpecAddData mSpecAddData;
	private String absPath;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File f = new File("");
		absPath = f.getAbsolutePath();

	}

	public void testWrongTag() {

		File specAdd = new File(absPath + "/data/specAdd/wrongTag.xml");
		mSpecAddData = SpecAddParser.parse(specAdd);
		// there is wrong tag
		// must @return null
		assertNull(mSpecAddData);

	}

	public void testUseLessTag() {

		File specAdd = new File(absPath + "/data/specAdd/uselessTag.xml");
		mSpecAddData = SpecAddParser.parse(specAdd);
		// there is unknownTag
		// must not @return. Just ignore it
		assertNotNull(mSpecAddData);

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

	public void testWrongMainTag() {

		File specAdd = new File(absPath + "/data/specAdd/wrongTag.xml");
		mSpecAddData = SpecAddParser.parse(specAdd);
		// main tag is not "Spec"
		// must @return null
		assertNull(mSpecAddData);

	}

	public void testNotEndedTag() {

		File specAdd = new File(absPath + "/data/specAdd/malformed.xml");
		mSpecAddData = SpecAddParser.parse(specAdd);
		// main tag is not "Spec"
		// must @return null
		assertNull(mSpecAddData);

	}

}
