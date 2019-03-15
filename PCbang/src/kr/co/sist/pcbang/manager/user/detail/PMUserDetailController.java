package kr.co.sist.pcbang.manager.user.detail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.manager.user.PMUserController;
import kr.co.sist.pcbang.manager.user.PMUserDAO;

public class PMUserDetailController extends WindowAdapter implements ActionListener{

	private PMUserDetailView udv;
	private PMUserDAO u_dao;
	private PMUserController uc;
	private PMUpdateVO upvo;
	
	public PMUserDetailController(PMUserDetailView udv) {
		this.udv = udv;
		u_dao = PMUserDAO.getInstance();		
		uc=PMUserController.getInstance();
		
		
		upvo = null;	
		upvo = new PMUpdateVO
				(udv.getJtfEmail().getText(), udv.getJtfTel().getText(), udv.getJtfBirth().getText(), udv.getJtfId().getText(), 
						Integer.parseInt(udv.getJtfLeftTime().getText()));
		
	}
	
	public void updateUser() {
		try {

			String udvEmail=udv.getJtfEmail().getText().trim();
			String udvTel=udv.getJtfTel().getText().trim();
			String udvBirth=udv.getJtfBirth().getText().trim();
			int udvLeftTime=Integer.parseInt(udv.getJtfLeftTime().getText().trim());
			
			if(!chkEmail(udvEmail)) {
				JOptionPane.showMessageDialog(udv, "�߸��� �̸��� �����Դϴ�", "Message", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!chkPhone(udvTel)) {
				JOptionPane.showMessageDialog(udv, "�߸��� ��ȭ��ȣ �����Դϴ�", "Message", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!chkBirth(udvBirth) ) {
				JOptionPane.showMessageDialog(udv, "�߸��� ������� �����Դϴ�", "Message", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!chkLeftTime(udvLeftTime)) {
				JOptionPane.showMessageDialog(udv, "�ܿ��ð��� ���������̰� 0~9999�Դϴ�", "Message", JOptionPane.ERROR_MESSAGE);
				return;
			}
		
			if(u_dao.updateMemberData(upvo)) {
				JOptionPane.showMessageDialog(udv, "ȸ�������� ����Ǿ����ϴ�", "Message", 1);
				udv.dispose();
				uc.selectUser();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(udv, "�Է°��� Ȯ�����ּ���", "Message", JOptionPane.ERROR_MESSAGE);
		} catch(NullPointerException ne) {
			JOptionPane.showMessageDialog(udv, "��ĭ�� �Է��� �� �����ϴ�", "Message", JOptionPane.ERROR_MESSAGE);
		} catch(NumberFormatException nfe) {
		JOptionPane.showMessageDialog(udv, "�߸��� �ܿ��ð� �����Դϴ�", "Message", JOptionPane.ERROR_MESSAGE);
		
		} catch(Exception e) {
			JOptionPane.showMessageDialog(udv, "error", "Message", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
		public boolean chkPhone(String phone) {
			boolean flag=false;
			boolean flag2=Pattern.matches("^\\d{3}-\\d{3,4}-\\d{4}$", phone);
			
			if(flag2) {
				flag=true;
			}
			
			return flag;
	}
		
		public boolean chkBirth(String birth) {
			boolean flag=false;
			boolean chkBirth = Pattern.matches("^[0-9]*$", birth.trim());
			
			if(chkBirth && birth.length()==8) {
				flag=true;
			}
			
			return flag;
		}
		
		public boolean chkEmail(String email) {
			boolean flag=false;
			
			boolean chkEmail = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
			
			if(chkEmail) {
				flag=true;
			}
			
			return flag;
		}
		
		public boolean chkLeftTime(int leftTime) {
			boolean flag=false;
			boolean chkLeftTime=Integer.toString(leftTime).matches("^[0-9]*$");
			
			if(chkLeftTime&&leftTime>=0&&leftTime<=9999) {
				flag=true;
			}
			
			return flag;
		}
		
		
	@Override
	public void windowClosing(WindowEvent e) {
		udv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource() == udv.getJbtnUpdate()) {
			updateUser();
		}
		
		if(ae.getSource() == udv.getJbtnCancel()) {
			udv.dispose();
		}
	}
	
}
