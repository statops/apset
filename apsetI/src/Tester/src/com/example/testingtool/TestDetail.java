package com.example.testingtool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import android.util.Log;

import com.example.testingtool.util.Xml;

public class TestDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mName;
	private String mVerdict = "false";
	private String mExceptionName = null;

	private String mAllDetail;
	private String mpackage;

	public TestDetail() {
	}

	public TestDetail(String Name, String AllDetail, String pack)
			throws ParserConfigurationException, TransformerException {
		mName = Name;
		mAllDetail = AllDetail;
		mpackage = pack;
		generateTestInfo();
		generateJuniXmlReport();

	}

	private void generateJuniXmlReport() throws ParserConfigurationException,
			TransformerException {
		File JUnitOutput = new File(TESTRESULT, getName() + ".xml");
		if (!JUnitOutput.exists()) {
			try {
				JUnitOutput.createNewFile();
				FileWriter write;
				try {
					write = new FileWriter(JUnitOutput.getAbsoluteFile());
					BufferedWriter out = new BufferedWriter(write);
					String text = Xml.create(getName(), getVerdict(),
							getDetail(), mpackage);
					out.write(text);
					out.close();
					Log.i("[Writting JUnit reports]", "OK");
				} catch (IOException e1) {
					e1.printStackTrace();
					Log.i("[Writting JUnit reports]", "fail");

				}
			} catch (IOException e) {
				e.printStackTrace();
				Log.i("[Creating new JUnit reports]", "fail");
			}
		}

	}

	private final static String TESTRESULT = "/mnt/sdcard/testResults/";
	public static final String FAIL = "fail";
	public static final String PASS = "ok";
	public static final String INC = "inconclusive";

	private void readJunitReport(TestDetail td) {

	}

	/**
	 * 
	 * Not Finish
	 */
	private void generateTestInfo() {

		if (mAllDetail.contains("Fail") || mAllDetail.contains("Exception"))
			mVerdict = "fail";
		else if (mAllDetail.contains("OK"))
			mVerdict = "pass";
		else
			mVerdict = "inc";
	}

	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @return the mVerdict
	 */
	public String getVerdict() {
		return mVerdict;
	}

	/**
	 * @return the mExceptionName
	 */
	public String getExceptionName() {
		if (!mExceptionName.equals(null))
			return mExceptionName;
		else
			return "No Exception";
	}

	public Boolean isPass() {
		if (mVerdict.equals("pass"))
			return true;
		else
			return false;
	}

	public Boolean isFail() {
		if (mVerdict.equals("fail"))
			return true;
		else
			return false;
	}

	public Boolean isInconclusive() {
		if (mVerdict.equals("inc"))
			return true;
		else
			return false;
	}

	public String getDetail() {
		return mAllDetail;
	}

}
