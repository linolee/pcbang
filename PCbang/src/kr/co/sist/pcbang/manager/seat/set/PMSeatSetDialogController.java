package kr.co.sist.pcbang.manager.seat.set;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class PMSeatSetDialogController extends WindowAdapter implements ActionListener {

	private PMSeatSetDialogView pmssdv;

	public PMSeatSetDialogController(PMSeatSetDialogView pmssdv) {
		this.pmssdv = pmssdv;
		pmssdv.addWindowListener(this);
	}// constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pmssdv.getBtnReset()) {// 좌석초기화 버튼이 눌러질 때
			resetSeatInfo();
			pmssdv.getPmssc().seatSet();// SeatSet창의 좌석정보를 갱신하고
			pmssdv.dispose();// Dialog를 종료한다.
		}
		if (e.getSource() == pmssdv.getBtnUpdate()) {// 좌석변경 버튼이 눌러질 때
			if (checkSeatNum() && checkIPAddr()) {// 좌석번호 숫자 형식, 중복 체크//IP 형식, 중복 체크//둘 다 만족하면
				changeSeatInfo();// 해당 좌석의 좌석값을 변경하고
				pmssdv.getPmssc().seatSet();// SeatSet창의 좌석정보를 갱신하고
				pmssdv.dispose();// Dialog를 종료한다.
			}
		}
	}

	private boolean checkSeatNum() {
		boolean flag = true;
		String input = pmssdv.getJtfSeatNum().getText().trim();
		try {
			// 좌석번호가 100 이하의 정수인지 체크
			int seatNum = Integer.parseInt(input);
			if (seatNum > 100 && seatNum > 0) {
				JOptionPane.showMessageDialog(pmssdv, "100 이하의 정수를 입력해주세요.");
				flag = false;
				return flag;
			}
			// 좌석번호가 중복되는지 체크
			PMSeatSetVO[][] seat = pmssdv.getPmssc().getSeat();
			if (!seat[pmssdv.getX()][pmssdv.getY()].getSeatNum().equals(Integer.parseInt(input))) {// 처음 불러온 좌석번호와 입력한
																									// 좌석번호가 다를 때
				for (int i = 0; i < seat.length; i++) {
					for (int j = 0; j < seat[i].length; j++) {
						if (seat[i][j].getSeatNum() == seatNum) {
							flag = false;
							JOptionPane.showMessageDialog(pmssdv, "좌석 번호가 이미 할당되었습니다. 다른 번호를 할당해주세요.");
							return flag;
						}
					}
				}
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmssdv, "100 이하의 정수를 입력해주세요.");
			flag = false;
		}

		return flag;
	}

	private boolean checkIPAddr() {
		boolean flag = true;
		// IP주소가 형식에 맞는지 체크
		String input = pmssdv.getJtfIPAddr().getText().trim();
		String[] inputArr = input.split("\\.");
		try {
			Integer[] ipArr = new Integer[inputArr.length];
			for (int i = 0; i < inputArr.length; i++) {
				ipArr[i] = Integer.parseInt(inputArr[i]);
			}

			if (ipArr.length != 4) {
				JOptionPane.showMessageDialog(pmssdv, "올바른 IP를 입력해주세요.");
				flag = false;
				return flag;
			}
			for (int i = 0; i < ipArr.length; i++) {
				if (ipArr[i] < 0 || ipArr[i] > 255) {
					flag = false;
					return flag;
				}
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmssdv, "올바른 IP를 입력해주세요.");
			flag = false;
		}
		// 좌석번호가 중복되는지 체크
		PMSeatSetVO[][] seat = pmssdv.getPmssc().getSeat();
		if (!seat[pmssdv.getX()][pmssdv.getY()].getPcIP().equals(input)) {// 처음 불러온 IP와 입력한 IP가 다를 때
			for (int i = 0; i < seat.length; i++) {
				for (int j = 0; j < seat[i].length; j++) {
					if (seat[i][j].getPcIP().equals(input)) {
						flag = false;
						JOptionPane.showMessageDialog(pmssdv,
								"IP가 이미 " + seat[i][j].getSeatNum() + "번 좌석에 할당되었습니다. 다른 IP를 할당해주세요.");
						return flag;
					} // end if
				} // end inner for
			} // end outer for
		}
		// IP주소가 중복되는지 체크
		return flag;
	}

	private void changeSeatInfo() {// 좌석정보를 입력한 값으로 변경
		pmssdv.getPmssc().getSeat()[pmssdv.getX()][pmssdv.getY()] = new PMSeatSetVO(
				Integer.parseInt(pmssdv.getJtfSeatNum().getText().trim()), pmssdv.getJtfIPAddr().getText().trim());
	}

	private void resetSeatInfo() {// 좌석정보를 입력한 값으로 변경
		pmssdv.getPmssc().getSeat()[pmssdv.getX()][pmssdv.getY()] = new PMSeatSetVO(0, "");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		pmssdv.dispose();
	}
	
}
