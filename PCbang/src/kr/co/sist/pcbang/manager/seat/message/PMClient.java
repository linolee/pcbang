package kr.co.sist.pcbang.manager.seat.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.manager.login.PCRoomManagerRun;
import kr.co.sist.pcbang.manager.order.PMOrderDAO;
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
	private PMMessageDAO pmm_dao;

	public PMClient(Socket client, PMSeatController pmsc) {
//		System.out.println("메세지창생성");
		mv = new PMMsgView(this);
		mv.setBounds(100, 100, 650, 350);
		mv.setVisible(false);// 첫 생성에서는 false로 생성
		mv.getJbtSendMsg().addActionListener(this);
		mv.getJbtSendClose().addActionListener(this);
		mv.getJbtOrderDone().addActionListener(this);
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
		pmm_dao = PMMessageDAO.getInstance();

	}

	private Integer setTitle() {
		seatNum = 0;
		for (int i = 0; i < pmsc.getSeat().length; i++) {
			for (int j = 0; j < pmsc.getSeat()[i].length; j++) {
				if (client.getInetAddress().toString().equals("/" + pmsc.getSeat()[i][j].getPcIP())) {
					seatNum = pmsc.getSeat()[i][j].getSeatNum();
				}
			}
		}
		mv.setTitle(Integer.toString(seatNum) + "번 좌석" + client.getInetAddress().toString());
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
				if (mv.isVisible()) {// 메세지창이 켜져 이미 켜져있다면
					// Message_Status를 'N'로 바꾼다.
					try {
						pmsc.getPms_dao().chgMsgStatusYtoN(seatNum);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				mv.getJtaMsg()
						.setText(mv.getJtaMsg().getText() + "[상대] : " + temp.substring(temp.indexOf("]") + 1) + "\n");
//				mv.setVisible(true);
//				mv.requestFocus();
				mv.getJspTalkDisplay().getVerticalScrollBar()
						.setValue(mv.getJspTalkDisplay().getVerticalScrollBar().getMaximum());
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				break;
			case "[close]":// 기존 좌석을 로그아웃 해야할 때
				closeOrder(Integer.parseInt(temp.substring(temp.indexOf("]") + 1)));
				break;
			case "[logout]":// 사용자가 종료할 때
				dropClient();
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				break;
			case "[update time]":
				updateOrder(temp.substring(temp.indexOf("]") + 1));
				break;
			case "[fileList]":// 클라이언트에서 파일리스트가 오면
				// 파일리스트 문자열을 배열로 쪼개서
				List<String> clientFileList = recieveClientFileList(temp.substring(temp.indexOf("]") + 2));
				// 가지고 있는 파일 문자열과 비교
				for (int i = 0; i < PCRoomManagerRun.PrdImgList.size(); i++) {
					System.out.println(PCRoomManagerRun.PrdImgList.get(i)+"있는파일");
					if (clientFileList.contains(PCRoomManagerRun.PrdImgList.get(i))) {
						clientFileList.remove("s_" + PCRoomManagerRun.PrdImgList.get(i));
					}
				}
				
				for (Iterator iterator = clientFileList.iterator(); iterator.hasNext();) {
					System.out.println((String) iterator.next());
					System.out.println("없는파일");
//					fileSend(fname, dos);
				}
				// 클라이언트에 없는 파일 리스트는 보내준다.
				break;
//			default:
//				System.out.println("알 수 없는 형식");
//				break;
			}//end switch
		}//end while
	}

	private void fileSend(String fname, DataOutputStream dos) throws IOException {

		FileInputStream fis = null;

		try {
			int fileData = 0;

			String path = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String abspath = path + filepath;

			fis = new FileInputStream(abspath + fname);
			byte[] readData = new byte[512];

			int fileLen = 0;
			while ((fileLen = fis.read(readData)) != -1) {
				fileData++;
			} // end while

			fis.close();
			dos.writeInt(fileData);
			dos.flush();

			dos.writeUTF(fname);// writeUTF

			fis = new FileInputStream(abspath + fname);
			while ((fileLen = fis.read(readData)) != -1) {// 서버에서 전송한 파일조각을 읽어들여
				dos.write(readData, 0, fileLen);// 생성한 파일로 기록
			} // end while
			dos.flush();
			System.out.println("결과 "+	dis.readUTF());
		} finally {
			if (fis != null) {
				fis.close();
			} // end if
		} // end finally

	}// fileSend
	
	private List<String> recieveClientFileList(String fileList) {
		List<String> clientFileList = new ArrayList<>();
		String[] clientFile = fileList.split("/");
		for (int i = 0; i < clientFile.length; i++) {
			clientFileList.add(clientFile[i]);
		}
		return clientFileList;
	}

	private void writeStream(String info) throws IOException {
		dos.writeUTF(info);
		dos.flush();
	}

	private void sendMsg(String msg) throws IOException {
		if (!msg.equals("")) {// 공백이 아닐 경우
			mv.getJtaMsg().append("[관리자] : " + msg.substring(msg.indexOf("]") + 1) + "\n");
			mv.getJtfMsg().setText("");
			msg = "[message]" + msg;
			writeStream(msg);
			mv.getJspTalkDisplay().getVerticalScrollBar()
					.setValue(mv.getJspTalkDisplay().getVerticalScrollBar().getMaximum());
		} else {// 공백이 입력될 경우
			mv.getJtfMsg().setText("");
		}
	}

	private void closeOrder(int seatNum) throws IOException {// 해당 pc에 종료명령을 내리는 메소드
		String ipAddr = "";

		PMSeatVO[][] seat = pmsc.getSeat();// 현재 좌석 중에서
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (seatNum == seat[i][j].getSeatNum()) {// 받은 좌석번호와 동일한 좌석정보를 찾아서
					ipAddr = seat[i][j].getPcIP();// ip를 저장하고
				}
			}
		}

		for (Iterator<PMClient> iterator = clientSocketList.iterator(); iterator.hasNext();) {// clientSocketList에서
			PMClient pmClient = (PMClient) iterator.next();
			if (pmClient.getClient().getInetAddress().toString().substring(1).equals(ipAddr)) {// 해당 ip를 찾아서
				pmClient.getDos().writeUTF("[logout]");// 로그아웃 명령을 보낸다.
				pmClient.getDos().flush();
			}
		}
	}

	private void updateOrder(String id) throws IOException {// 해당 pc에 종료명령을 내리는 메소드
		int seatNum = 0;

		PMSeatVO[][] seat = pmsc.getSeat();// 현재 좌석 중에서
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (id == seat[i][j].getUser()) {// 받은 좌석번호와 동일한 좌석정보를 찾아서
					seatNum = seat[i][j].getSeatNum();// ip를 저장하고
				}
			}
		}

		if (seatNum != 0) {
			for (Iterator<PMClient> iterator = clientSocketList.iterator(); iterator.hasNext();) {// clientSocketList에서
				PMClient pmClient = (PMClient) iterator.next();
				if (pmClient.getSeatNum() == seatNum) {// 해당 ip를 찾아서
					pmClient.getDos().writeUTF("[update time]");// 로그아웃 명령을 보낸다.
					pmClient.getDos().flush();
				} // end if
			} // end for
		} // end if
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
		if (ae.getSource() == mv.getJbtSendMsg() || ae.getSource() == mv.getJtfMsg()) {// 엔터키나 메세지 보내기 버튼이 눌려졌을 때
			try {
				sendMsg(mv.getJtfMsg().getText().trim());
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(mv, "서버가 종료되어 메세지를 전송할 수 없습니다");
				ie.printStackTrace();
			}
		}
		if (ae.getSource() == mv.getJbtSendClose()) {// 원격 종료 버튼이 눌려졌을 때
			if (JOptionPane.showConfirmDialog(mv, "해당 사용자를 정말 로그아웃 시키시겠습니까?", "경고",
					JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
				try {
					writeStream("[logout]");
				} catch (IOException ie) {
					JOptionPane.showMessageDialog(mv, "서버가 종료되어 메세지를 전송할 수 없습니다");
					ie.printStackTrace();
				}
			}
		}
		if (ae.getSource() == mv.getJbtOrderDone()) {// 주문 완료 버튼이 눌려졌을 때
			if (JOptionPane.showConfirmDialog(mv, "해당 사용자의 주문이 모두 완료되었습니까? 해당 사용자의 모든 주문이 완료처리됩니다!", "확인",
					JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
				try {
					orderDone(seatNum);// 좌석번호에 해당하는 모든 주문의 STATUS를 'Y'로 변경하고 좌석번호에 해당하는 PC_STATUS의 ORDER_STATUS를 'N'으로
										// 변경
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			pmsc.seatLoad();
			pmsc.setBtnSeat();
		}
	}

	private int orderDone(int seatNum) throws SQLException {
		int cnt = pmm_dao.orderDone(seatNum);// DB자료를 갱신하고
		pmsc.getPmsv().getPmov().getPmoc().setOrder();
		pmsc.getPmsv().getPmov().getPmoc().setOrderComplete();// 주문테이블을 갱신
		return cnt;
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
		mv.setVisible(false);// 종료되는 대신 visible을 false로 바꿈
	}

	@Override
	public String toString() {
		return "PMClient [mv=" + mv + ", client=" + client + ", dis=" + dis + ", dos=" + dos + ", clientSocketList="
				+ clientSocketList + ", thread=" + thread + "]";
	}

	public int getSeatNum() {
		return seatNum;
	}

	public PMMessageDAO getPmm_dao() {
		return pmm_dao;
	}

}
