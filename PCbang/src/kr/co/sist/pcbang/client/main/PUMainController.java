package kr.co.sist.pcbang.client.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.ordering.PUOrderingView;

public class PUMainController extends WindowAdapter implements ActionListener,Runnable{

	private PUMainView pumv;
	private PUMainDAO pum_dao;
	
	private Thread threadOrdering;
	
	public PUMainController(PUMainView pumv) {
		this.pumv=pumv;
		pum_dao=PUMainDAO.getInstance();
		try {
			searchUseInfo("user","");//����� ���� ��ȸ
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
			JOptionPane.showMessageDialog(pumv, "�ֹ�");
			
		}//end if
		if(ae.getSource()==pumv.getJbtChange()) {//�¼�����
			JOptionPane.showMessageDialog(pumv, "�ֹ�");
			changeSeat(0);
		}//end if
		if(ae.getSource()==pumv.getJbtMsg()) {//�޼���
			
		}//end if
		if(ae.getSource()==pumv.getJbtExit()) {//�������
			logout("",0,0);
			pumv.dispose();
		}//end if
	}//actionPerformed
	
	@Override
	public void run() {
		for(int i=0; i<10000; i++) {
			try {
				JLabel jlUseTime=pumv.getJlUseTime();//00:00
				jlUseTime.setText(hourTime(String.valueOf(i)));
				Thread.sleep(1000*60);
				//useTimeSet();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch
		}
		
	}//run

	@Override
	public void windowClosing(WindowEvent we) {
		pumv.dispose();
	}//windowClosing
	
//	/**
//	 * ���ð�+1
//	 * @return 
//	 */
//	private void useTimeSet() {
//		JLabel jlUseTime=pumv.getJlUseTime();//00:00
//		int i=0;
//		jlUseTime.setText(hourTime(String.valueOf(i++)));
//		//JLabel jlRestTime=pumv.getJlRestTime();
//		//String restTime=jlRestTime.getText();//05:05
//		
//		//jlRestTime.setText(hourTime(restTime));
//	}//useTimeSet
	
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
		
		if(!id.equals("")) {//���̵� �����ٸ� ȸ��
			PUMainInfoVO puminfovo=pum_dao.selectInfo(id,cardNum);
			String name=puminfovo.getName();
			jlName.setText(name);
			String restTime=puminfovo.getRestTime();
			jlRestTime.setText(hourTime(restTime));
		}else if(!cardNum.equals("")) {//ī���ȣ�� �����ٸ� ��ȸ��
			
		}//end else
		
	}//searchUseInfo
	
	/**
	 * �¼���ȣ�� �޾� �����û->��α��� ������ ���·� ����
	 * @param seatNum
	 */
	private void changeSeat(int seatNum) {
		
	}//changeSeat
	
	/**
	 * �α׾ƿ�->PC���� ����,�����ð� ����,�޼��� �ʱ�ȭ
	 * @param id
	 * @param cardNum
	 * @param seatNum
	 */
	private void logout(String id, int cardNum, int seatNum) {
		
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

}//class