package kr.co.sist.pcbang.manager.seat;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PMSeatView extends JPanel{
	private JButton[][] btnSeat;
	private JButton btnMsg, btnDetail, btnSet;
		
	public PMSeatView() {
		btnMsg = new JButton("메세지 보내기");
		btnDetail = new JButton("좌석설정");
		btnSet = new JButton("좌석설정");
		
		btnSeat = new JButton[10][10];
		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[i].length; j++) {
				btnSeat[i][j] = new JButton("<"+i+","+j+">");
			}
		}
		
		setLayout(null);
		
		btnMsg.setBounds(0, 0, 150, 30);
		btnDetail.setBounds(0, 30, 150, 30);
		
		add(btnMsg);
		add(btnDetail);
		
		btnMsg.setVisible(false);
		btnDetail.setVisible(false);
		
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

	public JButton getBtnMsg() {
		return btnMsg;
	}

	public JButton getBtnDetail() {
		return btnDetail;
	}

	public JButton getBtnSet() {
		return btnSet;
	}
	
}
