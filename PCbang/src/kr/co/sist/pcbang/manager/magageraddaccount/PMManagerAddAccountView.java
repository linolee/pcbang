package kr.co.sist.pcbang.manager.magageraddaccount;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class PMManagerAddAccountView extends JFrame{

	private JButton jbtAdd, jbtCancle;
	private JTextField jtfAddId, jtfAddName;
	private JPasswordField jpfAddPass;
	
	
	public PMManagerAddAccountView() {
		super("������ ���� �߰�");
		
		jtfAddId = new JTextField();
		jtfAddName = new JTextField();
		jpfAddPass = new JPasswordField();
		jbtAdd = new JButton("�߰�");
		jbtCancle = new JButton("���");
		JLabel jlAddId = new JLabel("���̵�");
		JLabel jlAddPass = new JLabel("�н�����");
		JLabel jlname = new JLabel("�̸�");
		
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
		
		PMManagerAddAccountController pmmaac = new PMManagerAddAccountController(this);
		addWindowListener(pmmaac);
		jbtCancle.addActionListener(pmmaac);
		jtfAddId.addActionListener(pmmaac);
		jpfAddPass.addActionListener(pmmaac);
		jtfAddName.addActionListener(pmmaac);
		jbtAdd.addActionListener(pmmaac);
		
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