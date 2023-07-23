package node;




public class AnalyseNode {


	







	


	


	

//	public void makeBetterChildRen(int width) {
//
//		allExpand();
//
//		if (whichSide() == Side.ºì)
//			children = new BigCollection(children, width).getBestAll();
//		else
//			children = new SmallCollection(children, width).getBestAll();
//
//	}
//
//	
//
//	public void bestSearch(int depth, int width) {
//
//		if (depth > 1) {
//
//			makeBetterChildRen(width);
//			for (AnalyseNode child : children)
//				child.bestSearch(depth - 1, width);
//			freeSpace();
//		} else {
//
//			allExpand();
//
//			for (AnalyseNode child : children) {
//
//				child.freeSpace();
//			}
//
//			freeSpace();
//
//		}
//
//	}
//
//	public Side getChildSide() {
//		if (mySide == Side.ºì) {
//
//			return Side.ºÚ;
//		} else {
//
//			return Side.ºì;
//		}
//	}
//
//	public Side whichSide() {
//
//		return mySide;
//	}
//
//	
//
//	private void freeSpace() {
//
//		if (!freeSpaceed) {
//
//			stateAnalyse.freeSpace();
//
//			freeSpaceed = true;
//		}
//
//	}
//
//	public int count() {
//		if (!isexpanded())
//
//		{
//			return 1;
//		}
//
//		int c = 0;
//
//		for (AnalyseNode child : children) {
//			c += child.count();
//		}
//
//		return c + 1;
//	}
//
//	public float getValue() {
//
//		return stateAnalyse.getValue();
//	}
//
//	public ChessStep getStep() {
//		return step;
//	}
//
//	public int compareTo(AnalyseNode another) {
//
//		float x = getValue() - another.getValue();
//
//		if (x > 0.00000001)
//			return 1;
//		else if (x < -0.000001)
//			return -1;
//		else
//			return 0;
//
//	}
//
//	public void sortChild() {
//
//		allExpand();
//
//		Iterator<AnalyseNode> iter = children.iterator();
//
//		Collections.sort(new LinkedList<AnalyseNode>(children));
//	}
//
//	public AnalyseNode findBestChild() {
//		if (!expanded || children.size() == 0)
//			return this;
//		else {
//
//			Iterator<AnalyseNode> iter = children.iterator();
//
//			AnalyseNode better = iter.next();
//			float betterValue = 0;
//
//			if (mySide == Side.ºì) {
//
//				betterValue = better.findBestChild().getValue();
//				float nextValue;
//				while (iter.hasNext()) {
//					AnalyseNode next = iter.next();
//					nextValue = next.findBestChild().getValue();
//					if (betterValue < nextValue) {
//						betterValue = nextValue;
//						better = next;
//					}
//
//				}
//
//			} else {
//
//				betterValue = better.findBestChild().getValue();
//				float nextValue;
//
//				while (iter.hasNext()) {
//
//					AnalyseNode next = iter.next();
//					nextValue = next.findBestChild().getValue();
//					if (betterValue > nextValue) {
//						betterValue = nextValue;
//						better = next;
//					}
//
//				}
//
//			}
//
//			this.stateAnalyse.setValue(betterValue);
//			return better;
//
//		}
//
//	}
//
//	abstract class SeparateCollection {
//
//		Collection<AnalyseNode> nodes;
//
//		int maxNum;
//
//		public SeparateCollection(Collection<AnalyseNode> nodes, int maxNum) {
//
//			this.nodes = nodes;
//			this.maxNum = maxNum;
//		}
//
//		abstract Collection<AnalyseNode> getBestAll();
//
//	}
//
//	class BigCollection extends SeparateCollection {
//
//		Collection<AnalyseNode> bigCollection;
//		AnalyseNode[] buf;
//
//		public BigCollection(Collection<AnalyseNode> nodes, int maxNum) {
//			super(nodes, maxNum);
//
//			if (nodes.size() <= maxNum) {
//				bigCollection = nodes;
//
//			} else {
//				buf = new AnalyseNode[maxNum];
//				Iterator<AnalyseNode> iter = nodes.iterator();
//
//				for (int i = 0; i < maxNum; i++)
//					buf[i] = iter.next();
//
//				for (int i = (maxNum - 1) / 2; i >= 0; i--) {
//					AdjustBuf(i);
//				}
//
//				while (iter.hasNext()) {
//					AnalyseNode node = iter.next();
//					if (node.getValue() > buf[0].getValue()) {
//						buf[0] = node;
//						AdjustBuf(0);
//					}
//				}
//
//				bigCollection = new LinkedList<AnalyseNode>();
//				for (int i = 0; i < maxNum; i++)
//					bigCollection.add(buf[i]);
//
//			}
//
//		}
//
//		private void AdjustBuf(int n) {
//			int cur = n;
//
//			AnalyseNode curNode = buf[n];
//
//			for (int i = 2 * n + 1; i < maxNum; i = i * 2 + 1) {
//				if (i < (maxNum - 1)
//						&& buf[i].getValue() > buf[i + 1].getValue())
//					i++;
//				if (curNode.getValue() > buf[i].getValue()) {
//					buf[cur] = buf[i];
//					cur = i;
//				} else
//					break;
//
//			}
//			buf[cur] = curNode;
//		}
//
//		Collection<AnalyseNode> getBestAll() {
//
//			return bigCollection;
//		}
//
//	}
//
//	class SmallCollection extends SeparateCollection {
//
//		Collection<AnalyseNode> smallCollection;
//		AnalyseNode[] buf;
//
//		public SmallCollection(Collection<AnalyseNode> nodes, int maxNum) {
//			super(nodes, maxNum);
//
//			if (nodes.size() <= maxNum) {
//				smallCollection = nodes;
//
//			} else {
//				buf = new AnalyseNode[maxNum];
//				Iterator<AnalyseNode> iter = nodes.iterator();
//
//				for (int i = 0; i < maxNum; i++)
//					buf[i] = iter.next();
//
//				for (int i = (maxNum - 1) / 2; i >= 0; i--) {
//					AdjustBuf(i);
//				}
//
//				while (iter.hasNext()) {
//					AnalyseNode node = iter.next();
//					if (node.getValue() < buf[0].getValue()) {
//						buf[0] = node;
//						AdjustBuf(0);
//					}
//				}
//
//				smallCollection = new LinkedList<AnalyseNode>();
//				for (int i = 0; i < maxNum; i++)
//					smallCollection.add(buf[i]);
//
//			}
//
//		}
//
//		private void AdjustBuf(int n) {
//			int cur = n;
//
//			AnalyseNode curNode = buf[n];
//
//			for (int i = 2 * n + 1; i < maxNum; i = i * 2 + 1) {
//				if (i < (maxNum - 1)
//						&& buf[i].getValue() < buf[i + 1].getValue())
//					i++;
//				if (curNode.getValue() < buf[i].getValue()) {
//					buf[cur] = buf[i];
//					cur = i;
//				} else
//					break;
//
//			}
//			buf[cur] = curNode;
//		}
//
//		Collection<AnalyseNode> getBestAll() {
//
//			return smallCollection;
//		}
//
//	}
}