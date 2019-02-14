package kr.co.sist.pcbang.manager.seat.set;

import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PMSeatSetDialogView extends JFrame{
	private JTextField jtfSeatNum, jtfIDAddr;
	private JButton btnUpdate;
	private PMSeatSetController pmssc;
	private int x, y;
	
	public PMSeatSetDialogView(int x, int y, PMSeatSetController pmssc) {
		
		super("("+x+","+y+")");
		this.pmssc = pmssc;
		this.x = x;
		this.y = y;
		
		jtfSeatNum = new JTextField();
		jtfSeatNum.setBorder(new TitledBorder("�¼���ȣ"));
		
		jtfIDAddr = new JTextField();
		jtfIDAddr.setBorder(new TitledBorder("IP�ּ�"));
		
		btnUpdate = new JButton("�¼����� ����");
		
		add(jtfSeatNum);
		add(jtfIDAddr);
		add(btnUpdate);
		
		setLayout(null);
		jtfSeatNum.setBounds(50, 50, 200, 60);
		jtfIDAddr.setBounds(50, 150, 200, 60);
		btnUpdate.setBounds(75, 250, 150, 30);
		
		PMSeatSetDialogController pmssdc = new PMSeatSetDialogController(this);
		btnUpdate.addActionListener(pmssdc);
		
		setVisible(true);
		setBounds(100, 100, 300, 400);
		setResizable(false);
	
	}//constructor

	public JTextField getJtfSeatNum() {
		return jtfSeatNum;
	}

	public JTextField getJtfIDAddr() {
		return jtfIDAddr;
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
