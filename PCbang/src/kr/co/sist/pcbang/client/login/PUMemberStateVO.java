package kr.co.sist.pcbang.client.login;


public class PUMemberStateVO {

		private String memberId, pcIp;
		
		public PUMemberStateVO(String memberId, String pcIp) {
			this.memberId = memberId;
			this.pcIp = pcIp;
		}

		public String getMemberId() {
			return memberId;
		}

		public String getPcIp() {
			return pcIp;
		}

	}

