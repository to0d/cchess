package node;

import tree.AbstractAnalyseTreeModel;

public interface NodeListener {

	public abstract void fireAction(AbstractAnalyseTreeModel treeModel,
			ChessNode node);

}
