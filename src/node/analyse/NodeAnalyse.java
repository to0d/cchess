package node.analyse;

import java.util.Iterator;

import model.ChessLocation;
import model.ChessState;
import model.ChessStep;
import model.Sidable;
import model.Side;
import node.ChessNode;
import node.valueFun.AbstractValueFun;

public class NodeAnalyse {

	PieceAnalyse analyses[][] = new PieceAnalyse[9][10];

	int redStepCount = 0;

	int blackStepCount = 0;

	ChessState state;

	public NodeAnalyse(ChessNode chessNode) {

		ChessNode parentNode = (ChessNode) chessNode.getParent();
		state = chessNode.getState();

		if (parentNode == null) {

			for (ChessLocation location : state.getOnlyPieceLocations()) {
				setPieceAnalyse(location, new PieceAnalyse(state, location));
				if (state.getPiece(location).whichSide() == Side.ºì)
					redStepCount += getPieceAnalyse(location).allStepCount();
				else
					blackStepCount += getPieceAnalyse(location).allStepCount();
			}

		} else { // if (parentNode == null) {

			NodeAnalyse parentAnalyse = parentNode.getStateAnalyse();
			ChessState parentState = parentNode.getState();

			for (ChessLocation location : parentState.getOnlyPieceLocations()) {
				this.setPieceAnalyse(location, parentAnalyse
						.getPieceAnalyse(location));
			}

			ChessStep step = chessNode.getStep();

			this.setPieceAnalyse(step.fromLocation, null);

			this.setPieceAnalyse(step.toLocation, new PieceAnalyse(state,
					step.toLocation));

			for (ChessLocation location : parentState.getOnlyPieceLocations()) {
				ChessStep affectStep_From = new ChessStep(state, location,
						step.fromLocation);
				ChessStep affectStep_To = new ChessStep(state, location,
						step.toLocation);
				if (affectStep_From.isAffected() || affectStep_To.isAffected())
					setPieceAnalyse(location, new PieceAnalyse(state, location));

				if (state.getPiece(location).whichSide() == Side.ºì)
					redStepCount += getPieceAnalyse(location).allStepCount();
				else
					blackStepCount += getPieceAnalyse(location).allStepCount();

			}

		}

	}

	public float getValue(AbstractValueFun myvalue) {
		return 0;
	}
	
	public ChessState getState()
	{
		return state;
	}

	public int getRedStepCount() {
		return this.redStepCount;
	}

	public int getBlackStepCount() {
		return this.blackStepCount;
	}

	public PieceAnalyse getPieceAnalyse(ChessLocation location) {
		return this.analyses[location.getAbsoluteX()][location.getAbsoluteY()];
	}

	private void setPieceAnalyse(ChessLocation location,
			PieceAnalyse pieceAnalyse) {
		analyses[location.getAbsoluteX()][location.getAbsoluteY()] = pieceAnalyse;
	}

	public PieceAnalyse getPieceAnalyse(int x, int y) {
		return this.analyses[x][y];
	}

	class StepIterator implements Iterator<ChessStep>, Sidable {

		NodeAnalyse nodeAnalyse;

		private int count = 0;

		private int totalCount;

		private Iterator<ChessLocation> curIter;

		private Side mySide;

		Iterator<ChessStep> reachIterator;

		StepIterator(NodeAnalyse nodeAnalyse, Side mySide) {
			this.nodeAnalyse = nodeAnalyse;

			this.mySide = mySide;

			if (mySide == Side.ºì)
				totalCount = nodeAnalyse.getRedStepCount();
			else
				totalCount = nodeAnalyse.getBlackStepCount();

			PieceAnalyse ana = null;

			curIter = nodeAnalyse.state.getOnlyPieceLocations().iterator();

			while (curIter.hasNext()) {
				ana = nodeAnalyse.getPieceAnalyse(curIter.next());
				if (ana.whichSide() == mySide) {
					reachIterator = ana.getReachIterator();
					return;
				}
			}

		}

		public boolean hasNext() {

			return (count < totalCount);

		}

		private void moveNext() {

			while (curIter.hasNext()) {
				PieceAnalyse ana = nodeAnalyse.getPieceAnalyse(curIter.next());
				if (ana.whichSide() == mySide) {
					reachIterator = ana.getReachIterator();
					return;
				}
			}

		}

		public ChessStep next() {

			if (!reachIterator.hasNext())

				moveNext();

			if (hasNext()) {

				count++;
				return reachIterator.next();

			}

			return null;

		}

		public void remove() {

		}

		public Side whichSide() {
			// TODO Auto-generated method stub
			return mySide;
		}

	}

	public Iterator<ChessStep> redStepIterator() {

		return new StepIterator(this, Side.ºì);
	}

	public Iterator<ChessStep> blackStepIterator() {

		return new StepIterator(this, Side.ºÚ);
	}

}
