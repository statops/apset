package test;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {

		TestSuite specGenTestSuite = new TestSuite("Spec Gen test");
		specGenTestSuite.addTestSuite(ActivitysimpleTest.class);
		specGenTestSuite.addTestSuite(ServiceSimpleTest.class);
		specGenTestSuite.addTestSuite(MultipleActivityTest.class);
		specGenTestSuite.addTestSuite(MultipleServiceTest.class);
		specGenTestSuite.addTestSuite(MultipleActService.class);
		specGenTestSuite.addTestSuite(CpandRecieverTest.class);
		specGenTestSuite.addTestSuite(IostsParserTest.class);
		specGenTestSuite.addTestSuite(ExtremManifestParserTest.class);
		specGenTestSuite.addTestSuite(ExtremSpeccAddTest.class);
		specGenTestSuite.addTestSuite(SymbolicTreeGenerationTest.class);
		specGenTestSuite.addTestSuite(SpeccAddParserTest.class);

		return specGenTestSuite;

	}
}
