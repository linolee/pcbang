package kr.co.sist.pcbang.client.mileage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class PUMileageDAO {
	
	private static PUMileageDAO a_dao;
	
	private PUMileageDAO() {
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	public static PUMileageDAO getInstance() {
		if(a_dao==null) {
			a_dao=new PUMileageDAO();
		}
		return a_dao;
	}
	
	private Connection getConn() throws SQLException{
		
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";

		Connection con = DriverManager.getConnection(url, id, pass);
		
		return con;
		
	}
	
	
	// ����� Ŭ���̾�Ʈ���� ���̵� �޾� ���ϸ������� �޾ƿ´�
	public int getMileage(String userID) throws SQLException{
		int mileage=0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
		
		con = getConn();
		
		String getMileage="select member_mileage from pc_member where member_id=?";
		pstmt=con.prepareStatement(getMileage);
		
		pstmt.setString(1, userID);
		
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			mileage=rs.getInt("member_mileage");
		}
		
		} finally {
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		
		return mileage;
	}
	
	
	// ���ð� �߰����� �Ű������� �޾� ���ϸ�����, �ܿ��ð��� �����Ѵ�
	public void updateMileage(int time, int mileage, String id) throws SQLException {
//		boolean flag=false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
		
		con = getConn();
		
		String updateMileage="update pc_member set member_rest_time=member_rest_time+?, member_mileage=member_mileage-? where member_id=?";
		pstmt=con.prepareStatement(updateMileage);
		
		pstmt.setInt(1, time);
		pstmt.setInt(2, mileage);
		pstmt.setString(3, id);
		
		rs=pstmt.executeQuery();
//		if(rs.next()) {
//			flag=true;
//		}
		
		} finally {
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		
		
	}
	
	
	
	
}
