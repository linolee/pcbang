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

			if(!udv.getJtfEmail().getText().contains("@")) {
				JOptionPane.showMessageDialog(udv, "잘못된 이메일 형식입니다");
				return;
			}
			if(chkPhone(udv.getJtfTel().getText()) ) {
				JOptionPane.showMessageDialog(udv, "잘못된 전화번호 형식입니다");
				return;
			}
			if(!chkBirth || !(birth.length()==8) ) {
				JOptionPane.showMessageDialog(udv, "잘못된 생년월일 형식입니다");
				return;
			}
			if(Integer.parseInt(udv.getJtfLeftTime().getText())>=10000) {
				JOptionPane.showMessageDialog(udv, "잔여시간은 0~9999입니다");
				return;
				
			}
		
			if(u_dao.updateMemberData(upvo)) {
				JOptionPane.showMessageDialog(udv, "회원정보가 변경되었습니다");
				udv.dispose();
				uc.selectUser();
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(udv, "입력값을 확인해주세요");
		} catch(NullPointerException ne) {
			JOptionPane.showMessageDialog(udv, "빈칸을 입력할 수 없습니다");
		} 
		catch(NumberFormatException nfe) {
		JOptionPane.showMessageDialog(udv, "입력값을 확인해주세요");
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
