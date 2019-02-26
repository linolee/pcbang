package kr.co.sist.pcbang.client.login.newuser.seq;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PUSeqController extends WindowAdapter implements ActionListener {

	private PUSeqView pusv;
	private PUZipcodeDAO puz_dao;

	public PUSeqController(PUSeqView pusv) {
		this.pusv = pusv;
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

				// DTM에 추가
				dtmAddr.addRow(rowData);

			} // end for

			if (listAddr.isEmpty()) {// 주소가 없을 때
				JOptionPane.showMessageDialog(pusv, "올바른 주소를 입력하세요.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pusv, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} // end catch

	}// setAccount

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pusv.getJbtSearch()) {

		} else if (ae.getSource() == pusv.getJtfAddr()) {
			searchAddr();
		} // end if
	} //

} // class