package kr.co.sist.pcbang.client.message;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PUMessageView extends JDialog {

	private JTextArea jtaMsg;
	private JTextField jtfMsg;
	private JButton jbtSendMsg;
	private JScrollPane jspTalkDisplay;
	
	public PUMessageView() {
		jtaMsg = new JTextArea();
		jtfMsg = new JTextField();
		jbtSendMsg = new JButton();
		jspTalkDisplay = new JScrollPane();
	}
	
	
}
