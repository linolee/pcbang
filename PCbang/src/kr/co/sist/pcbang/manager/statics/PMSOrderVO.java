package kr.co.sist.pcbang.manager.statics;

public class PMSOrderVO {
	private int total;
	private String day;
	public PMSOrderVO(int total, String day) {
		this.total = total;
		this.day = day;
	}
	public int getTotal() {
		return total;
	}
	public String getDay() {
		return day;
	}

}
