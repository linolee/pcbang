package kr.co.sist.pcbang.manager.seat.detail;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import kr.co.sist.pcbang.manager.seat.PMSeatController;
import kr.co.sist.pcbang.manager.seat.PMSeatVO;

@SuppressWarnings("serial")
public class PMSeatDetailView extends JFrame {

	public PMSeatDetailView(PMSeatController pmsc, int i, int j) {
		PMSeatVO seat = pmsc.getSeat()[i][j];

		JLabel lblSeatNum = new JLabel(seat.getSeatNum().toString());
		JLabel lblIpAddr = new JLabel(seat.getPcIP());
		lblSeatNum.setBorder(new TitledBorder("좌석번호"));
		lblIpAddr.setBorder(new TitledBorder("IP주소"));

		setLayout(null);
		
		lblSeatNum.setBounds(10, 20, 260, 50);
		lblIpAddr.setBounds(10, 90, 260, 50);

		add(lblSeatNum);
		add(lblIpAddr);

		setVisible(true);
		setBounds(500, 300, 300, 200);
		
		addWindowListener(new PMSeatDetailController(this));

	}// constructor

}
