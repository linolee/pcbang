package kr.co.sist.pcbang.manager.magageraccount;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class PMManagerAccountView extends JFrame {

	private JButton jbtOk, jbtAdd, jbtDel;
	private JTable jtAccount;
	private DefaultTableModel dtmAccount; // 관리자 계정
	
	public PMManagerAccountView() {
		super("관리자 계정관리");

		String[] AccountColumns = { "아이디", "이름", "생성일" };
		dtmAccount = new DefaultTableModel(AccountColumns, 3) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		jtAccount = new JTable(dtmAccount);

		JScrollPane jspJTable = new JScrollPane(jtAccount);

		jtAccount.getTableHeader().setReorderingAllowed(false);

		jtAccount.getColumnModel().getColumn(0).setPreferredWidth(90);
		jtAccount.getColumnModel().getColumn(1).setPreferredWidth(125);
		jtAccount.getColumnModel().getColumn(2).setPreferredWidth(255);

		// 테이블의 높이
		jtAccount.setRowHeight(40);

		jbtAdd = new JButton("계정추가");
		jbtDel = new JButton("계정삭제");
		jbtOk = new JButton("확인");

		setLayout(null);

		jspJTable.setBounds(10, 10, 375, 300);
		jbtAdd.setBounds(40, 350, 90, 40);
		jbtDel.setBounds(155, 350, 90, 40);
		jbtOk.setBounds(270, 350, 90, 40);

		add(jspJTable);
		add(jbtAdd);
		add(jbtDel);
		add(jbtOk);

		PMManagerAccountController pmmac = new PMManagerAccountController(this);

		addWindowListener(pmmac);
		jbtOk.addActionListener(pmmac);
		jbtAdd.addActionListener(pmmac);
		jbtDel.addActionListener(pmmac);

		jtAccount.addMouseListener(pmmac);
		
		setBounds(800, 200, 400, 450);
		setVisible(true);
		setResizable(false);

	} // PMManagerAccountView

	public JButton getJbtOk() {
		return jbtOk;
	}
	public JButton getJbtAdd() {
		return jbtAdd;
	}
	public JButton getJbtDel() {
		return jbtDel;
	}

	public DefaultTableModel getDtmAccount() {
		return dtmAccount;
	}
	public JTable getJtAccount() {
		return jtAccount;
	}

}// class
