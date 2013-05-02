package fr.openium.Junit.pairwise;

import java.util.ArrayList;
import java.util.List;

import fr.addlib.AllPairs;

/***
 * 
 * @author Stassia
 * 
 *         generate all pairs
 * 
 */
public class PairWise {

	private List<List> sequence = new ArrayList<List>();
	private static int nbTestMax = -1;

	List<String> liste = new ArrayList<String>();

	/**
	 * generate sequence value
	 * 
	 * @return String List of the sequence value.
	 */
	public List<String> test() {
		StringBuffer chaine = new StringBuffer();
		if (sequence.size() == 1) {
			for (int j = 0; j < sequence.get(0).size(); j++) {
				if (nbTestMax != -1 && j == nbTestMax)
					break;
				chaine.append(sequence.get(0).get(j));
				liste.add(chaine.toString());
				chaine = new StringBuffer();
			}
		} else {

			int[] tab = new int[sequence.size()];
			for (int a = 0; a < sequence.size(); a++) {
				if (sequence.get(a).size() > 1)
					tab[a] = sequence.get(a).size();
				else
					tab[a] = 1;
			}

			int[][] res;
			AllPairs p = new AllPairs(tab, (long) 1, true);
			res = p.generateGreedy();
			for (int i = 0; i < res.length; i++) {
				if (nbTestMax != -1 && i == nbTestMax)
					break;
				for (int j = 0; j < sequence.size(); j++) {
					if (sequence.get(j).size() > 1) {
						if (j == tab.length - 1)
							chaine.append(sequence.get(j).get(res[i][j]));
						else
							chaine.append(sequence.get(j).get(res[i][j]) + ":");
					} else {
						if (j == tab.length - 1)
							chaine.append("");
						else
							chaine.append(" :");
					}

				}
				liste.add(chaine.toString());
				chaine = new StringBuffer();
			}

		}
		return liste;
	}

	/**
	 * 
	 * 
	 * @param sequence
	 *            assign sequence
	 */
	public void setSequence(List<List> sequence) {
		this.sequence = sequence;
	}

	public static void setNbTestMax(int nbTestMax) {
		PairWise.nbTestMax = nbTestMax;
	}

}
