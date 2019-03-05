package kr.co.sist.pcbang.client.main;

public class PUMainRestTimeVO {
	private int memberUseTime;
	private String memberId;
	
	public PUMainRestTimeVO(int memberUseTime, String memberId) {
		this.memberUseTime = memberUseTime;
		this.memberId = memberId;
	}

	public int getMemberUseTime() {
		return memberUseTime;
	}

	public String getMemberId() {
		return memberId;
	}

}
