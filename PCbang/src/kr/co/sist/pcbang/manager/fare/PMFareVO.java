package kr.co.sist.pcbang.manager.fare;


public class PMFareVO {
	
	private	int chargeTime, memberPrice, guestPrice;

	public PMFareVO(int chargeTime, int memberPrice, int guestPrice) {
		this.chargeTime = chargeTime;
		this.memberPrice = memberPrice;
		this.guestPrice = guestPrice;
	}

	public int getChargeTime() {
		return chargeTime;
	}
	
	public int getMemberPrice() {
		return memberPrice;
	}

	public int getGuestPrice() {
		return guestPrice;
	}
	
	
	 
}
