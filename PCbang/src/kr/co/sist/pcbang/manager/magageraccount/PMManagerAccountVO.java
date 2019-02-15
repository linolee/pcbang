package kr.co.sist.pcbang.manager.magageraccount;

public class PMManagerAccountVO {

	private String adminId, adminName, adminDate;
	
	public PMManagerAccountVO(String adminId, String adminName, String adminDate) {
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminDate = adminDate;
	}

	public String getAdminId() {
		return adminId;
	}


	public String getAdminName() {
		return adminName;
	}

	public String getAdminDate() {
		return adminDate;
	}

	@Override
	public String toString() {
		return "PMManagerAccountVO [adminId=" + adminId + ", adminName=" + adminName+ ", adminDate=" + adminDate + "]";
	}
	
	
	
} // class
