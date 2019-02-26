package kr.co.sist.pcbang.client.login.finduser;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.login.finduser.updatepass.PUUpdatePassView;

public class PUFindUserController extends WindowAdapter implements ActionListener, KeyListener {

	private PUFindUserView pufuv;
	private PUFindUserDAO pufu_dao;
	
	public PUFindUserController(PUFindUserView pufuv) {
		this.pufuv=pufuv;
		pufu_dao=PUFindUserDAO.getInstance();
	}//PUFindUserController
	
	private void findId() {
		String userName=pufuv.getJtfUserIdName().getText();
		String id="";
		StringBuilder userPhone=new StringBuilder();
		
		userPhone
		.append(pufuv.getJtfIdPhone1().getText()).append("-")
		.append(pufuv.getJtfIdPhone2().getText()).append("-")
		.append(pufuv.getJtfIdPhone3().getText());
		
		PUFindUserVO pufuvo=new PUFindUserVO(userName, userPhone.toString());
		try {
			id=pufu_dao.searchId(pufuvo);
			if(!id.equals("")) {
				JOptionPane.showMessageDialog(pufuv, userName+"님의 아이디는 "+id+" 입니다.");
			}//end if
			if(id.equals("")) {
				JOptionPane.showMessageDialog(pufuv, "등록된 정보로 아이디가 조회되지않습니다.");
			}//end if
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(pufuv, "DB에 문제발생!!");
			se.printStackTrace();
		}//end catch
	}//findId
	
	private void findIdCheckNull() {
		String idName=pufuv.getJtfUserIdName().getText();
		String phone1=pufuv.getJtfIdPhone1().getText();
		String phone2=pufuv.getJtfIdPhone2().getText();
		String phone3=pufuv.getJtfIdPhone3().getText();
		
		
		if(idName.trim().equals("")) {
			pufuv.getJtfUserIdName().requestFocus();
			return;
		}//end if
		
		if(phone1.trim().equals("")) {
			pufuv.getJtfIdPhone1().requestFocus();
			return;
		}//end if
		
		if(phone2.trim().equals("")) {
			pufuv.getJtfIdPhone2().requestFocus();
			return;
		}//end if
		
		if(phone3.trim().equals("")) {
			pufuv.getJtfIdPhone3().requestFocus();
			return;
			
		}//end if
		
		try {
			Integer.parseInt(phone1);
			Integer.parseInt(phone2);
			Integer.parseInt(phone3);
		}catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pufuv, "번호는 숫자이어야 합니다.");
			return;
		}//end catch
		
		findId();
		
	}//findIdCheckNull
	
	private void findPassCheckNull() {
		String id=pufuv.getJtfUserPassId().getText();
		String passName=pufuv.getJtfUserPassName().getText();
		String phone1=pufuv.getJtfPassPhone1().getText();
		String phone2=pufuv.getJtfPassPhone2().getText();
		String phone3=pufuv.getJtfPassPhone3().getText();
		
		if(id.trim().equals("")) {
			pufuv.getJtfUserPassId().requestFocus();
			return;
		}//end if
		
		if(passName.trim().equals("")) {
			pufuv.getJtfUserPassName().requestFocus();
			return;
		}//end if
		
		if(phone1.trim().equals("")) {
			pufuv.getJtfPassPhone1().requestFocus();
			return;
		}//end if
		
		if(phone2.trim().equals("")) {
			pufuv.getJtfPassPhone2().requestFocus();
			return;
		}//end if
		
		if(phone3.trim().equals("")) {
			pufuv.getJtfPassPhone3().requestFocus();
			return;
		}//end if
		
		try {
			Integer.parseInt(phone1);
			Integer.parseInt(phone2);
			Integer.parseInt(phone3);
		}catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pufuv, "번호는 숫자이어야 합니다.");
			return;
		}//end catch
		
		checkInform();
		
	}//findPassCheckNull
	
	private void checkInform() {
		String userId=pufuv.getJtfUserPassId().getText();
		String userName=pufuv.getJtfUserPassName().getText();
		StringBuilder userPhone=new StringBuilder();
		
		userPhone
		.append(pufuv.getJtfPassPhone1().getText()).append("-")
		.append(pufuv.getJtfPassPhone2().getText()).append("-")
		.append(pufuv.getJtfPassPhone3().getText());
		
		PUFindPassVO pufpvo=new PUFindPassVO(userId, userName, userPhone.toString());
		
		try {
			if(pufu_dao.searchPass(pufpvo)) {
				new PUUpdatePassView(pufuv);
			}//end if
			if(!pufu_dao.searchPass(pufpvo)) {
				JOptionPane.showMessageDialog(pufuv, "등록된 정보로 조회되지 않습니다!");
			}//end if
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(pufuv, "DB에 문제발생!!");
			se.printStackTrace();
		}//end catch
		
		
	}//checkInform
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if( (ae.getSource()==pufuv.getJtfUserIdName()) || (ae.getSource()==pufuv.getJtfIdPhone1())
				|| (ae.getSource() == pufuv.getJtfIdPhone2()) || (ae.getSource() == pufuv.getJtfIdPhone3())
				|| (ae.getSource() == pufuv.getJbtIdSearch())) {
			findIdCheckNull();
		}//end if
		
		if( (ae.getSource()==pufuv.getJtfUserPassName()) || (ae.getSource() == pufuv.getJtfUserPassId()) || 
				(ae.getSource()==pufuv.getJtfPassPhone1())|| (ae.getSource() == pufuv.getJtfPassPhone2()) ||
				(ae.getSource() == pufuv.getJtfPassPhone3()) || (ae.getSource() == pufuv.getJbtPassSearch()) ) {
			findPassCheckNull();
		}//end if
		
	}//actionPerformed
	
	@Override
	public void windowClosing(WindowEvent we) {
		pufuv.dispose();
	}//windowClosing

	@Override
	public void keyTyped(KeyEvent ke) {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		if(ke.getSource()==pufuv.getJtfIdPhone1()) {
			if(pufuv.getJtfIdPhone1().getText().length()==3) {
				pufuv.getJtfIdPhone2().requestFocus();
			}//end if
		}//end if
		if(ke.getSource()==pufuv.getJtfIdPhone2()) {
			if(pufuv.getJtfIdPhone2().getText().length()==4) {
				pufuv.getJtfIdPhone3().requestFocus();
			}//end if
		}//end if
		if(ke.getSource()==pufuv.getJtfPassPhone1()) {
			if(pufuv.getJtfPassPhone1().getText().length()==3) {
				pufuv.getJtfPassPhone2().requestFocus();
			}//end if
		}//end if
		if(ke.getSource()==pufuv.getJtfPassPhone2()) {
			if(pufuv.getJtfPassPhone2().getText().length()==4) {
				pufuv.getJtfPassPhone3().requestFocus();
			}//end if
		}//end if
	}//keyReleased

}//class