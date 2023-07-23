package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import gui.ChessBoard;
import model.ChessLocation;
import model.ChessName;
import model.ChessPiece;
import model.Config;
import model.Side;
import tree.AbstractAnalyseTreeModel;

public class TestSet extends JPanel {

	JButton moreDepth = new JButton("更深");
	JButton analys_count2 = new JButton("宽度");
	JButton optimizeSearch = new JButton("优化");

	JButton compute_value = new JButton("棋势");

	JButton reset = new JButton("重新");

	JButton brush = new JButton("刷新");

	JButton clear = new JButton("清除");

	JToggleButton set = new JToggleButton("布局");
	JToggleButton endset = new JToggleButton("结束");
	ButtonGroup sets = new ButtonGroup();

	JButton find = new JButton("寻找");

	JToggleButton currentColor红 = new JToggleButton("红");
	JToggleButton currentColor黑 = new JToggleButton("黑");
	ButtonGroup sellectColor = new ButtonGroup();

	JButton analys_count3 = new JButton("核");

	JToggleButton show_eat = new JToggleButton("吃");
	JToggleButton show_move = new JToggleButton("走");
	JToggleButton show_protect = new JToggleButton("防");
	JToggleButton show_text = new JToggleButton("字");

	final ChessBoard board;

	TextArea out = new TextArea("", 12, 25, TextArea.SCROLLBARS_VERTICAL_ONLY);

	AbstractAnalyseTreeModel tree;

	JPanel choice = new JPanel();

	MouseAdapter basicListener ;

	MouseAdapter popListener = new PopupListener();

	boolean 布局中 = false;

	ChessLocation curLocation = ChessLocation.newInstance(0, 0);
	
	Config config=Config.getInstance();

	JComboBox set_depth = new JComboBox();
	{
		for (int i = 1; i < 5; i++)
			set_depth.addItem(Integer.toString(i));
	}

	JPopupMenu popup = new JPopupMenu();
	JMenuItem insert将 = new JMenuItem("Insert 将");
	JMenuItem insert士 = new JMenuItem("Insert 士");
	JMenuItem insert相 = new JMenuItem("Insert 相");
	JMenuItem insert马 = new JMenuItem("Insert 马");
	JMenuItem insert车 = new JMenuItem("Insert 车");
	JMenuItem insert炮 = new JMenuItem("Insert 炮");
	JMenuItem insert兵 = new JMenuItem("Insert 兵");
	JMenuItem deletePiece = new JMenuItem("删除");

	public TestSet(ChessBoard board) {

		this.board = board;
		setSize(200, config.getCaseSize() * 11);

		popup.add(insert将);
		popup.add(insert士);
		popup.add(insert相);
		popup.add(insert马);

		popup.add(insert车);
		popup.add(insert炮);
		popup.add(insert兵);
		popup.addSeparator();
		popup.add(deletePiece);

		insert将.addActionListener(new InsertPieceL(ChessName.将));
		insert士.addActionListener(new InsertPieceL(ChessName.士));
		insert相.addActionListener(new InsertPieceL(ChessName.相));
		insert马.addActionListener(new InsertPieceL(ChessName.马));
		insert车.addActionListener(new InsertPieceL(ChessName.车));
		insert炮.addActionListener(new InsertPieceL(ChessName.炮));
		insert兵.addActionListener(new InsertPieceL(ChessName.兵));
		deletePiece.addActionListener(new DeletePiece());

		JPanel widthsearch = new JPanel();

		widthsearch.setLayout(new GridLayout(2, 3));

		clear.setBackground(Color.red);
		analys_count3.setEnabled(false);

		widthsearch.add(analys_count2);
		widthsearch.add(set_depth);
		widthsearch.add(optimizeSearch);
		widthsearch.add(analys_count3);
		widthsearch.add(moreDepth);

		add(widthsearch);
		widthsearch.setBorder(new TitledBorder("搜索树"));

		sets.add(set);
		sets.add(endset);
		set.setSelected(false);
		endset.setSelected(true);

		JPanel cmd = new JPanel();
		cmd.setLayout(new GridLayout(3, 3));
		cmd.add(brush);
		cmd.add(clear);
		cmd.add(reset);
		cmd.add(set);
		cmd.add(endset);
		cmd.add(compute_value);
		cmd.setBorder(new TitledBorder("操作"));
		add(cmd);

		sellectColor.add(currentColor红);
		sellectColor.add(currentColor黑);
		currentColor红.setSelected(true);
		currentColor红.setForeground(Color.red);
		currentColor黑.setSelected(false);
		currentColor黑.setForeground(Color.black);

		JPanel sellectPanel = new JPanel();
		cmd.add(find);
		sellectPanel.setLayout(new GridLayout(2, 1));

		sellectPanel.add(currentColor红);
		sellectPanel.add(currentColor黑);
		sellectPanel.setBorder(new TitledBorder("当前玩家"));
		add(sellectPanel);

		choice.setLayout(new GridLayout(2, 3));

		choice.add(show_eat);
		show_eat.setSelected(true);
		choice.add(show_move);
		show_move.setSelected(true);
		choice.add(show_protect);
		show_protect.setSelected(true);
		choice.add(show_text);
		show_text.setSelected(false);
		choice.setBorder(new TitledBorder("选择显示"));

		add(choice);

		JPanel panel = new JPanel();

		panel.add(out, BorderLayout.CENTER);
		add(panel);

		// sort.addActionListener(new sortL());

		

		

		set.addActionListener(new setL());
		endset.addActionListener(new setL());

		board.addMouseListener(basicListener);

		

		

		

		

		find.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}});

		this.setBorder(new TitledBorder("助手"));

	}

	private Side getCurSide() {
		if (currentColor红.isSelected()) {
			return Side.红;

		} else {
			return Side.黑;

		}
	}

	

	

	class setL implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == set && !布局中) {

				布局中 = true;
				out.append("开始布局 : 请右键选择\n");

				board.removeMouseListener(basicListener);
				board.stopShowFocus();
				board.clearInf();
				board.addMouseListener(popListener);

			} else if (e.getSource() == endset && 布局中) {
				布局中 = false;
				out.append("结束布局\n");
				board.addMouseListener(basicListener);
				board.removeMouseListener(popListener);

			}

		}

	}

	class PopupListener extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {

			if (e.isPopupTrigger()) {
				curLocation = board.getLocationFromPoint(e.getX(), e.getY());
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}

	}

	class InsertPieceL implements ActionListener {
		ChessName name;

		InsertPieceL(ChessName name) {
			this.name = name;
		}

		public void actionPerformed(ActionEvent e) {

			board.setPiece(curLocation, new ChessPiece(name, getCurSide()));

		}

	}

	class DeletePiece implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			board.erase(curLocation);

		}

	}



	
	

}
