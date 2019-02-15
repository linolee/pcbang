package kr.co.sist.pcbang.client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;
import kr.co.sist.pcbang.client.main.PUMainView;

/**
 * Ŭ���̾�Ʈ �α���
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
		if(ae.getSource()==pulv.getJbtStart()) {//�α���
			new PUMainView();
			pulv.dispose();
			
			if(checkMember()) {//ȸ���̸�
				if(loginStatus()=="") {//�α��� ������ ��������
					//�α���
					login("","");
				}else if(loginStatus()=="") {
					//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û��
				}else if(loginStatus()=="") {
					//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û����
					
				}else {//�� �ƴϸ�
					
				}//end else
			}else{//��ȸ���̸�
				if(loginStatus()=="") {//�α��� ������ ��������
					//�α���
					login(100);
				}else if(loginStatus()=="") {
					//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û��
				}else if(loginStatus()=="") {
					//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û����
					
				}else {//�� �ƴϸ�
					
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
	 * ȸ��, ��ȸ�� ����
	 * @return false�� ��ȸ�� true�� ȸ��
	 */
	private boolean checkMember() {
		boolean flag=false;
		
		return flag;
	}//checkNull
	
	/**
	 * ���̵� �α��� ������ Ȯ��, �̵������� �������� Ȯ��
	 * @return 
	 */
	private String loginStatus() {
		String flag="";
		
		return flag;
	}//loginStatus
	
	/**
	 * ��ȸ�� �α���
	 * @param guestNum
	 * @return
	 */
	private boolean login(int guestNum) {
		boolean flag=false;
		
		return flag;
	}//login guest
	
	/**
	 * ȸ�� �α���
	 * @param pucvo
	 * @return
	 */
	private boolean login(String id,String pass) {//PUCertificationVO pucvo
		boolean flag=false;
		
		return flag;
	}//login member
	
	/**
	 * �������� �ҷ�����
	 * @return
	 */
	private void setNotice() {
		String notice=pul_dao.selectNotice();
		JTextArea jt=pulv.getJtaNotice();
		jt.setText(notice);
	}//setNotice

}//class
