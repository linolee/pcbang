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
					JOptionPane.showMessageDialog(pucv, "�ð��� �����Ǿ����ϴ�~ "+price*0.1+"���ϸ����� �����Ǿ����ϴ�!");
				}else {
					JOptionPane.showMessageDialog(pucv, "���ϸ��� �������� ������ �߻��Ͽ����ϴ�.");
				}//end else
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(pucv, "DB���� �����߻�!!");
				se.printStackTrace();
			}//end catch
		}//end if
		if(!pucv.isMember()) {
			try {
				GuestPriceUpdateVO gpuvo=new GuestPriceUpdateVO(puc_dao.selectGuestNum(seatNum));
				puc_dao.guestUpdate(gpuvo, time, price);
				pumc.setRestTime(pumc.getRestTime()+time);
				JOptionPane.showMessageDialog(pucv, "�ð��� �����Ǿ����ϴ�~");
			} catch (SQLException se) {
				JOptionPane.showMessageDialog(pucv, "DB���� �����߻�!!");
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

