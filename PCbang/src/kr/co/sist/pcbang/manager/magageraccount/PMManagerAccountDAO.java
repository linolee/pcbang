package kr.co.sist.pcbang.manager.magageraccount;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PMManagerAccountDAO {

	private static PMManagerAccountDAO pmma_dao;

	public PMManagerAccountDAO() {

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch

	} // PMManagerAccountDAO

	public static PMManagerAccountDAO getInstance() {
		if (pmma_dao == null) {
			pmma_dao = new PMManagerAccountDAO();
		} // end if
		return pmma_dao;
	} // getInstance

	private Connection getConn() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pass = "tiger";

		Connection con = DriverManager.getConnection(url, id, pass);

		return con;
	} // getConn

	public List<PMManagerAccountVO> selectAccount() throws SQLException, NullPointerException {
		List<PMManagerAccountVO> list=new ArrayList<PMManagerAccountVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			
			String resultAccount = "SELECT * FROM PC_ADMIN";
			pstmt = con.prepareStatement(resultAccount);

			rs = pstmt.executeQuery();

			PMManagerAccountVO pmmaVO = null;
			
			while (rs.next()) {
				pmmaVO = new PMManagerAccountVO(rs.getString("admin_id"), rs.getString("admin_name"), rs.getString("admin_input_date"));
				list.add( pmmaVO );
			} // end if
		} finally {
			if (rs != null) {
				rs.close();
			} // end if
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		}
		return list;

		
	} // searchAccount

} // class
