package kr.co.sist.pcbang.client.message;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PUMessageDAO {

	private static PUMessageDAO pum_dao;
	
	public PUMessageDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	}//PUMainDAO
	
	public static PUMessageDAO getInstance() {
		if(pum_dao==null) {
			pum_dao=new PUMessageDAO();
		}//end if
		return pum_dao;
	}//getInstance
	
	private Connection getConn() throws SQLException{
		//2.
		String url="jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id="zizon";
		String pass="darkness";
		Connection con=DriverManager.getConnection(url, id, pass);
		return con;
	}//getConn
	
	/**
	 * PC 테이블에서 입력한 IP를 사용하는 좌석번호를 검색하여 그 좌석번호의 PC_STATUS > MESSAGE_STATUS값을 'Y'로 바꾼다. 
	 * @param ipAddr
	 * @return update가 1번 정상적으로 이루어지면 true를 반환
	 * @throws SQLException
	 */
	public boolean chgMsgStatusNtoY(String ipAddr) throws SQLException{
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConn();
			//3.
			StringBuilder updateMsgStatus = new StringBuilder();
			updateMsgStatus.append(" update pc_status set message_status = 'Y' " )
			.append(" where seat_num = (select seat_num from pc where pc_ip = ? ) ");

			pstmt = con.prepareStatement(updateMsgStatus.toString());
			//4.
			pstmt.setString(1, ipAddr);
			//5.
			int cnt = pstmt.executeUpdate();
			if(cnt == 1) {
				flag = true;
			}
		} finally {
			//6.
			if(pstmt != null) {pstmt.close();}
			if(con != null) {con.close();}
		}

		return flag;
	}
	
}//class