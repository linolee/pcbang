package kr.co.sist.pcbang.manager.seat.set;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import kr.co.sist.pcbang.manager.seat.PMSeatController;

public class PMSeatSetView extends JFrame {
	private JButton[][] btnSeat;
	private JButton btnSeatSave, btnSeatLoad, btnSeatReset;
	private PMSeatController pmsc;

	public PMSeatSetView(PMSeatController pmsc) {
		this.pmsc = pmsc;

		btnSeatSave = new JButton("현재 좌석 저장");
		btnSeatLoad = new JButton("기존 좌석 불러오기");
		btnSeatReset = new JButton("좌석 비우기");

		JPanel pnlNorth = new JPanel();
		pnlNorth.setLayout(new GridLayout(1, 3));

		pnlNorth.add(btnSeatSave);
		pnlNorth.add(btnSeatLoad);
		pnlNorth.add(btnSeatReset);

		add("North", pnlNorth);

		btnSeat = new JButton[10][10];
		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[i].length; j++) {
				btnSeat[i][j] = new JButton();
			}
		}

		JPanel pnlMain = new JPanel(new GridLayout(btnSeat.length, btnSeat[0].length));
		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[i].length; j++) {
				pnlMain.add(btnSeat[i][j]);
			}
		}

		add(pnlMain);

		PMSeatSetController pmssc = new PMSeatSetController(this);
		btnSeatSave.addActionListener(pmssc);
		btnSeatLoad.addActionListener(pmssc);
		btnSeatReset.addActionListener(pmssc);
		addWindowListener(pmssc);

		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[i].length; j++) {
				btnSeat[i][j].addActionListener(pmssc);
			}
		}

		setBounds(100, 100, 700, 700);
		setVisible(true);

	}

	public JButton[][] getBtnSeat() {
		return btnSeat;
	}

	public JButton getBtnSeatSave() {
		return btnSeatSave;
	}

	public JButton getBtnSeatLoad() {
		return btnSeatLoad;
	}

	public JButton getBtnSeatReset() {
		return btnSeatReset;
	}

	public PMSeatController getPmsc() {
		return pmsc;
	}

}
