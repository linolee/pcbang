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
	 * ȸ�������� �ҷ��´�
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
				
				// ���̵�� �̸� �� �߿� �ϳ��� ��ĭ�� �ƴϸ�
				if(!userId.equals("") || !userName.equals("")) {
					
					// ���̵�� �̸��� �Ѵ� ��ĭ�� �ƴ϶�� ���� �߰�
					if(!userId.equals("") && !userName.equals("")) {
						selectAllUser.append("where member_id=? and member_name=?");
						System.out.println("���̵�� �̸� �� �� ��ĭ �ƴ�");
					}
					
					// ���̵� ��ĭ�� �ƴϸ� ���� �߰�
					else if(!userId.equals("")) {
						selectAllUser.append("where member_id=? ");
						System.out.println("���̵� ��ĭ�ƴ�");
					}
					
					// ���̵� ��ĭ�̰� �̸��� ��ĭ�� �ƴϸ� ���� �߰�
					else if(!userName.equals("")) {
						selectAllUser.append("where member_name=? ");
						System.out.println("�̸��� ��ĭ�ƴ�");
					}
					
					pstmt = con.prepareStatement(selectAllUser.toString());
					
					
					// ���̵�� �̸� �� �߿� �ϳ��� ��ĭ�� �ƴϸ� 
					if(!userId.equals("") || !userName.equals("")) {
						
						// ���̵�, �̸��� �� �� ��ĭ�� �ƴϸ� ���ε庯�� ����
						if(!userId.equals("") && !userName.equals("")) {
							pstmt.setString(1, userId);	
							pstmt.setString(2, userName);
						}
						// ���̵� ��ĭ�� �ƴϸ� ���ε庯�� ����
						else if(!userId.equals("")) {
							pstmt.setString(1, userId);
						}
						
						// �̸��� ��ĭ�� �ƴϸ� ���ε庯�� ����
						else if(!userName.equals("")) {
							pstmt.setString(1, userName);
						}
						
					}
					
					// ���̵�, �̸� �� �� ��ĭ�̶��	
				} 
			else {
				pstmt = con.prepareStatement(selectAllUser.toString());
//				JOptionPane.showMessageDialog(uv, "���̵� Ȥ�� �̸��� �Է����ּ���");
			} // end if
				
				rs = pstmt.executeQuery();
				
			} catch(SQLException se) {
				se.printStackTrace();
				JOptionPane.showMessageDialog(uv, "��ȸ�� ���� �����ϴ�");
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
