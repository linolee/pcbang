package kr.co.sist.pcbang.manager.statics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Stack;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import kr.co.sist.pcbang.manager.order.PMOrderVO;
import kr.co.sist.pcbang.manager.statics.food.PMFoodSalesView;

public class PMStaticsController implements ActionListener {//
	private PMStaticsView pmsv;
	private PMStaticsDAO pms_dao;
	private String beforeDate, afterDate;
	private Stack<String> termStack;
	public static final int FullSeatNum = 30;

	private boolean flagTableOperDay, flagTableOperMonth, flagTableFoodDay, flagTableFoodMonth, flagTableTotalDay,
			flagTableTotalMonth;
	private DefaultCategoryDataset dataset;
	
	private PMFoodSalesView pmfsv;

	public PMStaticsController(PMStaticsView pmsv) {
		this.pmsv = pmsv;
		pms_dao = PMStaticsDAO.getInstance();
		beforeDate = "";
		afterDate = "";
		termStack = new Stack<String>();
		flagTableOperDay = false;
		flagTableOperMonth = false;
		flagTableFoodDay = false;
		flagTableFoodMonth = false;
		flagTableTotalDay = false;
		flagTableTotalMonth = false;

		// ������ ����
		dataset = new DefaultCategoryDataset(); // line chart
		
		pmfsv = new PMFoodSalesView();
		pmsv.getJlbResult().add(pmfsv);
		pmfsv.setBounds(0, 0, 750, 490);
		pmfsv.setVisible(false);
	}

	// ��¥ �޺��ڽ� ���۽� �� (28~31)����
	private void setDay(Object year, Object month, JComboBox<String> jcbDay) {
		DefaultComboBoxModel<String> dcbSetDay;
		Calendar cal = Calendar.getInstance();
		int m_year = Integer.valueOf((String) year);
		int m_month = Integer.valueOf((String) month);
		cal.set(m_year, m_month - 1, 1);

		String day = "";
		for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			day += i + ",";
		}
		dcbSetDay = new DefaultComboBoxModel<String>(day.split(","));
		jcbDay.setModel(dcbSetDay);
	}

	private void selectStatics(int index) {// ��� ���� ���� - �ϰ������ �� �ڿ� ��¥������ �Ⱥ��̰� ��
		Calendar cal = Calendar.getInstance();
		if (index == 0) {
			pmsv.getJcbBeforeDay().setVisible(true);
			pmsv.getJcbAfterYear().setVisible(false);
			pmsv.getJcbAfterMonth().setVisible(false);
			pmsv.getJcbAfterDay().setVisible(false);
			//pmsv.getJlb2().setVisible(false);
			pmsv.getJcbAfterYear().setSelectedIndex(0);
			pmsv.getJcbAfterMonth().setSelectedIndex(cal.get(Calendar.MONTH));
			pmsv.getJcbAfterDay().setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH) - 1);
		} else if (index == 1) {
			pmsv.getJcbBeforeDay().setVisible(true);
			pmsv.getJcbAfterYear().setVisible(true);
			pmsv.getJcbAfterMonth().setVisible(true);
			pmsv.getJcbAfterDay().setVisible(true);
			//pmsv.getJlb2().setVisible(true);
		} else if (index == 2) {
			pmsv.getJcbAfterYear().setVisible(true);
			pmsv.getJcbAfterMonth().setVisible(true);
			pmsv.getJcbAfterDay().setVisible(false);
			pmsv.getJcbBeforeDay().setVisible(false);
			//pmsv.getJlb2().setVisible(true);
		}
	}

	private boolean effectivenessChk() {// ��ȿ���˻� �� ����� ��¥�ν��Ͻ������� ����
		boolean flag = false;
		int b_year = Integer.valueOf((String) pmsv.getJcbBeforeYear().getSelectedItem());
		int b_month = Integer.valueOf((String) pmsv.getJcbBeforeMonth().getSelectedItem());
		int b_day = Integer.valueOf((String) pmsv.getJcbBeforeDay().getSelectedItem());
		int a_year = Integer.valueOf((String) pmsv.getJcbAfterYear().getSelectedItem());
		int a_month = Integer.valueOf((String) pmsv.getJcbAfterMonth().getSelectedItem());
		int a_day = Integer.valueOf((String) pmsv.getJcbAfterDay().getSelectedItem());

		if (b_year == a_year && b_month == a_month && b_day <= a_day) {
			flag = true;
		} else if (b_year == a_year && b_month < a_month) {
			flag = true;
		} else if (b_year < a_year) {
			flag = true;
		} else {
			flag = false;
		}

		if (flag) {
			beforeDate = setDateForm(String.valueOf(b_year), String.valueOf(b_month), String.valueOf(b_day));
			afterDate = setDateForm(String.valueOf(a_year), String.valueOf(a_month), String.valueOf(a_day));
		}
		return flag;
	}

	// ����� �Է��� 'yy-mm-dd'���·� �ٲ���
	private String setDateForm(String year, String month, String day) {
		StringBuilder sb = new StringBuilder();
		if (month.length() == 1)
			month = "0" + month;
		if (day.length() == 1)
			day = "0" + day;

		sb.append(year).append("-").append(month).append("-").append(day);
		return sb.toString();
	}

	// �� ��¥ ������ ��¥ ������ list�� ������ �� -> stack���� �ٲ�
	private void getTermDate() {
		Calendar b_cal = new GregorianCalendar(Integer.parseInt(beforeDate.substring(0, 4)),
				Integer.parseInt(beforeDate.substring(5, 7)) - 1, Integer.parseInt(beforeDate.substring(8, 10)));
		Calendar a_cal = new GregorianCalendar(Integer.parseInt(afterDate.substring(0, 4)),
				Integer.parseInt(afterDate.substring(5, 7)) - 1, Integer.parseInt(afterDate.substring(8, 10)));

		// �� ��¥�� �ϼ� ��
		long diffSec = (a_cal.getTimeInMillis() - b_cal.getTimeInMillis()) / 1000;
		long diffDays = diffSec / (24 * 60 * 60);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		termStack.push(sdf.format(b_cal.getTime()));
		for (int i = 0; i < diffDays; i++) {
			b_cal.add(Calendar.DAY_OF_MONTH, 1);
			termStack.push(sdf.format(b_cal.getTime()));
		}
	}

	private void statOperatingRate() {// ������ ��� 
		try {
			PMSOperatingTermVO otvo_m = null;
			PMSOperatingTermVO otvo_g = null;
			int day = 1;
			String b_date = "";
			String a_date = "";
			String temp_date = "";
			double operation = 0.00;
			double tempOperation = 0.00;
			int i = 1;
			String[] rowData = new String[2];

			if (termStack.size() > 10) { // �ְ� ����
				while (!termStack.isEmpty()) {
					if (i == 1) {
						a_date = termStack.pop();
						i++;
					} else if (i == 7) {
						b_date = termStack.pop();
						otvo_m = pms_dao.selectMemberTermOperating(b_date, a_date);
						otvo_g = pms_dao.selectGuestTermOperating(b_date, a_date);
						operation = (otvo_m.getSumTime() + otvo_g.getSumTime()) / FullSeatNum / 60 / 24 / i;
						rowData[0] = a_date + " ~ " + b_date;
						rowData[1] = String.valueOf(operation) + "%";
						pmsv.getDtmMonthOper().addRow(rowData);
						if (pmsv.getDtmMaxData().getRowCount() == 0) { // �ʱⰪ
							pmsv.getDtmMaxData().addRow(rowData);
							tempOperation = operation;
						}
						if (tempOperation < operation) { // �ִ밪
							pmsv.getDtmMaxData().setRowCount(0);
							pmsv.getDtmMaxData().addRow(rowData);
							tempOperation = operation;
						}
						// ������ �Է� ( ��, ����, ī�װ� )
						dataset.addValue(operation, "������", a_date.substring(5) + " ~ " + b_date.substring(5));
						i = 1;
						day++;
					} else {
						temp_date = termStack.pop();
						i++;
					}
				}
				if (termStack.isEmpty()) {
					if (i == 1) {
						a_date = beforeDate;
					}
					b_date = beforeDate;
					otvo_m = pms_dao.selectMemberTermOperating(b_date, a_date);
					otvo_g = pms_dao.selectGuestTermOperating(b_date, a_date);
					operation = (otvo_m.getSumTime() + otvo_g.getSumTime()) / FullSeatNum / i; // 60 / 24
					rowData[0] = a_date + " ~ " + b_date;
					rowData[1] = String.valueOf(operation) + "%";
					pmsv.getDtmMonthOper().addRow(rowData);
					if (pmsv.getDtmMaxData().getRowCount() == 0) {// �ʱⰪ
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					if (tempOperation < operation) { // �ִ밪
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					// ������ �Է� ( ��, ����, ī�װ� )
					dataset.addValue(operation, "������", a_date.substring(5) + " ~ " + b_date.substring(5));
				}
				flagTableOperMonth = true; // ���
			} else { ///////////// �ϰ� ����
				while (!termStack.isEmpty()) {
					a_date = termStack.pop();
					otvo_m = pms_dao.selectMemberTermOperating(a_date, a_date);
					otvo_g = pms_dao.selectGuestTermOperating(a_date, a_date);
					operation = (otvo_m.getSumTime() + otvo_g.getSumTime()) / FullSeatNum / i; /// 60 / 24
					rowData[0] = a_date;
					rowData[1] = String.valueOf(operation) + "%";
					pmsv.getDtmDayOper().addRow(rowData);
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // �ʱⰪ
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					if (tempOperation < operation) { // �ִ밪
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					// ������ �Է� ( ��, ����, ī�װ� )
					dataset.addValue(operation, "������", a_date.substring(5));
					flagTableOperDay = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// end statOperatingRate

	private void statFoodSell() {// ���ĸ��� ���
		try {
			PMSOrderVO ovo = null;
			int day = 1;
			String b_date = "";
			String a_date = "";
			String temp_date = "";
			int total = 0;
			int tempTotal = 0;
			int i = 1;
			int sum = 0;
			Vector rowSum = new Vector<String>();
			String[] rowData = new String[2];

			if (termStack.size() > 10) { // �ְ� ����
				while (!termStack.isEmpty()) {
					if (i == 1) {
						a_date = termStack.pop();
						i++;
					} else if (i == 7) {
						b_date = termStack.pop();
						ovo = pms_dao.selectTermFoodSell(b_date, a_date);
						total = ovo.getTotal();
						rowData[0] = a_date + " ~ " + b_date;
						rowData[1] = String.format("%,d", total) + "��";
						pmsv.getDtmMonthFood().addRow(rowData);
						sum += total;
						if (pmsv.getDtmMaxData().getRowCount() == 0) { // �ʱⰪ
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						if (tempTotal < total) { // �ִ밪
							pmsv.getDtmMaxData().setRowCount(0);
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						// ������ �Է� ( ��, ����, ī�װ� )
						dataset.addValue(total, "���� ����", a_date.substring(5) + " ~ " + b_date.substring(5));
						i = 1;
						day++;
					} else {
						temp_date = termStack.pop();
						i++;
					}
				}
				if (termStack.isEmpty()) {
					if (i == 1) {
						a_date = beforeDate;
					}
					b_date = beforeDate;
					ovo = pms_dao.selectTermFoodSell(b_date, a_date);
					total = ovo.getTotal();
					rowData[0] = a_date + " ~ " + b_date;
					rowData[1] = String.format("%,d", total) + "��";
					pmsv.getDtmMonthFood().addRow(rowData);
					sum += total;
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // �ʱⰪ
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if (tempTotal < total) { // �ִ밪
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					// ������ �Է� ( ��, ����, ī�װ� )
					dataset.addValue(total, "���� ����", a_date.substring(5) + " ~ " + b_date.substring(5));
				}
				flagTableFoodMonth = true; // ���
			} else { ///////////// �ϰ� ����
				while (!termStack.isEmpty()) {
					a_date = termStack.pop();
					ovo = pms_dao.selectTermFoodSell(a_date, a_date);
					total = ovo.getTotal();
					rowData[0] = a_date;
					rowData[1] = String.format("%,d", total) + "��";
					pmsv.getDtmDayFood().addRow(rowData);
					sum += total;
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // �ʱⰪ
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if (tempTotal < total) { // �ִ밪
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					// ������ �Է� ( ��, ����, ī�װ� )
					dataset.addValue(total, "���� ����", a_date.substring(5));
					flagTableFoodDay = true;
				}
			}
			pmsv.getDtmSumData().setRowCount(0);
			rowSum.add(String.format("%,d", sum) + "��");
			pmsv.getDtmSumData().addRow(rowSum);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end statFoodSell

	private void statTotalSell() {// �Ѹ��� ��� 
		try {
			PMSOrderVO ovo = null;
			PMSOrderVO ovo_charge_m = null;
			PMSOrderVO ovo_charge_g = null;
			int day = 1;
			String b_date = "";
			String a_date = "";
			String temp_date = "";
			int total = 0;
			int tempTotal = 0;
			int i = 1;
			int sum = 0;
			Vector rowSum = new Vector<String>();
			String[] rowData = new String[2];

			if (termStack.size() > 10) { // �ְ� ����
				while (!termStack.isEmpty()) {
					if (i == 1) {
						a_date = termStack.pop();
						i++;
					} else if (i == 7) {
						b_date = termStack.pop();
						ovo = pms_dao.selectTermFoodSell(b_date, a_date);
						ovo_charge_m = pms_dao.selectTermChargePriceMember(b_date, a_date);
						ovo_charge_g = pms_dao.selectTermChargePriceGuest(b_date, a_date);
						total = ovo.getTotal() + ovo_charge_m.getTotal() + ovo_charge_g.getTotal();
						rowData[0] = a_date + " ~ " + b_date;
						rowData[1] = String.format("%,d", total) + "��";
						pmsv.getDtmMonthTotal().addRow(rowData);
						sum += total;
						if (pmsv.getDtmMaxData().getRowCount() == 0) { // �ʱⰪ
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						if (tempTotal < total) { // �ִ밪
							pmsv.getDtmMaxData().setRowCount(0);
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						// ������ �Է� ( ��, ����, ī�װ� )
						dataset.addValue(total, "�� ����", a_date.substring(5) + " ~ " + b_date.substring(5));
						i = 1;
						day++;
					} else {
						temp_date = termStack.pop();
						i++;
					}
				}
				if (termStack.isEmpty()) {
					if (i == 1) {
						a_date = beforeDate;
					}
					b_date = beforeDate;
					ovo = pms_dao.selectTermFoodSell(b_date, a_date);
					ovo_charge_m = pms_dao.selectTermChargePriceMember(b_date, a_date);
					ovo_charge_g = pms_dao.selectTermChargePriceGuest(b_date, a_date);
					total = ovo.getTotal() + ovo_charge_m.getTotal() + ovo_charge_g.getTotal();
					rowData[0] = a_date + " ~ " + b_date;
					rowData[1] = String.format("%,d", total) + "��";
					pmsv.getDtmMonthTotal().addRow(rowData);
					sum += total;
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // �ʱⰪ
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if (tempTotal < total) { // �ִ밪
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					// ������ �Է� ( ��, ����, ī�װ� )
					dataset.addValue(total, "�� ����", a_date.substring(5) + " ~ " + b_date.substring(5));
				}
				flagTableTotalMonth = true; // ���
			} else { ///////////// �ϰ� ����
				while (!termStack.isEmpty()) {
					a_date = termStack.pop();
					ovo = pms_dao.selectTermFoodSell(a_date, a_date);
					ovo_charge_m = pms_dao.selectTermChargePriceMember(a_date, a_date);
					ovo_charge_g = pms_dao.selectTermChargePriceGuest(a_date, a_date);
					total = ovo.getTotal() + ovo_charge_m.getTotal() + ovo_charge_g.getTotal();
					rowData[0] = a_date;
					rowData[1] = String.format("%,d", total) + "��";
					pmsv.getDtmDayTotal().addRow(rowData);
					sum += total;
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // �ʱⰪ
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if (tempTotal < total) { // �ִ밪
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					// ������ �Է� ( ��, ����, ī�װ� )
					dataset.addValue(total, "�� ����", a_date.substring(5));
					flagTableTotalDay = true;
				}
			}

			pmsv.getDtmSumData().setRowCount(0);
			rowSum.add(String.format("%,d", sum) + "��");
			pmsv.getDtmSumData().addRow(rowSum);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end statFoodSell

	private void printTable() {// ǥ ���
		pmsv.getJspDO().setVisible(false);
		pmsv.getJspDT().setVisible(false);
		pmsv.getJspDF().setVisible(false);
		pmsv.getJspMO().setVisible(false);
		pmsv.getJspMT().setVisible(false);
		pmsv.getJspMF().setVisible(false);
		pmsv.getJspMax().setVisible(false);
		pmsv.getJspSum().setVisible(false);
		pmsv.getCp().setVisible(false);
		pmfsv.setVisible(false);
		pmsv.getDtmMonthOper().setRowCount(0);
		pmsv.getDtmMonthFood().setRowCount(0);
		pmsv.getDtmMonthTotal().setRowCount(0);
		pmsv.getDtmDayOper().setRowCount(0);
		pmsv.getDtmDayFood().setRowCount(0);
		pmsv.getDtmDayTotal().setRowCount(0);
		pmsv.getDtmMaxData().setRowCount(0);
		pmsv.getDtmSumData().setRowCount(0);
		flagTableOperDay = false;
		flagTableOperMonth = false;
		flagTableFoodDay = false;
		flagTableFoodMonth = false;
		flagTableTotalDay = false;
		flagTableTotalMonth = false;

		// ��� ����(������, ���ĸ���, �Ѹ���)�� ���� �Ⱓ�� ���� �����Ͱ� �޾ƿ�
		if (pmsv.getJrbOperRate().isSelected()) {
			statOperatingRate(); 
			if (flagTableOperDay) {
				pmsv.getJspDO().setVisible(true);
			} else if (flagTableOperMonth) {
				pmsv.getJspMO().setVisible(true);
			}
			pmsv.getJspMax().setBorder(new TitledBorder("�ְ� ������"));
		} else if (pmsv.getJrbFoodSell().isSelected()) {
			statFoodSell();
			if (flagTableFoodDay) {
				pmsv.getJspDF().setVisible(true);
			} else if (flagTableFoodMonth) {
				pmsv.getJspMF().setVisible(true);
			}
			pmsv.getJspMax().setBorder(new TitledBorder("�ְ� ���� ����"));
			pmsv.getJspSum().setBorder(new TitledBorder("���� ����� �� ��"));
			pmsv.getJspSum().setVisible(true);
		} else if (pmsv.getJrbTotalSell().isSelected()) {
			statTotalSell();
			if (flagTableTotalDay) {
				pmsv.getJspDT().setVisible(true);
			} else if (flagTableTotalMonth) {
				pmsv.getJspMT().setVisible(true);
			}
			pmsv.getJspMax().setBorder(new TitledBorder("�ְ� �� ����"));
			pmsv.getJspSum().setBorder(new TitledBorder("����� �� ��"));
			pmsv.getJspSum().setVisible(true);
		}
		pmsv.getJspMax().setVisible(true);

	}// end printTable

	private void printGraph() {// �׷��� ���
		pmsv.getJspDO().setVisible(false);
		pmsv.getJspDT().setVisible(false);
		pmsv.getJspDF().setVisible(false);
		pmsv.getJspMO().setVisible(false);
		pmsv.getJspMT().setVisible(false);
		pmsv.getJspMF().setVisible(false);
		pmsv.getJspMax().setVisible(false);
		pmsv.getJspSum().setVisible(false);
		pmsv.getCp().setVisible(false);
		pmfsv.setVisible(false);
		dataset.clear();

		// ������ ���� �� ����
		// ������ ����
		final LineAndShapeRenderer renderer = new LineAndShapeRenderer();

		// ���� �ɼ� ����
		final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
		final ItemLabelPosition p_center = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		final ItemLabelPosition p_below = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT);
		Font f = new Font("Gulim", Font.BOLD, 14);
		Font axisF = new Font("Gulim", Font.PLAIN, 12);

		// ������ ����
		renderer.setBaseItemLabelGenerator(generator);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		renderer.setBaseFillPaint(Color.WHITE);
		renderer.setBaseItemLabelFont(f);
		renderer.setBasePositiveItemLabelPosition(p_below);
		renderer.setSeriesPaint(0, new Color(219, 121, 22));
		renderer.setSeriesStroke(0, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 3.0f));

		// plot ����
		CategoryPlot plot = pmsv.getPlot();

		// plot �� ������ ����
		plot.setDataset(2, dataset);
		plot.setRenderer(2, renderer);

		// plot �⺻ ����
		plot.setOrientation(PlotOrientation.VERTICAL); // �׷��� ǥ�� ����
		plot.setRangeGridlinesVisible(true); // X�� ���̵� ���� ǥ�ÿ���
		plot.setDomainGridlinesVisible(true); // Y�� ���̵� ���� ǥ�ÿ���

		// ������ ���� ���� : dataset ��� ������� ������ ( ��, ���� ����Ѱ� �Ʒ��� �� )
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		// X�� ����
		plot.setDomainAxis(new CategoryAxis()); // X�� ���� ����
		plot.getDomainAxis().setTickLabelFont(axisF); // X�� ���ݶ� ��Ʈ ����
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD); // ī�װ� �� ��ġ ����

		// Y�� ����
		plot.setRangeAxis(new NumberAxis()); // Y�� ���� ����
		plot.getRangeAxis().setTickLabelFont(axisF); // Y�� ���ݶ� ��Ʈ ����

		// ���õ� plot�� �������� chart ����
		JFreeChart chart = pmsv.getChart();

		// ��� ����(������, ���ĸ���, �Ѹ���)�� ���� �Ⱓ�� ���� �����Ͱ� �޾ƿ�
		if (pmsv.getJrbOperRate().isSelected()) {
			statOperatingRate();// statOperatingRate(staticsChoice);
			chart.setTitle("������ ��� �׷���"); // ��Ʈ Ÿ��Ʋ
		} else if (pmsv.getJrbFoodSell().isSelected()) {
			statFoodSell();
			chart.setTitle("���� ���� ��� �׷���"); // ��Ʈ Ÿ��Ʋ
		} else if (pmsv.getJrbTotalSell().isSelected()) {
			statTotalSell();
			chart.setTitle("�� ���� ��� �׷���"); // ��Ʈ Ÿ��Ʋ
		}

		pmsv.getCp().setVisible(true);
	}// end printGraph

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == pmsv.getJbtFoodDetail()) {// ���� �� ��� ��ư
			if (effectivenessChk()) {// ��賯¥ ��ȿ�� �˻�
				if (beforeDate.equals(afterDate)) {
					JOptionPane.showMessageDialog(pmsv, "�Ϸ�ġ�� ���� Ȯ���Ͻ� �� �����ϴ�.\n��¥�� �������ּ���.");
					return;
				} else {
					getTermDate(); // ���ÿ� ��¥������ �Է�
					if (termStack.size() > 70) {
						JOptionPane.showMessageDialog(pmsv, "10��ġ �̻��� ��赥���͸� ǥ���� �� �����ϴ�.\n��¥�� �������ּ���.");
						termStack.clear();
						return;
					} else {
						pmsv.getJspDO().setVisible(false);
						pmsv.getJspDT().setVisible(false);
						pmsv.getJspDF().setVisible(false);
						pmsv.getJspMO().setVisible(false);
						pmsv.getJspMT().setVisible(false);
						pmsv.getJspMF().setVisible(false);
						pmsv.getJspMax().setVisible(false);
						pmsv.getJspSum().setVisible(false);
						pmsv.getCp().setVisible(false);
						termStack.clear();
						
						pmfsv.inputData(beforeDate, afterDate);
						pmfsv.setVisible(true);//���� �� ��� ���̱�
						//JOptionPane.showMessageDialog(pmsv, "���� �� ��� ��ư");
					}
				}
			} else {
				JOptionPane.showMessageDialog(pmsv, "��¥�Է��� �߸��Ǿ��ų� �ش� ��¥�� ����ڷᰡ �����ϴ�. ");
			}
		}

		if (ae.getSource() == pmsv.getJbtCreateTable()) {// ǥ ���� ��ư
			if (effectivenessChk()) {// ��賯¥ ��ȿ�� �˻�
				if (beforeDate.equals(afterDate)) {
					JOptionPane.showMessageDialog(pmsv, "�Ϸ�ġ�� ���� Ȯ���Ͻ� �� �����ϴ�.\n��¥�� �������ּ���.");
					return;
				} else {
					getTermDate(); // ���ÿ� ��¥������ �Է�
					if (termStack.size() > 70) {
						JOptionPane.showMessageDialog(pmsv, "10��ġ �̻��� ��赥���͸� ǥ���� �� �����ϴ�.\n��¥�� �������ּ���.");
						termStack.clear();
						return;
					} else {
						printTable();// ǥ ���
					}
				}
			} else {
				JOptionPane.showMessageDialog(pmsv, "��¥�Է��� �߸��Ǿ��ų� �ش� ��¥�� ����ڷᰡ �����ϴ�. ");
			}
		}
		if (ae.getSource() == pmsv.getJbtCreateGraph()) {// �׷��� ���� ��ư
			if (effectivenessChk()) {// ��賯¥ ��ȿ�� �˻�
				if (beforeDate.equals(afterDate)) {
					JOptionPane.showMessageDialog(pmsv, "�Ϸ�ġ�� ���� Ȯ���Ͻ� �� �����ϴ�.\n��¥�� �������ּ���.");
					return;
				} else {
					getTermDate(); // ���ÿ� ��¥������ �Է�
					printGraph();// �׷��� ���
				}
			} else {
				JOptionPane.showMessageDialog(pmsv, "��¥�Է��� �߸��Ǿ��ų� �ش� ��¥�� ����ڷᰡ �����ϴ�. ");
			}
		}

		// ��¥ �޺��ڽ� ����( ���'��' (28~31)���� )
		if (ae.getSource() == pmsv.getJcbBeforeYear() || ae.getSource() == pmsv.getJcbBeforeMonth()) {
			setDay(pmsv.getJcbBeforeYear().getSelectedItem(), pmsv.getJcbBeforeMonth().getSelectedItem(),
					pmsv.getJcbBeforeDay());
		}
		if (ae.getSource() == pmsv.getJcbAfterYear() || ae.getSource() == pmsv.getJcbAfterMonth()) {// �޺��ڽ� ����
			setDay(pmsv.getJcbAfterYear().getSelectedItem(), pmsv.getJcbAfterMonth().getSelectedItem(),
					pmsv.getJcbAfterDay());
		}

	}//actionPerformed
	
}
