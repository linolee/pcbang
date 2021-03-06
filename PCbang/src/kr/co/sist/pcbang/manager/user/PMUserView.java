package kr.co.sist.pcbang.manager.user;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class PMUserView extends JPanel {
	
	 private JLabel jlId, jlName;
	 private JTextField jtfId, jtfName;
	 private JButton jbtnSearch, jbtnReset;
	 private JTable jtMember;
	 private DefaultTableModel dtmMember;
	
	public PMUserView() {
		
		jlId = new JLabel("아이디");
		jlName = new JLabel("이름");
		
		jtfId = new JTextField(10);
		jtfName = new JTextField(10);
		
		jbtnSearch = new JButton("조회");
		jbtnReset = new JButton("초기화");
		
		setLayout(null);
		
		String[] memberColumns = {"순차", "아이디", "이름", "생년월일", "성별", "전화번호", "이메일",  "주소", "마일리지", "잔여시간", "총 사용 금액", "가입일"};
		dtmMember = new DefaultTableModel(memberColumns, 0){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtMember = new JTable(dtmMember); 
		
		
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// 정렬할 테이블의 ColumnModel을 가져옴

		TableColumnModel tcmSchedule = jtMember.getColumnModel();

		 
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {

		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);

		}

		
		jtMember.getTableHeader().setReorderingAllowed(false);	
		
		jtMember.getColumnModel().getColumn(0).setPreferredWidth(5);
		jtMember.getColumnModel().getColumn(1).setPreferredWidth(40);
		jtMember.getColumnModel().getColumn(2).setPreferredWidth(65);
		jtMember.getColumnModel().getColumn(3).setPreferredWidth(55);
		jtMember.getColumnModel().getColumn(4).setPreferredWidth(10);
		jtMember.getColumnModel().getColumn(6).setPreferredWidth(110);
		jtMember.getColumnModel().getColumn(7).setPreferredWidth(140);
		jtMember.getColumnModel().getColumn(8).setPreferredWidth(35);
		jtMember.getColumnModel().getColumn(9).setPreferredWidth(35);
		jtMember.getColumnModel().getColumn(10).setPreferredWidth(45);
		jtMember.getColumnModel().getColumn(11).setPreferredWidth(50);
		jtMember.setRowHeight(55);
		
		JScrollPane jspMember = new JScrollPane(jtMember);
		
		JPanel jpMember=new JPanel();
		jpMember.setLayout(new BorderLayout());
		
		JPanel jpMemberNorth = new JPanel();
		
		jpMemberNorth.add(jlId);
		jpMemberNorth.add(jtfId);
		jpMemberNorth.add(jlName);
		jpMemberNorth.add(jlName);
		jpMemberNorth.add(jtfName);
		jpMemberNorth.add(jbtnSearch);
		jpMemberNorth.add(jbtnReset);
		
		jpMember.add("Center",jspMember);
		jpMember.add("North",jpMemberNorth);
		
		jpMember.setBounds(0, 0, 1000, 570);
		add(jpMember);
		
		PMUserController uc = new PMUserController(this);
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
