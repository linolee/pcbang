package kr.co.sist.pcbang.manager.login;

public class PMLoginVO {
	private String id, pass;

	public PMLoginVO(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}

	public String getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}

	@Override
	public String toString() {
		return "PMLoginVO [id=" + id + ", pass=" + pass + "]";
	}
	
	
	
} // class
