package node.valueFun;

import model.ChessLocation;
import model.ChessPiece;
import model.ChessStep;
import model.Side;
import node.ChessNode;
import node.analyse.NodeAnalyse;
import node.analyse.PieceAnalyse;

public class anaValue_11_29 extends AbstractValueFun {

	class PieceWeight {

		final static float lifePercent = 1;
		final static float attackPercent = 1;
		final static float defencePercent = 1;
		final static float movePercent = 1;

		final float lifeWeight;
		final float attackWeight;
		final float defenceWeight;
		final float moveWeight;

		public PieceWeight(float life, float attackWeight, float defenceWeight,
				float moveWeight) {
			lifeWeight = life;
			this.attackWeight = attackWeight;
			this.defenceWeight = defenceWeight;
			this.moveWeight = moveWeight;

		}

	}

	PieceWeight Weight�� = new PieceWeight(1000, -100, 0, 1);
	PieceWeight Weightʿ = new PieceWeight(5, -8, 8, 1);
	PieceWeight Weight�� = new PieceWeight(5, -8, 8, 1);
	PieceWeight Weight�� = new PieceWeight(15, -10, 10, 1);
	PieceWeight Weight�� = new PieceWeight(8, -8, 8, 2);
	PieceWeight Weight�� = new PieceWeight(1000, -100, 0, 1);
	PieceWeight Weight�� = new PieceWeight(1, -3, 1, 1);

	int[][] attackNum = new int[9][10];
	int[][] defenceNum = new int[9][10];
	int[][] moveNum = new int[9][10];

	float lifeTotal;

	float attackTotal;

	float defenceTotal;

	float moveTotal;

	@Override
	public AbstractValue getValue(ChessNode node) {
		clear();

		NodeAnalyse nodeAnalyse = node.getStateAnalyse();

		for (ChessLocation location : node.getState().getOnlyPieceLocations()) {
			analysePiece(nodeAnalyse.getPieceAnalyse(location), location);
		}

		return compute(nodeAnalyse);

	}

	private AbstractValue compute(NodeAnalyse nodeAnalyse) {

		for (ChessLocation location : nodeAnalyse.getState()
				.getOnlyPieceLocations()) {
			PieceWeight weight = null;

			ChessPiece piece = nodeAnalyse.getPieceAnalyse(location).piece;
			switch (piece.name().index) {
			case 1: // ��

				weight = Weight��;
				break;

			case 2: // ʿ

				weight = Weightʿ;
				break;

			case 3: // ��

				weight = Weight��;
				break;

			case 4: // ��

				weight = Weight��;
				break;

			case 5: // ��

				weight = Weight��;
				break;

			case 6: // ��

				weight = Weight��;
				break;

			case 7: // ��

				weight = Weight��;
				break;

			}

			int i=location.getAbsoluteX();
			int j=location.getAbsoluteY();
			if (piece.whichSide() == Side.��) {
				lifeTotal += weight.lifeWeight;
				attackTotal += weight.attackWeight * attackNum[i][j];
				defenceTotal += weight.defenceWeight * defenceNum[i][j];
				moveTotal += weight.moveWeight * moveNum[i][j];
			} else {
				lifeTotal -= weight.lifeWeight;
				attackTotal -= weight.attackWeight * attackNum[i][j];
				defenceTotal -= weight.defenceWeight * defenceNum[i][j];
				moveTotal -= weight.moveWeight * moveNum[i][j];
			}

		}

		return new DefaultValue(lifeTotal * PieceWeight.lifePercent
				+ attackTotal * PieceWeight.attackPercent + defenceTotal
				* PieceWeight.defencePercent + moveTotal
				* PieceWeight.movePercent);
	}

	private void clear() {

		lifeTotal = 0;

		attackTotal = 0;

		defenceTotal = 0;

		moveTotal = 0;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				attackNum[i][j] = 0;
				defenceNum[i][j] = 0;
				moveNum[i][j] = 0;

			}
		}
	}

	private void analysePiece(PieceAnalyse pieceAnalyse, ChessLocation location) {

		for (ChessStep step : pieceAnalyse.canEatStep) {
			attackNum[step.toLocation.getAbsoluteX()][step.toLocation
					.getAbsoluteY()]++;

		}

		for (ChessStep step : pieceAnalyse.protectStep) {
			defenceNum[step.toLocation.getAbsoluteX()][step.toLocation
					.getAbsoluteY()]++;
		}
		moveNum[location.getAbsoluteX()][location.getAbsoluteY()] = pieceAnalyse.canMoveStep
				.size();

	}

}
