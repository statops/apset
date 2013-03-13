/**
 * 
 */
package fr.openium.mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class Controller implements ActionListener, FocusListener {
	private Model mdl;

	public Controller(Model mdl) {
		this.mdl = mdl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("btn")) {
			JButton b = (JButton) e.getSource();
			if (b.getName().equals(MainPanel.OPEN_SDK_FILE)) {
				System.out.println("Open Android Manifest File");
				mdl.openfile(MainPanel.OPEN_SDK_FILE);
			}
			if (b.getName().equals(MainPanel.OPEN_STRING_FILE)) {
				System.out.println("Open String File");
				mdl.openfile(MainPanel.OPEN_STRING_FILE);
			}
			if (b.getName().equals(MainPanel.OPEN_TEST_PROJECT_FILE)) {
				System.out.println("Open test project File");
				mdl.openfile(MainPanel.OPEN_TEST_PROJECT_FILE);
			}
			if (b.getName().equals(MainPanel.OPEN_TESTER_PROJECT_FILE)) {
				System.out.println("Open tester project File");
				mdl.openfile(MainPanel.OPEN_TESTER_PROJECT_FILE);
			}
			
			if (b.getName().equals(MainPanel.GENERATE)) {
				Run generate = new Run(MainPanel.GENERATE);
				generate.start();
			}
			if (b.getName().equals(MainPanel.SHOWRESULT)) {
				mdl.showResult();
			}
			if (b.getName()
					.equals(MainPanel.INSTALL)) {
				Run install = new Run(MainPanel.INSTALL);
				 install.start();
			}
			if (b.getName().equals(MainPanel.RESET)) {
				mdl.reset();
			}
			if (b.getName().equals(MainPanel.LAUNCH)) {
				mdl.launchTesting();
			}
		}

		if (e.getActionCommand().contains("check")) {

			JCheckBox check = (JCheckBox) e.getSource();
			String nom = e.getActionCommand().split(";")[1];

			if (check.isSelected()) {
				mdl.setVulnerabiltyType(nom);
			} else
				mdl.isUnchecked(nom);

		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		JTextField txt = (JTextField) arg0.getSource();

		if (txt.getName().equals(MainPanel.MAX_TEST_NUMBER)) {
			mdl.setNbMaxTest(txt.getText());
		}
	}

	class Run extends Thread {
		private String mName;

		public Run(String name) {
			mName = name;
		}

		public void run() {
			if (mName.equals(MainPanel.GENERATE)) {
				try {
					mdl.generate();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			if (mName.equals(MainPanel.INSTALL)) {
				if (mdl.getGenerateStatus()) {
					mdl.installAll();
				}
			}

		}

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}
}
