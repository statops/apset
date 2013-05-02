package fr.openium.testcases.verdict;

/**
 * 
 * @author Stassia
 * 
 */
public class Fail extends Verdict {
	public Fail(String name) {
		super(name);

	}

	private static final String FAIL = "fail";

	@Override
	public String getVerdictName() {
		// TODO Auto-generated method stub
		return FAIL;
	}

}
