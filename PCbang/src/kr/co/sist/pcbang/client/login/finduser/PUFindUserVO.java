package kr.co.sist.pcbang.client.login.finduser;

public class PUFindUserVO {
	private String userName, userPhone;

	public PUFindUserVO(String userName, String userPhone) {
		this.userName = userName;
		this.userPhone = userPhone;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPhone() {
		return userPhone;
	}
	
}//class

