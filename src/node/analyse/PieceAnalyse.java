package node.analyse;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import model.ChessLocation;
import model.ChessPiece;
import model.ChessState;
import model.ChessStep;
import model.Sidable;
import model.Side;

public class PieceAnalyse implements Sidable {

	public Collection<ChessStep> canMoveStep = new LinkedList<ChessStep>();

	public Collection<ChessStep> canEatStep = new LinkedList<ChessStep>();

	public Collection<ChessStep> protectStep = new LinkedList<ChessStep>();

	public ChessPiece piece;

	Side myside;

	public PieceAnalyse(ChessState state, ChessLocation location) {

		piece = state.getPiece(location);
		if (piece != null) {
			myside = piece.whichSide();
			analyse(state, location);
		}

	}

	private void analyse(ChessState state, ChessLocation fromLocation) {

		Collection<ChessStep> allStep = new LinkedList<ChessStep>();

		allStep=piece.name().getPossibleSteps(state, fromLocation);
	


		for (ChessStep step : allStep) {

			if (!step.notMeetRule()) {

				if (step.isAttack()) {

					if (step.isEatSelf())
						protectStep.add(step);
					else
						canEatStep.add(step);
				} else {
					canMoveStep.add(step);
				}

			}

		}

		protectStep = Collections.unmodifiableCollection(protectStep);
		canEatStep = Collections.unmodifiableCollection(canEatStep);
		canMoveStep = Collections.unmodifiableCollection(canMoveStep);

		// moveNum = canMoveStep.size();

	}

	class ReachStepCollection implements Iterator<ChessStep> {

		Iterator<ChessStep> canMoveIterator;

		Iterator<ChessStep> canEatIterator;

		ReachStepCollection(PieceAnalyse analyse) {

			canMoveIterator = analyse.canMoveStep.iterator();
			canEatIterator = analyse.canEatStep.iterator();
		}

		public boolean hasNext() {
			if (canMoveIterator.hasNext() || canEatIterator.hasNext())
				return true;
			return false;
		}

		public ChessStep next() {

			if (canMoveIterator.hasNext())
				return canMoveIterator.next();
			else if (canEatIterator.hasNext()) {
				return canEatIterator.next();
			} else
				return null;
		}

		public void remove() {
			// TODO Auto-generated method stub

		}

	}

	public Iterator<ChessStep> getReachIterator() {

		return new ReachStepCollection(this);
	}

	public int MoveStepCount() {
		return canMoveStep.size();
	}

	public int EatStepCount() {
		return canEatStep.size();
	}

	public int allStepCount() {
		return this.canEatStep.size() + this.canMoveStep.size();
	}

	public Side whichSide() {

		return myside;
	}

}
