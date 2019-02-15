package kr.co.sist.pcbang.manager.product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import admin.view.PMProductAddView;
import admin.view.PMProductView;
import admin.vo.PMProductAddVO;
import admin.vo.PMProductDetailVO;
import admin.vo.PMProductVO;

public class PMProductDAO {

	private static PMProductDAO pmp_dao;

	private PMProductDAO() {
		// 드라이브 로딩은 생성자에서
		// DB 1. 드라이브 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch
	}// PMProductDAO

	public static PMProductDAO getInstance() {
		if (pmp_dao == null) {// 최초호출 또는 객체가 죽었을때
			// 생성된 la_dao를 반환한다.
			pmp_dao = new PMProductDAO();
		} // end if
		return pmp_dao;
	}// getInstance

	private Connection getConn() throws SQLException {
		// DB 2.
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";
		Connection con = DriverManager.getConnection(url, id, pass);
		return con;
	}// getConn

	/**
	 * 입력된 상품의 코드, 이름, 이미지, 가격, 판매량, 총판매량 조회
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<PMProductVO> selectPrd() throws SQLException {
		List<PMProductVO> list = new ArrayList<PMProductVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			StringBuilder selectAllPrd = new StringBuilder();
			selectAllPrd.append(
					"	select m.menu_code, m.menu_name, m.img, m.menu_price, o.quan, (m.menu_price)*(o.quan) total ")
					.append("	from menu m, ordering o ").append("	where o.menu_code=m.menu_code ")
					.append("	order by m.menu_code desc ");

			pstmt = con.prepareStatement(selectAllPrd.toString());
			// 4.
			// 5.
			rs = pstmt.executeQuery();

			PMProductVO pmp_vo = null;
			while (rs.next()) {
				pmp_vo = new PMProductVO(rs.getString("MENU_CODE"), rs.getString("MENU_NAME"), rs.getString("IMG"),
						rs.getInt("menu_price"), rs.getInt("QUAN"), rs.getInt("total"));
				list.add(pmp_vo);
			} // end while
		} finally {
			if (rs != null) {
				rs.close();
			} // end if
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // finally

		return list;
	}// selectPrd

	/**
	 * 입력되는 코드에 의한 품목의 상세정보를 조회
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public PMProductDetailVO selectDetailPrd(String code) throws SQLException {
		PMProductDetailVO pmpdvo = null; // 여기다가 new로 하면 조회되지않아도 객체를 만듬
		// 필요한게 아니면 new로 만들지 말아야 한다.

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			String selectMenuCode = "select lunch_name, img, price, category from menu where menu_code=?";
			pstmt = con.prepareStatement(selectMenuCode);
			// 4.
			pstmt.setString(1, code);
			// 5.
			rs = pstmt.executeQuery();
			// 입력된 코드로 조회된 레코드가 존재할 때 VO를 생성하고 값을 추가한다.
			if (rs.next()) {
				pmpdvo = new PMProductDetailVO(rs.getString("menu_Name"), rs.getString("category"), rs.getString("img"), rs.getInt("price"));
			} // end if
		} finally {
			// 6.
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

		return pmpdvo;
	}// selectDetailPrd

	
	/////////////////////////////////2월 14일 할일////
	/**
	 * 상품 정보를 추가하는 일
	 * 
	 * @param pmpav
	 * @throws SQLException
	 */
	public void insertPrd(PMProductAddVO pmpav) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			StringBuilder insertPrd = new StringBuilder();
			insertPrd.append("insert into menu")
			.append("(menu_CODE, category, menu_NAME, menu_price, img, menu_input_date, admin_id)")
			.append("values(lunch_code, ?,?,?,?,?,? )");
			pstmt = con.prepareStatement(insertPrd.toString());

			// 4. 바인드 변수에 값넣기
			pstmt.setString(1, pmpav.getCategory());
			pstmt.setString(2, pmpav.getMenuName());
			pstmt.setInt(3, pmpav.getPrice());
			pstmt.setString(4, pmpav.getImg());
			pstmt.setString(6, PMProductView.adminId);
			// 5.
			pstmt.executeUpdate(); // insert되거나 예외이거나 둘 중 하나
		} finally {
			// 6.
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally

	}// insertLunch

	public boolean deleteLunch(String code) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		// DB를 여러번 써야 해서 위에서 DAO 선언함
		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			String deleteLunch = "DELETE FROM LUNCH WHERE LUNCH_CODE = ? ";
			pstmt = con.prepareStatement(deleteLunch);
			// 4.
			pstmt.setString(1, code);
			// 5.
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				flag = true;
			} // end if

		} finally {
			// 6.
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} // end finally
		return flag;
	}// deleteLunch

	/**
	 * 도시락 코드, 도시락명, 이미지, 가격, 특장점을 입력받아 도시락코드에 해당하는 도시락을 변경. 이미지가 ""라면 이미지는 변경하지
	 * 않는다.
	 * 
	 * @param luvo
	 * @return
	 * @throws SQLException
	 */
	public boolean updateLunch(LunchUpdateVO luvo) throws SQLException {
		boolean flag = false;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.
			// 2.
			con = getConn();
			// 3.
			StringBuilder updateLunch = new StringBuilder();
			updateLunch.append(" update Lunch ").append(" set lunch_name=?, ").append(" 	price=?, spec=? ");

			if (!luvo.getImg().equals("")) {
				updateLunch.append(", img=? ");
			} // end if
			updateLunch.append("where lunch_code=?");
			pstmt = con.prepareStatement(updateLunch.toString());
			// 4.
			pstmt.setString(1, luvo.getLunch_name());
			pstmt.setInt(2, luvo.getPrice());
			pstmt.setString(3, luvo.getSpec());

			int index = 4; // index에 3을 넣으면
			if (!luvo.getImg().equals("")) {
				pstmt.setString(index++, luvo.getImg()); // ++index
			} // end if
			pstmt.setString(index, luvo.getLunch_code());

			// 5.
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				flag = true;
			} // end if
		} finally {
			// 6.
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // end finally

		return flag;
	}// updateLunch

}// class
