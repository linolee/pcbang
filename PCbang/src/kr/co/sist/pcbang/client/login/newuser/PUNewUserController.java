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
				JOptionPane.showMessageDialog(punuv, "�̹� ����� �Դϴ�.");

			} else {
				JOptionPane.showMessageDialog(punuv, "��� �����մϴ�.");
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
			JOptionPane.showMessageDialog(punuv, "���̵� �Է��� �ּ���.");
		} else {
			checkMemberId();
		}
		return flag;
	} // checkNull

	/* �����߰��ϴ� method */
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
		while (enums.hasMoreElements()) { // hasMoreElements() Enum�� �� ���� ��ü�� �ִ��� üũ�Ѵ�. ������ false ��ȯ
			AbstractButton ab = enums.nextElement(); // ���׸����� AbstractButton �̴ϱ� �翬�� AbstractButton���� �޾ƾ���
			JRadioButton jb = (JRadioButton) ab; // ����ȯ. ���� ���ٰ� ������ ���ļ� �ٷ� ����ȯ �ؼ� �޾Ƶ� �ȴ�.

			if (jb.isSelected()) // �޾Ƴ� ������ư�� üũ ���¸� Ȯ���Ѵ�. üũ�Ǿ������ true ��ȯ.
				gibonCode = jb.getText().trim(); // getText() �޼ҵ�� ���ڿ� �޾Ƴ���.
		}
		if (gibonCode.equals("����")) {
			gibonCode = "M";
		} else {
			gibonCode = "F";
		} // end else

		String birth = String.valueOf(jcbBirth1.getSelectedItem()) + String.valueOf(jcbBirth2.getSelectedItem())
				+ String.valueOf(jcbBirth3.getSelectedItem());
		String phone = String.valueOf(jtftel1.getText()+"-"+ jtftel2.getText()+"-"+jtftel3.getText());

		// �Է��� ������ ��ü ����
		PUNewUserVO punuvo = new PUNewUserVO(jtfId.getText().trim(), pass.trim(), jtfName.getText().trim(),
				birth, gibonCode, phone, jtfemail.getText().trim(), jtfdetailAdd.getText().trim());

		// ���� �߰� daoȣ��
		PUNewUserDAO.getInstance().insertMember(punuvo);

		JOptionPane.showMessageDialog(punuv, "������ �Ϸ� �Ǿ����ϴ�.");
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
				JOptionPane.showMessageDialog(punuv, "�̸��� �ʼ� �Է��Դϴ�.");
				punuv.getJtfUserName().requestFocus();
				return;
			} // end if
			if (punuv.getJtfId().getText().equals("")) {
				JOptionPane.showMessageDialog(punuv, "���̵�� �ʼ� �Է��Դϴ�.");
				punuv.getJtfId().requestFocus();
				return;
			} // end if
			if (pass.trim().equals("")) {
				JOptionPane.showMessageDialog(punuv, "��й�ȣ�� �ʼ� �Է��Դϴ�.");
				punuv.getJpfPass().requestFocus();
				return;
			} // end if
			if (pass1.trim().equals("")) {
				JOptionPane.showMessageDialog(punuv, "��й�ȣ Ȯ���� �ʼ� �Է��Դϴ�.");
				punuv.getJpfPassCheck().requestFocus();
				return;
			} // end if

			if (!(pass.trim().equals(pass1.trim()))) {
				JOptionPane.showMessageDialog(punuv, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				punuv.getJpfPass().requestFocus();
				punuv.getJpfPassCheck().setText("");
				return;
			} // end if
			if (punuv.getBg().isSelected(null)) {
				JOptionPane.showMessageDialog(punuv, "������ �����ϼ���.");
				return;
			} // end if
			if (punuv.getJtfPhone1().getText().equals("")) {
				JOptionPane.showMessageDialog(punuv, "�޴��� ��ȣ�� �ʼ� �Է��Դϴ�.");
				punuv.getJtfPhone1().requestFocus();
				return;
			} // end if
			if (punuv.getJtfPhone2().getText().equals("")) {
				JOptionPane.showMessageDialog(punuv, "�޴��� ��ȣ�� �ʼ� �Է��Դϴ�.");
				punuv.getJtfPhone2().requestFocus();
				return;
			} // end if
			if (punuv.getJtfPhone3().getText().equals("")) {
				JOptionPane.showMessageDialog(punuv, "�޴��� ��ȣ�� �ʼ� �Է��Դϴ�.");
				punuv.getJtfPhone3().requestFocus();
				return;
			} // end if

			if (flagCheck) {
				// insert ����
				try {
					addMember();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// dialog�� �޽��� ���� -> �ߺ�Ȯ�� �ϼ���
				JOptionPane.showMessageDialog(punuv, "�ߺ�Ȯ���� �ʼ��Դϴ�.");

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
		// �Էµ� ���ڰ��� ������ false
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
