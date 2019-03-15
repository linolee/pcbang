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
		super(":::::::::::ä��Ŭ���̾�Ʈ:::::::::::");
		
		jtaChat=new JTextArea("");
		jtaChat.setBorder(new TitledBorder("��ȭ����"));
		jtaChat.setEditable(false);
		jtaChat.setLineWrap(true);
		jtaChat.setWrapStyleWord(true);
		
		jspChat=new JScrollPane(jtaChat);
		
		jtfChat=new JTextField();
		jtfChat.setBorder(new TitledBorder("��ȭ�Է�"));
		
		add("Center",jspChat);
		add("South",jtfChat);
		
		setBounds(100, 100, 300, 400);
		jtfChat.requestFocus(); //Ŀ���� jtf�� ��ġ��Ų��.
		
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
