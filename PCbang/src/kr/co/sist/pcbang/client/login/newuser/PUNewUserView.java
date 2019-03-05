package kr.co.sist.pcbang.client.login.newuser;

import java.awt.Color;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PUNewUserView extends JFrame {

	private JTextField jtfUserName, jtfId, jtfPhone1, jtfPhone2, jtfPhone3, jtfZipcode, jtfAddr, jtfDetailAddr,
			jtfEmail;
	private JPasswordField jpfPass, jpfPassCheck;
	private JButton jbtIdCheck, jbtAddrSearch, jbtOk, jbtCancel;
	private JComboBox<Integer> jcYear, jcMonth, jcDay;
	private DefaultComboBoxModel<Integer> cbmYear, cbmMonth, cbmDay;
	private JRadioButton jrbMale, jrbFemale;
	private ButtonGroup bg;

	private Calendar cal;

	public PUNewUserView() {
		super("회원가입");

		cal = Calendar.getInstance();

		jtfUserName = new JTextField();
		jtfId = new JTextField();
		jtfPhone1 = new JTextField();
		jtfPhone2 = new JTextField();
		jtfPhone3 = new JTextField();
		jtfZipcode = new JTextField();
		jtfAddr = new JTextField();
		jtfDetailAddr = new JTextField();
		jtfEmail = new JTextField();

		jpfPass = new JPasswordField();
		jpfPassCheck = new JPasswordField();

		jbtIdCheck = new JButton("중복확인");
		jbtAddrSearch = new JButton("검색");
		jbtOk = new JButton("확인");
		jbtCancel = new JButton("취소");

		cbmYear = new DefaultComboBoxModel<Integer>();
		jcYear = new JComboBox<Integer>(cbmYear);
		cbmMonth = new DefaultComboBoxModel<Integer>();
		jcMonth = new JComboBox<Integer>(cbmMonth);
		cbmDay = new DefaultComboBoxModel<Integer>();
		jcDay = new JComboBox<Integer>(cbmDay);

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
		JLabel jlZipcode = new JLabel(" 우편번호");
		JLabel jlAddr = new JLabel(" 주소");
		JLabel jlDetailAddr = new JLabel(" 상세주소");
		JLabel jlEmail = new JLabel(" 이메일");
		JLabel jlEssential = new JLabel("*는 필수입력 사항입니다.");

		JLabel jlhyphen1 = new JLabel("-");
		JLabel jlhyphen2 = new JLabel("-");

		bg = new ButtonGroup();
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
		jlhyphen1.setBounds(192, 290, 40, 30);
		jtfPhone2.setBounds(200, 290, 50, 30);
		jlhyphen2.setBounds(252, 290, 50, 30);
		jtfPhone3.setBounds(260, 290, 50, 30);
		jlZipcode.setBounds(50, 330, 90, 30);
		jtfZipcode.setBounds(150, 330, 90, 30);
		jlAddr.setBounds(50, 370, 90, 30);
		jtfAddr.setBounds(150, 370, 160, 30);
		jlDetailAddr.setBounds(50, 410, 90, 30);
		jtfDetailAddr.setBounds(150, 410, 160, 30);
		jlEmail.setBounds(50, 450, 90, 30);
		jtfEmail.setBounds(150, 450, 160, 30);
		jbtIdCheck.setBounds(285, 90, 88, 30);
		jbtAddrSearch.setBounds(250, 330, 60, 30);
		jbtOk.setBounds(85, 520, 90, 50);
		jbtCancel.setBounds(235, 520, 90, 50);
		jlEssential.setBounds(130, 485, 150, 30);

		jtfZipcode.setEditable(false);
		jtfAddr.setEditable(false);
		jtfZipcode.setBackground(Color.white);
		jtfAddr.setBackground(Color.white);

		add(jlUserName);
		add(jtfUserName);
		add(jlId);
		add(jtfId);
		add(jlPass);
		add(jpfPass);
		add(jlPassCheck);
		add(jpfPassCheck);
		add(jlbrith);
		add(jcYear);
		add(jcMonth);
		add(jcDay);
		add(jlgender);
		add(jrbMale);
		add(jrbFemale);
		add(jlPhone);
		add(jlhyphen1);
		add(jlhyphen2);
		add(jtfPhone1);
		add(jtfPhone2);
		add(jtfPhone3);
		add(jlZipcode);
		add(jtfZipcode);
		add(jlAddr);
		add(jtfAddr);
		add(jlDetailAddr);
		add(jtfDetailAddr);
		add(jlEmail);
		add(jtfEmail);
		add(jlYear);
		add(jlMonth);
		add(jlDay);
		add(jbtIdCheck);
		add(jbtAddrSearch);
		add(jbtOk);
		add(jbtCancel);
		add(jlEssential);

		setYear(); // JCB Year설정
		setMonth(); // JCB Month설정
		setDay();// JCB Day설정

		PUNewUserController punuc = new PUNewUserController(this);
		addWindowListener(punuc);
		jbtCancel.addActionListener(punuc);
		jbtAddrSearch.addActionListener(punuc);
		jbtIdCheck.addActionListener(punuc);
		jbtOk.addActionListener(punuc);
		jtfId.addKeyListener(punuc);

		jcMonth.addActionListener(punuc);

		setBounds(800, 200, 400, 650);
		setVisible(true);
		setResizable(false);

		jtfUserName.requestFocus();

	} // PUNewUserView

	private void setYear() {// 현재년도 50년전까지
		int year = cal.get(Calendar.YEAR);
		for (int temp = 0; temp < 50; temp++) {
			cbmYear.addElement(year - temp);
		} // end for
		jcYear.setSelectedItem(new Integer(year));

	}// setYear

	private void setMonth() {// 월 1~12월
		int now_month = cal.get(Calendar.MONTH) + 1;
		
		for (int month = 1; month < 13; month++) {
			cbmMonth.addElement(month);
		} // end for
		
		jcMonth.setSelectedItem(new Integer(now_month));
	}// setMonth

	private void setDay() {// 그월의 마지막날
		int lastDay = cal.getActualMaximum(Calendar.DATE); // 31
		int  nowDay=cal.get(Calendar.DAY_OF_MONTH);
		for (int day = 1; day < lastDay + 1; day++) {
			cbmDay.addElement(day);
		} // end for
		jcDay.setSelectedItem(new Integer(nowDay));

	}// setYear

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

	public JTextField getJtfZipcode() {
		return jtfZipcode;
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

	public JButton getJbtIdCheck() {
		return jbtIdCheck;
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

	public DefaultComboBoxModel<Integer> getCbmYear() {
		return cbmYear;
	}

	public DefaultComboBoxModel<Integer> getCbmMonth() {
		return cbmMonth;
	}

	public DefaultComboBoxModel<Integer> getCbmDay() {
		return cbmDay;
	}

	public JRadioButton getJrbMale() {
		return jrbMale;
	}

	public JRadioButton getJrbFemale() {
		return jrbFemale;
	}

	public ButtonGroup getBg() {
		return bg;
	}

	public Calendar getCal() {
		return cal;
	}

} // class
