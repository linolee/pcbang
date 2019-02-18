package kr.co.sist.pcbang.manager.fare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

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
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == fv.getJbtnUpdate()) {
			try {
				updateFare();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
