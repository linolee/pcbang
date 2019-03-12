package kr.co.sist.pcbang.manager.statics;

import java.awt.Color;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;

public class PMStaticsView extends JPanel {
	// 190304 이재찬 작성
	private JRadioButton jrbOperRate, jrbFoodSell, jrbTotalSell;
	private JButton jbtFoodDetail, jbtCreateTable, jbtCreateGraph;

	private JComboBox<String> jcbBeforeYear, jcbBeforeMonth, jcbBeforeDay;
	private JComboBox<String> jcbAfterYear, jcbAfterMonth, jcbAfterDay;
	private JPanel jlbResult;

	private DefaultTableModel dtmMonthOper, dtmDayOper, dtmMonthFood, dtmDayFood, dtmMonthTotal, dtmDayTotal, dtmMaxData, dtmSumData;
	private JScrollPane jspMT, jspDT, jspMF, jspDF, jspMO, jspDO, jspMax, jspSum;

	private CategoryPlot plot;
	private JFreeChart chart;
	private ChartPanel cp;
	
	public PMStaticsView() {
		//String[] category = { "일간 통계", "주간 통계", "월간 통계" };
		//DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<String>(category);
		plot = new CategoryPlot();
		chart = new JFreeChart(plot);
		cp = new ChartPanel(chart);
		
		jcbBeforeYear = new JComboBox<String>();
		jcbBeforeMonth = new JComboBox<String>();
		jcbBeforeDay = new JComboBox<String>();

		jcbAfterYear = new JComboBox<String>();
		jcbAfterMonth = new JComboBox<String>();
		jcbAfterDay = new JComboBox<String>();

		setDate(jcbBeforeYear, jcbBeforeMonth, jcbBeforeDay); //
		setDate(jcbAfterYear, jcbAfterMonth, jcbAfterDay); //

		ButtonGroup bg = new ButtonGroup();
		jrbOperRate = new JRadioButton("가동률");
		jrbFoodSell = new JRadioButton("음식 매출");
		jrbTotalSell = new JRadioButton("총 매출액");
		bg.add(jrbOperRate);
		bg.add(jrbFoodSell);
		bg.add(jrbTotalSell);
		jrbOperRate.setSelected(true);
		jbtFoodDetail = new JButton("음식 상세 통계");
		jbtCreateTable = new JButton("표 생성");
		jbtCreateGraph = new JButton("그래프 생성");

		jlbResult = new JPanel();
		jlbResult.setBackground(Color.WHITE);
		jlbResult.setLayout(null);

		String[] monthOperColumns = { "주차", "가동율" };
		String[] dayOperColumns = { "일자", "가동율" };
		String[] maxColumns = { "일자", "최고값" };
		String[] sumColumn = { "매출 총 합" };
		dtmMonthOper = new DefaultTableModel(monthOperColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		dtmDayOper = new DefaultTableModel(dayOperColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		dtmMaxData = new DefaultTableModel(maxColumns, 1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		dtmSumData = new DefaultTableModel(sumColumn, 1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		JTable jtMonthOper = new JTable(dtmMonthOper);
		JTable jtDayOper = new JTable(dtmDayOper);
		JTable jtMax = new JTable(dtmMaxData);
		JTable jtSum = new JTable(dtmSumData);

		// 가동률 테이블 크기 설정 : 전체 200
		jtMonthOper.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtMonthOper.getColumnModel().getColumn(1).setPreferredWidth(140);
		jtMonthOper.setRowHeight(25);
		jtDayOper.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtDayOper.getColumnModel().getColumn(1).setPreferredWidth(140);
		jtDayOper.setRowHeight(25);
		jtMax.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtMax.getColumnModel().getColumn(1).setPreferredWidth(140);
		jtMax.setRowHeight(25);
		jtSum.getColumnModel().getColumn(0).setPreferredWidth(340);
		jtSum.setRowHeight(25);

		jspMO = new JScrollPane(jtMonthOper);
		jspDO = new JScrollPane(jtDayOper);
		jspMax = new JScrollPane(jtMax);
		jspSum = new JScrollPane(jtSum);
		jspMO.setBorder(new TitledBorder("가동률 통계"));
		jspDO.setBorder(new TitledBorder("가동률 통계"));

		String[] monthFoodColumns = { "주차", "상품 매출액" };
		String[] dayFoodColumns = { "일자", "상품 매출액" };
		dtmMonthFood = new DefaultTableModel(monthFoodColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		dtmDayFood = new DefaultTableModel(dayFoodColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		
		JTable jtMonthFood = new JTable(dtmMonthFood);
		JTable jtDayFood = new JTable(dtmDayFood);

		// 상품 매출액 테이블 크기 설정 : 전체 200
		jtMonthFood.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtMonthFood.getColumnModel().getColumn(1).setPreferredWidth(140);
		jtMonthFood.setRowHeight(25);
		jtDayFood.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtDayFood.getColumnModel().getColumn(1).setPreferredWidth(140);
		jtDayFood.setRowHeight(25);

		jspMF = new JScrollPane(jtMonthFood);
		jspDF = new JScrollPane(jtDayFood);
		jspMF.setBorder(new TitledBorder("상품 매출 통계"));
		jspDF.setBorder(new TitledBorder("상품 매출 통계"));

		String[] monthTotalColumns = { "주차", "총 매출액" };
		String[] dayTotalColumns = { "일자", "총 매출액" };
		dtmMonthTotal = new DefaultTableModel(monthTotalColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		dtmDayTotal = new DefaultTableModel(dayTotalColumns, 4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};
		JTable jtMonthTotal = new JTable(dtmMonthTotal);
		JTable jtDayTotal = new JTable(dtmDayTotal);

		// 상품 매출액 테이블 크기 설정 : 전체 200
		jtMonthTotal.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtMonthTotal.getColumnModel().getColumn(1).setPreferredWidth(140);
		jtMonthTotal.setRowHeight(25);
		jtDayTotal.getColumnModel().getColumn(0).setPreferredWidth(200);
		jtDayTotal.getColumnModel().getColumn(1).setPreferredWidth(140);
		jtDayTotal.setRowHeight(25);

		jspMT = new JScrollPane(jtMonthTotal);
		jspDT = new JScrollPane(jtDayTotal);
		jspMT.setBorder(new TitledBorder("총 매출 통계"));
		jspDT.setBorder(new TitledBorder("총 매출 통계"));

		setLayout(null);

		JLabel jlb1 = new JLabel("조회기간 설정");
		JLabel jlb2 = new JLabel("~");
		add(jlb1);

		add(jcbBeforeYear);
		add(jcbBeforeMonth);
		add(jcbBeforeDay);
		add(jlb2);
		add(jcbAfterYear);
		add(jcbAfterMonth);
		add(jcbAfterDay);

		add(jrbOperRate);
		add(jrbFoodSell);
		add(jrbTotalSell);
		add(jbtFoodDetail);
		add(jbtCreateTable);
		add(jbtCreateGraph);
		add(jlbResult);
		
		jlbResult.add(jspMT);
		jlbResult.add(jspDT);
		jlbResult.add(jspMF);
		jlbResult.add(jspDF);
		jlbResult.add(jspMO);
		jlbResult.add(jspDO);
		jlbResult.add(jspMax);
		jlbResult.add(jspSum);
		
		jlbResult.add(cp);
		
		jlb1.setBounds(30, 20, 100, 25);
		jcbBeforeYear.setBounds(120, 20, 60, 25);
		jcbBeforeMonth.setBounds(180, 20, 40, 25);
		jcbBeforeDay.setBounds(220, 20, 40, 25);
		jlb2.setBounds(265, 21, 10, 25);
		jcbAfterYear.setBounds(280, 20, 60, 25);
		jcbAfterMonth.setBounds(340, 20, 40, 25);
		jcbAfterDay.setBounds(380, 20, 40, 25);

		jrbOperRate.setBounds(830, 150, 100, 25);
		jrbFoodSell.setBounds(830, 180, 100, 25);
		jrbTotalSell.setBounds(830, 210, 100, 25);
		jbtFoodDetail.setBounds(830, 280, 120, 25);
		jbtCreateTable.setBounds(830, 350, 120, 40);
		jbtCreateGraph.setBounds(830, 400, 120, 40);
		jlbResult.setBounds(30, 60, 750, 490);
		
		jspMT.setBounds(20, 20, 340, 450);
		jspDT.setBounds(20, 20, 340, 450);
		jspMF.setBounds(20, 20, 340, 450);
		jspDF.setBounds(20, 20, 340, 450);
		jspMO.setBounds(20, 20, 340, 450);
		jspDO.setBounds(20, 20, 340, 450);
		jspMax.setBounds(390, 20, 340, 80);
		jspSum.setBounds(390, 120, 340, 80);
		
		cp.setBounds(0, 0, 750, 490);
		
		jspMT.setVisible(false);
		jspDT.setVisible(false);
		jspMF.setVisible(false);
		jspDF.setVisible(false);
		jspMO.setVisible(false);
		jspDO.setVisible(false);
		jspMax.setVisible(false);
		jspSum.setVisible(false);
		cp.setVisible(false);

		PMStaticsController pmsc = new PMStaticsController(this);
		jbtFoodDetail.addActionListener(pmsc);
		jbtCreateTable.addActionListener(pmsc);
		jbtCreateGraph.addActionListener(pmsc);

		jcbBeforeYear.addActionListener(pmsc);
		jcbBeforeMonth.addActionListener(pmsc);
		jcbAfterYear.addActionListener(pmsc);
		jcbAfterMonth.addActionListener(pmsc);
	}

	private void setDate(JComboBox<String> jcbYear, JComboBox<String> jcbMonth, JComboBox<String> jcbDay) {
		DefaultComboBoxModel<String> dcbSetYear, dcbSetMonth, dcbSetDay;
		Calendar cal = Calendar.getInstance();

		String year = "";
		String month = "";
		String day = "";
		for (int i = cal.get(Calendar.YEAR); 2017 <= i; i--) {
			year += i + ",";
		}
		for (int i = 1; i <= 12; i++) {
			month += i + ",";
		}
		for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			day += i + ",";
		}
		dcbSetYear = new DefaultComboBoxModel<String>(year.split(","));
		dcbSetMonth = new DefaultComboBoxModel<String>(month.split(","));
		dcbSetDay = new DefaultComboBoxModel<String>(day.split(","));
		jcbYear.setModel(dcbSetYear);
		jcbMonth.setModel(dcbSetMonth);
		jcbDay.setModel(dcbSetDay);

		jcbYear.setSelectedIndex(0);
		jcbMonth.setSelectedIndex(cal.get(Calendar.MONTH));
		jcbDay.setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH) - 1);
	}

	public JRadioButton getJrbOperRate() {
		return jrbOperRate;
	}

	public JRadioButton getJrbFoodSell() {
		return jrbFoodSell;
	}

	public JRadioButton getJrbTotalSell() {
		return jrbTotalSell;
	}

	public JButton getJbtFoodDetail() {
		return jbtFoodDetail;
	}

	public JButton getJbtCreateTable() {
		return jbtCreateTable;
	}

	public JButton getJbtCreateGraph() {
		return jbtCreateGraph;
	}

	public JComboBox<String> getJcbBeforeYear() {
		return jcbBeforeYear;
	}

	public JComboBox<String> getJcbBeforeMonth() {
		return jcbBeforeMonth;
	}

	public JComboBox<String> getJcbBeforeDay() {
		return jcbBeforeDay;
	}

	public JComboBox<String> getJcbAfterYear() {
		return jcbAfterYear;
	}

	public JComboBox<String> getJcbAfterMonth() {
		return jcbAfterMonth;
	}

	public JComboBox<String> getJcbAfterDay() {
		return jcbAfterDay;
	}

//	public JLabel getJlb2() {
//		return jlb2;
//	}

	public DefaultTableModel getDtmMonthOper() {
		return dtmMonthOper;
	}

	public DefaultTableModel getDtmDayOper() {
		return dtmDayOper;
	}

	public DefaultTableModel getDtmMonthFood() {
		return dtmMonthFood;
	}

	public DefaultTableModel getDtmDayFood() {
		return dtmDayFood;
	}

	public DefaultTableModel getDtmMonthTotal() {
		return dtmMonthTotal;
	}

	public DefaultTableModel getDtmDayTotal() {
		return dtmDayTotal;
	}

	public JScrollPane getJspMT() {
		return jspMT;
	}

	public JScrollPane getJspDT() {
		return jspDT;
	}

	public JScrollPane getJspMF() {
		return jspMF;
	}

	public JScrollPane getJspDF() {
		return jspDF;
	}

	public JScrollPane getJspMO() {
		return jspMO;
	}

	public JScrollPane getJspDO() {
		return jspDO;
	}

	public DefaultTableModel getDtmMaxData() {
		return dtmMaxData;
	}

	public JScrollPane getJspMax() {
		return jspMax;
	}

	public DefaultTableModel getDtmSumData() {
		return dtmSumData;
	}

	public JScrollPane getJspSum() {
		return jspSum;
	}

	public CategoryPlot getPlot() {
		return plot;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public ChartPanel getCp() {
		return cp;
	}

	public JPanel getJlbResult() {
		return jlbResult;
	}

}
