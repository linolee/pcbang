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
		if (e.getSource() == pmssdv.getBtnUpdate()) {//�¼����� ��ư�� ������ ��
			
			
			
			
		}
	}
	
	private boolean checkSeatNum() {
		boolean flag = true;
		//�¼���ȣ�� 100 ������ �������� üũ
		//�¼���ȣ�� �ߺ��Ǵ��� üũ
		
		return flag;
	}

	private boolean checkIPAddr() {
		boolean flag = true;
		//IP�ּҰ� ���Ŀ� �´��� üũ
		//IP�ּҰ� �ߺ��Ǵ��� üũ
		return flag;
	}
	
	private void changeSeatInfo() {//�¼������� �Է��� ������ ����
		
	}
	
	
}
