package app;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import gui.ChessBoard;
import model.Config;

public class MyApp extends JFrame {

	Config config = Config.getInstance();

	public MyApp() {

		con.setLayout(null);

		con.add(board);
		board.setBounds(0, 0, config.getCaseSize() * 10, config.getCaseSize() * 11);

		TestSet testboard = new TestSet(board);

		con.add(testboard);
		testboard.setBounds(config.getCaseSize() * 10, 0, 200, config.getCaseSize() * 11);

		setSize(width, height);
		setVisible(true);

		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		new MyApp();

	}

	public ChessBoard board = new ChessBoard();

	private final int width = config.getCaseSize() * 10 + 200;

	private final int height = config.getCaseSize() * 11 + 30;

	Container con = getContentPane();

}
