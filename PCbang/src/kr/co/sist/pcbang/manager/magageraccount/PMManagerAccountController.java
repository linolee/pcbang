package kr.co.sist.pcbang.manager.magageraccount;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.pcbang.manager.magageraddaccount.PMManagerAddAccountView;

public class PMManagerAccountController extends WindowAdapter implements ActionListener, MouseListener {

	private PMManagerAccountView pmmav;
	private PMManagerAccountDAO pmma_dao;

	private int selectedRow;
	public String adminId;

	public PMManagerAccountController(PMManagerAccountView pmmav) {
		this.pmmav = pmmav;

		pmma_dao = PMManagerAccountDAO.getInstance();
		setAccount();
		System.out.println(adminId);
	} // PMManagerAccountController

	/* 계정 조회 메소드 */
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
		} // end catch

	}// setAccount

	public void removeAccount() {
		
		StringBuilder deleteMsg=new StringBuilder();
		deleteMsg.append(adminId).append(" 을(를) 삭제하시겠습니까?");
		
		switch(JOptionPane.showConfirmDialog(pmmav, deleteMsg.toString())) {
		case JOptionPane.OK_OPTION:

			if(adminId != null) {
			try {
					
				if (pmma_dao.deleteAccount(adminId)) { // DB테이블에서 해당 계정 삭제
					//저기 값을 그대로 넘깁니다. 
					JOptionPane.showMessageDialog(pmmav,  adminId + "이(가) 삭제 되었습니다.");
					// 주문테이블을 생신
					setAccount();

				} else {
					JOptionPane.showMessageDialog(pmmav, adminId + "주문이 삭제 되지 않았습니다.","실패",JOptionPane.ERROR_MESSAGE);

				} // end else
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(pmmav,  adminId + "DB에서 문제발생", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} // end catch				
			
			} else {
				JOptionPane.showMessageDialog(pmmav, "삭제할 계정을 선택하세요.", "Fail", JOptionPane.ERROR_MESSAGE);
			} // end else
		}//end switch		
		
	} // removeAccount

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmmav.getJbtOk()) { // 1. 확인버튼을 누르면 계정관리 view가 종료
			pmmav.dispose();
		} // end if

		if (ae.getSource() == pmmav.getJbtAdd()) { // 2. 계정추가 버튼을 누르면 계정추가 view가 보여진다.
			new PMManagerAddAccountView(this);
		} // end if

		if (ae.getSource() == pmmav.getJbtDel()) { // 삭제
			removeAccount();
		} // end if

	} // actionPerformed

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == pmmav.getJtAccount() || me.getButton() == MouseEvent.BUTTON1) {

			JTable jt = pmmav.getJtAccount();

			// 마우스 포인터가 클릭되면 테이블에서 클릭한 행을 가져오는 일
			int r = jt.rowAtPoint(me.getPoint());
			if (r >= 0 && r < jt.getRowCount()) {
				jt.setRowSelectionInterval(r, r);// 시작행과 끝행 사이의 행을 선택 하는일
			} else {
				jt.clearSelection();
			} // end else
				// 선택한 행을 넣는다.
			selectedRow = r;

			adminId = (String) jt.getValueAt(jt.getSelectedRow(), 0);

		} // end if

	} // mouseClicked

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub

	}

} // class
