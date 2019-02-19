package kr.co.sist.pcbang.client.ordering;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.main.PUMainController;

public class PUOrderingController extends WindowAdapter implements MouseListener, ActionListener {

	private PUOrderingView puov;
	private PUOrderingDAO puo_dao;
	private PUMainController pumc;
	
	public PUOrderingController(PUOrderingView puov) {
		this.puov=puov;
		
	}//PUOrderingController
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==puov.getJbtOk()) {
			JOptionPane.showMessageDialog(puov, "이 목록으로 주문합니다");
		}//end if
		if(ae.getSource()==puov.getJbtExit()) {
			//JOptionPane.showMessageDialog(puov, "닫기");
			puov.dispose();
		}//end if
	}//actionPerformed
	
	@Override
	public void mouseClicked(MouseEvent me) {	
		JOptionPane.showMessageDialog(puov, "마우스 이벤트가 눌렸습니다.");
	}//mouseClicked
	@Override public void mousePressed(MouseEvent e) {	}
	@Override public void mouseReleased(MouseEvent e) {	}
	@Override public void mouseEntered(MouseEvent e) {	}
	@Override public void mouseExited(MouseEvent e) { }

}//class
