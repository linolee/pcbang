package kr.co.sist.pcbang.client.login.newuser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PUNewUserDAO {

	private static PUNewUserDAO punu_dao;

	public PUNewUserDAO() {

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch

	} // PUZipcodeDAO

	public static PUNewUserDAO getInstance() {
		if (punu_dao == null) {
			punu_dao = new PUNewUserDAO();
		} // end if
		return punu_dao;
	} // getInstance

	private Connection getConn() throws SQLException {
		String url="jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id="zizon";
		String pass="darkness";

		Connection con = DriverManager.getConnection(url, id, pass);

		return con;
	} // getConn

	/*아이디 중복확인*/
	public boolean selectMemderId(String id) throws SQLException {
		boolean flag= false;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
	
		try {
			
		con = getConn();
		
		String selectId = "";
		
		selectId = "SELECT MEMBER_ID FROM PC_MEMBER WHERE MEMBER_ID = ?";
		pstmt = con.prepareStatement(selectId.toString());
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		
		//PUNewUserVO punuvo = null;
		
		if(rs.next()) {
			//punuvo = new PUNewUserVO(rs.getString("MEMBER_ID"));
			flag = true;
		}
		
		} finally {

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
	
	
	/*회원 계정 추가*/
	
	public void insertMember(PUNewUserVO punuvo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			StringBuilder insertMember = new StringBuilder();
			
			insertMember.append("insert into PC_MEMBER").append("(MEMBER_ID, MEMBER_PASS, MEMBER_NAME, MEMBER_BIRTH, MEMBER_GENDER, MEMBER_TEL, MEMBER_EMAIL, MEMBER_DETAILADD, MEMBER_INPUT_DATE, MEMBER_REST_TIME, MEMBER_TOTAL_PRICE, MEMBER_MILEAGE)")
					.append("values(?,?,?,?,?,?,?,?,sysdate,0,0,0 )");

			pstmt = con.prepareStatement(insertMember.toString());
			// 4.바인드 변수 값넣기
			pstmt.setString(1, punuvo.getId());
			pstmt.setString(2, punuvo.getPass());
			pstmt.setString(3, punuvo.getName());
			pstmt.setString(4, punuvo.getBirth());
			pstmt.setString(5, punuvo.getGender());
			pstmt.setString(6, punuvo.getTel());
			pstmt.setString(7, punuvo.getEmail());
			pstmt.setString(8, punuvo.getDetailadd());
			
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
	}// insertMember	
	
} // class
