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
		selectPC.append("	select x_Coor, y_Coor, seat_num, pc_ip	").append("	from pc");

		try {
			con = getConn();
			pstmt = con.prepareStatement(selectPC.toString());
			rs = pstmt.executeQuery();

			PMSeatSetLocVO pmsslvo;
			while (rs.next()) {
				pmsslvo = new PMSeatSetLocVO(rs.getInt("x_Coor"), rs.getInt("y_Coor"), rs.getInt("seat_num"),
						rs.getString("pc_ip"));
				tempArr.add(pmsslvo);
			} // ���� DB�� PC ������ Arr�� ����
			PMSeatSetVO[][] seatSet;
			seatSet = new PMSeatSetVO[10][10];
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					seatSet[i][j] = new PMSeatSetVO(0, "");
				}
			} // �� ���� seatSet�� ����

			for (PMSeatSetLocVO tempPmsslvo : tempArr) {
				seatSet[tempPmsslvo.getxCoor()][tempPmsslvo.getyCoor()] = new PMSeatSetVO(tempPmsslvo.getSeatNum(),
						tempPmsslvo.getPcIP());
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
		PreparedStatement pstmt = null, pstmt2 = null;// pstmt = pc���̺� ������ ����, pstmt2 = pc_status ���̺� ������ ����

		StringBuilder insertPC = new StringBuilder();
		insertPC.append("	insert into pc (x_Coor, y_Coor, seat_num, pc_ip) values	").append("	(?,?,?,?)	");

		Integer totalInsert = 0;

		try {
			con = getConn();
			pstmt = con.prepareStatement(insertPC.toString());
			pstmt2 = con.prepareStatement("	insert into pc_status (seat_num) values (?)");
			for (int i = 0; i < seat.length; i++) {
				for (int j = 0; j < seat[i].length; j++) {// ��� �¼��� ����
					if (seat[i][j].getSeatNum() != 0) {// �¼� ��ȣ�� 0�� �ƴ� ��
						pstmt.setInt(1, i);
						pstmt.setInt(2, j);
						pstmt.setInt(3, seat[i][j].getSeatNum());
						pstmt.setString(4, seat[i][j].getPcIP());
						// �ش��ϴ� ���� �Է�
						totalInsert += pstmt.executeUpdate();// DB�� ���� �Է��ϰ� �Է��� ����� ����

						pstmt2 = con.prepareStatement("	insert into pc_status (seat_num) values (?)");
						pstmt2.setInt(1, seat[i][j].getSeatNum());
						pstmt2.executeUpdate();
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

		Integer totalDelete = 0;

		try {
			con = getConn();
			pstmt = con.prepareStatement("	delete pc ");
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
		selectPC.append(
				"	select x_coor, y_coor, pc.seat_num seat_num, pc_ip, member_id, card_num, pc_status, message_status, order_status	")
				.append("	from pc, pc_status pcs	").append("	where pc.seat_num = pcs.seat_num	");

		try {
			con = getConn();
			pstmt = con.prepareStatement(selectPC.toString());
			rs = pstmt.executeQuery();

			PMSeatLocVO pmslvo;
			while (rs.next()) {
				pmslvo = new PMSeatLocVO(rs.getInt("x_Coor"), rs.getInt("y_Coor"), rs.getInt("seat_num"),
						rs.getString("pc_ip"), rs.getString("member_Id"), rs.getString("card_Num"),
						rs.getString("pc_status"), rs.getString("message_status"), rs.getString("order_status"));
				tempArr.add(pmslvo);
			} // ���� DB�� PC ������ Arr�� ����
			PMSeatVO[][] seat;
			seat = new PMSeatVO[10][10];
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					seat[i][j] = new PMSeatVO(0, "", "", "", "", "");
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
						tempPmslvo.getPcIP(), user, tempPmslvo.getPcStatus(), tempPmslvo.getMessageStatus(),
						tempPmslvo.getOrderStatus());
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

	public boolean chgMsgStatusYtoN(int seatNum) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConn();
			// 3.
			StringBuilder updateMsgStatus = new StringBuilder();
			updateMsgStatus.append(" update pc_status set message_status = 'N' ").append(" where seat_num = ? ");

			pstmt = con.prepareStatement(updateMsgStatus.toString());
			// 4.
			pstmt.setInt(1, seatNum);
			// 5.
			int cnt = pstmt.executeUpdate();
			if (cnt == 1) {
				flag = true;
			}
		} finally {
			// 6.
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}

		return flag;
	}

}
