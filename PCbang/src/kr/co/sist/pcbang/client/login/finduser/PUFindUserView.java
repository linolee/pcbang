package kr.co.sist.pcbang.client.login.finduser;

import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class PUFindUserView extends JFrame {

	private JTextField jtfUserIdName, jtfIdPhone1, jtfIdPhone2, jtfIdPhone3,
				jtfUserPassId, jtfUserPassName, jtfPassPhone1, jtfPassPhone2, jtfPassPhone3;
	private JButton jbtIdSearch, jbtPassSearch;
	
	public PUFindUserView() {
		jtfUserIdName = new JTextField();
		jtfIdPhone1 = new JTextField();
		jtfIdPhone2 = new JTextField();
		jtfIdPhone3 = new JTextField();
		jtfUserPassId = new JTextField();
		jtfUserPassName = new JTextField();
		jtfPassPhone1 = new JTextField();
		jtfPassPhone2 = new JTextField();
		jtfPassPhone3 = new JTextField();
		
		jbtIdSearch = new JButton("찾기");
		jbtPassSearch = new JButton("찾기");
		
		JLabel findId=new JLabel("아이디 찾기");
		JLabel idName=new JLabel("이름");
		JLabel idPhone=new JLabel("핸드폰 번호");
		JLabel findPass=new JLabel("비밀번호 찾기");
		JLabel passId=new JLabel("아이디");
		JLabel passName=new JLabel("이름");
		JLabel passPhone=new JLabel("핸드폰 번호");
		JLabel hipen1=new JLabel("-");
		JLabel hipen2=new JLabel("-");
		JLabel hipen3=new JLabel("-");
		JLabel hipen4=new JLabel("-");
		JLabel empty=new JLabel("");
		
		setLayout(null);
		
		findId.setBounds(40, 20, 100, 20);
		idName.setBounds(40, 50, 100, 20);
		jtfUserIdName.setBounds(120, 50, 120, 20);
		idPhone.setBounds(40, 80, 100, 20);
		jtfIdPhone1.setBounds(120, 80, 30, 20);
		hipen1.setBounds(150, 80, 10, 20);
		jtfIdPhone2.setBounds(160, 80, 35, 20);
		hipen2.setBounds(195, 80, 10, 20);
		jtfIdPhone3.setBounds(205, 80, 35, 20);
		jbtIdSearch.setBounds(250, 50, 60, 50);

		
		Panel idPanel=new Panel();
		idPanel.add(idName);
		idPanel.add(jtfUserIdName);
		
		
		findPass.setBounds(40, 120, 100, 20);
		passId.setBounds(40, 150, 100, 20);
		jtfUserPassId.setBounds(120, 150, 120, 20);
		passName.setBounds(40, 180, 100, 20);
		jtfUserPassName.setBounds(120, 180, 120, 20);
		passPhone.setBounds(40, 210, 100, 20);
		jtfPassPhone1.setBounds(120, 210, 30, 20);
		hipen3.setBounds(150, 210, 10, 20);
		jtfPassPhone2.setBounds(160, 210, 35, 20);
		hipen4.setBounds(195, 210, 10, 20);
		jtfPassPhone3.setBounds(205, 210, 35, 20);
		jbtPassSearch.setBounds(250, 150, 60, 50);
		
		add(findId);
		add(idName);
		add(jtfUserIdName);
		add(idPhone);
		add(jtfIdPhone1);
		add(hipen1);
		add(jtfIdPhone2);
		add(hipen2);
		add(jtfIdPhone3);
		add(jbtIdSearch);
		add(findPass);
		add(passId);
		add(jtfUserPassId);
		add(passName);
		add(jtfUserPassName);
		add(passPhone);
		add(jtfPassPhone1);
		add(hipen3);
		add(jtfPassPhone2);
		add(hipen4);
		add(jtfPassPhone3);
		add(jbtPassSearch);
		add(empty);
		
		PUFindUserController pufuc=new PUFindUserController(this);
		jbtIdSearch.addActionListener(pufuc);
		jbtPassSearch.addActionListener(pufuc);
		jtfUserIdName.addActionListener(pufuc);
		jtfIdPhone1.addActionListener(pufuc);
		jtfIdPhone2.addActionListener(pufuc);
		jtfIdPhone3.addActionListener(pufuc);
		jtfUserPassId.addActionListener(pufuc);
		jtfUserPassName.addActionListener(pufuc);
		jtfPassPhone1.addActionListener(pufuc);
		jtfPassPhone2.addActionListener(pufuc);
		jtfPassPhone3.addActionListener(pufuc);
		addWindowListener(pufuc);
		
		setBounds(100, 100, 400, 300);
		setVisible(true);
		
		
		
	}//PUFindUserView
	
	public JTextField getJtfUserIdName() {
		return jtfUserIdName;
	}

	public JTextField getJtfIdPhone1() {
		return jtfIdPhone1;
	}

	public JTextField getJtfIdPhone2() {
		return jtfIdPhone2;
	}

	public JTextField getJtfIdPhone3() {
		return jtfIdPhone3;
	}

	public JTextField getJtfUserPassId() {
		return jtfUserPassId;
	}

	public JTextField getJtfUserPassName() {
		return jtfUserPassName;
	}

	public JTextField getJtfPassPhone1() {
		return jtfPassPhone1;
	}

	public JTextField getJtfPassPhone2() {
		return jtfPassPhone2;
	}

	public JTextField getJtfPassPhone3() {
		return jtfPassPhone3;
	}

	public JButton getJbtIdSearch() {
		return jbtIdSearch;
	}

	public JButton getJbtPassSearch() {
		return jbtPassSearch;
	}

	public static void main(String[] args) {
		new PUFindUserView();
	}
}

