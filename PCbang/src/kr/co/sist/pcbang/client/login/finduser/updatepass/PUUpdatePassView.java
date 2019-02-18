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
		super(pufuv, "��й�ȣ �缳��", true);
		this.pufuv=pufuv;
		
		String userName=pufuv.getJtfUserPassName().getText();
		String userId=pufuv.getJtfUserPassId().getText();
		
		StringBuilder headerText=new StringBuilder();
		
		headerText.append(userName).append("[").append(userId).append("]")
		.append("����");
		
		jpfPass = new JPasswordField();
		jpfPassCheck = new JPasswordField();
		
		jbtOk = new JButton("Ȯ��");
		jbtCancel = new JButton("���");
		
		JLabel header=new JLabel(headerText.toString());
		JLabel header2=new JLabel("��й�ȣ�� �ٽ� �������ּ���.");
		JLabel password=new JLabel("*��й�ȣ");
		JLabel passwordCheck=new JLabel("*��й�ȣ Ȯ��");
		
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
