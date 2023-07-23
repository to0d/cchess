package node;

import java.util.Iterator;

import model.ChessState;
import model.ChessStep;
import model.Side;



public class RedNode extends ChessNode {

	public RedNode(ChessState astate) {
		super(astate);
		// TODO Auto-generated constructor stub
	}

	public RedNode(ChessStep step, ChessNode parent) {
		super(step, parent);
	}

	public Side whichSide() {
		// TODO Auto-generated method stub
		return Side.ºì;
	}

	

	@Override
	public ChessNode makeChildNode(ChessStep step) {
		// TODO Auto-generated method stub
		return new BlackNode(step, this);
	}

	@Override
	public Iterator<ChessStep> childStepIterator() {
		// TODO Auto-generated method stub
		return nodeAnalyse.redStepIterator();
	}

}
