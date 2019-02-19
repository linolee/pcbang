package kr.co.sist.pcbang.client.ordering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PUOrderingDAO {

	private static PUOrderingDAO puo_dao;
	
	public PUOrderingDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	}//PUOrderingDAO
	
	private static PUOrderingDAO getInstance() {
		if(puo_dao==null) {
			puo_dao=new PUOrderingDAO();
		}//end if
		return puo_dao;
	}//getInstance
	
	private Connection getConn() throws SQLException{
		//2.
		String url="jdbc:oracle:thin:@211.63.89.150:1521:orcl";
		String id="scott";
		String pass="tiger";
		Connection con=DriverManager.getConnection(url, id, pass);
		return con;
	}//getConn
	
	
	
}//class