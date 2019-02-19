package kr.co.sist.pcbang.client.login.newuser;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PUNewUserView extends JFrame {

	private JTextField jtfUserName, jtfId, jtfPhone1, jtfPhone2, jtfPhone3, jtfAddr, jtfDetailAddr, jtfEmail;
	private JPasswordField jpfPass, jpfPassCheck;
	private JButton jbtIdChack, jbtAddrSearch, jbtOk, jbtCancel;
	private JComboBox <Integer>jcYear, jcMonth, jcDay;
	private JRadioButton jrbMale, jrbFemale;
	
	public PUNewUserView() {
		super("회원가입");
		
		jtfUserName = new JTextField();
		jtfId = new JTextField();
		jtfPhone1 = new JTextField();
		jtfPhone2 = new JTextField();
		jtfPhone3 = new JTextField();
		jtfAddr = new JTextField();
		jtfDetailAddr = new JTextField();
		jtfEmail = new JTextField();
		
		jpfPass = new JPasswordField();
		jpfPassCheck = new JPasswordField();
		
		jbtIdChack = new JButton("중복확인");
		jbtAddrSearch = new JButton("검색");
		jbtOk = new JButton("확인");
		jbtCancel = new JButton("취소");
		
		jcYear = new JComboBox<Integer>();
		jcMonth = new JComboBox<Integer>();
		jcDay = new JComboBox<Integer>();
		
		jrbMale = new JRadioButton("남자");
		jrbFemale = new JRadioButton("여자");

		JLabel jlUserName = new JLabel("*이름");
		JLabel jlId = new JLabel("*아이디");
		JLabel jlPass = new JLabel("*비밀번호");
		JLabel jlPassCheck = new JLabel("*비밀번호확인");
		JLabel jlbrith = new JLabel(" 생년월일");
		JLabel jlYear = new JLabel("년");
		JLabel jlMonth = new JLabel("월");
		JLabel jlDay = new JLabel("일");
		JLabel jlgender = new JLabel("*성별");
		JLabel jlPhone = new JLabel("*휴대폰");
		JLabel jlAddr = new JLabel(" 주소");
		JLabel jlDetailAddr = new JLabel(" 상세주소");
		JLabel jlEmail = new JLabel(" 이메일");

		ButtonGroup bg = new ButtonGroup();
		bg.add(jrbMale); 
		bg.add(jrbFemale);
		
		setLayout(null);

		jlUserName.setBounds(50, 50, 90, 30);
		jtfUserName.setBounds(150, 50, 130, 30);
		jlId.setBounds(50, 90, 90, 30);
		jtfId.setBounds(150, 90, 130, 30);
		jlPass.setBounds(50, 130, 90, 30);
		jpfPass.setBounds(150, 130, 130, 30);
		jlPassCheck.setBounds(50, 170, 90, 30);
		jpfPassCheck.setBounds(150, 170, 130, 30);
		jlbrith.setBounds(50, 210, 90, 30);
		jcYear.setBounds(150, 210, 60, 30);
		jlYear.setBounds(215, 210, 40, 30);
		jcMonth.setBounds(230, 210, 45, 30);
		jlMonth.setBounds(280, 210, 40, 30);
		jcDay.setBounds(295, 210, 45, 30);
		jlDay.setBounds(345, 210, 90, 30);
		jlgender.setBounds(50, 250, 90, 30);
		jrbMale.setBounds(150, 250, 60, 30);
		jrbFemale.setBounds(210, 250, 60, 30);
		jlPhone.setBounds(50, 290, 90, 30);
		jtfPhone1.setBounds(150, 290, 40, 30);
		jtfPhone2.setBounds(200, 290, 50, 30);
		jtfPhone3.setBounds(260, 290, 50, 30);
		jlAddr.setBounds(50, 330, 90, 30);
		jtfAddr.setBounds(150, 330, 90, 30);
		jlDetailAddr.setBounds(50, 370, 90, 30);
		jtfDetailAddr.setBounds(150, 370, 160, 30);
		jlEmail.setBounds(50, 410, 90, 30);
		jtfEmail.setBounds(150, 410, 160, 30);
		jbtIdChack.setBounds(285, 90, 88, 30);
		jbtAddrSearch.setBounds(250, 330, 60, 30);
		jbtOk.setBounds(85, 470, 90, 50);
		jbtCancel.setBounds(235, 470, 90, 50);

		
		
		add(jlUserName);		add(jtfUserName);
		add(jlId);				add(jtfId);
		add(jlPass);			add(jpfPass);
		add(jlPassCheck);		add(jpfPassCheck);
		add(jlbrith);			add(jcYear);	add(jcMonth);	add(jcDay);
		add(jlgender);			add(jrbMale);	add(jrbFemale);
		add(jlPhone);
		add(jtfPhone1);
		add(jtfPhone2);
		add(jtfPhone3);
		add(jlAddr);
		add(jtfAddr);
		add(jlDetailAddr);
		add(jtfDetailAddr);
		add(jlEmail);
		add(jtfEmail);
		add(jlYear);
		add(jlMonth);
		add(jlDay);
		add(jbtIdChack);
		add(jbtAddrSearch);
		add(jbtOk);
		add(jbtCancel);
		
		PUNewUserController punuc = new PUNewUserController(this);
		addWindowListener(punuc);
		jbtCancel.addActionListener(punuc);
		jbtAddrSearch.addActionListener(punuc);
		
		setBounds(800, 200, 400, 600);
		setVisible(true);
		setResizable(false);
		
		jlUserName.requestFocus();
		
	} // PUNewUserView
	
	public JTextField getJtfUserName() {
		return jtfUserName;
	}



	public JTextField getJtfId() {
		return jtfId;
	}



	public JTextField getJtfPhone1() {
		return jtfPhone1;
	}



	public JTextField getJtfPhone2() {
		return jtfPhone2;
	}



	public JTextField getJtfPhone3() {
		return jtfPhone3;
	}



	public JTextField getJtfAddr() {
		return jtfAddr;
	}



	public JTextField getJtfDetailAddr() {
		return jtfDetailAddr;
	}



	public JTextField getJtfEmail() {
		return jtfEmail;
	}



	public JPasswordField getJpfPass() {
		return jpfPass;
	}



	public JPasswordField getJpfPassCheck() {
		return jpfPassCheck;
	}



	public JButton getJbtIdChack() {
		return jbtIdChack;
	}



	public JButton getJbtAddrSearch() {
		return jbtAddrSearch;
	}



	public JButton getJbtOk() {
		return jbtOk;
	}



	public JButton getJbtCancel() {
		return jbtCancel;
	}



	public JComboBox<Integer> getJcYear() {
		return jcYear;
	}



	public JComboBox<Integer> getJcMonth() {
		return jcMonth;
	}



	public JComboBox<Integer> getJcDay() {
		return jcDay;
	}



	public JRadioButton getJrbMale() {
		return jrbMale;
	}



	public JRadioButton getJrbFemale() {
		return jrbFemale;
	}

} // class
