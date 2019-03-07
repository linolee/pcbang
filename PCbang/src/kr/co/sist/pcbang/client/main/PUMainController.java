package kr.co.sist.pcbang.client.main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.charge.PUChargeController;
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
			JOptionPane.showMessageDialog(pumv, "이전 PC의 좌석번호를 관리자에게 보내는데 오류");
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

			searchUseInfo(id,card);//사용자 정보 조회
			//System.out.println("로그인 되었습니다.");

		} catch (UnknownHostException e) {
			System.out.println("아이피주소를 출력할수 없음");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("sql문제");
			e.printStackTrace();
		}
		if(threadOrdering==null) {//이거 없으면 계속 만들어진다.
			threadOrdering =new Thread(this);
			threadOrdering.start();
		}//end if
	}//PUMainController


	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==pumv.getJbtOrder()) {//상품주문
			JLabel jlSeat=pumv.getJlSeatNum();
			new PUOrderingView(Integer.parseInt(jlSeat.getText()));
		}//end if
		if(ae.getSource()==pumv.getJbtCharge()) {//시간충전
			//JOptionPane.showMessageDialog(pumv, "시간충전");

			JLabel jlSeat=pumv.getJlSeatNum();
			new PUChargeView(Integer.parseInt(jlSeat.getText()), this);
			//RestTime
		}//end if
		if(ae.getSource()==pumv.getJbtChange()) {//좌석변경
			int flag=JOptionPane.showConfirmDialog(pumv, "자리변경을 하시겠습니까?");
			if(flag==0) {
				JLabel jlSeat=pumv.getJlSeatNum();
				changeSeat(Integer.parseInt(jlSeat.getText()));
			}//end if
		}//end if
		if(ae.getSource()==pumv.getJbtMsg()) {//메세지
			pu_manager.getPumsgv().setVisible(true);
		}//end if


		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(ae.getSource()==pumv.getJbtMileage()) {
			if(!id.equals("")) {
			new PUMileageStore(this);
			} else {
				JOptionPane.showMessageDialog(pumv, "회원만 이용가능한 버튼입니다");
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
	
		if(ae.getSource()==pumv.getJbtExit()) {//사용종료
			//비회원일때에는 시간이 저장되지 않습니다...

			if(!pumv.card.equals("")) {
				int flag=JOptionPane.showConfirmDialog(pumv, "비회원은 남은시간이 저장되지 않습니다.\\n로그아웃 하시겠습니까?");
				if(flag==0) {
					logout();
				}//end if
			}else {			
				int flag=JOptionPane.showConfirmDialog(pumv, "로그아웃 하시겠습니까??");
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
				// 사용시간을 가져와서 +1
				JLabel jlUseTime=pumv.getJlUseTime();//00:00
				jlUseTime.setText(hourTime(String.valueOf(i)));
				
				//남은 시간을 가져와서 -1
				JLabel jlRestTime=pumv.getJlRestTime();
				String timeString=jlRestTime.getText();//05:05
				///int restTime=minutesTime(timeString);//520
				RestTime=minutesTime(timeString);
				//남은시간이 없으면 사용종료
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
		//비회원일때에는 시간이 저장되지 않습니다...
		if(!pumv.card.equals("")) {
			int flag=JOptionPane.showConfirmDialog(pumv, "비회원은 남은시간이 저장되지 않습니다.\\n로그아웃 하시겠습니까?");
			if(flag==0) {
				logout();
				pumv.dispose();
				System.exit(0);//모든 객체 종료
			}//end if
		}else {			
			int flag=JOptionPane.showConfirmDialog(pumv, "로그아웃 하시겠습니까?");
			if(flag==0) {
				logout();
				pumv.dispose();
				System.exit(0);//모든 객체 종료
			}//end if
		}//end else
	}//windowClosing

	/**
	 * 사용자 정보 조회->남은시간이 없으면 시간충전창
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
		jlSeat.setText(String.valueOf(seatnum));//자리 출력
		if(!id.equals("")) {//아이디를 가진다면 회원
			PUMainInfoVO puminfovo=pum_dao.selectInfo(id,cardNum);
			String name=puminfovo.getName();
			jlName.setText(name);
			String restTime=puminfovo.getRestTime();
			jlRestTime.setText(hourTime(restTime));
			if(Integer.parseInt(restTime)==0) {//만약 시간이 0이라면 충전창
				//new PUChargeView();//만약 시간이 남았는데 충전하면 닫기가능 충전된 시간이 없으면 충전창
				JOptionPane.showMessageDialog(pumv, "");
			}//end if
		}else if(!cardNum.equals("")) {//카드번호를 가진다면 비회원
			jlName.setText("guest"+seatnum);
			String time="0";
			jlRestTime.setText(hourTime(time));

			if(Integer.parseInt(time)==0) {//만약 시간이 0이라면 충전창
				new PUChargeView(Integer.parseInt(jlSeat.getText()), this);
			}//end if
		}//end else
	}//searchUseInfo
	
	/**
	 * 좌석번호를 받아 변경요청->재로그인 가능한 상태로 변경
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
	 * 로그아웃->PC상태 변경(PC-status),남은시간 저장(회원),메세지 초기화(메세지),로그 저장(로그)
	 * @param id
	 * @param cardNum
	 * @param seatNum
	 */
	public void logout() {
		try {
		//    먼저 로그저장 (String memberId,useDate/int useTime,chargePrice)
		String id=pumv.id;
		String card=pumv.card;
		String useDate = String.valueOf(Calendar.getInstance());
		InetAddress ip;
		ip = InetAddress.getLocalHost();
		String pcIp = String.valueOf(ip).substring(InetAddress.getLocalHost().toString().indexOf("/") + 1);
		chargePrice=PUChargeController.chargeLog;
		System.out.println(chargePrice);
		
		JLabel jlUseTime=pumv.getJlUseTime();
		String useTimestr=jlUseTime.getText();//00:00
		int uTime=minutesTime(useTimestr);//사용한시간
		
		
		
		//String restTimestr=jlRestTime.getText();
		//int restTime=minutesTime(restTimestr);

		if(!id.equals("")) {//아이디를 가진다면 회원
			PUMainUserLogVO pumLogvo=new PUMainUserLogVO(id, uTime, chargePrice);
			PUMainRestTimeVO pumrtvo=new PUMainRestTimeVO(uTime, id); //남은시간과 아이디
			
			pum_dao.updateRestTime(pumrtvo);//남은시간 갱신
			pum_dao.updatePC(pcIp);
			pum_dao.insertLog(pumLogvo);
		
			
		}else if(!card.equals("")) {//카드번호를 가진다면 비회원
			//System.out.println(card);
			pum_dao.updatePC(Integer.parseInt(card));
			
		}//end else
//		pum_dao.updateMsg(Integer.parseInt(seatNum));
//		
		
		//그다음 pc상태 변경
		
		//그다음 회원에 남은시간 변경
		
		//그다음 메세지 초기화
		
		//관리자에 로그아웃 했다고 메세지 보내기
			pu_manager.getWriteStream().writeUTF("[logout]");
			pu_manager.getWriteStream().flush();
			pu_manager.getWriteStream().close();
			pu_manager.getReadStream().close();
			pu_manager.getClient().close();
			pumv.dispose();
			System.exit(0);//모든 객체 종료
		} catch (IOException e) {
			e.printStackTrace();	
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
	}//logout
	
	/**
	 * 분으로 저장되어있는 시간을 시간으로 바꾸어 준다.
	 * @param time
	 * @return
	 */
	private String hourTime(String time) {//520
		int hour=0;
		int minute=0;
		DecimalFormat df=new DecimalFormat("00");
		
		hour=(int) Math.floor(Integer.parseInt(time)/60);//몫은 시간에
		minute=Integer.parseInt(time)-(hour*60);//나머지는 분에 할당하여
		return df.format(hour)+":"+df.format(minute);//문자열로 반환
	}//hourTime
	
	/**
	 * 시간으로 저장되어있는 시간을 분시간으로
	 * @param time
	 * @return
	 */
	private int minutesTime(String time) {//00:00
		String[] arrtime=time.split(":");
		int restTime=(Integer.parseInt(arrtime[0])*60)+Integer.parseInt(arrtime[1]);
		
		return restTime;
	}//minutesTime
	
	/**
	 * 시간이 0이면 충전창 부르고 만약 충전하지 않으면 종료
	 * @param time
	 */
	private void callcharge(int restTime) {
		pumv.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		if(restTime==0) {
			int flag=JOptionPane.showConfirmDialog(pumv, "충전된 시간이 없습니다.충전하시겠습니까?");
			if(flag==JOptionPane.OK_OPTION) {
				JLabel jlSeat=pumv.getJlSeatNum();
				new PUChargeView(Integer.parseInt(jlSeat.getText()), this);
			}else if(flag==JOptionPane.NO_OPTION){
				//return;
				int exitFlag=JOptionPane.showConfirmDialog(pumv, "충전을 하시지 않으면 사용이 종료됩니다.");
				if(exitFlag==JOptionPane.OK_OPTION) {
					logout();
					pumv.dispose();
					System.exit(0);
				}else if(exitFlag==JOptionPane.NO_OPTION){
					callcharge(restTime);
				}//end else
			}//end else
		}//end if
		
		/*

		int flag=JOptionPane.showConfirmDialog(this, "점심 맛있게 드셨어요?");
//		System.out.println(flag);//0,1,2순으로 나온다.
		switch (flag) {
//		case 0 ,1 ,2 로도 줄수 있지만 무엇인지 명확하지 않기때문에 상수표현을 쓴다.
		case JOptionPane.OK_OPTION : 
			JOptionPane.showMessageDialog(this, "!"); 
			break;
		case JOptionPane.NO_OPTION: String menu = JOptionPane.showInputDialog("어떤 점심 메뉴였어요?"); 
			JOptionPane.showMessageDialog(this,menu+"가 다 그렇죠 뭐!");
			break;
		case JOptionPane.CANCEL_OPTION:	JOptionPane.showMessageDialog(this, "하기 싫으냐??");

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

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getId() {
		return id;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
 
	public String getCard() {
		return card;
	}

	
	
	
}//class 