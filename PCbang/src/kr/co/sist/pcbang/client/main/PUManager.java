package kr.co.sist.pcbang.client.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.message.PUMessageController;
import kr.co.sist.pcbang.client.message.PUMessageView;

public class PUManager extends Thread {

	private DataInputStream readStream;
	private DataOutputStream writeStream;
	private Socket client;
	private PUMessageView pumsgv;
	private Thread thread;
	private PUMainController pumc;
	private PUMainDAO pum_dao;

	public PUManager(PUMainController pumc) {
		this.pumc=pumc;
		try {
			connectToServer();// 매니저와 접속하고
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 메세지창을 만듦
		pumsgv = new PUMessageView();// 뷰 생성
		PUMessageController pumsgc = new PUMessageController(pumsgv, pumc, this);// 컨트롤러 생성
		pumsgv.setVisible(false);

		// 뷰에 액션리스너 달기
		pumsgv.getJtfChat().addActionListener(pumsgc);
		pumsgv.addWindowListener(pumsgc);

		thread = new Thread(this);
		thread.start();
	}

	public void connectToServer() throws UnknownHostException, IOException {
		try {
			if (client == null) {
				String serverIp = "211.63.89.130"; // 고정 서버 IP
				client = new Socket(serverIp, 55000); // 입력한 ip address의 컴퓨터에 연결.

				readStream = new DataInputStream(client.getInputStream());
				writeStream = new DataOutputStream(client.getOutputStream());
				writeStream.writeUTF("[login]");//처음 접속했을 때 정보를 전달
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

	public void updateTimeMsg(String id) throws IOException {
			// 스트림에 기록하고
			writeStream.writeUTF("[update time]"+id);
			// 스트림의 내용을 목적지로 분출
			writeStream.flush();
	}	
	private void readStream() throws IOException, SocketException {
		String temp;
		String flag;
		
		while (true) {
			temp = readStream.readUTF();
			flag = temp.substring(0, temp.indexOf("]") + 1);// [order]
			switch (flag) {
			case "[message]":// 메세지 값이 도착했을 때
				pumsgv.getJtaChat().append("[관리자] : "+temp.substring(temp.indexOf("]") + 1)+"\n");
				pumsgv.setVisible(true);
				pumsgv.requestFocus();
				pumsgv.getJspChat().getVerticalScrollBar().setValue(pumsgv.getJspChat().getVerticalScrollBar().getMaximum());
				break;
			case "[logout]":
				updateTimeMsg(pumc.getId());
				pumc.logout();
				break;
			case "[update time]":
				String cardNum=pumc.getCard();
				String id=pumc.getId();
				try {
					pum_dao.selectInfo(id, cardNum);
					PUMainInfoVO puminfovo=pum_dao.selectInfo(id,cardNum);
					int restTime=Integer.parseInt(puminfovo.getRestTime());
					pumc.setRestTime(restTime);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			default:
				System.out.println("알 수 없는 형식");
				break;
			}
		}
	}
	
	public DataInputStream getReadStream() {
		return readStream;
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
