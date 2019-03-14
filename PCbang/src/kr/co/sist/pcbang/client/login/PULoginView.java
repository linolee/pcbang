package kr.co.sist.pcbang.client.login;


import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PULoginView extends JFrame{
	
	private JTextArea jtaNotice;
	private JTextField jtfId,jtfCardNum;
	private JPasswordField jpfPass;
	private JButton jbtStart,jbtMembership,jbtFind;
	
	public PULoginView() {
		super("PC�� ����� �α���");
		
		jtaNotice=new JTextArea();//�����Ұ��ϰ�
		//����Ұ�
		jtaNotice.setEditable(false);
		jtaNotice.setLineWrap(true);
		jtaNotice.setWrapStyleWord(true);
		//��ũ�� �߰�
		JScrollPane jspNotice=new JScrollPane(jtaNotice);
		jtfId=new JTextField();
		jpfPass=new JPasswordField();
		jtfCardNum=new JTextField();
		jbtStart=new JButton("������");
		jbtMembership=new JButton("ȸ������");
		jbtFind=new JButton("ID/��й�ȣ ã��");
		
		JLabel jlNoticeTitle=new JLabel("��������");
		JLabel jlIdTitle=new JLabel(" ���̵�");
		JLabel jlPassTitle=new JLabel(" ��й�ȣ    ");
		JLabel jlCardNumTitle=new JLabel(" ī���ȣ    ");
		
		JPanel panel2=new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add("North",jlIdTitle);
		panel2.add("South",jlPassTitle);
		
		JPanel panel3=new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.add("North",jtfId);
		panel3.add("South",jpfPass);
		
		JPanel panel1=new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add("West",panel2);
		panel1.add("Center",panel3);
		
		JPanel panel4=new JPanel();
		panel4.setLayout(new BorderLayout());
		panel4.add("West",jlCardNumTitle);
		panel4.add("Center",jtfCardNum);
		
		panel1.setBorder(new TitledBorder("ȸ��"));
		panel4.setBorder(new TitledBorder("��ȸ��"));
		
		setLayout(null);
		panel1.setBounds(20, 120, 220, 80);
		jspNotice.setBounds(20, 35, 340, 70);
		jlNoticeTitle.setBounds(21, 10, 60, 25);
		panel4.setBounds(20, 210, 220, 47);
		//jlIdTitle.setBounds(50, 120, 60, 25);
		//jlPassTitle.setBounds(50, 160, 60, 25);
		//jtfId.setBounds(110, 120, 90, 25);
		//jpfPass.setBounds(110, 160, 90, 25);
		//jtfCardNum.setBounds(110, 220, 90, 25);
		//jlCardNumTitle.setBounds(50, 220, 60, 25);
		jbtStart.setBounds(250, 140, 100, 100);
		jbtMembership.setBounds(53, 280, 130, 50);
		jbtFind.setBounds(205, 280, 130, 50);
		
		add(jspNotice);
		add(jlNoticeTitle);
		add(panel1);
		add(panel4);
		add(jbtStart);
		add(jbtMembership);
		add(jbtFind);
		
		PULoginController pulc=new PULoginController(this);
		jbtStart.addActionListener(pulc);
		jbtMembership.addActionListener(pulc);
		jbtFind.addActionListener(pulc);
		addWindowListener(pulc);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		setBounds(200, 200, 400, 400);
		setVisible(true);
	}//PULoginView

	public JTextArea getJtaNotice() {
		return jtaNotice;
	}
	public JTextField getJtfId() {
		return jtfId;
	}
	public JTextField getJtfCardNum() {
		return jtfCardNum;
	}
	public JPasswordField getJpfPass() {
		return jpfPass;
	}
	public JButton getJbtStart() {
		return jbtStart;
	}
	public JButton getJbtMembership() {
		return jbtMembership;
	}
	public JButton getJbtFind() {
		return jbtFind;
	}
	
}//class
