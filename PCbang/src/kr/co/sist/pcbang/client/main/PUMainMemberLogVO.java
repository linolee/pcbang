package kr.co.sist.pcbang.client.main;

public class PUMainMemberLogVO {
	private String memberId;
	private int useTime,chargePrice;
	
	public PUMainMemberLogVO(String memberId, int useTime, int chargePrice) {
		this.memberId = memberId;
		this.useTime = useTime;
		this.chargePrice = chargePrice;
	}//PUMainUserLogVO

	public String getMemberId() {
		return memberId;
	}
	public int getUseTime() {
		return useTime;
	}
	public int getChargePrice() {
		return chargePrice;
	}
}//class
