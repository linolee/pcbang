package kr.co.sist.pcbang.client.login.newuser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import kr.co.sist.pcbang.client.login.newuser.seq.PUSeqView;

public class PUNewUserController extends WindowAdapter implements ActionListener {

	private PUNewUserView punuv;
	
	public PUNewUserController(PUNewUserView punuv) {
		this.punuv = punuv;
	} // PUNewUserController
	
	
	@Override
	public void actionPerformed(ActionEvent we) {
		
		if(we.getSource()==punuv.getJbtAddrSearch()) {
			new PUSeqView();
		}
		
		if(we.getSource()==punuv.getJbtCancel()) {
			punuv.dispose();
		} // end if
		
	} // actionPerformed

} // class
