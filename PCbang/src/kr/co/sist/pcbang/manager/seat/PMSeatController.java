package kr.co.sist.pcbang.manager.seat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import kr.co.sist.pcbang.manager.seat.detail.PMSeatDetailView;
import kr.co.sist.pcbang.manager.seat.message.PMClient;
import kr.co.sist.pcbang.manager.seat.set.PMSeatSetView;

public class PMSeatController implements Runnable, ActionListener {
	private PMSeatView pmsv;
	private ServerSocket serverSocket;
	private List<PMClient> clientSocketList;
	private PMSeatDAO pms_dao;
	private PMSeatVO[][] seat;
	private Thread threadServer; // 접속자에 대한 처리를 하기 위한 thread

	public PMSeatController(PMSeatView pmsv) {
		// DAO연결
		this.pmsv = pmsv;
		pms_dao = PMSeatDAO.getInstance();
		clientSocketList = new ArrayList<PMClient>();
		try {
			serverSocket = new ServerSocket(55000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//서버 소켓을 열고
		setServer();
		seatLoad();
		setBtnSeat();
	}// constructor


	public void setBtnSeat() {
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				pmsv.getBtnSeat()[i][j].setText(seat[i][j].getSeatNum().toString());
				if (seat[i][j].getSeatNum() == 0) {// 좌석 번호가 0이라면 검은색
					pmsv.getBtnSeat()[i][j].setBackground(Color.BLACK);
				} else if (!seat[i][j].getUser().equals("")) {// 유저가 컴퓨터에 있다면 초록색
					pmsv.getBtnSeat()[i][j].setBackground(Color.GREEN);
					if (seat[i][j].getMessageStatus().equals("Y") && seat[i][j].getOrderStatus().equals("Y")) {// 메세지와
																												// 주문이 둘
																												// 다 있다면
						pmsv.getBtnSeat()[i][j].setBackground(Color.ORANGE);
					} else if (seat[i][j].getOrderStatus().equals("Y")) {// 주문만 있다면
						pmsv.getBtnSeat()[i][j].setBackground(Color.YELLOW);
					} else if (seat[i][j].getMessageStatus().equals("Y")) {// 메세지만 있다면
						pmsv.getBtnSeat()[i][j].setBackground(Color.RED);
					}

				} else {// 없다면 회색
					pmsv.getBtnSeat()[i][j].setBackground(Color.GRAY);
				}

//				if (seat[i][j].getPcStatus().equals("")) {
//					
//				}
				///////////////////////////// 상태에 따라 색
				///////////////////////////// 추가//////////////////////////////////////////
			} // end inner for
		} // end outer for
	}

	public void seatLoad() {
		try {
			seat = pms_dao.selectSeatInfo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pmsv.getBtnSet()) {// 좌석 수정버튼이 눌렸을 때
			openSeatSet();
		}

		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (e.getSource() == pmsv.getBtnSeat()[i][j]) {// 각 좌석 버튼이 클릭 됐을 때
					if (seat[i][j].getSeatNum() != 0) {// 좌석번호가 0이 아니고
						if (seat[i][j].getUser().equals("")) {// 사용자가 없다면
							openSeatDetail(i, j);// 좌석 상세정보를 열고
							System.out.println("좌석상세정보");
						} else {// 사용자가 있다면
							visibleMsg();// 해당 사용자와의 채팅창을 연다.
							System.out.println(i + "," + j + "사용자와의 메세지창 보이기");
						}
					}
				} // end if
			} // end inner for
		} // end outer for
	}

	private void openSeatSet() {
		new PMSeatSetView(this);
	}

	private void openSeatDetail(int i, int j) {
		new PMSeatDetailView(this, i, j);
	}

	private void visibleMsg() {
	}

	private void setServer() {
		threadServer = new Thread(this);
		threadServer.start();// start() -> run()
	}
	
	public void run() {
		try {
			// 서버소켓을 열고 접속자 소켓을 받는다.
			recieveClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 접속자 소켓을 List에 등록한다.

	}

	private void recieveClient() throws IOException {
		
		Socket clientSocket = serverSocket.accept();//클라이언트가 접속할 경우 소켓을 받아서
		PMClient client = new PMClient(clientSocket, this);//클라이언트 객체를 만들고
		clientSocketList.add(client);//리스트에 넣는다.
//		client.run();//클라이언트의 Thread를 실행한다.
		System.out.println(clientSocket.getInetAddress());
	}

	private void readSeatInfo() {

	}

	public PMSeatView getPmsv() {
		return pmsv;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public List<PMClient> getClientSocket() {
		return clientSocketList;
	}

	public PMSeatDAO getPms_dao() {
		return pms_dao;
	}

	public PMSeatVO[][] getSeat() {
		return seat;
	}

}
