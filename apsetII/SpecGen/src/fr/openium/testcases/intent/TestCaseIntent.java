package fr.openium.testcases.intent;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.openium.specification.IAbstractIntent;
import fr.openium.specification.config.Extra;
import fr.openium.specification.xml.ManifestData.IntentData;
import fr.openium.testcases.verdict.Fail;
import fr.openium.testcases.verdict.Inconclusive;
import fr.openium.testcases.verdict.Pass;
import fr.openium.testcases.verdict.Verdict;

/***
 * 
 * @author Stassia
 * 
 *         Intent Object
 */
public class TestCaseIntent extends IAbstractIntent {

	/** Action Set */
	private ArrayList<String> mActionSet = new ArrayList<String>();

	/** Category Set */
	private ArrayList<String> mCategorySet = new ArrayList<String>();

	/** Host set */
	private ArrayList<String> mHostSet = new ArrayList<String>();

	/** Scheme Set */
	private ArrayList<String> mSchemeSet = new ArrayList<String>();

	/** Path set */
	private ArrayList<String> mPathSet = new ArrayList<String>();

	/** Type set */
	private ArrayList<String> mTypeSet = new ArrayList<String>();

	/**
	 * Extra set: completed during JUNIT generation, because no Guard about it
	 * is handle in the guard
	 */
	private ArrayList<Extra> mExtraSet = new ArrayList<Extra>();

	/** Pass set */
	private Verdict mPass;

	/** Fail set */
	private Verdict mFail;

	/** Inconclusive set */
	private Verdict mInconclusive;

	public TestCaseIntent(String name) {
		super(name);
		mPass = new Pass(name);
		mFail = new Fail(name);
		mInconclusive = new Inconclusive(name);

	}

	public ArrayList<String> getActionSet() {
		return mActionSet;
	}

	public void setActionSet(String action) {
		if (!mActionSet.contains(action)) {
			this.mActionSet.add(action);
		}
	}

	public ArrayList<String> getCategorySet() {
		return mCategorySet;
	}

	public void setCategorySet(String category) {
		if (!mCategorySet.contains(category)) {
			this.mCategorySet.add(category);
		}
	}

	public void setCategorySet(ArrayList<String> category) {
		for (String categoryTemp : category) {
			setCategorySet(categoryTemp);
		}
	}

	public void setActionSet(ArrayList<String> action) {
		for (String actionTemp : action) {
			setActionSet(actionTemp);
		}

	}

	public void setUri(IntentData data) {
		setSchemeSet(data.getScheme());
		setHostSet(data.getHost());
		setPathSet(data.getPath());

	}

	public ArrayList<String> getHostSet() {
		return mHostSet;
	}

	public void setHostSet(String host) {
		if (!mHostSet.contains(host)) {
			this.mHostSet.add(host);
		}
	}

	public ArrayList<String> getSchemeSet() {
		return mSchemeSet;
	}

	public void setSchemeSet(String schme) {
		if (!mSchemeSet.contains(schme)) {
			this.mSchemeSet.add(schme);
		}

	}

	public void setUri(ArrayList<IntentData> data) {
		for (IntentData temp : data) {
			setUri(temp);
		}

	}

	public ArrayList<String> getPathSet() {
		return mPathSet;
	}

	public void setPathSet(ArrayList<String> mPathSet) {
		this.mPathSet = mPathSet;
	}

	public void setPathSet(String path) {
		if (!mPathSet.contains(path)) {
			this.mPathSet.add(path);
		}
	}

	public ArrayList<String> getTypeSet() {
		return mTypeSet;
	}

	public void setTypeSet(String type) {
		if (!mTypeSet.contains(type)) {
			this.mTypeSet.add(type);
		}
	}

	public void setTypeSet(ArrayList<String> type) {
		for (String temp : type) {
			setTypeSet(temp);
		}
	}

	public Fail getFail() {
		return (Fail) mFail;
	}

	public Pass getPass() {
		return (Pass) mPass;
	}

	public void setFail(Fail fail) {
		this.mFail = fail;
	}

	public void setFail(String output) {
		this.mFail.add(output);
	}

	public Inconclusive getInconclusive() {
		return (Inconclusive) mInconclusive;
	}

	public void setInconclusive(Inconclusive inconclusive) {
		this.mInconclusive = inconclusive;
	}

	public void setInconclusive(String inconclusive) {
		this.mInconclusive.add(inconclusive);
	}

	public void setPass(String ouput, String guard, String assign) {
		mPass.add(ouput);
		Pattern inPattern2 = Pattern.compile(PASSREGEX);
		Matcher matcher2 = inPattern2.matcher(guard);
		while (matcher2.find()) {
			if (!matcher2.group(0).isEmpty()
					&& !matcher2.group(0).contentEquals(" ")
					&& !matcher2.group(0).contentEquals("x"))
				((Pass) mPass).setGuard(matcher2.group(0));
		}

		((Pass) mPass).setAssign(assign);
	}

	private static final String PASSREGEX = "([A-WY-Za-wy-z\\(\\)\\,\\.]*)(?:[x]?)([A-WY-Za-wy-z\\=]*)";

	public void setPass(String ouput) {
		this.mPass.add(ouput);
	}

}
