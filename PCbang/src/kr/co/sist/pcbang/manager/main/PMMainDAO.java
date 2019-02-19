package kr.co.sist.pcbang.manager.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PMMainDAO {

	private static PMMainDAO pmm_dao;
	
	public PMMainDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch		
	} // PMMainDAO
	
	public static PMMainDAO getInstance() {
		if (pmm_dao == null) {
			pmm_dao = new PMMainDAO();
		} // end if
		return pmm_dao;
	} // getInstance	
	
	private Connection getConn() throws SQLException {
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";

		Connection con = DriverManager.getConnection(url, id, pass);

		return con;	
	} // getConn

	public void insertNotice(PMMainVO pmmVO) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;		
		
		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			StringBuilder insertNotice = new StringBuilder();
			
			insertNotice.append("insert into pc_notice").append("(admin_notice, notice_input_date)")
					.append("values(?,sysdate)");
			pstmt = con.prepareStatement(insertNotice.toString());
			
			// 4.바인드 변수 값넣기
			pstmt.setString(1, pmmVO.getAdminNotice());
			// 5.
			pstmt.executeUpdate();
		}finally {
			// 6.
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally
	} // insertNotice
	
	public String selectNotice(/*PMMainVO pmmVO*/) throws SQLException {	
		
		String noticeView = "";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			

			String resultAccount = "SELECT ADMIN_NOTICE FROM (SELECT ADMIN_NOTICE FROM PC_NOTICE ORDER BY NOTICE_INPUT_DATE DESC) WHERE ROWNUM=1"; //등록일자 순으로 내림차순, 최신글 하나만 들고옴
			pstmt = con.prepareStatement(resultAccount);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				noticeView = rs.getString("admin_notice");
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
		return noticeView;
	} // selectNotice
	
} // class
