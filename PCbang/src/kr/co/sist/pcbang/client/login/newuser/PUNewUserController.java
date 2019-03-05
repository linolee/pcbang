package kr.co.sist.pcbang.client.login.newuser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import kr.co.sist.pcbang.client.login.newuser.seq.PUSeqView;

public class PUNewUserController extends WindowAdapter implements ActionListener, KeyListener {

	private PUNewUserView punuv;

	private boolean flagCheck = false;

	public PUNewUserController(PUNewUserView punuv) {
		this.punuv = punuv;
	} // PUNewUserController

	private void checkMemberId() {
		// boolean flag = false;

		JTextField jtfId = punuv.getJtfId();
		String id = jtfId.getText().trim();
		PUNewUserDAO punu_dao = PUNewUserDAO.getInstance();

		try {
			if (punu_dao.selectMemderId(id)) {
				JOptionPane.showMessageDialog(punuv, "이미 사용중 입니다.");

			} else {
				JOptionPane.showMessageDialog(punuv, "사용 가능합니다.");
				flagCheck = true;
			} // end else
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		return flag;
	} // checkMemberId

	private boolean checkNull() {
		boolean flag = false;

		JTextField jtfId = punuv.getJtfId();

		if (jtfId.getText().trim().equals("")) {
			jtfId.setText("");
			jtfId.requestFocus();
			flag = true;
			JOptionPane.showMessageDialog(punuv, "아이디를 입력해 주세요.");
		} else {
			checkMemberId();
		}
		return flag;
	} // checkNull

	/* 계정추가하는 method */
	private void addMember() throws SQLException {
		JTextField jtfId = punuv.getJtfId();
		JPasswordField jtfPass = punuv.getJpfPass();
		JTextField jtfName = punuv.getJtfUserName();
		JComboBox<Integer> jcbBirth1 = punuv.getJcYear();
		JComboBox<Integer> jcbBirth2 = punuv.getJcMonth();
		JComboBox<Integer> jcbBirth3 = punuv.getJcDay();
		ButtonGroup bgGender = punuv.getBg();
		JTextField jtftel1 = punuv.getJtfPhone1();
		JTextField jtftel2 = punuv.getJtfPhone2();
		JTextField jtftel3 = punuv.getJtfPhone3();
		JTextField jtfemail = punuv.getJtfEmail();
		JTextField jtfdetailAdd = punuv.getJtfDetailAddr();

		String pass = "";
		pass = new String(jtfPass.getPassword());
		
		Enumeration<AbstractButton> enums = bgGender.getElements();
		String gibonCode = "";
		while (enums.hasMoreElements()) { // hasMoreElements() Enum에 더 꺼낼 개체가 있는지 체크한다. 없으며 false 반환
			AbstractButton ab = enums.nextElement(); // 제네릭스가 AbstractButton 이니까 당연히 AbstractButton으로 받아야함
			JRadioButton jb = (JRadioButton) ab; // 형변환. 물론 윗줄과 이줄을 합쳐서 바로 형변환 해서 받아도 된다.

			if (jb.isSelected()) // 받아낸 라디오버튼의 체크 상태를 확인한다. 체크되었을경우 true 반환.
				gibonCode = jb.getText().trim(); // getText() 메소드로 문자열 받아낸다.
		}
		if (gibonCode.equals("남자")) {
			gibonCode = "M";
		} else {
			gibonCode = "F";
		} // end else

		String birth = String.valueOf(jcbBirth1.getSelectedItem()) + String.valueOf(jcbBirth2.getSelectedItem())
				+ String.valueOf(jcbBirth3.getSelectedItem());
		String phone = String.valueOf(jtftel1.getText()+"-"+ jtftel2.getText()+"-"+jtftel3.getText());

		// 입력한 값으로 객체 생성
		PUNewUserVO punuvo = new PUNewUserVO(jtfId.getText().trim(), pass.trim(), jtfName.getText().trim(),
				birth, gibonCode, phone, jtfemail.getText().trim(), jtfdetailAdd.getText().trim());

		// 계정 추가 dao호출
		PUNewUserDAO.getInstance().insertMember(punuvo);

		JOptionPane.showMessageDialog(punuv, "가입이 완료 되었습니다.");
		punuv.dispose();
	}// addMember

	@Override
	public void actionPerformed(ActionEvent ae) {

		String pass = "";
		pass = new String(punuv.getJpfPass().getPassword());
		String pass1 = "";
		pass1 = new String(punuv.getJpfPassCheck().getPassword());

		if (ae.getSource() == punuv.getJbtIdCheck()) {
			checkNull();
		} // end if

		if (ae.getSource() == punuv.getJbtAddrSearch()) {
			new PUSeqView(punuv);
		} // end if

		if (ae.getSource() == punuv.getJbtCancel()) {
			punuv.dispose();
		} // end if

		if (ae.getSource() == punuv.getJbtOk()) {
			if (punuv.getJtfUserName().getText().equals("")) {
				JOptionPane.showMessageDialog(punuv, "이름은 필수 입력입니다.");
				punuv.getJtfUserName().requestFocus();
				return;
			} // end if
			if (punuv.getJtfId().getText().equals("")) {
				JOptionPane.showMessageDialog(punuv, "아이디는 필수 입력입니다.");
				punuv.getJtfId().requestFocus();
				return;
			} // end if
			if (pass.trim().equals("")) {
				JOptionPane.showMessageDialog(punuv, "비밀번호는 필수 입력입니다.");
				punuv.getJpfPass().requestFocus();
				return;
			} // end if
			if (pass1.trim().equals("")) {
				JOptionPane.showMessageDialog(punuv, "비밀번호 확인은 필수 입력입니다.");
				punuv.getJpfPassCheck().requestFocus();
				return;
			} // end if

			if (!(pass.trim().equals(pass1.trim()))) {
				JOptionPane.showMessageDialog(punuv, "비밀번호가 일치하지 않습니다.");
				punuv.getJpfPass().requestFocus();
				punuv.getJpfPassCheck().setText("");
				return;
			} // end if
			if (punuv.getBg().isSelected(null)) {
				JOptionPane.showMessageDialog(punuv, "성별을 선택하세요.");
				return;
			} // end if
			if (punuv.getJtfPhone1().getText().equals("")) {
				JOptionPane.showMessageDialog(punuv, "휴대폰 번호는 필수 입력입니다.");
				punuv.getJtfPhone1().requestFocus();
				return;
			} // end if
			if (punuv.getJtfPhone2().getText().equals("")) {
				JOptionPane.showMessageDialog(punuv, "휴대폰 번호는 필수 입력입니다.");
				punuv.getJtfPhone2().requestFocus();
				return;
			} // end if
			if (punuv.getJtfPhone3().getText().equals("")) {
				JOptionPane.showMessageDialog(punuv, "휴대폰 번호는 필수 입력입니다.");
				punuv.getJtfPhone3().requestFocus();
				return;
			} // end if

			if (flagCheck) {
				// insert 진행
				try {
					addMember();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// dialog로 메시지 띄우기 -> 중복확인 하세요
				JOptionPane.showMessageDialog(punuv, "중복확인은 필수입니다.");

			} // end else
				// end if

		} // end if

	} // actionPerformed

	@Override
	public void windowClosing(WindowEvent we) {
		punuv.dispose();
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		String tmp = String.valueOf(ke.getKeyChar());
		// 입력된 문자값이 있으면 false
		if (tmp != null && !tmp.equals("")) {
			flagCheck = false;
		} // end if
	} // keyPressed

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub

	}

} // class
