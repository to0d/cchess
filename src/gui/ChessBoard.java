package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.ChessDirection;
import model.ChessLocation;
import model.ChessPiece;
import model.ChessState;
import model.ChessStep;
import model.Config;
import model.Side;
import node.analyse.PieceAnalyse;

public final class ChessBoard extends JPanel {

	private static final long serialVersionUID = 1L;

	private static Config config = Config.getInstance();

	private ChessState state = new ChessState();

	BottomShape bottomShape = new BottomShape();

	MyShape[][] pieceShapes = new MyShape[ChessState.X_LENGTH][ChessState.Y_LENGTH];

	private FocusPieceShape focusPieceShape;

	Collection<MyShape> helpInfs = new LinkedList<MyShape>();

	private ShowFocus showFocus;

	boolean showHelp = true;

	boolean showProtect = false;

	public ChessBoard() {

		setSize(config.getCaseSize() * ChessState.X_LENGTH + 2 * config.getBorderX(),
				config.getCaseSize() * ChessState.Y_LENGTH + 2 * config.getBorderY());

		setBorder(new TitledBorder("∆Â≈Ã"));

		makePieceShape();

		setVisible(true);

	}

	public static void main(String[] args) {

		JFrame fram = new JFrame();

		final ChessBoard board = new ChessBoard();

		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fram.setSize(board.getWidth(), board.getHeight() + 30);
		fram.add(board, BorderLayout.CENTER);
		fram.setLocation(300, 100);
		fram.setVisible(true);

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

	}

	private void makePieceShape() {

		for (ChessLocation location : ChessState.getAllLocations())

			setPieceShape(location, new PieceShape(state.getPiece(location), location));

	}

	public ChessLocation getFocusLocation() {
		return focusPieceShape.getLocation();
	}

	private MyShape getPieceShape(ChessLocation location) {
		return pieceShapes[location.getAbsoluteX()][location.getAbsoluteY()];
	}

	private void setPieceShape(ChessLocation location, MyShape myShape) {
		pieceShapes[location.getAbsoluteX()][location.getAbsoluteY()] = myShape;
	}

	public void erase(ChessLocation location) {
		if (!location.outofBound()) {
			state.setPiece(location, null);
			setPieceShape(location, new PieceShape(null, location));
			repaintArea(location);
		}

	}

	public void paintComponent(Graphics g) {

		bottomShape.draw(g);

		for (ChessLocation location : ChessState.getAllLocations())
			getPieceShape(location).draw(g);

		if (showHelp) {

			for (MyShape helpInf : helpInfs) {

				helpInf.draw(g);
			}
		}

		drawFocus();

	}

	public static ChessLocation getLocationFromPoint(int x, int y) {
		int casesize = config.getCaseSize();
		return ChessLocation.newInstance((x - config.getBorderX()) / casesize, (y - config.getBorderY()) / casesize);
	}

	public void showHelp(boolean show) {
		showHelp = show;
	}

	public boolean isNull(ChessLocation l) {
		return state.getPiece(l) == null;
	}

	public void setPiece(ChessLocation location, ChessPiece piece) {
		state.setPiece(location, piece);
		setPieceShape(location, new PieceShape(piece, location));

		repaintArea(location);
	}

	public boolean goByStep(ChessStep step) {

		if (!step.isWrong()) {

			setPiece(step.toLocation, state.getPiece(step.fromLocation));
			setPiece(step.fromLocation, null);

			setFocusPiece(step.toLocation);

			return true;
		} else
			return false;
	}

	public void clearInf() {

		Iterator<MyShape> iter = helpInfs.iterator();

		while (iter.hasNext()) {
			ChessLocation location = iter.next().getLocation();
			repaintArea(location);

		}

		helpInfs = new LinkedList<MyShape>();

	}

	public ChessLocation getFocusPiece() {
		return focusPieceShape.getLocation();
	}

	private void repaintArea(ChessLocation location) {

		Graphics2D g2d = (Graphics2D) getGraphics().create();

		int casesize = config.getCaseSize();

		g2d.clearRect(casesize * location.getAbsoluteX() + config.getBorderX(),
				casesize * location.getAbsoluteY() + config.getBorderY(), casesize, casesize);

		g2d.setClip(new Rectangle2D.Float(casesize * location.getAbsoluteX() + config.getBorderX(),
				casesize * location.getAbsoluteY() + config.getBorderY(), casesize, casesize));

		bottomShape.draw(g2d);

		getPieceShape(location).draw(g2d);

		if (focusPieceShape != null && getFocusPiece().equal(location))

			drawFocus();
	}

	public boolean setFocusPiece(ChessLocation location) {

		if (location.outofBound())
			return false;

		if (focusPieceShape != null) {
			ChessLocation oldPiece = getFocusPiece();
			if (oldPiece.equal(location))
				return false;
			else {
				focusPieceShape = new FocusPieceShape(location);
				repaintArea(oldPiece);
				clearInf();
			}

		} else {
			focusPieceShape = new FocusPieceShape(location);

		}

		if (state.getPiece(location) != null) {
			PieceAnalyse analyse = new PieceAnalyse(state, getFocusPiece());
			for (ChessStep step : analyse.canEatStep)

				addEatInf(step.toLocation, "");
			for (ChessStep step : analyse.canMoveStep)

				addMoveInf(step.toLocation, "");
			if (showProtect)
				for (ChessStep step : analyse.protectStep) {

					addProtectInf(step.toLocation, "");

				}
		}

		drawFocus();

		return true;
	}

	private synchronized void drawFocus() {

		if (showFocus != null) {
			showFocus.stopMyself();
		}

		if (focusPieceShape == null)
			return;

		showFocus = new ShowFocus(this);
		showFocus.start();
	}

	public void clear() {
		setState(null);
	}

	public void reset() {

		setState(new ChessState());

	}

	public void setState(ChessState s) {

		helpInfs = new LinkedList<MyShape>();

		if (s != null)
			state = s;
		else
			state = new ChessState();
		makePieceShape();
		focusPieceShape = new FocusPieceShape();

		for (ChessLocation location : ChessState.getAllLocations())
			repaintArea(location);

	}

	public ChessState getChessStateCopy() {
		return state.clone();

	}

	private Point getCenterPoint(ChessLocation location) {

		return new Point(
				location.getAbsoluteX() * config.getCaseSize() + config.getBorderX() + config.getCaseSize() / 2,
				location.getAbsoluteY() * config.getCaseSize() + config.getBorderY() + config.getCaseSize() / 2);
	}

	public void stopShowFocus() {
		if (showFocus != null) {

			showFocus.stopMyself();
		}
		showFocus = null;
		focusPieceShape = null;
	}

	public void addEatInf(ChessLocation location, String s) {

		MyShape inf = new HelpInfEat(location, s);
		inf.draw(getGraphics());
		helpInfs.add(inf);

	}

	public void addMoveInf(ChessLocation location, String s) {
		MyShape inf = new HelpInfMove(location, s);
		inf.draw(getGraphics());
		helpInfs.add(inf);

	}

	public void addProtectInf(ChessLocation location, String s) {

		MyShape inf = new HelpInfProtect(location, s);
		inf.draw(getGraphics());
		helpInfs.add(inf);

	}

	private abstract class MyShape {

		Shape myShape = null;

		ChessLocation location;

		Point center;

		int r;

		Color color;

		BasicStroke bs;

		public MyShape(ChessLocation location) {

			this.location = location;

			center = getCenterPoint(location);

			makeShape();
		}

		public abstract void makeShape();

		public void draw(Graphics g) {

			Graphics2D g2d = (Graphics2D) g.create();

			g2d.setColor(color);
			g2d.setStroke(bs);
			g2d.translate(center.x, center.y);

			g2d.draw(myShape);

		}

		public ChessLocation getLocation() {

			return location;
		}

	}

	private class PieceShape extends MyShape {

		ChessPiece piece;

		public PieceShape(ChessPiece apiece, ChessLocation location) {

			super(location);

			piece = apiece;

			r = config.getPieceR();

		}

		public void draw(Graphics g) {

			if (piece == null)
				return;

			Graphics2D g2d = (Graphics2D) g.create();

			Point movePoint = getCenterPoint(location);

			g2d.translate(movePoint.x, movePoint.y);

			g2d.setBackground(ChessBoard.this.getBackground());

			g2d.scale(0.8, 0.8);

			g2d.clearRect(-r / 2, -r / 2, r, r);

			Color currentcolor = null;

			if (piece.whichSide() == Side.∫Ï)
				currentcolor = Color.RED;
			else
				currentcolor = Color.BLACK;

			g2d.setColor(currentcolor);

			BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
			BasicStroke bs2 = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

			g2d.setStroke(bs2);
			g2d.draw(myShape);
			g2d.setStroke(bs1);

			g2d.setFont(new Font("ø¨ÃÂ_GB2312", Font.PLAIN, r - 10));

			g2d.drawString(piece.name().toString(), -15, 10);
			g2d.scale(0.8, 0.8);
			g2d.draw(myShape);

		}

		public void makeShape() {

			int casesize = config.getCaseSize();
			myShape = new Ellipse2D.Float(5 - casesize / 2, 5 - casesize / 2, casesize - 10, casesize - 10);

		}

		public ChessLocation getLocation() {

			return location;
		}

	}

	private class BottomShape {

		Shape myShape = null;

		public BottomShape() {

			makeShape();

		}

		public void makeShape() {

			GeneralPath path = new GeneralPath();

			for (int i = ChessState.Y_BEGIN; i <= ChessState.Y_END; i++) {
				path.append(new Line2D.Float(getCenterPoint(ChessLocation.newInstance(ChessState.X_BEGIN, i)),
						getCenterPoint(ChessLocation.newInstance(ChessState.X_END, i))), false);
			}

			for (int i = ChessState.X_BEGIN; i <= ChessState.X_END; i++) {
				path.append(new Line2D.Float(getCenterPoint(ChessLocation.newInstance(i, ChessState.Y_BEGIN)),
						getCenterPoint(ChessLocation.newInstance(i, ChessState.Y_BLACK_MAX))), false

				);

				path.append(new Line2D.Float(getCenterPoint(ChessLocation.newInstance(i, ChessState.Y_RED_MIN)),
						getCenterPoint(ChessLocation.newInstance(i, ChessState.Y_END))), false

				);

			}

			ChessLocation cL = Side.∫Ï.getCoreLocation();

			for (ChessDirection dir : ChessDirection.XieDirection)
				path.append(new Line2D.Float(getCenterPoint(cL), getCenterPoint(cL.nextLocation(dir))), false);

			cL = Side.∫⁄.getCoreLocation();

			for (ChessDirection dir : ChessDirection.XieDirection)
				path.append(new Line2D.Float(getCenterPoint(cL), getCenterPoint(cL.nextLocation(dir))), false);

			myShape = path;

		}

		public void draw(Graphics g) {

			int casesize = config.getCaseSize();

			Graphics2D g2d = (Graphics2D) g.create();

			g2d.setColor(Color.pink);

			g2d.draw(myShape);

			BasicStroke bs2 = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
			g2d.setStroke(bs2);//

			Font font = new Font("ø¨ÃÂ_GB2312", Font.PLAIN, casesize - 4);
			g2d.setFont(font);
			g2d.drawString("≥˛∫”  ∫∫ΩÁ", casesize * 2 + 30, casesize + casesize * 5 - 8);

		}

	}

	private class HelpInfEat extends MyShape {

		String text = null;

		public HelpInfEat(ChessLocation location, String s) {

			super(location);

			text = s;

			color = Color.red;
			bs = new BasicStroke(6, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

		}

		public void makeShape() {

			r = 44;

			myShape = new Rectangle2D.Float(-r / 2, -r / 2, r, r);

		}

	}

	private class HelpInfMove extends MyShape {

		String text = null;

		public HelpInfMove(ChessLocation location, String s) {

			super(location);
			this.text = s;
			color = Color.GREEN;
			bs = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

		}

		public void draw(Graphics g) {

			super.draw(g);

			Graphics2D g2d = (Graphics2D) g.create();

			if (text != null) {

				g2d.setColor(Color.BLACK);

				Font font = new Font("Sefif", Font.ITALIC, 14);
				g2d.setFont(font);

				g2d.drawString(text, 0, 0);
			}

		}

		public void makeShape() {
			r = 30;

			myShape = new Ellipse2D.Float(-r / 2, -r / 2, r, r);

		}

	}

	private class HelpInfProtect extends MyShape {

		String text = null;

		public HelpInfProtect(ChessLocation location, String s) {

			super(location);

			this.text = s;

			color = Color.GREEN;

			bs = new BasicStroke(6, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		}

		public void makeShape() {

			r = 44;

			myShape = new Rectangle2D.Float(-r / 2, -r / 2, r, r);

		}

	}

	private class FocusPieceShape extends MyShape {

		public FocusPieceShape(ChessLocation location) {

			super(location);
			r = 40;
		}

		public FocusPieceShape() {

			super(state.getRedΩ´Location());
			r = 40;
		}

		public void draw(Graphics g) {

			Graphics2D g2d = (Graphics2D) (g.create());

			g2d.setColor(Color.BLUE);

			BasicStroke bs = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
			g2d.setStroke(bs);

			g2d.translate(center.x, center.y);

			g2d.draw(myShape);

		}

		public void undraw(Graphics g) {

			Graphics2D g2d = (Graphics2D) g.create();

			g2d.setColor(ChessBoard.this.getBackground());

			BasicStroke bs = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
			g2d.setStroke(bs);

			g2d.translate(center.x, center.y);

			g2d.draw(myShape);
		}

		public ChessLocation getLocation() {
			return location;
		}

		public void makeShape() {

			myShape = new Rectangle2D.Float(-r / 2, -r / 2, r, r);

		}

	}

	private class ShowFocus extends Thread {

		FocusPieceShape focusPieceShape;
		ChessBoard board;
		boolean stop = false;

		ShowFocus(ChessBoard board) {

			this.focusPieceShape = board.focusPieceShape;
			this.board = board;
		}

		public void run() {

			Graphics g = board.getGraphics();

			while (!stop) {

				focusPieceShape.draw(g);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				focusPieceShape.undraw(g);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		public void stopMyself() {
			stop = true;
		}

	}

}
