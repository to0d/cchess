package model;

public class ChessPiece implements Sidable {

	private Side side;

	private ChessName name;

	public ChessPiece(ChessName name, Side side) {
		this.name = name;
		this.side = side;

	}

	public Side whichSide() {

		return side;
	}

	public ChessName name() {
		return name;
	}

	public String toString()

	{
		if (side == Side.��)
			return "��" + name.toString();
		else
			return "��" + name.toString();
	}

}
