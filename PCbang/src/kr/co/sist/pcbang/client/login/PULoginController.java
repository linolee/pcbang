
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
			
			if(checkMember()) {//ȸ���̸�
				JTextField jtf=pulv.getJtfId();
				JPasswordField jpf=pulv.getJpfPass();
				
				String id=jtf.getText().trim();
				String pass=new String (jpf.getPassword());
				
//				if(loginStatus().equals("o")) {//�α��� ������ ��������
//					//�α���
//					PUCertificationVO pucvo=new PUCertificationVO(id, pass);
//					String memberName=login(pucvo);
//					
//					if(memberName.equals("")) {//������ ��� ""��� 
//						JOptionPane.showMessageDialog(pulv, "���̵� ��й�ȣ�� Ȯ���ϼ���.");
//						jtf.setText("");
//						jpf.setText("");
//						jtf.requestFocus();
//					}else {
//						PUMainView.userId=id;//�α����� �����ߴٸ� id�� ��簴ü���� ����� �� �ֵ��� static ����� ����Ѵ�.
//						PUMainView.cardNum="";
//						new PUMainView();//new PUMainView(memberName);
//						pulv.dispose();
//					}//end else
//				}else if(loginStatus().equals("c")) {
				
				if(pul_dao.selectPcId(id)) {//PC���̺� �ȸ�Ǵ� id�� ��ٸ� �α���
					//�α���
					PUCertificationVO pucvo=new PUCertificationVO(id, pass);
					String memberName=login(pucvo);
					
					if(memberName.equals("")) {//������ ��� ""��� 
						JOptionPane.showMessageDialog(pulv, "���̵� ��й�ȣ�� Ȯ���ϼ���.");
						jtf.setText("");
						jpf.setText("");
						jtf.requestFocus();
					}else {
						PUMainView.userId=id;//�α����� �����ߴٸ� id�� ��簴ü���� ����� �� �ֵ��� static ����� ����Ѵ�.
						PUMainView.cardNum="";
						new PUMainView();//new PUMainView(memberName);
						pulv.dispose();
					}//end else
				}else {//�ִٸ� �α����� �ȵŴµ�,�ڸ��̵��̸�~
					if(loginStatus().equals("c")) {
						//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û��
						System.out.println("�ڸ������� �����Դϴ�->�α���");
						
						PUCertificationVO pucvo=new PUCertificationVO(id, pass);
						String memberName=login(pucvo);
						
						if(memberName.equals("")) {//������ ��� ""��� 
							JOptionPane.showMessageDialog(pulv, "���̵� ��й�ȣ�� Ȯ���ϼ���.");
							jtf.setText("");
							jpf.setText("");
							jtf.requestFocus();
						}else {
							new PUMainView();
							PUMainView.userId=id;
							PUMainView.cardNum="";
							pulv.dispose();
						}//end else
//					}else if(loginStatus().equals("x")) {
					}else{
						//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û����
						JOptionPane.showMessageDialog(pulv, "�ڸ������ ���� ��û�� �ּ���!");
					}//end else
				}//end else
				
			}else{//��ȸ���̸�
				JTextField jtf2=pulv.getJtfCardNum();
				int card=Integer.parseInt(jtf2.getText().trim());
				
//				if(loginStatus().equals("o")) {//�α��� ������ ��������
//					//�α���
//					if(login(card)) {
//						PUMainView.userId="";//�α����� �����ߴٸ� id�� ��簴ü���� ����� �� �ֵ��� static ����� ����Ѵ�.
//						PUMainView.cardNum=String.valueOf(card);
//						new PUMainView();
//						pulv.dispose();
//					}else {
//						JOptionPane.showMessageDialog(pulv, "ī���ȣ�� Ȯ�����ּ���");
//					}
//				}else if(loginStatus().equals("c")) {
				if(pul_dao.selectPcCard(card)) {//PC���̺� �ȸ�Ǵ� id�� ��ٸ� �α���
					//�α���
					if(login(card)) {
						PUMainView.userId="";//�α����� �����ߴٸ� id�� ��簴ü���� ����� �� �ֵ��� static ����� ����Ѵ�.
						PUMainView.cardNum=String.valueOf(card);
						new PUMainView();
						pulv.dispose();
					}else {
						JOptionPane.showMessageDialog(pulv, "ī���ȣ�� Ȯ�����ּ���");
					}
				}else {//�ִٸ� �α����� �ȵŴµ�,�ڸ��̵��̸�~
					if(loginStatus().equals("c")) {
						//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û��
						if(login(card)) {
							new PUMainView();
							PUMainView.userId="";//�α����� �����ߴٸ� id�� ��簴ü���� ����� �� �ֵ��� static ����� ����Ѵ�.
							PUMainView.cardNum=String.valueOf(card);
							pulv.dispose();
						}else {
							JOptionPane.showMessageDialog(pulv, "ī���ȣ�� Ȯ�����ּ���");
						}//end else
//					}else if(loginStatus().equals("x")) {
					}else{
						//�̹� �α��� �Ǿ��ִµ�=>�ڸ����� ��û����
						JOptionPane.showMessageDialog(pulv, "�ڸ������ ���� ��û�� �ּ���!");
					}//end else
				}//end else
			}//end else
			
		}//end if
		if(ae.getSource()==pulv.getJbtMembership()) {
			JOptionPane.showMessageDialog(pulv, "ȸ����!");
		}//end if
		if(ae.getSource()==pulv.getJbtFind()) {
			JOptionPane.showMessageDialog(pulv, "���̵�/��й�ȣ ã��!");
		}//end if
	}//actionPerformed
	
	@Override
	public void windowClosing(WindowEvent we) {
		int flag=JOptionPane.showConfirmDialog(pulv, "�α��� ���� ����ø� ����� �Ұ��� �մϴ�.\n�׷��� ����Ͻðڽ�ϱ�?");
		if(flag==0) {
			pulv.dispose();
		}//end if
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
		//System.out.println(jtf1.getText().trim()+"/"+pass.trim()+"/"+jtf2.getText().trim());
		
		if(jtf2.getText().trim().equals("")) {
			if(jtf1.getText().trim().equals("")) {
				jtf1.setText("");
				jtf1.requestFocus();
				flagId=true;
				System.out.println("���̵� �Է��� �ּ���");
				JOptionPane.showMessageDialog(pulv, "���̵� �Է��� �ּ���.");
			}else if(pass.trim().equals("")) {
				jpf.setText("");//���� �Է��ϰ� ����ģ ��� JPasswordField�� ��� �ʱ�ȭ
				jpf.requestFocus();
				flagPass=true;
				System.out.println("��й�ȣ�� �Է��� �ּ���");
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
				System.out.println("��ȸ�� ī���ȣ�� �Է����ּ���");
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
	private String loginStatus() {//o,c,x�� ���;��Ѵ�
		String flag="";
		char status;
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
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			PULoginDAO pul_dao=PULoginDAO.getInstance();
			PUGuestStateVO pugsvo=new PUGuestStateVO(guestNum, String.valueOf(ip));
			
			pul_dao.updateGuestState(pugsvo);
			flag=true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pulv, "DB���� ����� �߻��߽�ϴ�.");
			e.printStackTrace();
		} catch (UnknownHostException uhe) {
			JOptionPane.showMessageDialog(pulv, "ip�ȸ ����");
			uhe.printStackTrace();
		} //end catch
		return flag;
	}//login guest
	
	/**
	 * ȸ�� �α���
	 * @param pucvo
	 * @return
	 */
	private String login(PUCertificationVO pucvo) {
		String adminName="";
		
		PULoginDAO pul_dao=PULoginDAO.getInstance();
		try {
			adminName=String.valueOf(pul_dao.selectMemberLogin(pucvo));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pulv, "DB���� ����� �߻��߽�ϴ�.");
			e.printStackTrace();
		}//end catch
		
		return adminName;
	}//login member
	
	/**
	 * ������� �ҷ����
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
