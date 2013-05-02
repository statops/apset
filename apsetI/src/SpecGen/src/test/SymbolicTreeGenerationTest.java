package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import junit.framework.TestCase;
import fr.openium.iosts.Iosts;
import fr.openium.iosts.IostsAbstractTestCase;
import fr.openium.iosts.IostsParser;
import fr.openium.iosts.IostsSpecification;
import fr.openium.iosts.IostsVulnerabilities;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;
import fr.openium.testcases.SymbolicTreeGeneration;

public class SymbolicTreeGenerationTest extends TestCase {
	private static ArrayList<IostsSpecification> mIostsSpecifications = new ArrayList<IostsSpecification>();
	private ManifestData mManifestData = null;
	public Iosts mIostsSpec = new IostsSpecification();
	public Iosts mIostsVul = new IostsVulnerabilities();
	public Iosts mIostsAbsTC;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File file = new File("");
		String absolutePath = file.getAbsolutePath();
		String manifestPath = absolutePath
				+ "/data/SymbolicTreeGenerationTest/cpreceiveractservice.xml";

		/** For Manifest Data */
		InputStream manifestStream = new FileInputStream(manifestPath);
		mManifestData = AndroidManifestParser.parse(manifestStream);
		assertNotNull(mManifestData);

		/** Get the partial specification */
		mIostsSpecifications = IostsParser
				.getIostsSpecSetFromData(mManifestData);
		assertNotNull(mIostsSpecifications);

		/** Vulnerabilities */
		String vulnPath = absolutePath
				+ "/src/fr/openium/vulnerabilities/dot/activity/availability.dot";
		InputStream vulndotfile = new FileInputStream(vulnPath);
		mIostsVul = IostsParser.parseDotIosts(vulndotfile, mIostsVul);
		assertNotNull(mIostsVul);

	}

	public void testPartialSpecificationRefinement() {

		String expectedLabelValue = null;
		String actualLabelValue = null;

		/**
		 * See result for the second component [activity 2](that contains 2 (=4
		 * transitions) intents corresponding to the same symbolic descriptions)
		 */
		/** before */
		mIostsSpec = mIostsSpecifications.get(1);
		assertEquals(4, mIostsSpec.getTransitions().size());
		expectedLabelValue = mIostsSpec.getTransitions().get(0).getLabel();
		/** after */
		SymbolicTreeGeneration.refine((IostsSpecification) mIostsSpec);
		assertEquals(2, mIostsSpec.getTransitions().size());
		actualLabelValue = mIostsSpec.getTransitions().get(0).getLabel();
		assertEquals(expectedLabelValue, actualLabelValue);

		/**
		 * See result for the third component [activity 3] (that contains 5 (=10
		 * transitions) intents corresponding to the same symbolic descriptions)
		 */

		/** before */
		mIostsSpec = mIostsSpecifications.get(2);
		assertEquals(10, mIostsSpec.getTransitions().size());
		expectedLabelValue = mIostsSpec.getTransitions().get(0).getLabel();
		/** after */
		SymbolicTreeGeneration.refine((IostsSpecification) mIostsSpec);
		assertEquals(2, mIostsSpec.getTransitions().size());
		actualLabelValue = mIostsSpec.getTransitions().get(0).getLabel();
		assertEquals(expectedLabelValue, actualLabelValue);

		/**
		 * See result for the fourth component [activity 4] (that contains 2
		 * intents corresponding to different symbolic descriptions)
		 */

		/** before */
		mIostsSpec = mIostsSpecifications.get(3);
		assertEquals(4, mIostsSpec.getTransitions().size());
		int i = mIostsSpec.getTransitions().size() - 1;
		expectedLabelValue = mIostsSpec.getTransitions().get(i).getLabel();
		/** after */
		/** number of transitions must not be changed */
		SymbolicTreeGeneration.refine((IostsSpecification) mIostsSpec);
		assertEquals(4, mIostsSpec.getTransitions().size());
		actualLabelValue = mIostsSpec.getTransitions().get(i).getLabel();
		assertEquals(expectedLabelValue, actualLabelValue);

	}

	public void testVulnAndSpecCombination() {
		/** Choice of one Specification to be combined withe teh vulnerabilities */

		/** Simple: Activity2 */
		mIostsSpec = mIostsSpecifications.get(1);
		mIostsAbsTC = new IostsAbstractTestCase(
				((IostsSpecification) mIostsSpec).getName(),
				((IostsSpecification) mIostsSpec).getType());
		SymbolicTreeGeneration tC = new SymbolicTreeGeneration(
				(IostsSpecification) mIostsSpec,
				(IostsVulnerabilities) mIostsVul);
		mIostsAbsTC = (IostsAbstractTestCase) tC.generateAbstractTestCases();
		assertEquals(mIostsSpec.getName(), mIostsAbsTC.getName());

		/** Do not have initlocation but has combined initLocation */
		assertEquals(null, mIostsAbsTC.getInitLocation());
		assertEquals(null, mIostsAbsTC.getInitLocation());

		/** first elt of combine init location correspond to Spec init location */
		assertEquals(
				mIostsSpec.getInitLocation(),
				((IostsAbstractTestCase) mIostsAbsTC).getInitCombinedLocation()[0]);
		/** second elt of combine init location correspond to Spec init location */
		assertEquals(
				mIostsVul.getInitLocation(),
				((IostsAbstractTestCase) mIostsAbsTC).getInitCombinedLocation()[1]);

		assertEquals(2, mIostsSpec.getTransitions().size());
		assertEquals(9, mIostsVul.getTransitions().size());

		/** first level : [l_0,l_0]-> [l_1,l_1] */
		assertEquals(mIostsSpec.getTransitions().get(0).getSource(),
				((IostsAbstractTestCase) mIostsAbsTC).getTransitions().get(0)
						.getSourceTab()[0]);
		assertEquals(mIostsVul.getTransitions().get(0).getSource(),
				((IostsAbstractTestCase) mIostsAbsTC).getTransitions().get(0)
						.getSourceTab()[1]);
		assertEquals(mIostsSpec.getTransitions().get(0).getTarget(),
				((IostsAbstractTestCase) mIostsAbsTC).getTransitions().get(0)
						.getTargetTab()[0]);
		assertEquals(mIostsVul.getTransitions().get(0).getTarget(),
				((IostsAbstractTestCase) mIostsAbsTC).getTransitions().get(0)
						.getTargetTab()[1]);

		/**
		 * compare each element transition through label comparaison; we must
		 * have :
		 * 
		 * ?intent(a,d,c,v,) & !intent(action,category,type,uri,extra),
		 * [[a.in(Ac)] & [in(action,Ac+RV(String)) x
		 * in(category,C+RV(String)+INJ) x in(type,T+RV(String)) x
		 * in(uri,U+RV(String)) x in(extra,Ve)]] ,* & *
		 **/
		assertEquals(
				"?intent(a,d,c,v,) & !intent(action,category,type,uri,extra), [[a.in(Ac)] & [in(action,Ac+RV(String)) x in(category,C+RV(String)+INJ) x in(type,T+RV(String)) x in(uri,U+RV(String)) x in(extra,Ve)]] ,* &  *",
				((IostsAbstractTestCase) mIostsAbsTC).getTransitions().get(0)
						.getLabel());

		/**
		 * Second Level :
		 * 
		 * [l_1,l_1] -> [l_0,pass] or [l_1,l_1] -> [l_0,fail] or
		 * 
		 * [l_1,l_1] -> [l_0,inc]
		 */

		// first transition : [l_1,l_1] -> [l_0,pass]
		assertEquals("l_1", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(1).getSourceTab()[0].getName());
		assertEquals("l_1", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(1).getSourceTab()[1].getName());
		assertEquals("l_0", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(1).getTargetTab()[0].getName());
		assertEquals("pass", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(1).getTargetTab()[1].getName());
		// -> =!display (Activity A) & ?Display(Activit, [[*] & [),
		// [in(A.name,ApplicationComponent) x A.isEnable=true]] ,* & *
		assertEquals(
				"!display (Activity A) & ?Display(ActivityA), [[*] & [in(A.name,ApplicationComponent) x A.isEnable=true]] ,* &  *",
				((IostsAbstractTestCase) mIostsAbsTC).getTransitions().get(1)
						.getLabel());

		// second transition : [l_1,l_1] -> [l_0,fail]
		assertEquals("l_1", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(2).getSourceTab()[0].getName());
		assertEquals("l_1", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(2).getSourceTab()[1].getName());
		assertEquals("l_0", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(2).getTargetTab()[0].getName());
		assertEquals("fail", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(2).getTargetTab()[1].getName());
		// -> = !display (Activity A) & ?RessourceNotFoundException, [[*] & [*]]
		// ,* & *
		assertEquals(
				"!display (Activity A) & ?RessourceNotFoundException, [[*] & [*]] ,* &  *",
				((IostsAbstractTestCase) mIostsAbsTC).getTransitions().get(2)
						.getLabel());
		// last transion : [l_1,l_1] -> [l_0,inc]

		assertEquals("l_1", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(8).getSourceTab()[0].getName());
		assertEquals("l_1", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(8).getSourceTab()[1].getName());
		assertEquals("l_0", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(8).getTargetTab()[0].getName());
		assertEquals("inconclusive", ((IostsAbstractTestCase) mIostsAbsTC)
				.getTransitions().get(8).getTargetTab()[1].getName());

		// -> = !display (Activity A) & ?ActivityNotFoundException, [[*] & [*]]
		// ,* & *
		assertEquals(
				"!display (Activity A) & ?ActivityNotFoundException, [[*] & [*]] ,* &  *",
				((IostsAbstractTestCase) mIostsAbsTC).getTransitions().get(8)
						.getLabel());

		assertEquals(9, mIostsAbsTC.getTransitions().size());

	}
}
