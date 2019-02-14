package kr.co.sist.pcbang.manager.fare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PMFareDAO {
	
	private static PMFareDAO f_dao;
	
	public PMFareDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static PMFareDAO getInstance() {
		if(f_dao==null) {
			f_dao = new PMFareDAO();
		}
		return f_dao;
	}
	
	private Connection getConn() throws SQLException{
		// 2.
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";

		Connection con = DriverManager.getConnection(url, id, pass);
		
		return con;
	} // getConn
	

	
	public Integer[] selectMemberFare() throws SQLException {
		Integer[] memberFare = null;
		memberFare = new Integer[10];
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = getConn();
			
			String selectFare = " select member_price from pc_price  ";
			pstmt = con.prepareStatement(selectFare);
			rs = pstmt.executeQuery();
			
			
			for(int i=0; rs.next();i++) {
				memberFare[i]=rs.getInt("member_price");
			}
			
		} finally {
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		
		
		return memberFare;
	}
	
	public Integer[] selectGuestFare() throws SQLException {
		Integer[] guestFare = null;
		guestFare = new Integer[10];
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = getConn();
			
			String selectFare = " select guest_price from pc_price  ";
			pstmt = con.prepareStatement(selectFare);
			rs = pstmt.executeQuery();
			
			for(int i=0; rs.next();i++) {
				guestFare[i]=rs.getInt("guest_price");
			}
			
		} finally {
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		
		
		return guestFare;
	}
	
	public boolean updatePrice(PMFareVO fvo) throws SQLException {
		
		boolean flag=false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = getConn();
			
			
			String updateFare = " update pc_price set member_price=?, guest_price=? where charge_time=?  ";
			pstmt = con.prepareStatement(updateFare);
			
			pstmt.setInt(1, fvo.getMemberPrice());
			pstmt.setInt(2, fvo.getGuestPrice());
			pstmt.setInt(3, fvo.getChargeTime());
			
			int cnt = pstmt.executeUpdate();
//			System.out.println("cnt = "+cnt);
			if(cnt!=0) {
				flag = true;
			}
			
		} finally {
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		
		return flag;
	}
	
	public static void main(String[] args) {
//		try {
//			System.out.println(getInstance().updatePrice(1001, 1200, 60));
//		} catch(SQLException se) {
//			se.printStackTrace();
//		}
	}
	
}
