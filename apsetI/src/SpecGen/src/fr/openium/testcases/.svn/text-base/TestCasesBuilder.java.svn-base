package fr.openium.testcases;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.openium.iosts.Iosts;
import fr.openium.iosts.IostsAbstractTestCase;
import fr.openium.iosts.IostsParser;
import fr.openium.iosts.IostsSpecification;
import fr.openium.iosts.IostsVulnerabilities;
import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;
import fr.openium.specification.xml.StreamException;

/**
 * @author Stassia
 * 
 * 
 *         This Class aim to realize the test concretisation :
 * 
 */
public class TestCasesBuilder {

	/** the manifest Data */
	private ManifestData mManifestData;

	/** The completed Specifications */
	private ArrayList<IostsAbstractTestCase> mAbstractTC = new ArrayList<IostsAbstractTestCase>();

	/**
	 * the partial specification
	 */
	private ArrayList<IostsSpecification> mPartialSpecifications = new ArrayList<IostsSpecification>();

	/**
	 * @param manifestStream
	 *            : the manifest file
	 * @param vulnActivitylStream
	 *            : vulnerability for activity
	 * @param vulnServiceStream
	 *            : vulnerability for service
	 * 
	 * 
	 * **/
	public TestCasesBuilder(InputStream manifestStream,
			InputStream vulnActivityStream, InputStream vulnServiceStream) {
		setAbstractTC(manifestStream, vulnActivityStream, vulnServiceStream);

	}

	/**
	 * Step 1:
	 * 
	 * @input: Manifest file
	 * @output : ManifestData
	 * */
	private ManifestData getManifestData(InputStream manifestStream) {
		try {
			mManifestData = AndroidManifestParser.parse(manifestStream);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mManifestData;
	}

	private void setAbstractTC(InputStream manifestStream,
			InputStream vulnActivitylStream, InputStream vulnServiceStream) {
		mAbstractTC = getAbstractTestCase(manifestStream, vulnActivitylStream,
				vulnServiceStream);

	}

	/**
	 * Step 2:
	 * 
	 * @input: ManifestData
	 * @output : List of Specifications
	 * */

	private ArrayList<IostsSpecification> getPartialSpecifications(
			InputStream manifestStream) {
		mManifestData = getManifestData(manifestStream);
		mPartialSpecifications = IostsParser
				.getIostsSpecSetFromData(mManifestData);
		return mPartialSpecifications;
	}

	/**
	 * @param manifestStream
	 *            : the manifest file
	 * @param vulnActivitylStream
	 *            : vulnerability for activity
	 * @param vulnServiceStream
	 *            : vulnerability for service
	 * @return the abstract test case
	 * 
	 * **/
	private ArrayList<IostsAbstractTestCase> getAbstractTestCase(
			InputStream manifestStream, InputStream vulnActivitylStream,
			InputStream vulnServiceStream) {

		mPartialSpecifications = getPartialSpecifications(manifestStream);
		Iosts IostsActivityVulnerability = new IostsVulnerabilities();
		Iosts IostsServiceVulnerability = new IostsVulnerabilities();

		IostsActivityVulnerability = getVulnerability(vulnActivitylStream,
				IostsActivityVulnerability);
		IostsServiceVulnerability = getVulnerability(vulnServiceStream,
				IostsServiceVulnerability);
		/**
		 * Step 4:
		 * 
		 * @input: List of Specifications & Iosts Vulnerability
		 * @output : iostsAbstractTestCase
		 * */

		SymbolicTreeGeneration tCgen;
		for (IostsSpecification spec : mPartialSpecifications) {
			if (spec != null) {

				if (spec.getType().equals(IostsParser.SERVICE_TYPE)) {
					tCgen = new SymbolicTreeGeneration(spec,
							(IostsVulnerabilities) IostsServiceVulnerability);

				} else if (spec.getType().equals(IostsParser.ACTIVITY_TYPE)) {
					tCgen = new SymbolicTreeGeneration(spec,
							(IostsVulnerabilities) IostsActivityVulnerability);
				} else {
					tCgen = null;
				}

				if (tCgen != null) {
					IostsAbstractTestCase tc = (IostsAbstractTestCase) tCgen
							.generateAbstractTestCases();

					mAbstractTC.add(tc);
				}
			}
		}
		return mAbstractTC;

	}

	/**
	 * Step 3:
	 * 
	 * @input: vuln File
	 * @output : IostsVulnerability
	 * */
	private Iosts getVulnerability(InputStream vulnActivitylStream, Iosts vuln) {
		return IostsParser.parseDotIosts(vulnActivitylStream, vuln);
	}

	/**
	 * @return the Abstract test cases List mAbstractTC
	 * */
	public ArrayList<IostsAbstractTestCase> getAbstractTestCase() {
		return mAbstractTC;

	}

	/**
	 * Translation of each transition expression for each test case
	 * 
	 * Generate variables set values for each test cases.
	 * 
	 * Associate Variable set for each test case
	 * 
	 * */
	private void concretize() {

	}

	public void generateJunitTestCase() {

	}

}
