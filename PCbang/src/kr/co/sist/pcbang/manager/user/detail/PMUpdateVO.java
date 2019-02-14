package kr.co.sist.pcbang.manager.user.detail;

public class PMUpdateVO {
	
	private String email, tel, birth, id;
	private int leftTime;
	
	public PMUpdateVO(String email, String tel, String birth, String id, int leftTime) {
		super();
		this.email = email;
		this.tel = tel;
		this.birth = birth;
		this.id = id;
		this.leftTime = leftTime;
	}

	public String getEmail() {
		return email;
	}

	public String getTel() {
		return tel;
	}

	public String getBirth() {
		return birth;
	}

	public String getId() {
		return id;
	}

	public int getLeftTime() {
		return leftTime;
	}
	
}
