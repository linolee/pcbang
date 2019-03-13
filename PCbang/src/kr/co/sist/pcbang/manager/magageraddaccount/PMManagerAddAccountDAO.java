package kr.co.sist.pcbang.manager.magageraddaccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PMManagerAddAccountDAO {

	private static PMManagerAddAccountDAO pmmaa_dao;

	public PMManagerAddAccountDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch

	} // PMManagerAddAccountDAO

	public static PMManagerAddAccountDAO getInstance() {
		if (pmmaa_dao == null) {
			pmmaa_dao = new PMManagerAddAccountDAO();
		} // end if
		return pmmaa_dao;
	} // getInstance

	private Connection getConn() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "zizon";
		String pass = "darkness";

		Connection con = DriverManager.getConnection(url, id, pass);

		return con;
	} // getConn

	/* 관리자 계정추가 */
	public void insertAccount(PMManagerAddAccountVO pmmaaVO) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			StringBuilder insertAccount = new StringBuilder();

			insertAccount.append("insert into pc_admin").append("(admin_Id,	admin_Pass,	admin_Name)")
					.append("values(?,?,?  )");

			pstmt = con.prepareStatement(insertAccount.toString());
			// 4.바인드 변수 값넣기
			pstmt.setString(1, pmmaaVO.getAdminId());
			pstmt.setString(2, pmmaaVO.getAdminPass());
			pstmt.setString(3, pmmaaVO.getAdminName());
			// 5.
			pstmt.executeUpdate();
		} finally {
			// 6.
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally
	}// insertAccount

	/* 중복확인 */
	public boolean selectAccount(String id) throws SQLException {
		boolean flag= false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
	
		System.out.println("두번째 : "+id);
		
		try {
			
		con = getConn();
		
		String selectAccId = ""; 
		
		selectAccId = "SELECT * FROM PC_ADMIN WHERE ADMIN_ID = ?";
		
		pstmt = con.prepareStatement(selectAccId.toString());
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			flag = true;
		}
		
		}finally {

		if (rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (con != null) {
			con.close();
		}
	} // end finally
		
	return flag;
} // selectAcount
} // class