package kr.co.sist.pcbang.manager.main;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

<<<<<<< HEAD
import kr.co.sist.pcbang.manager.fare.PMFareView;
=======
import kr.co.sist.pcbang.manager.product.PMProductView;
>>>>>>> refs/heads/Minjeong
import kr.co.sist.pcbang.manager.seat.PMSeatView;
import kr.co.sist.pcbang.manager.user.PMUserView;


@SuppressWarnings("serial")
public class PMMainView extends JFrame{

	private JTabbedPane jtb;
	private JButton jbtLogOut, jbtAccount, jbtNoticeSave;
	private JTextArea jtaNotice;
	private JLabel jlBoard, jlOrderNum, jlMsgNum, jlTodayMoneyNum, jlOrder, jlMsg, jlTodayMoney;
	private JPanel seat, order, statistics, member, menu, price;
	
	public static String adminId;	
	
	public PMMainView(String adminName) {
		super("관리자 시스템 [ 로그인 계정 : "+adminName+" ]");
		
		jtb = new JTabbedPane();
		jbtLogOut = new JButton("로그아웃");
		jbtAccount = new JButton("계정관리");
		jbtNoticeSave = new JButton("공지사항저장");
		jlBoard = new JLabel();						//현황
		jtaNotice = new JTextArea();				//공지사항
		jlOrder = new JLabel("주문");
		jlOrderNum = new JLabel("0");
		jlMsg = new JLabel("메세지");
		jlMsgNum = new JLabel("0");
		jlTodayMoney = new JLabel("금일매출");
		jlTodayMoneyNum = new JLabel("0");
		
		JScrollPane jspBoard = new JScrollPane(jlBoard);
		JScrollPane jspNotice = new JScrollPane(jtaNotice);
		
		setLayout(null);

		jspBoard.setBounds(10,30, 120, 110);
		jspNotice.setBounds(10,250, 120, 100);
		jbtLogOut.setBounds(10, 145, 120, 30);
		jbtAccount.setBounds(10, 180, 120, 30);
		jbtNoticeSave.setBounds(10, 215, 120, 30);
		jlOrder.setBounds(10, 360, 120, 30);
		jlOrderNum.setBounds(10, 400, 120, 30);
		jlMsg.setBounds(10, 460, 120, 30);
		jlMsgNum.setBounds(10, 500, 120, 30);
		jlTodayMoney.setBounds(10, 550, 120, 30);
		jlTodayMoneyNum.setBounds(10, 590, 120, 30);
		jtb.setBounds(150, 30, 1000, 600);
		
		jspBoard.setBorder(new TitledBorder("현황"));
		jspNotice.setBorder(new TitledBorder("공지사항"));
		
<<<<<<< HEAD
	    seat = new PMSeatView();
	    order = new JPanel();
	    statistics = new JPanel();
	    member = new PMUserView();
	    menu = new JPanel();
	    price = new PMFareView();
=======
	    JPanel seat = new PMSeatView();
	    JPanel order = new JPanel();
	    JPanel statistics = new JPanel();
	    JPanel member = new JPanel();
	    JPanel menu = new PMProductView();
	    JPanel price = new JPanel();
>>>>>>> refs/heads/Minjeong
	    
	    jtb.add("좌석", seat );		
	    jtb.add("주문", order );		
	    jtb.add("통계", statistics );		
	    jtb.add("회원관리" ,member );		
	    jtb.add("상품관리" ,menu );		
	    jtb.add("요금제관리" ,price );		
		
		add(jspBoard);
		add(jspNotice);
		add(jbtLogOut);
		add(jbtAccount);
		add(jbtNoticeSave);
		add(jlOrder);
		add(jlMsg);
		add(jlTodayMoney);
		add(jlOrderNum);
		add(jlMsgNum);
		add(jlTodayMoneyNum);
		add(jtb);
		
		jlBoard.setBackground(Color.white);
		jlOrder.setHorizontalAlignment(JTextField.CENTER);
		jlOrder.setBackground(Color.white);
		jlMsg.setHorizontalAlignment(JTextField.CENTER);
		jlMsg.setBackground(Color.white);	
		jlTodayMoney.setHorizontalAlignment(JTextField.CENTER);
		jlTodayMoney.setBackground(Color.white);
		jlOrderNum.setHorizontalAlignment(JLabel.CENTER);
		jlOrderNum.setBackground(Color.white);
		jlMsgNum.setHorizontalAlignment(JLabel.CENTER);
		jlMsgNum.setBackground(Color.white);
		jlTodayMoneyNum.setHorizontalAlignment(JLabel.CENTER);
		jlTodayMoneyNum.setBackground(Color.white);
		jtb.setBackground(Color.white);
		
		jlBoard.setOpaque(true);
		jlOrder.setOpaque(true);
		jlOrderNum.setOpaque(true);
		jlMsg.setOpaque(true);
		jlMsgNum.setOpaque(true);
		jlTodayMoney.setOpaque(true);
		jlTodayMoneyNum.setOpaque(true);
		
		
		PMMainController pmmc = new PMMainController(this);
		addWindowListener(pmmc);
		jbtAccount.addActionListener(pmmc);
		jbtLogOut.addActionListener(pmmc);
		
		setBounds(400, 100, 1200, 700);
		setVisible(true);
		setResizable(false);
		
	} // PMMainView

	public JTabbedPane getJtb() {
		return jtb;
	}

	public JButton getJbtLogOut() {
		return jbtLogOut;
	}

	public JButton getJbtAccount() {
		return jbtAccount;
	}

	public JButton getJbtNoticeSave() {
		return jbtNoticeSave;
	}

	public JTextArea getJtaNotice() {
		return jtaNotice;
	}

	public JLabel getJlBoard() {
		return jlBoard;
	}

	public JLabel getJlOrderNum() {
		return jlOrderNum;
	}

	public JLabel getJlMsgNum() {
		return jlMsgNum;
	}

	public JLabel getJlTodayMoneyNum() {
		return jlTodayMoneyNum;
	}

	public JLabel getJlOrder() {
		return jlOrder;
	}

	public JLabel getJlMsg() {
		return jlMsg;
	}

	public JLabel getJlTodayMoney() {
		return jlTodayMoney;
	}

	public static String getAdminId() {
		return adminId;
	}


	
	
}//class
