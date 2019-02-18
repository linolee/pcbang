package kr.co.sist.pcbang.client.login.finduser;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.sist.pcbang.client.login.finduser.updatepass.PUUpdatePassVO;


public class PUFindUserDAO {
	private static PUFindUserDAO pufu_dao;
	
	private PUFindUserDAO() {
		//1. 
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	}//PUFindUserDAO
	
	public static PUFindUserDAO getInstance() {
		if(pufu_dao == null) {
			pufu_dao=new PUFindUserDAO();
		}//end if
		return pufu_dao;
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
	
	public String searchId(PUFindUserVO pufuvo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String id="";
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder search=new StringBuilder();
			
			search.append("select member_id ")
			.append(" from pc_member ")
			.append("where member_name=? and member_tel=?");
			
			pstmt=con.prepareStatement(search.toString());
			pstmt.setString(1, pufuvo.getUserName());
			pstmt.setString(2, pufuvo.getUserPhone());
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				id=rs.getString("member_id");
			}//end if
			
			//4.
			//5.
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return id;
	}//searchId
	
	public boolean searchPass(PUFindPassVO pufpvo) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean flag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder search=new StringBuilder();
			
			search.append("select member_id ")
			.append(" from pc_member ")
			.append("where member_name=? and member_tel=?");
			
			pstmt=con.prepareStatement(search.toString());
			pstmt.setString(1, pufpvo.getUserName());
			pstmt.setString(2, pufpvo.getUserPhone());
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				if(pufpvo.getUserId().equals(rs.getString("member_id"))) {
					flag=true;
				}//end if
				if(!pufpvo.getUserId().equals(rs.getString("member_id"))) {
					flag=false;
				}//end if
			}//end if
			
			//4.
			//5.
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return flag;
	}//searchId
	
	public void changePass(PUUpdatePassVO puupvo) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder update=new StringBuilder();
			
			update.append("update pc_member ")
			.append(" set member_pass=?")
			.append(" where member_id=?");
			
			pstmt=con.prepareStatement(update.toString());
			pstmt.setString(1, puupvo.getUserPass());
			pstmt.setString(2, puupvo.getUserId());
			pstmt.executeUpdate();
			//4.
			//5.
		}finally {
			//6.
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
	}//checkMember
	
}//class
