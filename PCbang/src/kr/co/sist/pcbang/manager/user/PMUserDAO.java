package kr.co.sist.pcbang.manager.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.manager.user.detail.PMUpdateVO;

public class PMUserDAO {
	
	private static PMUserDAO u_dao;
	private PMUserView uv;
	
	public PMUserDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	} // PMUsersDAO
	
	public static PMUserDAO getInstance() {
		if(u_dao==null) {
			u_dao = new PMUserDAO();
		}
		return u_dao;
	} // getInstance
	
	private Connection getConn() throws SQLException{
		// 2.
		String url = "jdbc:oracle:thin:@211.63.89.152:1521:orcl";
		String id = "zizon";
		String pass = "darkness";

		Connection con = DriverManager.getConnection(url, id, pass);
		
		return con;
	} // getConn

	
	/**
	 * 회원정보를 불러온다
	 * @param uvo
	 * @return
	 * @throws SQLException
	 */
	public List<PMUserVO> selectUserData(String userId, String userName) throws SQLException, NullPointerException {
		
		List<PMUserVO> list = new ArrayList<PMUserVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = getConn();
			
			try {
				StringBuilder selectAllUser = new StringBuilder();
				
				selectAllUser.append(" select * from pc_member  ");
				
				// 아이디와 이름 둘 중에 하나라도 빈칸이 아니면
				if(!userId.equals("") || !userName.equals("")) {
					
					// 아이디와 이름이 둘다 빈칸이 아니라면 쿼리 추가
					if(!userId.equals("") && !userName.equals("")) {
						selectAllUser.append("where member_id=? and member_name=?");
						System.out.println("아이디와 이름 둘 다 빈칸 아님");
					}
					
					// 아이디가 빈칸이 아니면 쿼리 추가
					else if(!userId.equals("")) {
						selectAllUser.append("where member_id=? ");
						System.out.println("아이디만 빈칸아님");
					}
					
					// 아이디가 빈칸이고 이름이 빈칸이 아니면 쿼리 추가
					else if(!userName.equals("")) {
						selectAllUser.append("where member_name=? ");
						System.out.println("이름만 빈칸아님");
					}
					
					pstmt = con.prepareStatement(selectAllUser.toString());
					
					
					// 아이디와 이름 둘 중에 하나라도 빈칸이 아니면 
					if(!userId.equals("") || !userName.equals("")) {
						
						// 아이디, 이름이 둘 다 빈칸이 아니면 바인드변수 설정
						if(!userId.equals("") && !userName.equals("")) {
							pstmt.setString(1, userId);	
							pstmt.setString(2, userName);
						}
						// 아이디가 빈칸이 아니면 바인드변수 설정
						else if(!userId.equals("")) {
							pstmt.setString(1, userId);
						}
						
						// 이름이 빈칸이 아니면 바인드변수 설정
						else if(!userName.equals("")) {
							pstmt.setString(1, userName);
						}
						
					}
					
					// 아이디, 이름 둘 다 빈칸이라면	
				} 
			else {
				pstmt = con.prepareStatement(selectAllUser.toString());
//				JOptionPane.showMessageDialog(uv, "아이디 혹은 이름을 입력해주세요");
			} // end if
				
				rs = pstmt.executeQuery();
				
			} catch(SQLException se) {
				se.printStackTrace();
				JOptionPane.showMessageDialog(uv, "조회된 값이 없습니다");
			}
			
			PMUserVO usv = null;
			
			while(rs.next()) {
				usv = new PMUserVO(rs.getString("member_id"), rs.getString("member_name"), rs.getString("member_birth"),
						rs.getString("member_gender"), rs.getString("member_tel"), rs.getString("member_email"),
						rs.getString("member_detailadd"), rs.getInt("member_rest_time"),
						rs.getInt("member_total_price"), rs.getString("member_input_date")	);
				list.add(usv);
			}
			
		} finally {
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		
		return list;
	}
	
	/**
	 * @param upvo
	 * @return
	 * @throws SQLException
	 */
	public boolean updateMemberData(PMUpdateVO upvo) throws SQLException{
		boolean flag = false;
		int cnt=0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con= getConn();
			
			StringBuilder updateMember = new StringBuilder();
			updateMember.append(" update pc_member ")
			.append(" set member_email = ?, ")
			.append(" member_tel = ?, ")
			.append(" member_birth = ?, ")
			.append(" member_rest_time = ? ")
			.append(" where member_id = ? ");
			
			pstmt=con.prepareStatement(updateMember.toString());
			
			pstmt.setString(1, upvo.getEmail());
			pstmt.setString(2, upvo.getTel());
			pstmt.setString(3, upvo.getBirth());
			pstmt.setInt(4, upvo.getLeftTime());
			pstmt.setString(5, upvo.getId());

			cnt = pstmt.executeUpdate();
			
			if(cnt == 1) {
				flag = true;
			}
			
		} finally {
			if(pstmt!=null) {pstmt.close();}
			if(con!=null) {con.close();}
		}
		
		return flag;
	}
	
}
