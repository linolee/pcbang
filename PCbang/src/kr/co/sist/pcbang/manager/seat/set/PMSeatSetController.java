package kr.co.sist.pcbang.manager.seat.set;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.manager.seat.PMSeatDAO;

public class PMSeatSetController extends WindowAdapter implements ActionListener{
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
			if (JOptionPane.showConfirmDialog(pmssv, "DB 좌석 정보에 현재 설정한 좌석 정보를 덮어씁니다. 이전 DB 좌석 정보는 저장되지 않습니다.") == JOptionPane.OK_OPTION) {
				seatSave(seat);
				seatSet();
			}
		}
		if (e.getSource() == pmssv.getBtnSeatLoad()) {//기존 좌석 불러오기 버튼
			if (JOptionPane.showConfirmDialog(pmssv, "현재 설정한 좌석 정보에 DB 좌석 정보를 덮어씁니다. 현재 좌석 정보는 저장되지 않습니다.") == JOptionPane.OK_OPTION) {
				seatLoad();
				seatSet();
			}
		}
		if (e.getSource() == pmssv.getBtnSeatReset()) {//좌석 비우기 버튼
			if (JOptionPane.showConfirmDialog(pmssv, "현재 설정한 좌석 정보를 삭제합니다. 현재 좌석 정보는 저장되지 않습니다.") == JOptionPane.OK_OPTION) {
				seatReset();
				seatSet();
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (e.getSource() == pmssv.getBtnSeat()[i][j]) {//각 좌석 버튼
					PMSeatSetDialogView pmssdv = new PMSeatSetDialogView(i, j, this);//좌석설정 다이얼로그 불러오기
					if (seat[i][j].getSeatNum() != 0) {//좌석번호가 0이 아니라면 좌석의 정보를 입력창에 출력해준다.
						pmssdv.getJtfSeatNum().setText(seat[i][j].getSeatNum().toString());
						pmssdv.getJtfIPAddr().setText(seat[i][j].getPcIP());
					}
				}
			}//inner for
		}//outer for
		
	}
	
	private void seatSave(PMSeatSetVO[][] seat) {
		try {
			Integer totalDelete = pms_dao.deleteSeatSetInfo();
			Integer totalInsert = pms_dao.insertSeatSetInfo(seat);
			System.out.println(totalDelete+"개 좌석정보 DB에서 삭제 완료");/////////////////////////JOptionPane처리할것
			System.out.println(totalInsert+"개 좌석정보 DB에 입력완료");/////////////////////////JOptionPane처리할것
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private void seatLoad() {
		try {
			seat = pms_dao.selectSeatSetInfo();
		} catch (SQLException e) {
			e.printStackTrace();
		}//DB에서 좌석정보를 받아와서 변수에 저장.
	}
	private void seatReset() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				seat[i][j] = new PMSeatSetVO(0, "");
			}//inner for
		}//outer 
	}
	public void seatSet() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				pmssv.getBtnSeat()[i][j].setText(seat[i][j].getSeatNum().toString());//seat와 btnSeat의 정보를 맞추기
				if (seat[i][j].getSeatNum() == 0) {
					pmssv.getBtnSeat()[i][j].setBackground(Color.GRAY);//좌석번호가 0이라면 배경색을 회색으로 바꾸기
				}else {
					pmssv.getBtnSeat()[i][j].setBackground(Color.WHITE);
				}
			}//end inner for
		}//end outer for
	}

	public PMSeatSetVO[][] getSeat() {
		return seat;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		pmssv.getPmsc().seatLoad();
		pmssv.getPmsc().setBtnSeat();
		super.windowClosing(e);
	}
}
