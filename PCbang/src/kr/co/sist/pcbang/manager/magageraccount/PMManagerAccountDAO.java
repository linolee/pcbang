package kr.co.sist.pcbang.manager.magageraccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.pcbang.manager.magageraddaccount.PMManagerAddAccountVO;

public class PMManagerAccountDAO {

	private static PMManagerAccountDAO pmma_dao;

	public PMManagerAccountDAO() {

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
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
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";

		Connection con = DriverManager.getConnection(url, id, pass);

		return con;
	} // getConn
	
	/*계정조회*/
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
	
	/*계정추가*/
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
	
	
	/*계정삭제*/
	public boolean deleteAccount(String adminId) throws SQLException {
		boolean flag=false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.
			// 2.
			con = getConn();

			StringBuilder deleteAccount = new StringBuilder();
			
			deleteAccount.append("delete from pc_admin ").append("where admin_Id = '").append(adminId+"'");
			
			pstmt = con.prepareStatement(deleteAccount.toString());
			
			// 5.
			int cnt=pstmt.executeUpdate();
			if( cnt == 1) {
				flag=true;
			}//end if
		} finally {
			// 6.
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally
		return flag;
	}// deleteAccount
	
} // class
