package model;

import java.util.Collection;
import java.util.LinkedList;

public class ChessDirection {

	final int addx;
	final int addy;

	public final static ChessDirection REDLeft = new ChessDirection(-1, 0);
	public final static ChessDirection REDRight = new ChessDirection(1, 0);
	public final static ChessDirection REDUp = new ChessDirection(0, -1);
	public final static ChessDirection REDDown = new ChessDirection(0, 1);
	public final static ChessDirection REDLeftUp = new ChessDirection(-1, -1);
	public final static ChessDirection REDLeftDown = new ChessDirection(-1, 1);
	public final static ChessDirection REDRightUp = new ChessDirection(1, -1);
	public final static ChessDirection REDRightDown = new ChessDirection(1, 1);

	public final static ChessDirection BLACKLeft = REDRight;
	public final static ChessDirection BLACKRight = REDLeft;
	public final static ChessDirection BLACKUp = REDDown;
	public final static ChessDirection BLACKDown = REDUp;
	public final static ChessDirection BLACKLeftUp = REDRightDown;
	public final static ChessDirection BLACKLeftDown = REDRightUp;
	public final static ChessDirection BLACKRightUp = REDLeftDown;
	public final static ChessDirection BLACKRightDown = REDLeftUp;

	public final static Collection<ChessDirection> LRUDDirection = new LinkedList<ChessDirection>();
	public final static Collection<ChessDirection> XieDirection = new LinkedList<ChessDirection>();
	public final static Collection<ChessDirection> AllDirection = new LinkedList<ChessDirection>();

	static {
		LRUDDirection.add(ChessDirection.REDLeft);
		LRUDDirection.add(ChessDirection.REDRight);
		LRUDDirection.add(ChessDirection.REDUp);
		LRUDDirection.add(ChessDirection.REDDown);

		XieDirection.add(ChessDirection.REDLeftUp);
		XieDirection.add(ChessDirection.REDLeftDown);
		XieDirection.add(ChessDirection.REDRightUp);
		XieDirection.add(ChessDirection.REDRightDown);

		AllDirection.addAll(LRUDDirection);
		AllDirection.addAll(XieDirection);
	}

	private ChessDirection(int x, int y) {
		addx = x;
		addy = y;
	}

	public static ChessDirection getRelativelyDir(ChessLocation fromLocation, ChessLocation toLocation) {

		int subx = toLocation.x - fromLocation.x;
		int suby = toLocation.y - fromLocation.y;

		if (subx == 0 || suby == 0) {

			if (subx == 0) {
				if (suby > 0)
					return REDDown;
				else if (suby < 0)
					return REDUp;
			} else {
				if (subx > 0)
					return REDRight;
				else if (subx < 0)
					return REDLeft;
			}

			return null;
		} else {
			if (subx == suby) {
				if (subx > 0)
					return REDRightDown;
				else
					return REDLeftUp;
			} else if (subx == -suby) {
				if (subx > 0) {
					return REDRightUp;
				} else
					return REDLeftDown;
			}
			return null;
		}

	}

	public ChessDirection opposite() {
		return getRelativelyDir(ChessLocation.newInstance(0, 0), ChessLocation.newInstance(addx, addy));
	}

}
