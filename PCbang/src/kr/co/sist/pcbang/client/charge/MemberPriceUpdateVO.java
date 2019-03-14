package kr.co.sist.pcbang.client.charge;


public class MemberPriceUpdateVO {

	private String id;
	private int time, price;

	public MemberPriceUpdateVO(String id, int time, int price) {
		this.id = id;
		this.time = time;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public int getTime() {
		return time;
	}

	public int getPrice() {
		return price;
	}
	
}
