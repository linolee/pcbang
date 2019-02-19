package kr.co.sist.pcbang.manager.seat.set;

public class PMSeatSetVO {

	private Integer seatNum;
	private String pcIP;
	
	public PMSeatSetVO(Integer seatNum, String pcIP) {
		super();
		this.seatNum = seatNum;
		this.pcIP = pcIP;
	}
	
	@Override
	public String toString() {
		return "["+seatNum + "/" + pcIP + "]";
	}

	public Integer getSeatNum() {
		return seatNum;
	}
	public String getPcIP() {
		return pcIP;
	}
}