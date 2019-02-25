package kr.co.sist.pcbang.client.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.message.PUMessageController;
import kr.co.sist.pcbang.client.message.PUMessageView;

public class PUManager {

	private DataInputStream readStram;
	private DataOutputStream writeStream;
	private Socket client;

	public PUManager(PUMainController pumc) {
		try {
			connectToServer();//매니저와 접속하고
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//메세지창을 만듦
		PUMessageView pumsgv = new PUMessageView();//뷰 생성
		PUMessageController pumsgc = new PUMessageController(pumsgv, pumc, this);//컨트롤러 생성
		
		//뷰에 액션리스너 달기
		pumsgv.getJtfChat().addActionListener(pumsgc);
//		pumsgv.getJbtSend().addActionListener(pumsgc);
		
	}

	public void connectToServer() throws UnknownHostException, IOException {
		try {
			if (client == null) {
				String serverIp = "211.63.89.152"; // 고정 서버 IP
				client = new Socket(serverIp, 55000); // 입력한 ip address의 컴퓨터에 연결.

				readStram = new DataInputStream(client.getInputStream());
				writeStream = new DataOutputStream(client.getOutputStream());
				writeStream.writeUTF("[message]:fffffffff");
				writeStream.flush();
			} else {
				JOptionPane.showMessageDialog(null, "error code #9980");
			}

		} catch (ConnectException ce) {
			JOptionPane.showMessageDialog(null, "서버에 접속할 수 없습니다");
		}
	}

	public DataInputStream getReadStram() {
		return readStram;
	}

	public DataOutputStream getWriteStream() {
		return writeStream;
	}

	public Socket getClient() {
		return client;
	}
	
}
