package app;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import gui.ChessBoard;
import model.ChessLocation;
import model.Config;
import model.Side;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;

	JMenuBar menuBar = new JMenuBar();
	JMenu menu_��Ϸ = new JMenu("��ʼ");
	JMenu menu_ģʽ = new JMenu("ģʽ");
	JMenu menu_���� = new JMenu("����");
	JMenu menu_���� = new JMenu("����");
	JMenuItem twoPeople = new JMenuItem("˫��ģʽ");
	JMenu onePeople = new JMenu("����ģʽ");
	JMenuItem begin = new JMenuItem("���¿���");
	JMenuItem end = new JMenuItem("�����Ƴ�");
	JMenuItem about = new JMenuItem("����");
	// JMenuItem searchA = new JMenuItem("����ģʽ-A");
	JMenuItem searchB = new JMenuItem("����ģʽ-B");
	JMenuItem ���� = new JMenuItem("����");

	Config config = Config.getInstance();

	public ChessBoard board = new ChessBoard();

	private final int width = config.getCaseSize() * 10;

	private final int height = config.getCaseSize() * 11 + 60;

	ChessLocation lastRedLocation = ChessLocation.newInstance(4, 9);
	ChessLocation lastBlackLocation = ChessLocation.newInstance(4, 0);

	Container con = getContentPane();

	Icon icon = new ImageIcon("pic\\main.gif");

	JLabel iconLabel = new JLabel("", icon, JLabel.CENTER);

	public Game() {

		con.setLayout(null);

		menuBar.add(menu_��Ϸ);
		menu_��Ϸ.add(begin);
		menu_��Ϸ.add(end);

		menuBar.add(menu_ģʽ);

		menu_ģʽ.add(twoPeople);
		menu_ģʽ.add(onePeople);

		// onePeople.add(searchA);
		onePeople.add(searchB);

		menuBar.add(menu_����);
		menu_����.add(����);
		menuBar.add(menu_����);

		menu_����.add(about);

		con.add(menuBar);
		menuBar.setBounds(0, 0, 160, 30);

		con.add(board);
		board.setBounds(0, 30, config.getCaseSize() * 10, config.getCaseSize() * 11 + 30);

		setLocation(1024 / 2 - config.getCaseSize() * 5, 50);

		setSize(width, height);

		{

			lable.setForeground(Color.RED);

			lable.setFont(new Font("����_GB2312", Font.PLAIN, 30));

			lable.setBounds(180, 10, 250, 50);

			add(lable);

		}

		setIconImage(getToolkit().getImage("pic//main.gif"));
		setVisible(true);

		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				System.exit(0);
			}
		});

//		searchA.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent arg0) {
//
//				reset();
//
//				lable.setText("����ģʽ-A");
//
//				lable.setBounds(180, 10, 250, 50);
//
//				board.addMouseListener(searchAL);
//
//				curListener = searchAL;
//
//			}
//
//		});

		searchB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				reset();

				lable.setText("����ģʽ-B");

				lable.setBounds(180, 10, 250, 50);

			}

		});

		twoPeople.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				reset();

				lable.setText("˫��ģʽ");

				lable.setBounds(190, 10, 130, 50);

			}

		});

		begin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				board.reset();

			}

		});

		end.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				System.exit(0);

			}

		});
		
		about.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				aboutDialog.setVisible(true);

			}

		});

	}

	public static void main(String[] args) {
		new Game();

	}

	private void showVictory(Side side) {

		JDialog myDialog = new JDialog(this, "����", true);

		JTextArea text = new JTextArea(1, 5);
		if (side == Side.��)
			text.setText("�췽ʤ��");
		else
			text.setText("�ڷ�ʤ��");

		text.setFont(new Font("����_GB2312", Font.PLAIN, 30));
		text.setBackground(aboutDialog.getBackground());

		myDialog.setLayout(new FlowLayout());
		myDialog.add(iconLabel);
		myDialog.add(text);
		myDialog.pack();
		myDialog.setLocation(400, 300);

		myDialog.setVisible(true);

		myDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				System.exit(0);
			}
		});

	}

	private void reset() {
		lastRedLocation = ChessLocation.newInstance(4, 9);
		lastBlackLocation = ChessLocation.newInstance(4, 0);
		currentSide = Side.��;
		board.reset();
		board.removeMouseListener(curListener);
	}

	JDialog aboutDialog = new JDialog(this, "����Chess 3");
	{
		aboutDialog.setLocation(450, 250);

		aboutDialog.setModal(true);

		Container con = aboutDialog.getContentPane();

		con.setLayout(new FlowLayout());
		con.add(iconLabel);

		JTextArea area = new JTextArea(5, 10);

		area.setBackground(aboutDialog.getBackground());

		area.append("Chess \n" +

				"Version: 3.0.0\n\n" + "Belong to liuyan\n" + "Mail: libman@eyou.com");

		area.setEditable(false);
		con.add(area);

		aboutDialog.pack();

	}

	JLabel lable = new JLabel("����ģʽ-B");

	private void changeSide() {
		if (currentSide == Side.��)
			currentSide = Side.��;
		else
			currentSide = Side.��;

	}

	MouseAdapter curListener;

	Side currentSide = Side.��;

}
