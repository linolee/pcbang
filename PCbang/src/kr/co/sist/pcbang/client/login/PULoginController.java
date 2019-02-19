package kr.co.sist.pcbang.client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.co.sist.pcbang.client.login.newuser.PUPolicyView;
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
//			new PUMainView();
//			pulv.dispose();
			
			if(checkMember()) {//회원이면
				JTextField jtf=pulv.getJtfId();
				JPasswordField jpf=pulv.getJpfPass();
				
				String id=jtf.getText().trim();
				String pass=new String (jpf.getPassword());
				
				if(loginStatus().equals("o")) {//로그인 가능한 상태인지
					//로그인
					PUCertificationVO pucvo=new PUCertificationVO(id, pass);
					String memberName=login(pucvo);
					
					if(memberName.equals("")) {//수행한 결과가 ""라면 
						JOptionPane.showMessageDialog(pulv, "아이디나 비밀번호를 확인하세요.");
						jtf.setText("");
						jpf.setText("");
						jtf.requestFocus();
					}else {
						new PUMainView();//new PUMainView(memberName);
						PUMainView.userId=id;//로그인이 성공했다면 id를 모든객체에서 사용할 수 있도록 static 변수로 설정한다.
						PUMainView.cardNum="";
						pulv.dispose();
					}//end else
				}else if(loginStatus().equals("c")) {
					//이미 로그인 되어있는데=>자리변경 신청함
					System.out.println("자리변경한 상태입니다->로그인");

					PUCertificationVO pucvo=new PUCertificationVO(id, pass);
					String memberName=login(pucvo);
					
					if(memberName.equals("")) {//수행한 결과가 ""라면 
						JOptionPane.showMessageDialog(pulv, "아이디나 비밀번호를 확인하세요.");
						jtf.setText("");
						jpf.setText("");
						jtf.requestFocus();
					}else {
						new PUMainView();//new PUMainView(memberName);
						PUMainView.userId=id;//로그인이 성공했다면 id를 모든객체에서 사용할 수 있도록 static 변수로 설정한다.
						PUMainView.cardNum="";
						pulv.dispose();
					}//end else
				}else if(loginStatus().equals("x")) {
					//이미 로그인 되어있는데=>자리변경 신청안함
					JOptionPane.showMessageDialog(pulv, "자리변경을 신청해 주세요!");
				}//end else
				
				
			}else{//비회원이면
				JTextField jtf2=pulv.getJtfCardNum();
				int card=Integer.parseInt(jtf2.getText().trim());
				
				if(loginStatus()=="o") {//로그인 가능한 상태인지
					//로그인
					if(login(card)) {
						new PUMainView();//new PUMainView(memberName);
						PUMainView.userId="";//로그인이 성공했다면 id를 모든객체에서 사용할 수 있도록 static 변수로 설정한다.
						PUMainView.cardNum=String.valueOf(card);
						pulv.dispose();
					}else {
						JOptionPane.showMessageDialog(pulv, "카드번호를 확인해주세요");
					}
				}else if(loginStatus()=="c") {
					//이미 로그인 되어있는데=>자리변경 신청함
					if(login(card)) {
						new PUMainView();//new PUMainView(memberName);
						PUMainView.userId="";//로그인이 성공했다면 id를 모든객체에서 사용할 수 있도록 static 변수로 설정한다.
						PUMainView.cardNum=String.valueOf(card);
						pulv.dispose();
					}else {
						JOptionPane.showMessageDialog(pulv, "카드번호를 확인해주세요");
					}
				}else if(loginStatus()=="x") {
					//이미 로그인 되어있는데=>자리변경 신청안함
					JOptionPane.showMessageDialog(pulv, "자리변경을 신청해 주세요!");
				}//end else
			}//end else
			
		}//end if
		if(ae.getSource()==pulv.getJbtMembership()) {
			new PUPolicyView();
			pulv.dispose();
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
		boolean flagmember=true;
		boolean flagId=false;
		boolean flagPass=false;
		boolean flagCard=false;
		
		JTextField jtf1=pulv.getJtfId();
		JPasswordField jpf=pulv.getJpfPass();
		String pass=new String(jpf.getPassword());
		JTextField jtf2=pulv.getJtfCardNum();
		//System.out.println(jtf1.getText().trim()+"/"+pass.trim()+"/"+jtf2.getText().trim());
		
		if(jtf2.getText().trim().equals("")) {
			if(jtf1.getText().trim().equals("")) {
				jtf1.setText("");
				jtf1.requestFocus();
				flagId=true;
				System.out.println("아이디를 입력해 주세요");
				JOptionPane.showMessageDialog(pulv, "아이디를 입력해 주세요.");
			}else if(pass.trim().equals("")) {
				jpf.setText("");//공백을 입력하고 엔터친 경우 JPasswordField의 값을 초기화
				jpf.requestFocus();
				flagPass=true;
				System.out.println("비밀번호를 입력해 주세요");
				JOptionPane.showMessageDialog(pulv, "비밀번호를 입력해 주세요.");
			}//end if
			if(!flagId&&!flagPass) {
				flagmember=true;//회원
				//System.out.println("회원 입니다.");
			}//end if
		}else if(jtf1.getText().trim().equals("") && pass.trim().equals("")){
			if(jtf2.getText().trim().equals("")) {
				jtf2.setText("");
				jtf2.requestFocus();
				flagCard=true;
				System.out.println("비회원 카드번호를 입력해주세요");
			}//end if
			if(!flagCard) {
				flagmember=false;//비회원
			}//end if
//		}else {
//			System.out.println("로그인해 주세요!");
		}
		return flagmember;
	}//checkNull
	
	/**
	 * 아이디가 로그인 중인지 확인, 이동가능한 상태인지 확인=>회원인지 비회원인지 어케 알음?!
	 * @return 
	 */
	private String loginStatus() {
		String flag="";
		char status;
		try {
			status = pul_dao.selectStatus();
			flag=String.valueOf(status);
			//flag=String.valueOf("o");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		return flag;
	}//loginStatus
	
	/**
	 * 비회원 로그인
	 * @param guestNum
	 * @return
	 */
	private boolean login(int guestNum) {
		boolean flag=false;
		PULoginDAO pul_dao=PULoginDAO.getInstance();
		try {
			pul_dao.login(guestNum);
//			pul_dao.login(100);//100번을 조회해서 없으면
			flag=true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pulv, "DB에서 문제가 발생했습니다.");//<쿼리쪽에 문제가 없음을 보았음으로 DB탓
			e.printStackTrace();
		}//end catch
		return flag;
	}//login guest
	
	/**
	 * 회원 로그인
	 * @param pucvo
	 * @return
	 */
	private String login(PUCertificationVO pucvo) {
		String adminName="";
		
		PULoginDAO pul_dao=PULoginDAO.getInstance();
		try {
			adminName=pul_dao.login(pucvo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pulv, "DB에서 문제가 발생했습니다.");//<쿼리쪽에 문제가 없음을 보았음으로 DB탓
			e.printStackTrace();
		}//end catch
		
		return adminName;
	}//login member
	
	/**
	 * 공지사항 불러오기
	 * @return
	 */
	private void setNotice() {
		String notice="";
		try {
			notice=pul_dao.selectNotice();
			JTextArea jt=pulv.getJtaNotice();
			jt.setText(notice);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
	}//setNotice

}//class
