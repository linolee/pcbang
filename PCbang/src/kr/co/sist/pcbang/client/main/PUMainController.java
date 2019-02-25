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
			new PUOrderingView();
		}//end if
		if(ae.getSource()==pumv.getJbtCharge()) {//시간충전
			//JOptionPane.showMessageDialog(pumv, "시간충전");
			JLabel jlSeat=pumv.getJlSeatNum();
			new PUChargeView(Integer.parseInt(jlSeat.getText()));
		}//end if
		if(ae.getSource()==pumv.getJbtChange()) {//좌석변경
			int flag=JOptionPane.showConfirmDialog(pumv, "자리변경을 하시겠습니까?");
			if(flag==0) {
				JLabel jlSeat=pumv.getJlSeatNum();
				changeSeat(Integer.parseInt(jlSeat.getText()));
			}//end if
		}//end if
		if(ae.getSource()==pumv.getJbtMsg()) {//메세지
			JOptionPane.showMessageDialog(pumv, "메세지");
			
		}//end if
		if(ae.getSource()==pumv.getJbtExit()) {//사용종료
			//비회원일때에는 시간이 저장되지 않습니다...
			int flag=JOptionPane.showConfirmDialog(pumv, "정말 로그아웃 하시겠습니까?");
			if(flag==0) {
				logout();
				pumv.dispose();
				System.exit(0);//모든 객체 종료
			}//end if
		}//end if
	}//actionPerformed
	
	@Override
	public void run() {
		for(int i=0; i<10080; i++) {//7일
			try {
				//사용시간을 가져와서 +1
				JLabel jlUseTime=pumv.getJlUseTime();//00:00
				jlUseTime.setText(hourTime(String.valueOf(i)));
				
				//남은 시간을 가져와서 -1
				JLabel jlRestTime=pumv.getJlRestTime();
				String timeString=jlRestTime.getText();//05:05
				int restTime=minutesTime(timeString);//520
				if(0<i) {
					jlRestTime.setText(hourTime(String.valueOf(restTime-1)));
				}//end if
				//남은시간이 없으면 사용종료
				if(timeString.equals("00:00")) {
					int flag=JOptionPane.showConfirmDialog(pumv, "충전된 시간이 없습니다.\n충전하시겠습니까?");
					if(flag==0) {
						JOptionPane.showMessageDialog(pumv, "충전창");
						JLabel jlSeat=pumv.getJlSeatNum();
						new PUChargeView(Integer.parseInt(jlSeat.getText()));
					}else if(flag==1){
						JOptionPane.showMessageDialog(pumv, "사용이 종료됩니다.");
						pumv.dispose();
					}
				}//end if
				
				Thread.sleep(1000*60);//60초
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//end catch
		}//end for
	}//run

	@Override
	public void windowClosing(WindowEvent we) {
		//비회원일때에는 시간이 저장되지 않습니다...
		int flag=JOptionPane.showConfirmDialog(pumv, "정말 로그아웃 하시겠습니까?");
		if(flag==0) {
			logout();
			pumv.dispose();
			System.exit(0);//모든 객체 종료
		}//end if
	}//windowClosing
	
//	/**
//	 * 사용시간+1/남은시간-1
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
		//System.out.println("로그인중");
		if(!id.equals("")) {//아이디를 가진다면 회원
			//System.out.println("회원");
			PUMainInfoVO puminfovo=pum_dao.selectInfo(id,cardNum);
			String name=puminfovo.getName();
			jlName.setText(name);
			String restTime=puminfovo.getRestTime();
			jlRestTime.setText(hourTime(restTime));
			if(Integer.parseInt(restTime)==0) {//만약 시간이 0이라면 충전창
				//new PUChargeView();//만약 시간이 남았는데 충전하면 닫기가능 충전된 시간이 없으면 충전창
				JOptionPane.showMessageDialog(pumv, "충전하세요");
			}//end if
		}else if(!cardNum.equals("")) {//카드번호를 가진다면 비회원
			//System.out.println("비회원");
			jlName.setText("guest"+seatnum);
			String time="0";
			jlRestTime.setText(hourTime(time));
			if(Integer.parseInt(time)==0) {//만약 시간이 0이라면 충전창
				new PUChargeView(Integer.parseInt(jlSeat.getText()));
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
		}
	}//changeSeat
	
	/**
	 * 로그아웃->PC상태 변경(PC-status),남은시간 저장(회원),메세지 초기화(메세지),로그 저장(로그)
	 * @param id
	 * @param cardNum
	 * @param seatNum
	 */
	private void logout() {
		//먼저 로그저장(String memberId,useDate/int useTime,chargePrice)
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
		
		//그다음 pc상태 변경
		
		//그다음 회원에 남은시간 변경
		
		//그다음 메세지 초기화
		
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
		
		//System.out.println("총시간: "+time);
		hour=(int) Math.floor(Integer.parseInt(time)/60);//몫은 시간에
		//System.out.println("시: "+hour);
		minute=Integer.parseInt(time)-(hour*60);//나머지는 분에 할당하여
		//System.out.println("분: "+minute);
		//System.out.println(df.format(hour)+":"+df.format(minute+10));
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
	private void callcharge(int time) {
		pumv.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		if(time==0) {
			int flag=JOptionPane.showConfirmDialog(pumv, "충전된 시간이 없습니다.\n충전하시겠습니까?");
			if(flag==JOptionPane.OK_OPTION) {
				JLabel jlSeat=pumv.getJlSeatNum();
				new PUChargeView(Integer.parseInt(jlSeat.getText()));
			}else if(flag==JOptionPane.NO_OPTION){
				return;
				//JOptionPane.showMessageDialog(pumv, "사용이 종료됩니다.");
				//pumv.dispose();
			}
		}//end if
	}

	public PUManager getPu_manager() {
		return pu_manager;
	}

}//class