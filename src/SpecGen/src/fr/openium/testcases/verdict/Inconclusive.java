package fr.openium.testcases.verdict;

/**
 * 
 * @author Stassia
 * 
 */
public class Inconclusive extends Verdict {
	public Inconclusive(String name) {
		super(name);

	}

	private static final String INCONCLUSIVE = "inconclusive";

	@Override
	public String getVerdictName() {
		return INCONCLUSIVE;
	}

}
