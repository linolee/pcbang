package kr.co.sist.pcbang.client.login.newuser.seq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PUZipcodeDAO {

	private static PUZipcodeDAO puz_dao;

	public PUZipcodeDAO() {

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch

	} // PUZipcodeDAO

	public static PUZipcodeDAO getInstance() {
		if (puz_dao == null) {
			puz_dao = new PUZipcodeDAO();
		} // end if
		return puz_dao;
	} // getInstance

	private Connection getConn() throws SQLException {
		
		String url="jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id="zizon";
		String pass="darkness";

		Connection con = DriverManager.getConnection(url, id, pass);

		return con;
	} // getConn

	//이 메소드를 주소검색 팝업창에서 검색버튼을 눌렀을 때 실행되도록 바꿔야돼
	public List<PUZipcodeVO> selectAddr(String dong) throws SQLException, NullPointerException {
		List<PUZipcodeVO> list = new ArrayList<PUZipcodeVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			
			StringBuilder selectAddr = new StringBuilder();
			selectAddr.append("select * ").append("from ZIPCODE where dong like '%").append(dong+"%'");
			// select * from zipcode where dong like '%계산%';

			pstmt = con.prepareStatement(selectAddr.toString());

			rs = pstmt.executeQuery();
			PUZipcodeVO puzvo = null;

			while (rs.next()) {
				puzvo = new PUZipcodeVO(rs.getString("zipcode"), rs.getString("sido"), rs.getString("gugun"),
						rs.getString("dong"), rs.getString("bunji"), rs.getInt("seq"));
				list.add(puzvo);
			} // end while
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

		System.out.println(list);
		return list;
	} // selectAddr

} // class
