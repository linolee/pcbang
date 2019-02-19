package kr.co.sist.pcbang.manager.seat.set;

public class PMSeatSetLocVO {
	Integer xCoor, yCoor, seatNum;
	String pcIP;

	public PMSeatSetLocVO(Integer xCoor, Integer yCoor, Integer seatNum, String pcIP) {
		super();
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.seatNum = seatNum;
		this.pcIP = pcIP;
	}

	@Override
	public String toString() {
		return "(" + xCoor + "," + yCoor + ")" + seatNum + "/" + pcIP;
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

}
