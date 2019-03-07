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

		List<PMSeatSetLocVO> tempArr = new ArrayList<>();// DB의 정보를 담을 Arr를 생성

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
			} // 현재 DB의 PC 정보를 Arr에 저장
			PMSeatSetVO[][] seatSet;
			seatSet = new PMSeatSetVO[10][10];
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					seatSet[i][j] = new PMSeatSetVO(0, "");
				}
			} // 빈 값을 seatSet에 저장

			for (PMSeatSetLocVO tempPmsslvo : tempArr) {
				seatSet[tempPmsslvo.getxCoor()][tempPmsslvo.getyCoor()] = new PMSeatSetVO(tempPmsslvo.getSeatNum(),
						tempPmsslvo.getPcIP());
			} // Arr에 저장된 값을 seatSet에 저장
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
		PreparedStatement pstmt = null, pstmt2 = null;// pstmt = pc테이블에 정보를 삽입, pstmt2 = pc_status 테이블에 정보를 삽입

		StringBuilder insertPC = new StringBuilder();
		insertPC.append("	insert into pc (x_Coor, y_Coor, seat_num, pc_ip) values	").append("	(?,?,?,?)	");

		Integer totalInsert = 0;

		try {
			con = getConn();
			pstmt = con.prepareStatement(insertPC.toString());
			pstmt2 = con.prepareStatement("	insert into pc_status (seat_num) values (?)");
			for (int i = 0; i < seat.length; i++) {
				for (int j = 0; j < seat[i].length; j++) {// 모든 좌석에 대해
					if (seat[i][j].getSeatNum() != 0) {// 좌석 번호가 0이 아닐 때
						pstmt.setInt(1, i);
						pstmt.setInt(2, j);
						pstmt.setInt(3, seat[i][j].getSeatNum());
						pstmt.setString(4, seat[i][j].getPcIP());
						// 해당하는 값을 입력
						totalInsert += pstmt.executeUpdate();// DB에 값을 입력하고 입력한 행수를 저장

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
			totalDelete = pstmt.executeUpdate();// DB에 지운 행수를 저장
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

		List<PMSeatLocVO> tempArr = new ArrayList<>();// DB의 정보를 담을 Arr를 생성

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
			} // 현재 DB의 PC 정보를 Arr에 저장
			PMSeatVO[][] seat;
			seat = new PMSeatVO[10][10];
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					seat[i][j] = new PMSeatVO(0, "", "", "", "", "");
				}
			} // 빈 값을 seatSet에 저장
			String user = "";// 비회원인지 회원인지에 따라 seat에 다른 값을 입력하기 위한 변수
			for (PMSeatLocVO tempPmslvo : tempArr) {

				if (tempPmslvo.getMemberId() != null) {// 회원값이 있다면
					user = tempPmslvo.getMemberId();
				} else if (tempPmslvo.getCardNum() != null) {// 카드 값이 있다면
					user = "guest_" + tempPmslvo.getCardNum();
				} else {// 둘 다 없다면
					user = "";
				}

//				seat[tempPmslvo.getxCoor()][tempPmslvo.getyCoor()] = new PMSeatVO(tempPmslvo.getSeatNum(), tempPmslvo.getPcIP(), tempPmslvo.getPcStatus(),
//						!tempPmslvo.getMemberId().equals(null)?tempPmslvo.getMemberId():tempPmslvo.getCardNum(), "N");
				seat[tempPmslvo.getxCoor()][tempPmslvo.getyCoor()] = new PMSeatVO(tempPmslvo.getSeatNum(),
						tempPmslvo.getPcIP(), user, tempPmslvo.getPcStatus(), tempPmslvo.getMessageStatus(),
						tempPmslvo.getOrderStatus());
			} // Arr에 저장된 값을 seatSet에 저장
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
