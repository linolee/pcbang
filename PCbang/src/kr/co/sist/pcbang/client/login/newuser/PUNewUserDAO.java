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
		
		PUNewUserVO punuvo = null;
		
		if(rs.next()) {
			punuvo = new PUNewUserVO(rs.getString("MEMBER_ID"));
			System.out.println(punuvo.getId()); 
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
} // class
