package kr.co.sist.pcbang.manager.login;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PMLoginDAO {

	private static PMLoginDAO pml_dao;
	
	public PMLoginDAO() {
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	} // PMLoginDAO
	
	public static PMLoginDAO getInstance() {
		if(pml_dao == null) {
			pml_dao = new PMLoginDAO();
		} // end if
		return pml_dao;
	} // getInstance
	
	private Connection getConn() throws SQLException {
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";
		Connection con = DriverManager.getConnection(url, id, pass);
		return con;
	} //getConn
	
	public String selectAcount(PMLoginVO pmlvo) throws SQLException {
		String adminName="";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
		con = getConn();
		
		StringBuilder selectName = new StringBuilder();
		selectName
		.append(" select admin_id, admin_pass, admin_name" )
		.append(" from pc_admin where admin_id = ? and admin_pass = ?");
		
		pstmt = con.prepareStatement(selectName.toString());
		
		pstmt.setString(1, pmlvo.getId());
		pstmt.setString(2, pmlvo.getPass());
		
		rs = pstmt.executeQuery();
		if(rs.next()) {
			adminName=rs.getString("admin_name");
			System.out.println(rs.getString("admin_name"));
		} // end if
		}finally {
			
		if(rs!=null) {rs.close();}
		if(pstmt!=null) {pstmt.close();}
		if(con!=null) {con.close();}
		} // end finally
		
		return adminName;
	} // selectAcount
	
} // class
