package kr.co.sist.pcbang.client.login.newuser;

public class PUNewUserVO {

	private String id, pass, name, birth, gender, tel, email, address;

	public PUNewUserVO(String id, String pass, String name, String birth, String gender, String tel, String email,String address) {
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.tel = tel;
		this.email = email;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public String getPass() {
		return pass;
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

	public String getAddress() {
		return address;
	}

} // class
