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

	// 아이디 널 체크
	public boolean checkId() {
		boolean flag = false;
		JTextField jtfId = pmlv.getJtfId();
		if(jtfId.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmlv, "아이디를 입력하세요.");
			jtfId.setText("");
			jtfId.requestFocus();
			flag = true;
		} // end if
		return flag;
	} // checkID
	
	// 패스워드 널 체크
	public boolean checkPass() {
		boolean flag = false;
		JPasswordField jpfPass = pmlv.getJpfPass();
		String pass = new String(jpfPass.getPassword());
		if(pass.trim().equals("")) {
			JOptionPane.showMessageDialog(pmlv, "패스워드를 입력하세요.");
			jpfPass.setText("");
			jpfPass.requestFocus();
			flag = true;
		} // end if
		return flag;
	} // checkPass

	@Override
	public void actionPerformed(ActionEvent ae) {
		if( !checkId() && !checkPass()) {//아이디와 비밀번호가 empty가 아니면
			JTextField jtf=pmlv.getJtfId();
			JPasswordField jpf=pmlv.getJpfPass();
			
			String id=jtf.getText().trim();
			String pass=new String(jpf.getPassword());	
			
			//입력한 아이디와 비밀번호를 가지고 		
			PMLoginVO pmlvo=new PMLoginVO(id, pass);
			String adminName=login(pmlvo);// DB로그인 인증을 수행한 결과를 받았음.
			if( adminName.equals("") ) {// 수행결과가 ""라면
				JOptionPane.showMessageDialog(pmlv, "아이디나 비밀번호를 확인하세요.");
				jtf.setText("");
				jpf.setText("");
				jtf.requestFocus();
			}else {
				PMMainView.adminId=id; //로그인이 성공 했다면 id를
				new PMMainView(adminName);
				//모든 객체에서 사용할 수 있도록 static 변수에 설정한다. 
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
			JOptionPane.showMessageDialog(pmlv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
		}//end catch
		return adminName;
	}//login	
	
	@Override
	public void windowClosing(WindowEvent we) {
		pmlv.dispose();
	} // windowClosing
	
} // class
