package kr.co.sist.pcbang.manager.fare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PMFareController implements ActionListener {
	
	private PMFareView fv;
	private PMFareDAO f_dao;
	
	public PMFareController(PMFareView fv) {
		this.fv = fv;
		f_dao = PMFareDAO.getInstance();
	}

	
	public void updateFare() throws SQLException {
		
		PMFareVO fvo = null;
		int time = 60;
		
		try {
		
		for(int i=0; i<10;i++) {
		fvo = new PMFareVO((time*(i+1)), Integer.parseInt(fv.getMjtfs()[i].getText()), Integer.parseInt(fv.getGjtfs()[i].getText()));
		f_dao.updatePrice(fvo);
		} // for
		if(f_dao.updatePrice(fvo)) {
			JOptionPane.showMessageDialog(fv, "수정완료");
		} 
		
		} catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(fv,  "입력값을 확인해주세요");
		}
	}
	
	public void selectFare() throws SQLException{
		Integer[] memberFare=null;
		Integer[] guestFare=null;
		
		try {
			memberFare = f_dao.selectMemberFare();
			guestFare = f_dao.selectGuestFare();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JTextField[] mjtfs = null;
		JTextField[] gjtfs = null;
		
		mjtfs = fv.getMjtfs();
		gjtfs = fv.getGjtfs();
		
		for(int i=0;i<mjtfs.length;i++) {
			mjtfs[i].setText(String.valueOf(memberFare[i]));
		}
		
		for(int i=0;i<gjtfs.length;i++) {
			gjtfs[i].setText(String.valueOf(guestFare[i]));
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == fv.getJbtnUpdate()) {
						try {
				updateFare();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(ae.getSource() == fv.getJbtnReset()) {
			try {
				selectFare();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}