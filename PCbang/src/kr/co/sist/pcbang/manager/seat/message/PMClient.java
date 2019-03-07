package kr.co.sist.pcbang.manager.seat.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
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
	private int seatNum;

	public PMClient(Socket client, PMSeatController pmsc) {
//		System.out.println("메세지창생성");
		mv = new PMMsgView(this);
		mv.setBounds(100, 100, 650, 350);
		mv.setVisible(false);//첫 생성에서는 false로 생성
		mv.getJbtSendMsg().addActionListener(this);
		mv.getJbtSendClose().addActionListener(this);
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
		seatNum = setTitle();
		
	}

	private Integer setTitle() {
		seatNum = 0;
		for (int i = 0; i < pmsc.getSeat().length; i++) {
			for (int j = 0; j < pmsc.getSeat()[i].length; j++) {
				if (client.getInetAddress().toString().equals("/"+pmsc.getSeat()[i][j].getPcIP())) {
					seatNum = pmsc.getSeat()[i][j].getSeatNum();
				}
			}
		}
		mv.setTitle(Integer.toString(seatNum)+"번 좌석"+client.getInetAddress().toString());
		return seatNum;
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
				pmsc.getPmsv().getPmov().getPmoc().setOrder();
				pmsc.getPmsv().getPmov().getPmoc().setOrderComplete();
			case "[login]":// 처음 접속했을 때
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				break;
			case "[message]":// 메세지 값이 도착했을 때
				if (mv.isVisible()) {//메세지창이 켜져 이미 켜져있다면
					//Message_Status를 'N'로 바꾼다.
					try {
						pmsc.getPms_dao().chgMsgStatusYtoN(seatNum);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				mv.getJtaMsg().setText(mv.getJtaMsg().getText()+"[상대] : "+temp.substring(temp.indexOf("]") + 1)+"\n");
//				mv.setVisible(true);
//				mv.requestFocus();
				mv.getJspTalkDisplay().getVerticalScrollBar().setValue(mv.getJspTalkDisplay().getVerticalScrollBar().getMaximum());
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				break;
			case "[close]":// 기존 좌석을 로그아웃 해야할 때
				mv.getJtaMsg().setText(temp+"\n");
				closeOrder(Integer.parseInt(temp.substring(temp.indexOf("]") + 1)));
				mv.setVisible(true);
				break;
			case "[logout]":// 사용자가 종료할 때
				mv.getJtaMsg().setText(temp+"\n");
				dropClient();
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				mv.setVisible(true);
				break;
			case "[update time]":
				mv.getJtaMsg().setText(temp+"\n");
				String msg="[update time]";
				writeStream(msg);
				mv.setVisible(true);
				break;
//			default:
//				System.out.println("알 수 없는 형식");
//				break;
			}
		}
	}

	private void writeStream(String info) throws IOException {
		dos.writeUTF(info);
		dos.flush();
	}

	private void sendMsg(String msg) throws IOException {
		if (!msg.equals("")) {//공백이 아닐 경우
			mv.getJtaMsg().append("[관리자] : "+msg.substring(msg.indexOf("]") + 1)+"\n");
			mv.getJtfMsg().setText("");
			msg = "[message]" + msg;
			writeStream(msg);
			mv.getJspTalkDisplay().getVerticalScrollBar().setValue(mv.getJspTalkDisplay().getVerticalScrollBar().getMaximum());
		}else {//공백이 입력될 경우
			mv.getJtfMsg().setText("");
		}
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
			if (pmClient.getClient().getInetAddress().toString().substring(1).equals(ipAddr)) {//해당 ip를 찾아서
				pmClient.getDos().writeUTF("[logout]");//로그아웃 명령을 보낸다.
				pmClient.getDos().flush();
			}
		}
	}

	private void dropClient() {
//		System.out.println("클라이언트 접속종료");
		try {
			if (dis != null) {
				dis.close();
			}
			if (dos != null) {
				dos.close();
			}
			if (client != null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		clientSocketList.remove(this);
//		for (int i = 0; i < clientSocketList.size(); i++) {
//			System.out.println(clientSocketList.get(i));
//		}
		mv.dispose();
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mv.getJbtSendMsg() || ae.getSource() == mv.getJtfMsg()) {//엔터키나 메세지 보내기 버튼이 눌려졌을 때
			try {
				sendMsg(mv.getJtfMsg().getText().trim());
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(mv, "서버가 종료되어 메세지를 전송할 수 없습니다");
				ie.printStackTrace();
			}
		}
		if (ae.getSource() == mv.getJbtSendClose()) {//원격 종료 버튼이 눌려졌을 때
			if (JOptionPane.showConfirmDialog(mv, "해당 사용자를 정말 로그아웃 시키시겠습니까?", "경고", JOptionPane.WARNING_MESSAGE)==JOptionPane.OK_OPTION) {
				try {
					writeStream("[logout]");
				} catch (IOException ie) {
					JOptionPane.showMessageDialog(mv, "서버가 종료되어 메세지를 전송할 수 없습니다");
					ie.printStackTrace();
				}
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

	public PMMsgView getMv() {
		return mv;
	}

	public PMSeatController getPmsc() {
		return pmsc;
	}

	public List<PMClient> getClientSocketList() {
		return clientSocketList;
	}

	public Thread getThread() {
		return thread;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		mv.setVisible(false);//종료되는 대신 visible을 false로 바꿈
	}
	
	
	@Override
	public String toString() {
		return "PMClient [mv=" + mv + ", client=" + client + ", dis=" + dis + ", dos=" + dos + ", clientSocketList="
				+ clientSocketList + ", thread=" + thread + "]";
	}

}
