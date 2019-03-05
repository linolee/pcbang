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
import kr.co.sist.pcbang.client.login.PULoginController;
import kr.co.sist.pcbang.client.ordering.PUOrderingView;

public class PUMainController extends WindowAdapter implements ActionListener,Runnable{

	private PUMainView pumv;
	private PUMainDAO pum_dao;
	private PUManager pu_manager;
	
	private int chargePrice;
	private int RestTime;
	private Thread threadOrdering;
	private String id;
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
			e1.printStackTrace();
		}//end catch
		
		try {
			id=pumv.id;
			card=pumv.card;
			
			
			if(id==null) {
				id="";
			}//end if
			if(card==null) {
				card="";
			}//end if
			searchUseInfo(id,card);//�궗�슜�옄 �젙蹂� 議고쉶
			//System.out.println("濡쒓렇�씤 �릺�뿀�뒿�땲�떎.");
		} catch (UnknownHostException e) {
			System.out.println("�븘�씠�뵾二쇱냼瑜� 異쒕젰�븷�닔 �뾾�쓬");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("sql臾몄젣");
			e.printStackTrace();
		}
		if(threadOrdering==null) {//�씠嫄� �뾾�쑝硫� 怨꾩냽 留뚮뱾�뼱吏꾨떎.
			threadOrdering =new Thread(this);
			threadOrdering.start();
		}//end if
		
		
	}//PUMainController

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==pumv.getJbtOrder()) {//�긽�뭹二쇰Ц
			new PUOrderingView();
		}//end if
		if(ae.getSource()==pumv.getJbtCharge()) {//�떆媛꾩땐�쟾
			//JOptionPane.showMessageDialog(pumv, "�떆媛꾩땐�쟾");
			JLabel jlSeat=pumv.getJlSeatNum();
			new PUChargeView(Integer.parseInt(jlSeat.getText()), this);
			//RestTime諛쏆븘�꽌...
		}//end if
		if(ae.getSource()==pumv.getJbtChange()) {//醫뚯꽍蹂�寃�
			int flag=JOptionPane.showConfirmDialog(pumv, "�옄由щ�寃쎌쓣 �븯�떆寃좎뒿�땲源�?");
			if(flag==0) {
				JLabel jlSeat=pumv.getJlSeatNum();
				changeSeat(Integer.parseInt(jlSeat.getText()));
			}//end if
		}//end if
		if(ae.getSource()==pumv.getJbtMsg()) {//硫붿꽭吏�
			pu_manager.getPumsgv().setVisible(true);
		}//end if
		if(ae.getSource()==pumv.getJbtExit()) {//�궗�슜醫낅즺
			//鍮꾪쉶�썝�씪�븣�뿉�뒗 �떆媛꾩씠 ���옣�릺吏� �븡�뒿�땲�떎...
			if(!pumv.card.equals("")) {
				int flag=JOptionPane.showConfirmDialog(pumv, "鍮꾪쉶�썝�� �궓���떆媛꾩씠 ���옣�릺吏� �븡�뒿�땲�떎.\n濡쒓렇�븘�썐 �븯�떆寃좎뒿�땲源�?");
				if(flag==0) {
					logout();
					pumv.dispose();
					System.exit(0);//紐⑤뱺 媛앹껜 醫낅즺
				}//end if
			}else {			
				int flag=JOptionPane.showConfirmDialog(pumv, "濡쒓렇�븘�썐 �븯�떆寃좎뒿�땲源�?");
				if(flag==0) {
					logout();
					pumv.dispose();
					System.exit(0);//紐⑤뱺 媛앹껜 醫낅즺
				}//end if
			}//end else
		}//end if
	}//actionPerformed
	
	@Override
	public void run() {
		for(int i=0; ; i++) {
			try {
				//�궗�슜�떆媛꾩쓣 媛��졇���꽌 +1
				JLabel jlUseTime=pumv.getJlUseTime();//00:00
				jlUseTime.setText(hourTime(String.valueOf(i)));
				
				//�궓�� �떆媛꾩쓣 媛��졇���꽌 -1
				JLabel jlRestTime=pumv.getJlRestTime();
				String timeString=jlRestTime.getText();//05:05
				///int restTime=minutesTime(timeString);//520
				RestTime=minutesTime(timeString);
				//�궓���떆媛꾩씠 �뾾�쑝硫� �궗�슜醫낅즺
				callcharge(RestTime);
				
				Thread.sleep(1000*1);//60珥�
				jlRestTime.setText(hourTime(String.valueOf(RestTime-1)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch
		}//end for
	}//run

	@Override
	public void windowClosing(WindowEvent we) {
		//鍮꾪쉶�썝�씪�븣�뿉�뒗 �떆媛꾩씠 ���옣�릺吏� �븡�뒿�땲�떎...
		if(!pumv.card.equals("")) {
			int flag=JOptionPane.showConfirmDialog(pumv, "鍮꾪쉶�썝�� �궓���떆媛꾩씠 ���옣�릺吏� �븡�뒿�땲�떎.\n濡쒓렇�븘�썐 �븯�떆寃좎뒿�땲源�?");
			if(flag==0) {
				logout();
				pumv.dispose();
				System.exit(0);//紐⑤뱺 媛앹껜 醫낅즺
			}//end if
		}else {			
			int flag=JOptionPane.showConfirmDialog(pumv, "濡쒓렇�븘�썐 �븯�떆寃좎뒿�땲源�?");
			if(flag==0) {
				logout();
				pumv.dispose();
				System.exit(0);//紐⑤뱺 媛앹껜 醫낅즺
			}//end if
		}//end else
	}//windowClosing

	/**
	 * �궗�슜�옄 �젙蹂� 議고쉶->�궓���떆媛꾩씠 �뾾�쑝硫� �떆媛꾩땐�쟾李�
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
		jlSeat.setText(String.valueOf(seatnum));//�옄由� 異쒕젰
		if(!id.equals("")) {//�븘�씠�뵒瑜� 媛�吏꾨떎硫� �쉶�썝
			PUMainInfoVO puminfovo=pum_dao.selectInfo(id,cardNum);
			String name=puminfovo.getName();
			jlName.setText(name);
			String restTime=puminfovo.getRestTime();
			jlRestTime.setText(hourTime(restTime));
			if(Integer.parseInt(restTime)==0) {//留뚯빟 �떆媛꾩씠 0�씠�씪硫� 異⑹쟾李�
				//new PUChargeView();//留뚯빟 �떆媛꾩씠 �궓�븯�뒗�뜲 異⑹쟾�븯硫� �떕湲곌��뒫 異⑹쟾�맂 �떆媛꾩씠 �뾾�쑝硫� 異⑹쟾李�
				JOptionPane.showMessageDialog(pumv, "異⑹쟾�븯�꽭�슂");
			}//end if
		}else if(!cardNum.equals("")) {//移대뱶踰덊샇瑜� 媛�吏꾨떎硫� 鍮꾪쉶�썝
			jlName.setText("guest"+seatnum);
			String time="0";
			jlRestTime.setText(hourTime(time));

			if(Integer.parseInt(time)==0) {//留뚯빟 �떆媛꾩씠 0�씠�씪硫� 異⑹쟾李�
				new PUChargeView(Integer.parseInt(jlSeat.getText()), this);
			}//end if
		}//end else
	}//searchUseInfo
	
	/**
	 * 醫뚯꽍踰덊샇瑜� 諛쏆븘 蹂�寃쎌슂泥�->�옱濡쒓렇�씤 媛��뒫�븳 �긽�깭濡� 蹂�寃�
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
	 * 濡쒓렇�븘�썐->PC�긽�깭 蹂�寃�(PC-status),�궓���떆媛� ���옣(�쉶�썝),硫붿꽭吏� 珥덇린�솕(硫붿꽭吏�),濡쒓렇 ���옣(濡쒓렇)
	 * @param id
	 * @param cardNum
	 * @param seatNum
	 */
	public void logout() {
		try {
		//癒쇱� 濡쒓렇���옣(String memberId,useDate/int useTime,chargePrice)
		String id=pumv.id;
		String card=pumv.card;
		String useDate = String.valueOf(Calendar.getInstance());
		
		JLabel jlUseTime=pumv.getJlRestTime();
		String useTimestr=jlUseTime.getText();//00:00
		int uTime=minutesTime(useTimestr);//520
		
		JLabel jlSeatNum=pumv.getJlSeatNum();
		String seatNum=jlSeatNum.getText();
		
		JLabel jlRestTime=pumv.getJlRestTime();//->RestTime
		//String restTimestr=jlRestTime.getText();
		//int restTime=minutesTime(restTimestr);
		
		if(!id.equals("")) {//�븘�씠�뵒瑜� 媛�吏꾨떎硫� �쉶�썝
			PUMainUserLogVO pumLogvo=new PUMainUserLogVO(id, useDate, uTime, chargePrice);
			pum_dao.updatePC(id);
			pum_dao.updateLog(pumLogvo);
		
			
		}else if(!card.equals("")) {//移대뱶踰덊샇瑜� 媛�吏꾨떎硫� 鍮꾪쉶�썝
			//System.out.println(card);
			pum_dao.updatePC(Integer.parseInt(card));
			
		}//end else
//		pum_dao.updateMsg(Integer.parseInt(seatNum));
//		
		
		//洹몃떎�쓬 pc�긽�깭 蹂�寃�
		
		//洹몃떎�쓬 �쉶�썝�뿉 �궓���떆媛� 蹂�寃�
		
		//洹몃떎�쓬 硫붿꽭吏� 珥덇린�솕
		
		//愿�由ъ옄�뿉 濡쒓렇�븘�썐 �뻽�떎怨� 硫붿꽭吏� 蹂대궡湲�
			pu_manager.getWriteStream().writeUTF("[logout]");
			pu_manager.getWriteStream().flush();
			pu_manager.getWriteStream().close();
			pu_manager.getReadStream().close();
			pu_manager.getClient().close();
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
	}//logout
	
	/**
	 * 遺꾩쑝濡� ���옣�릺�뼱�엳�뒗 �떆媛꾩쓣 �떆媛꾩쑝濡� 諛붽씀�뼱 以��떎.
	 * @param time
	 * @return
	 */
	private String hourTime(String time) {//520
		int hour=0;
		int minute=0;
		DecimalFormat df=new DecimalFormat("00");
		
		hour=(int) Math.floor(Integer.parseInt(time)/60);//紐レ� �떆媛꾩뿉
		minute=Integer.parseInt(time)-(hour*60);//�굹癒몄��뒗 遺꾩뿉 �븷�떦�븯�뿬
		return df.format(hour)+":"+df.format(minute);//臾몄옄�뿴濡� 諛섑솚
	}//hourTime
	
	/**
	 * �떆媛꾩쑝濡� ���옣�릺�뼱�엳�뒗 �떆媛꾩쓣 遺꾩떆媛꾩쑝濡�
	 * @param time
	 * @return
	 */
	private int minutesTime(String time) {//00:00
		String[] arrtime=time.split(":");
		int restTime=(Integer.parseInt(arrtime[0])*60)+Integer.parseInt(arrtime[1]);
		
		return restTime;
	}//minutesTime
	
	/**
	 * �떆媛꾩씠 0�씠硫� 異⑹쟾李� 遺�瑜닿퀬 留뚯빟 異⑹쟾�븯吏� �븡�쑝硫� 醫낅즺
	 * @param time
	 */
	private void callcharge(int restTime) {
		pumv.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		if(restTime==0) {
			int flag=JOptionPane.showConfirmDialog(pumv, "異⑹쟾�맂 �떆媛꾩씠 �뾾�뒿�땲�떎.\n異⑹쟾�븯�떆寃좎뒿�땲源�?");
			if(flag==JOptionPane.OK_OPTION) {
				JLabel jlSeat=pumv.getJlSeatNum();
				new PUChargeView(Integer.parseInt(jlSeat.getText()), this);
			}else if(flag==JOptionPane.NO_OPTION){
				//return;
				//JOptionPane.showMessageDialog(pumv, "�궗�슜�씠 醫낅즺�맗�땲�떎.");
				//pumv.dispose();
			}//end else
		}//end if
		
		/*
		int flag=JOptionPane.showConfirmDialog(this, "�젏�떖 留쏆엳寃� �뱶�뀲�뼱�슂?");
//		System.out.println(flag);//0,1,2�닚�쑝濡� �굹�삩�떎.
		switch (flag) {
//		case 0 ,1 ,2 濡쒕룄 以꾩닔 �엳吏�留� 臾댁뾿�씤吏� 紐낇솗�븯吏� �븡湲곕븣臾몄뿉 �긽�닔�몴�쁽�쓣 �벖�떎.
		case JOptionPane.OK_OPTION : 
			JOptionPane.showMessageDialog(this, "�뻾蹂듯븳 �삤�썑 �릺�꽭�슂!"); 
			break;
		case JOptionPane.NO_OPTION: String menu = JOptionPane.showInputDialog("�뼱�뼡 �젏�떖 硫붾돱���뼱�슂?"); 
			JOptionPane.showMessageDialog(this,menu+"媛� �떎 洹몃젃二� 萸�!");
			break;
		case JOptionPane.CANCEL_OPTION:	JOptionPane.showMessageDialog(this, "�븯湲� �떕�쑝�깘??");
		}//end switch
		*/
	}//callcharge
	
	public void changeSeatMsg(int seatNum) throws IOException {
			// 스트림에 기록하고
			pu_manager.getWriteStream().writeUTF("[close]" + seatNum);
			// 스트림의 내용을 목적지로 분출
			pu_manager.getWriteStream().flush();
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

	public String getId() {
		return id;
	}

	public String getCard() {
		return card;
	}
	

}//class