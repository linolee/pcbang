package kr.co.sist.pcbang.manager.user.detail;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



@SuppressWarnings("serial")
public class PMUserDetailView extends JFrame {

	 private JLabel jlMemberUpdate, jlName, jlId, jlGender, jlInputDate, jlEmail, jlTel, jlBirth, jlLeftTime;
	 private JTextField jtfName, jtfId, jtfGender, jtfInputDate, jtfEmail, jtfTel, jtfBirth, jtfLeftTime;
	 private JButton jbtnUpdate, jbtnCancel;
	 
	 private int f_width, f_height;
	 private Toolkit tk = Toolkit.getDefaultToolkit();
	 
	 public PMUserDetailView(PMUserDetailVO udvo) {
		 
		 setTitle("회원정보");
		 
		 setLayout(null);
		 jlMemberUpdate = new JLabel("회원정보 수정");
		 jlName = new JLabel("이름");
		 jlId = new JLabel("아이디");
		 jlGender = new JLabel("성별");
		 jlInputDate = new JLabel("가입일");
		 jlEmail = new JLabel("이메일");
		 jlTel = new JLabel("전화번호");
		 jlBirth = new JLabel("생년월일");
		 jlLeftTime = new JLabel("잔여시간");
		 
		jtfName = new JTextField(udvo.getName());
		jtfId = new JTextField(udvo.getId());
		jtfGender = new JTextField(udvo.getGender());
		jtfInputDate = new JTextField(udvo.getInputDate());
		jtfEmail = new JTextField(udvo.getEmail());
		jtfTel = new JTextField(udvo.getTel());
		jtfBirth = new JTextField(udvo.getBirth());
		jtfLeftTime = new JTextField( String.valueOf(udvo.getLeftTime()));
		
		jbtnUpdate = new JButton("수정");
		jbtnCancel = new JButton("취소");
		
		jlMemberUpdate.setFont(new Font("돋움체", Font.BOLD, 15));
			
		add(jlMemberUpdate);
		add(jlName);
		add(jtfName);
		add(jlId);
		add(jtfId);
		add(jlGender);
		add(jtfGender);
		add(jlInputDate);
		add(jtfInputDate);
		add(jlEmail);
		add(jtfEmail);
		add(jlTel);
		add(jtfTel);
		add(jlBirth);
		add(jtfBirth);
		add(jlLeftTime);
		add(jtfLeftTime);
		
		add(jbtnUpdate);
		add(jbtnCancel);
		
		
		jlMemberUpdate.setBounds(200,10, 150, 50);
		
		jlName.setBounds(50, 80, 50, 30);
		jtfName.setBounds(110, 80, 120, 25);
		jtfName.setEditable(false);
		
		jlId.setBounds(270, 80, 50, 30);
		jtfId.setBounds(330, 80, 120, 25);
		jtfId.setEditable(false);
		
		jlGender.setBounds(50, 130, 50, 30);
		jtfGender.setBounds(110, 130, 120, 25);
		jtfGender.setEditable(false);
		
		jlInputDate.setBounds(270, 130, 50, 30);
		jtfInputDate.setBounds(330, 130, 120, 25);
		jtfInputDate.setEditable(false);
		
		jlEmail.setBounds(50, 180, 50, 30);
		jtfEmail.setBounds(110, 180, 120, 25);
		
		jlTel.setBounds(270, 180, 70, 30);
		jtfTel.setBounds(330, 180, 120, 25);
		
		jlBirth.setBounds(50, 230, 70, 30);
		jtfBirth.setBounds(110, 230, 120, 25);
		
		jlLeftTime.setBounds(270, 230, 70, 30);
		jtfLeftTime.setBounds(330, 230, 120, 25);
		
		jbtnUpdate.setBounds(170, 300, 70, 30);
		jbtnCancel.setBounds(250, 300, 70, 30);
		
		
		PMUserDetailController udc = new PMUserDetailController(this);
		addWindowListener(udc);
		jtfEmail.addActionListener(udc);
		jtfTel.addActionListener(udc);
		jtfBirth.addActionListener(udc);
		jtfLeftTime.addActionListener(udc);
		jbtnUpdate.addActionListener(udc);
		jbtnCancel.addActionListener(udc);
		
		setVisible(true);
		
		Dimension screen = tk.getScreenSize();
		
		f_width=500;
		f_height=400;
		
		int f_xpos = (int) (screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int) (screen.getHeight() / 2 - f_height / 2);
		
		setResizable(false);
		setLocation(f_xpos, f_ypos); 
		setSize(f_width, f_height);
	 }

	public JLabel getJlMemberUpdate() {
		return jlMemberUpdate;
	}

	public JLabel getJlName() {
		return jlName;
	}

	public JLabel getJlId() {
		return jlId;
	}

	public JLabel getJlGender() {
		return jlGender;
	}

	public JLabel getJlInputDate() {
		return jlInputDate;
	}

	public JLabel getJlEmail() {
		return jlEmail;
	}

	public JLabel getJlTel() {
		return jlTel;
	}

	public JLabel getJlBirth() {
		return jlBirth;
	}

	public JLabel getJlLeftTime() {
		return jlLeftTime;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JTextField getJtfId() {
		return jtfId;
	}

	public JTextField getJtfGender() {
		return jtfGender;
	}

	public JTextField getJtfInputDate() {
		return jtfInputDate;
	}

	public JTextField getJtfEmail() {
		return jtfEmail;
	}

	public JTextField getJtfTel() {
		return jtfTel;
	}

	public JTextField getJtfBirth() {
		return jtfBirth;
	}

	public JTextField getJtfLeftTime() {
		return jtfLeftTime;
	}

	public JButton getJbtnUpdate() {
		return jbtnUpdate;
	}

	public JButton getJbtnCancel() {
		return jbtnCancel;
	}
	
//	public static void main(String[] args) {
//		new PMUserDetailView();
//	}

}
