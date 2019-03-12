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
		String[] foodSalesColumns = { "순위", "사진", "상품이름", "상품코드", "카테고리", "총판매량", "가격", "총가격" };
		dtmFoodSales = new DefaultTableModel(foodSalesColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		//JTable jtFoodSales = new JTable(dtmFoodSales);
		JTable jtFoodSales = new JTable(dtmFoodSales){
			@Override //이미지로 들어갈 수 있도록
			public Class getColumnClass(int column) {//제네릭 쓰지 않고
				return getValueAt(0, column).getClass();
			}
		};//jtMenu

		//750 { "순위", "사진", "상품이름", "상품코드", "카테고리", "총판매량", "가격", "총가격" };
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

		jlb = new JLabel("조회기간 설정");
		add("North", jlb);
		add("Center", jspFS);

	}

	public void inputData(String beforeDate, String afterDate) {
		dtmFoodSales.setRowCount(0);

		try {
			// DB에서 상품 정보를 조회
			List<PMFoodSalesVO> list = pmfsdao.selectFoodSales(beforeDate, afterDate);

			// JTable에 조회한 정보를 출력
			PMFoodSalesVO pmfsvo = null;

			String path = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = path + filepath + "s_";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {
				pmfsvo = list.get(i);
				// DTM에 넣을 때는 1차원배열 혹은 벡터로 넣어야 함
				rowData = new Object[8];
				rowData[0] = (i + 1) + "위";
				rowData[1] = new ImageIcon(imgPath + pmfsvo.getImg());
				rowData[2] = pmfsvo.getMenuName();
				rowData[3] = pmfsvo.getMenuCode();
				rowData[4] = pmfsvo.getCategory();
				rowData[5] = new Integer(pmfsvo.getQuans());
				rowData[6] = new Integer(pmfsvo.getMenuPrice());
				rowData[7] = new Integer(pmfsvo.getTotal());

				// DTM에 추가
				dtmFoodSales.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// 입력된 상품이 없을 때
				rowData = new Object[8];
				rowData[0] = "";
				rowData[1] = "입력된";
				rowData[2] = "상품이";
				rowData[3] = "없습니다";
				rowData[4] = "";
				rowData[5] = "";
				rowData[6] = "";
				rowData[7] = "";

				// DTM에 추가
				dtmFoodSales.addRow(rowData);
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "DB에서 데이터를 받아오는 중 문제 발생");
			e.printStackTrace();
		} // end catch

	}// end inputData

}
