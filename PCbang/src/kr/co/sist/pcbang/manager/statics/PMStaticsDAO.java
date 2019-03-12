package kr.co.sist.pcbang.manager.statics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.pcbang.manager.order.PMOrderVO;

public class PMStaticsDAO {
	private static PMStaticsDAO pms_dao;

	private PMStaticsDAO() {
		// 1.
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static PMStaticsDAO getInstance() {
		if (pms_dao == null) {
			pms_dao = new PMStaticsDAO();
		}
		return pms_dao;
	}

	private Connection getConn() throws SQLException {
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";
		Connection con = DriverManager.getConnection(url, id, pass);
		return con;
	} // getConn

	public List<PMSOperatingTodayVO> selectMemberTodayOperating(String today) throws SQLException {
		List<PMSOperatingTodayVO> list = new ArrayList<PMSOperatingTodayVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			PMSOperatingTodayVO pmsovo = null;

			for (int i = 0; i < 24; i++) {
				// 3.
				StringBuilder selectTodayOperating = new StringBuilder();
				selectTodayOperating.append(" select nvl(SUM(member_usetime), 0) sum_m_time ")
						.append(" from MEMBER_LOG ")
						.append(" where member_usedate between TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') ")
						.append(" and TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') ");

				pstmt = con.prepareStatement(selectTodayOperating.toString());
				// 4.
				pstmt.setString(1, today + " " + i + ":00:00");
				pstmt.setString(2, today + " " + i + ":59:59");//
				// 5.
				rs = pstmt.executeQuery();

				if (rs.next()) {
					pmsovo = new PMSOperatingTodayVO(i, rs.getInt("SUM_M_TIME"));
					list.add(pmsovo);
				}
			}
		} finally {
			// 6.
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		}
		return list;
	}// end selectTodayOperating
	
	public List<PMSOperatingTodayVO> selectGuestTodayOperating(String today) throws SQLException {
		List<PMSOperatingTodayVO> list = new ArrayList<PMSOperatingTodayVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			PMSOperatingTodayVO pmsovo = null;

			for (int i = 0; i < 24; i++) {
				// 3.
				StringBuilder selectTodayOperating = new StringBuilder();
				selectTodayOperating.append(" select nvl(SUM(guest_usetime), 0) sum_g_time ")
						.append(" from PC_GUEST_LOG ")
						.append(" where guest_usedate between TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') ")
						.append(" and TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') ");

				pstmt = con.prepareStatement(selectTodayOperating.toString());
				// 4.
				pstmt.setString(1, today + " " + i + ":00:00");
				pstmt.setString(2, today + " " + i + ":59:59");//
				// 5.
				rs = pstmt.executeQuery();

				if (rs.next()) {
					pmsovo = new PMSOperatingTodayVO(i, rs.getInt("SUM_G_TIME"));
					list.add(pmsovo);
				}
			}
		} finally {
			// 6.
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		}
		return list;
	}// end selectTodayOperating

	public PMSOperatingTermVO selectMemberTermOperating(String b_day, String a_day) throws SQLException {
		PMSOperatingTermVO pmsovo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();

			// 3.
			StringBuilder selectTermOperating = new StringBuilder();
			selectTermOperating.append(" select nvl(SUM(member_usetime), 0) sum_m_time ").append(" from MEMBER_LOG ")
					.append(" where member_usedate between TO_DATE(? ||' 00:00:00','YYYY-MM-DD HH24:MI:SS') ")
					.append(" and TO_DATE(? ||' 23:59:59','YYYY-MM-DD HH24:MI:SS') ");

			pstmt = con.prepareStatement(selectTermOperating.toString());
			// 4.
			pstmt.setString(1, b_day);
			pstmt.setString(2, a_day);//
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pmsovo = new PMSOperatingTermVO(a_day, rs.getInt("SUM_M_TIME"));
			}
		} finally {
			// 6.
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		}

		return pmsovo;
	}// end selectTermOperating
	
	public PMSOperatingTermVO selectGuestTermOperating(String b_day, String a_day) throws SQLException {
		PMSOperatingTermVO pmsovo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();

			// 3.
			StringBuilder selectTermOperating = new StringBuilder();
			selectTermOperating.append(" select nvl(SUM(guest_usetime), 0) sum_m_time ").append(" from PC_GUEST_LOG ")
					.append(" where guest_usedate between TO_DATE(? ||' 00:00:00','YYYY-MM-DD HH24:MI:SS') ")
					.append(" and TO_DATE(? ||' 23:59:59','YYYY-MM-DD HH24:MI:SS') ");

			pstmt = con.prepareStatement(selectTermOperating.toString());
			// 4.
			pstmt.setString(1, b_day);
			pstmt.setString(2, a_day);//
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pmsovo = new PMSOperatingTermVO(a_day, rs.getInt("SUM_M_TIME"));
			}
		} finally {
			// 6.
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		}

		return pmsovo;
	}// end selectTermOperating

	
	public PMSOrderVO selectTermFoodSell(String b_day, String a_day) throws SQLException {
		PMSOrderVO pmsovo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();

			// 3.
			StringBuilder selectTermFoodSell = new StringBuilder();
			selectTermFoodSell
			.append(" select sum(total) total ")
			.append(" from(select o.quan, m.menu_price, o.quan*m.menu_price as total ")
			.append(" from ORDERING2 o, MENU m ")
			.append(" where (m.menu_code = o.menu_code) AND o.order_date between TO_DATE(? ||' 00:00:00','YYYY-MM-DD HH24:MI:SS') ")
			.append(" and TO_DATE(? ||' 23:59:59','YYYY-MM-DD HH24:MI:SS')) ");
			
			pstmt = con.prepareStatement(selectTermFoodSell.toString());
			// 4.
			pstmt.setString(1, b_day);
			pstmt.setString(2, a_day);//
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if(b_day.equals(a_day)) {
					pmsovo = new PMSOrderVO(rs.getInt("total"), a_day);
				}else {
					pmsovo = new PMSOrderVO(rs.getInt("total"), b_day+" ~ "+a_day);
				}
			}
		} finally {
			// 6.
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		}

		return pmsovo;
	}// end selectTermFoodSell
	
	public PMSOrderVO selectTermChargePriceMember(String b_day, String a_day) throws SQLException {
		PMSOrderVO pmsovo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();

			// 3.
			StringBuilder selectTermChargePriceMember = new StringBuilder();
			selectTermChargePriceMember
			.append(" select nvl(SUM(member_chargeprice), 0) sum_m_price ")
			.append(" from MEMBER_LOG ")
			.append(" where member_usedate between TO_DATE(? ||' 00:00:00','YYYY-MM-DD HH24:MI:SS') ")
			.append(" and TO_DATE(? ||' 23:59:59','YYYY-MM-DD HH24:MI:SS') ");
			
			pstmt = con.prepareStatement(selectTermChargePriceMember.toString());
			// 4.
			pstmt.setString(1, b_day);
			pstmt.setString(2, a_day);//
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if(b_day.equals(a_day)) {
					pmsovo = new PMSOrderVO(rs.getInt("sum_m_price"), a_day);
				}else {
					pmsovo = new PMSOrderVO(rs.getInt("sum_m_price"), b_day+" ~ "+a_day);
				}
			}
		} finally {
			// 6.
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		}

		return pmsovo;
	}// end selectTermFoodSell
	
	public PMSOrderVO selectTermChargePriceGuest(String b_day, String a_day) throws SQLException {
		PMSOrderVO pmsovo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();

			// 3.
			StringBuilder selectTermChargePriceGuest = new StringBuilder();
			selectTermChargePriceGuest
			.append(" select nvl(SUM(guest_chargeprice), 0) sum_g_price ")
			.append(" from PC_GUEST_LOG ")
			.append(" where guest_usedate between TO_DATE(? ||' 00:00:00','YYYY-MM-DD HH24:MI:SS') ")
			.append(" and TO_DATE(? ||' 23:59:59','YYYY-MM-DD HH24:MI:SS') ");
			
			pstmt = con.prepareStatement(selectTermChargePriceGuest.toString());
			// 4.
			pstmt.setString(1, b_day);
			pstmt.setString(2, a_day);//
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if(b_day.equals(a_day)) {
					pmsovo = new PMSOrderVO(rs.getInt("sum_g_price"), a_day);
				}else {
					pmsovo = new PMSOrderVO(rs.getInt("sum_g_price"), b_day+" ~ "+a_day);
				}
			}
		} finally {
			// 6.
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		}

		return pmsovo;
	}// end selectTermFoodSell
}
