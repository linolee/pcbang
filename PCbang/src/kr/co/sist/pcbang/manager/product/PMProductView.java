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

	//���̺�
	String[] menuColumns = {"��ǰ�ڵ�", "��ǰ��", "�̹���", "����", "�Ǹŷ�", "���Ǹž�"};
	dtmPrd = new DefaultTableModel(menuColumns, 4) {
		//����Ŭ�� ������ �������� ���ϵ���
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}//isCellEditable
	};//dtmPrd
	
	jtMenu = new JTable(dtmPrd) {
		@Override //�̹����� �� �� �ֵ���
		public Class<?> getColumnClass(int column) {//���׸� ���� �ʰ�
			return getValueAt(0, column).getClass();
		}
	};//jtMenu
	
	//���̺��� ũ�⸦ ���� : ��ü width 800, ���ö��̹��� (W122X H110)  
	jtMenu.getColumnModel().getColumn(0).setPreferredWidth(150);
	jtMenu.getColumnModel().getColumn(1).setPreferredWidth(200);
	jtMenu.getColumnModel().getColumn(2).setPreferredWidth(120);
	jtMenu.getColumnModel().getColumn(3).setPreferredWidth(200);
	jtMenu.getColumnModel().getColumn(4).setPreferredWidth(100);
	jtMenu.getColumnModel().getColumn(5).setPreferredWidth(200);
	
	//���̺��� ����
	jtMenu.setRowHeight(110);
	
	JLabel jlProduct = new JLabel("<��ǰ ��ȸ>");
	JLabel jlCategory = new JLabel("ī�װ�");
	JLabel jlPrdName = new JLabel("��ǰ��");
	
	jbtAddPrd = new JButton("�߰�");
	jbtSchPrd = new JButton("��ȸ");
	jbtRstPrd = new JButton("�ʱ�ȭ");
	
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
	
	setCategory(); //Category ���� ->�ϴ� �޼ҵ�� ���������� DB���� ������ �� �ֵ��� �ٲ������
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
		String[] category = {"����", "����", "���"};
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
