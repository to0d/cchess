package tree;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import model.ChessState;
import model.ChessStep;
import model.Side;
import node.BlackNode;
import node.ChessNode;
import node.NodeListener;
import node.RedNode;

public abstract class AbstractAnalyseTreeModel implements TreeModel {

	public ChessNode rootNode;

	public AbstractAnalyseTreeModel(ChessState state, Side side) {

		if (side == Side.ºì)

			rootNode = new RedNode(state);
		else
			rootNode = new BlackNode(state);

	}

	public AbstractAnalyseTreeModel(ChessNode node) {
		rootNode = node;
	}

	// public void optimizeSearch(int depth, int width) {
	//
	// if (depth > 1) {
	// rootNode.allExpand();
	// for (AnalyseNode child : rootNode.children) {
	// child.bestSearch(depth - 1, width);
	// }
	//
	// } else {
	// makeWidthSearch(depth);
	// }
	//
	// }

	private void predSearch(NodeListener nodeListener, ChessNode chessNode) {

		nodeListener.fireAction(this, chessNode);

		if (chessNode.isLeaf())
			return;

		for (ChessNode child : chessNode.getChildren()) {

			predSearch(nodeListener, child);

		}
	}

	public void preOrderDeepSearch(NodeListener nodeListener) {

		predSearch(nodeListener, rootNode);

	}

	private void mindSearch(NodeListener nodeListener, ChessNode chessNode) {

		if (!chessNode.isLeaf())

		{
			Iterator<ChessNode> iter = chessNode.getChildren().iterator();
			mindSearch(nodeListener, iter.next());
			nodeListener.fireAction(this, chessNode);
			while (iter.hasNext())
				mindSearch(nodeListener, iter.next());
		} else {
			nodeListener.fireAction(this, chessNode);
		}

		for (ChessNode child : chessNode.getChildren()) {

			mindSearch(nodeListener, child);

		}
	}

	public void minOrderDeepSearch(NodeListener nodeListener) {

		mindSearch(nodeListener, rootNode);

	}

	private void bedSearch(NodeListener nodeListener, ChessNode chessNode) {

		if (!chessNode.isLeaf())

			for (ChessNode child : chessNode.getChildren()) {

				bedSearch(nodeListener, child);

			}

		nodeListener.fireAction(this, chessNode);
	}

	public void beOrderDeepSearch(NodeListener nodeListener) {

		bedSearch(nodeListener, rootNode);

	}

	public void widthSearch(NodeListener nodeListener) {
		LinkedList<ChessNode> queue = new LinkedList<ChessNode>();
		queue.add(rootNode);

		while (!queue.isEmpty()) {
			ChessNode head = queue.removeFirst();
			nodeListener.fireAction(this, head);
			if (!head.isLeaf()) {
				for (ChessNode child : head.getChildren()) {
					queue.addLast(child);
				}
			}
		}

	}

	public void addTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub

	}

	public Object getChild(Object parent, int index) {
		// TODO Auto-generated method stub
		return ((ChessNode) parent).getChildAt(index);
	}

	public int getChildCount(Object node) {
		// TODO Auto-generated method stub
		return ((ChessNode) node).getChildCount();
	}

	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		return ((ChessNode) parent).getIndex((ChessNode) child);
	}

	public Object getRoot() {
		// TODO Auto-generated method stub
		return rootNode;
	}

	public boolean isLeaf(Object node) {
		// TODO Auto-generated method stub
		return ((ChessNode) node).isLeaf();
	}

	public void removeTreeModelListener(TreeModelListener arg0) {
		// TODO Auto-generated method stub
	}

	public void valueForPathChanged(TreePath arg0, Object arg1) {
		// TODO Auto-generated method stub
	}

	public abstract void expend();
	
	public ChessStep searchBestStep(int n)
	{
		return null;
		
	}

	public void clear() {
		rootNode.clearChildren();

	}

}
