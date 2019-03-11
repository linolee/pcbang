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

	/* 계정 추가 메소드 */
	private void addAccount() throws SQLException {

		JTextField jtfId = pmmaav.getJtfAddId();
		JPasswordField jpfPass = pmmaav.getJpfAddPass();
		JTextField jtfName = pmmaav.getJtfAddName();

		if (jtfId.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmmaav, "아이디를 입력하세요.");
			jtfId.setText("");
			jtfId.requestFocus();
			return;
		} // end if

		if (jpfPass.getPassword().equals("")) {
			JOptionPane.showMessageDialog(pmmaav, "패스워드를 입력하세요.");
			jpfPass.setText("");
			jpfPass.requestFocus();
			return;
		} // end if

		if (jtfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmmaav, "이름을 입력하세요.");
			jtfName.setText("");
			jtfName.requestFocus();
			return;
		} // end if

	      String pass = "";
	      pass = new String(jpfPass.getPassword());
	      
	      //입력한 값으로 객체 생성
	      PMManagerAddAccountVO pmmaaVO = new PMManagerAddAccountVO(jtfId.getText().trim(),pass.trim(), jtfName.getText().trim());		
		
		
		//계정 추가 dao호출
		PMManagerAddAccountDAO.getInstance().insertAccount(pmmaaVO);

		// 리스트 갱신 -> 추가 후 진행할 것
		pmmac.setAccount();

		// 다음 계정의 입력을 편하게 하기 위해서 입력 폼 초기화
		jtfId.setText("");
		jpfPass.setText("");
		jtfName.setText("");

		JOptionPane.showMessageDialog(pmmaav, "관리자 계정이 추가되었습니다.");

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
				JOptionPane.showMessageDialog(pmmaav, "이미 사용중 입니다.");
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
			switch (JOptionPane.showConfirmDialog(pmmaav, "정말 추가하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				System.out.println("첫번째 : "+pmmaav.getJtfAddId().getText());
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

