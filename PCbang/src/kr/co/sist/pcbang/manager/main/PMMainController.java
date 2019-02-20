package kr.co.sist.pcbang.manager.main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import kr.co.sist.pcbang.manager.login.PMLoginView;
import kr.co.sist.pcbang.manager.magageraccount.PMManagerAccountView;

public class PMMainController extends WindowAdapter implements ActionListener, MouseListener, Runnable {

	private PMMainView pmmv;
	private PMMainDAO pmm_dao;
	private static String adminId;

	public PMMainController(PMMainView pmmv) {
		this.pmmv = pmmv;
		setNotice();
	} // PMMainController
	
	/* 공지사항 추가 메소드 */
	private void addNotice() throws SQLException{
		
		JTextArea jtaNotice = pmmv.getJtaNotice();
		
		if(jtaNotice.getText().equals("")) {
			JOptionPane.showMessageDialog(pmmv, "공지사항을 입력하세요.");
			jtaNotice.requestFocus();
			return;
		} // end if
		
		//입력한 값으로 객체 생성
		PMMainVO pmmVO = new PMMainVO(jtaNotice.getText());
		
		//계정 추가 dao호출
		PMMainDAO.getInstance().insertNotice(pmmVO);

		JOptionPane.showMessageDialog(pmmv, "공지사항이 변경되었습니다.");		
		
	} // addNotice

	/* 공지사항에 보여주기 */
	public void setNotice() {
		JTextArea jtaNotice = pmmv.getJtaNotice();
		String noticeText = "";

		try {
			noticeText = PMMainDAO.getInstance().selectNotice();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		jtaNotice.setText(noticeText);
		
	} // setNotice
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==pmmv.getJbtAccount()) {
			new PMManagerAccountView();
		} // end if
				
		if(ae.getSource()==pmmv.getJbtLogOut()) {
			switch (JOptionPane.showConfirmDialog(pmmv, "로그아웃 하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				new PMLoginView();
				pmmv.setVisible(false);				
				break;
			} // end switch
		} // end if
		
		if(ae.getSource()==pmmv.getJbtNoticeSave()) {
			switch (JOptionPane.showConfirmDialog(pmmv, "공지사항을 변경하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				try {
					addNotice();
				} catch (SQLException e) {
					e.printStackTrace();
				} // end catch
				
				break;

			} // end switch
		} //end if
	} // actionPerformed
	
	@Override
	public void run() {

	} // run
	
	@Override
	public void mouseClicked(MouseEvent we) {
		
	} // mouseClicked
	
	@Override
	public void windowClosing(WindowEvent we) {
		pmmv.dispose();
	} // windowClosing
	
	@Override
	public void windowClosed(WindowEvent we) {
		System.exit(0); // JVM의 모든 인스턴스 종료		
	} // windowClosed
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
} // class

