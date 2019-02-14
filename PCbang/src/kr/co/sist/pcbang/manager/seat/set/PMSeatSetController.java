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
		if (e.getSource() == pmssv.getBtnSeatSave()) {//현재 좌석 저장 버튼
			seatSave(seat);
			seatSet();
		}
		if (e.getSource() == pmssv.getBtnSeatLoad()) {//기존 좌석 불러오기 버튼
			seatLoad();
			seatSet();
		}
		if (e.getSource() == pmssv.getBtnSeatReset()) {//좌석 비우기 버튼
			seatReset();
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (e.getSource() == pmssv.getBtnSeat()[i][j]) {//각 좌석 버튼
					new PMSeatSetDialogView(i, j, this);//좌석설정 다이얼로그 불러오기
				}
			}//inner for
		}//outer for
		
	}
	
	private void seatSave(PMSeatSetVO[][] seat) {
		
	}
	private void seatLoad() {
		try {
			seat = pms_dao.selectSeatSetInfo();
		} catch (SQLException e) {
			e.printStackTrace();
		}//DB에서 좌석정보를 받아와서 변수에 저장.
	}
	private void seatReset() {
		
	}
	private void seatSet() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				pmssv.getBtnSeat()[i][j].setText(seat[i][j].getSeatNum().toString());//seat와 btnSeat의 정보를 맞추기
				if (seat[i][j].getSeatNum() == 0) {
					pmssv.getBtnSeat()[i][j].setBackground(Color.GRAY);//좌석번호가 0이라면 배경색을 회색으로 바꾸기
				}//end if
			}//end inner for
		}//end outer for
	}

	public PMSeatSetVO[][] getSeat() {
		return seat;
	}
	
}
