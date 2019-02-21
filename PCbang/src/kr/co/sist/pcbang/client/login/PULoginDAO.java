package kr.co.sist.pcbang.client.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PULoginDAO {
private static PULoginDAO pul_dao;
	
	private PULoginDAO() {
		//1. 
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	}//LunchClientDAO
	
	public static PULoginDAO getInstance() {
		if(pul_dao == null) {
			pul_dao=new PULoginDAO();
		}//end if
		return pul_dao;
	}//getInstance
	
	private Connection getConn() throws SQLException{
		//2.
		Connection con=null;
		
		String url="jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id="zizon";
		String pass="darkness";
		
		con=DriverManager.getConnection(url, id, pass );
		
		return con;
	}//getConn
	
	/**
	 * PC에서 id를 이미 사용중인지 판단
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean selectMember(String id) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String userStatus="";
		boolean userFlag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select member_id ").append(" from pc ")
			.append(" where member_id='").append(id).append("'");
			
			pstmt=con.prepareStatement(status.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("member_id");
			}//end if
			if(!userStatus.equals("")) {
				userFlag=true;
			}//end if
			
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return userFlag;
	}//selectMember
	
	/**
	 * PC에서 카드번호를 이미 사용중인지 판단
	 * @param cardNum
	 * @return
	 * @throws SQLException
	 */
	public boolean selectGuest(int cardNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String userStatus="";
		boolean userFlag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select card_num ").append(" from pc ")
			.append(" where card_num=").append(cardNum);
			
			pstmt=con.prepareStatement(status.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("card_num");
			}//end if
			if(!userStatus.equals("")) {
				userFlag=true;
			}//end if
			
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return userFlag;
	}//selectGuest
	
	/**
	 * 회원의 아이디가 현재 PC에서 자리이동 등 의 상태가 가능한지 여부
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String selectMemberIdStatus(String id) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String userStatus="";
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select pc_status ").append(" from pc_status ")
			.append(" seat_num=(select seat_num from pc where member_id='").append(id).append("')");
			
			pstmt=con.prepareStatement(status.toString());
		//4.
		//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("pc_status");
			}//end if
			
		}finally {
		//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return userStatus;
	}//memberIdStatus
	
	/**
	 * 비회원의 카드가 현재 PC에서 자리이동 등의 상태가 가능한지 여부
	 * @param cardNum
	 * @return
	 * @throws SQLException
	 */
	public String selectGuestIdStatus(int cardNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String userStatus="";
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select pc_status ").append(" from pc_status ")
			.append(" seat_num=(select seat_num from pc where card_num=").append(cardNum).append(")");
			
			pstmt=con.prepareStatement(status.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("pc_status");
			}//end if
			
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return userStatus;
	}//guestIdStatus
	
	/**
	 * 회원의 아이디와 비밀번호가 맞다면 남은시간 정보를 가져오는 일
	 * @param pucvo
	 * @return
	 * @throws SQLException
	 */
	public int selectMemberLogin(PUCertificationVO pucvo) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int restTime=-1;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder login=new StringBuilder();
			
			login.append("select member_rest_time ").append(" from pc_member ")
			.append(" where member_id=? and member_pass=?");
			
			pstmt=con.prepareStatement(login.toString());
			//4.
			pstmt.setString(1, pucvo.getMemberId());
			pstmt.setString(2, pucvo.getMemberPass());
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				restTime=rs.getInt("member_rest_time");
			}//end if
			System.out.println(restTime);
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return restTime;
	}//memberLogin
	
	/**
	 * 비회원의 카드번호가 맞는지 판단
	 * @param cardNum
	 * @return
	 * @throws SQLException
	 */
	public boolean selectGuestCheck(int cardNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int num=-1;
		boolean flag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder check=new StringBuilder();
			
			check.append("select card_num ").append(" from pc_guest ")
			.append(" where card_num=").append(cardNum);
			
			pstmt=con.prepareStatement(check.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				num=rs.getInt("card_num");
			}//end if
			if(num!=-1) {
				flag=true;
			}//end if
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		return flag;
	}//guestCheck
	
	/**
	 * 회원이 로그인 했을 때 로그인한 PC의 상태변경
	 * @param pumsvo
	 * @throws SQLException
	 */
	public boolean updateMemberState(PUMemberStateVO pumsvo)throws SQLException {
		Connection con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		boolean flag=false;
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			String updatePc="update pc set member_id=? where pc_ip=?";
			pstmt1=con.prepareStatement(updatePc);
		//4.
			pstmt1.setString(1, pumsvo.getMemberId());
			pstmt1.setString(2, pumsvo.getPcIp());
		//5.
			int cnt1 = pstmt1.executeUpdate();
		//3.
			String updateStatus
			="update pc_status set pc_status='Y' where seat_num=(select seat_num from pc where pc_ip=?)";
			pstmt2=con.prepareStatement(updateStatus);
		//4.
			pstmt2.setString(1, pumsvo.getMemberId());
			pstmt2.setString(2, pumsvo.getPcIp());
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
	}//changeMemberState
	
	/**
	 * 비회원이 로그인 했을 때 로그인한 PC의 상태변경
	 * @param pugsvo
	 * @return
	 * @throws SQLException
	 */
	public boolean updateGuestState(PUGuestStateVO pugsvo)throws SQLException {
		Connection con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		boolean flag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			String updatePc="update pc set card_num=? where pc_ip=?";
			pstmt1=con.prepareStatement(updatePc);
		//4.
			pstmt1.setInt(1, pugsvo.getCardNum());
			pstmt1.setString(2, pugsvo.getPcIp());
		//5.
			int cnt1 = pstmt1.executeUpdate();
		//3.
			String updateStatus
			="update pc_status set pc_status='Y' where seat_num=(select seat_num from pc where pc_ip=?)";
			pstmt2=con.prepareStatement(updateStatus);
		//4.
			pstmt2.setInt(1, pugsvo.getCardNum());
			pstmt2.setString(2, pugsvo.getPcIp());
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
	}//changeGuestState
	
	/**
	 * 관리자의 공지사항
	 * @return
	 * @throws SQLException
	 */
	public String selectNotice() throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String notice="";
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder note=new StringBuilder();
			
			note.append("select admin_notice ").append(" from pc_admin ");
			
			pstmt=con.prepareStatement(note.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				notice=rs.getString("admin_notice");
			}//end if
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		return notice;
	}//guestCheck
	
}//class
