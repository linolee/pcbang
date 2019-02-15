package kr.co.sist.pcbang.client.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PULoginDAO {
	
	private static PULoginDAO pul_dao;

	public PULoginDAO() {
		//1.드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	}//PULoginDAO

	public static PULoginDAO getInstance() {
		if(pul_dao==null) {
			pul_dao=new PULoginDAO();
		}//end if
		return pul_dao;
	}//getInstance
	
	private Connection getConn() throws SQLException{
		//2.
		String url="jdbc:oracle:thin:@211.63.89.150:1521:orcl";
		String id="scott";
		String pass="tiger";
		Connection con=DriverManager.getConnection(url, id, pass);
		return con;
	}//getConn
	
	public String selectNotice() {
		return "공지사항";
	}//selectNotice
}//class