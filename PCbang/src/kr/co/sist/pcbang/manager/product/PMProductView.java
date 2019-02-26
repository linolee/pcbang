package kr.co.sist.pcbang.manager.product;

import java.awt.BorderLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class PMProductView extends JPanel{
	private JComboBox<String> jcbPrdCategory;
	private DefaultComboBoxModel<String> dcCategory;
	private JTextField jtfPrdName;
	private JButton jbtAddPrd, jbtSchPrd, jbtRstPrd;
	private DefaultTableModel dtmPrd;
	private JTable jtMenu;
	private JPanel jplPrd;
	
	public static String adminId;
	
	public PMProductView() {

	//테이블
	String[] menuColumns = {"상품코드", "상품명", "이미지", "가격", "판매량", "총판매액"};
	dtmPrd = new DefaultTableModel(menuColumns, 4) {
		//더블클릭 눌러도 편집되지 못하도록
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}//isCellEditable
	};//dtmPrd
	
	jtMenu = new JTable(dtmPrd) {
		@Override //이미지로 들어갈 수 있도록
		public Class<?> getColumnClass(int column) {//제네릭 쓰지 않고
			return getValueAt(0, column).getClass();
		}
	};//jtMenu
	
	//테이블의 크기를 설정 : 전체 width 800, 도시락이미지 (W122X H110)  
	jtMenu.getColumnModel().getColumn(0).setPreferredWidth(150);
	jtMenu.getColumnModel().getColumn(1).setPreferredWidth(200);
	jtMenu.getColumnModel().getColumn(2).setPreferredWidth(120);
	jtMenu.getColumnModel().getColumn(3).setPreferredWidth(200);
	jtMenu.getColumnModel().getColumn(4).setPreferredWidth(100);
	jtMenu.getColumnModel().getColumn(5).setPreferredWidth(200);
	
	//테이블의 높이
	jtMenu.setRowHeight(110);
	
	JLabel jlProduct = new JLabel("<상품 조회>");
	JLabel jlCategory = new JLabel("카테고리");
	JLabel jlPrdName = new JLabel("상품명");
	
	jbtAddPrd = new JButton("추가");
	jbtSchPrd = new JButton("조회");
	jbtRstPrd = new JButton("초기화");
	
	jtfPrdName = new JTextField(10);
	
	dcCategory = new DefaultComboBoxModel<String>();
	jcbPrdCategory = new JComboBox<String>(dcCategory);
	
	JScrollPane jspPrd = new JScrollPane(jtMenu);
	
	
	JPanel jplPrdNorth = new JPanel();
	jplPrdNorth.add(jlProduct);
	jplPrdNorth.add(jlCategory);
	jplPrdNorth.add(jcbPrdCategory);
	jplPrdNorth.add(jlPrdName);
	jplPrdNorth.add(jtfPrdName);
	jplPrdNorth.add(jbtAddPrd);
	jplPrdNorth.add(jbtSchPrd);
	jplPrdNorth.add(jbtRstPrd);
	
	setLayout(new BorderLayout());
	add("North", jplPrdNorth);
	add("Center", jspPrd);
	
	setCategory(); //Category 설정 ->일단 메소드로 만들어놨으나 DB에서 가져올 수 있도록 바꿔줘야함
	PMProductController pmpc = new PMProductController(this);
	addMouseListener(pmpc);
	
	jtMenu.addMouseListener(pmpc);
	jcbPrdCategory.addActionListener(pmpc);
	jtfPrdName.addActionListener(pmpc);
	jbtAddPrd.addActionListener(pmpc);
	jbtRstPrd.addActionListener(pmpc);
	jbtSchPrd.addActionListener(pmpc);
	
	setVisible(true);
	}//PMProductView

	private void setCategory() {
		String[] category = {"음료", "스낵", "라면"};
		for(int i=0; i<category.length; i++) {
		dcCategory.addElement(category[i]);
		}//end for
	}//setCategory

	public JComboBox<String> getJcbPrdCategory() {
		return jcbPrdCategory;
	}

	public DefaultComboBoxModel<String> getDcCategory() {
		return dcCategory;
	}
	
	public static String getAdminId() {
		return adminId;
	}

	public JTextField getJtfPrdName() {
		return jtfPrdName;
	}

	public JButton getJbtAddPrd() {
		return jbtAddPrd;
	}

	public JButton getJbtSchPrd() {
		return jbtSchPrd;
	}

	public JButton getJbtRstPrd() {
		return jbtRstPrd;
	}

	public DefaultTableModel getDtmPrd() {
		return dtmPrd;
	}

	public JTable getJtMenu() {
		return jtMenu;
	}

	public JPanel getJplPrd() {
		return jplPrd;
	}
	

}//class
