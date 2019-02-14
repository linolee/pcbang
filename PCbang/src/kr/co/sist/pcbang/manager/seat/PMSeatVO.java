package kr.co.sist.pcbang.manager.seat;

public class PMSeatVO {
	Integer seatNum;
	String pcIP, pcStatus, user, msgStatus;
	
	public PMSeatVO(Integer seatNum, String pcIP, String pcStatus, String user, String msgStatus) {
		super();
		this.seatNum = seatNum;
		this.pcIP = pcIP;
		this.pcStatus = pcStatus;
		this.user = user;
		this.msgStatus = msgStatus;
	}
	
	@Override
	public String toString() {
		return "PMSeatVO [seatNum=" + seatNum + ", pcIP=" + pcIP + ", pcStatus=" + pcStatus + ", user=" + user
				+ ", msgStatus=" + msgStatus + "]";
	}

	public Integer getSeatNum() {
		return seatNum;
	}
	public String getPcIP() {
		return pcIP;
	}
	public String getPcStatus() {
		return pcStatus;
	}
	public String getUser() {
		return user;
	}
	public String getMsgStatus() {
		return msgStatus;
	}
	
	
}