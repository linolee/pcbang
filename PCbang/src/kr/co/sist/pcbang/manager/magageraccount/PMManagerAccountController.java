package kr.co.sist.pcbang.manager.magageraccount;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.pcbang.manager.magageraddaccount.PMManagerAddAccountView;

public class PMManagerAccountController extends WindowAdapter implements ActionListener {

	private PMManagerAccountView pmmav;
	private PMManagerAccountDAO pmma_dao;
	
	public PMManagerAccountController(PMManagerAccountView pmmav) {
		this.pmmav = pmmav;
		pmma_dao = PMManagerAccountDAO.getInstance();
		setAccount();
		
	} // PMManagerAccountController
	
	public void setAccount() {
		DefaultTableModel dtmAccount = pmmav.getDtmAccount();
		dtmAccount.setRowCount(0);

		try {
			List<PMManagerAccountVO> listAccount = pmma_dao.selectAccount();
			
			PMManagerAccountVO pmmaVO = null;
			Object[] rowData = null;
			for (int i = 0; i < listAccount.size(); i++) {
				pmmaVO = listAccount.get(i);
				
				rowData = new Object[3];
				rowData[0] = pmmaVO.getAdminId();
				rowData[1] = pmmaVO.getAdminName();
				rowData[2] = pmmaVO.getAdminDate();

				// DTM에 추가
				dtmAccount.addRow(rowData);

			} // end for
			
			if (listAccount.isEmpty()) {// 계정이 없을 때
				JOptionPane.showMessageDialog(pmmav, "관리자 계정이 없습니다.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmmav, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}// end catch

	}// setLunch
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==pmmav.getJbtOk()) {
			pmmav.dispose();
		}//end if
		
		if(ae.getSource()==pmmav.getJbtAdd()) {
			new PMManagerAddAccountView(this);
		}// end if
	} // actionPerformed

	@Override
	public void windowClosing(WindowEvent we) {
		pmmav.dispose();
	}
	
}
