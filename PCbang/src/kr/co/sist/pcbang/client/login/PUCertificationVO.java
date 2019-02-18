package kr.co.sist.pcbang.client.login;

public class PUCertificationVO {
	private String memberId, memberPass;

	public PUCertificationVO(String memberId, String memberPass) {
		this.memberId = memberId;
		this.memberPass = memberPass;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getMemberPass() {
		return memberPass;
	}
	
}//class
