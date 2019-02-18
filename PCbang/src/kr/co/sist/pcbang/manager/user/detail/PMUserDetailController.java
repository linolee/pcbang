package kr.co.sist.pcbang.manager.user.detail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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
		
			if(u_dao.updateMemberData(upvo)) {
				JOptionPane.showMessageDialog(udv, "ȸ�������� ����Ǿ����ϴ�");
				udv.dispose();
				uc.selectUser();
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(udv, "���̸���");
			e.printStackTrace();
		} catch(NullPointerException ne) {
			JOptionPane.showMessageDialog(udv, "��ĭ�� �Է��� �� �����ϴ�");
		} catch(NumberFormatException nfe) {
		JOptionPane.showMessageDialog(udv, "��ĭ�� �Է��� �� �����ϴ�");
	} 
		
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
