package kr.co.sist.pcbang.manager.seat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kr.co.sist.pcbang.manager.seat.set.PMSeatSetLocVO;

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

	public PMSeatVO[][] selectSeatSetInfo() throws SQLException{
		
		List<PMSeatSetLocVO> tempArr = new ArrayList<>();//DB의 정보를 담을 Arr를 생성
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuilder selectPC = new StringBuilder();
		selectPC
		.append("	select x_Coor, y_Coor, seat_num, pc_ip, admin_id	")
		.append("	from pc")
		;
		
		con = getConn();
		pstmt = con.prepareStatement(selectPC.toString());
		rs = pstmt.executeQuery();
		
		PMSeatSetLocVO pmssvo;
		while (rs.next()) {
			pmssvo = new PMSeatSetLocVO(rs.getInt("x_Coor"), rs.getInt("x_Coor"), rs.getInt("seat_num"), rs.getString("pc_ip"), rs.getString("admin_id"));
			
			/////////////////////////////////////////////////////////////////////////////////////
		}
		
		PMSeatVO[][] seat; 
		return seat;
	}
	
}

