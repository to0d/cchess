package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class ChessState implements Cloneable {

	private ChessPiece totalpiece[][];

	public final static int X_BEGIN = 0;
	public final static int X_END = 8;
	public final static int X_LENGTH = 9;

	public final static int Y_BEGIN = 0;
	public final static int Y_END = 9;
	public final static int Y_LENGTH = 10;

	public final static int Y_BLACK_MAX = 4;
	public final static int Y_RED_MIN = 5;

	public final static int X_MIDDLE = 4;
	public final static int Y_BLACK_CORE = 1;
	public final static int Y_RED_CORE = 8;

	Collection<ChessLocation> pieceLocations = new LinkedList<ChessLocation>();
	static Collection<ChessLocation> allLocations = new ArrayList<ChessLocation>();
	static {
		for (int i = ChessState.X_BEGIN; i <= ChessState.X_END; i++)
			for (int j = ChessState.Y_BEGIN; j <= ChessState.Y_END; j++)
				allLocations.add(ChessLocation.newInstance(i, j));

		allLocations = Collections.unmodifiableCollection(allLocations);

	}
	boolean updating = false;

	private ChessLocation red将Location;
	private ChessLocation black将Locaiton;

	public ChessState() {

		totalpiece = init_normal();
		updating = true;
	}

	public ChessStep getStep(ChessLocation fromLocation, ChessLocation toLocation) {
		return new ChessStep(this, fromLocation, toLocation);
	}

	public void setPiece(ChessLocation location, ChessPiece piece) {
		totalpiece[location.x][location.y] = piece;
		updating = true;
	}

	public ChessPiece getPiece(ChessLocation location) {

		return totalpiece[location.x][location.y];
	}

	public Collection<ChessLocation> getOnlyPieceLocations() {
		if (updating)
			reCollectPieceLocations();
		return pieceLocations;
	}

	public static Collection<ChessLocation> getAllLocations() {
		return allLocations;
	}

	private ChessPiece getPiece(int x, int y) {
		return totalpiece[x][y];
	}

	private void reCollectPieceLocations() {
		pieceLocations = new LinkedList<ChessLocation>();

		for (ChessLocation location : getAllLocations()) {
			ChessPiece piece = getPiece(location);
			if (piece != null) {
				pieceLocations.add(location);
				if (piece.name() == ChessName.将) {
					if (piece.whichSide() == Side.红)
						red将Location = location;
					else
						black将Locaiton = location;
				}
			}
		}
		updating = false;
	}

	public ChessLocation get将Location(Side side) {
		if (updating)
			reCollectPieceLocations();
		if (side == Side.红)
			return red将Location;
		else
			return black将Locaiton;
	}

	public ChessLocation getRed将Location() {
		if (updating)
			reCollectPieceLocations();
		return red将Location;
	}

	public ChessLocation getBlack将Locaiton() {
		if (updating)
			reCollectPieceLocations();
		return black将Locaiton;
	}

	static public ChessPiece[][] init_normal() {

		ChessPiece temps[][] = {
				{ new ChessPiece(ChessName.车, Side.黑), null, null, new ChessPiece(ChessName.兵, Side.黑), null, null,
						new ChessPiece(ChessName.兵, Side.红), null, null, new ChessPiece(ChessName.车, Side.红), },
				{ new ChessPiece(ChessName.马, Side.黑), null, new ChessPiece(ChessName.炮, Side.黑), null, null, null,
						null, new ChessPiece(ChessName.炮, Side.红), null, new ChessPiece(ChessName.马, Side.红), },
				{ new ChessPiece(ChessName.相, Side.黑), null, null, new ChessPiece(ChessName.兵, Side.黑), null, null,
						new ChessPiece(ChessName.兵, Side.红), null, null, new ChessPiece(ChessName.相, Side.红), },
				{ new ChessPiece(ChessName.士, Side.黑), null, null, null, null, null, null, null, null,
						new ChessPiece(ChessName.士, Side.红), },
				{ new ChessPiece(ChessName.将, Side.黑), null, null, new ChessPiece(ChessName.兵, Side.黑), null, null,
						new ChessPiece(ChessName.兵, Side.红), null, null, new ChessPiece(ChessName.将, Side.红), },
				{ new ChessPiece(ChessName.士, Side.黑), null, null, null, null, null, null, null, null,
						new ChessPiece(ChessName.士, Side.红), },
				{ new ChessPiece(ChessName.相, Side.黑), null, null, new ChessPiece(ChessName.兵, Side.黑), null, null,
						new ChessPiece(ChessName.兵, Side.红), null, null, new ChessPiece(ChessName.相, Side.红), },
				{ new ChessPiece(ChessName.马, Side.黑), null, new ChessPiece(ChessName.炮, Side.黑), null, null, null,
						null, new ChessPiece(ChessName.炮, Side.红), null, new ChessPiece(ChessName.马, Side.红), },
				{ new ChessPiece(ChessName.车, Side.黑), null, null, new ChessPiece(ChessName.兵, Side.黑), null, null,
						new ChessPiece(ChessName.兵, Side.红), null, null, new ChessPiece(ChessName.车, Side.红), },

		};

		return temps;
	}

	public ChessState getNextState(ChessStep step) {

		ChessState state = this.clone();
		if (!step.isWrong()) {

			state.setPiece(step.toLocation, step.fromPiece);
			state.setPiece(step.fromLocation, null);

		}
		state.reCollectPieceLocations();

		return state;

	}

	public void clear() {
		totalpiece = new ChessPiece[X_LENGTH][Y_LENGTH];
		pieceLocations = new LinkedList<ChessLocation>();
		updating = false;
	}

	public String toString() {
		String s = "";

		for (int i = X_BEGIN; i <= X_END; i++) {
			for (int j = Y_BEGIN; j <= Y_END; j++) {

				if (getPiece(i, j) == null)
					s += "    ";
				else
					s += getPiece(i, j).toString();
			}
			s += "\n";
		}
		return s;
	}

	public static void main(String[] args) {

		System.out.print(new ChessState());

	}

	public ChessState clone() {
		try {
			ChessState newState = (ChessState) super.clone();
			newState.updating = false;

			return newState;

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
