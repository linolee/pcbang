package kr.co.sist.pcbang.client.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


import kr.co.sist.pcbang.client.main.PUMainController;
import kr.co.sist.pcbang.client.main.PUManager;

public class PUMessageController extends WindowAdapter implements ActionListener {
	private PUMessageView pumv;
	private PUMainController pumc;
	private PUManager pu_manager;
//	private Socket client;
//	private DataInputStream readStream;
	private String id;

	public PUMessageController(PUMessageView pumv, PUMainController pumc, PUManager pu_manager) {
		this.pumv = pumv;
		this.pumc = pumc;
		this.pu_manager = pu_manager;
	}

	@Override
	public void windowClosing(WindowEvent we) {
		pumv.setVisible(false);
	}

	public void sendMsg() throws IOException {
		// �ۼ��� �޼����� �����ͼ�
		String sendMsg = pumv.getJtfChat().getText().trim();
		// ��Ʈ���� ����ϰ�
		pu_manager.getWriteStream().writeUTF("[message]" + sendMsg);
		// ��Ʈ���� ������ �������� ����
		pu_manager.getWriteStream().flush();
		// �ۼ��� �޼����� ä��â�� ����Ѵ�.
		pumv.getJtaChat().append("[��] : " + sendMsg + "\n");
		// T.F�� ������ �����Ѵ�.
		pumv.getJtfChat().setText("");
		pumv.getJspChat().getVerticalScrollBar().setValue(pumv.getJspChat().getVerticalScrollBar().getMaximum());
	}// sendMsg

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			sendMsg();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// actionPerformed

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

//	@Override
//	public void run() {
////		String revMsg="";
////		if(pu_manager.getReadStream() != null) {
////			try {
////				while( true ) {
////					revMsg=pu_manager.getReadStream().readUTF();
////					pumv.getJtaChat().append("[ "+ id+" ] : "+revMsg+"\n");
////					pumv.getJspChat().getVerticalScrollBar().setValue(pumv.getJspChat().getVerticalScrollBar().getMaximum());
////				}//end while
////			}catch(IOException ie) {
////				JOptionPane.showMessageDialog(pumv, id
////															+"�Բ��� �������");
////				ie.printStackTrace();
////			}//end catch
////		}//end if		
//	}//run

}// class
