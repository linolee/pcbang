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
		//작성된 메세지를 가져와서
		String sendMsg=pumv.getJtfChat().getText().trim();
		//스트림에 기록하고
		pumc.os.writeUTF(sendMsg);
		//스트림의 내용을 목적지로 분출
		pumc.os.writeStream.flush();
		//작성된 메세지를 채팅창에 출력한다.
		pumv.getJtaChat().append("[ "+id+" ] : "+sendMsg+"\n");
		//T.F의 내용을 삭제한다.
		pumv.getJtfChat().setText("");
		pumv.getJspChat().getVerticalScrollBar().setValue(pumv.getJspChat().getVerticalScrollBar().getMaximum());
	}//sendMsg
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		sendMsg();
	}//actionPerformed

//	public void connectToServer()throws IOException {
//		clientNick=JOptionPane.showInputDialog("대화명 입력");
//		//2.
////		client=new Socket("211.63.89.134", 65535);
//		client=new Socket("211.63.89.132", 35000);
//		
//		//4. 스트림 연결
//		readStream=new DataInputStream(client.getInputStream());
//		writeStream=new DataOutputStream(client.getOutputStream());
//		
//		//서버로 대화명 전달
//		writeStream.writeUTF( clientNick );
//		jta.setText("대화방에 \""+clientNick+"\"으로 입장하셨습니다.\n");
//		//서버의 대화명을 저장
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
															+"님께서 사용종료");
				ie.printStackTrace();
			}//end catch
		}//end if		
	}//run

}//class
