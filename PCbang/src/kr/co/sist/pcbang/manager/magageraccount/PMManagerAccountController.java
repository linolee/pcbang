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

	/* ���� ��ȸ �޼ҵ� */
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

				// DTM�� �߰�
				dtmAccount.addRow(rowData);

			} // end for

			if (listAccount.isEmpty()) {// ������ ���� ��
				JOptionPane.showMessageDialog(pmmav, "������ ������ �����ϴ�.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmmav, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} // end catch

	}// setAccount

	public void removeAccount() {
		
		StringBuilder deleteMsg=new StringBuilder();
		deleteMsg.append(adminId).append(" ��(��) �����Ͻðڽ��ϱ�?");
		
		switch(JOptionPane.showConfirmDialog(pmmav, deleteMsg.toString())) {
		case JOptionPane.OK_OPTION:

			if(adminId != null) {
			try {
					
				if (pmma_dao.deleteAccount(adminId)) { // DB���̺��� �ش� ���� ����
					//���� ���� �״�� �ѱ�ϴ�. 
					JOptionPane.showMessageDialog(pmmav,  adminId + "��(��) ���� �Ǿ����ϴ�.");
					// �ֹ����̺��� ����
					setAccount();

				} else {
					JOptionPane.showMessageDialog(pmmav, adminId + "�ֹ��� ���� ���� �ʾҽ��ϴ�.","����",JOptionPane.ERROR_MESSAGE);

				} // end else
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(pmmav,  adminId + "DB���� �����߻�", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} // end catch				
			
			} else {
				JOptionPane.showMessageDialog(pmmav, "������ ������ �����ϼ���.", "Fail", JOptionPane.ERROR_MESSAGE);
			} // end else
		}//end switch		
		
	} // removeAccount

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmmav.getJbtOk()) { // 1. Ȯ�ι�ư�� ������ �������� view�� ����
			pmmav.dispose();
		} // end if

		if (ae.getSource() == pmmav.getJbtAdd()) { // 2. �����߰� ��ư�� ������ �����߰� view�� ��������.
			new PMManagerAddAccountView(this);
		} // end if

		if (ae.getSource() == pmmav.getJbtDel()) { // ����
			removeAccount();
		} // end if

	} // actionPerformed

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == pmmav.getJtAccount() || me.getButton() == MouseEvent.BUTTON1) {

			JTable jt = pmmav.getJtAccount();

			// ���콺 �����Ͱ� Ŭ���Ǹ� ���̺��� Ŭ���� ���� �������� ��
			int r = jt.rowAtPoint(me.getPoint());
			if (r >= 0 && r < jt.getRowCount()) {
				jt.setRowSelectionInterval(r, r);// ������� ���� ������ ���� ���� �ϴ���
			} else {
				jt.clearSelection();
			} // end else
				// ������ ���� �ִ´�.
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
