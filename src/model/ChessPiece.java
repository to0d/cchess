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
		if (side == Side.ºì)
			return "ºì" + name.toString();
		else
			return "ºÚ" + name.toString();
	}

}
