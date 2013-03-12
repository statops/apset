/**
 * 
 */
package fr.openium.mvc;

import java.awt.Color;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;

	public Window() {
		setBackground(Color.LIGHT_GRAY);
		setTitle("Test Case Generation Tool");
		// setSize(800, 800);
		setResizable(true);
		Model mdl = new Model();
		Controller ctrl = new Controller(mdl);
		addWindowListener(new WindowAdapter(this));
		add(new MainPanel(mdl, ctrl, this));
		setVisible(true);
	}

	public static void main(String[] args) {
		new Window();
	}

	public class WindowAdapter extends java.awt.event.WindowAdapter {
		private Window f;

		public WindowAdapter(Window fenetre) {
			f = fenetre;
		}

		public void windowClosing(WindowEvent e) {
			f.dispose();
		}
	}

}
