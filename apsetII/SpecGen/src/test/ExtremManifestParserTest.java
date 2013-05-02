package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.junit.Test;
import org.xml.sax.SAXException;

import fr.openium.specification.xml.AndroidManifestParser;
import fr.openium.specification.xml.ManifestData;
import fr.openium.specification.xml.StreamException;

public class ExtremManifestParserTest extends TestCase {

	private ManifestData mManifestTestApp;
	private static String absolutePath;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File file = new File("");
		absolutePath = file.getAbsolutePath();

	}

	@Test
	public void testMissingTag() throws FileNotFoundException {
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/missingTag.xml");
		try {
			mManifestTestApp = AndroidManifestParser.parse(manifestStream);
			assertNotNull(mManifestTestApp);

		} catch (SAXException e) {
			assertNull("Missing Tag", mManifestTestApp);
			e.printStackTrace();
		} catch (IOException e) {
			assertNull(mManifestTestApp);
			e.printStackTrace();
		} catch (StreamException e) {
			assertNull(mManifestTestApp);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			assertNull("Missing Tag", mManifestTestApp);
			e.printStackTrace();
		}

	}

	@Test
	public void testWrongTag() throws FileNotFoundException {
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/wrongTag.xml");
		try {
			mManifestTestApp = AndroidManifestParser.parse(manifestStream);
			assertNotNull(mManifestTestApp);

		} catch (SAXException e) {
			assertNull("Wrong Tag", mManifestTestApp);
			e.printStackTrace();
		} catch (IOException e) {
			assertNull(mManifestTestApp);
			e.printStackTrace();
		} catch (StreamException e) {
			assertNull(mManifestTestApp);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			assertNull("Missing Tag", mManifestTestApp);
			e.printStackTrace();
		}

	}

	@Test
	public void testUselessTag() throws FileNotFoundException {
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/uselessTag.xml");
		try {
			mManifestTestApp = AndroidManifestParser.parse(manifestStream);
			assertNotNull(mManifestTestApp);
		} catch (SAXException e) {
			assertNull("Missing Tag", mManifestTestApp);
			e.printStackTrace();
		} catch (IOException e) {
			assertNull(mManifestTestApp);
			e.printStackTrace();
		} catch (StreamException e) {
			assertNull(mManifestTestApp);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			assertNull("Missing Tag", mManifestTestApp);
			e.printStackTrace();
		}

	}

	@Test
	public void testWrongMainTag() throws FileNotFoundException {
		/**
		 * 
		 * Manifest file did not begin with manifest Tag
		 * 
		 * Manifest data must be empty
		 * 
		 * */
		InputStream manifestStream = new FileInputStream(absolutePath
				+ "/data/wrongMainTag.xml");
		try {
			mManifestTestApp = AndroidManifestParser.parse(manifestStream);
			assertNotNull(mManifestTestApp);
			assertTrue(mManifestTestApp.getComponents().isEmpty());
			assertTrue(mManifestTestApp.getActivities().isEmpty());
			assertTrue(mManifestTestApp.getServices().isEmpty());

		} catch (SAXException e) {
			assertNull("Missing Tag", mManifestTestApp);
			e.printStackTrace();
		} catch (IOException e) {
			assertNull(mManifestTestApp);
			e.printStackTrace();
		} catch (StreamException e) {
			assertNull(mManifestTestApp);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			assertNull("Missing Tag", mManifestTestApp);
			e.printStackTrace();
		}

	}

}
