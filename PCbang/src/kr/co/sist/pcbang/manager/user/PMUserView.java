package kr.co.sist.pcbang.manager.user;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class PMUserView extends JPanel {
	
	 private JLabel jlSearch, jlId, jlName;
	 private JTextField jtfId, jtfName;
	 private JButton jbtnSearch, jbtnReset;
	 private JTable jtMember;
	 private DefaultTableModel dtmMember;
	
	public PMUserView() {
		jlSearch = new JLabel("회원조회");
		jlId = new JLabel("아이디");
		jlName = new JLabel("이름");
		
		jtfId = new JTextField(10);
		jtfName = new JTextField(10);
		
		jbtnSearch = new JButton("조회");
		jbtnReset = new JButton("초기화");
		
		setLayout(null);
		
		String[] memberColumns = {"순차", "아이디", "이름", "생년월일", "성별", "전화번호", "이메일",  "상세주소", "잔여시간", "총 사용 금액", "입력일"};
		dtmMember = new DefaultTableModel(memberColumns, 0){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jtMember = new JTable(dtmMember); 

		jtMember.getTableHeader().setReorderingAllowed(false);	
		
		jtMember.getColumnModel().getColumn(0).setPreferredWidth(10);
		jtMember.getColumnModel().getColumn(1).setPreferredWidth(20);
		jtMember.getColumnModel().getColumn(2).setPreferredWidth(25);
		jtMember.getColumnModel().getColumn(3).setPreferredWidth(35);
		jtMember.getColumnModel().getColumn(4).setPreferredWidth(10);
		jtMember.getColumnModel().getColumn(6).setPreferredWidth(150);
		jtMember.getColumnModel().getColumn(7).setPreferredWidth(210);
		jtMember.getColumnModel().getColumn(8).setPreferredWidth(10);
		jtMember.getColumnModel().getColumn(9).setPreferredWidth(10);
		jtMember.getColumnModel().getColumn(10).setPreferredWidth(100);
		jtMember.setRowHeight(55);
		
		JScrollPane jspMember = new JScrollPane(jtMember);
		
		JPanel jpMember=new JPanel();
		jpMember.setLayout(new BorderLayout());
		
		JPanel jpMemberNorth = new JPanel();
		
		jpMemberNorth.add(jlSearch);
		jpMemberNorth.add(jlId);
		jpMemberNorth.add(jtfId);
		jpMemberNorth.add(jlName);
		jpMemberNorth.add(jlName);
		jpMemberNorth.add(jtfName);
		jpMemberNorth.add(jbtnSearch);
		jpMemberNorth.add(jbtnReset);
		
		jpMember.add("Center",jspMember);
		jpMember.add("North",jpMemberNorth);
		
		jpMember.setBounds(0, 0, 1000, 600);
		add(jpMember);
		
		PMUserController uc = new PMUserController(this);
//		addWindowFocusListener(uc);
		jtfId.addActionListener(uc);
		jtfName.addActionListener(uc);
		jbtnSearch.addActionListener(uc);
		jbtnReset.addActionListener(uc);
		jtMember.addMouseListener(uc);
		
		uc.selectUser();
		
		setVisible(true);	
		setBounds(100, 100, 1000, 600);
		
	}
	
	
	

	public JTextField getJtfId() {
		return jtfId;
	}



	public JTextField getJtfName() {
		return jtfName;
	}



	public JButton getJbtnSearch() {
		return jbtnSearch;
	}



	public JButton getJbtnReset() {
		return jbtnReset;
	}



	public JTable getJtMember() {
		return jtMember;
	}



	public DefaultTableModel getDtmMember() {
		return dtmMember;
	}



	public static void main(String[] args) {
		new PMUserView();
	}
}

