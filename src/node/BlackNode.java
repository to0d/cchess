package node;

import java.util.Iterator;

import model.ChessState;
import model.ChessStep;
import model.Side;




public class BlackNode extends ChessNode {

	public BlackNode(ChessState astate) {
		super(astate);
		// TODO Auto-generated constructor stub
	}

	public BlackNode(ChessStep step, ChessNode parent) {
		super(step, parent);
	}

	
	public Side whichSide() {
		// TODO Auto-generated method stub
		return Side.ºÚ;
	}

	

	@Override
	public ChessNode makeChildNode(ChessStep step) {
		
		return  new RedNode(step,this);
	}

	@Override
	public Iterator<ChessStep> childStepIterator() {
		// TODO Auto-generated method stub
		return nodeAnalyse.blackStepIterator();
	}	

}
