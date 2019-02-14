package kr.co.sist.pcbang.manager.user.detail;
public class PMUserDetailVO {
	
	private int leftTime;
	private String name, id, gender, inputDate, email, tel, birth;

	public PMUserDetailVO(String name, String id, String gender, String inputDate, String email, String tel,
			String birth, int leftTime) {
		this.name = name;
		this.id = id;
		this.gender = gender;
		this.inputDate = inputDate;
		this.email = email;
		this.tel = tel;
		this.birth = birth;
		this.leftTime = leftTime;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getGender() {
		return gender;
	}

	public String getInputDate() {
		return inputDate;
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

	public int getLeftTime() {
		return leftTime;
	}
	
	
	
	
}
