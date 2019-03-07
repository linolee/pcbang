package kr.co.sist.pcbang.manager.seat.message;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PMMsgView extends JFrame {

	private JTextArea jtaMsg;
	private JTextField jtfMsg;
	private JButton jbtSendMsg, jbtSendClose;
	private JScrollPane jspTalkDisplay;

	public PMMsgView(PMClient client) {
		//super(client.);//Ŭ���̾�Ʈ�� �¼���ȣ�� IP�ּ� ǥ��////////////////////////////////////////
		jtaMsg = new JTextArea();
		jtfMsg = new JTextField(40);
		jbtSendMsg = new JButton("����");
		jbtSendClose = new JButton("���� ����");
		jbtSendClose.setBackground(Color.ORANGE);
		jspTalkDisplay = new JScrollPane();

		jspTalkDisplay = new JScrollPane(jtaMsg);
		jspTalkDisplay.setBorder(new TitledBorder("��ȭ����"));

		jtaMsg.setEditable(false);

		JPanel pnlS = new JPanel();
		pnlS.add(jbtSendClose);
		pnlS.add(jtfMsg);
		pnlS.add(jbtSendMsg);

		add("Center", jspTalkDisplay);
		add("South", pnlS);

	}

	public JTextArea getJtaMsg() {
		return jtaMsg;
	}

	public JTextField getJtfMsg() {
		return jtfMsg;
	}

	public JButton getJbtSendMsg() {
		return jbtSendMsg;
	}

	public JScrollPane getJspTalkDisplay() {
		return jspTalkDisplay;
	}

	public JButton getJbtSendClose() {
		return jbtSendClose;
	}

}
