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
import kr.co.sist.pcbang.manager.seat.set.PMSeatSetVO;

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

	public PMSeatSetVO[][] selectSeatSetInfo() throws SQLException{
		
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
		
		PMSeatSetLocVO pmsslvo;
		while (rs.next()) {
			pmsslvo = new PMSeatSetLocVO(rs.getInt("x_Coor"), rs.getInt("y_Coor"), rs.getInt("seat_num"), rs.getString("pc_ip"), rs.getString("admin_id"));
			tempArr.add(pmsslvo);
		}//현재 DB의 PC 정보를 Arr에 저장
		PMSeatSetVO[][] seatSet;
		seatSet = new PMSeatSetVO[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				seatSet[i][j] = new PMSeatSetVO(0, "", "");
			}
		}//빈 값을 seatSet에 저장

		for (PMSeatSetLocVO tempPmsslvo : tempArr) {
			seatSet[tempPmsslvo.getxCoor()][tempPmsslvo.getyCoor()] = new PMSeatSetVO(tempPmsslvo.getSeatNum(), tempPmsslvo.getPcIP(), tempPmsslvo.getAdminID());
		}//Arr에 저장된 값을 seatSet에 저장
		
		//////////////////////////////////////////////////////////////////
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(seatSet[i][j]);
			}
			System.out.println();
		}
		////////////////////////////////////////////////////////////////
		return seatSet;
	}
	
}

