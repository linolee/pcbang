package kr.co.sist.pcbang.manager.seat;

public class PMSeatVO {
	private Integer seatNum;
	private String pcIP, user, pcStatus, messageStatus, orderStatus;

	public PMSeatVO(Integer seatNum, String pcIP, String user, String pcStatus, String messageStatus,
			String orderStatus) {
		super();
		this.seatNum = seatNum;
		this.pcIP = pcIP;
		this.user = user;
		this.pcStatus = pcStatus;
		this.messageStatus = messageStatus;
		this.orderStatus = orderStatus;
	}

	public Integer getSeatNum() {
		return seatNum;
	}

	public String getPcIP() {
		return pcIP;
	}

	public String getUser() {
		return user;
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