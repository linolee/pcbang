package kr.co.sist.pcbang.manager.statics;

public class PMSOperatingTermVO {
	private String day;
	private int sumTime;
	public PMSOperatingTermVO(String day, int sumTime) {
		this.day = day;
		this.sumTime = sumTime;
	}
	public String getDay() {
		return day;
	}
	public int getSumTime() {
		return sumTime;
	}
	@Override
	public String toString() {
		return "PMSOperatingTermVO [day=" + day + ", sumTime=" + sumTime + "]";
	}
	
}
