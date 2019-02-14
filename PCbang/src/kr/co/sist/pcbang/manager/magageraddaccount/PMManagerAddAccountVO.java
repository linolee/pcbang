package kr.co.sist.pcbang.manager.magageraddaccount;

public class PMManagerAddAccountVO {

	private String adminId, adminPass, adminName;

	public PMManagerAddAccountVO(String adminId, String adminPass, String adminName) {
		this.adminId = adminId;
		this.adminPass = adminPass;
		this.adminName  = adminName;
	}

	public String getAdminId() {
		return adminId;
	}

	public String getAdminPass() {
		return adminPass;
	}

	public String getAdminName() {
		return adminName;
	}


	
	
} // class
