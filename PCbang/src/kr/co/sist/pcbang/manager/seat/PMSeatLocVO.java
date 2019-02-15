package kr.co.sist.pcbang.manager.seat;

public class PMSeatLocVO {
	private Integer xCoor, yCoor, seatNum;
	private String pcIP, pcStatus, memberId, cardNum, msgStatus;

	public PMSeatLocVO(Integer xCoor, Integer yCoor, Integer seatNum, String pcIP, String pcStatus, String memberId,
			String cardNum) {
		super();
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.seatNum = seatNum;
		this.pcIP = pcIP;
		this.pcStatus = pcStatus;
		this.memberId = memberId;
		this.cardNum = cardNum;
	}

	public Integer getxCoor() {
		return xCoor;
	}

	public Integer getyCoor() {
		return yCoor;
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

	public String getMemberId() {
		return memberId;
	}

	public String getCardNum() {
		return cardNum;
	}

	@Override
	public String toString() {
		return "PMSeatLocVO [xCoor=" + xCoor + ", yCoor=" + yCoor + ", seatNum=" + seatNum + ", pcIP=" + pcIP
				+ ", pcStatus=" + pcStatus + ", memberId=" + memberId + ", cardNum=" + cardNum + "]";
	}

}
