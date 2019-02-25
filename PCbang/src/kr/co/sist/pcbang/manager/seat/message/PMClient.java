package kr.co.sist.pcbang.manager.seat.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.manager.seat.PMSeatController;
import kr.co.sist.pcbang.manager.seat.PMSeatVO;

public class PMClient extends WindowAdapter implements Runnable, ActionListener {

	private PMMsgView mv;
	private Socket client;
	private DataInputStream dis;
	private DataOutputStream dos;
	private PMSeatController pmsc;
	private List<PMClient> clientSocketList;
	private Thread thread;

	public PMClient(Socket client, PMSeatController pmsc) {
		System.out.println("메세지창생성");
		mv = new PMMsgView(this);
		mv.setBounds(100, 100, 600, 300);
		mv.setVisible(false);//false로 바꿔주기
		// actionListener 달아주기
		mv.getJbtSendMsg().addActionListener(this);
		mv.getJtfMsg().addActionListener(this);
		this.client = client;
		try {
			dis = new DataInputStream(client.getInputStream());
			dos = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.pmsc = pmsc;
		clientSocketList = pmsc.getClientSocket();
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
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				break;
			case "[message]":// 메세지 값이 도착했을 때
				System.out.println("메시지 도착");
				
				mv.getJtaMsg().setText(mv.getJtaMsg().getText()+temp+"\n");
				mv.setVisible(true);
				break;
			case "[close]":// 기존 좌석을 로그아웃 해야할 때
				closeOrder(Integer.parseInt(temp.substring(temp.indexOf("]") + 1)));
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

	private void closeOrder(int seatNum) throws IOException {//해당 pc에 종료명령을 내리는 메소드
		String ipAddr = "";
		
		PMSeatVO[][] seat = pmsc.getSeat();//현재 좌석 중에서
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (seatNum == seat[i][j].getSeatNum()) {//받은 좌석번호와 동일한 좌석정보를 찾아서
					ipAddr = seat[i][j].getPcIP();//ip를 저장하고
				}
			}
		}
		
		for (Iterator<PMClient> iterator = clientSocketList.iterator(); iterator.hasNext();) {//clientSocketList에서
			PMClient pmClient = (PMClient) iterator.next();
			if (pmClient.getClient().getInetAddress().toString().equals(ipAddr)) {//해당 ip를 찾아서
				pmClient.getDos().writeUTF("[logout]");//로그아웃 명령을 보낸다.
				pmClient.getDos().flush();
			}
		}
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
	
	public Socket getClient() {
		return client;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	@Override
	public String toString() {
		return "PMClient [mv=" + mv + ", client=" + client + ", dis=" + dis + ", dos=" + dos + ", clientSocketList="
				+ clientSocketList + ", thread=" + thread + "]";
	}

}
