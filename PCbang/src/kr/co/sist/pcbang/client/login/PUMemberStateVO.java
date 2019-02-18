package kr.co.sist.pcbang.client.login;

public class PUMemberStateVO {

	
	private String memberId;
	private int pcIp;
	
	public PUMemberStateVO(String memberId, int pcIp) {
		this.memberId = memberId;
		this.pcIp = pcIp;
	}

	public String getMemberId() {
		return memberId;
	}

	public int getPcIp() {
		return pcIp;
	}

}

