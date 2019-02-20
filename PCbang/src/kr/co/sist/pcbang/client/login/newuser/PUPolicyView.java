package kr.co.sist.pcbang.client.login.newuser;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class PUPolicyView extends JFrame{

	private JCheckBox jcb1, jcb2, jcb3;
	private JTextArea jtaCare1, jtaCare2, jtaCare3;
	private JButton jbtOk, jbtcancel;
	private JLabel msgJoin1, msgJoin2, msgJoin3, msgJoin4, msgJoin5;
	
	public PUPolicyView() {
		super("�� �������� ��ȣ");	
		jcb1 = new JCheckBox("�����ϴ� �������� �׸� �����մϴ�.");
		jcb2 = new JCheckBox("�������� ���� �� �̿����, �̿�Ⱓ�� �����մϴ�.");
		jcb3 = new JCheckBox("�������� ��� ��Ź�� �����մϴ�.");
		
		jbtOk = new JButton("Ȯ��");
		jbtcancel = new JButton("���");
		
		jtaCare1 = new JTextArea();
		jtaCare2 = new JTextArea();
		jtaCare3 = new JTextArea();
				
		JScrollPane jspCare1 = new JScrollPane(jtaCare1);
		JScrollPane jspCare2 = new JScrollPane(jtaCare2);
		JScrollPane jspCare3 = new JScrollPane(jtaCare3);
		
		msgJoin1 = new JLabel();
		msgJoin2 = new JLabel();
		msgJoin3 = new JLabel();
		msgJoin4 = new JLabel();
		msgJoin5 = new JLabel();
		
		setLayout(null);
		msgJoin1.setText("����������ȣ�����࿡ ���� �������� ���� �� �̿뿡 ���� ����");
		msgJoin2.setText("Ȯ�� �� �����ؾ� ȸ�������� �����մϴ�.");
		msgJoin3.setText("������ �ֹε�Ϲ��� ���� Ÿ���� �ֹε�Ϲ�ȣ�� ���� ���");
		msgJoin4.setText("�ϴ� �ڴ� 3�� ������ ¡�� �Ǵ� 1õ ���� ������ ������ �ΰ���");
		msgJoin5.setText("�� �ֽ��ϴ�.");
		
		jtaCare1.setText("�����ϴ� ���������� �׸�\n" +
				"�����ϴ� ����/����� ���� �����ϴ� �������� �׸��� ������ �����ϴ�.\n" + 
				" - �⺻ �������� ���� \r\n" + 
				" - �̸�, �ֹε�Ϲ�ȣ\r\n" + 
				" - �α��� ID, ��й�ȣ\r\n" + 
				" - ������ȭ��ȣ, �����ּ�, �޴���ȭ��ȣ\r\n" + 
				" - �̸���, �̸��� ���ſ���\r\n" + 
				" - ������/���� �̿��� ���� ����\r\n" + 
				" - ȸ���, �μ�/��å, ȸ����ȭ��ȣ, �ֹε�Ϲ�ȣ\r\n" + 
				" - ���� �̿� �� �߻��Ǵ� ����\r\n" + 
				" - ���� �̿���, ���ӷα�, ��Ű\r\n" + 
				" - ������ܿ� ���� ��Ͽ���(����), ������");
		
		jtaCare2.setText("�������� ���� �� �̿� ����\n" + 
				"PC���� ������ ���������� ������ ������ ���� Ȱ���մϴ�.\r\n" + 
				" - ���� ������ ���� ��� ���� �� ���� ������ ���� ������� ����\r\n" + 
				"PC�̿�, ������ ����, ���� �� ��� ����, ��ǰ����\r\n" + 
				" - ȸ�� ����\r\n" + 
				"ȸ���� ���� �̿뿡 ���� ����Ȯ��, ���� �ĺ�, �ҷ�ȸ���� ���� �̿� ������\r\n" + 
				"���ΰ���� ����, ���� �ǻ� Ȯ��, ����Ȯ��, �Ҹ�ó�� �� �ο�ó��, �������� ����\r\n" + 
				" - ������ �� ���� Ȱ��\r\n" + 
				"�ű� ����(��ǰ) ���� �� Ưȭ, �̺�Ʈ �� ���� ���� ����, �α�������� Ư���� ����\r\n" + 
				"���� ���� �� ���� ����, ���� �� �ľ� �Ǵ� ȸ���� ���� �̿뿡 ���� ��� \r\n");
		
		jtaCare3.setText("���� ���� ���� �� �̿�Ⱓ\n" + 
				"��Ģ������, �������� ���� �� �̿������ �޼��� �Ŀ��� �ش� ������ ��ü ���� �ı��մϴ�.\r\n" + 
				"��, ������ ������ ���ؼ��� �Ʒ��� ������ ����� �Ⱓ ���� �����մϴ�. \r\n" + 
				"���� �׸� : �̸�, ���� ��ȭ��ȣ, �����ּ�, �޴���ȭ��ȣ, �̸���, ȸ���, �μ�, ��å, ȸ����ȭ��ȣ, �ֹε�Ϲ�ȣ\r\n" + 
				"���� �ٰ� : ��뺸�� ȯ�� ������ ����\r\n" + 
				"���� �Ⱓ : 3�� \r\n" + 
				"�׸��� ��������� ������ ���Ͽ� ������ �ʿ䰡 �ִ� ��� ȸ��� �Ʒ��� ���� ������ɿ��� ���� ������ �Ⱓ ���� ȸ�������� �����մϴ�.\r\n" + 
				"1) ��Ÿ ���ɿ� ���� �����Ⱓ/����� �ȳ�\r\n" + 
				"- ��� �Ǵ� û��öȸ � ���� ��� : 5�� (���ڻ�ŷ� ����� �Һ��� ��ȣ�� ���� ����)\r\n" + 
				"- ��ݰ��� �� ��ȭ ���� ���޿� ���� ��� : 5�� (���ڻ�ŷ� ����� �Һ��� ��ȣ�� ���� ����)\r\n" + 
				"- �Һ����� �Ҹ� �Ǵ� ����ó���� ���� ��� : 3�� (���ڻ�ŷ� ����� �Һ��� ��ȣ�� ���� ����)\r\n" + 
				"- ����Ȯ�ο� ���� ��� ���� ���� : ������� �̿����� �� ������ȣ � ���� ���� ���� �Ⱓ : 6���� \r\n" + 
				"- �湮�� ���� ��� ���� ���� : ��� ��� ��ȣ�� ���� �Ⱓ : 3����");
		
		msgJoin1.setBounds(10, 10, 375, 30);
		msgJoin2.setBounds(10, 25, 375, 30);
		msgJoin3.setBounds(10, 60, 375, 30);
		msgJoin4.setBounds(10, 75, 375, 30);
		msgJoin5.setBounds(10, 90, 375, 30);
		jspCare1.setBounds(10, 130, 375, 110);
		jspCare2.setBounds(10, 270, 375, 110);
		jspCare3.setBounds(10, 410, 375, 110);
		jcb1.setBounds(10, 240, 350, 30);
		jcb2.setBounds(10, 380, 350, 30);
		jcb3.setBounds(10, 520, 350, 30);
		jbtOk.setBounds(90, 580, 80, 40);
		jbtcancel.setBounds(230, 580, 80, 40);
		
		add(msgJoin1);
		add(msgJoin2);
		add(msgJoin3);
		add(msgJoin4);
		add(msgJoin5);
		add(jspCare1);
		add(jspCare2);
		add(jspCare3);
		add(jcb1);
		add(jcb2);
		add(jcb3);
		add(jbtOk);
		add(jbtcancel);

		msgJoin3.setForeground(Color.RED);
		msgJoin4.setForeground(Color.RED);
		msgJoin5.setForeground(Color.RED);
		
		jtaCare1.setBackground(Color.white);
		jtaCare2.setBackground(Color.white);
		jtaCare3.setBackground(Color.white);
		
		jtaCare1.setEditable(false);
		jtaCare2.setEditable(false);
		jtaCare3.setEditable(false);
		
		jtaCare3.setOpaque(true);
		jtaCare2.setOpaque(true);
		jtaCare3.setOpaque(true);

		PUPolicyController pupc = new PUPolicyController(this);
		addWindowListener(pupc);
		jcb1.addActionListener(pupc);
		jcb2.addActionListener(pupc);
		jcb3.addActionListener(pupc);
		jbtOk.addActionListener(pupc);
		jbtcancel.addActionListener(pupc);
		
		setBounds(800, 200, 400, 700);
		setVisible(true);
		setResizable(false);
				
		
	} // PUPolicyView
	
	public JCheckBox getJcb1() {
		return jcb1;
	}

	public JCheckBox getJcb2() {
		return jcb2;
	}

	public JCheckBox getJcb3() {
		return jcb3;
	}

	public JTextArea getJtaCare1() {
		return jtaCare1;
	}

	public JTextArea getJtaCare2() {
		return jtaCare2;
	}

	public JTextArea getJtaCare3() {
		return jtaCare3;
	}

	public JButton getJbtOk() {
		return jbtOk;
	}

	public JButton getJbtcancel() {
		return jbtcancel;
	}

} // class
