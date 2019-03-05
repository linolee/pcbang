package kr.co.sist.pcbang.client.main;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class PUMainView extends JFrame{
	
	private JLabel jlSeatNum,jlUseTime,jlRestTime,jlName;
	private JButton jbtOrder,jbtCharge,jbtChange,jbtMsg,jbtExit;
	
	public static String userId;
	public static String cardNum;
	public static int seatNum; 
	public String id=userId;
	public String card=cardNum;
	public int seat=seatNum;
	//public static String seatNum;
	
	public PUMainView() {
		super("PC방 사용자-메인");
		
		jlSeatNum=new JLabel("00");
		jlUseTime=new JLabel("00:00");
		jlUseTime.setFont(new Font("돋움", Font.BOLD, 20));
		jlRestTime=new JLabel("00:00");
		jlRestTime.setFont(new Font("돋움", Font.BOLD, 20));
		jlName=new JLabel("아무개");
		
		jbtOrder=new JButton("상품 주문");
		jbtCharge=new JButton("시간 충전");
		jbtChange=new JButton("좌석 변경");
		jbtMsg=new JButton("메세지");
		jbtExit=new JButton("사용 종료");
		
		JLabel jlSeat=new JLabel("No.");
		JLabel jlUseTimeTitle=new JLabel("사용시간          ");
		jlUseTimeTitle.setFont(new Font("돋움", Font.BOLD, 18));
		JLabel jlRestTimeTitle=new JLabel("남은시간          ");
		jlRestTimeTitle.setFont(new Font("돋움", Font.BOLD, 18));
		JLabel jlHello=new JLabel("님 환영합니다.");
		
		JPanel pan1=new JPanel();
		pan1.add(jlSeat);
		pan1.add(jlSeatNum);

		JPanel pan2=new JPanel();
		pan2.add(jlName);
		pan2.add(jlHello);
		
		JPanel pan3=new JPanel();
		pan3.setLayout(new BorderLayout());
		pan3.add("West",pan1);
		pan3.add("East",pan2);
		
		JPanel pan4=new JPanel();
		pan4.add(jlUseTimeTitle);
		pan4.add(jlUseTime);
		
		JPanel pan5=new JPanel();
		pan5.add(jlRestTimeTitle);
		pan5.add(jlRestTime);
		
		JPanel pan6=new JPanel();
		pan6.setLayout(new BorderLayout());
		pan6.add("North",pan3);
		pan6.add("Center",pan4);
		pan6.add("South",pan5);
		
		pan6.setBorder(new LineBorder(Color.BLACK));
		
		setLayout(null);
		pan6.setBounds(20, 20, 350, 100);
		jbtOrder.setBounds(40, 140, 90, 30);
		jbtCharge.setBounds(150, 140, 90, 30);
		jbtChange.setBounds(260, 140, 90, 30);
		jbtMsg.setBounds(95, 185, 90, 30);
		jbtExit.setBounds(205, 185, 90, 30);
		
		add(pan6);
		add(jbtOrder);
		add(jbtCharge);
		add(jbtChange);
		add(jbtMsg);
		add(jbtExit);
		
		PUMainController pumc=new PUMainController(this);
		jbtOrder.addActionListener(pumc);
		jbtCharge.addActionListener(pumc);
		jbtChange.addActionListener(pumc);
		jbtMsg.addActionListener(pumc);
		jbtExit.addActionListener(pumc);
		addWindowListener(pumc);
		
		setBounds(20, 20, 400, 290);
		setVisible(true);
	}//PUMainView

	public JLabel getJlSeatNum() {
		return jlSeatNum;
	}
	public JLabel getJlUseTime() {
		return jlUseTime;
	}
	public JLabel getJlRestTime() {
		return jlRestTime;
	}
	public JLabel getJlName() {
		return jlName;
	}
	public JButton getJbtOrder() {
		return jbtOrder;
	}
	public JButton getJbtCharge() {
		return jbtCharge;
	}
	public JButton getJbtChange() {
		return jbtChange;
	}
	public JButton getJbtMsg() {
		return jbtMsg;
	}
	public JButton getJbtExit() {
		return jbtExit;
	}
	
//	public static void main(String[] args) {
//		new PUMainView();
//	}//main
}//class
