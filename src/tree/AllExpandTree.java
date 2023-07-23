package tree;

import model.ChessState;
import model.Side;
import node.ChessNode;
import node.NodeListener;
import node.NodeMark;

public class AllExpandTree extends AbstractAnalyseTreeModel {

	static int count;

	class CountMark implements NodeMark {

		int i;

		public CountMark(int i) {
			this.i = i;

		}

		public int getNum() {
			// TODO Auto-generated method stub
			return i;
		}

		public String toString() {
			return Integer.toOctalString(i);
		}
	}

	int depth;

	public AllExpandTree(ChessState state, Side side, int n) {
		super(state, side);

		depth = n;

	}

	@Override
	public void expend() {

		count = 1;
		rootNode.mark = new CountMark(depth);

		preOrderDeepSearch(new NodeListener() {

			public void fireAction(AbstractAnalyseTreeModel treeModel,
					ChessNode node) {

				if (node.mark.getNum() > 0) {
					int n = node.mark.getNum() - 1;
					node.allExpend();
					for (ChessNode cnode : node.getChildren()) {
						cnode.mark = new CountMark(n);
						count++;
					}

				}
			}
		});

		System.out.println("There are " + count + " nodes");

	}

	public static void main(String[] args) {
//		JFrame frame = new TreeFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
		AbstractAnalyseTreeModel treeModel = new AllExpandTree(
				new ChessState(), Side.ºì, 2);
		treeModel.expend();
		
		treeModel.widthSearch(new NodeListener(){
			int n=0;

			public void fireAction(AbstractAnalyseTreeModel treeModel,
					ChessNode node) {
				System.out.println(n+" "+node.mark);
				n++;
				
				
			}});

	}

}
