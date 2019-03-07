package kr.co.sist.pcbang.client.charge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.main.PUMainController;

public class PUChargeController extends WindowAdapter implements ActionListener {
	private PUChargeView pucv;
	private PUChargeDAO puc_dao;
	private PUMainController pumc;
	public static int chargeLog=0;
	
	public PUChargeController(PUChargeView pucv, PUMainController pumc) {
		this.pucv=pucv;
		this.pumc=pumc;
		puc_dao=PUChargeDAO.getInstance();
	}//PUChargeController

	public  void addTime(int seatNum, int time, int price) {
		if(pucv.isMember()) {
			try {
				MemberPriceUpdateVO mpuvo=new MemberPriceUpdateVO(puc_dao.selectMemberId(seatNum));
				MemberMileageVO mmvo=new MemberMileageVO(price, puc_dao.selectMemberId(seatNum));
				puc_dao.memberUpdate(mpuvo, time, price);
				pumc.setRestTime(pumc.getRestTime()+time);
				this.chargeLog=this.chargeLog+price;
				System.out.println(chargeLog);
				
				if(puc_dao.updateMemberMileage(mmvo)) {
					JOptionPane.showMessageDialog(pucv, "시간이 충전되었습니다~ "+price*0.1+"마일리지가 적립되었습니다!");
				}else {
					JOptionPane.showMessageDialog(pucv, "마일리지 적립에서 오류가 발생하였습니다.");
				}//end else
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(pucv, "DB에서 문제발생!!");
				se.printStackTrace();
			}//end catch
		}//end if
		if(!pucv.isMember()) {
			try {
				GuestPriceUpdateVO gpuvo=new GuestPriceUpdateVO(puc_dao.selectGuestNum(seatNum));
				puc_dao.guestUpdate(gpuvo, time, price);
				pumc.setRestTime(pumc.getRestTime()+time);
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
					pucv.dispose();
				}//end if
		}//end for
	}//actionPerformed
	
	@Override
	public void windowClosing(WindowEvent we) {
		pucv.dispose();
	}//windowClosing
	
}//class

