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
			searchUseInfo("user","");//사용자 정보 조회
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
			JOptionPane.showMessageDialog(pumv, "주문");
			
		}//end if
		if(ae.getSource()==pumv.getJbtChange()) {//좌석변경
			JOptionPane.showMessageDialog(pumv, "주문");
			changeSeat(0);
		}//end if
		if(ae.getSource()==pumv.getJbtMsg()) {//메세지
			
		}//end if
		if(ae.getSource()==pumv.getJbtExit()) {//사용종료
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
//	 * 사용시간+1
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
		}else if(!cardNum.equals("")) {//카드번호를 가진다면 비회원
			
		}//end else
		
	}//searchUseInfo
	
	/**
	 * 좌석번호를 받아 변경요청->재로그인 가능한 상태로 변경
	 * @param seatNum
	 */
	private void changeSeat(int seatNum) {
		
	}//changeSeat
	
	/**
	 * 로그아웃->PC상태 변경,남은시간 저장,메세지 초기화
	 * @param id
	 * @param cardNum
	 * @param seatNum
	 */
	private void logout(String id, int cardNum, int seatNum) {
		
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

}//class