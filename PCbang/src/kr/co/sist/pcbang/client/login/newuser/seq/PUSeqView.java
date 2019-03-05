package kr.co.sist.pcbang.client.login.newuser.seq;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.pcbang.client.login.newuser.PUNewUserView;


@SuppressWarnings("serial")
public class PUSeqView extends JFrame {

	private JTextField jtfAddr;
	private JTable jtZipcode;
	private DefaultTableModel dtmAddr;
	private JButton jbtSearch;

	public PUSeqView(PUNewUserView punuv) {
		super("주소찾기");

		String[] AddrColumns = { "우편번호", "시도", "구군", "동", "번지", "seq" };
		dtmAddr = new DefaultTableModel(AddrColumns, 6) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		jtZipcode = new JTable(dtmAddr);

		JScrollPane jspJTable = new JScrollPane(jtZipcode);

		jtZipcode.getTableHeader().setReorderingAllowed(false);

		jtZipcode.getColumnModel().getColumn(0).setPreferredWidth(40); // 우편번호
		jtZipcode.getColumnModel().getColumn(1).setPreferredWidth(10); // 시도
		jtZipcode.getColumnModel().getColumn(2).setPreferredWidth(40); // 구군
		jtZipcode.getColumnModel().getColumn(3).setPreferredWidth(180); // 동
		jtZipcode.getColumnModel().getColumn(4).setPreferredWidth(80); // 번지
		jtZipcode.getColumnModel().getColumn(5).setPreferredWidth(30); // seq

		// 테이블의 높이
		jtZipcode.setRowHeight(20);
		jtfAddr = new JTextField();
		jbtSearch = new JButton("조회");

		setLayout(null);

		jtfAddr.setBounds(20, 10, 100, 40);
		jbtSearch.setBounds(130, 10, 60, 40);
		jspJTable.setBounds(20, 65, 605, 390);

		add(jtfAddr);
		add(jbtSearch);
		add(jspJTable);
		
		jtfAddr.setBorder(new TitledBorder("동 입력"));

		jtZipcode.setBackground(Color.white);
		jtZipcode.setOpaque(true);


		PUSeqController pusc = new PUSeqController(this, punuv);
		addWindowListener(pusc);
		
		jtZipcode.addMouseListener(pusc);
		
		jtfAddr.addActionListener(pusc);
		jbtSearch.addActionListener(pusc);

		jtfAddr.requestFocus();
		
		setBounds(690, 200, 650, 500);
		setVisible(true);
		setResizable(false);


	} // PUSeqView

	public JTextField getJtfAddr() {
		return jtfAddr;
	}

	public JTable getJtZipcode() {
		return jtZipcode;
	}

	public DefaultTableModel getDtmAddr() {
		return dtmAddr;
	}

	public JButton getJbtSearch() {
		return jbtSearch;
	}

} // class
