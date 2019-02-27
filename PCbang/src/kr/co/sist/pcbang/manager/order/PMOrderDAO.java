package kr.co.sist.pcbang.manager.order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PMOrderDAO {
	private static PMOrderDAO pmo_dao;
	
	private PMOrderDAO() {
		// 1.
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static PMOrderDAO getInstance() {
		if(pmo_dao == null) {
			pmo_dao = new PMOrderDAO();
		}
		return pmo_dao;
	}
	
	private Connection getConn() throws SQLException {
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";
		Connection con = DriverManager.getConnection(url, id, pass);
		return con;
	} //getConn
	
	public List<PMOrderVO> selectOrder(String status) throws SQLException{
		List<PMOrderVO> list = new ArrayList<PMOrderVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = getConn();
			//3.
			StringBuilder selectAllOrder = new StringBuilder();
			selectAllOrder.append(" select o.order_num, to_char( o.order_date, 'yy-mm-dd hh:mi' )as order_date, p.seat_num, m.menu_code, m.menu_name, o.quan, m.menu_price, o.quan*m.menu_price as total ")
			.append(" from  ORDERING o, PC p, MENU m ")
			.append(" where ((p.member_id = o.member_id) OR (p.card_num = o.card_num)) AND m.menu_code = o.menu_code ")
			.append(" AND o.status = ? ");

			pstmt = con.prepareStatement(selectAllOrder.toString());
			//4.
			pstmt.setString(1, status);
			//5.
			rs = pstmt.executeQuery();
			
			PMOrderVO pmovo = null;
			
			while(rs.next()) {
				pmovo = new PMOrderVO(rs.getString("ORDER_NUM"), rs.getString("MENU_CODE"), rs.getString("MENU_NAME"), 
						rs.getString("ORDER_DATE"), rs.getInt("SEAT_NUM"), rs.getInt("QUAN"),
						rs.getInt("MENU_PRICE"), rs.getInt("TOTAL"));
				list.add(pmovo);
			}
		} finally {
			//6.
			if(rs != null) {rs.close();}
			if(pstmt != null) {pstmt.close();}
			if(con != null) {con.close();}
		}

		return list;
	}//end selectOrder
	
	public boolean deleteOrder(String orderNum) throws SQLException{
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConn();
			//3.
			String deleteOrder = " DELETE FROM ORDERING WHERE ORDER_NUM = ? ";
			pstmt = con.prepareStatement(deleteOrder);
			//4.
			pstmt.setString(1, orderNum);
			//5.
			int cnt = pstmt.executeUpdate();
			if(cnt == 1) {
				flag = true;
			}
		} finally {
			//6.
			if(pstmt != null) {pstmt.close();}
			if(con != null) {con.close();}
		}

		return flag;
	}//end deleteOrder
	
	public boolean updateOrder(String orderNum) throws SQLException{
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConn();
			//3.
			StringBuilder updateOrder = new StringBuilder();
			updateOrder.append(" UPDATE	ORDERING ")
			.append(" SET STATUS = 'Y' ")
			.append(" WHERE ORDER_NUM = ? ");

			pstmt = con.prepareStatement(updateOrder.toString());
			//4.
			pstmt.setString(1, orderNum);
			//5.
			int cnt = pstmt.executeUpdate();
			if(cnt == 1) {
				flag = true;
			}
		} finally {
			//6.
			if(pstmt != null) {pstmt.close();}
			if(con != null) {con.close();}
		}

		return flag;
	}//end updateOrder
	
	
	
	
}
