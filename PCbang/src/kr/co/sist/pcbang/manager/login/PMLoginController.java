package kr.co.sist.pcbang.manager.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.pcbang.manager.main.PMMainView;


public class PMLoginController extends WindowAdapter implements ActionListener {

	private PMLoginView pmlv;

	public PMLoginController(PMLoginView pmlv) {
		this.pmlv = pmlv;
	} // PMLonginController

	// ���̵� �� üũ
	public boolean checkId() {
		boolean flag = false;
		JTextField jtfId = pmlv.getJtfId();
		if(jtfId.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmlv, "���̵� �Է��ϼ���.");
			jtfId.setText("");
			jtfId.requestFocus();
			flag = true;
		} // end if
		return flag;
	} // checkID
	
	// �н����� �� üũ
	public boolean checkPass() {
		boolean flag = false;
		JPasswordField jpfPass = pmlv.getJpfPass();
		String pass = new String(jpfPass.getPassword());
		if(pass.trim().equals("")) {
			JOptionPane.showMessageDialog(pmlv, "�н����带 �Է��ϼ���.");
			jpfPass.setText("");
			jpfPass.requestFocus();
			flag = true;
		} // end if
		return flag;
	} // checkPass

	@Override
	public void actionPerformed(ActionEvent ae) {
		if( !checkId() && !checkPass()) {//���̵�� ��й�ȣ�� empty�� �ƴϸ�
			JTextField jtf=pmlv.getJtfId();
			JPasswordField jpf=pmlv.getJpfPass();
			
			String id=jtf.getText().trim();
			String pass=new String(jpf.getPassword());	
			
			//�Է��� ���̵�� ��й�ȣ�� ������ 		
			PMLoginVO pmlvo=new PMLoginVO(id, pass);
			String adminName=login(pmlvo);// DB�α��� ������ ������ ����� �޾���.
			if( adminName.equals("") ) {// �������� ""���
				JOptionPane.showMessageDialog(pmlv, "���̵� ��й�ȣ�� Ȯ���ϼ���.");
				jtf.setText("");
				jpf.setText("");
				jtf.requestFocus();
			}else {
				PMMainView.adminId=id; //�α����� ���� �ߴٸ� id��
				new PMMainView(adminName);
				//��� ��ü���� ����� �� �ֵ��� static ������ �����Ѵ�. 
				pmlv.dispose();
				
			}//end else
		}//end if		
	} // actionPerformed
	
	private String login(PMLoginVO pmlvo) {
		String adminName="";
		
		PMLoginDAO pml_dao=PMLoginDAO.getInstance();
		try {
			adminName=pml_dao.selectAcount(pmlvo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmlv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}//end catch
		return adminName;
	}//login	
	
	@Override
	public void windowClosing(WindowEvent we) {
		pmlv.dispose();
	} // windowClosing
	
} // class
