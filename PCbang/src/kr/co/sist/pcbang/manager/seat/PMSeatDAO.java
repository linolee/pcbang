package kr.co.sist.pcbang.manager.seat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PMSeatDAO {

	private static PMSeatDAO pms_dao;
	
	
	private PMSeatDAO() {
		// 1.
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch
	}// LunchAdminDAO

	public static PMSeatDAO getInstance() {
		if (pms_dao == null) {
			pms_dao = new PMSeatDAO();
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

	public Set<PMSeatLocVO> selectSeatInfo() throws SQLException{
		Set<PMSeatLocVO> seat = new HashSet<>(); 
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectPC = new StringBuilder();
		selectPC
		.append("	select x_Coor, y_Coor, seat_num, pc_ip, pc_status	")
		.append("	from pc")
		;
		
		con = getConn();
		pstmt = con.prepareStatement(selectPC.toString());
		rs = pstmt.executeQuery();
		
		PMSeatLocVO pmsvo;
		while (rs.next()) {
			/////////////////////////////////////////////////////////////////////////////////////
		}
		
		return seat;
	}
	
}

