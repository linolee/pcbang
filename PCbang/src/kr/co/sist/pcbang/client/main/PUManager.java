package kr.co.sist.pcbang.client.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.message.PUMessageController;
import kr.co.sist.pcbang.client.message.PUMessageView;

public class PUManager extends Thread{

	private DataInputStream readStram;
	private DataOutputStream writeStream;
	private Socket client;
	private PUMessageView pumsgv;

	public PUManager(PUMainController pumc) {
		try {
			connectToServer();//매니저와 접속하고
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//메세지창을 만듦
		pumsgv = new PUMessageView();//뷰 생성
		PUMessageController pumsgc = new PUMessageController(pumsgv, pumc, this);//컨트롤러 생성
		pumsgv.setVisible(false);
		
		//뷰에 액션리스너 달기
		pumsgv.getJtfChat().addActionListener(pumsgc);
//		pumsgv.getJbtSend().addActionListener(pumsgc);
		
		this.start();
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

	public void run() {
		try {
			readStream();
		} catch (SocketException ie) {
			ie.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} // end catch
	}// run

	private void readStream() throws IOException, SocketException {
		String temp;
		String flag;
		while (true) {
			temp = readStram.readUTF();
			flag = temp.substring(0, temp.indexOf("]") + 1);// [order]
			switch (flag) {
			case "[message]":// 메세지 값이 도착했을 때
				System.out.println("메시지 도착");
				
				pumsgv.getJtaChat().setText(pumsgv.getJtaChat().getText()+temp+"\n");
				pumsgv.setVisible(true);
				break;
			case "[close]":// 기존 좌석을 로그아웃 해야할 때
				//closeOrder(Integer.parseInt(temp.substring(temp.indexOf("]") + 1)));
				break;

			default:
				break;
			}
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

	public PUMessageView getPumsgv() {
		return pumsgv;
	}
	
	
}
