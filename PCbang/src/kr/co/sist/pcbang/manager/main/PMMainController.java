package kr.co.sist.pcbang.manager.main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kr.co.sist.pcbang.manager.login.PMLoginView;
import kr.co.sist.pcbang.manager.magageraccount.PMManagerAccountView;


public class PMMainController extends WindowAdapter implements ActionListener, MouseListener, Runnable {

	private PMMainView pmmv;
	private PMMainDAO pmm_dao;
	private static String adminId;
	
	public PMMainController(PMMainView pmmv) {
		this.pmmv = pmmv;
	} // PMMainController
	
	@Override
	public void run() {

	} // run

	@Override
	public void mouseClicked(MouseEvent we) {

	} // mouseClicked


	@Override
	public void actionPerformed(ActionEvent we) {
		if(we.getSource()==pmmv.getJbtAccount()) {
			PMManagerAccountView pmmav = new PMManagerAccountView();
		} // end if
				
		if(we.getSource()==pmmv.getJbtLogOut()) {
			new PMLoginView();
			pmmv.setVisible(false);
		}
	} // actionPerformed
	
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
