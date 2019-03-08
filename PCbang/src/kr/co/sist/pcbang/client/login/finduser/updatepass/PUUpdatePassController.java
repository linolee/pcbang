package kr.co.sist.pcbang.client.login.finduser.updatepass;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.login.finduser.PUFindUserDAO;

public class PUUpdatePassController extends WindowAdapter implements ActionListener{

	private PUUpdatePassView puupv;
	private PUFindUserDAO pufu_dao;
	
	public PUUpdatePassController(PUUpdatePassView puupv) {
		this.puupv=puupv;
		pufu_dao=PUFindUserDAO.getInstance();
	}//PUUpdatePassController

	private void checkNull() {
		
		String pass=new String(puupv.getJpfPass().getPassword());
		String passCheck=new String(puupv.getJpfPassCheck().getPassword());
		
		if(pass.trim().equals("")) {
			puupv.getJpfPass().requestFocus();
			return;
		}//end if
		
		if(passCheck.trim().equals("")) {
			puupv.getJpfPassCheck().requestFocus();
			return;
		}//end if
		
		updatePass();
		
	}//checkNull
	
	private void updatePass() {
		String pass=new String(puupv.getJpfPass().getPassword());
		String passCheck=new String(puupv.getJpfPassCheck().getPassword());
		String id=puupv.getPufuv().getJtfUserPassId().getText();
		System.out.println(pass);
		System.out.println(id);
		try {
			if(pass.equals(passCheck)) {
				PUUpdatePassVO puupvo=new PUUpdatePassVO(pass, id);
				pufu_dao.changePass(puupvo);
				JOptionPane.showMessageDialog(puupv, "비밀번호가 재설정 되었습니다!!");
				puupv.dispose();
			}//end if
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(puupv, "DB에서 문제발생!");
			se.printStackTrace();
		}//end catch
		
		if(!pass.equals(passCheck)) {
			JOptionPane.showMessageDialog(puupv, "비밀번호가 일치하지 않습니다!");
		}//end if
		
	}//updatePass
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if( (ae.getSource() == puupv.getJpfPass()) || (ae.getSource() == puupv.getJpfPassCheck())
				|| (ae.getSource() == puupv.getJbtOk()) ) {
			checkNull();
		}//end if
		
		if( ae.getSource() == puupv.getJbtCancel() ) {
			puupv.dispose();
		}//end if
		
	}//actionPerformed
	
	@Override
	public void windowClosing(WindowEvent we) {
		puupv.dispose();
	}//windowClosing
	
}//class
