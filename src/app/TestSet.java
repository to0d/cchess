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

	JButton moreDepth = new JButton("����");
	JButton analys_count2 = new JButton("���");
	JButton optimizeSearch = new JButton("�Ż�");

	JButton compute_value = new JButton("����");

	JButton reset = new JButton("����");

	JButton brush = new JButton("ˢ��");

	JButton clear = new JButton("���");

	JToggleButton set = new JToggleButton("����");
	JToggleButton endset = new JToggleButton("����");
	ButtonGroup sets = new ButtonGroup();

	JButton find = new JButton("Ѱ��");

	JToggleButton currentColor�� = new JToggleButton("��");
	JToggleButton currentColor�� = new JToggleButton("��");
	ButtonGroup sellectColor = new ButtonGroup();

	JButton analys_count3 = new JButton("��");

	JToggleButton show_eat = new JToggleButton("��");
	JToggleButton show_move = new JToggleButton("��");
	JToggleButton show_protect = new JToggleButton("��");
	JToggleButton show_text = new JToggleButton("��");

	final ChessBoard board;

	TextArea out = new TextArea("", 12, 25, TextArea.SCROLLBARS_VERTICAL_ONLY);

	AbstractAnalyseTreeModel tree;

	JPanel choice = new JPanel();

	MouseAdapter basicListener ;

	MouseAdapter popListener = new PopupListener();

	boolean ������ = false;

	ChessLocation curLocation = ChessLocation.newInstance(0, 0);
	
	Config config=Config.getInstance();

	JComboBox set_depth = new JComboBox();
	{
		for (int i = 1; i < 5; i++)
			set_depth.addItem(Integer.toString(i));
	}

	JPopupMenu popup = new JPopupMenu();
	JMenuItem insert�� = new JMenuItem("Insert ��");
	JMenuItem insertʿ = new JMenuItem("Insert ʿ");
	JMenuItem insert�� = new JMenuItem("Insert ��");
	JMenuItem insert�� = new JMenuItem("Insert ��");
	JMenuItem insert�� = new JMenuItem("Insert ��");
	JMenuItem insert�� = new JMenuItem("Insert ��");
	JMenuItem insert�� = new JMenuItem("Insert ��");
	JMenuItem deletePiece = new JMenuItem("ɾ��");

	public TestSet(ChessBoard board) {

		this.board = board;
		setSize(200, config.getCaseSize() * 11);

		popup.add(insert��);
		popup.add(insertʿ);
		popup.add(insert��);
		popup.add(insert��);

		popup.add(insert��);
		popup.add(insert��);
		popup.add(insert��);
		popup.addSeparator();
		popup.add(deletePiece);

		insert��.addActionListener(new InsertPieceL(ChessName.��));
		insertʿ.addActionListener(new InsertPieceL(ChessName.ʿ));
		insert��.addActionListener(new InsertPieceL(ChessName.��));
		insert��.addActionListener(new InsertPieceL(ChessName.��));
		insert��.addActionListener(new InsertPieceL(ChessName.��));
		insert��.addActionListener(new InsertPieceL(ChessName.��));
		insert��.addActionListener(new InsertPieceL(ChessName.��));
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
		widthsearch.setBorder(new TitledBorder("������"));

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
		cmd.setBorder(new TitledBorder("����"));
		add(cmd);

		sellectColor.add(currentColor��);
		sellectColor.add(currentColor��);
		currentColor��.setSelected(true);
		currentColor��.setForeground(Color.red);
		currentColor��.setSelected(false);
		currentColor��.setForeground(Color.black);

		JPanel sellectPanel = new JPanel();
		cmd.add(find);
		sellectPanel.setLayout(new GridLayout(2, 1));

		sellectPanel.add(currentColor��);
		sellectPanel.add(currentColor��);
		sellectPanel.setBorder(new TitledBorder("��ǰ���"));
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
		choice.setBorder(new TitledBorder("ѡ����ʾ"));

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

		this.setBorder(new TitledBorder("����"));

	}

	private Side getCurSide() {
		if (currentColor��.isSelected()) {
			return Side.��;

		} else {
			return Side.��;

		}
	}

	

	

	class setL implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == set && !������) {

				������ = true;
				out.append("��ʼ���� : ���Ҽ�ѡ��\n");

				board.removeMouseListener(basicListener);
				board.stopShowFocus();
				board.clearInf();
				board.addMouseListener(popListener);

			} else if (e.getSource() == endset && ������) {
				������ = false;
				out.append("��������\n");
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
