package node;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.tree.TreeNode;

import model.ChessState;
import model.ChessStep;
import model.Sidable;
import node.analyse.NodeAnalyse;
import node.valueFun.AbstractValueFun;


public abstract class ChessNode implements Sidable, TreeNode {

	protected ChessState state;
	protected ChessStep step;
	protected NodeAnalyse nodeAnalyse;
	protected ChessNode parent;
	protected AbstractValueFun value;
	protected Collection<ChessNode> children;
	public NodeMark mark;

	public ChessNode(ChessState astate) {

		state = astate;

		nodeAnalyse = new NodeAnalyse(this);
		
		
	}

	public ChessNode(ChessStep step, ChessNode parent) {

		this.parent = parent;
		this.step = step;

		state = parent.getState().getNextState(step);
		nodeAnalyse = new NodeAnalyse(this);

	}

	public ChessState getState() {

		if (state == null) {
			return parent.getState().getNextState(step);
		} else
			return state;
	}

	public NodeAnalyse getStateAnalyse() {
		if (nodeAnalyse == null)
			nodeAnalyse = new NodeAnalyse(this);

		return nodeAnalyse;

	}

	public Enumeration<ChessNode> children() {
		return null;
	}

	public ChessStep getStep() {
		return step;
	}

	public Collection<ChessNode> getChildren() {
		return children;
	}

	public void allExpend() {
		children = new LinkedList<ChessNode>();
		Iterator<ChessStep> iter = childStepIterator();

		while (iter.hasNext()) {

			children.add(makeChildNode(iter.next()));
		}
	}

	public void freeSpace() {
		state = null;
		nodeAnalyse = null;
	}

	public void clearChildren() {
		children = null;
	}

	public abstract Iterator<ChessStep> childStepIterator();

	public abstract ChessNode makeChildNode(ChessStep step);

	public boolean gameIsNotEnd() {
		return children.size() != 0;
	}

	public static void main(String args[]) {
		ChessNode chessNode = new RedNode(new ChessState());

		System.out.println(chessNode.nodeAnalyse.getBlackStepCount());

		chessNode.allExpend();

		// System.out.println(chessNode);
	}

	public String toString() {
		String s = mark+" 节点：" + whichSide().toString() + "方 ,子数：红:"
				+ nodeAnalyse.getRedStepCount() + " 黑:"
				+ nodeAnalyse.getBlackStepCount() + "\n";
		s += state.toString();
		return s;
	}

	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return true;
	}

	public TreeNode getChildAt(int index) {
		if (isLeaf() || getChildCount() == 0 || index < 0
				|| index >= getChildCount())
			return null;

		Iterator<ChessNode> iter = children.iterator();
		int i = 0;
		while (i < index) {
			i++;
			iter.next();
		}
		return iter.next();
	}

	public int getChildCount() {
		if (children == null)
			return 0;
		else
			return children.size();
	}

	public TreeNode getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return children == null;
	}

	public int getIndex(TreeNode node) {

		if (isLeaf() || getChildCount() == 0)
			return -1;
		else {

			int i = 0;
			for (ChessNode cnode : children) {
				if (cnode == node)
					return i;
				else
					i++;
			}

			return -1;
		}
	}

}
