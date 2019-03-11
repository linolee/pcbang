package kr.co.sist.pcbang.client.main;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.sist.pcbang.client.charge.PUChargeController;
import kr.co.sist.pcbang.client.login.PUMemberStateVO;

public class PUMainDAO {

	private static PUMainDAO pum_dao;
	
	public PUMainDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	}//PUMainDAO
	
	public static PUMainDAO getInstance() {
		if(pum_dao==null) {
			pum_dao=new PUMainDAO();
		}//end if
		return pum_dao;
	}//getInstance
	
	private Connection getConn() throws SQLException{
		//2.
		String url="jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id="zizon";
		String pass="darkness";
		Connection con=DriverManager.getConnection(url, id, pass);
		return con;
	}//getConn
	
	/**
	 * 컴퓨터의 아이피를 입력 받아 자리번호를 검색.
	 * @param ip
	 * @return
	 * @throws SQLException
	 * @throws UnknownHostException 
	 */
	public int selectSeatNum() throws SQLException, UnknownHostException{
		int seatNum=0;
		InetAddress comIp = InetAddress.getLocalHost(); 
		//comIp.getHostAddress();//내 아이피

		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
				
		try {
			con=getConn();
			String selectName="SELECT SEAT_NUM FROM PC WHERE PC_IP=?";
			pstmt=con.prepareStatement(selectName);
			pstmt.setString(1, comIp.getHostAddress());
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				seatNum=Integer.parseInt(rs.getString("SEAT_NUM"));
			}//end if
		}finally {
			if(rs!=null) {rs.close();}//end if
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally
		
		return seatNum;
	}//selectSeatNum

	/**
	 * 사용자 아이디 또는 카드 번호로 이름과 남은시간 조회
	 * @param puminfovo
	 * @throws SQLException
	 */
	public PUMainInfoVO selectInfo(String id, String cardNum) throws SQLException{
		PUMainInfoVO puminfovo=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
			
		try {
		//1.
		//2.
			con=getConn();
		//3.
			if(!id.equals("")) {//아이디를 가진다면 회원
				String selectInfo="SELECT MEMBER_ID, MEMBER_REST_TIME , MEMBER_NAME FROM PC_MEMBER WHERE MEMBER_ID=?";
				pstmt=con.prepareStatement(selectInfo);
				pstmt.setString(1, id);
			}else if(!cardNum.equals("")) {//카드번호를 가진다면 비회원
				
			}//end else
			rs=pstmt.executeQuery();
			if(rs.next()) {
				puminfovo=new PUMainInfoVO(rs.getString("MEMBER_REST_TIME"), rs.getString("MEMBER_NAME"));
			}//end if
		
		}finally {
			if(rs!=null) {rs.close();}//end if
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}
		return puminfovo;
	}//selectInfo
	
	/**
	 * 자리이동이 가능한 상태로 변경->'c'
	 * @param seatNum
	 * @throws SQLException
	 */
	public void updateSeat(int seatNum) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=getConn();
		//3.
			StringBuilder updateSeat=new StringBuilder();
			updateSeat.append("UPDATE PC_STATUS SET PC_STATUS='C' WHERE SEAT_NUM=?");
			pstmt=con.prepareStatement(updateSeat.toString());
		//4.바인드변수 값넣기
			pstmt.setString(1, String.valueOf(seatNum));
		//5.
			pstmt.executeUpdate();
		}finally {
			//6.
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally
	}//updateSeat
	
	/**
	 * 회원이 로그아웃할 때 pc테이블 업데이트
	 * @param pumlogvo
	 */
	public boolean updateMemberPC(String pcIp) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		boolean flag=false;
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			StringBuilder updatePc=new StringBuilder();
			updatePc.append("update pc set member_id='' where pc_ip='")
			.append(pcIp).append("'");
		//4.
			pstmt1=con.prepareStatement(updatePc.toString());
		//5.
			int cnt1 = pstmt1.executeUpdate();
		//3.
			StringBuilder updateStatus=new StringBuilder();
			updateStatus.append("update pc_status set pc_status='N' where seat_num=(select seat_num from pc where pc_ip='")
			.append(pcIp).append("')");
		//4.
			pstmt2=con.prepareStatement(updateStatus.toString());
		//5.
			int cnt2 = pstmt2.executeUpdate();
			
			if( cnt1==1 && cnt2 ==1 ) {
				flag=true;
			}//end if
		}finally {
		//6.
			if( pstmt2 != null ) { pstmt2.close(); }//end if
			if( pstmt1 != null ) { pstmt1.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return flag;
	}//updatePC
	/**
	 * 비회원이 로그아웃할 때 pc테이블 업데이트
	 * @param pumlogvo
	 */
	public boolean updateGuestPC(String pcIp) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		boolean flag=false;
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			StringBuilder updatePc=new StringBuilder();
			updatePc.append("update pc set card_num='' where pc_ip='")
			.append(pcIp).append("'");
		//4.
			pstmt1=con.prepareStatement(updatePc.toString());
		//5.
			int cnt1 = pstmt1.executeUpdate();
		//3.
			StringBuilder updateStatus=new StringBuilder();
			updateStatus.append("update pc_status set pc_status='N' where seat_num=(select seat_num from pc where pc_ip='")
			.append(pcIp).append("')");
		//4.
			pstmt2=con.prepareStatement(updateStatus.toString());
		//5.
			int cnt2 = pstmt2.executeUpdate();
			
			if( cnt1==1 && cnt2 ==1 ) {
				flag=true;
			}//end if
		}finally {
		//6.
			if( pstmt2 != null ) { pstmt2.close(); }//end if
			if( pstmt1 != null ) { pstmt1.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		return flag;
	}//updateGuestPC
	
	public void updateRestTime(PUMainRestTimeVO pumrtvo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		String updateRestTime="";
		try {
			con=getConn();
		//3.
			updateRestTime="UPDATE PC_MEMBER SET member_rest_time=member_rest_time-? where member_id=?";
			
			pstmt=con.prepareStatement(updateRestTime);
		//4.바인드변수 값넣기
			pstmt.setInt(1, pumrtvo.getMemberUseTime());
			pstmt.setString(2, pumrtvo.getMemberId());
		//5.
			pstmt.executeUpdate();
			
		}finally {
			//6.
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally	
		
	}//updateRestTime
	
	/**
	 * 로그아웃 할때 저장된 메세지 초기화
	 * @param seatNum
	 */
	public void updateMsg(int seatNum) {
		
	}//updateMsg
	
	/**
	 * 로그아웃할 때 log저장
	 * @param pumLogvo
	 */
	public void insertLog(PUMainMemberLogVO pummLogvo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		String insertLog="";
		
		try {
			con=getConn();
		//3.
			insertLog="insert into member_log(member_id, member_usetime, member_usedate, member_chargeprice) values(?,?,sysdate,?)";
			pstmt=con.prepareStatement(insertLog);
		//4.바인드변수 값넣기
			pstmt.setString(1, pummLogvo.getMemberId());
			pstmt.setInt(2, pummLogvo.getUseTime());
			pstmt.setInt(3, pummLogvo.getChargePrice());
		//5.
			pstmt.executeUpdate();
		}finally {
			//6.
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally	
		
	}//insertLog
	
	public void insertLog(PUMainGuestLogVO pumgLogvo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		String insertLog="";
		
		try {
			con=getConn();
			//3.
			insertLog="insert into pc_guest_log(card_num, guest_usetime, guest_usedate, guest_chargeprice) values(?,?,sysdate,?)";
			pstmt=con.prepareStatement(insertLog);
			//4.바인드변수 값넣기
			pstmt.setInt(1, pumgLogvo.getCardNum());
			pstmt.setInt(2, pumgLogvo.getUseTime());
			pstmt.setInt(3, pumgLogvo.getChargePrice());
			//5.
			pstmt.executeUpdate();
		}finally {
			//6.
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally	
		
	}//insertLog
	
}//class