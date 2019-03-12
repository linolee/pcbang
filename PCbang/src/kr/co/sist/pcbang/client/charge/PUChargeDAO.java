package kr.co.sist.pcbang.client.charge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class PUChargeDAO {

	private static PUChargeDAO puc_dao;
	
	private PUChargeDAO() {
		//1. 
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	}//PUChargeDAO
	
	public static PUChargeDAO getInstance() {
		if(puc_dao == null) {
			puc_dao=new PUChargeDAO();
		}//end if
		return puc_dao;
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
	
	public boolean selectCheckMember(int seatNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean member=false;
		String id="";
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			StringBuilder check=new StringBuilder();
			
			check.append("select member_id ").append(" from pc ")
			.append(" where seat_num=").append(seatNum);
			
			pstmt=con.prepareStatement(check.toString());
		//4.
		//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				id=rs.getString("member_id");
			}//end if
			
			if(!(id==null)) {
				member=true;
			}//end if
			
		}finally {
		//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return member;
	}//checkMember
	
	public void memberUpdate(MemberPriceUpdateVO mpuvo, int time, int price) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder check=new StringBuilder();
			
			check.append("update pc_member ")
			.append(" set member_rest_time=(member_rest_time+").append(time)
			.append("),").append("member_total_price=(member_total_price+").append(price).append(")")
			.append(" where member_id=?");
			
			pstmt=con.prepareStatement(check.toString());
			pstmt.setString(1, mpuvo.getId());
			pstmt.executeUpdate();
			//4.
			//5.
		}finally {
			//6.
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
	}//checkMember
	
	public void guestUpdate(GuestPriceUpdateVO gpuvo, int time, int price) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder check=new StringBuilder();
			
			check.append("update pc_guest ")
			.append(" set guest_total_time=(guest_total_time+").append(time)
			.append("),").append("guest_total_price=(guest_total_price+").append(price).append(")")
			.append(" where card_num=?");
			
			pstmt=con.prepareStatement(check.toString());
			pstmt.setInt(1, gpuvo.getCardNum());
			pstmt.executeUpdate();
			//4.
			//5.
		}finally {
			//6.
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
	}//checkMember
	
	public String selectMemberId(int seatNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String userId="";
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder check=new StringBuilder();
			
			check.append("select member_id ")
			.append(" from pc ")
			.append("where seat_num=").append(seatNum);
			
			pstmt=con.prepareStatement(check.toString());
			rs=pstmt.executeQuery();
			if(rs.next()) {
				userId=rs.getString("member_id");
			}//end if
			
			//4.
			//5.
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return userId;
	}//selectInform
	
	public int selectGuestNum(int seatNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int cardNum=0;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder check=new StringBuilder();
			
			check.append("select card_num ")
			.append(" from pc ")
			.append("where seat_num=").append(seatNum);
			
			pstmt=con.prepareStatement(check.toString());
			rs=pstmt.executeQuery();
			if(rs.next()) {
				cardNum=rs.getInt("card_num");
			}//end if
			
			//4.
			//5.
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return cardNum;
	}//selectInform
	
	public List<PriceVO> price(boolean member) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		PriceVO pvo=null;
		List<PriceVO> list = new ArrayList<PriceVO>();
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder check=new StringBuilder();
			
			if(member) {
			check.append("select charge_time, member_price")
			.append(" from pc_price ");
			}//end if
			if(!member) {
				check.append("select charge_time, guest_price ")
				.append(" from pc_price ");
			}//end if
			pstmt=con.prepareStatement(check.toString());
			rs=pstmt.executeQuery();
			
			if(member) {
				while(rs.next()) {
					pvo=new PriceVO(rs.getInt("charge_time"), rs.getInt("member_price"));
					list.add(pvo);
				}//end if
			}
			if(!member) {
				while(rs.next()) {
					pvo=new PriceVO(rs.getInt("charge_time"), rs.getInt("guest_price"));
					list.add(pvo);
				}//end if
			}
			
			//4.
			//5.
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
	
		return list;
	}//selectInform
	
	public boolean updateMemberMileage(MemberMileageVO mmvo)throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		boolean flag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			String updateMileage="update pc_member set member_mileage=nvl(member_mileage,0)+?*0.1 where member_id=?";
			pstmt=con.prepareStatement(updateMileage);
		//4.
			pstmt.setInt(1, mmvo.getMemebrMileage());
			pstmt.setString(2, mmvo.getMemberId());
		//5.
			int cnt1 = pstmt.executeUpdate();
			
			if( cnt1==1 ) {
				flag=true;
			}//end if
		}finally {
			//6.
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return flag;
	}//changeGuestState
	
}

