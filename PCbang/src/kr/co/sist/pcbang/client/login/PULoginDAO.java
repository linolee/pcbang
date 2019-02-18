package kr.co.sist.pcbang.client.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PULoginDAO {
	
	private static PULoginDAO pul_dao;

	public PULoginDAO() {
		//1.����̹� �ε�
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
	
	public String selectNotice() throws SQLException{
		return "��������";
	}//selectNotice

	public char memberIdStatus(String id) throws SQLException{
		return 'o';
	}
	public char guestIdStatus(int cardNum) throws SQLException{
		return 'o';
	}

	public String login(PUCertificationVO pucvo) throws SQLException {
		return "user";
	}

	public void changeMemberStatus(String memberId, String ip) throws SQLException {
	}
	
	public void changeGuestStatus(int guestNum, String ip) throws SQLException {
	}
}//class
