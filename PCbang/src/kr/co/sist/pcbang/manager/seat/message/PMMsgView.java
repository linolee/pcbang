package kr.co.sist.pcbang.manager.seat.message;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PMMsgView extends JDialog{

	private JTextArea jtaMsg;
	private JTextField jtfMsg;
	private JButton jbtSendMsg;
	private JScrollPane jspTalkDisplay;
	
	public PMMsgView(PMClient client) {
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

		PMClient client = new PMClient(this);
		jbtSendMsg.addActionListener(client);
		
		
		setBounds(100, 100, 600, 300);
		setVisible(true);
		
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





	public static void main(String[] args) {
		new PMMsgView();
	}
	
}
