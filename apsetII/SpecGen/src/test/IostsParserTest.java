package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import junit.framework.TestCase;
import fr.openium.iosts.Iosts;
import fr.openium.iosts.IostsLocation;
import fr.openium.iosts.IostsParser;
import fr.openium.iosts.IostsSpecification;
import fr.openium.iosts.IostsTransition;
import fr.openium.iosts.IostsVulnerabilities;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;

public class IostsParserTest extends TestCase {
	public static String DOTACTIVITIESFILEPATH;// =
												// "/Users/Stassia/Documents/workspace/SpecGen/dotFile/activities/";
	public static String DOTVULNERABILITIESPATH; // =
													// "/Users/Stassia/Documents/workspace/SpecGen/src/com/vulnerabilities/dot/activity/availability.dot";
	private String absolutePath;
	public Iosts mIostsSpec = new IostsSpecification();
	public Iosts mIostsVul = new IostsVulnerabilities();
	ManifestData mManifestData = null;
	private static ArrayList<IostsSpecification> mIostsSpecifications = new ArrayList<IostsSpecification>();
	private static final ArrayList<String> COMPONENTS = new ArrayList<String>() {
		{
			add("com.test4.activity1");
			add("com.test4.activity2");
			add("com.test4.activity3");
			add("com.test4.service1");
			add("com.test4.service2");
			add("com.test4.service3");
		}

	};

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File file = new File("");
		absolutePath = file.getAbsolutePath();
		DOTACTIVITIESFILEPATH = absolutePath + "/dotFile/activities/";
		DOTVULNERABILITIESPATH = absolutePath
				+ "/src/fr/openium/vulnerabilities/dot/activity/availability.dot";

		/** For Vulnerabilities */
		InputStream vulndotfile = new FileInputStream(DOTVULNERABILITIESPATH);
		mIostsVul = IostsParser.parseDotIosts(vulndotfile, mIostsVul);
		assertNotNull(mIostsVul);

		/** For Manifest Data */
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/cpreceiveractservice.xml");
		mManifestData = AndroidManifestParser.parse(manifestStream);
		assertNotNull(mManifestData);

		mIostsSpecifications = IostsParser
				.getIostsSpecSetFromData(mManifestData);
		assertNotNull(mIostsSpecifications);

	}

	public void testSpecification() throws FileNotFoundException {
		/** For Spec */
		InputStream dotfile = new FileInputStream(absolutePath
				+ "/dotFile/com.test4.activity2.dot");
		mIostsSpec = IostsParser.parseDotIosts(dotfile, mIostsSpec);
		assertNotNull(mIostsSpec);

		/** Number of Transition */
		// assertEquals(4, mIostsSpec.getTransitions().size());

		/** Name of the Iosts */
		// assertEquals("G", mIostsSpec.getName());

		/** Test of first Transition */
		assertEquals("l_0", mIostsSpec.getTransitions().get(0).getSource()
				.getName());

		assertEquals("l_1", mIostsSpec.getTransitions().get(0).getTarget()
				.getName());

		assertEquals("\"?intent(com.jeuxvideo.action.A1), [blabla], *\"",
				mIostsSpec.getTransitions().get(0).getLabel());

		// assertEquals("?intent(a,d,c,v,)", mIostsSpec.getTransitions().get(0)
		// .getActionString());
		// assertEquals("a.in(Ac)", mIostsSpec.getTransitions().get(0)
		// .getGuardString());

		/** Test of the last Transition */
		assertEquals("l_2", mIostsSpec.getTransitions().get(3).getSource()
				.getName());

		assertEquals("l_0", mIostsSpec.getTransitions().get(3).getTarget()
				.getName());

		assertEquals("\"!display (Activity A),[*],*\"", mIostsSpec
				.getTransitions().get(3).getLabel());

	}

	public void testVulnerabilities() throws FileNotFoundException {

		/** Number of Transition */
		assertEquals(9, mIostsVul.getTransitions().size());

		/** Name of the Iosts */
		// assertEquals("G", mIostsSpec.getName());

		/** Test of first Transition */
		assertEquals("l_0", mIostsVul.getTransitions().get(0).getSource()
				.getName());

		assertEquals("l_1", mIostsVul.getTransitions().get(0).getTarget()
				.getName());

		assertEquals(
				"\"!intent(action,category,type,uri,extra), [in(action,Ac+RV(String)) x in(category,C+RV(String)+INJ) x in(type,T+RV(String)) x in(uri,U+RV(String)) x in(extra,Ve)], *\"",
				mIostsVul.getTransitions().get(0).getLabel());

		/** Test of the last Transition */
		assertEquals("l_1", mIostsVul.getTransitions().get(8).getSource()
				.getName());

		assertEquals("inconclusive", mIostsVul.getTransitions().get(8)
				.getTarget().getName());

		assertEquals("\"?ActivityNotFoundException, [*], *\"", mIostsVul
				.getTransitions().get(8).getLabel());

		/** Test Action, guard and Assignment Value */
		// first Transition
		assertEquals("!intent(action,category,type,uri,extra)", mIostsVul
				.getTransitions().get(0).getActionString());
		assertEquals(
				"[in(action,Ac+RV(String)) x in(category,C+RV(String)+INJ) x in(type,T+RV(String)) x in(uri,U+RV(String)) x in(extra,Ve)]",
				mIostsVul.getTransitions().get(0).getGuardString());
		assertEquals(" *", mIostsVul.getTransitions().get(0)
				.getAssignmentsString());

		// last Transition
		assertEquals("?ActivityNotFoundException", mIostsVul.getTransitions()
				.get(8).getActionString());
		assertEquals("[*]", mIostsVul.getTransitions().get(8).getGuardString());
		assertEquals(" *", mIostsVul.getTransitions().get(8)
				.getAssignmentsString());

		// Test the obtained IostsTransition Object

		// Test of all IostsLocationsObject
		// expected 5
		assertEquals(5, mIostsVul.getIostsLocations().size());
		assertEquals("l_0", mIostsVul.getIostsLocations().get(0).getName());
		assertEquals("l_1", mIostsVul.getIostsLocations().get(1).getName());
		assertTrue(mIostsVul.getIostsLocations().contains(
				new IostsLocation("fail")));
		assertTrue(mIostsVul.getIostsLocations().contains(
				new IostsLocation("pass")));

	}

	public void testManifestDatatoIosts() {

		/**
		 * Check number of Iosts for activity and services:
		 * 
		 * In fact, we've just parsed 6 components (3 services an 3 activities)
		 * but in the manifestdata , we have also the provider and the receiver
		 * component but their iosts is equal to null, that why we have 8.
		 * 
		 */

		assertEquals(8, mIostsSpecifications.size());

		/**
		 * Check each component Iosts specification structure. we will take one
		 * activity and one service
		 * 
		 * */

		/*
		 * * the activity 3
		 */
		IostsSpecification activityIosts = mIostsSpecifications.get(2);
		assertEquals("com.test4.activity3", activityIosts.getName());
		assertEquals(IostsParser.ACTIVITY_TYPE, activityIosts.getType());
		/** this activity has 5 actions, then 10 transitions */
		assertEquals(10, activityIosts.getTransitions().size());
		/** check of initial location name */
		assertEquals("l_0", activityIosts.getInitLocation().getName());
		/** Check transitions */
		/** activity 3 has 5 actions belonging to the same set i.e Ac */
		for (IostsTransition tr : activityIosts.getTransitions()) {
			if (tr.getSource().getName()
					.equals(activityIosts.getInitLocation().getName())) {
				assertEquals("[a.in(Ac)]", tr.getGuardString());
				assertEquals("?intent(a,d,c,v,)", tr.getActionString());
				assertEquals("*", tr.getAssignmentsString());

			} else {
				assertEquals("!display (Activity A)", tr.getActionString());

			}
		}

		/** the service 2 */
		IostsSpecification serviceIosts = mIostsSpecifications.get(4);
		assertEquals("com.test4.service2", serviceIosts.getName());
		assertEquals(IostsParser.SERVICE_TYPE, serviceIosts.getType());
		/** this activity has 5 actions, then 10 transitions */
		assertEquals(2, serviceIosts.getTransitions().size());
		/** check of initial location name */
		assertEquals("l_0", serviceIosts.getInitLocation().getName());
		/** Check transitions */
		/** activity 3 has 5 actions belonging to the same set i.e Ac */
		for (IostsTransition tr : serviceIosts.getTransitions()) {
			if (tr.getSource().getName()
					.equals(serviceIosts.getInitLocation().getName())) {
				assertEquals("[*]", tr.getGuardString());
				assertEquals("?intent(a,d,c,v,)", tr.getActionString());
				assertEquals("*", tr.getAssignmentsString());

			} else {
				assertEquals("!running (Service S)", tr.getActionString());

			}
		}

	}

	public void testIostsToDotFile() throws FileNotFoundException {

		/** gnerate dotfile from iosts spec */
		IostsParser.generateSpecDotFile(mIostsSpecifications);

		/** For Spec */
		InputStream dotfile = new FileInputStream(absolutePath
				+ "/dotFile/activities/com.test4.activity2.dot");
		mIostsSpec = IostsParser.parseDotIosts(dotfile, mIostsSpec);
		assertNotNull(mIostsSpec);

		/** Number of Transition */
		// assertEquals(4, mIostsSpec.getTransitions().size());

		/** Name of the Iosts */
		// assertEquals("G", mIostsSpec.getName());

		/** Test of first Transition */
		assertEquals("l_0", mIostsSpec.getTransitions().get(0).getSource()
				.getName());

		assertEquals("l_1", mIostsSpec.getTransitions().get(0).getTarget()
				.getName());

		assertEquals("\"?intent(a,d,c,v,), [a.in(Ac)] ,*\"", mIostsSpec
				.getTransitions().get(0).getLabel());

		String action = mIostsSpec.getTransitions().get(0).getActionString();
		action = action.replaceAll("\\s", "");
		assertEquals("?intent(a,d,c,v,)", action);
		String guard = mIostsSpec.getTransitions().get(0).getGuardString();
		guard = guard.replaceAll("\\s", "");
		assertEquals("[a.in(Ac)]", guard);
		String assgn = mIostsSpec.getTransitions().get(0)
				.getAssignmentsString();
		assertEquals("*", assgn);

		/** Test of the last Transition */
		assertEquals("l_2", mIostsSpec.getTransitions().get(3).getSource()
				.getName());

		assertEquals("l_0", mIostsSpec.getTransitions().get(3).getTarget()
				.getName());
		String label = mIostsSpec.getTransitions().get(3).getLabel();
		label = label.replaceAll("\\s", "");
		String expected = "\"!display (Activity A),[*],*\"";
		expected = expected.replaceAll("\\s", "");
		assertEquals(expected, label);
		action = mIostsSpec.getTransitions().get(3).getActionString();
		action = action.replaceAll("\\s", "");
		expected = "!display (Activity A)";
		expected = expected.replaceAll("\\s", "");
		assertEquals(expected, action);
		guard = mIostsSpec.getTransitions().get(3).getGuardString();
		guard = guard.replaceAll("\\s", "");
		expected = "[*]";
		assertEquals(expected, guard);

	}
}
