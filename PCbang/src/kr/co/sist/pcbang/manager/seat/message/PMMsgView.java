package kr.co.sist.pcbang.manager.seat.message;

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
	private JButton jbtSendMsg;
	private JScrollPane jspTalkDisplay;

	public PMMsgView(PMClient client) {
		//super(client.);//클라이언트의 좌석번호랑 IP주소 표시////////////////////////////////////////
		jtaMsg = new JTextArea();
		jtfMsg = new JTextField(45);
		jbtSendMsg = new JButton("전송");
		jspTalkDisplay = new JScrollPane();

		jspTalkDisplay = new JScrollPane(jtaMsg);
		jspTalkDisplay.setBorder(new TitledBorder("대화내용"));

		jtaMsg.setEditable(false);

		JPanel pnlS = new JPanel();
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

}
