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
			String selectLunch="SELECT MENU_CODE, MENU_NAME, IMG, MENU_PRICE FROM MENU ORDER BY  CATEGORY,MENU_CODE";
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
	
	/**
	 * 라면 카테고리 조회
	 * @return	 
	 * @throws SQLException
	 */
	public List<PUOrderVO> selectProductCategory(String category) throws SQLException{
		List<PUOrderVO> list=new ArrayList<PUOrderVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
//		if(category.equals("라면")){
//		}else if(category.equals("음료")) {
//		}else if(category.equals("스낵")) {
//		}
		
		try {
			con=getConn();
			String selectLunch="SELECT MENU_CODE, MENU_NAME, IMG, MENU_PRICE FROM (select MENU_CODE, MENU_NAME, IMG, MENU_PRICE " + 
								"from MENU where CATEGORY=?) ORDER BY MENU_CODE";
			pstmt=con.prepareStatement(selectLunch);
			pstmt.setString(1, category);
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
	}//selectProductRamen
	
	
	/**
	 * 베스트메뉴 앉히기
	 * @return	 
	 * @throws SQLException
	 */
	public List<PUOrderVO> selectProductBest() throws SQLException{
		List<PUOrderVO> list=new ArrayList<PUOrderVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			con=getConn();
			String selectLunch="select MENU_CODE, MENU_NAME, MENU_PRICE, IMG ,\r\n" + 
					"nvl((select  sum(o.quan)\r\n" + 
					"from ORDERING2 o, menu m1\r\n" + 
					"where (m1.menu_code=o.menu_code) and m1.menu_code = m2.menu_code\r\n" + 
					"group by  m1.MENU_CODE)* MENU_PRICE ,0) as total\r\n" + 
					"from menu m2\r\n" + 
					"order by total desc";
			pstmt=con.prepareStatement(selectLunch);
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
	}//selectProductRamen

	/**
	 * 이미지로 찾기(베스트->주문 목록)
	 * @param p_code
	 * @return
	 * @throws SQLException
	 */
	public PUOrderVO selectCode(String img) throws SQLException{
		PUOrderVO pouvo=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			String selectPro="SELECT MENU_NAME,MENU_PRICE,IMG,MENU_CODE FROM MENU WHERE IMG=? AND Rownum<=1";
			pstmt=con.prepareStatement(selectPro);
		//4.
			pstmt.setString(1, img);
		//5.
			rs=pstmt.executeQuery();
			if(rs.next()) {
				pouvo=new PUOrderVO(img,rs.getString("MENU_CODE"),rs.getString("MENU_NAME"),
										rs.getString("MENU_PRICE"));
			}//end if
		}finally {
			//6.
			if(rs!=null) {rs.close();}//end if
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally
		
		return pouvo;
	}//selectCode
	
	//insert into ORDERING2(ORDER_NUM, QUAN, ORDER_DATE, STATUS, MENU_CODE, SEAT_NUM) 
	//values(ORDER_CODE(),5,sysdate,'N','M_000022',100);
	public void InsertOrder(List<PUOrderAddVO> list, int seatNum) throws SQLException{
		//List<PUOrderAddVO> list=new ArrayList<PUOrderAddVO>();
		PUOrderAddVO puoadd=null;
		
		Connection con =null;
		PreparedStatement pstmt=null;
		try {
		//1.
		//2.
			con=getConn();
		//3.
			for(int i=0; i<list.size(); i++) {
				StringBuilder insertOrder=new StringBuilder();
				insertOrder.append("insert into ORDERING2(ORDER_NUM, QUAN, ORDER_DATE, STATUS, MENU_CODE, SEAT_NUM)")
				.append("values(ORDER_CODE(),?,sysdate,'N',?,?)");
				pstmt=con.prepareStatement(insertOrder.toString());
			//4.바인드변수 값넣기
				puoadd=list.get(i);
				//System.out.println(puoadd.getProductCode()+"/"+puoadd.getProductQuan()+"/"+seatNum);
				pstmt.setInt(1, puoadd.getProductQuan());
				pstmt.setString(2, puoadd.getProductCode());
				pstmt.setInt(3, seatNum);
			//5.
				pstmt.executeUpdate();
			}//end for
				
		}finally {
			//6.
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally
	
	}//InsertOrder
	
	public void UpdatePcStatus(int seatNum) throws SQLException{
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=getConn();
		//3.
			StringBuilder updateSt=new StringBuilder();
			updateSt.append("UPDATE PC_STATUS SET ORDER_STATUS='Y' WHERE SEAT_NUM=?");
			pstmt=con.prepareStatement(updateSt.toString());
		//4.바인드변수 값넣기
			pstmt.setString(1, String.valueOf(seatNum));
		//5.
			pstmt.executeUpdate();
		}finally {
			//6.
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally
		
	}//UpdatePcStatus
	
}//class
