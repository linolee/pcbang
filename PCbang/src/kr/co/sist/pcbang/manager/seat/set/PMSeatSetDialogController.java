package kr.co.sist.pcbang.manager.seat.set;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class PMSeatSetDialogController implements ActionListener {

	PMSeatSetDialogView pmssdv;

	public PMSeatSetDialogController(PMSeatSetDialogView pmssdv) {
		this.pmssdv = pmssdv;
	}// constructor

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pmssdv.getBtnUpdate()) {// �¼����� ��ư�� ������ ��
			if (checkSeatNum() && checkIPAddr()) {// �¼���ȣ ���� ����, �ߺ� üũ//IP ����, �ߺ� üũ//�� �� �����ϸ�
				changeSeatInfo();//�ش� �¼��� �¼����� �����ϰ�
				pmssdv.getPmssc().seatSet();//SeatSetâ�� �¼������� �����ϰ�
				pmssdv.dispose();//Dialog�� �����Ѵ�.
			}
		}
	}

	private boolean checkSeatNum() {
		boolean flag = true;
		String input = pmssdv.getJtfSeatNum().getText().trim();
		try {
			// �¼���ȣ�� 100 ������ �������� üũ
			int seatNum = Integer.parseInt(input);
			if (seatNum > 100) {
				JOptionPane.showMessageDialog(pmssdv, "100 ������ ������ �Է����ּ���.");
				flag = false;
			}
			// �¼���ȣ�� �ߺ��Ǵ��� üũ
			PMSeatSetVO[][] seat = pmssdv.getPmssc().getSeat();
			for (int i = 0; i < seat.length; i++) {
				for (int j = 0; j < seat[i].length; j++) {
					if (seat[i][j].getSeatNum() == seatNum) {
						flag = false;
						JOptionPane.showMessageDialog(pmssdv, "�¼� ��ȣ�� �̹� �Ҵ�Ǿ����ϴ�. �ٸ� ��ȣ�� �Ҵ����ּ���.");
					}
				}
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmssdv, "100 ������ ������ �Է����ּ���.");
			flag = false;
		}

		return flag;
	}

	private boolean checkIPAddr() {
		boolean flag = true;
		// IP�ּҰ� ���Ŀ� �´��� üũ
		String input = pmssdv.getJtfIDAddr().getText().trim();
		String[] inputArr = input.split("\\.");
		try {
			Integer[] ipArr = new Integer[inputArr.length];
			for (int i = 0; i < inputArr.length; i++) {
				ipArr[i] = Integer.parseInt(inputArr[i]);
			}

			if (ipArr.length != 4) {
				JOptionPane.showMessageDialog(pmssdv, "�ùٸ� IP�� �Է����ּ���.");
				flag = false;
			}
			for (int i = 0; i < ipArr.length; i++) {
				if (ipArr[i] < 0 || ipArr[i] > 255) {
					flag = false;
				}
			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmssdv, "�ùٸ� IP�� �Է����ּ���.");
		}

		// �¼���ȣ�� �ߺ��Ǵ��� üũ
		PMSeatSetVO[][] seat = pmssdv.getPmssc().getSeat();
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (seat[i][j].getPcIP().equals(input)) {
					flag = false;
					JOptionPane.showMessageDialog(pmssdv, "IP�� �̹� "+seat[i][j].getSeatNum()+"�� �¼��� �Ҵ�Ǿ����ϴ�. �ٸ� IP�� �Ҵ����ּ���.");
				} // end if
			} // end inner for
		} // end outer for

		// IP�ּҰ� �ߺ��Ǵ��� üũ
		return flag;
	}

	private void changeSeatInfo() {// �¼������� �Է��� ������ ����
		pmssdv.getPmssc().getSeat()[pmssdv.getX()][pmssdv.getY()] = new PMSeatSetVO(
				Integer.parseInt(pmssdv.getJtfSeatNum().getText().trim()),
				pmssdv.getJtfIDAddr().getText().trim(), "Admin");
	}

}
