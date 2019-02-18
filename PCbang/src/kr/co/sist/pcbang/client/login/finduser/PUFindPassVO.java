package kr.co.sist.pcbang.client.login.finduser;

public class PUFindPassVO {

	private String userId, userName, userPhone;

	public PUFindPassVO(String userId, String userName, String userPhone) {
		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPhone() {
		return userPhone;
	}
	
}//class

