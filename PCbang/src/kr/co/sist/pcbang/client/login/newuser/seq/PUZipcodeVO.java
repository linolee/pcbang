package kr.co.sist.pcbang.client.login.newuser.seq;

public class PUZipcodeVO {
	private String zipcode, sido, gugun, dong, bunji;
	private int seq;
	public PUZipcodeVO(String zipcode, String sido, String gugun, String dong, String bunji, int seq) {
		this.zipcode = zipcode;
		this.sido = sido;
		this.gugun = gugun;
		this.dong = dong;
		this.bunji = bunji;
		this.seq = seq;
	}
	public String getZipcode() {
		return zipcode;
	}
	public String getSido() {
		return sido;
	}
	public String getGugun() {
		return gugun;
	}
	public String getDong() {
		return dong;
	}
	public String getBunji() {
		return bunji;
	}
	public int getSeq() {
		return seq;
	}
	@Override
	public String toString() {
		return "PUZipcodeVO [zipcode=" + zipcode + ", sido=" + sido + ", gugun=" + gugun + ", dong=" + dong + ", bunji="
				+ bunji + ", seq=" + seq + "]";
	}
	
	
}// class
