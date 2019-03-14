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
	
	public PMUserDetailController(PMUserDetailView udv) {
		this.udv = udv;
		u_dao = PMUserDAO.getInstance();		
		uc=PMUserController.getInstance();
	}
	
	public void updateUser() {
		try {
		PMUpdateVO upvo = null;	
		upvo = new PMUpdateVO
				(udv.getJtfEmail().getText(), udv.getJtfTel().getText(), udv.getJtfBirth().getText(), udv.getJtfId().getText(), 
						Integer.parseInt(udv.getJtfLeftTime().getText()));
		
			String birth = udv.getJtfBirth().getText();
			boolean chkBirth = Pattern.matches("^[0-9]*$", birth);
			
			String phone1 = udv.getJtfTel().getText().substring(0, 2);
			String phone2 = udv.getJtfTel().getText().substring(4, 7);
			String phone3 = udv.getJtfTel().getText().substring(9, udv.getJtfTel().getText().length());
			boolean chkPhone1 = Pattern.matches("^[0-9]*$", phone1);
			boolean chkPhone2 = Pattern.matches("^[0-9]*$", phone2);
			boolean chkPhone3 = Pattern.matches("^[0-9]*$", phone3);

			if(!udv.getJtfEmail().getText().contains("@")||!udv.getJtfEmail().getText().contains(".")) {
				JOptionPane.showMessageDialog(udv, "�߸��� �̸��� �����Դϴ�", "Message", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(chkPhone(udv.getJtfTel().getText())|| !chkPhone1 || !chkPhone2 || !chkPhone3 ) {
				JOptionPane.showMessageDialog(udv, "�߸��� ��ȭ��ȣ �����Դϴ�", "Message", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!chkBirth || !(birth.length()==8) ) {
				JOptionPane.showMessageDialog(udv, "�߸��� ������� �����Դϴ�", "Message", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(Integer.parseInt(udv.getJtfLeftTime().getText())>=10000) {
				JOptionPane.showMessageDialog(udv, "�ܿ��ð��� 0~9999�Դϴ�", "Message", JOptionPane.ERROR_MESSAGE);
				return;
				
			}
		
			if(u_dao.updateMemberData(upvo)) {
				JOptionPane.showMessageDialog(udv, "ȸ�������� ����Ǿ����ϴ�", "Message", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(udv, "error");
		}
		
	}
	
	
		public boolean chkPhone(String phone) {
		boolean flag=false;
		if(!(phone.charAt(3)=='-') || !(phone.charAt(8)=='-') || !(phone.length()==13) ) {
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
