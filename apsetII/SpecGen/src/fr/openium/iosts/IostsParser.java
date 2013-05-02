package fr.openium.iosts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import fr.openium.specification.IAbstractComponent;
import fr.openium.specification.config.Actions;
import fr.openium.specification.config.Tokens;
import fr.openium.specification.dot.DotToGraphviz;
import fr.openium.specification.xml.ManifestData;
import fr.openium.specification.xml.ManifestData.Intent;
import graphiz.api.GraphViz;

/**
 * This Class is responsible for parsing the dot file. It provides information
 * about the locations and transitions of the IOSTS.
 * */
public class IostsParser {
	public static final String GRAPHOPEN = "digraph";
	public static final String GRAPHEND = "}";
	public static final String UNION = "+";
	public static final String AND = "x";
	public static final String OR = "-";
	public static final String ACTIVITY_TYPE = "activity";
	public static final String SERVICE_TYPE = "service";
	public static final String PROVIDER_TYPE = "provider";
	public static final String RECEIVER_TYPE = "receiver";

	public void init() {

	}

	/**
	 * Reads the content of a text file into a String.
	 * 
	 * @param dot
	 *            : the dotfile iosts: the object
	 * 
	 * @return the Iosts object
	 **/
	public static Iosts parseDotIosts(InputStream dot, Iosts iosts) {
		/**
		 * Retrieve the DOT text
		 * */

		Scanner scan;
		scan = new Scanner(dot);

		/** First line is the start_graph containing the graph name : ID */
		String dotText = scan.nextLine();
		String id = null;

		while (scan.hasNext()) {
			dotText = scan.nextLine();

			try {
				if (!dotText.isEmpty()) {

					String source = dotText.substring(0,
							dotText.indexOf(Tokens.ARROW, 0));
					String target = dotText.substring(
							dotText.indexOf(Tokens.ARROW, 0) + 2,
							dotText.indexOf(Tokens.LSBRACKETS, 0));
					String transition = dotText.substring(
							dotText.indexOf(Tokens.LABEL) + 7,
							dotText.indexOf(Tokens.RSBRACKETS
									+ Tokens.SEMICOLON));
					iosts.setTransitionFromString(new IostsLocation(source),
							new IostsLocation(target), transition);

				}

			} catch (StringIndexOutOfBoundsException s1) {
				if (dotText.contains(GRAPHOPEN)) {
					id = getId(dotText);
					iosts.setName(id);
				}
				if (dotText.equalsIgnoreCase(GRAPHEND))
					System.out.println("fin");

			}
		}

		/** first syllable is always a Location */
		iosts.setInitLocation(iosts.getTransitions().get(0).getSource());

		return iosts;
	}

	/**
	 * Reads the content of a text file into a String.
	 * 
	 * @param component
	 * 
	 * 
	 * @return the Iosts object
	 **/
	public static Iosts parseComponentToIosts(IAbstractComponent component,
			IostsSpecification out) {
		int i = 0;
		out.setName(component.getName());
		out.setType(component.getComponentType());
		IostsLocation init = new IostsLocation("l_" + i);
		out.setInitLocation(init);
		System.out.println("hasIntentFilter: " + component.hasIntentFilter()
				+ " : " + component.getName());
		if ((component.getComponentType().equals(PROVIDER_TYPE))
				|| (component.getComponentType().equals(RECEIVER_TYPE))) {
			out = null;
		} else if (!component.hasIntentFilter()) {
			// We apply one simple explicit intent
			/** the first transition */
			i++;
			IostsTransition tr = new IostsTransition(init, new IostsLocation(
					"l_" + i));
			tr.setAction(IostsSpecification.INPUT
					+ IostsSpecification.INPUT_ACTION);
			tr.setGuard(Tokens.EMPTY);
			tr.setAssignments(Tokens.EMPTY);
			tr.setLabel();

			/** the second transition */
			IostsTransition tr2 = new IostsTransition(new IostsLocation("l_"
					+ i), init);
			if (component.getComponentType().equals(ACTIVITY_TYPE)) {
				tr2.setAction(IostsSpecification.OUTPUT
						+ IostsSpecification.OUTPUT_ACTIVITY_ACTION);
			} else if (component.getComponentType().equals(SERVICE_TYPE)) {
				tr2.setAction(IostsSpecification.OUTPUT
						+ IostsSpecification.OUTPUT_SERVICE_ACTION);
			}
			tr2.setGuard(Tokens.EMPTY);
			tr2.setAssignments(Tokens.EMPTY);
			tr2.setLabel();
			out.addTransition(tr);// (tr);
			out.addTransition(tr2);// getTransitions().add(tr2);

		} else if (component.getComponentType().equals(ACTIVITY_TYPE)) {
			for (Intent intent : component.getIntent()) {
				if (Actions.A_output.contains(intent.getAction())) {
					/**
					 * to be completed with category, data and some defined
					 * extra
					 */

					/** the first transition */
					i++;
					IostsTransition tr = new IostsTransition(init,
							new IostsLocation("l_" + i));
					tr.setAction(IostsSpecification.INPUT
							+ IostsSpecification.INPUT_ACTION);
					tr.setGuard(IostsSpecification.A_0);
					tr.setAssignments(Tokens.EMPTY);
					tr.setLabel();

					/** the second transition */
					IostsTransition tr2 = new IostsTransition(
							new IostsLocation("l_" + i), init);
					tr2.setAction(IostsSpecification.OUTPUT
							+ IostsSpecification.OUTPUT_ACTIVITY_ACTION);
					tr2.setGuard(Tokens.EMPTY);
					tr2.setAssignments(IostsSpecification.ACTIVITY_RESPONSE);
					tr2.setLabel();
					out.addTransition(tr);// (tr);
					out.addTransition(tr2);// getTransitions().add(tr2);
				} else if (Actions.A_quiescence.contains(intent.getAction())) {

					/** the first transition */
					i++;
					IostsTransition tr = new IostsTransition(init,
							new IostsLocation("l_" + i));
					tr.setAction(IostsSpecification.INPUT
							+ IostsSpecification.INPUT_ACTION);
					tr.setGuard(IostsSpecification.A_Q);
					tr.setAssignments(Tokens.EMPTY);
					tr.setLabel();

					/** the second transition */
					IostsTransition tr2 = new IostsTransition(
							new IostsLocation("l_" + i), init);
					tr2.setAction(IostsSpecification.OUTPUT
							+ IostsSpecification.OUTPUT_ACTIVITY_ACTION);
					tr2.setGuard(Tokens.EMPTY);
					tr2.setAssignments(IostsSpecification.ACTIVITY_NORESPONSE);
					tr2.setLabel();
					out.addTransition(tr);// (tr);
					out.addTransition(tr2);// getTransitions().add(tr2);
				} else {

					/** the first transition */
					i++;
					IostsTransition tr = new IostsTransition(init,
							new IostsLocation("l_" + i));
					tr.setAction(IostsSpecification.INPUT
							+ IostsSpecification.INPUT_ACTION);
					tr.setGuard(IostsSpecification.A_C);
					tr.setAssignments(Tokens.EMPTY);
					tr.setLabel();

					/** the second transition */
					IostsTransition tr2 = new IostsTransition(
							new IostsLocation("l_" + i), init);
					tr2.setAction(IostsSpecification.OUTPUT
							+ IostsSpecification.OUTPUT_ACTIVITY_ACTION);
					tr2.setGuard(Tokens.EMPTY);
					tr2.setAssignments(Tokens.EMPTY);
					tr2.setLabel();
					out.addTransition(tr);// (tr);
					out.addTransition(tr2);// getTransitions().add(tr2);
				}

			}
		} else if (component.getComponentType().equals(SERVICE_TYPE)) {

			for (Intent intent : component.getIntent()) {
				i++;
				if (Actions.A_output.contains(intent.getAction())) {

					/** the first transition */
					IostsTransition tr = new IostsTransition(init,
							new IostsLocation("l_" + i));
					tr.setAction(IostsSpecification.INPUT
							+ IostsSpecification.INPUT_ACTION);
					tr.setGuard(IostsSpecification.A_C);
					tr.setAssignments(Tokens.EMPTY);
					tr.setLabel();

					/** the second transition */
					IostsTransition tr2 = new IostsTransition(
							new IostsLocation("l_" + i), init);
					tr2.setAction(IostsSpecification.OUTPUT
							+ IostsSpecification.OUTPUT_SERVICE_ACTION);
					tr2.setGuard(Tokens.EMPTY);
					tr2.setAssignments(IostsSpecification.SERVICE_RESPONSE);
					tr2.setLabel();
					out.getTransitions().add(tr);
					out.getTransitions().add(tr2);

				} else {
					/** the first transition */
					IostsTransition tr = new IostsTransition(init,
							new IostsLocation("l_" + i));
					tr.setAction(IostsSpecification.INPUT
							+ IostsSpecification.INPUT_ACTION);
					tr.setGuard(Tokens.EMPTY);
					tr.setAssignments(Tokens.EMPTY);
					tr.setLabel();

					/** the second transition */
					IostsTransition tr2 = new IostsTransition(
							new IostsLocation("l_" + i), init);
					tr2.setAction(IostsSpecification.OUTPUT
							+ IostsSpecification.OUTPUT_SERVICE_ACTION);
					tr2.setGuard(Tokens.EMPTY);
					tr2.setAssignments(Tokens.EMPTY);
					tr2.setLabel();
					out.getTransitions().add(tr);
					out.getTransitions().add(tr2);

				}
			}

		}

		return out;
	}

	private StringBuilder getText(File file) {
		StringBuilder sb = new StringBuilder();
		FileInputStream fIn;
		try {
			fIn = new FileInputStream(file);
			DataInputStream dIn = new DataInputStream(fIn);
			BufferedReader br = new BufferedReader(new InputStreamReader(dIn));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			dIn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * @param the
	 *            first line of the dot file
	 * @return the ID
	 */
	private static String getId(String s) {
		String temp[] = s.split(" ");
		return temp[1];
	}

	/**
	 * this method provide the final result of the partial specification.
	 * 
	 * @param data
	 *            the manifest object
	 * 
	 * @return the set of IostsSpecification
	 * */
	public static ArrayList<IostsSpecification> getIostsSpecSetFromData(
			ManifestData data) {
		ArrayList<IostsSpecification> iostsSpecifications = new ArrayList<IostsSpecification>();
		for (IAbstractComponent component : data.getComponents()) {
			iostsSpecifications.add((IostsSpecification) parseComponentToIosts(
					component, new IostsSpecification()));

		}
		return iostsSpecifications;

	}

	/**
	 * this method provide the final result of the partial specification.
	 * 
	 * @param dot
	 *            the list of iosts description in dot files of all component
	 * 
	 * @return the set of IostsSpecification
	 * */

	public ArrayList<Iosts> getIostsSpecSetFromDot(ArrayList<InputStream> dot) {
		ArrayList<Iosts> iostsSpec = new ArrayList<Iosts>();
		for (InputStream specDot : dot) {
			iostsSpec.add(parseDotIosts(specDot, new IostsSpecification()));
		}
		return iostsSpec;

	}

	public static void generateDotFile(Iosts iosts) {
		GraphViz gv = new GraphViz();
		DotToGraphviz DotToGraphviz = new DotToGraphviz();
		DotToGraphviz.beginGraphiz(gv, iosts.getName());
		for (IostsTransition transition : iosts.getTransitions()) {
			gv.add(transition.toString());
			gv.add("\n");
		}
		DotToGraphviz.endGraphiz(gv);

		/**
		 * the dot file generated for each component
		 * */
		File dotfile = null;
		if (iosts instanceof IostsSpecification) {
			if (iosts.getType().equals(ACTIVITY_TYPE)) {
				dotfile = new File(DotToGraphviz.DOTFILEPATH
						+ DotToGraphviz.ACTIVITIES + iosts.getName() + ".dot");
			} else if (iosts.getType().equals(SERVICE_TYPE)) {
				dotfile = new File(DotToGraphviz.DOTFILEPATH
						+ DotToGraphviz.SERVICES + iosts.getName() + ".dot");
			} else {
				dotfile = new File(DotToGraphviz.DOTFILEPATH + iosts.getName());
			}
		} else if (iosts instanceof IostsAbstractTestCase) {
			dotfile = new File(DotToGraphviz.DOTFILEPATH
					+ DotToGraphviz.ABSTRACTTESTCASE + iosts.getName() + ".dot");
		}

		FileWriter fW;
		try {
			fW = new FileWriter(dotfile, false);
			BufferedWriter output = new BufferedWriter(fW);
			output.write(gv.getDotSource());

			/**
			 * Each transition is ended with a next line
			 */
			output.write("\n");
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Generate the dotFile from IostsSpecification
	 * */
	public static void generateSpecDotFile(
			ArrayList<IostsSpecification> iostsSpec) {
		for (IostsSpecification spec : iostsSpec) {
			// we add "if not null" condition because we do not take account of
			// the case of
			// provider and receiver ( we affected null to their iosts in
			// @parseIostsFromManifestData
			if (spec != null) {
				generateDotFile(spec);
			}

		}

	}

	/** Generate the dotFile from IostsAbstractTestCase */
	public static void generateAbstractTestCaseDotFile(
			ArrayList<IostsAbstractTestCase> iostsTC) {
		for (IostsAbstractTestCase aTC : iostsTC) {
			if (aTC != null) {
				generateDotFile(aTC);
			}

		}

	}

}
