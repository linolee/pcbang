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

	public PMSOperatingTermVO selectTermOperating(String today) throws SQLException {
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
			pstmt.setString(1, today);
			pstmt.setString(2, today);//
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pmsovo = new PMSOperatingTermVO(today, rs.getInt("SUM_M_TIME"));
			}
		} finally {
			// 6.
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}

		return pmsovo;
	}// end selectTermOperating

}
