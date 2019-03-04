package kr.co.sist.pcbang.client.charge;

public class MemberMileageVO {

	private int memebrMileage;
	private String memberId;
	
	public MemberMileageVO(int memebrMileage, String memberId) {
		this.memebrMileage = memebrMileage;
		this.memberId = memberId;
	}//PUMemberMileage
	
	public int getMemebrMileage() {
		return memebrMileage;
	}
	
	public String getMemberId() {
		return memberId;
	}
	
}//class
