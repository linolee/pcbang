package kr.co.sist.pcbang.client.login.newuser.seq;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.pcbang.client.login.newuser.PUNewUserView;

public class PUSeqController extends WindowAdapter implements ActionListener, MouseListener {

	private PUSeqView pusv;
	private PUZipcodeDAO puz_dao;
	private PUNewUserView punuv;

	public static final int DBL_CLICK = 2;

	private int selectedRow;
	private String zipcode;
	private String addr;

	public PUSeqController(PUSeqView pusv, PUNewUserView punuv) {
		this.pusv = pusv;
		this.punuv = punuv;
		puz_dao = PUZipcodeDAO.getInstance();
	} // PUSeqController

	public void searchAddr() {
		DefaultTableModel dtmAddr = pusv.getDtmAddr();
		dtmAddr.setRowCount(0);

		try {
			String dong = pusv.getJtfAddr().getText();
			List<PUZipcodeVO> listAddr = puz_dao.selectAddr(dong);

			PUZipcodeVO puzvo = null;
			Object[] rowData = null;
			for (int i = 0; i < listAddr.size(); i++) {
				puzvo = listAddr.get(i);

				rowData = new Object[6];
				rowData[0] = puzvo.getZipcode();
				rowData[1] = puzvo.getSido();
				rowData[2] = puzvo.getGugun();
				rowData[3] = puzvo.getDong();
				rowData[4] = puzvo.getBunji();
				rowData[5] = puzvo.getSeq();

				// DTM�� �߰�
				dtmAddr.addRow(rowData);

			} // end for

			if (listAddr.isEmpty()) {// �ּҰ� ���� ��
				JOptionPane.showMessageDialog(pusv, "ã�� �� ���� �ּ��Դϴ�.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pusv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} // end catch

	}// setAccount

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pusv.getJbtSearch() || ae.getSource() == pusv.getJtfAddr()) {
			searchAddr();
		} // end if
	} //

	@Override
	public void mouseClicked(MouseEvent me) {

		if (me.getSource() == pusv.getJtZipcode() || me.getButton() == MouseEvent.BUTTON1) {

			JTable jt = pusv.getJtZipcode();

			// ���콺 �����Ͱ� Ŭ���Ǹ� ���̺��� Ŭ���� ���� �������� ��
			int r = jt.rowAtPoint(me.getPoint());
			if (r >= 0 && r < jt.getRowCount()) {
				jt.setRowSelectionInterval(r, r);// ������� ���� ������ ���� ���� �ϴ���
			} else {
				jt.clearSelection();
			} // end else
				// ������ ���� �ִ´�.
			selectedRow = r;

			// �����ȣ
			zipcode = (String) jt.getValueAt(jt.getSelectedRow(), 0);

			// �ּ�
			addr = jt.getValueAt(jt.getSelectedRow(), 1) + " " + jt.getValueAt(jt.getSelectedRow(), 2) + " "
					+ jt.getValueAt(jt.getSelectedRow(), 3)+" ";
			
			//������ null�� �ƴҰ�쿡�� �ּҿ� �־��ش�.
			if ((jt.getValueAt(jt.getSelectedRow(), 4) != null)) {
				addr += jt.getValueAt(jt.getSelectedRow(), 4);
			} // end if

		} // end if

		switch (me.getClickCount()) {
		case DBL_CLICK:
			if (me.getSource() == pusv.getJtZipcode()) { // �����ȣ ���̺��� ����Ŭ���� �Ǹ�
				switch (JOptionPane.showConfirmDialog(pusv, "'�����ȣ : "+zipcode+"'\n'�ּ� : "+addr+"'��(��) �½��ϱ�?")) {
				case JOptionPane.OK_OPTION:
				try{
					punuv.getJtfZipcode().setText(zipcode); 
					punuv.getJtfAddr().setText(addr);
					pusv.dispose();
				}catch (NullPointerException e) {
					e.printStackTrace();
				} // end catch
				break;
				} // end switch								
				
			} // end if

		}// end switch

	} // mouseClicked

	@Override
	public void windowClosing(WindowEvent we) {
		pusv.dispose();
	} // windowClosing

	@Override
	public void mousePressed(MouseEvent me) {

	}

	@Override
	public void mouseReleased(MouseEvent me) {

	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {

	}

} // class
