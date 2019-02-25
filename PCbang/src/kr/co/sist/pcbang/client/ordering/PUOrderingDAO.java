package kr.co.sist.pcbang.client.ordering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PUOrderingDAO {

	private static PUOrderingDAO puo_dao;
	
	public PUOrderingDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	}//PUOrderingDAO
	
	public static PUOrderingDAO getInstance() {
		if(puo_dao==null) {
			puo_dao=new PUOrderingDAO();
		}//end if
		return puo_dao;
	}//getInstance
	
	private Connection getConn() throws SQLException{
		//2.
		Connection con=null;
		String url="jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id="zizon";
		String pass="darkness";
		con=DriverManager.getConnection(url, id, pass );
		return con;
	}//getConn
	
	/**
	 * 상품 목록 조회->테이블에 셋팅
	 * @return	 
	 * @throws SQLException
	 */
	public List<PUOrderVO> selectProduct() throws SQLException{
		List<PUOrderVO> list=new ArrayList<PUOrderVO>();

		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			String selectLunch="SELECT MENU_CODE, MENU_NAME, IMG, MENU_PRICE FROM MENU ORDER BY MENU_CODE DESC";
			pstmt=con.prepareStatement(selectLunch);
		//4.
		//5.
			rs=pstmt.executeQuery();
			PUOrderVO puovo=null;
			
			while(rs.next()){
				puovo=new PUOrderVO(rs.getString("IMG"), rs.getString("MENU_CODE"), rs.getString("MENU_NAME"), rs.getString("MENU_PRICE"));
				list.add(puovo);
			}//end while
		}finally {
			//6.
			if(rs!=null) {rs.close();}//end if
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally
		
		return list;
	}//selectProduct
	
}//class
