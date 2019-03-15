package kr.co.sist.pcbang.client.charge;

public class GuestPriceUpdateVO {

	private int cardNum, time, price;

	public GuestPriceUpdateVO(int cardNum, int time, int price) {
		this.cardNum = cardNum;
		this.time = time;
		this.price = price;
	}

	public int getCardNum() {
		return cardNum;
	}

	public int getTime() {
		return time;
	}

	public int getPrice() {
		return price;
	}

}
