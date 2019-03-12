package kr.co.sist.pcbang.manager.statics.food;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PMFoodSalesView extends JPanel {
	private DefaultTableModel dtmFoodSales;
	private JLabel jlb;
	private PMFoodSalesDAO pmfsdao;
	// private JScrollPane jspFS;
	// private JTable jtFoodSales;

	public PMFoodSalesView() {
		pmfsdao = PMFoodSalesDAO.getInstance();
		String[] foodSalesColumns = { "����", "����", "��ǰ�̸�", "��ǰ�ڵ�", "ī�װ�", "���Ǹŷ�", "����", "�Ѱ���" };
		dtmFoodSales = new DefaultTableModel(foodSalesColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		//JTable jtFoodSales = new JTable(dtmFoodSales);
		JTable jtFoodSales = new JTable(dtmFoodSales){
			@Override //�̹����� �� �� �ֵ���
			public Class getColumnClass(int column) {//���׸� ���� �ʰ�
				return getValueAt(0, column).getClass();
			}
		};//jtMenu

		//750 { "����", "����", "��ǰ�̸�", "��ǰ�ڵ�", "ī�װ�", "���Ǹŷ�", "����", "�Ѱ���" };
		jtFoodSales.getColumnModel().getColumn(0).setPreferredWidth(50);
		jtFoodSales.getColumnModel().getColumn(1).setPreferredWidth(140);
		jtFoodSales.getColumnModel().getColumn(2).setPreferredWidth(120);
		jtFoodSales.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtFoodSales.getColumnModel().getColumn(4).setPreferredWidth(80);
		jtFoodSales.getColumnModel().getColumn(5).setPreferredWidth(80);
		jtFoodSales.getColumnModel().getColumn(6).setPreferredWidth(80);
		jtFoodSales.getColumnModel().getColumn(7).setPreferredWidth(100);
		jtFoodSales.setRowHeight(110);

		JScrollPane jspFS = new JScrollPane(jtFoodSales);

		setLayout(new BorderLayout());

		jlb = new JLabel("��ȸ�Ⱓ ����");
		add("North", jlb);
		add("Center", jspFS);

	}

	public void inputData(String beforeDate, String afterDate) {
		dtmFoodSales.setRowCount(0);

		try {
			// DB���� ��ǰ ������ ��ȸ
			List<PMFoodSalesVO> list = pmfsdao.selectFoodSales(beforeDate, afterDate);

			// JTable�� ��ȸ�� ������ ���
			PMFoodSalesVO pmfsvo = null;

			String path = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = path + filepath + "s_";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {
				pmfsvo = list.get(i);
				// DTM�� ���� ���� 1�����迭 Ȥ�� ���ͷ� �־�� ��
				rowData = new Object[8];
				rowData[0] = (i + 1) + "��";
				rowData[1] = new ImageIcon(imgPath + pmfsvo.getImg());
				rowData[2] = pmfsvo.getMenuName();
				rowData[3] = pmfsvo.getMenuCode();
				rowData[4] = pmfsvo.getCategory();
				rowData[5] = new Integer(pmfsvo.getQuans());
				rowData[6] = new Integer(pmfsvo.getMenuPrice());
				rowData[7] = new Integer(pmfsvo.getTotal());

				// DTM�� �߰�
				dtmFoodSales.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// �Էµ� ��ǰ�� ���� ��
				rowData = new Object[8];
				rowData[0] = "";
				rowData[1] = "�Էµ�";
				rowData[2] = "��ǰ��";
				rowData[3] = "�����ϴ�";
				rowData[4] = "";
				rowData[5] = "";
				rowData[6] = "";
				rowData[7] = "";

				// DTM�� �߰�
				dtmFoodSales.addRow(rowData);
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "DB���� �����͸� �޾ƿ��� �� ���� �߻�");
			e.printStackTrace();
		} // end catch

	}// end inputData

}
