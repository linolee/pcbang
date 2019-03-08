package kr.co.sist.pcbang.client.main;

public class PUMainGuestLogVO {
	private int cardNum, useTime, chargePrice;

	public PUMainGuestLogVO(int cardNum, int useTime, int chargePrice) {
		this.cardNum = cardNum;
		this.useTime = useTime;
		this.chargePrice = chargePrice;
	}

	public int getCardNum() {
		return cardNum;
	}

	public int getUseTime() {
		return useTime;
	}

	public int getChargePrice() {
		return chargePrice;
	}
	
}
