package app;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JApplet;

import gui.ChessBoard;
import model.ChessLocation;
import model.ChessStep;

public class BoardApplet extends JApplet {

	private static final long serialVersionUID = 8972800334330227253L;

	public void init() {

		final ChessBoard board = new ChessBoard();

		board.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				int x = e.getX();
				int y = e.getY();

				ChessLocation location = ChessBoard.getLocationFromPoint(x, y);

				if (e.getModifiers() == InputEvent.BUTTON1_MASK) {

					board.setFocusPiece(location);

				} else if (e.getModifiers() == InputEvent.BUTTON3_MASK) {

					ChessLocation fromeLocation = board.getFocusLocation();

					ChessStep step = new ChessStep(board.getChessStateCopy(), fromeLocation, location);

					if (!step.isWrong()) {
						board.goByStep(step);
					}

				}

			}

		});

		add(board);
	}
}
