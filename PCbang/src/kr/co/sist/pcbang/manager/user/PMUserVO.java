package kr.co.sist.pcbang.manager.user;

public class PMUserVO {
	
	private int leftTime, totalPrice;
	  private String id, name, birth, gender, tel, email, detailAddress, inputDate;

	  public PMUserVO(String id, String name, String birth, String gender, String tel, String email,
			String detailAddress, int leftTime, int totalPrice, String inputDate) {
		this.id = id;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.tel = tel;
		this.email = email;
		this.detailAddress = detailAddress;
		this.leftTime = leftTime;
		this.totalPrice = totalPrice;
		this.inputDate = inputDate;
	}

	  
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBirth() {
		return birth;
	}

	public String getGender() {
		return gender;
	}

	public String getTel() {
		return tel;
	}

	public String getEmail() {
		return email;
	}


	public String getDetailAddress() {
		return detailAddress;
	}

	public int getLeftTime() {
		return leftTime;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public String getInputDate() {
		return inputDate;
	}

	
	
	
	
	
}
