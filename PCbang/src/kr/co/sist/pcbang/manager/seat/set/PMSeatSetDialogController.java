package kr.co.sist.pcbang.manager.seat.set;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PMSeatSetDialogController implements ActionListener{

	PMSeatSetDialogView pmssdv;
	
	public PMSeatSetDialogController(PMSeatSetDialogView pmssdv) {
		this.pmssdv = pmssdv;
		
		pmssdv.getPmssc().getSeat();
		pmssdv.getX();
		pmssdv.getY();
	}//constructor
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pmssdv.getBtnUpdate()) {//좌석변경 버튼이 눌러질 때
			
			
			
			
		}
	}
	
	private boolean checkSeatNum() {
		boolean flag = true;
		//좌석번호가 100 이하의 정수인지 체크
		//좌석번호가 중복되는지 체크
		
		return flag;
	}

	private boolean checkIPAddr() {
		boolean flag = true;
		//IP주소가 형식에 맞는지 체크
		//IP주소가 중복되는지 체크
		return flag;
	}
	
	private void changeSeatInfo() {//좌석정보를 입력한 값으로 변경
		
	}
	
	
}
