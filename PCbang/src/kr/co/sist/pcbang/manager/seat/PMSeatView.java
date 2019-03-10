package kr.co.sist.pcbang.manager.seat;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.co.sist.pcbang.manager.main.PMMainView;
import kr.co.sist.pcbang.manager.order.PMOrderView;

@SuppressWarnings("serial")
public class PMSeatView extends JPanel {
	private JButton[][] btnSeat;
	private JButton btnSet;
	private PMOrderView pmov;
	private PMMainView pmmv;

	public PMSeatView(PMOrderView pmov, PMMainView pmmv) {
		this.pmov = pmov;
		this.pmmv = pmmv;
		btnSet = new JButton("좌석설정");

		btnSeat = new JButton[10][10];
		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[i].length; j++) {
				btnSeat[i][j] = new JButton("<" + i + "," + j + ">");
			}
		}

		// 0306 좌석 색상 정보 표시 추가
		JLabel colorLabel1, colorLabel2, colorLabel3, colorLabel4, colorInfoLabel1, colorInfoLabel2, colorInfoLabel3,
				colorInfoLabel4;
		colorInfoLabel1 = new JLabel("사용중");
		colorInfoLabel2 = new JLabel("주문");
		colorInfoLabel3 = new JLabel("메세지");
		colorInfoLabel4 = new JLabel("주문&메세지");
		colorLabel1 = new JLabel();
		colorLabel1.setBackground(Color.GREEN);
		colorLabel1.setOpaque(true);
		colorLabel2 = new JLabel();
		colorLabel2.setBackground(Color.YELLOW);
		colorLabel2.setOpaque(true);
		colorLabel3 = new JLabel();
		colorLabel3.setBackground(Color.RED);
		colorLabel3.setOpaque(true);
		colorLabel4 = new JLabel();
		colorLabel4.setBackground(Color.ORANGE);
		colorLabel4.setOpaque(true);

		setLayout(null);

		add(btnSet);

		JPanel pnlMain = new JPanel(new GridLayout(btnSeat.length, btnSeat[0].length));
		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[i].length; j++) {
				pnlMain.add(btnSeat[i][j]);
			}
		}

		add(pnlMain);
		add(colorLabel1);
		add(colorInfoLabel1);
		add(colorLabel2);
		add(colorInfoLabel2);
		add(colorLabel3);
		add(colorInfoLabel3);
		add(colorLabel4);
		add(colorInfoLabel4);

		colorLabel1.setBounds(0, 0, 20, 20);
		colorInfoLabel1.setBounds(25, 0, 70, 20);
		colorLabel2.setBounds(100, 0, 20, 20);
		colorInfoLabel2.setBounds(125, 0, 70, 20);
		colorLabel3.setBounds(200, 0, 20, 20);
		colorInfoLabel3.setBounds(225, 0, 70, 20);
		colorLabel4.setBounds(300, 0, 20, 20);
		colorInfoLabel4.setBounds(325, 0, 120, 20);
		btnSet.setBounds(900, 0, 100, 20);
		pnlMain.setBounds(0, 20, 1000, 550);

		PMSeatController pmsc = new PMSeatController(this, pmmv);
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

	public PMOrderView getPmov() {
		return pmov;
	}
	
	public PMMainView getMmov() {
		return pmmv;
	}

}
