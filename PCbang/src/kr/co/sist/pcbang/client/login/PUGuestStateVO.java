package kr.co.sist.pcbang.client.login;

public class PUGuestStateVO {

	private int cardNum;
	private String pcIp;

	public PUGuestStateVO(int cardNum, String pcIp) {
		this.cardNum = cardNum;
		this.pcIp = pcIp;
	}

	public int getCardNum() {
		return cardNum;
	}

	public String getPcIp() {
		return pcIp;
	}
	
}