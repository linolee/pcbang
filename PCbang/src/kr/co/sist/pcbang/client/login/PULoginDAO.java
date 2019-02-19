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
	
	public boolean selectMemberIdStatus(String id) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean statusFlag=false;
		String userStatus="";
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select pc_status ").append(" from pc ")
			.append(" where admin_id='").append(id).append("'");
			
			pstmt=con.prepareStatement(status.toString());
		//4.
		//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("pc_status");
			}//end if
			
			if(userStatus.toLowerCase().equals("y")) {
				statusFlag=true;
			}//end if
			
		}finally {
		//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return statusFlag;
	}//memberIdStatus
	
	public boolean selectGuestIdStatus(int cardNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean statusFlag=false;
		String userStatus="";
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select pc_status ").append(" from pc ")
			.append(" where card_num=").append(cardNum);
			
			pstmt=con.prepareStatement(status.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("pc_status");
			}//end if
			
			if(userStatus.toLowerCase().equals("y")) {
				statusFlag=true;
			}//end if
			
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return statusFlag;
	}//guestIdStatus
	
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
			.append(" where member_id=? && member_pass=?");
			
			pstmt=con.prepareStatement(login.toString());
			//4.
			pstmt.setString(1, pucvo.getMemberId());
			pstmt.setString(2, pucvo.getMemberPass());
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				restTime=rs.getInt("member_rest_time");
			}//end if
			
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return restTime;
	}//memberLogin
	
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
	
	public void updateMemberState(PUMemberStateVO pumsvo)throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			String updateOrder="update pc set pc_status='Y'&&member_id=? where pc_ip=?";
			pstmt=con.prepareStatement(updateOrder);
		//4.
			pstmt.setString(1, pumsvo.getMemberId());
			pstmt.setInt(2, pumsvo.getPcIp());
		//5.
			pstmt.executeUpdate();
		}finally {
		//6.
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
	}//changeMemberState
	
	public void updateGuestState(PUGuestStateVO pugsvo)throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			String updateOrder="update pc set pc_status='Y'&&card_num=? where pc_ip=?";
			pstmt=con.prepareStatement(updateOrder);
			//4.
			pstmt.setInt(1, pugsvo.getCardNum());
			pstmt.setInt(2, pugsvo.getPcIp());
			//5.
			pstmt.executeUpdate();
		}finally {
			//6.
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
	}//changeGuestState
	
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
	
}
