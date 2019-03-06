package kr.co.sist.pcbang.manager.fare;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
 
@SuppressWarnings("serial")
public class PMFareView extends JPanel {

	 private JTextField[] mjtfs, gjtfs;
	 private JButton jbtnUpdate, jbtnReset;
	 private PMFareDAO f_dao;
	
	public PMFareView() { 

		f_dao = PMFareDAO.getInstance();
	
		JPanel mainPanel = new JPanel();
		
		JPanel jpFareMember = new JPanel();
		JPanel jpFareGuest = new JPanel();
		
		setLayout(new BorderLayout());
		
		jpFareMember.setBorder(new TitledBorder("회원"));
		jpFareGuest.setBorder(new TitledBorder("비회원"));
		
		
		jpFareMember.setLayout(new GridLayout(10,1));
		jpFareGuest.setLayout(new GridLayout(10,1));
		
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
		jbtnReset = new JButton("초기화");

		
		mainPanel.setLayout(new GridLayout(1,2));
		
		mainPanel.add(jpFareMember);
		mainPanel.add(jpFareGuest);
		
		Panel southPnl = new Panel();
		southPnl.add(jbtnUpdate);
		southPnl.add(jbtnReset);
		
		add("Center", mainPanel); 
		add("South", southPnl);
		
		
		PMFareController fc = new PMFareController(this);
		jbtnUpdate.addActionListener(fc);
		jbtnReset.addActionListener(fc);
		
		
		setVisible(true);

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

	public JButton getJbtnReset() {
		return jbtnReset;
	}


}