package kr.co.sist.pcbang.manager.fare;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class PMFareView extends JPanel {

	 private JTextField[] mjtfs, gjtfs;
	 private JButton jbtnUpdate;
	 private PMFareDAO f_dao;
	
	public PMFareView() {
//		super("요금제 변경");
		f_dao = PMFareDAO.getInstance();
	
		JPanel mainPanel = new JPanel();
		
		JPanel jpFareMember = new JPanel();
		JPanel jpFareGuest = new JPanel();
		
		jpFareMember.setBorder(new TitledBorder("회원"));
		jpFareGuest.setBorder(new TitledBorder("비회원"));
		
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
			mjtfs[i].setBorder(new TitledBorder(i+1+"시간"));
			jpFareMember.add(mjtfs[i]);
		}
		
		for(int i=0;i<gjtfs.length;i++) {
			gjtfs[i]=new JTextField(String.valueOf(guestFare[i]));
			gjtfs[i].setBorder(new TitledBorder(i+1+"시간"));
			jpFareGuest.add(gjtfs[i]);
		}
		
		jbtnUpdate = new JButton("수정");
		
		mainPanel.add(jpFareMember);
		mainPanel.add(jpFareGuest);
		
		add(mainPanel);
		add("South",jbtnUpdate);
		
		PMFareController fc = new PMFareController(this);
		jbtnUpdate.addActionListener(fc);
		
		setVisible(true);
		setBounds(100, 100, 600, 500);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

}
