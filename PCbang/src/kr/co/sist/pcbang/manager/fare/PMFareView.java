package kr.co.sist.pcbang.manager.fare;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PMFareView extends JPanel {

	 private JTextField[] mjtfs, gjtfs;
	 private JButton jbtnUpdate;
	 private PMFareDAO f_dao;
	
	public PMFareView() {
		f_dao = PMFareDAO.getInstance();
	
		JPanel mainPanel = new JPanel();
		
		JPanel jpFareMember = new JPanel();
		JPanel jpFareGuest = new JPanel();
		
		setLayout(null);
		
		jpFareMember.setBorder(new TitledBorder("ȸ��"));
		jpFareGuest.setBorder(new TitledBorder("��ȸ��"));
		
		mainPanel.setLayout(new GridLayout(1,2));
		jpFareMember.setLayout(new GridLayout(10,2));
		jpFareGuest.setLayout(new GridLayout(10,2));
		
		mjtfs=new JTextField[10];
		gjtfs=new JTextField[10];
		
		Integer[] memberFare=null;
		Integer[] guestFare=null;
		
		try {
			memberFare = f_dao.selectMemberFare();
			guestFare = f_dao.selectGuestFare();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<mjtfs.length;i++) {
			mjtfs[i]=new JTextField(String.valueOf(memberFare[i]));
			mjtfs[i].setBorder(new TitledBorder(i+1+"�ð�"));
			jpFareMember.add(mjtfs[i]);
		}
		
		for(int i=0;i<gjtfs.length;i++) {
			gjtfs[i]=new JTextField(String.valueOf(guestFare[i]));
			gjtfs[i].setBorder(new TitledBorder(i+1+"�ð�"));
			jpFareGuest.add(gjtfs[i]);
		}
		
		jbtnUpdate = new JButton("����");
		
//		mainPanel.setLayout(null);
//		jpFareMember.setBounds(0, 0, 500, 400);
//		jpFareGuest.setBounds(500, 0, 500, 400);
		
		mainPanel.add(jpFareMember);
		mainPanel.add(jpFareGuest);
		
		JPanel honPanel = new JPanel();
		honPanel.add("Center", mainPanel);
		honPanel.add("South",  jbtnUpdate);
		
		honPanel.setBounds(0, 0, 1000, 600);
		
		add(honPanel);
		
		PMFareController fc = new PMFareController(this);
		jbtnUpdate.addActionListener(fc);
		
		
		
		setVisible(true);
		setBounds(100, 100, 1000, 600);
	}

	public JTextField[] getMjtfs() {
		return mjtfs;
	}

	public JTextField[] getGjtfs() {
		return gjtfs;
	}

	public JButton getJbtnUpdate() {
		return jbtnUpdate;
	}

	public static void main(String[] args) {
		new PMFareView();
	}
}
