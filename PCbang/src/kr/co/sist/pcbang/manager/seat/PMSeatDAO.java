package kr.co.sist.pcbang.manager.seat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.pcbang.manager.seat.set.PMSeatSetLocVO;
import kr.co.sist.pcbang.manager.seat.set.PMSeatSetVO;

public class PMSeatDAO {

	private static PMSeatDAO pms_dao;

	private PMSeatDAO() {
		// 1.
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch
	}// LunchAdminDAO

	public static PMSeatDAO getInstance() {
		if (pms_dao == null) {
			pms_dao = new PMSeatDAO();
		} // end if
		return pms_dao;
	}// getInstance

	private Connection getConn() throws SQLException {
		// 2.
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";

		Connection con = DriverManager.getConnection(url, id, pass);
		return con;
	}// getConn

	public PMSeatSetVO[][] selectSeatSetInfo() throws SQLException {

		List<PMSeatSetLocVO> tempArr = new ArrayList<>();// DB�� ������ ���� Arr�� ����

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder selectPC = new StringBuilder();
		selectPC.append("	select x_Coor, y_Coor, seat_num, pc_ip, admin_id	").append("	from pc");

		try {
			con = getConn();
			pstmt = con.prepareStatement(selectPC.toString());
			rs = pstmt.executeQuery();

			PMSeatSetLocVO pmsslvo;
			while (rs.next()) {
				pmsslvo = new PMSeatSetLocVO(rs.getInt("x_Coor"), rs.getInt("y_Coor"), rs.getInt("seat_num"),
						rs.getString("pc_ip"), rs.getString("admin_id"));
				tempArr.add(pmsslvo);
			} // ���� DB�� PC ������ Arr�� ����
			PMSeatSetVO[][] seatSet;
			seatSet = new PMSeatSetVO[10][10];
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					seatSet[i][j] = new PMSeatSetVO(0, "", "");
				}
			} // �� ���� seatSet�� ����

			for (PMSeatSetLocVO tempPmsslvo : tempArr) {
				seatSet[tempPmsslvo.getxCoor()][tempPmsslvo.getyCoor()] = new PMSeatSetVO(tempPmsslvo.getSeatNum(),
						tempPmsslvo.getPcIP(), tempPmsslvo.getAdminID());
			} // Arr�� ����� ���� seatSet�� ����
				//////////////////////////////////////////////////////////////////
//			for (int i = 0; i < 10; i++) {
//				for (int j = 0; j < 10; j++) {
//					System.out.print(seatSet[i][j]);
//				}
//				System.out.println();
//			}
			////////////////////////////////////////////////////////////////
			return seatSet;
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
		}
	}

	public Integer insertSeatSetInfo(PMSeatSetVO[][] seat) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;

		StringBuilder insertPC = new StringBuilder();
		insertPC.append("	insert into pc (x_Coor, y_Coor, seat_num, pc_ip, admin_id) values	")
				.append("	(?,?,?,?,?)	");

		Integer totalInsert = 0;

		try {
			con = getConn();
			pstmt = con.prepareStatement(insertPC.toString());
			for (int i = 0; i < seat.length; i++) {
				for (int j = 0; j < seat[i].length; j++) {// ��� �¼��� ����
					if (seat[i][j].getSeatNum() != 0) {// �¼� ��ȣ�� 0�� �ƴ� ��
						pstmt.setInt(1, i);
						pstmt.setInt(2, j);
						pstmt.setInt(3, seat[i][j].getSeatNum());
						pstmt.setString(4, seat[i][j].getPcIP());
						pstmt.setString(5, seat[i][j].getAdminID());
						// �ش��ϴ� ���� �Է�
						totalInsert += pstmt.executeUpdate();// DB�� ���� �Է��ϰ� �Է��� ����� ����
					} // end if
				} // end inner for
			} // end outer for

			return totalInsert;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	public Integer deleteSeatSetInfo() throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;

		String deletePC = "	delete pc ";

		Integer totalDelete = 0;

		try {
			con = getConn();
			pstmt = con.prepareStatement(deletePC.toString());
			totalDelete = pstmt.executeUpdate();// DB�� ���� ����� ����
			return totalDelete;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	public PMSeatVO[][] selectSeatInfo() throws SQLException {

		List<PMSeatLocVO> tempArr = new ArrayList<>();// DB�� ������ ���� Arr�� ����

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder selectPC = new StringBuilder();
		selectPC.append("	select x_Coor, y_Coor, seat_num, pc_ip, pc_status, member_id, card_num	")
				.append("	from pc");

		try {
			con = getConn();
			pstmt = con.prepareStatement(selectPC.toString());
			rs = pstmt.executeQuery();

			PMSeatLocVO pmslvo;
			while (rs.next()) {
				pmslvo = new PMSeatLocVO(rs.getInt("x_Coor"), rs.getInt("y_Coor"), rs.getInt("seat_num"),
						rs.getString("pc_ip"), rs.getString("pc_status"), rs.getString("member_Id"),
						rs.getString("card_Num"));
				tempArr.add(pmslvo);
			} // ���� DB�� PC ������ Arr�� ����
			PMSeatVO[][] seat;
			seat = new PMSeatVO[10][10];
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					seat[i][j] = new PMSeatVO(0, "", "", "", "");
				}
			} // �� ���� seatSet�� ����
			String user = "";// ��ȸ������ ȸ�������� ���� seat�� �ٸ� ���� �Է��ϱ� ���� ����
			for (PMSeatLocVO tempPmslvo : tempArr) {

				if (tempPmslvo.getMemberId() != null) {// ȸ������ �ִٸ�
					user = tempPmslvo.getMemberId();
				} else if (tempPmslvo.getCardNum() != null) {// ī�� ���� �ִٸ�
					user = "guest_" + tempPmslvo.getCardNum();
				} else {// �� �� ���ٸ�
					user = "";
				}

//				seat[tempPmslvo.getxCoor()][tempPmslvo.getyCoor()] = new PMSeatVO(tempPmslvo.getSeatNum(), tempPmslvo.getPcIP(), tempPmslvo.getPcStatus(),
//						!tempPmslvo.getMemberId().equals(null)?tempPmslvo.getMemberId():tempPmslvo.getCardNum(), "N");
				seat[tempPmslvo.getxCoor()][tempPmslvo.getyCoor()] = new PMSeatVO(tempPmslvo.getSeatNum(),
						tempPmslvo.getPcIP(), tempPmslvo.getPcStatus(), user, "N");
			} // Arr�� ����� ���� seatSet�� ����
				//////////////////////////////////////////////////////////////////
//			for (int i = 0; i < 10; i++) {
//				for (int j = 0; j < 10; j++) {
//					System.out.print(seat[i][j]);
//				}
//				System.out.println();
//			}
			////////////////////////////////////////////////////////////////
			return seat;
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
		}
	}

}
