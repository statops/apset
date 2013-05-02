package fr.openium.specification.dot;

import fr.openium.components.ActivityComponent;
import fr.openium.components.ServiceComponent;
import fr.openium.specification.config.Actions;
import fr.openium.specification.xml.ManifestData;
import fr.openium.specification.xml.ManifestData.Intent;
import graphiz.api.GraphViz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DotToGraphviz {

	private static final File FILE = new File("");
	public final String DOTFILEPATH;
	public final static String ACTIVITIES = "activities/";
	public final String GIFFILEPATH;
	public static final String SERVICES = "services/";
	public final static String ABSTRACTTESTCASE = "AbstractTestCase/outputAbstract/";

	public DotToGraphviz() {
		DOTFILEPATH = FILE.getAbsolutePath() + "/dotFile/";
		GIFFILEPATH = FILE.getAbsolutePath() + "/GifFile/";
	}

	/** Construct a Dot Graph */

	public class State {
		private String mLabel;
		private final String mID;
		private String mComponent;

		public State(String name) {
			mID = "l" + name;

		}

		public String getName() {
			return mID;
		}

		public String getLabel() {
			return mLabel;
		}

		public void setLabel(String mLabel) {
			this.mLabel = mLabel;
		}

		public String getComponentName() {
			return mComponent;
		}

		public String setComponentName(String name) {
			mComponent = name;
			return mComponent;
		}

	}

	public class Transition {

		private final State mA;
		private final State mB;
		private final String mLabel;

		public Transition(State a, String label, State b) {
			mA = a;
			mB = b;
			mLabel = label;

		}

		public State getA() {
			return mA;
		}

		public State getB() {
			return mB;
		}

		public String getLabel() {
			return mLabel;
		}

		public String toString() {
			return mA.getName() + "->" + mB.getName() + "[label=" + mLabel
					+ "]" + ";";

		}
	}

	/**
	 * generate the dot file for each component.
	 * 
	 * @param Data
	 *            the Manifest Data resulting from the manifest parsing
	 * @return Graphiz :list of the graph of the specification that we will use
	 *         to generate .gif file.
	 * */

	public ArrayList<GraphViz> createIostsDot(ManifestData data) {

		/**
		 * The number of dot file will be equal to the number of the service and
		 * activities
		 */
		int size = data.getActivities().size() + data.getServices().size();
		ArrayList<GraphViz> graph = new ArrayList<GraphViz>(size);

		/**
		 * generate dot file for eact Activity
		 * */

		for (ActivityComponent activity : data.getActivities()) {
			GraphViz gv = new GraphViz();
			beginGraphiz(gv, activity.getName());
			int i = 0;
			State initialState = new State("_" + i);
			i++;
			for (Intent intent : activity.getIntent()) {
				if (Actions.A_output.contains(intent.getAction())) {
					/**
					 * to be completed with category, data and some defined
					 * extra
					 */
					String label = "\"?intent(" + intent.getAction() + "), "
							+ "[a.in(Ao)], " + "*" + "\"";
					State two = new State("_" + i);
					i++;
					/** the first transition */
					Transition tr = new Transition(initialState, label, two);
					addTransitionToGraphiz(tr, gv);
					// gv.add(tr.toString());

					/** the second transition */
					label = "\"!display (Activity A), [A.resp#null], " + "*"
							+ "\"";
					Transition tr2 = new Transition(two, label, initialState);
					addTransitionToGraphiz(tr2, gv);// gv.add(tr.toString());

				} else if (Actions.A_quiescence.contains(intent.getAction())) {
					String label = "\"?intent(" + intent.getAction() + "), "
							+ "[a.in(Aq)], " + "*" + "\"";
					State two = new State("_" + i);
					i++;
					/** the first transition */
					Transition tr = new Transition(initialState, label, two);
					addTransitionToGraphiz(tr, gv);// gv.add(tr.toString());
					/** the second transition */
					label = "\"!display (Activity A),[A.resp=null], " + "*"
							+ "\"";
					Transition tr2 = new Transition(two, label, initialState);
					addTransitionToGraphiz(tr2, gv);// gv.add(tr2.toString());
				} else {
					String label = "\"?intent(" + intent.getAction() + "), "
							+ "[a.in(Ac)], " + "*" + "\"";
					State two = new State("_" + i);
					i++;
					/** the first transition */
					Transition tr = new Transition(initialState, label, two);
					addTransitionToGraphiz(tr, gv);// gv.add(tr.toString());
					/** the second transition */
					label = "\"!display (Activity A), [*], * \"";
					Transition tr2 = new Transition(two, label, initialState);
					addTransitionToGraphiz(tr2, gv);// gv.add(tr2.toString());
				}

			}
			/** End of the graph */
			endGraphiz(gv);
			graph.add(gv);
			/**
			 * /** the dot file generated for each activity
			 * */
			File dotfile = new File(DOTFILEPATH + ACTIVITIES
					+ activity.getName() + ".dot");
			FileWriter fW;
			try {
				fW = new FileWriter(dotfile, true);
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
		 * Generate dot file for Services
		 * */
		for (ServiceComponent service : data.getServices()) {
			GraphViz gv = new GraphViz();
			beginGraphiz(gv, service.getName());
			int i = 0;
			State initialState = new State("_" + i);
			i++;
			for (Intent intent : service.getIntent()) {
				if (Actions.A_output.contains(intent.getAction())) {
					/**
					 * to be completed with category, data and some defined
					 * extra
					 */

					String label = "\"?intent(" + intent.getAction()
							+ "), [a.in(A_o)], * \"";
					State two = new State("_" + i);
					i++;
					/** the first transition */
					Transition tr = new Transition(initialState, label, two);
					addTransitionToGraphiz(tr, gv);// gv.add(tr.toString());
					/** the second transition */
					label = "\"!running (Service S), [S.resp#null], *\"";
					Transition tr2 = new Transition(two, label, initialState);
					addTransitionToGraphiz(tr2, gv);// gv.add(tr2.toString());

				}

				else {
					String label = "\"?intent(" + intent.getAction()
							+ "), [*], *\"";
					State two = new State("_" + i);
					i++;
					/** the first transition */
					Transition tr = new Transition(initialState, label, two);
					addTransitionToGraphiz(tr, gv);// gv.add(tr.toString());
					/** the second transition */
					label = "\"!running (Service), [*], *\"";
					Transition tr2 = new Transition(two, label, initialState);
					addTransitionToGraphiz(tr2, gv);// gv.add(tr2.toString());
				}

			}
			/** End of the graph */
			endGraphiz(gv);
			graph.add(gv);
			/**
			 * the dot file generated for each activity
			 * */
			File dotfile = new File(DOTFILEPATH + SERVICES + service.getName()
					+ ".dot");
			FileWriter fW;
			try {
				fW = new FileWriter(dotfile, true);
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
		return graph;

	}

	/**
	 * add the iosts transition in a dot file
	 * 
	 * @param transition
	 *            the transition description
	 * @param file
	 *            the file.dot
	 * 
	 * */

	private File addTransition(Transition transition, File file) {

		try {
			FileWriter fW = new FileWriter(file, true);
			BufferedWriter output = new BufferedWriter(fW);
			output.write(transition.toString());

			/**
			 * Each transition is ended with a next line
			 */
			output.write("\n");
			output.flush();
			output.close();
			return file;

		} catch (IOException e) {

			e.printStackTrace();
			return file;
		}

	}

	/**
	 * add the iosts transition in a graph
	 * 
	 * @param transition
	 *            the transition description
	 * @param graphiz
	 *            gv
	 * 
	 * 
	 * */

	private void addTransitionToGraphiz(Transition transition, GraphViz gv) {
		gv.add(transition.toString());
		gv.add("\n");
		gv.add("\n");
		/**
		 * Do not forget to stop the graph
		 */
	}

	public void endGraphiz(GraphViz gv) {
		gv.addln(gv.end_graph());
	}

	public static void beginGraphiz(GraphViz gv, String ID) {
		gv.addln(gv.start_graph(" " + ID));
	}

	public void createGifFile(ArrayList<GraphViz> gv) {
		String type = "gif";
		for (GraphViz graph : gv) {
			int i = 0;
			File out = new File(GIFFILEPATH + "out" + i + "." + type);
			i++;
			graph.writeGraphToFile(graph.getGraph(graph.getDotSource(), type),
					out);
		}

	}

	/**
	 * Read Dot source from a file convert to image and store the image in the
	 * file system.
	 */
	private void readDotFile() {
		String dir = "/home/jabba/eclipse2/laszlo.sajat/graphviz-java-api"; // Linux
		String input = dir + "/sample/simple.dot";

		GraphViz gv = new GraphViz();
		gv.readSource(input);
		System.out.println(gv.getDotSource());

		String type = "gif";
		File out = new File("/tmp/simple." + type); // Linux

		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	}
}
