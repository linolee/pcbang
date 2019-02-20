package kr.co.sist.pcbang.client.login.newuser;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class PUPolicyView extends JFrame{

	private JCheckBox jcb1, jcb2, jcb3;
	private JTextArea jtaCare1, jtaCare2, jtaCare3;
	private JButton jbtOk, jbtcancel;
	private JLabel msgJoin1, msgJoin2, msgJoin3, msgJoin4, msgJoin5;
	
	public PUPolicyView() {
		super("※ 개인정보 보호");	
		jcb1 = new JCheckBox("수집하는 개인정보 항목에 동의합니다.");
		jcb2 = new JCheckBox("개인정보 수집 및 이용목적, 이용기간에 동의합니다.");
		jcb3 = new JCheckBox("개인정보 취급 위탁에 동의합니다.");
		
		jbtOk = new JButton("확인");
		jbtcancel = new JButton("취소");
		
		jtaCare1 = new JTextArea();
		jtaCare2 = new JTextArea();
		jtaCare3 = new JTextArea();
				
		JScrollPane jspCare1 = new JScrollPane(jtaCare1);
		JScrollPane jspCare2 = new JScrollPane(jtaCare2);
		JScrollPane jspCare3 = new JScrollPane(jtaCare3);
		
		msgJoin1 = new JLabel();
		msgJoin2 = new JLabel();
		msgJoin3 = new JLabel();
		msgJoin4 = new JLabel();
		msgJoin5 = new JLabel();
		
		setLayout(null);
		msgJoin1.setText("개인정보보호법시행에 따라 개인정보 수집 및 이용에 관한 내용");
		msgJoin2.setText("확인 뒤 동의해야 회원가입이 가능합니다.");
		msgJoin3.setText("개정된 주민등록법에 의해 타인의 주민등록번호를 부정 사용");
		msgJoin4.setText("하는 자는 3년 이하의 징역 또는 1천 만원 이하의 벌금이 부과될");
		msgJoin5.setText("수 있습니다.");
		
		jtaCare1.setText("수집하는 개인정보의 항목\n" +
				"수집하는 목적/방법에 따라 수집하는 개인정보 항목은 다음과 같습니다.\n" + 
				" - 기본 개인정보 정보 \r\n" + 
				" - 이름, 주민등록번호\r\n" + 
				" - 로그인 ID, 비밀번호\r\n" + 
				" - 자택전화번호, 자택주소, 휴대전화번호\r\n" + 
				" - 이메일, 이메일 수신여부\r\n" + 
				" - 마케팅/서비스 이용을 위한 정보\r\n" + 
				" - 회사명, 부서/직책, 회사전화번호, 주민등록번호\r\n" + 
				" - 서비스 이용 중 발생되는 정보\r\n" + 
				" - 서비스 이용기록, 접속로그, 쿠키\r\n" + 
				" - 결재수단에 대한 기록여부(계좌), 결재기록");
		
		jtaCare2.setText("개인정보 수집 및 이용 목적\n" + 
				"PC방은 수집한 개인정보를 다음의 목적을 위해 활용합니다.\r\n" + 
				" - 서비스 제공에 관한 계약 이행 및 서비스 제공에 따른 요금정산 목적\r\n" + 
				"PC이용, 컨텐츠 제공, 구매 및 요금 결제, 물품구입\r\n" + 
				" - 회원 관리\r\n" + 
				"회원제 서비스 이용에 따른 본인확인, 개인 식별, 불량회원의 부정 이용 방지와\r\n" + 
				"비인가사용 방지, 가입 의사 확인, 연령확인, 불만처리 등 민원처리, 고지사항 전달\r\n" + 
				" - 마케팅 및 광고에 활용\r\n" + 
				"신규 서비스(제품) 개발 및 특화, 이벤트 등 광고성 정보 전달, 인구통계학적 특성에 따른\r\n" + 
				"서비스 제공 및 광고 게재, 접속 빈도 파악 또는 회원의 서비스 이용에 대한 통계 \r\n");
		
		jtaCare3.setText("개인 정보 보유 및 이용기간\n" + 
				"원칙적으로, 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다.\r\n" + 
				"단, 다음의 정보에 대해서는 아래의 이유로 명시한 기간 동안 보존합니다. \r\n" + 
				"보존 항목 : 이름, 자택 전화번호, 자택주소, 휴대전화번호, 이메일, 회사명, 부서, 직책, 회사전화번호, 주민등록번호\r\n" + 
				"보존 근거 : 고용보험 환급 적정성 심의\r\n" + 
				"보존 기간 : 3년 \r\n" + 
				"그리고 관계법령의 규정에 의하여 보존할 필요가 있는 경우 회사는 아래와 같이 관계법령에서 정한 일정한 기간 동안 회원정보를 보관합니다.\r\n" + 
				"1) 기타 법령에 따른 보유기간/관계법 안내\r\n" + 
				"- 계약 또는 청약철회 등에 관한 기록 : 5년 (전자상거래 등에서의 소비자 보호에 관한 법률)\r\n" + 
				"- 대금결제 및 재화 등의 공급에 관한 기록 : 5년 (전자상거래 등에서의 소비자 보호에 관한 법률)\r\n" + 
				"- 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년 (전자상거래 등에서의 소비자 보호에 관한 법률)\r\n" + 
				"- 본인확인에 관한 기록 보존 이유 : 정보통신 이용촉진 및 정보보호 등에 관한 법률 보존 기간 : 6개월 \r\n" + 
				"- 방문에 관한 기록 보존 이유 : 통신 비밀 보호법 보존 기간 : 3개월");
		
		msgJoin1.setBounds(10, 10, 375, 30);
		msgJoin2.setBounds(10, 25, 375, 30);
		msgJoin3.setBounds(10, 60, 375, 30);
		msgJoin4.setBounds(10, 75, 375, 30);
		msgJoin5.setBounds(10, 90, 375, 30);
		jspCare1.setBounds(10, 130, 375, 110);
		jspCare2.setBounds(10, 270, 375, 110);
		jspCare3.setBounds(10, 410, 375, 110);
		jcb1.setBounds(10, 240, 350, 30);
		jcb2.setBounds(10, 380, 350, 30);
		jcb3.setBounds(10, 520, 350, 30);
		jbtOk.setBounds(90, 580, 80, 40);
		jbtcancel.setBounds(230, 580, 80, 40);
		
		add(msgJoin1);
		add(msgJoin2);
		add(msgJoin3);
		add(msgJoin4);
		add(msgJoin5);
		add(jspCare1);
		add(jspCare2);
		add(jspCare3);
		add(jcb1);
		add(jcb2);
		add(jcb3);
		add(jbtOk);
		add(jbtcancel);

		msgJoin3.setForeground(Color.RED);
		msgJoin4.setForeground(Color.RED);
		msgJoin5.setForeground(Color.RED);
		
		jtaCare1.setBackground(Color.white);
		jtaCare2.setBackground(Color.white);
		jtaCare3.setBackground(Color.white);
		
		jtaCare1.setEditable(false);
		jtaCare2.setEditable(false);
		jtaCare3.setEditable(false);
		
		jtaCare3.setOpaque(true);
		jtaCare2.setOpaque(true);
		jtaCare3.setOpaque(true);

		PUPolicyController pupc = new PUPolicyController(this);
		addWindowListener(pupc);
		jcb1.addActionListener(pupc);
		jcb2.addActionListener(pupc);
		jcb3.addActionListener(pupc);
		jbtOk.addActionListener(pupc);
		jbtcancel.addActionListener(pupc);
		
		setBounds(800, 200, 400, 700);
		setVisible(true);
		setResizable(false);
				
		
	} // PUPolicyView
	
	public JCheckBox getJcb1() {
		return jcb1;
	}

	public JCheckBox getJcb2() {
		return jcb2;
	}

	public JCheckBox getJcb3() {
		return jcb3;
	}

	public JTextArea getJtaCare1() {
		return jtaCare1;
	}

	public JTextArea getJtaCare2() {
		return jtaCare2;
	}

	public JTextArea getJtaCare3() {
		return jtaCare3;
	}

	public JButton getJbtOk() {
		return jbtOk;
	}

	public JButton getJbtcancel() {
		return jbtcancel;
	}

} // class
