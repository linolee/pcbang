package kr.co.sist.pcbang.client.login.newuser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class PUPolicyController extends WindowAdapter implements ActionListener {

	private PUPolicyView pupv;

	public PUPolicyController(PUPolicyView pupv) {
		this.pupv = pupv;
	}//

	@Override
	public void actionPerformed(ActionEvent we) {

		if (we.getSource() == pupv.getJbtOk()) {
			if (pupv.getJcb1().isSelected() && pupv.getJcb2().isSelected() && pupv.getJcb3().isSelected()) {
				new PUNewUserView();
				pupv.dispose();
			} else {
				JOptionPane.showMessageDialog(pupv, "모든 약관에 동의하세요.");
			}
		} // end if

		if (we.getSource() == pupv.getJbtcancel()) {
			pupv.dispose();
		} // end if

	} // actionPerformed

	@Override
	public void windowClosing(WindowEvent e) {
		pupv.dispose();
	} // windowClosing

} // class
