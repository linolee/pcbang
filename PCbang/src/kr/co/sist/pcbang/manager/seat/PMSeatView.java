package kr.co.sist.pcbang.manager.seat;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import kr.co.sist.pcbang.manager.order.PMOrderView;

@SuppressWarnings("serial")
public class PMSeatView extends JPanel {
	private JButton[][] btnSeat;
	private JButton btnSet;

	public PMSeatView(PMOrderView pmov) {
		btnSet = new JButton("ÁÂ¼®¼³Á¤");

		btnSeat = new JButton[10][10];
		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[i].length; j++) {
				btnSeat[i][j] = new JButton("<" + i + "," + j + ">");
			}
		}

		setLayout(null);

		add(btnSet);

		JPanel pnlMain = new JPanel(new GridLayout(btnSeat.length, btnSeat[0].length));
		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[i].length; j++) {
				pnlMain.add(btnSeat[i][j]);
			}
		}

		add(pnlMain);

		btnSet.setBounds(900, 0, 100, 20);
		pnlMain.setBounds(0, 20, 1000, 550);

		PMSeatController pmsc = new PMSeatController(this);
		btnSet.addActionListener(pmsc);
		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[i].length; j++) {
				btnSeat[i][j].addActionListener(pmsc);
			}
		}

	}

	public JButton[][] getBtnSeat() {
		return btnSeat;
	}

	public JButton getBtnSet() {
		return btnSet;
	}

}
