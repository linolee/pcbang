package kr.co.sist.pcbang.manager.magageraddaccount;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.pcbang.manager.magageraccount.PMManagerAccountController;

public class PMManagerAddAccountController extends WindowAdapter implements ActionListener {

	private PMManagerAddAccountView pmmaav;
	private PMManagerAccountController pmmac;
	private PMManagerAddAccountDAO pmmaa_dao;
	
	private boolean flagCheck = false;

	public PMManagerAddAccountController(PMManagerAddAccountView pmmaav, PMManagerAccountController pmmac) {
		this.pmmaav = pmmaav;
		this.pmmac = pmmac;
	}

	/* ���� �߰� �޼ҵ� */
	private void addAccount() throws SQLException {

		JTextField jtfId = pmmaav.getJtfAddId();
		JPasswordField jpfPass = pmmaav.getJpfAddPass();
		JTextField jtfName = pmmaav.getJtfAddName();

		if (jtfId.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmmaav, "���̵� �Է��ϼ���.");
			jtfId.setText("");
			jtfId.requestFocus();
			return;
		} // end if

		if (jpfPass.getPassword().equals("")) {
			JOptionPane.showMessageDialog(pmmaav, "�н����带 �Է��ϼ���.");
			jpfPass.setText("");
			jpfPass.requestFocus();
			return;
		} // end if

		if (jtfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmmaav, "�̸��� �Է��ϼ���.");
			jtfName.setText("");
			jtfName.requestFocus();
			return;
		} // end if

	      String pass = "";
	      pass = new String(jpfPass.getPassword());
	      
	      //�Է��� ������ ��ü ����
	      PMManagerAddAccountVO pmmaaVO = new PMManagerAddAccountVO(jtfId.getText().trim(),pass.trim(), jtfName.getText().trim());		
		
		
		//���� �߰� daoȣ��
		PMManagerAddAccountDAO.getInstance().insertAccount(pmmaaVO);

		// ����Ʈ ���� -> �߰� �� ������ ��
		pmmac.setAccount();

		// ���� ������ �Է��� ���ϰ� �ϱ� ���ؼ� �Է� �� �ʱ�ȭ
		jtfId.setText("");
		jpfPass.setText("");
		jtfName.setText("");

		JOptionPane.showMessageDialog(pmmaav, "������ ������ �߰��Ǿ����ϴ�.");

		jtfId.requestFocus();

	}// addAccount
	
	private void checkAccountId() {
		JTextField jtfId = pmmaav.getJtfAddId();
		JPasswordField jpfPass = pmmaav.getJpfAddPass();
		JTextField jtfName = pmmaav.getJtfAddName();
		
		String id = jtfId.getText().trim();
		pmmaa_dao = PMManagerAddAccountDAO.getInstance();

		try {
			if (pmmaa_dao.selectAccount(id)) {
				JOptionPane.showMessageDialog(pmmaav, "�̹� ����� �Դϴ�.");
				jtfId.setText("");
				jpfPass.setText("");
				jtfName.setText("");
				jtfId.requestFocus();				
			} else {
				addAccount();
				flagCheck = true;
			} // end else
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // checkAccountId

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmmaav.getJbtCancle()) {
			pmmaav.dispose();
		} // end if

		if (ae.getSource() == pmmaav.getJbtAdd()) {
			switch (JOptionPane.showConfirmDialog(pmmaav, "���� �߰��Ͻðڽ��ϱ�?")) {
			case JOptionPane.OK_OPTION:
				System.out.println("ù��° : "+pmmaav.getJtfAddId().getText());
				checkAccountId();
			break;
			} // end switch
		} // end if
	}// actionPerformed

	@Override
	public void windowClosing(WindowEvent we) {
		pmmaav.dispose();
	}

}// class

