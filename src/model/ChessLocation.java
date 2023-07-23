package model;

public abstract class ChessLocation implements Sidable {

	protected int x;

	protected int y;

	protected ChessLocation(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public int getAbsoluteX() {
		return x;
	}

	public int getAbsoluteY() {
		return y;
	}

	public abstract int getRelativeX();

	public abstract int getRelativeY();

	public static ChessLocation newInstance(int x, int y) {
		if (y <= ChessState.Y_BLACK_MAX)
			return new BlackLocation(x, y);
		else
			return new RedLocation(x, y);
	}

	public ChessLocation newAbsoluteLocation(int x, int y) {
		return newInstance(this.x + x, this.y + y);
	}

	public ChessLocation nextLocation(ChessDirection dir) {
		return ChessLocation.newInstance(x + dir.addx, y + dir.addy);
	}

	public static ChessLocation newInstance(int x, int y, Side side) {
		if (side == Side.ºì) {
			return new RedLocation(x + ChessState.X_BEGIN, ChessState.Y_END - y);
		} else {
			return new BlackLocation(ChessState.X_END - x, y + ChessState.Y_BEGIN);
		}
	}

	public boolean equal(ChessLocation location) {
		if (x == location.x && y == location.y)
			return true;
		else
			return false;
	}

	public abstract ChessLocation copy();

	public String toString() {
		return "x=" + x + "y=" + y;
	}

	public boolean outofBound() {
		if (x < ChessState.X_BEGIN || y < ChessState.Y_BEGIN || x > ChessState.X_END || y > ChessState.Y_END)
			return true;
		return false;
	}

	public int squareDistance(ChessLocation location) {
		int addx = x - location.x;
		int addy = y - location.y;
		return addx * addx + addy * addy;
	}

	public abstract Side whichSide();

}

class RedLocation extends ChessLocation {

	public RedLocation(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Side whichSide() {
		// TODO Auto-generated method stub
		return Side.ºì;
	}

	@Override
	public ChessLocation copy() {
		// TODO Auto-generated method stub
		return new RedLocation(x, y);
	}

	@Override
	public int getRelativeX() {
		// TODO Auto-generated method stub
		return x - ChessState.X_BEGIN;
	}

	@Override
	public int getRelativeY() {
		// TODO Auto-generated method stub
		return ChessState.Y_END - y;
	}

}

class BlackLocation extends ChessLocation {

	public BlackLocation(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Side whichSide() {
		// TODO Auto-generated method stub
		return Side.ºÚ;
	}

	@Override
	public ChessLocation copy() {
		// TODO Auto-generated method stub
		return new BlackLocation(x, y);
	}

	@Override
	public int getRelativeX() {
		// TODO Auto-generated method stub
		return ChessState.X_END - x;
	}

	@Override
	public int getRelativeY() {
		// TODO Auto-generated method stub
		return y - ChessState.Y_BEGIN;
	}

}
