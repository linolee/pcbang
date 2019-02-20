package kr.co.sist.pcbang.client.login.newuser.seq;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PUSeqView extends JFrame{

	private JTextField jtfAddr;
	private JButton jbtSearch;
	private JTable jtZipcode;
	
	public PUSeqView() {
		super("주소찾기");
		
		jtfAddr = new JTextField();
		jbtSearch = new JButton("조회");
		jtZipcode = new JTable();
		
		JScrollPane jspJTable = new JScrollPane(jtZipcode);
		
		setLayout(null);

		jtfAddr.setBounds(20, 20, 100, 30);
		jbtSearch.setBounds(130, 20, 60, 30);
		jspJTable.setBounds(20, 65, 355, 290);
		
		add(jtfAddr);
		add(jbtSearch);
		add(jspJTable);

		jtZipcode.setBackground(Color.white);
		jtZipcode.setOpaque(true);
		
		setBounds(400, 100, 400, 400);
		setVisible(true);
		setResizable(false);
		
		jtfAddr.requestFocus();

		
	} // PUSeqView
	
} // class
