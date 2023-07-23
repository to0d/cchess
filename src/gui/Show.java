package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Show {

	public static void inFrame(JPanel jp, int width, int height) {

		JFrame frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(jp, BorderLayout.CENTER);
		frame.setSize(width, height);
		frame.setVisible(true);
	}
} // /:~
