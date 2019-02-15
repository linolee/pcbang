package kr.co.sist.pcbang.manager.seat.set;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import kr.co.sist.pcbang.manager.seat.PMSeatDAO;

public class PMSeatSetController implements ActionListener{
	private PMSeatSetView pmssv;
	private PMSeatDAO pms_dao;
	private PMSeatSetVO[][] seat;
	
	public PMSeatSetController(PMSeatSetView pmssv) {
		this.pmssv = pmssv;
		pms_dao = PMSeatDAO.getInstance();
		seatLoad();
		seatSet();
		
		
		
	}//constructor
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pmssv.getBtnSeatSave()) {//���� �¼� ���� ��ư
			seatSave(seat);
			seatSet();
		}
		if (e.getSource() == pmssv.getBtnSeatLoad()) {//���� �¼� �ҷ����� ��ư
			seatLoad();
			seatSet();
		}
		if (e.getSource() == pmssv.getBtnSeatReset()) {//�¼� ���� ��ư
			seatReset();
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (e.getSource() == pmssv.getBtnSeat()[i][j]) {//�� �¼� ��ư
					new PMSeatSetDialogView(i, j, this);//�¼����� ���̾�α� �ҷ�����
				}
			}//inner for
		}//outer for
		
	}
	
	private void seatSave(PMSeatSetVO[][] seat) {
		try {
			Integer totalDelete = pms_dao.deleteSeatSetInfo();
			Integer totalInsert = pms_dao.insertSeatSetInfo(seat);
			System.out.println(totalDelete+"�� �¼����� DB���� ���� �Ϸ�");/////////////////////////JOptionPaneó���Ұ�
			System.out.println(totalInsert+"�� �¼����� DB�� �Է¿Ϸ�");/////////////////////////JOptionPaneó���Ұ�
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private void seatLoad() {
		try {
			seat = pms_dao.selectSeatSetInfo();
		} catch (SQLException e) {
			e.printStackTrace();
		}//DB���� �¼������� �޾ƿͼ� ������ ����.
	}
	private void seatReset() {
		
	}
	public void seatSet() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				pmssv.getBtnSeat()[i][j].setText(seat[i][j].getSeatNum().toString());//seat�� btnSeat�� ������ ���߱�
				if (seat[i][j].getSeatNum() == 0) {
					pmssv.getBtnSeat()[i][j].setBackground(Color.GRAY);//�¼���ȣ�� 0�̶�� ������ ȸ������ �ٲٱ�
				}else {
					pmssv.getBtnSeat()[i][j].setBackground(Color.WHITE);
				}
			}//end inner for
		}//end outer for
	}

	public PMSeatSetVO[][] getSeat() {
		return seat;
	}
	
}
