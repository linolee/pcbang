package kr.co.sist.pcbang.client.main;

public class PUMainInfoVO {
	private String restTime,name;
	
	public PUMainInfoVO(String restTime, String name) {
		this.restTime = restTime;
		this.name = name;
	}//PUMainInfoVO

	public String getRestTime() {
		return restTime;
	}
	public String getName() {
		return name;
	}

}//class