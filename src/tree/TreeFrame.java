package tree;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import model.ChessState;
import model.Side;

public class TreeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	int width = 400;
	int height = 300;

	public TreeFrame() {
		setSize(width, height);
		setLocation(200, 200);

		AbstractAnalyseTreeModel treeModel = new AllExpandTree(
				new ChessState(), Side.ºì, 2);
		treeModel.expend();
		JTree tree = new JTree(treeModel);
		add(new JScrollPane(tree), BorderLayout.CENTER);

	}

	

}
