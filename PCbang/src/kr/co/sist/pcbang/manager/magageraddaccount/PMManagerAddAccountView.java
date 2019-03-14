package kr.co.sist.pcbang.manager.magageraddaccount;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.pcbang.manager.magageraccount.PMManagerAccountController;

@SuppressWarnings("serial")
public class PMManagerAddAccountView extends JFrame{

	private JButton jbtAdd, jbtCancle;
	private JTextField jtfAddId, jtfAddName;
	private JPasswordField jpfAddPass;
	
	
	public PMManagerAddAccountView(PMManagerAccountController pmmac) {
		super("관리자 계정 추가");
		
		jtfAddId = new JTextField();
		jtfAddName = new JTextField();
		jpfAddPass = new JPasswordField();
		jbtAdd = new JButton("추가");
		jbtCancle = new JButton("취소");
		JLabel jlAddId = new JLabel("아이디");
		JLabel jlAddPass = new JLabel("패스워드");
		JLabel jlname = new JLabel("이름");
		
		setLayout(null);

		jlAddId.setBounds(110, 40, 100, 30);
		jlAddPass.setBounds(110, 90, 100, 30);
		jlname.setBounds(110, 140, 100, 30);
		jtfAddId.setBounds(190, 40, 100, 30);
		jpfAddPass.setBounds(190, 90, 100, 30);
		jtfAddName.setBounds(190, 140, 100, 30);
		jbtAdd.setBounds(70, 200, 100, 40);
		jbtCancle.setBounds(230, 200, 100, 40);
		
		add(jlAddId);
		add(jlAddPass);
		add(jlname);
		add(jtfAddId);
		add(jpfAddPass);		
		add(jtfAddName);		
		add(jbtAdd);		
		add(jbtCancle);		
		
		PMManagerAddAccountController pmmaac = new PMManagerAddAccountController(this, pmmac);
		addWindowListener(pmmaac);
		jbtCancle.addActionListener(pmmaac);
		jtfAddId.addActionListener(pmmaac);
		jpfAddPass.addActionListener(pmmaac);
		jtfAddName.addActionListener(pmmaac);
		jbtAdd.addActionListener(pmmaac);
		
		jtfAddId.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField)ke.getSource();
				if(src.getText().length()>=10)ke.consume(); {
					
				}
			}
		});		
		jpfAddPass.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField)ke.getSource();
				if(src.getText().length()>=10)ke.consume(); {
					
				}
			}
		});		
		jtfAddName.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField)ke.getSource();
				if(src.getText().length()>=10)ke.consume(); {
					
				}
			}
		});		
		
		setBounds(800, 200, 400, 300);
		setVisible(true);
		setResizable(false);
		
	}//PMManagerAddAccountView



	public JButton getJbtAdd() {
		return jbtAdd;
	}


	public JButton getJbtCancle() {
		return jbtCancle;
	}


	public JTextField getJtfAddId() {
		return jtfAddId;
	}


	public JTextField getJtfAddName() {
		return jtfAddName;
	}


	public JPasswordField getJpfAddPass() {
		return jpfAddPass;
	}
	
}// class
