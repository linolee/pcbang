package kr.co.sist.pcbang.client.main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.charge.PUChargeView;
import kr.co.sist.pcbang.client.ordering.PUOrderingView;

public class PUMainController extends WindowAdapter implements ActionListener,Runnable{

	private PUMainView pumv;
	private PUMainDAO pum_dao;
	private PUManager pu_manager;
	
	private Thread threadOrdering;
	
	public PUMainController(PUMainView pumv) {
		this.pumv=pumv;
		pu_manager = new PUManager(this);
		pum_dao=PUMainDAO.getInstance();
		try {
			String id=pumv.id;
			String card=pumv.card;
			if(id==null) {
				id="";
			}//end if
			if(card==null) {
				card="";
			}//end if
			searchUseInfo(id,card);//����� ���� ��ȸ
			//System.out.println("�α��� �Ǿ����ϴ�.");
		} catch (UnknownHostException e) {
			System.out.println("�������ּҸ� ����Ҽ� ����");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("sql����");
			e.printStackTrace();
		}
		if(threadOrdering==null) {//�̰� ������ ��� ���������.
			threadOrdering =new Thread(this);
			threadOrdering.start();
		}//end if
	}//PUMainController

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==pumv.getJbtOrder()) {//��ǰ�ֹ�
			new PUOrderingView();
		}//end if
		if(ae.getSource()==pumv.getJbtCharge()) {//�ð�����
			//JOptionPane.showMessageDialog(pumv, "�ð�����");
			JLabel jlSeat=pumv.getJlSeatNum();
			new PUChargeView(Integer.parseInt(jlSeat.getText()));
		}//end if
		if(ae.getSource()==pumv.getJbtChange()) {//�¼�����
			int flag=JOptionPane.showConfirmDialog(pumv, "�ڸ������� �Ͻðڽ��ϱ�?");
			if(flag==0) {
				JLabel jlSeat=pumv.getJlSeatNum();
				changeSeat(Integer.parseInt(jlSeat.getText()));
			}//end if
		}//end if
		if(ae.getSource()==pumv.getJbtMsg()) {//�޼���
			JOptionPane.showMessageDialog(pumv, "�޼���");
			
		}//end if
		if(ae.getSource()==pumv.getJbtExit()) {//�������
			//��ȸ���϶����� �ð��� ������� �ʽ��ϴ�...
			int flag=JOptionPane.showConfirmDialog(pumv, "���� �α׾ƿ� �Ͻðڽ��ϱ�?");
			if(flag==0) {
				logout();
				pumv.dispose();
				System.exit(0);//��� ��ü ����
			}//end if
		}//end if
	}//actionPerformed
	
	@Override
	public void run() {
		for(int i=0; i<10080; i++) {//7��
			try {
				//���ð��� �����ͼ� +1
				JLabel jlUseTime=pumv.getJlUseTime();//00:00
				jlUseTime.setText(hourTime(String.valueOf(i)));
				
				//���� �ð��� �����ͼ� -1
				JLabel jlRestTime=pumv.getJlRestTime();
				String timeString=jlRestTime.getText();//05:05
				int restTime=minutesTime(timeString);//520
				if(0<i) {
					jlRestTime.setText(hourTime(String.valueOf(restTime-1)));
				}//end if
				//�����ð��� ������ �������
				if(timeString.equals("00:00")) {
					int flag=JOptionPane.showConfirmDialog(pumv, "������ �ð��� �����ϴ�.\n�����Ͻðڽ��ϱ�?");
					if(flag==0) {
						JOptionPane.showMessageDialog(pumv, "����â");
						JLabel jlSeat=pumv.getJlSeatNum();
						new PUChargeView(Integer.parseInt(jlSeat.getText()));
					}else if(flag==1){
						JOptionPane.showMessageDialog(pumv, "����� ����˴ϴ�.");
						pumv.dispose();
					}
				}//end if
				
				Thread.sleep(1000*60);//60��
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch
		}//end for
	}//run

	@Override
	public void windowClosing(WindowEvent we) {
		//��ȸ���϶����� �ð��� ������� �ʽ��ϴ�...
		int flag=JOptionPane.showConfirmDialog(pumv, "���� �α׾ƿ� �Ͻðڽ��ϱ�?");
		if(flag==0) {
			logout();
			pumv.dispose();
			System.exit(0);//��� ��ü ����
		}//end if
	}//windowClosing
	
//	/**
//	 * ���ð�+1/�����ð�-1
//	 * @return 
//	 */
//	private void timeSet() {
//		JLabel jlUseTime=pumv.getJlUseTime();//00:00
//		int i=0;
//		jlUseTime.setText(hourTime(String.valueOf(i++)));
//		//JLabel jlRestTime=pumv.getJlRestTime();
//		//String restTime=jlRestTime.getText();//05:05
//		
//		//jlRestTime.setText(hourTime(restTime));
//	}//timeSet
	
	/**
	 * ����� ���� ��ȸ->�����ð��� ������ �ð�����â
	 * @param id
	 * @param cardNum
	 * @throws SQLException 
	 * @throws UnknownHostException 
	 */
	private void searchUseInfo(String id,String cardNum) throws SQLException, UnknownHostException {
		JLabel jlSeat=pumv.getJlSeatNum();
		JLabel jlRestTime=pumv.getJlRestTime();
		JLabel jlName=pumv.getJlName();
		
		int seatnum=pum_dao.selectSeatNum();
		jlSeat.setText(String.valueOf(seatnum));//�ڸ� ���
		//System.out.println("�α�����");
		if(!id.equals("")) {//���̵� �����ٸ� ȸ��
			//System.out.println("ȸ��");
			PUMainInfoVO puminfovo=pum_dao.selectInfo(id,cardNum);
			String name=puminfovo.getName();
			jlName.setText(name);
			String restTime=puminfovo.getRestTime();
			jlRestTime.setText(hourTime(restTime));
			if(Integer.parseInt(restTime)==0) {//���� �ð��� 0�̶�� ����â
				//new PUChargeView();//���� �ð��� ���Ҵµ� �����ϸ� �ݱⰡ�� ������ �ð��� ������ ����â
				JOptionPane.showMessageDialog(pumv, "�����ϼ���");
			}//end if
		}else if(!cardNum.equals("")) {//ī���ȣ�� �����ٸ� ��ȸ��
			//System.out.println("��ȸ��");
			jlName.setText("guest"+seatnum);
			String time="0";
			jlRestTime.setText(hourTime(time));
			if(Integer.parseInt(time)==0) {//���� �ð��� 0�̶�� ����â
				new PUChargeView(Integer.parseInt(jlSeat.getText()));
			}//end if
		}//end else
	}//searchUseInfo
	
	/**
	 * �¼���ȣ�� �޾� �����û->��α��� ������ ���·� ����
	 * @param seatNum
	 */
	private void changeSeat(int seatNum) {
		try {
			pum_dao.updateSeat(seatNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//changeSeat
	
	/**
	 * �α׾ƿ�->PC���� ����(PC-status),�����ð� ����(ȸ��),�޼��� �ʱ�ȭ(�޼���),�α� ����(�α�)
	 * @param id
	 * @param cardNum
	 * @param seatNum
	 */
	private void logout() {
		//���� �α�����(String memberId,useDate/int useTime,chargePrice)
		String id=pumv.id;
		String card=pumv.card;
		String useDate="20190219";
		JLabel jlUseTime=pumv.getJlRestTime();
		String useTimestr=jlUseTime.getText();//00:00
		int uTime=minutesTime(useTimestr);//520
		
		JLabel jlSeatNum=pumv.getJlSeatNum();
		String seatNum=jlSeatNum.getText();
		String chargePrice="";
		JLabel jlRestTime=pumv.getJlRestTime();
		String restTimestr=jlRestTime.getText();
		int restTime=minutesTime(restTimestr);
		
		//PUMainUserLogVO pumLogvo=new PUMainUserLogVO(id, useDate, uTime, Integer.parseInt(chargePrice));
		
//		pum_dao.updatePC(id, card);
//		pum_dao.updateLog(pumLogvo);
//		pum_dao.updateMsg(Integer.parseInt(seatNum));
//		
		
		//�״��� pc���� ����
		
		//�״��� ȸ���� �����ð� ����
		
		//�״��� �޼��� �ʱ�ȭ
		
	}//logout
	
	/**
	 * ������ ����Ǿ��ִ� �ð��� �ð����� �ٲپ� �ش�.
	 * @param time
	 * @return
	 */
	private String hourTime(String time) {//520
		int hour=0;
		int minute=0;
		DecimalFormat df=new DecimalFormat("00");
		
		//System.out.println("�ѽð�: "+time);
		hour=(int) Math.floor(Integer.parseInt(time)/60);//���� �ð���
		//System.out.println("��: "+hour);
		minute=Integer.parseInt(time)-(hour*60);//�������� �п� �Ҵ��Ͽ�
		//System.out.println("��: "+minute);
		//System.out.println(df.format(hour)+":"+df.format(minute+10));
		return df.format(hour)+":"+df.format(minute);//���ڿ��� ��ȯ
	}//hourTime
	
	/**
	 * �ð����� ����Ǿ��ִ� �ð��� �нð�����
	 * @param time
	 * @return
	 */
	private int minutesTime(String time) {//00:00
		String[] arrtime=time.split(":");
		int restTime=(Integer.parseInt(arrtime[0])*60)+Integer.parseInt(arrtime[1]);
		
		return restTime;
	}//minutesTime
	
	/**
	 * �ð��� 0�̸� ����â �θ��� ���� �������� ������ ����
	 * @param time
	 */
	private void callcharge(int time) {
		pumv.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		if(time==0) {
			int flag=JOptionPane.showConfirmDialog(pumv, "������ �ð��� �����ϴ�.\n�����Ͻðڽ��ϱ�?");
			if(flag==JOptionPane.OK_OPTION) {
				JLabel jlSeat=pumv.getJlSeatNum();
				new PUChargeView(Integer.parseInt(jlSeat.getText()));
			}else if(flag==JOptionPane.NO_OPTION){
				return;
				//JOptionPane.showMessageDialog(pumv, "����� ����˴ϴ�.");
				//pumv.dispose();
			}
		}//end if
	}

	public PUManager getPu_manager() {
		return pu_manager;
	}

}//class