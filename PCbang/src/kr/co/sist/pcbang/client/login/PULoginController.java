package kr.co.sist.pcbang.client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;
import kr.co.sist.pcbang.client.main.PUMainView;

/**
 * 클라이언트 로그인
 * @author owner
 */
public class PULoginController extends WindowAdapter implements ActionListener{

	private PULoginView pulv;
	private PULoginDAO pul_dao;
	
	public PULoginController(PULoginView pulv) {
		this.pulv=pulv;
		pul_dao=PULoginDAO.getInstance();
		setNotice();
	}//PULoginController
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==pulv.getJbtStart()) {//로그인
			new PUMainView();
			pulv.dispose();
			
			if(checkMember()) {//회원이면
				if(loginStatus()=="") {//로그인 가능한 상태인지
					//로그인
					login("","");
				}else if(loginStatus()=="") {
					//이미 로그인 되어있는데=>자리변경 신청함
				}else if(loginStatus()=="") {
					//이미 로그인 되어있는데=>자리변경 신청안함
					
				}else {//다 아니면
					
				}//end else
			}else{//비회원이면
				if(loginStatus()=="") {//로그인 가능한 상태인지
					//로그인
					login(100);
				}else if(loginStatus()=="") {
					//이미 로그인 되어있는데=>자리변경 신청함
				}else if(loginStatus()=="") {
					//이미 로그인 되어있는데=>자리변경 신청안함
					
				}else {//다 아니면
					
				}//end else
			}//end else
			
		}//end if
		if(ae.getSource()==pulv.getJbtMembership()) {
			
		}//end if
		if(ae.getSource()==pulv.getJbtFind()) {
			
		}//end if
	}//actionPerformed
	
	@Override
	public void windowClosing(WindowEvent we) {
		pulv.dispose();
	}//windowClosing
	
	/**
	 * 회원, 비회원 구분
	 * @return false는 비회원 true는 회원
	 */
	private boolean checkMember() {
		boolean flag=false;
		
		return flag;
	}//checkNull
	
	/**
	 * 아이디가 로그인 중인지 확인, 이동가능한 상태인지 확인
	 * @return 
	 */
	private String loginStatus() {
		String flag="";
		
		return flag;
	}//loginStatus
	
	/**
	 * 비회원 로그인
	 * @param guestNum
	 * @return
	 */
	private boolean login(int guestNum) {
		boolean flag=false;
		
		return flag;
	}//login guest
	
	/**
	 * 회원 로그인
	 * @param pucvo
	 * @return
	 */
	private boolean login(String id,String pass) {//PUCertificationVO pucvo
		boolean flag=false;
		
		return flag;
	}//login member
	
	/**
	 * 공지사항 불러오기
	 * @return
	 */
	private void setNotice() {
		String notice=pul_dao.selectNotice();
		JTextArea jt=pulv.getJtaNotice();
		jt.setText(notice);
	}//setNotice

}//class
