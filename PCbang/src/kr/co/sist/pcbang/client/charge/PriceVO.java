package kr.co.sist.pcbang.client.charge;

public class PriceVO {

	private int chargeTime, price;

	public PriceVO(int chargeTime, int price) {
		this.chargeTime = chargeTime;
		this.price = price;
	}

	public int getChargeTime() {
		return chargeTime;
	}

	public int getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return chargeTime + ", " + price;
	}

}

