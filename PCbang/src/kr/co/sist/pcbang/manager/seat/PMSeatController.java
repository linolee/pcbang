package kr.co.sist.pcbang.manager.seat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import kr.co.sist.pcbang.manager.seat.detail.PMSeatDetailView;
import kr.co.sist.pcbang.manager.seat.message.PMClient;
import kr.co.sist.pcbang.manager.seat.set.PMSeatSetView;

public class PMSeatController extends WindowAdapter implements Runnable, ActionListener {
	private PMSeatView pmsv;
	private Socket serverSocket;
	private List<PMClient> clientSocket;
	private PMSeatDAO pms_dao;
	private PMSeatVO[][] seat;

	public PMSeatController(PMSeatView pmsv) {
		// DAO����
		this.pmsv = pmsv;
		pms_dao = PMSeatDAO.getInstance();
		setServer();
		seatLoad();
		setBtnSeat();
	}// constructor

	private void setServer() {

	}

	private void setBtnSeat() {
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				pmsv.getBtnSeat()[i][j].setText(seat[i][j].getSeatNum().toString());
				if (seat[i][j].getSeatNum() == 0) {// �¼� ��ȣ�� 0�̶�� ������
					pmsv.getBtnSeat()[i][j].setBackground(Color.BLACK);
				} else if (!seat[i][j].getUser().equals("")) {// ������ ��ǻ�Ϳ� �ִٸ� �ʷϻ�
					pmsv.getBtnSeat()[i][j].setBackground(Color.GREEN);
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
	}

	private void seatLoad() {
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
					if (seat[i][j].getSeatNum() != 0) {// �¼���ȣ�� 0�� �ƴϰ�
						if (seat[i][j].getUser().equals("")) {// ����ڰ� ���ٸ�
							openSeatDetail(i, j);// �¼� �������� ����
							System.out.println("�¼�������");
						} else {// ����ڰ� �ִٸ�
							visibleMsg();// �ش� ����ڿ��� ä��â�� ����.
							System.out.println(i + "," + j + "����ڿ��� �޼���â ���̱�");
						}
					}
				} // end if
			} // end inner for
		} // end outer for
	}

	private void openSeatSet() {
		new PMSeatSetView();
	}

	private void openPopBtn() {

	}

	private void openSeatDetail(int i, int j) {
		new PMSeatDetailView(this, i, j);
	}

	private void visibleMsg() {
	}

	public void run() {

	}

	private void recieveClient() {

	}

	private void dropClient() {

	}

	private void readSeatInfo() {

	}

	public PMSeatView getPmsv() {
		return pmsv;
	}

	public Socket getServerSocket() {
		return serverSocket;
	}

	public List<PMClient> getClientSocket() {
		return clientSocket;
	}

	public PMSeatDAO getPms_dao() {
		return pms_dao;
	}

	public PMSeatVO[][] getSeat() {
		return seat;
	}

}
