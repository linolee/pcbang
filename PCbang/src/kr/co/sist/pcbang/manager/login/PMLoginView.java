package kr.co.sist.pcbang.manager.login;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class PMLoginView extends JFrame {
	
	private JTextField jtfId;
	private JPasswordField jpfPass;
	private JButton jbtLogin;
	
	private JTextField jtfId2;
	private JTextField jpfPass2;
	
	public PMLoginView() {
		super("PC방 관리자");
		
		jtfId = new JTextField();
		jpfPass = new JPasswordField();
		jbtLogin = new JButton("로그인");
		
		jtfId2 = new JTextField("ID");
		jpfPass2 = new JTextField("PW");
		
		jtfId.setHorizontalAlignment(JTextField.CENTER);
		jtfId2.setHorizontalAlignment(JTextField.CENTER);
		jtfId2.setBackground(Color.white);
		jpfPass.setHorizontalAlignment(JTextField.CENTER);
		jpfPass2.setHorizontalAlignment(JTextField.CENTER);
		jpfPass2.setBackground(Color.white);
		
		setLayout(null);
		
		jtfId2.setBounds(110, 170, 130, 20);
		jtfId2.setEditable(false);
		jtfId.setBounds(110, 200, 130, 20);
		jpfPass2.setBounds(110, 230, 130, 20);
		jpfPass2.setEditable(false);
		jpfPass.setBounds(110, 260, 130, 20);
		jbtLogin.setBounds(110, 290, 130, 40);
		
		add(jtfId2);
		add(jtfId);
		add(jpfPass2);
		add(jpfPass);
		add(jbtLogin);
		
		PMLoginController pmlc = new PMLoginController(this);
		addWindowListener(pmlc);
		jtfId.addActionListener(pmlc);
		jpfPass.addActionListener(pmlc);
		jbtLogin.addActionListener(pmlc);
		
		setBounds(800, 200, 350, 600);
		setVisible(true);
		setResizable(false);
		
		jtfId.requestFocus();		
		
	}//PMLoginView

	public JTextField getJtfId() {
		return jtfId;
	}

	public JPasswordField getJpfPass() {
		return jpfPass;
	}

	public JButton getJbtLogin() {
		return jbtLogin;
	}

	
	
}//class

