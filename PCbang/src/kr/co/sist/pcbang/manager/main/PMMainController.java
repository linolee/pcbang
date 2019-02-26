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
	
	/* �������� �߰� �޼ҵ� */
	private void addNotice() throws SQLException{
		
		JTextArea jtaNotice = pmmv.getJtaNotice();
		
		if(jtaNotice.getText().equals("")) {
			JOptionPane.showMessageDialog(pmmv, "���������� �Է��ϼ���.");
			jtaNotice.requestFocus();
			return;
		} // end if
		
		//�Է��� ������ ��ü ����
		PMMainVO pmmVO = new PMMainVO(jtaNotice.getText());
		
		//���� �߰� daoȣ��
		PMMainDAO.getInstance().insertNotice(pmmVO);

		JOptionPane.showMessageDialog(pmmv, "���������� ����Ǿ����ϴ�.");		
		
	} // addNotice

	/* �������׿� �����ֱ� */
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
			switch (JOptionPane.showConfirmDialog(pmmv, "�α׾ƿ� �Ͻðڽ��ϱ�?")) {
			case JOptionPane.OK_OPTION:
				new PMLoginView();
				pmmv.setVisible(false);				
				break;
			} // end switch
		} // end if
		
		if(ae.getSource()==pmmv.getJbtNoticeSave()) {
			switch (JOptionPane.showConfirmDialog(pmmv, "���������� �����Ͻðڽ��ϱ�?")) {
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
		System.exit(0); // JVM�� ��� �ν��Ͻ� ����		
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

