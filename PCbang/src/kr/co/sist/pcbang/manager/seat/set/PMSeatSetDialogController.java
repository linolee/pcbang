package kr.co.sist.pcbang.manager.seat.set;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PMSeatSetDialogController implements ActionListener {

	PMSeatSetDialogView pmssdv;

	public PMSeatSetDialogController(PMSeatSetDialogView pmssdv) {
		this.pmssdv = pmssdv;
	}// constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pmssdv.getBtnUpdate()) {// 좌석변경 버튼이 눌러질 때
			if (checkSeatNum() && checkIPAddr()) {// 좌석번호 숫자 형식, 중복 체크//IP 형식, 중복 체크//둘 다 만족하면
				changeSeatInfo();//해당 좌석의 좌석값을 변경하고
				pmssdv.getPmssc().seatSet();//SeatSet창의 좌석정보를 갱신하고
				pmssdv.dispose();//Dialog를 종료한다.
			}
		}
	}

	private boolean checkSeatNum() {
		boolean flag = true;
		String input = pmssdv.getJtfSeatNum().getText().trim();
		try {
			// 좌석번호가 100 이하의 정수인지 체크
			int seatNum = Integer.parseInt(input);
			if (seatNum > 100) {
				System.out.println("100 이하의 정수 입력");/////////////////// JoptionPane으로 변경해야함
				flag = false;
			}
			// 좌석번호가 중복되는지 체크
			PMSeatSetVO[][] seat = pmssdv.getPmssc().getSeat();
			for (int i = 0; i < seat.length; i++) {
				for (int j = 0; j < seat[i].length; j++) {
					if (seat[i][j].getSeatNum() == seatNum) {
						flag = false;
						System.out.println("좌석 번호가 이미 할당되었습니다. 다른 번호를 할당해주세요.");/////////////////// JoptionPane으로 변경해야함
					}
				}
			}
		} catch (NumberFormatException nfe) {
			System.out.println("100 이하의 정수 입력");/////////////////// JoptionPane으로 변경해야함
			flag = false;
		}

		return flag;
	}

	private boolean checkIPAddr() {
		boolean flag = true;
		// IP주소가 형식에 맞는지 체크
		String input = pmssdv.getJtfIDAddr().getText().trim();
		String[] inputArr = input.split("\\.");
		try {
			Integer[] ipArr = new Integer[inputArr.length];
			for (int i = 0; i < inputArr.length; i++) {
				ipArr[i] = Integer.parseInt(inputArr[i]);
			}

			if (ipArr.length != 4) {
				System.out.println("올바른 IP 입력 - 길이");//////////// JoptionPane
				flag = false;
			}
			for (int i = 0; i < ipArr.length; i++) {
				if (ipArr[i] < 0 || ipArr[i] > 255) {
					flag = false;
				}
			}
		} catch (NumberFormatException nfe) {
			System.out.println("올바른 IP 입력- 숫자");/////////////////// JoptionPane으로 변경해야함
		}

		// 좌석번호가 중복되는지 체크
		PMSeatSetVO[][] seat = pmssdv.getPmssc().getSeat();
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (seat[i][j].getPcIP().equals(input)) {
					flag = false;
					
					System.out.println("IP가 이미 "+seat[i][j].getSeatNum()+"번 좌석에 할당되었습니다. 다른 IP를 할당해주세요.");/////////////////// JoptionPane으로 변경해야함
				} // end if
			} // end inner for
		} // end outer for

		// IP주소가 중복되는지 체크
		return flag;
	}

	private void changeSeatInfo() {// 좌석정보를 입력한 값으로 변경
		pmssdv.getPmssc().getSeat()[pmssdv.getX()][pmssdv.getY()] = new PMSeatSetVO(
				Integer.parseInt(pmssdv.getJtfSeatNum().getText().trim()),
				pmssdv.getJtfIDAddr().getText().trim(), "Admin");
	}

}
