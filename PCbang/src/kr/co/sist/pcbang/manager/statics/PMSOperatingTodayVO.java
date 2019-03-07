package kr.co.sist.pcbang.manager.statics;

public class PMSOperatingTodayVO {
	private int time, sumTime;

	public PMSOperatingTodayVO(int time, int sumTime) {
		this.time = time;
		this.sumTime = sumTime;
	}

	public int getTime() {
		return time;
	}

	public int getSumTime() {
		return sumTime;
	}

	@Override
	public String toString() {
		return "PMSOperatingVO [timeOrDay=" + time + ", sumTime=" + sumTime + "]";
	}
	
	
}
