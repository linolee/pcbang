package kr.co.sist.pcbang.client.main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.charge.PUChargeView;
import kr.co.sist.pcbang.client.mileage.PUMileageStore;
import kr.co.sist.pcbang.client.ordering.PUOrderingView;

public class PUMainController extends WindowAdapter implements ActionListener,Runnable{

	private static PUMainView pumv;
	private PUMainDAO pum_dao;
	private PUManager pu_manager;
	
	private int chargePrice;
	private int RestTime;
	private Thread threadOrdering;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String id;
	private static PUMainController pumco;
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String card;
	
	
	public PUMainController(PUMainView pumv) {
		this.pumv=pumv;
		pu_manager = new PUManager(this);
		pum_dao=PUMainDAO.getInstance();
		
		
		try {
			int seatNum = pumv.seat;
			if(seatNum !=0) {
				changeSeatMsg(seatNum);
			}//end if
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(pumv, "���� PC�� �¼���ȣ�� �����ڿ��� �����µ� ����");
			e1.printStackTrace();
		}//end catch
		
		
		try {
			id=pumv.id;
//			String card=pumv.card;
			card=pumv.card;
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
			JLabel jlSeat=pumv.getJlSeatNum();
			new PUOrderingView(Integer.parseInt(jlSeat.getText()));
		}//end if
		if(ae.getSource()==pumv.getJbtCharge()) {//�ð�����
			//JOptionPane.showMessageDialog(pumv, "�ð�����");

			JLabel jlSeat=pumv.getJlSeatNum();
			new PUChargeView(Integer.parseInt(jlSeat.getText()), this);
			//RestTime
		}//end if
		if(ae.getSource()==pumv.getJbtChange()) {//�¼�����
			int flag=JOptionPane.showConfirmDialog(pumv, "�ڸ������� �Ͻðڽ��ϱ�?");
			if(flag==0) {
				JLabel jlSeat=pumv.getJlSeatNum();
				changeSeat(Integer.parseInt(jlSeat.getText()));
			}//end if
		}//end if
		if(ae.getSource()==pumv.getJbtMsg()) {//�޼���
			pu_manager.getPumsgv().setVisible(true);
		}//end if


		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(ae.getSource()==pumv.getJbtMileage()) {
			if(!id.equals("")) {
			new PUMileageStore(this);
			} else {
				JOptionPane.showMessageDialog(pumv, "ȸ���� �̿밡���� ��ư�Դϴ�");
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
	
		if(ae.getSource()==pumv.getJbtExit()) {//�������
			//��ȸ���϶����� �ð��� ������� �ʽ��ϴ�...

			if(!pumv.card.equals("")) {
				int flag=JOptionPane.showConfirmDialog(pumv, "��ȸ���� �����ð��� ������� �ʽ��ϴ�.\\n�α׾ƿ� �Ͻðڽ��ϱ�?");
				if(flag==0) {
					logout();
				}//end if
			}else {			
				int flag=JOptionPane.showConfirmDialog(pumv, "�α׾ƿ� �Ͻðڽ��ϱ�??");
				if(flag==0) {
					logout();
				}//end if
			}//end else
		}//end if
	}//actionPerformed
	
	@Override
	public void run() {
		for(int i=0; ; i++) {
			try {
				// ���ð��� �����ͼ� +1
				JLabel jlUseTime=pumv.getJlUseTime();//00:00
				jlUseTime.setText(hourTime(String.valueOf(i)));
				
				//���� �ð��� �����ͼ� -1
				JLabel jlRestTime=pumv.getJlRestTime();
				String timeString=jlRestTime.getText();//05:05
				///int restTime=minutesTime(timeString);//520
				RestTime=minutesTime(timeString);
				//�����ð��� ������ �������
				callcharge(RestTime);
				
				Thread.sleep(60000*1);//
				jlRestTime.setText(hourTime(String.valueOf(RestTime-1)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch
		}//end for
	}//run

	@Override
	public void windowClosing(WindowEvent we) {
		//��ȸ���϶����� �ð��� ������� �ʽ��ϴ�...
		if(!pumv.card.equals("")) {
			int flag=JOptionPane.showConfirmDialog(pumv, "��ȸ���� �����ð��� ������� �ʽ��ϴ�.\\n�α׾ƿ� �Ͻðڽ��ϱ�?");
			if(flag==0) {
				logout();
				pumv.dispose();
				System.exit(0);//��� ��ü ����
			}//end if
		}else {			
			int flag=JOptionPane.showConfirmDialog(pumv, "�α׾ƿ� �Ͻðڽ��ϱ�?");
			if(flag==0) {
				logout();
				pumv.dispose();
				System.exit(0);//��� ��ü ����
			}//end if
		}//end else
	}//windowClosing

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
			if(Integer.parseInt(restTime)==0) {//���� �ð��� 0�̶�� ����â
				//new PUChargeView();//���� �ð��� ���Ҵµ� �����ϸ� �ݱⰡ�� ������ �ð��� ������ ����â
				JOptionPane.showMessageDialog(pumv, "");
			}//end if
		}else if(!cardNum.equals("")) {//ī���ȣ�� �����ٸ� ��ȸ��
			jlName.setText("guest"+seatnum);
			String time="0";
			jlRestTime.setText(hourTime(time));

			if(Integer.parseInt(time)==0) {//���� �ð��� 0�̶�� ����â
				new PUChargeView(Integer.parseInt(jlSeat.getText()), this);
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
		}//end catch
	}//changeSeat
	
	/**
	 * �α׾ƿ�->PC���� ����(PC-status),�����ð� ����(ȸ��),�޼��� �ʱ�ȭ(�޼���),�α� ����(�α�)
	 * @param id
	 * @param cardNum
	 * @param seatNum
	 */
	public void logout() {
		try {
		//    ���� �α����� (String memberId,useDate/int useTime,chargePrice)
		String id=pumv.id;
		String card=pumv.card;
		String useDate = String.valueOf(Calendar.getInstance());
		
		JLabel jlUseTime=pumv.getJlUseTime();
		String useTimestr=jlUseTime.getText();//00:00
		int uTime=minutesTime(useTimestr);//����ѽð�
		
		
		
		//String restTimestr=jlRestTime.getText();
		//int restTime=minutesTime(restTimestr);

		if(!id.equals("")) {//���̵� �����ٸ� ȸ��
			PUMainUserLogVO pumLogvo=new PUMainUserLogVO(id, useDate, uTime, chargePrice);
			PUMainRestTimeVO pumrtvo=new PUMainRestTimeVO(uTime, id); //�����ð��� ���̵�
			
			pum_dao.updateRestTime(pumrtvo);//�����ð� ����
			pum_dao.updatePC(id);
			pum_dao.updateLog(pumLogvo);
		
			
		}else if(!card.equals("")) {//ī���ȣ�� �����ٸ� ��ȸ��
			//System.out.println(card);
			pum_dao.updatePC(Integer.parseInt(card));
			
		}//end else
//		pum_dao.updateMsg(Integer.parseInt(seatNum));
//		
		
		//�״��� pc���� ����
		
		//�״��� ȸ���� �����ð� ����
		
		//�״��� �޼��� �ʱ�ȭ
		
		//�����ڿ� �α׾ƿ� �ߴٰ� �޼��� ������
			pu_manager.getWriteStream().writeUTF("[logout]");
			pu_manager.getWriteStream().flush();
			pu_manager.getWriteStream().close();
			pu_manager.getReadStream().close();
			pu_manager.getClient().close();
			pumv.dispose();
			System.exit(0);//��� ��ü ����
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
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
		
		hour=(int) Math.floor(Integer.parseInt(time)/60);//���� �ð���
		minute=Integer.parseInt(time)-(hour*60);//�������� �п� �Ҵ��Ͽ�
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
	private void callcharge(int restTime) {
		pumv.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		if(restTime==0) {

			int flag=JOptionPane.showConfirmDialog(pumv, "������ �ð��� �����ϴ�.\\n�����Ͻðڽ��ϱ�?");

			if(flag==JOptionPane.OK_OPTION) {
				JLabel jlSeat=pumv.getJlSeatNum();
				new PUChargeView(Integer.parseInt(jlSeat.getText()), this);
			}else if(flag==JOptionPane.NO_OPTION){
				//return;

				//JOptionPane.showMessageDialog(pumv, "����� ����˴ϴ�.");

				//pumv.dispose();
			}//end else
		}//end if
		
		/*

		int flag=JOptionPane.showConfirmDialog(this, "���� ���ְ� ��̾��?");
//		System.out.println(flag);//0,1,2������ ���´�.
		switch (flag) {
//		case 0 ,1 ,2 �ε� �ټ� ������ �������� ��Ȯ���� �ʱ⶧���� ���ǥ���� ����.
		case JOptionPane.OK_OPTION : 
			JOptionPane.showMessageDialog(this, "!"); 
			break;
		case JOptionPane.NO_OPTION: String menu = JOptionPane.showInputDialog("� ���� �޴������?"); 
			JOptionPane.showMessageDialog(this,menu+"�� �� �׷��� ��!");
			break;
		case JOptionPane.CANCEL_OPTION:	JOptionPane.showMessageDialog(this, "�ϱ� ������??");

		}//end switch
		*/
	}//callcharge
	
	public void changeSeatMsg(int seatNum) throws IOException {
		// ��Ʈ���� ����ϰ�
		System.out.println("\"[close1111111]\" + seatNum");
		pu_manager.getWriteStream().writeUTF("[close]" + seatNum);
		// ��Ʈ���� ������ �������� ����
		pu_manager.getWriteStream().flush();
		System.out.println("\"[close222222]\" + seatNum");
}// sendMsg
	
	
	

	public int getRestTime() {
		return RestTime;
	}
	public void setRestTime(int restTime) {
		RestTime = restTime;
		JLabel jlRestTime=pumv.getJlRestTime();
		//String timeString=jlRestTime.getText();//05:05
		//int restTime=minutesTime(timeString);//520
		jlRestTime.setText(hourTime(String.valueOf(RestTime)));
	}
	public PUManager getPu_manager() {
		return pu_manager;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getId() {
		return id;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
 
	public String getCard() {
		return card;
	}

	
	
	
}//class 