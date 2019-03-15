package kr.co.sist.pcbang.client.message;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class PUMessageView extends JFrame{
	private JTextArea jtaChat;
	private JTextField jtfChat;
	private JScrollPane jspChat;	
	
	public PUMessageView() {
		super(":::::::::::채팅클라이언트:::::::::::");
		
		jtaChat=new JTextArea("");
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

}//class
