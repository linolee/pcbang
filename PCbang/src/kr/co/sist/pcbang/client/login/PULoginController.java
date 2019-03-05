package kr.co.sist.pcbang.client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.co.sist.pcbang.client.login.newuser.PUPolicyView;
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
			try {
			if(checkMember()) {//ȸ���̸�
				JTextField jtf=pulv.getJtfId();
				JPasswordField jpf=pulv.getJpfPass();
				
				String id=jtf.getText().trim();
				String pass=new String (jpf.getPassword());
				System.out.println(id+"/"+pass);
		
				//System.out.println(pul_dao.selectMember(id));
				if(!pul_dao.selectMember(id)) {//PC���̺� ��ȸ�Ǵ� id�� ���ٸ� �α���
					//System.out.println("�α���!!!");
					//�α���
					PUCertificationVO pucvo=new PUCertificationVO(id, pass);
					//String memberName=login(pucvo);
					//System.out.println("!!!");
					
					if(!login(pucvo)) {
						//System.out.println("���̵� ����?");
						JOptionPane.showMessageDialog(pulv, "���̵� ��й�ȣ�� Ȯ���ϼ���.");
						jtf.setText("");
						jpf.setText("");
						jtf.requestFocus();
					}else {
						//System.out.println("�α��εǾ����ϴ�.");
						PUMainView.userId=id;//�α����� �����ߴٸ� id�� ��簴ü���� ����� �� �ֵ��� static ������ �����Ѵ�.
						PUMainView.cardNum="";
						PUMainView.seatNum=0;
						new PUMainView();//new PUMainView(memberName);
						pulv.dispose();
					}//end else
				}else {//�ִٸ� �α����� �ȵŴµ�,�ڸ��̵��̸�~
					System.out.println("�ڸ��̵� ����");
					if(loginStatus().equals("C")) {
						//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û��
						//System.out.println("�ڸ������� �����Դϴ�->�α���");
						
						PUCertificationVO pucvo=new PUCertificationVO(id, pass);
						//String memberName=login(pucvo);
						
						if(!login(pucvo)) {
							JOptionPane.showMessageDialog(pulv, "���̵� ��й�ȣ�� Ȯ���ϼ���.");
							jtf.setText("");
							jpf.setText("");
							jtf.requestFocus();
						}else {
							new PUMainView();
							PUMainView.userId=id;
							PUMainView.cardNum="";
							PUMainView.seatNum=pul_dao.selectMemberSeatNum(id);
							pulv.dispose();
						}//end else
					}else{
						//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û����
						JOptionPane.showMessageDialog(pulv, "�ڸ������� ���� ��û�� �ּ���!");
					}//end else
				}//end else
				
			}else{//��ȸ���̸�
				JTextField jtf2=pulv.getJtfCardNum();
				try {
				int card=Integer.parseInt(jtf2.getText().trim());
				//System.out.println(pul_dao.selectGuest(card));
					if(!pul_dao.selectGuest(card)) {//PC���̺� ��ȸ�Ǵ� id�� ���ٸ� �α���
						//�α���
						if(login(card)) {
							PUMainView.userId="";//�α����� �����ߴٸ� id�� ��簴ü���� ����� �� �ֵ��� static ������ �����Ѵ�.
							PUMainView.cardNum=String.valueOf(card);
							PUMainView.seatNum=0;
							new PUMainView();
							pulv.dispose();
						}else {
							jtf2.setText("");
							jtf2.requestFocus();
							JOptionPane.showMessageDialog(pulv, "ī���ȣ�� Ȯ�����ּ���");
						}//end else
					}else {//�ִٸ� �α����� �ȵŴµ�,�ڸ��̵��̸�~
						if(loginStatus().equals("C")) {
							//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û��
							if(!login(card)) {
								new PUMainView();
								PUMainView.userId="";//�α����� �����ߴٸ� id�� ��簴ü���� ����� �� �ֵ��� static ������ �����Ѵ�.
								PUMainView.cardNum=String.valueOf(card);
								PUMainView.seatNum=pul_dao.selectGuestSeatNum(card);
								pulv.dispose();
							}else {
								jtf2.setText("");
								jtf2.requestFocus();
								JOptionPane.showMessageDialog(pulv, "ī���ȣ�� Ȯ�����ּ���");
							}//end else
						}else{
							//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û����
							jtf2.setText("");
							jtf2.requestFocus();
							JOptionPane.showMessageDialog(pulv, "�ڸ������� ���� ��û�� �ּ���!");
						}//end else
				}//end else
					
				} catch (NumberFormatException nbfe) {
					jtf2.setText("");
					jtf2.requestFocus();
					JOptionPane.showMessageDialog(pulv, "ī���ȣ�� �����Դϴ�.");
					//nbfe.printStackTrace();
				}//end catch
			}//end else
			
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end if
		
		if(ae.getSource()==pulv.getJbtMembership()) {
			//JOptionPane.showMessageDialog(pulv, "ȸ������!");
			new PUPolicyView();
			//pulv.dispose();
		}//end if
		if(ae.getSource()==pulv.getJbtFind()) {
			JOptionPane.showMessageDialog(pulv, "���̵�/��й�ȣ ã��!");
		}//end if
	}//actionPerformed
	
	@Override
	public void windowClosing(WindowEvent we) {
		int flag=JOptionPane.showConfirmDialog(pulv, "�α��� ���� �����ø� ����� �Ұ��� �մϴ�.\n�׷��� �����Ͻðڽ��ϱ�?");
		if(flag==0) {
			pulv.dispose();
		}else {
			//�ƴϿ並 ������ �ٽ� �α��� â���ΤФФ�
		}//end else
	}//windowClosing
	
	/**
	 * ȸ��, ��ȸ�� ����
	 * @return false�� ��ȸ�� true�� ȸ��
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
		
		if(jtf2.getText().trim().equals("")) {
			if(jtf1.getText().trim().equals("")) {
				jtf1.setText("");
				jtf1.requestFocus();
				flagId=true;
				JOptionPane.showMessageDialog(pulv, "���̵� �Է��� �ּ���.");
			}else if(pass.trim().equals("")) {
				jpf.setText("");//������ �Է��ϰ� ����ģ ��� JPasswordField�� ���� �ʱ�ȭ
				jpf.requestFocus();
				flagPass=true;
				JOptionPane.showMessageDialog(pulv, "��й�ȣ�� �Է��� �ּ���.");
			}//end if
			if(!flagId&&!flagPass) {
				flagmember=true;//ȸ��
			}//end if
		}else if(jtf1.getText().trim().equals("") && pass.trim().equals("")){
			if(jtf2.getText().trim().equals("")) {
				jtf2.setText("");
				jtf2.requestFocus();
				flagCard=true;
				JOptionPane.showMessageDialog(pulv, "��ȸ�� ī���ȣ�� �Է����ּ���");
			}//end if
			if(!flagCard) {
				flagmember=false;//��ȸ��
			}//end if
//		}else {
//			System.out.println("�α����� �ּ���!");
		}//end else
		return flagmember;
	}//checkNull
	
	/**
	 * ���̵� �α��� ������ Ȯ��, �̵������� �������� Ȯ��
	 * @return 
	 */
	private String loginStatus() {//c����
		String flag="";
		String status;
		JTextField jtfid=pulv.getJtfId();
		JTextField jtfcard=pulv.getJtfCardNum();
		
		String id=jtfid.getText().trim();
		String cardNum=jtfcard.getText().trim();
		if(checkMember()) {//ȸ���̸�
			try {
				status = pul_dao.selectMemberIdStatus(id);
				flag=String.valueOf(status);
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}else{//��ȸ���̸�
			try {
				status = pul_dao.selectGuestIdStatus(Integer.parseInt(cardNum));
				flag=String.valueOf(status);
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
		}//end else
		return flag;
	}//loginStatus
	
	/**
	 * ��ȸ�� �α���
	 * @param guestNum
	 * @return
	 */
	private boolean login(int guestNum) {
		boolean flag=false;
		try {
			PULoginDAO pul_dao=PULoginDAO.getInstance();
			InetAddress ip =InetAddress.getLocalHost();
			String pcIp=String.valueOf(ip).substring(InetAddress.getLocalHost().toString().indexOf("/")+1);

			if(pul_dao.selectGuestCheck(guestNum)) {
				PUGuestStateVO pugsvo=new PUGuestStateVO(guestNum, pcIp);
				pul_dao.updateGuestState(pugsvo);
				flag=true;
			}//end if
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pulv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		} catch (UnknownHostException uhe) {
			JOptionPane.showMessageDialog(pulv, "ip��ȸ ����");
			uhe.printStackTrace();
		} //end catch
		return flag;
	}//login guest
	
	/**
	 * ȸ�� �α���
	 * @param pucvo
	 * @return
	 */
	private boolean login(PUCertificationVO pucvo) {
		//String adminName="";
		boolean flag=false;
		try {
			PULoginDAO pul_dao=PULoginDAO.getInstance();

			InetAddress ip =InetAddress.getLocalHost();
			String pcIp=String.valueOf(ip).substring(InetAddress.getLocalHost().toString().indexOf("/")+1);
			//System.out.println(pcIp);
			//System.out.println(String.valueOf(pul_dao.selectMemberLogin(pucvo)).equals(""));
			if(pul_dao.selectMemberLogin(pucvo)!=-1) {
				
				flag=true;
				PUMemberStateVO pumsvo=new PUMemberStateVO(pucvo.getMemberId(),pcIp );
				//System.out.println("�������?");
				pul_dao.updateMemberState(pumsvo);
				//System.out.println("�������?2222");
//			}else {
//				JOptionPane.showMessageDialog(pulv, "��й�ȣ�� Ʋ�Ƚ��ϴ�.");
//				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pulv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		} catch (UnknownHostException uhe) {
			JOptionPane.showMessageDialog(pulv, "ip��ȸ ����");
			uhe.printStackTrace();
		} //end catch
		return flag;
	}//login member
	
	/**
	 * �������� �ҷ�����
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
