package kr.co.sist.pcbang.client.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.main.PUMainController;

public class PUMessageController extends WindowAdapter implements ActionListener, Runnable {
	private PUMessageView pumv;
	private PUMainController pumc;
	private Socket client;
	private DataInputStream readStream;
	private String id;
	
	public PUMessageController(PUMessageView pumv, PUMainController pumc) {
		this.pumv=pumv;
		this.pumc=pumc;
	}
	
	@Override
	public void windowClosing(WindowEvent we) {
		pumv.setVisible(false);
	}
	
	public void sendMsg() throws IOException {
		//�ۼ��� �޼����� �����ͼ�
		String sendMsg=pumv.getJtfChat().getText().trim();
		//��Ʈ���� ����ϰ�
		pumc.os.writeUTF(sendMsg);
		//��Ʈ���� ������ �������� ����
		pumc.os.writeStream.flush();
		//�ۼ��� �޼����� ä��â�� ����Ѵ�.
		pumv.getJtaChat().append("[ "+id+" ] : "+sendMsg+"\n");
		//T.F�� ������ �����Ѵ�.
		pumv.getJtfChat().setText("");
		pumv.getJspChat().getVerticalScrollBar().setValue(pumv.getJspChat().getVerticalScrollBar().getMaximum());
	}//sendMsg
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		sendMsg();
	}//actionPerformed

//	public void connectToServer()throws IOException {
//		clientNick=JOptionPane.showInputDialog("��ȭ�� �Է�");
//		//2.
////		client=new Socket("211.63.89.134", 65535);
//		client=new Socket("211.63.89.132", 35000);
//		
//		//4. ��Ʈ�� ����
//		readStream=new DataInputStream(client.getInputStream());
//		writeStream=new DataOutputStream(client.getOutputStream());
//		
//		//������ ��ȭ�� ����
//		writeStream.writeUTF( clientNick );
//		jta.setText("��ȭ�濡 \""+clientNick+"\"���� �����ϼ̽��ϴ�.\n");
//		//������ ��ȭ���� ����
//		serverNick=readStream.readUTF();
//		
//	}//connectToServer
	
	@Override
	public void run() {
		String revMsg="";
		if(readStream != null) {
			try {
				while( true ) {
					revMsg=readStream.readUTF();
					pumv.getJtaChat().append("[ "+ id+" ] : "+revMsg+"\n");
					pumv.getJspChat().getVerticalScrollBar().setValue(pumv.getJspChat().getVerticalScrollBar().getMaximum());
				}//end while
			}catch(IOException ie) {
				JOptionPane.showMessageDialog(pumv, id
															+"�Բ��� �������");
				ie.printStackTrace();
			}//end catch
		}//end if		
	}//run

}//class
