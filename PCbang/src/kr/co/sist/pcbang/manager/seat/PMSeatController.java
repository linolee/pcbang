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
	private Thread threadServer; // �����ڿ� ���� ó���� �ϱ� ���� thread
	private PMMainView pmmv;

	public PMSeatController(PMSeatView pmsv, PMMainView pmmv) {
		// DAO����
		this.pmsv = pmsv;
		this.pmmv = pmmv;
		pms_dao = PMSeatDAO.getInstance();
		clientSocketList = new ArrayList<PMClient>();
		try {
			serverSocket = new ServerSocket(55000);
		} catch (IOException e) {
			e.printStackTrace();
		}//���� ������ ����
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
				if (seat[i][j].getSeatNum() == 0) {// �¼� ��ȣ�� 0�̶�� ������
					pmsv.getBtnSeat()[i][j].setBackground(Color.BLACK);
				} else if (!seat[i][j].getUser().equals("")) {// ������ ��ǻ�Ϳ� �ִٸ� �ʷϻ�
					pmsv.getBtnSeat()[i][j].setBackground(Color.GREEN);
					sumSeat++;
					
					if (seat[i][j].getMessageStatus().equals("Y") && seat[i][j].getOrderStatus().equals("Y")) {// �޼�����
																												// �ֹ��� ��
																												// �� �ִٸ�
						pmsv.getBtnSeat()[i][j].setBackground(Color.ORANGE);
					} else if (seat[i][j].getOrderStatus().equals("Y")) {// �ֹ��� �ִٸ�
						pmsv.getBtnSeat()[i][j].setBackground(Color.YELLOW);
						sumOrder++;
					} else if (seat[i][j].getMessageStatus().equals("Y")) {// �޼����� �ִٸ�
						pmsv.getBtnSeat()[i][j].setBackground(Color.RED);
						sumMsg++;
					}

				} else {// ���ٸ� ȸ��
					pmsv.getBtnSeat()[i][j].setBackground(Color.GRAY);
				}
//				if (seat[i][j].getPcStatus().equals("")) {
//					
//				}
				///////////////////////////// ���¿� ���� ��
				///////////////////////////// �߰�//////////////////////////////////////////
			} // end inner for
		} // end outer for
		
		// ������ �߰�
		setStatus(sumSeat, sumOrder, sumMsg);
		
	}
	
	/* PC��Ȳ, �ֹ� ��, �޽��� �� - ������ �߰�  */
	public void setStatus(int seat, int order, int msg){
		//���� ���� 0�� �ƴҶ� ����
		if (seat != 0) {
			pmmv.getJtaBoard().setText(String.valueOf("�� �¼� �� : 25"+"\n����� �¼� �� : "+ seat+"\n�ܿ� �¼� �� : "+(25-seat)));
		} else {
			pmmv.getJtaBoard().setText(String.valueOf("�� �¼� �� : 25"+"\n����� �¼� �� : "+seat));
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
		if (e.getSource() == pmsv.getBtnSet()) {// �¼� ������ư�� ������ ��
			openSeatSet();
		}
		
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (e.getSource() == pmsv.getBtnSeat()[i][j]) {// �� �¼� ��ư�� Ŭ�� ���� ��
					btnSeatClicked(i, j);
					seatLoad();
					setBtnSeat();
				}// end if
			} // end inner for
		} // end outer for
	}
	
	private void btnSeatClicked(int i, int j) {
		if (seat[i][j].getSeatNum() != 0) {// �¼���ȣ�� 0�� �ƴϰ�
			if (seat[i][j].getUser().equals("")) {// ����ڰ� ���ٸ�
				openSeatDetail(i, j);// �¼� �������� ����
			} else {// ����ڰ� �ִٸ�
				for (PMClient pmClient : clientSocketList) {
					if (pmClient.getClient().getInetAddress().toString().equals("/"+seat[i][j].getPcIP())) {//Ŭ���̾�Ʈ ����Ʈ���� �ش��ϴ� �¼��� IP�� ����� Client�� �˻��ؼ�
						pmClient.getMv().setVisible(true);
						try {
							pms_dao.chgMsgStatusYtoN(seat[i][j].getSeatNum());//�޼����� Ȯ���ϸ� �ش� �¼��� �޼��� ���¸� �������� ǥ��
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
			// ���������� ���� ������ ������ �޴´�.
			while (true) {
				recieveClient();
			} // end while
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ������ ������ List�� ����Ѵ�.

	}

	private void recieveClient() throws IOException {
		
		Socket clientSocket = serverSocket.accept();//Ŭ���̾�Ʈ�� ������ ��� ������ �޾Ƽ�
		PMClient client = new PMClient(clientSocket, this);//Ŭ���̾�Ʈ ��ü�� �����
		clientSocketList.add(client);//����Ʈ�� �ִ´�.
//		client.run();//Ŭ���̾�Ʈ�� Thread�� �����Ѵ�.
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
