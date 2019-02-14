package kr.co.sist.pcbang.manager.seat.set;

public class PMSeatSetVO {

	Integer seatNum;
	String pcIP, adminID;
	
	public PMSeatSetVO(Integer seatNum, String pcIP, String adminID) {
		super();
		this.seatNum = seatNum;
		this.pcIP = pcIP;
		this.adminID = adminID;
	}
	
	@Override
	public String toString() {
		return "["+seatNum + "/" + pcIP + "/" + adminID+"]";
	}

	public Integer getSeatNum() {
		return seatNum;
	}
	public String getPcIP() {
		return pcIP;
	}
	public String getAdminID() {
		return adminID;
	}
}