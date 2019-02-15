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
		// ����̺� �ε��� �����ڿ���
		// DB 1. ����̺� �ε�
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch
	}// PMProductDAO

	public static PMProductDAO getInstance() {
		if (pmp_dao == null) {// ����ȣ�� �Ǵ� ��ü�� �׾�����
			// ������ la_dao�� ��ȯ�Ѵ�.
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
	 * �Էµ� ��ǰ�� �ڵ�, �̸�, �̹���, ����, �Ǹŷ�, ���Ǹŷ� ��ȸ
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
	 * �ԷµǴ� �ڵ忡 ���� ǰ���� �������� ��ȸ
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public PMProductDetailVO selectDetailPrd(String code) throws SQLException {
		PMProductDetailVO pmpdvo = null; // ����ٰ� new�� �ϸ� ��ȸ�����ʾƵ� ��ü�� ����
		// �ʿ��Ѱ� �ƴϸ� new�� ������ ���ƾ� �Ѵ�.

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
			// �Էµ� �ڵ�� ��ȸ�� ���ڵ尡 ������ �� VO�� �����ϰ� ���� �߰��Ѵ�.
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

	
	/////////////////////////////////2�� 14�� ����////
	/**
	 * ��ǰ ������ �߰��ϴ� ��
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

			// 4. ���ε� ������ ���ֱ�
			pstmt.setString(1, pmpav.getCategory());
			pstmt.setString(2, pmpav.getMenuName());
			pstmt.setInt(3, pmpav.getPrice());
			pstmt.setString(4, pmpav.getImg());
			pstmt.setString(6, PMProductView.adminId);
			// 5.
			pstmt.executeUpdate(); // insert�ǰų� �����̰ų� �� �� �ϳ�
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
		// DB�� ������ ��� �ؼ� ������ DAO ������
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
	 * ���ö� �ڵ�, ���ö���, �̹���, ����, Ư������ �Է¹޾� ���ö��ڵ忡 �ش��ϴ� ���ö��� ����. �̹����� ""��� �̹����� ��������
	 * �ʴ´�.
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

			int index = 4; // index�� 3�� ������
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
