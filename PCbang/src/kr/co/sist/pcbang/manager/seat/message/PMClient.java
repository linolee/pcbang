package kr.co.sist.pcbang.manager.seat.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
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
		System.out.println("메세지창생성");
		mv = new PMMsgView(this);
		mv.setBounds(100, 100, 600, 300);
		mv.setVisible(false);//false로 바꿔주기
		// actionListener 달아주기
		mv.getJbtSendMsg().addActionListener(this);
		mv.getJtfMsg().addActionListener(this);
		this.client = client;
		this.dis = dis;
		this.dos = dos;
		this.clientSocketList = clientSocketList;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		try {
			readStream();
		} catch (SocketException ie) {
			dropClient();
		} catch (IOException ie) {
			ie.printStackTrace();
		} // end catch
	}// run

	private void readStream() throws IOException, SocketException {
		String temp;
		String flag;
		while (true) {
			temp = dis.readUTF();
			flag = temp.substring(0, temp.indexOf("]") + 1);// [order]
			switch (flag) {
			case "[order]":// 주문을 넣었을 때

				break;
			case "[message]":// 메세지 값이 도착했을 때
				System.out.println("메시지 도착");
				
				mv.getJtaMsg().setText(mv.getJtaMsg().getText()+temp+"\n");
				mv.setVisible(true);
				break;
			case "[close]":// 기존 좌석을 로그아웃 해야할 때

				break;

			default:
				break;
			}
		}
	}

	private void writeStream(String info) throws IOException {
		dos.writeUTF(info);
		dos.flush();
	}

	private void sendMsg(String msg) throws IOException {
		msg = "[message]" + msg;
		writeStream(msg);
	}

	private void closeOrder() throws IOException {//해당 pc에 종료명령을 내리는 메소드

	}

	private void dropClient() {
		clientSocketList.remove(this);
		System.out.println("클라이언트 접속종료");
		for (PMClient pmClient : clientSocketList) {
			System.out.println(pmClient);
		}
		mv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mv.getJbtSendMsg() || ae.getSource() == mv.getJtfMsg()) {
			try {
				sendMsg(mv.getJtfMsg().getText().trim());
				System.out.println("메세지 전송");
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(mv, "서버가 종료되어 메세지를 전송할 수 없습니다");
				ie.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return "PMClient [mv=" + mv + ", client=" + client + ", dis=" + dis + ", dos=" + dos + ", clientSocketList="
				+ clientSocketList + ", thread=" + thread + "]";
	}

}
