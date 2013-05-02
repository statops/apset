package fr.openium.results;

public class JunitResultData {
	private String mMessage;
	private String mPackage;
	private String mClassname;
	private String mTestName;
	private String mTime;
	private String mTestSuiteName;
	private String mFailure;
	private String mError;
	private boolean mVerdict;

	/**
	 * JUnit resulting from parsing
	 */
	public JunitResultData() {

	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public String getPackage() {
		return mPackage;
	}

	public void setPackage(String mPackage) {
		this.mPackage = mPackage;
	}

	public String getClassname() {
		return mClassname;
	}

	public void setClassname(String mClassname) {
		this.mClassname = mClassname;
	}

	public String getTestName() {
		return mTestName;
	}

	public void setTestName(String mTestName) {
		this.mTestName = mTestName;
	}

	public String getTime() {
		return mTime;
	}

	public void setTime(String mTime) {
		this.mTime = mTime;
	}

	public String getTestSuiteName() {
		return mTestSuiteName;
	}

	public void setTestSuiteName(String mTestSuiteName) {
		this.mTestSuiteName = mTestSuiteName;
	}

	public String getFailure() {
		return mFailure;
	}

	public void setFailure(String mFailure) {
		this.mFailure = mFailure;
	}

	public String getError() {
		return mError;
	}

	public void setError(String mError) {
		this.mError = mError;
	}

	public boolean isVul() {

		if (mFailure.equalsIgnoreCase("1")) {
			if (mMessage.contains("NOT_VULNERABLE")) {
				return false;
			} else if (mMessage.contains("VULNERABLE")) {
				return true;
			}

		}
		// Case of pass
		if (mFailure.equalsIgnoreCase("0")) {
			return false;
		} else
			return false;

		// case of error

	}

	public boolean getVerdict() {
		return isVul();
	}

}
