package kr.co.sist.pcbang.client.charge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class PUChargeController extends WindowAdapter implements ActionListener {
	private PUChargeView pucv;
	private PUChargeDAO puc_dao;
	
	public PUChargeController(PUChargeView pucv) {
		this.pucv=pucv;
		puc_dao=PUChargeDAO.getInstance();
	}//PUChargeController

	public  void addTime(int seatNum, int time, int price) {
		if(pucv.isMember()) {
			try {
				MemberPriceUpdateVO mpuvo=new MemberPriceUpdateVO(puc_dao.selectMemberId(seatNum));
				puc_dao.memberUpdate(mpuvo, time, price);
				JOptionPane.showMessageDialog(pucv, "시간이 충전되었습니다~");
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(pucv, "DB에서 문제발생!!");
				se.printStackTrace();
			}//end catch
		}//end if
		if(!pucv.isMember()) {
			try {
				GuestPriceUpdateVO gpuvo=new GuestPriceUpdateVO(puc_dao.selectGuestNum(seatNum));
				puc_dao.guestUpdate(gpuvo, time, price);
				JOptionPane.showMessageDialog(pucv, "시간이 충전되었습니다~");
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(pucv, "DB에서 문제발생!!");
				se.printStackTrace();
			}//end catch
		}//end if
		
	}//end addTime
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		for(int i=0; i<pucv.getJbtPrice().length; i++) {
				if(ae.getSource()==pucv.getJbtPrice()[i]) {
					addTime(pucv.getSeatNum(), pucv.getTime()[i], pucv.getPrice()[i] );
				}//end if
		}//end for
	}//actionPerformed
	
	@Override
	public void windowClosing(WindowEvent we) {
		pucv.dispose();
	}//windowClosing
}//class

