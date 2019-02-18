package kr.co.sist.pcbang.client.main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * ��ǻ���� �����Ǹ� �Է� �޾� �ڸ���ȣ�� �˻�.
	 * @param ip
	 * @return
	 * @throws SQLException
	 * @throws UnknownHostException 
	 */
	public int selectSeatNum() throws SQLException, UnknownHostException{
		int seatNum=0;
		InetAddress comIp = InetAddress.getLocalHost(); 
		//comIp.getHostAddress();//�� ������

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
	 * ����� ���̵� �Ǵ� ī�� ��ȣ�� �̸��� �����ð� ��ȸ
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
			String selectInfo="SELECT MEMBER_ID, MEMBER_REST_TIME , MEMBER_NAME FROM PC_MEMBER WHERE MEMBER_ID=?";
			pstmt=con.prepareStatement(selectInfo);
			pstmt.setString(1, id);
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
	
	
	
	
}//class