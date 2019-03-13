package kr.co.sist.pcbang.manager.statics.food;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PMFoodSalesDAO {
	private static PMFoodSalesDAO pmfs_dao;

	private PMFoodSalesDAO() {
		// 1.
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static PMFoodSalesDAO getInstance() {
		if (pmfs_dao == null) {
			pmfs_dao = new PMFoodSalesDAO();
		}
		return pmfs_dao;
	}

	private Connection getConn() throws SQLException {
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";
		Connection con = DriverManager.getConnection(url, id, pass);
		return con;
	} // getConn

	public List<PMFoodSalesVO> selectFoodSales(String beforeDate, String afterDate) throws SQLException {
		List<PMFoodSalesVO> list = new ArrayList<PMFoodSalesVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			PMFoodSalesVO pmfsvo = null;

			// 3.
			StringBuilder selectFoodSales = new StringBuilder();
			selectFoodSales
			.append(" select MENU_CODE, CATEGORY, MENU_NAME, MENU_PRICE, IMG, ")
			.append(" nvl((select  sum(o.quan) ")
			.append(" from ORDERING2 o, menu m1 ")
			.append(" where (m1.menu_code=o.menu_code) and m1.menu_code = m2.menu_code ")
			.append(" and o.ORDER_DATE between TO_DATE(? ||' 00:00:00','YYYY-MM-DD HH24:MI:SS') ")
			.append(" and TO_DATE(? ||' 00:00:00','YYYY-MM-DD HH24:MI:SS') ")
			.append(" group by  m1.MENU_CODE) ,0) as quans, ")
			.append(" nvl((select  sum(o.quan) ")
			.append(" from ORDERING2 o, menu m1 ")
			.append(" where (m1.menu_code=o.menu_code) and m1.menu_code = m2.menu_code ")
			.append(" and o.ORDER_DATE between TO_DATE(? ||' 00:00:00','YYYY-MM-DD HH24:MI:SS') ")
			.append(" and TO_DATE(? ||' 00:00:00','YYYY-MM-DD HH24:MI:SS') ")
			.append(" group by  m1.MENU_CODE) * MENU_PRICE ,0) as total ")
			.append(" from menu m2 ")
			.append(" order by total desc ");

			pstmt = con.prepareStatement(selectFoodSales.toString());
			// 4.
			pstmt.setString(1, beforeDate);
			pstmt.setString(2, afterDate);//
			pstmt.setString(3, beforeDate);
			pstmt.setString(4, afterDate);//
			// 5.
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pmfsvo = new PMFoodSalesVO(rs.getString("menu_code"), rs.getString("category"), rs.getString("menu_name"), rs.getString("img")
						, rs.getInt("menu_price"), rs.getInt("quans"), rs.getInt("total"));
				list.add(pmfsvo);
			}
		} finally {
			// 6.
			if (rs != null) {rs.close();}
			if (pstmt != null) {pstmt.close();}
			if (con != null) {con.close();}
		}
		return list;
	}// end selectTodayOperating

}
