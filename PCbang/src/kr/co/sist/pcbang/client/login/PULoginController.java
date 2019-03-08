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
 * Ŭ���̾�Ʈ �α���
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
		if (ae.getSource() == pulv.getJbtStart()) {// �α��� ��ư�� ������ ��
			loginBtn();
		}
		if(ae.getSource()==pulv.getJbtMembership()) {
			//JOptionPane.showMessageDialog(pulv, "ȸ������!");
			new PUPolicyView();
			//pulv.dispose();
		}//end if
		if(ae.getSource()==pulv.getJbtFind()) {
			new PUFindUserView(pulv);
		}//end if
	}// actionPerformed

	private void loginBtn() {
		// View�� �Էµ� �� �޾ƿ���
		String id = pulv.getJtfId().getText().trim();
		String pass = String.valueOf(pulv.getJpfPass().getPassword()).trim();
		String strCardNum = pulv.getJtfCardNum().getText().trim();

		if (!id.equals("") && (!pass.equals(""))) {// ȸ�� ������ �Էµ��� ��
			if (checkID(new PUCertificationVO(id, pass))) {// ���̵� ������ �ִٸ�
				String memberIdStatus;
				try {
					memberIdStatus = pul_dao.selectMemberIdStatus(id);
					if (memberIdStatus.equals("Y")) {// �����
						JOptionPane.showMessageDialog(pulv, "�¼������� ���� ���ּ���.");
						return;
					}else if(memberIdStatus.equals("C")){
						PUMainView.seatNum = pul_dao.selectMemberSeatNum(id);
						openPUMV(id);
					} else {
						PUMainView.seatNum = 0;
						openPUMV(id);
					}
//					else if (memberIdStatus.equals("N")) {// ������
//						openPUMV(id);
//					} else if (memberIdStatus.equals("C")) {// ����������� �¼����� ��û
//						openPUMV(id);
//					}
				} catch (SQLException e) {
					//���ܰ� �߻��ϸ� ���� �¼���ȣ�� �ƴ� �ٸ��¼���ȣ�� ����
					
					e.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(pulv, "���̵� ��й�ȣ�� Ȯ�����ּ���.");
			}
		} else if (!strCardNum.equals("")) {// ��ȸ�� ������ �Էµ��� ��
			int cardNum;
			try {
				cardNum = Integer.parseInt(strCardNum);
				if (checkCardNum(cardNum)) {// ī�� ��ȣ�� �ִٸ�
					// �̹� ��������� Ȯ��
					try {
						String guestIdStatus = pul_dao.selectGuestIdStatus(cardNum);
						if (guestIdStatus.equals("Y")) {// �����
							JOptionPane.showMessageDialog(pulv, "�¼������� ���� ���ּ���.");
						}else if(guestIdStatus.equals("C")) {
							PUMainView.seatNum = pul_dao.selectMemberSeatNum(id);
							openPUMV(cardNum);
						}else {
							PUMainView.seatNum = 0;
							openPUMV(cardNum);
						}
//						else if (guestIdStatus.equals("N")) {// ������
//							openPUMV(cardNum);
//						} else if (guestIdStatus.equals("C")) {// ����������� �¼����� ��û
//							openPUMV(cardNum);
//						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(pulv, "��ϵ��� ���� ī���ȣ�Դϴ�..");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(pulv, "ī���ȣ�� ���ڸ� �Է����ּ���.");
				return;
			}

		} else {// �ƹ��͵� �Էµ��� �ʾ��� ��
			JOptionPane.showMessageDialog(pulv, "�α��� ������ �Է����ּ���");
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
		PUMainView.userId = id;// �α����� �����ߴٸ� id�� ��簴ü���� ����� �� �ֵ��� static ������ �����Ѵ�.
		PUMainView.cardNum = "";
		InetAddress ip;
			try {
				ip = InetAddress.getLocalHost();
				String pcIp = String.valueOf(ip).substring(InetAddress.getLocalHost().toString().indexOf("/") + 1);
				if(pul_dao.updateMemberState(new PUMemberStateVO(id, pcIp))) {
					new PUMainView();
					pulv.dispose();
				}else {
					JOptionPane.showMessageDialog(pulv, "�ȵ�");
				}//end else
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}//end catch
	}//openPUMV

	private void openPUMV(int cardNum) throws SQLException {
		PUMainView.userId = "";
		PUMainView.cardNum = String.valueOf(cardNum);// �α����� �����ߴٸ� cardNum�� ��簴ü���� ����� �� �ֵ��� static ������ �����Ѵ�.
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
		int flag = JOptionPane.showConfirmDialog(pulv, "�α��� ���� �����ø� ����� �Ұ��� �մϴ�.\n�׷��� �����Ͻðڽ��ϱ�?");
		if (flag == 0) {
			pulv.dispose();
		} else {
			// �ƴϿ並 ������ �ٽ� �α��� â���ΤФФ�
		} // end else
	}// windowClosing

	/**
	 * ��ȸ�� �α���
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
			JOptionPane.showMessageDialog(pulv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		} catch (UnknownHostException uhe) {
			JOptionPane.showMessageDialog(pulv, "ip��ȸ ����");
			uhe.printStackTrace();
		} // end catch
		return flag;
	}// login guest

	/**
	 * ȸ�� �α���
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
				// System.out.println("�������?");
				pul_dao.updateMemberState(pumsvo);
				// System.out.println("�������?2222");
//			}else {
//				JOptionPane.showMessageDialog(pulv, "��й�ȣ�� Ʋ�Ƚ��ϴ�.");
//				return false;
			}
		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(pulv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		} catch (UnknownHostException uhe) {
			JOptionPane.showMessageDialog(pulv, "ip��ȸ ����");
			uhe.printStackTrace();
		} // end catch
		return flag;
	}// login member

	/**
	 * �������� �ҷ�����
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
