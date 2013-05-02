/**
 * 
 */
package fr.openium.mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import fr.openium.Junit.pairwise.PairWise;

public class MainPanel extends AbstractPanel {
	private static final long serialVersionUID = 1L;

	private Window mWindow;

	public MainPanel(Model model, Controller ctrl, JFrame window) {
		super();
		chooser = new JFileChooser(".");
		mBouttons = new ArrayList<JButton>();
		this.mdl = model;
		this.ctrl = ctrl;
		mdl.addObserver(this);
		this.mWindow = (Window) window;
		add(childPnl(), BorderLayout.SOUTH);
	}

	private JPanel childPnl() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(30, 1));
		panel.add(sdkPanel());
		panel.add(selectedFile(ANDROID_SDK), BorderLayout.CENTER);
		panel.add(projectPanel());
		panel.add(selectedFile(APPLICATION_PROJECT), BorderLayout.CENTER);
		panel.add(testProjectPanel());
		panel.add(selectedFile(TESTPPROJECT), BorderLayout.CENTER);
		panel.add(testerProjectPanel());
		panel.add(maxTestpanel());
		panel.add(vulnpanel(AVAILABILITY));
		panel.add(vulnpanel(INTEGRITY));
		panel.add(generatebuttontnPnl());
		panel.add(installAllbuttontnPnl());
		panel.add(launchTestingbuttontnPnl());
		panel.add(showResultbuttontnPnl());
		panel.add(verdict());
		panel.add(resetbutton());
		return panel;
	}

	private Component testerProjectPanel() {
		JPanel testerprojectpnl = new JPanel();
		testerprojectpnl.setLayout(new GridLayout(0, 2));
		testerprojectpnl.add(new JLabel(TESTERPPROJECT));
		testerprojectpnl.add(newButton(OPEN_TESTER_PROJECT_FILE));
		return testerprojectpnl;
	}

	private Component verdict() {
		JPanel av = new JPanel();
		av.setLayout(new GridLayout(0, 2));
		av.add(vul());
		av.add(nVul());
		return av;
	}

	private Component vulnpanel(String name) {
		JPanel av = new JPanel();
		av.setLayout(new GridLayout(0, 2));
		av.add(new JLabel(name));
		av.add(checkBox(name));
		return av;
	}

	private Component resetbutton() {
		JPanel panBtn = new JPanel();
		panBtn.add(newButton(RESET));
		return panBtn;
	}

	private Component launchTestingbuttontnPnl() {

		JPanel panBtn = new JPanel();
		panBtn.setLayout(new GridLayout(0, 2));
		panBtn.add(new JLabel("Launch in the Emulator"));
		panBtn.add(newButton(LAUNCH));
		return panBtn;
	}

	private Component installAllbuttontnPnl() {
		JPanel panBtn = new JPanel();
		panBtn.setLayout(new GridLayout(0, 2));
		panBtn.add(new JLabel("Install to Android Device"));
		panBtn.add(newButton(INSTALL));
		return panBtn;

	}

	private Component checkBox(String nom) {
		JCheckBox c = new JCheckBox();
		c.addActionListener(ctrl);
		c.setActionCommand("check;" + nom);
		if (nom.equals(AVAILABILITY))
			mAvailability = c;
		else if (nom.equals(INTEGRITY))
			mIntegrity = c;
		return c;
	}

	private Component sdkPanel() {
		JPanel manifestpnl = new JPanel();
		manifestpnl.setLayout(new GridLayout(0, 2));
		manifestpnl.add(new JLabel(ANDROID_SDK), BorderLayout.WEST);
		manifestpnl.add(newButton(OPEN_SDK_FILE), BorderLayout.EAST);
		return manifestpnl;
	}

	private Component testProjectPanel() {
		JPanel testprojectpnl = new JPanel();
		testprojectpnl.setLayout(new GridLayout(0, 2));
		testprojectpnl.add(new JLabel(TESTPPROJECT));
		testprojectpnl.add(newButton(OPEN_TEST_PROJECT_FILE));
		return testprojectpnl;

	}

	private JLabel selectedFile(String name) {

		if (name.equals(JUNITPATHFILE)) {
			mRunShow = new JLabel(name);
			mRunShow.setText(JUNITPATHFILE);
			return mRunShow;
		} else if (name.equals(ANDROID_SDK)) {
			sdktlbl = new JLabel(name);
			sdktlbl.setText("No sdk  selected.");
			return sdktlbl;
		} else if (name.equals(APPLICATION_PROJECT)) {
			projectlbl = new JLabel(name);
			projectlbl.setText("No project selected.");
			return projectlbl;
		} else {
			testprojectlbl = new JLabel(name);
			testprojectlbl.setText("No test project  selected.");
			return testprojectlbl;

		}

	}

	private JLabel output() {

		mOutPuFiles = new JLabel();
		mOutPuFiles.setText("OutPut Files");
		return mOutPuFiles;

	}

	private JLabel vul() {

		mVul = new JLabel();
		mVul.setText("Vul Number");
		return mVul;

	}

	private JLabel nVul() {

		mNVul = new JLabel();
		mNVul.setText("Nvul Number");
		return mNVul;

	}

	private Component projectPanel() {
		JPanel stringpnl = new JPanel();
		stringpnl.setLayout(new GridLayout(0, 2));
		stringpnl.add(new JLabel(APPLICATION_PROJECT));
		stringpnl.add(newButton(OPEN_STRING_FILE));
		return stringpnl;
	}

	private Component maxTestpanel() {
		JPanel panTest = new JPanel();
		panTest.setLayout(new GridLayout(0, 2));
		panTest.add(new JLabel(MAX_TEST_NUMBER));
		panTest.add(textField(MAX_TEST_NUMBER));
		return panTest;
	}

	private Component generatebuttontnPnl() {
		JPanel panBtn = new JPanel();
		panBtn.setLayout(new GridLayout(0, 2));
		panBtn.add(selectedFile(JUNITPATHFILE));
		panBtn.add(newButton(GENERATE));
		mBouttons.get(4).setEnabled(false);
		return panBtn;
	}

	private Component showResultbuttontnPnl() {
		JPanel panBtn = new JPanel();
		panBtn.setLayout(new GridLayout(0, 2));

		panBtn.add(new JLabel(""));
		panBtn.add(newButton(SHOWRESULT));
		return panBtn;

	}

	private Component textField(String name) {
		JTextField field = new JTextField(name);
		field.addActionListener(ctrl);
		field.setName(name);
		field.addFocusListener(ctrl);
		if (name.equals(MAX_TEST_NUMBER)) {
			mTestNumber = field;
			field.setEnabled(true);
		}
		return field;
	}

	private JButton newButton(String name) {
		JButton button = new JButton(name);
		button.addActionListener(ctrl);
		button.setName(name);
		button.setActionCommand("btn");
		if (name.equals(OPEN_TESTER_PROJECT_FILE) || name.equals(OPEN_SDK_FILE)
				|| name.equals(OPEN_STRING_FILE)
				|| name.equals(OPEN_TEST_PROJECT_FILE) || name.equals(RESET)) {
			button.setEnabled(true);
		} else {
			button.setEnabled(true);
		}
		mBouttons.add(button);
		return button;
	}

	private JFileChooser chooser;

	@Override
	public void update(Observable arg0, Object o) {
		System.out.println("Update");
		String nom = (String) o;

		if (nom == null) {
			nom = "";
		}

		if (nom.equals(MainPanel.OPEN_SDK_FILE)) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(null);
			if (chooser.getSelectedFile() != null) {
				System.out.println(chooser.getSelectedFile());
				sdktlbl.setText("Selected file : "
						+ chooser.getSelectedFile().getPath());
				loadSdk(chooser.getSelectedFile());
				mBouttons.get(0).setEnabled(false);
			}

		}
		if (nom.equals(MainPanel.OPEN_STRING_FILE)) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(null);
			/*
			 * chooser.setCurrentDirectory(new File(
			 * "/Users/Stassia/Desktop/dossier/TEST/droid"));
			 */
			if (chooser.getSelectedFile() != null) {
				System.out.println(chooser.getSelectedFile());
				projectlbl.setText("Selected file : "
						+ chooser.getSelectedFile().getPath());
				loadProject(chooser.getSelectedFile());
				mBouttons.get(1).setEnabled(false);
			}
		}
		if (nom.equals(OPEN_TEST_PROJECT_FILE)) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(null);
			/*
			 * chooser.setCurrentDirectory(new File(
			 * "/Users/Stassia/Desktop/dossier/TEST/droid"));
			 */
			if (chooser.getSelectedFile() != null) {
				System.out.println(chooser.getSelectedFile());
				testprojectlbl.setText("Selected file : "
						+ chooser.getSelectedFile().getPath());
				loadtestProjectFile(chooser.getSelectedFile());
				mBouttons.get(2).setEnabled(false);
			}

		}

		if (nom.equals(OPEN_TESTER_PROJECT_FILE)) {
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(null);
			/*
			 * chooser.setCurrentDirectory(new File(
			 * "/Users/Stassia/Documents/workspace/"));
			 */
			if (chooser.getSelectedFile() != null) {
				System.out.println(chooser.getSelectedFile());
				testprojectlbl.setText("Selected file : "
						+ chooser.getSelectedFile().getPath());
				loadtesterProjectFile(chooser.getSelectedFile());
				mBouttons.get(3).setEnabled(false);
			}

		}

		if (nom.equals(MainPanel.JUNITPATHFILE)) {
			if (!mdl.mGenerateStatus) {
				mRunShow.setForeground(Color.red);

			} else {
				mRunShow.setForeground(Color.BLACK);
			}
			mRunShow.setText(mdl.getMessage());
			mdl.setMessage("");
			mBouttons.get(4).setEnabled(false);
			mBouttons.get(5).setEnabled(true);
		}
		if (nom.equals(MAX_TEST_NUMBER)) {

			int nb = Integer.parseInt(mTestNumber.getText());
			PairWise.setNbTestMax(nb);

		}
		if (nom.equals(SHOWRESULT)) {
			if (mdl.getEmulatorStatus()) {
				mVul.setText("No result ");
				mNVul.setVisible(false);
			} else {
				mNVul.setVisible(true);
				mVul.setText("Vul:  " + mdl.getVulNumber());
				mNVul.setText("Nvul:  " + mdl.getNvulNumber());
			}
		}
		if (nom.equals(INSTALL)) {
			if (!mdl.isInstalled()) {
				mVul.setForeground(Color.RED);
				mBouttons.get(5).setEnabled(false);
				mBouttons.get(6).setEnabled(false);
			} else {
				mVul.setBackground(Color.BLACK);
				mBouttons.get(5).setEnabled(false);
				mBouttons.get(6).setEnabled(true);
			}
			mVul.setText(mdl.getMessage());
			mdl.setMessage("");
			mNVul.setVisible(false);

		}
		if (nom.equals(LAUNCH)) {
			mVul.setBackground(Color.BLACK);
			mNVul.setVisible(true);
			mNVul.setText("Launch intent ...");
			mBouttons.get(6).setEnabled(false);
			mBouttons.get(7).setEnabled(true);
		}
		if (nom.equals(RESET)) {

			mNVul.setVisible(true);
			mVul.setVisible(true);

			for (JButton b : mBouttons) {
				b.setEnabled(true);

			}

			mTestNumber.removeFocusListener(ctrl);
			mTestNumber.setText(MAX_TEST_NUMBER);
			mTestNumber.addFocusListener(ctrl);

			sdktlbl.setText("");
			projectlbl.setText("");
			testprojectlbl.setText("");

			mRunShow.setText("");
			mVul.setText("");
			mNVul.setText("");
			mBouttons.get(4).setEnabled(false);

			mAvailability.removeActionListener(ctrl);
			if (mAvailability.isSelected())
				mAvailability.setSelected(false);
			mAvailability.addActionListener(ctrl);

			mIntegrity.removeActionListener(ctrl);
			if (mIntegrity.isSelected())
				mIntegrity.setSelected(false);
			mIntegrity.addActionListener(ctrl);

			mBouttons.get(6).setEnabled(false);
			mBouttons.get(5).setEnabled(false);
			mBouttons.get(7).setEnabled(false);

		}
		if (nom.equals(AVAILABILITY)) {
			mIntegrity.setEnabled(false);
			mBouttons.get(4).setEnabled(true);
		}

		if (nom.equals(INTEGRITY)) {
			mAvailability.setEnabled(false);
			mBouttons.get(4).setEnabled(true);
		}

		if (nom.equals("N" + AVAILABILITY)) {
			mIntegrity.setEnabled(true);
			mBouttons.get(4).setEnabled(false);
		}

		if (nom.equals("N" + INTEGRITY)) {
			mAvailability.setEnabled(true);
			mBouttons.get(4).setEnabled(false);
		}
	}

	private void loadtesterProjectFile(File selectedFile) {
		mdl.setTestingToolPath(selectedFile.getPath());

	}

	private void loadSdk(File selectedFile) {
		mdl.setSdk(selectedFile.getPath());
	}

	private void loadProject(File selectedFile) {
		mdl.setProjectPath(selectedFile.getPath());
		mdl.setManifestXMLFile(new File(selectedFile.getPath()
				+ "/AndroidManifest.xml"));
		mdl.setStringXMLFile(new File(selectedFile.getPath()
				+ "/res/values/strings.xml"));
	}

	private void loadtestProjectFile(File selectedFile) {
		mdl.setTestProject(selectedFile);
	}

	public static class FilterFile extends FileFilter {

		@Override
		public boolean accept(File fich) {
			boolean reponse = fich.isDirectory();
			if (!reponse) {
				int pos = fich.getAbsolutePath().lastIndexOf(".");
				String extension = fich.getAbsolutePath().substring(pos + 1);
				if (extension.equals("xml"))
					reponse = true;
			}
			return reponse;
		}

		@Override
		public String getDescription() {
			return ".xml";
		}

	}

}
