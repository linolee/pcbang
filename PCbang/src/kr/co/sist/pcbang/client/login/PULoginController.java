package kr.co.sist.pcbang.client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import kr.co.sist.pcbang.client.charge.PUChargeView;
import kr.co.sist.pcbang.client.login.finduser.PUFindUserView;
import kr.co.sist.pcbang.client.login.newuser.PUPolicyView;
import kr.co.sist.pcbang.client.main.PUMainController;
import kr.co.sist.pcbang.client.main.PUMainDAO;
import kr.co.sist.pcbang.client.main.PUMainInfoVO;
import kr.co.sist.pcbang.client.main.PUMainView;

/**
 * 클라이언트 로그인
 * @author owner
 */
public class PULoginController extends WindowAdapter implements ActionListener {

	private PULoginView pulv;
	private PUMainController pumc;
	private PULoginDAO pul_dao;
	private PUMainDAO pum_dao;

	public PULoginController(PULoginView pulv) {
		this.pulv = pulv;
		pul_dao = PULoginDAO.getInstance();
		pum_dao = PUMainDAO.getInstance();
		setNotice();
	}// PULoginController

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pulv.getJbtStart()) {// 로그인 버튼이 눌렸을 때
			loginBtn();
		}
		if(ae.getSource()==pulv.getJbtMembership()) {
			//JOptionPane.showMessageDialog(pulv, "회원가입!");
			new PUPolicyView();
			//pulv.dispose();
		}//end if
		if(ae.getSource()==pulv.getJbtFind()) {
			new PUFindUserView(pulv);
		}//end if
	}// actionPerformed

	private void loginBtn() {
		// View에 입력된 값 받아오기
		String id = pulv.getJtfId().getText().trim();
		String pass = String.valueOf(pulv.getJpfPass().getPassword()).trim();
		String strCardNum = pulv.getJtfCardNum().getText().trim();

		if (!id.equals("") && (!pass.equals(""))) {// 회원 정보가 입력됐을 때
			if (checkID(new PUCertificationVO(id, pass))) {// 아이디 정보가 있다면
				String memberIdStatus;
				try {
					memberIdStatus = pul_dao.selectMemberIdStatus(id);
					if (memberIdStatus.equals("Y")) {// 사용중
						JOptionPane.showMessageDialog(pulv, "좌석변경을 먼저 해주세요.");
						return;
					}else if(memberIdStatus.equals("C")){
						PUMainView.seatNum = pul_dao.selectMemberSeatNum(id);
						openPUMV(id);
					} else {
						PUMainView.seatNum = 0;
						openPUMV(id);
					}
//					else if (memberIdStatus.equals("N")) {// 비사용중
//						openPUMV(id);
//					} else if (memberIdStatus.equals("C")) {// 사용중이지만 좌석변경 신청
//						openPUMV(id);
//					}
				} catch (SQLException e) {
					//예외가 발생하면 현재 좌석번호가 아닌 다른좌석번호를 삭제
					
					e.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(pulv, "아이디나 비밀번호를 확인해주세요.");
			}
		} else if (!strCardNum.equals("")) {// 비회원 정보가 입력됐을 때
			int cardNum;
			try {
				cardNum = Integer.parseInt(strCardNum);
				if (checkCardNum(cardNum)) {// 카드 번호가 있다면
					// 이미 사용중인지 확인
					try {
						String guestIdStatus = pul_dao.selectGuestIdStatus(cardNum);
						if (guestIdStatus.equals("Y")) {// 사용중
							JOptionPane.showMessageDialog(pulv, "좌석변경을 먼저 해주세요.");
						}else if(guestIdStatus.equals("C")) {
							PUMainView.seatNum = pul_dao.selectMemberSeatNum(id);
							openPUMV(cardNum);
						}else {
							PUMainView.seatNum = 0;
							openPUMV(cardNum);
						}
//						else if (guestIdStatus.equals("N")) {// 비사용중
//							openPUMV(cardNum);
//						} else if (guestIdStatus.equals("C")) {// 사용중이지만 좌석변경 신청
//							openPUMV(cardNum);
//						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(pulv, "등록되지 않은 카드번호입니다..");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(pulv, "카드번호는 숫자를 입력해주세요.");
				return;
			}

		} else {// 아무것도 입력되지 않았을 때
			JOptionPane.showMessageDialog(pulv, "로그인 정보를 입력해주세요");
		}
	}

	private boolean checkID(PUCertificationVO pucvo) {
		boolean flag = false;
		try {
			if (pul_dao.selectMemberLogin(pucvo) != -1) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	private boolean checkCardNum(int cardNum) {
		boolean flag = false;
		try {
			flag = pul_dao.selectGuestCheck(cardNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	private void openPUMV(String id) throws SQLException {
		PUMainView.userId = id;// 로그인이 성공했다면 id를 모든객체에서 사용할 수 있도록 static 변수로 설정한다.
		PUMainView.cardNum = "";
		InetAddress ip;
			try {
				ip = InetAddress.getLocalHost();
				String pcIp = String.valueOf(ip).substring(InetAddress.getLocalHost().toString().indexOf("/") + 1);
				if(pul_dao.updateMemberState(new PUMemberStateVO(id, pcIp))) {
					new PUMainView();
					pulv.dispose();
				}else {
					JOptionPane.showMessageDialog(pulv, "안됨");
				}//end else
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}//end catch
	}//openPUMV

	private void openPUMV(int cardNum) throws SQLException {
		PUMainView.userId = "";
		PUMainView.cardNum = String.valueOf(cardNum);// 로그인이 성공했다면 cardNum를 모든객체에서 사용할 수 있도록 static 변수로 설정한다.
		PUMainView.seatNum = pul_dao.selectGuestSeatNum(cardNum);
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			String pcIp = String.valueOf(ip).substring(InetAddress.getLocalHost().toString().indexOf("/") + 1);
			pul_dao.updateGuestState(new PUGuestStateVO(cardNum, pcIp));
			new PUMainView();
			pulv.dispose();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void windowClosing(WindowEvent we) {
		int flag = JOptionPane.showConfirmDialog(pulv, "로그인 하지 않으시면 사용이 불가능 합니다.\n그래도 종료하시겠습니까?");
		if (flag == 0) {
			pulv.dispose();
		} else {
			// 아니요를 누르면 다시 로그인 창으로ㅠㅠㅠ
		} // end else
	}// windowClosing

	/**
	 * 비회원 로그인
	 * 
	 * @param guestNum
	 * @return
	 */
	private boolean login(int guestNum) {
		boolean flag = false;
		try {
			PULoginDAO pul_dao = PULoginDAO.getInstance();
			InetAddress ip = InetAddress.getLocalHost();
			String pcIp = String.valueOf(ip).substring(InetAddress.getLocalHost().toString().indexOf("/") + 1);

			if (pul_dao.selectGuestCheck(guestNum)) {
				PUGuestStateVO pugsvo = new PUGuestStateVO(guestNum, pcIp);
				pul_dao.updateGuestState(pugsvo);
				flag = true;
			} // end if
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pulv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
		} catch (UnknownHostException uhe) {
			JOptionPane.showMessageDialog(pulv, "ip조회 실패");
			uhe.printStackTrace();
		} // end catch
		return flag;
	}// login guest

	/**
	 * 회원 로그인
	 * 
	 * @param pucvo
	 * @return
	 */
	private boolean login(PUCertificationVO pucvo) {
		// String adminName="";
		boolean flag = false;
		try {
			PULoginDAO pul_dao = PULoginDAO.getInstance();

			InetAddress ip = InetAddress.getLocalHost();
			String pcIp = String.valueOf(ip).substring(InetAddress.getLocalHost().toString().indexOf("/") + 1);
//			 System.out.println(pcIp);
			// System.out.println(String.valueOf(pul_dao.selectMemberLogin(pucvo)).equals(""));
			if (pul_dao.selectMemberLogin(pucvo) != -1) {

				flag = true;
				PUMemberStateVO pumsvo = new PUMemberStateVO(pucvo.getMemberId(), pcIp);
				// System.out.println("여기까지?");
				pul_dao.updateMemberState(pumsvo);
				// System.out.println("여기까지?2222");
//			}else {
//				JOptionPane.showMessageDialog(pulv, "비밀번호가 틀렸습니다.");
//				return false;
			}
		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(pulv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
		} catch (UnknownHostException uhe) {
			JOptionPane.showMessageDialog(pulv, "ip조회 실패");
			uhe.printStackTrace();
		} // end catch
		return flag;
	}// login member

	/**
	 * 공지사항 불러오기
	 * 
	 * @return
	 */
	private void setNotice() {
		String notice = "";
		try {
			notice = pul_dao.selectNotice();
			JTextArea jt = pulv.getJtaNotice();
			jt.setText(notice);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// setNotice

}// class
