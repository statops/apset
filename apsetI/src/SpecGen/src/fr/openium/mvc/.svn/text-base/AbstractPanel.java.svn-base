package fr.openium.mvc;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class AbstractPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	protected Model mdl;

	protected Controller ctrl;
	protected List<JButton> mBouttons;
	public JTextField mTestNumber;

	protected JLabel sdktlbl;
	protected JLabel projectlbl;
	protected JLabel testprojectlbl;

	protected JLabel mRunShow;
	protected JLabel mOutPuFiles;
	protected JLabel mVul;
	protected JLabel mNVul;

	public JCheckBox mAvailability;
	public JCheckBox mIntegrity;

	public AbstractPanel() {

	}

	public static final String AVAILABILITY = "Availability";
	public static final String INTEGRITY = "Integrity";

	public static final String JUNITPATHFILE = "Junit Generation";
	public static final String MAX_TEST_NUMBER = "Max Test Number per Component";

	protected static final String ANDROID_SDK = "ANDROID SDK";
	protected static final String APPLICATION_PROJECT = "APPLICATION PROJECT";
	protected static final String TESTPPROJECT = "TEST PROJECT";
	protected static final String TESTERPPROJECT = "TESTER PROJECT";

	public static final String OPEN_SDK_FILE = "Open Sdk";
	public static final String OPEN_STRING_FILE = "Open Project";
	public static final String OPEN_TEST_PROJECT_FILE = "Open Test Project";
	public static final String OPEN_TESTER_PROJECT_FILE = "Open Tester Project";

	public static final String GENERATE = "Generate";
	public static final String SHOWRESULT = "Showresult";
	public static final String RESET = "Reset";
	public static final String INSTALL = "Clean/Build/Install";
	public static final String LAUNCH = "Launch Testing to Device";
	protected JFileChooser chooser;

	@Override
	public void update(Observable arg0, Object o) {

	}

}
