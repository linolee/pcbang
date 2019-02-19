package kr.co.sist.pcbang.manager.seat;

public class PMSeatLocVO {
	private Integer xCoor, yCoor, seatNum;
	private String pcIP, memberId, cardNum, pcStatus, messageStatus, orderStatus;

	public PMSeatLocVO(Integer xCoor, Integer yCoor, Integer seatNum, String pcIP, String memberId, String cardNum,
			String pcStatus, String messageStatus, String orderStatus) {
		super();
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.seatNum = seatNum;
		this.pcIP = pcIP;
		this.memberId = memberId;
		this.cardNum = cardNum;
		this.pcStatus = pcStatus;
		this.messageStatus = messageStatus;
		this.orderStatus = orderStatus;
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

	public String getMemberId() {
		return memberId;
	}

	public String getCardNum() {
		return cardNum;
	}

	public String getPcStatus() {
		return pcStatus;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

}
