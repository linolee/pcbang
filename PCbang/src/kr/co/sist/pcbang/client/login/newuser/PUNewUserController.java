package kr.co.sist.pcbang.client.login.newuser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import kr.co.sist.pcbang.client.login.newuser.seq.PUSeqView;

public class PUNewUserController extends WindowAdapter implements ActionListener {

	private PUNewUserView punuv;
	
	public PUNewUserController(PUNewUserView punuv) {
		this.punuv = punuv;
	} // PUNewUserController
	
	private boolean checkMemberId() {
		boolean flag = false;
		
		JTextField jtfId = punuv.getJtfId();
		String id = jtfId.getText().trim();
		PUNewUserDAO punu_dao = PUNewUserDAO.getInstance();
		
		try {
			if(punu_dao.selectMemderId(id)) {
				JOptionPane.showMessageDialog(punuv, "이미 사용중 입니다.");	
				
			}else {
				JOptionPane.showMessageDialog(punuv, "사용 가능합니다.");
				
			}// end else
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return flag;
	} // checkMemberId
	
	private boolean checkNull() {
		boolean flag = false;
		
		JTextField jtfId = punuv.getJtfId();
		
		if(jtfId.getText().trim().equals("")) {
			jtfId.setText("");
			jtfId.requestFocus();
			flag=true;
			JOptionPane.showMessageDialog(punuv, "아이디를 입력해 주세요.");		
		} else {
			checkMemberId();
		}
		return flag;
	} // checkNull
	
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==punuv.getJbtIdChack()) {
			checkNull();
		} // end if
		
		if(ae.getSource()==punuv.getJbtAddrSearch()) {
			new PUSeqView();
		}
		
		if(ae.getSource()==punuv.getJbtCancel()) {
			punuv.dispose();
		} // end if
		
	} // actionPerformed

	@Override
	public void windowClosing(WindowEvent we) {
		punuv.dispose();
	}
	
} // class
