package kr.co.sist.pcbang.client.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PULoginDAO {
private static PULoginDAO pul_dao;
	
	private PULoginDAO() {
		//1. 
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
	}//LunchClientDAO
	
	public static PULoginDAO getInstance() {
		if(pul_dao == null) {
			pul_dao=new PULoginDAO();
		}//end if
		return pul_dao;
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
	 * PC占쎈퓠占쎄퐣 id�몴占� 占쎌뵠沃섓옙 占쎄텢占쎌뒠餓λ쵐�뵥筌욑옙 占쎈솇占쎈뼊
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean selectMember(String id) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String userStatus="";
		boolean userFlag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select member_id ").append(" from pc ")
			.append(" where member_id='").append(id).append("'");
			
			pstmt=con.prepareStatement(status.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("member_id");
			}//end if
			if(!userStatus.equals("")) {
				userFlag=true;
			}//end if
			
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return userFlag;
	}//selectMember
	
	/**
	 * PC占쎈퓠占쎄퐣 燁삳�諭띈린�뜇�깈�몴占� 占쎌뵠沃섓옙 占쎄텢占쎌뒠餓λ쵐�뵥筌욑옙 占쎈솇占쎈뼊
	 * @param cardNum
	 * @return
	 * @throws SQLException
	 */
	public boolean selectGuest(int cardNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String userStatus="";
		boolean userFlag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select card_num ").append(" from pc ")
			.append(" where card_num=").append(cardNum);
			
			pstmt=con.prepareStatement(status.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("card_num");
			}//end if
			if(!userStatus.equals("")) {
				userFlag=true;
			}//end if
			
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return userFlag;
	}//selectGuest
	
	/**
	 * 占쎌돳占쎌뜚占쎌벥 占쎈툡占쎌뵠占쎈탵揶쏉옙 占쎌겱占쎌삺 PC占쎈퓠占쎄퐣 占쎌쁽�뵳�딆뵠占쎈짗 占쎈쾻 占쎌벥 占쎄맒占쎄묶揶쏉옙 揶쏉옙占쎈뮟占쎈립筌욑옙 占쎈연�겫占�
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String selectMemberIdStatus(String id) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String userStatus="";
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select pc_status ").append(" from pc_status ")
			.append(" where seat_num=(select seat_num from pc where member_id='").append(id).append("')");
			
			pstmt=con.prepareStatement(status.toString());
		//4.
		//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("pc_status");
			}//end if
			
		}finally {
		//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return userStatus;
	}//memberIdStatus
	
	/**
	 * �뜮袁れ돳占쎌뜚占쎌벥 燁삳�諭뜹첎占� 占쎌겱占쎌삺 PC占쎈퓠占쎄퐣 占쎌쁽�뵳�딆뵠占쎈짗 占쎈쾻占쎌벥 占쎄맒占쎄묶揶쏉옙 揶쏉옙占쎈뮟占쎈립筌욑옙 占쎈연�겫占�
	 * @param cardNum
	 * @return
	 * @throws SQLException
	 */
	public String selectGuestIdStatus(int cardNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String userStatus="";
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder status=new StringBuilder();
			
			status.append("select pc_status ").append(" from pc_status ")
			.append(" where seat_num=(select seat_num from pc where card_num=").append(cardNum).append(")");
			
			pstmt=con.prepareStatement(status.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				userStatus=rs.getString("pc_status");
			}//end if
			
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return userStatus;
	}//guestIdStatus
	
	/**
	 * 占쎌돳占쎌뜚占쎌벥 占쎈툡占쎌뵠占쎈탵占쏙옙 �뜮袁⑨옙甕곕뜇�깈揶쏉옙 筌띿쉶�뼄筌롳옙 占쎄텚占쏙옙占쎈뻻揶쏉옙 占쎌젟癰귣�占쏙옙 揶쏉옙占쎌죬占쎌궎占쎈뮉 占쎌뵬
	 * @param pucvo
	 * @return
	 * @throws SQLException
	 */
	public int selectMemberLogin(PUCertificationVO pucvo) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int restTime=-1;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder login=new StringBuilder();
			
			login.append("select member_rest_time ").append(" from pc_member ")
			.append(" where member_id=? and member_pass=?");
			
			pstmt=con.prepareStatement(login.toString());
			//4.
			pstmt.setString(1, pucvo.getMemberId());
			pstmt.setString(2, pucvo.getMemberPass());
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				restTime=rs.getInt("member_rest_time");
			}//end if
			System.out.println(restTime);
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return restTime;
	}//memberLogin
	
	/**
	 * �뜮袁れ돳占쎌뜚占쎌벥 燁삳�諭띈린�뜇�깈揶쏉옙 筌띿쉶�뮉筌욑옙 占쎈솇占쎈뼊
	 * @param cardNum
	 * @return
	 * @throws SQLException
	 */
	public boolean selectGuestCheck(int cardNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int num=-1;
		boolean flag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder check=new StringBuilder();
			
			check.append("select card_num ").append(" from pc_guest ")
			.append(" where card_num=").append(cardNum);
			
			pstmt=con.prepareStatement(check.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				num=rs.getInt("card_num");
			}//end if
			if(num!=-1) {
				flag=true;
			}//end if
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		return flag;
	}//guestCheck
	
	/**
	 * 占쎌돳占쎌뜚占쎌뵠 嚥≪뮄�젃占쎌뵥 占쎈뻥占쎌뱽 占쎈르 嚥≪뮄�젃占쎌뵥占쎈립 PC占쎌벥 占쎄맒占쎄묶癰귨옙野껓옙
	 * @param pumsvo
	 * @throws SQLException
	 */
	public boolean updateMemberState(PUMemberStateVO pumsvo)throws SQLException {
		Connection con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		boolean flag=false;
		
		try {
		//1.
		//2.
			con=getConn();
		//3.
			String updatePc="update pc set member_id=? where pc_ip=?";
			pstmt1=con.prepareStatement(updatePc);
		//4.
			pstmt1.setString(1, pumsvo.getMemberId());
			pstmt1.setString(2, pumsvo.getPcIp());
		//5.
			int cnt1 = pstmt1.executeUpdate();
		//3.
			String updateStatus
			="update pc_status set pc_status='Y' where seat_num=(select seat_num from pc where pc_ip=?)";
			pstmt2=con.prepareStatement(updateStatus);
		//4.
			pstmt2.setString(1, pumsvo.getMemberId());
			pstmt2.setString(2, pumsvo.getPcIp());
		//5.
			int cnt2 = pstmt2.executeUpdate();
			
			if( cnt1==1 && cnt2 ==1 ) {
				flag=true;
			}//end if
		}finally {
		//6.
			if( pstmt2 != null ) { pstmt2.close(); }//end if
			if( pstmt1 != null ) { pstmt1.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return flag;
	}//changeMemberState
	
	/**
	 * �뜮袁れ돳占쎌뜚占쎌뵠 嚥≪뮄�젃占쎌뵥 占쎈뻥占쎌뱽 占쎈르 嚥≪뮄�젃占쎌뵥占쎈립 PC占쎌벥 占쎄맒占쎄묶癰귨옙野껓옙
	 * @param pugsvo
	 * @return
	 * @throws SQLException
	 */
	public boolean updateGuestState(PUGuestStateVO pugsvo)throws SQLException {
		Connection con=null;
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;
		boolean flag=false;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			String updatePc="update pc set card_num=? where pc_ip=?";
			pstmt1=con.prepareStatement(updatePc);
		//4.
			pstmt1.setInt(1, pugsvo.getCardNum());
			pstmt1.setString(2, pugsvo.getPcIp());
		//5.
			int cnt1 = pstmt1.executeUpdate();
		//3.
			String updateStatus
			="update pc_status set pc_status='Y' where seat_num=(select seat_num from pc where pc_ip=?)";
			pstmt2=con.prepareStatement(updateStatus);
		//4.
			pstmt2.setInt(1, pugsvo.getCardNum());
			pstmt2.setString(2, pugsvo.getPcIp());
		//5.
			int cnt2 = pstmt2.executeUpdate();
			
			if( cnt1==1 && cnt2 ==1 ) {
				flag=true;
			}//end if
		}finally {
			//6.
			if( pstmt2 != null ) { pstmt2.close(); }//end if
			if( pstmt1 != null ) { pstmt1.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		
		return flag;
	}//changeGuestState
	
	/**
	 * �꽴占썹뵳�딆쁽占쎌벥 �⑤벊占쏙옙沅쀯옙鍮�
	 * @return
	 * @throws SQLException
	 */
	public String selectNotice() throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String notice="";
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder note=new StringBuilder();
			
			note.append("select admin_notice ")
			.append(" from (SELECT ADMIN_NOTICE FROM PC_NOTICE ORDER BY NOTICE_INPUT_DATE DESC) ")
			.append(" WHERE ROWNUM=1 ");
			
			pstmt=con.prepareStatement(note.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				notice=rs.getString("ADMIN_NOTICE");
			}//end if
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		return notice;
	}//guestCheck
	
	public int selectMemberSeatNum(String id) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int seatNum=0;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder number=new StringBuilder();
			
			number.append("select seat_num ")
			.append(" from pc ")
			.append(" WHERE member_id= '")
			.append(id)
			.append("'");
			
			pstmt=con.prepareStatement(number.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				seatNum=rs.getInt("seat_num");
			}//end if
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		return seatNum;
	}//guestCheck
	
	public int selectGuestSeatNum(int cardNum) throws SQLException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int seatNum=0;
		
		try {
			//1.
			//2.
			con=getConn();
			//3.
			StringBuilder number=new StringBuilder();
			
			number.append("select seat_num ")
			.append(" from pc ")
			.append(" WHERE card_num= ")
			.append(cardNum);
			
			pstmt=con.prepareStatement(number.toString());
			//4.
			//5.
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				seatNum=rs.getInt("seat_num");
			}//end if
		}finally {
			//6.
			if( rs != null ) { rs.close(); }//end if
			if( pstmt != null ) { pstmt.close(); }//end if
			if( con != null ) { con.close(); }//end if
		}//end finally
		return seatNum;
	}//guestCheck
	
}//class
