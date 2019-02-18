package kr.co.sist.pcbang.client.login;

public class PUGuestStateVO {

	private int cardNum, pcIp;

	public PUGuestStateVO(int cardNum, int pcIp) {
		this.cardNum = cardNum;
		this.pcIp = pcIp;
	}

	public int getCardNum() {
		return cardNum;
	}

	public int getPcIp() {
		return pcIp;
	}
	
}