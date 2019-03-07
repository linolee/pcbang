package kr.co.sist.pcbang.manager.seat.message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.pcbang.manager.seat.set.PMSeatSetLocVO;
import kr.co.sist.pcbang.manager.seat.set.PMSeatSetVO;

public class PMMessageDAO {

	private static PMMessageDAO pms_dao;

	private PMMessageDAO() {
		// 1.
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch
	}// LunchAdminDAO

	public static PMMessageDAO getInstance() {
		if (pms_dao == null) {
			pms_dao = new PMMessageDAO();
		} // end if
		return pms_dao;
	}// getInstance

	private Connection getConn() throws SQLException {
		// 2.
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";

		Connection con = DriverManager.getConnection(url, id, pass);
		return con;
	}// getConn

	public Integer orderDone(int seatNum) throws SQLException{
		Integer cnt = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		
		try {
			con = getConn();
			//3.
			String orderDone = " UPDATE ORDERING2 SET STATUS = 'Y' WHERE SEAT_NUM = ? ";
			pstmt = con.prepareStatement(orderDone);
			String changOrderStatus = " UPDATE PC_STATUS SET ORDER_STATUS = 'N' WHERE SEAT_NUM = ? ";
			pstmt2 = con.prepareStatement(changOrderStatus);
			//4.
			pstmt.setInt(1, seatNum);
			pstmt2.setInt(1, seatNum);
			//5.
			cnt = pstmt.executeUpdate();
			pstmt2.executeUpdate();
			
		} finally {
			//6.
			if(pstmt != null) {pstmt.close();}
			if(con != null) {con.close();}
		}

		return cnt;
	}

}
