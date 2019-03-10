package kr.co.sist.pcbang.manager.seat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.pcbang.manager.main.PMMainView;
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
	private PMMainView pmmv;

	public PMSeatController(PMSeatView pmsv, PMMainView pmmv) {
		// DAO연결
		this.pmsv = pmsv;
		this.pmmv = pmmv;
		pms_dao = PMSeatDAO.getInstance();
		clientSocketList = new ArrayList<PMClient>();
		try {
			serverSocket = new ServerSocket(55000);
		} catch (IOException e) {
			e.printStackTrace();
		}//서버 소켓을 열고
		setServer();
		seatLoad();
		setBtnSeat();
	}// constructor


	public void setBtnSeat() {
		
		int sumOrder = 0;
		int sumMsg = 0;
		int sumSeat = 0;
		
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				pmsv.getBtnSeat()[i][j].setText(seat[i][j].getSeatNum().toString());
				if (seat[i][j].getSeatNum() == 0) {// 좌석 번호가 0이라면 검은색
					pmsv.getBtnSeat()[i][j].setBackground(Color.BLACK);
				} else if (!seat[i][j].getUser().equals("")) {// 유저가 컴퓨터에 있다면 초록색
					pmsv.getBtnSeat()[i][j].setBackground(Color.GREEN);
					sumSeat++;
					
					if (seat[i][j].getMessageStatus().equals("Y") && seat[i][j].getOrderStatus().equals("Y")) {// 메세지와
																												// 주문이 둘
																												// 다 있다면
						pmsv.getBtnSeat()[i][j].setBackground(Color.ORANGE);
					} else if (seat[i][j].getOrderStatus().equals("Y")) {// 주문만 있다면
						pmsv.getBtnSeat()[i][j].setBackground(Color.YELLOW);
						sumOrder++;
					} else if (seat[i][j].getMessageStatus().equals("Y")) {// 메세지만 있다면
						pmsv.getBtnSeat()[i][j].setBackground(Color.RED);
						sumMsg++;
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
		
		// 김정운 추가
		setStatus(sumSeat, sumOrder, sumMsg);
		
	}
	
	/* PC현황, 주문 수, 메시지 수 - 김정운 추가  */
	public void setStatus(int seat, int order, int msg){
		//현재 값이 0이 아닐때 변경
		if (seat != 0) {
			pmmv.getJtaBoard().setText(String.valueOf("총 좌석 수 : 25"+"\n사용중 좌석 수 : "+ seat+"\n잔여 좌석 수 : "+(25-seat)));
		} else {
			pmmv.getJtaBoard().setText(String.valueOf("총 좌석 수 : 25"+"\n사용중 좌석 수 : "+seat));
		} // end else

		if (order != 0) {
			pmmv.getJlOrderNum().setText(String.valueOf(order));
		} else {
			pmmv.getJlOrderNum().setText(String.valueOf("0"));
		}

		if (msg != 0) {
			pmmv.getJlMsgNum().setText(String.valueOf(msg));
		} else {
			pmmv.getJlMsgNum().setText(String.valueOf("0"));
		}
		
	} // setStatus
	

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
					btnSeatClicked(i, j);
					seatLoad();
					setBtnSeat();
				}// end if
			} // end inner for
		} // end outer for
	}
	
	private void btnSeatClicked(int i, int j) {
		if (seat[i][j].getSeatNum() != 0) {// 좌석번호가 0이 아니고
			if (seat[i][j].getUser().equals("")) {// 사용자가 없다면
				openSeatDetail(i, j);// 좌석 상세정보를 열고
			} else {// 사용자가 있다면
				for (PMClient pmClient : clientSocketList) {
					if (pmClient.getClient().getInetAddress().toString().equals("/"+seat[i][j].getPcIP())) {//클라이언트 리스트에서 해당하는 좌석의 IP로 연결된 Client를 검색해서
						pmClient.getMv().setVisible(true);
						try {
							pms_dao.chgMsgStatusYtoN(seat[i][j].getSeatNum());//메세지를 확인하면 해당 좌석의 메세지 상태를 읽음으로 표시
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}//end if
				}//end for
			}//end if
		}//end if
	}
	
	private void openSeatSet() {
		new PMSeatSetView(this);
	}

	private void openSeatDetail(int i, int j) {
		new PMSeatDetailView(this, i, j);
	}

	private void setServer() {
		threadServer = new Thread(this);
		threadServer.start();// start() -> run()
	}
	
	public void run() {
		try {
			// 서버소켓을 열고 접속자 소켓을 받는다.
			while (true) {
				recieveClient();
			} // end while
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
