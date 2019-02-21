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
import javax.swing.JTextField;

public class PMClient extends WindowAdapter implements Runnable, ActionListener {

	private PMMsgView mv;
	private Socket client;
	private DataInputStream dis;
	private DataOutputStream dos;
	private List<PMClient> clientSocketList;
	private Thread thread;
	
	public PMClient(Socket client, DataInputStream dis, DataOutputStream dos, List<PMClient> clientSocketList) {
		mv = new PMMsgView(this);
		//actionListener 달아주기
		this.client = client;
		this.dis = dis;
		this.dos = dos;
		this.clientSocketList = clientSocketList;
		thread = new Thread(this);
		thread.start();
	}

//	@Override
//	public void run() {
//		try {
//			readStream();
//			dos.writeUTF("확인");
//			dos.flush();
//		} catch (IOException e) {
//			System.out.println("클라이언트 종료");
//			dropClient();
//		}
//	} // run

	public void run() {
		if (dis != null) {
			try {
				String revMsg = "";
				while (true) { // 서버에서 보내오는 모든 메세지를 읽어서, 모든 접속자에 뿌린다.
					revMsg = dis.readUTF();
				} // end while
			} catch (IOException ie) {
				// 접속자가 퇴실하면 해당 접속자를 리스트에서 삭제한다.
				clientSocketList.remove(this);
				// 메세지를 읽어들이지 못하는 상태라면 접속자가 연결을 종료한 상태
				ie.printStackTrace();
			} // end catch
		} // end if
	}// run

	private void readStream() throws IOException {
		while (true) {
			System.out.println(dis.readUTF());
		}
	}

	private void sendMsg() throws IOException {
		if (dos != null) {
			JTextField jtf = mv.getJtfMsg();
			String msg = jtf.getText().trim();

			if (!msg.equals("")) {
				dos.writeUTF(msg);
				dos.flush();
			}
			jtf.setText("");
		}
	}

	private void dropClient() {
		clientSocketList.remove(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mv.getJbtSendMsg()) {
			try {
				sendMsg();
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(mv, "서버가 종료되어 메세지를 전송할 수 없습니다");
				ie.printStackTrace();
			}
		}
	}

}
