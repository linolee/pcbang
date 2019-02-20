package kr.co.sist.pcbang.manager.seat.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PMClient extends Thread implements Runnable, ActionListener{
	
	private PMMsgView mv;
	private Socket client;
	private DataInputStream dis;
	private DataOutputStream dos;
	private List<PMClient> clientSocketList;
	
	public PMClient(Socket client, DataInputStream dis, DataOutputStream dos, List<PMClient> clientSocketList) {
		mv = new PMMsgView(this);
		this.client = client;
		this.dis = dis;
		this.dos = dos;
		this.clientSocketList = clientSocketList;
	}


	@Override
	public void run() {
		
	} // run
	
	public void readStream() {
		
	}
	
	
	public void sendMsg() throws IOException {
		if(dos!=null) {
			JTextField jtf = mv.getJtfMsg();
			String msg = jtf.getText().trim();
			
			if(!msg.equals("")) {
				dos.writeUTF(msg);
				dos.flush();
			}
			jtf.setText("");
		}
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == mv.getJbtSendMsg()) {
			try {
				sendMsg();
			} catch(IOException ie) {
				JOptionPane.showMessageDialog(mv,  "서버가 종료되어 메세지를 전송할 수 없습니다");
				ie.printStackTrace();
			}
		}
	}
	
	
	
	
}
