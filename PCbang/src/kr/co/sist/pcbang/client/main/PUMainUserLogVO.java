package kr.co.sist.pcbang.client.main;

public class PUMainUserLogVO {
	private String memberId,useDate;
	private int useTime,chargePrice;
	
	public PUMainUserLogVO(String memberId, String useDate, int useTime, int chargePrice) {
		this.memberId = memberId;
		this.useDate = useDate;
		this.useTime = useTime;
		this.chargePrice = chargePrice;
	}//PUMainUserLogVO

	public String getMemberId() {
		return memberId;
	}
	public String getUseDate() {
		return useDate;
	}
	public int getUseTime() {
		return useTime;
	}
	public int getChargePrice() {
		return chargePrice;
	}
}//class
