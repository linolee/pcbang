package kr.co.sist.pcbang.client.message;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.pcbang.client.main.PUMainController;


@SuppressWarnings("serial")
public class PUMessageView extends JFrame{

	private JTextArea jtaChat;
	private JTextField jtfChat;
	private JScrollPane jspChat;	
//	private JButton jbtSend;
	
	public PUMessageView() {
		super(":::::::::::채팅클라이언트:::::::::::");
		
		jtaChat=new JTextArea();
		jtaChat.setBorder(new TitledBorder("대화내용"));
		jtaChat.setEditable(false);
		jtaChat.setLineWrap(true);
		jtaChat.setWrapStyleWord(true);
		
		jspChat=new JScrollPane(jtaChat);
		
		jtfChat=new JTextField();
		jtfChat.setBorder(new TitledBorder("대화입력"));
		
		add("Center",jspChat);
		add("South",jtfChat);
		
		setBounds(100, 100, 300, 400);
//		setVisible(true);
		jtfChat.requestFocus(); //커서를 jtf에 위치시킨다.
		
	}//SimpleChatServer

	public JTextArea getJtaChat() {
		return jtaChat;
	}

	public JTextField getJtfChat() {
		return jtfChat;
	}

	public JScrollPane getJspChat() {
		return jspChat;
	}

//	public JButton getJbtSend() {
//		return jbtSend;
//	}
	
}//class
