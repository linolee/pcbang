package kr.co.sist.pcbang.manager.seat.set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PMSeatSetDialogView extends JFrame {
	private JTextField jtfSeatNum, jtfIDAddr;
	private JButton btnUpdate, btnReset;
	private PMSeatSetController pmssc;
	private int x, y;

	public PMSeatSetDialogView(int x, int y, PMSeatSetController pmssc) {

		super("(" + x + "," + y + ")");
		this.pmssc = pmssc;
		this.x = x;
		this.y = y;

		jtfSeatNum = new JTextField();
		jtfSeatNum.setBorder(new TitledBorder("좌석번호"));

		jtfIDAddr = new JTextField();
		jtfIDAddr.setBorder(new TitledBorder("IP주소"));

		btnReset = new JButton("좌석정보 초기화");
		btnUpdate = new JButton("좌석정보 수정");

		add(jtfSeatNum);
		add(jtfIDAddr);
		add(btnReset);
		add(btnUpdate);

		setLayout(null);
		jtfSeatNum.setBounds(50, 50, 200, 60);
		jtfIDAddr.setBounds(50, 150, 200, 60);
		btnReset.setBounds(75, 250, 150, 30);
		btnUpdate.setBounds(75, 290, 150, 30);

		PMSeatSetDialogController pmssdc = new PMSeatSetDialogController(this);
		btnReset.addActionListener(pmssdc);
		btnUpdate.addActionListener(pmssdc);

		setVisible(true);
		setBounds(100, 100, 300, 400);
		setResizable(false);

	}// constructor

	public JTextField getJtfSeatNum() {
		return jtfSeatNum;
	}

	public JTextField getJtfIDAddr() {
		return jtfIDAddr;
	}

	public JButton getBtnReset() {
		return btnReset;
	}

	public JButton getBtnUpdate() {
		return btnUpdate;
	}

	public PMSeatSetController getPmssc() {
		return pmssc;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
