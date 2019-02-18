package kr.co.sist.pcbang.client.login.finduser.updatepass;

public class PUUpdatePassVO {
	private String userPass, userId;

	public PUUpdatePassVO(String userPass, String userId) {
		this.userPass = userPass;
		this.userId = userId;
	}

	public String getUserPass() {
		return userPass;
	}

	public String getUserId() {
		return userId;
	}
}
