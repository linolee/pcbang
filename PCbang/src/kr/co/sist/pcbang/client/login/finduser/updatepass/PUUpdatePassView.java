package kr.co.sist.pcbang.client.login.finduser.updatepass;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import kr.co.sist.pcbang.client.login.finduser.PUFindUserView;

public class PUUpdatePassView extends JDialog{
	
	private PUFindUserView pufuv;
	private JPasswordField jpfPass, jpfPassCheck;
	private JButton jbtOk, jbtCancel;
	
	public PUUpdatePassView(PUFindUserView pufuv) {
		super(pufuv, "비밀번호 재설정", true);
		this.pufuv=pufuv;
		
		String userName=pufuv.getJtfUserPassName().getText();
		String userId=pufuv.getJtfUserPassId().getText();
		
		StringBuilder headerText=new StringBuilder();
		
		headerText.append(userName).append("[").append(userId).append("]")
		.append("님의");
		
		jpfPass = new JPasswordField();
		jpfPassCheck = new JPasswordField();
		
		jbtOk = new JButton("확인");
		jbtCancel = new JButton("취소");
		
		JLabel header=new JLabel(headerText.toString());
		JLabel header2=new JLabel("비밀번호를 다시 설정해주세요.");
		JLabel password=new JLabel("*비밀번호");
		JLabel passwordCheck=new JLabel("*비밀번호 확인");
		
		setLayout(null);
		
		header.setBounds(60, 20, 300, 20);
		header2.setBounds(60, 50, 300, 20);
		
		password.setBounds(60, 90, 100, 20);
		jpfPass.setBounds(160, 90, 100, 20);
		passwordCheck.setBounds(60, 120, 100, 20);
		jpfPassCheck.setBounds(160, 120, 100, 20);
		jbtOk.setBounds(60, 160, 90, 40);
		jbtCancel.setBounds(170, 160, 90, 40);
		
		add(header);
		add(header2);
		add(password);
		add(jpfPass);
		add(passwordCheck);
		add(jpfPassCheck);
		add(jbtOk);
		add(jbtCancel);
		
		PUUpdatePassController puupc=new PUUpdatePassController(this);
		jpfPass.addActionListener(puupc);
		jpfPassCheck.addActionListener(puupc);
		jbtOk.addActionListener(puupc);
		jbtCancel.addActionListener(puupc);
		
		setBounds(100, 100, 400, 280);
		setVisible(true);
		
	}//PUUpdatePassView

	public PUFindUserView getPufuv() {
		return pufuv;
	}

	public JPasswordField getJpfPass() {
		return jpfPass;
	}

	public JPasswordField getJpfPassCheck() {
		return jpfPassCheck;
	}

	public JButton getJbtOk() {
		return jbtOk;
	}

	public JButton getJbtCancel() {
		return jbtCancel;
	}
	
}//class
