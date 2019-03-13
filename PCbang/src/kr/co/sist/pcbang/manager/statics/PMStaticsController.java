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

		// 데이터 생성
		dataset = new DefaultCategoryDataset(); // line chart
		
		pmfsv = new PMFoodSalesView();
		pmsv.getJlbResult().add(pmfsv);
		pmfsv.setBounds(0, 0, 750, 490);
		pmfsv.setVisible(false);
	}

	// 날짜 콤보박스 조작시 일 (28~31)변경
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

	private void selectStatics(int index) {// 통계 종류 선택 - 일간통계일 때 뒤에 날짜선택을 안보이게 함
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

	private boolean effectivenessChk() {// 유효성검사 및 결과를 날짜인스턴스변수에 저장
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

	// 년월일 입력을 'yy-mm-dd'형태로 바꿔줌
	private String setDateForm(String year, String month, String day) {
		StringBuilder sb = new StringBuilder();
		if (month.length() == 1)
			month = "0" + month;
		if (day.length() == 1)
			day = "0" + day;

		sb.append(year).append("-").append(month).append("-").append(day);
		return sb.toString();
	}

	// 두 날짜 사이의 날짜 값들을 list에 저장한 것 -> stack으로 바꿈
	private void getTermDate() {
		Calendar b_cal = new GregorianCalendar(Integer.parseInt(beforeDate.substring(0, 4)),
				Integer.parseInt(beforeDate.substring(5, 7)) - 1, Integer.parseInt(beforeDate.substring(8, 10)));
		Calendar a_cal = new GregorianCalendar(Integer.parseInt(afterDate.substring(0, 4)),
				Integer.parseInt(afterDate.substring(5, 7)) - 1, Integer.parseInt(afterDate.substring(8, 10)));

		// 두 날짜의 일수 차
		long diffSec = (a_cal.getTimeInMillis() - b_cal.getTimeInMillis()) / 1000;
		long diffDays = diffSec / (24 * 60 * 60);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		termStack.push(sdf.format(b_cal.getTime()));
		for (int i = 0; i < diffDays; i++) {
			b_cal.add(Calendar.DAY_OF_MONTH, 1);
			termStack.push(sdf.format(b_cal.getTime()));
		}
	}

	private void statOperatingRate() {// 가동률 통계 
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

			if (termStack.size() > 10) { // 주간 단위
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
						if (pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
							pmsv.getDtmMaxData().addRow(rowData);
							tempOperation = operation;
						}
						if (tempOperation < operation) { // 최대값
							pmsv.getDtmMaxData().setRowCount(0);
							pmsv.getDtmMaxData().addRow(rowData);
							tempOperation = operation;
						}
						// 데이터 입력 ( 값, 범례, 카테고리 )
						dataset.addValue(operation, "가동률", a_date.substring(5) + " ~ " + b_date.substring(5));
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
					if (pmsv.getDtmMaxData().getRowCount() == 0) {// 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					if (tempOperation < operation) { // 최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					// 데이터 입력 ( 값, 범례, 카테고리 )
					dataset.addValue(operation, "가동률", a_date.substring(5) + " ~ " + b_date.substring(5));
				}
				flagTableOperMonth = true; // 출력
			} else { ///////////// 일간 단위
				while (!termStack.isEmpty()) {
					a_date = termStack.pop();
					otvo_m = pms_dao.selectMemberTermOperating(a_date, a_date);
					otvo_g = pms_dao.selectGuestTermOperating(a_date, a_date);
					operation = (otvo_m.getSumTime() + otvo_g.getSumTime()) / FullSeatNum / i; /// 60 / 24
					rowData[0] = a_date;
					rowData[1] = String.valueOf(operation) + "%";
					pmsv.getDtmDayOper().addRow(rowData);
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					if (tempOperation < operation) { // 최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					// 데이터 입력 ( 값, 범례, 카테고리 )
					dataset.addValue(operation, "가동률", a_date.substring(5));
					flagTableOperDay = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// end statOperatingRate

	private void statFoodSell() {// 음식매출 통계
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

			if (termStack.size() > 10) { // 주간 단위
				while (!termStack.isEmpty()) {
					if (i == 1) {
						a_date = termStack.pop();
						i++;
					} else if (i == 7) {
						b_date = termStack.pop();
						ovo = pms_dao.selectTermFoodSell(b_date, a_date);
						total = ovo.getTotal();
						rowData[0] = a_date + " ~ " + b_date;
						rowData[1] = String.format("%,d", total) + "원";
						pmsv.getDtmMonthFood().addRow(rowData);
						sum += total;
						if (pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						if (tempTotal < total) { // 최대값
							pmsv.getDtmMaxData().setRowCount(0);
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						// 데이터 입력 ( 값, 범례, 카테고리 )
						dataset.addValue(total, "음식 매출", a_date.substring(5) + " ~ " + b_date.substring(5));
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
					rowData[1] = String.format("%,d", total) + "원";
					pmsv.getDtmMonthFood().addRow(rowData);
					sum += total;
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if (tempTotal < total) { // 최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					// 데이터 입력 ( 값, 범례, 카테고리 )
					dataset.addValue(total, "음식 매출", a_date.substring(5) + " ~ " + b_date.substring(5));
				}
				flagTableFoodMonth = true; // 출력
			} else { ///////////// 일간 단위
				while (!termStack.isEmpty()) {
					a_date = termStack.pop();
					ovo = pms_dao.selectTermFoodSell(a_date, a_date);
					total = ovo.getTotal();
					rowData[0] = a_date;
					rowData[1] = String.format("%,d", total) + "원";
					pmsv.getDtmDayFood().addRow(rowData);
					sum += total;
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if (tempTotal < total) { // 최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					// 데이터 입력 ( 값, 범례, 카테고리 )
					dataset.addValue(total, "음식 매출", a_date.substring(5));
					flagTableFoodDay = true;
				}
			}
			pmsv.getDtmSumData().setRowCount(0);
			rowSum.add(String.format("%,d", sum) + "원");
			pmsv.getDtmSumData().addRow(rowSum);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end statFoodSell

	private void statTotalSell() {// 총매출 통계 
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

			if (termStack.size() > 10) { // 주간 단위
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
						rowData[1] = String.format("%,d", total) + "원";
						pmsv.getDtmMonthTotal().addRow(rowData);
						sum += total;
						if (pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						if (tempTotal < total) { // 최대값
							pmsv.getDtmMaxData().setRowCount(0);
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						// 데이터 입력 ( 값, 범례, 카테고리 )
						dataset.addValue(total, "총 매출", a_date.substring(5) + " ~ " + b_date.substring(5));
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
					rowData[1] = String.format("%,d", total) + "원";
					pmsv.getDtmMonthTotal().addRow(rowData);
					sum += total;
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if (tempTotal < total) { // 최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					// 데이터 입력 ( 값, 범례, 카테고리 )
					dataset.addValue(total, "총 매출", a_date.substring(5) + " ~ " + b_date.substring(5));
				}
				flagTableTotalMonth = true; // 출력
			} else { ///////////// 일간 단위
				while (!termStack.isEmpty()) {
					a_date = termStack.pop();
					ovo = pms_dao.selectTermFoodSell(a_date, a_date);
					ovo_charge_m = pms_dao.selectTermChargePriceMember(a_date, a_date);
					ovo_charge_g = pms_dao.selectTermChargePriceGuest(a_date, a_date);
					total = ovo.getTotal() + ovo_charge_m.getTotal() + ovo_charge_g.getTotal();
					rowData[0] = a_date;
					rowData[1] = String.format("%,d", total) + "원";
					pmsv.getDtmDayTotal().addRow(rowData);
					sum += total;
					if (pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if (tempTotal < total) { // 최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					// 데이터 입력 ( 값, 범례, 카테고리 )
					dataset.addValue(total, "총 매출", a_date.substring(5));
					flagTableTotalDay = true;
				}
			}

			pmsv.getDtmSumData().setRowCount(0);
			rowSum.add(String.format("%,d", sum) + "원");
			pmsv.getDtmSumData().addRow(rowSum);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end statFoodSell

	private void printTable() {// 표 출력
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

		// 통계 종류(가동률, 음식매충, 총매출)에 따라 기간에 따른 데이터값 받아옴
		if (pmsv.getJrbOperRate().isSelected()) {
			statOperatingRate(); 
			if (flagTableOperDay) {
				pmsv.getJspDO().setVisible(true);
			} else if (flagTableOperMonth) {
				pmsv.getJspMO().setVisible(true);
			}
			pmsv.getJspMax().setBorder(new TitledBorder("최고 가동률"));
		} else if (pmsv.getJrbFoodSell().isSelected()) {
			statFoodSell();
			if (flagTableFoodDay) {
				pmsv.getJspDF().setVisible(true);
			} else if (flagTableFoodMonth) {
				pmsv.getJspMF().setVisible(true);
			}
			pmsv.getJspMax().setBorder(new TitledBorder("최고 음식 매출"));
			pmsv.getJspSum().setBorder(new TitledBorder("음식 매출액 총 합"));
			pmsv.getJspSum().setVisible(true);
		} else if (pmsv.getJrbTotalSell().isSelected()) {
			statTotalSell();
			if (flagTableTotalDay) {
				pmsv.getJspDT().setVisible(true);
			} else if (flagTableTotalMonth) {
				pmsv.getJspMT().setVisible(true);
			}
			pmsv.getJspMax().setBorder(new TitledBorder("최고 총 매출"));
			pmsv.getJspSum().setBorder(new TitledBorder("매출액 총 합"));
			pmsv.getJspSum().setVisible(true);
		}
		pmsv.getJspMax().setVisible(true);

	}// end printTable

	private void printGraph() {// 그래프 출력
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

		// 렌더링 생성 및 세팅
		// 렌더링 생성
		final LineAndShapeRenderer renderer = new LineAndShapeRenderer();

		// 공통 옵션 정의
		final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
		final ItemLabelPosition p_center = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
		final ItemLabelPosition p_below = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT);
		Font f = new Font("Gulim", Font.BOLD, 14);
		Font axisF = new Font("Gulim", Font.PLAIN, 12);

		// 렌더링 세팅
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

		// plot 생성
		CategoryPlot plot = pmsv.getPlot();

		// plot 에 데이터 적재
		plot.setDataset(2, dataset);
		plot.setRenderer(2, renderer);

		// plot 기본 설정
		plot.setOrientation(PlotOrientation.VERTICAL); // 그래프 표시 방향
		plot.setRangeGridlinesVisible(true); // X축 가이드 라인 표시여부
		plot.setDomainGridlinesVisible(true); // Y축 가이드 라인 표시여부

		// 렌더링 순서 정의 : dataset 등록 순서대로 렌더링 ( 즉, 먼저 등록한게 아래로 깔림 )
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		// X축 세팅
		plot.setDomainAxis(new CategoryAxis()); // X축 종류 설정
		plot.getDomainAxis().setTickLabelFont(axisF); // X축 눈금라벨 폰트 조정
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD); // 카테고리 라벨 위치 조정

		// Y축 세팅
		plot.setRangeAxis(new NumberAxis()); // Y축 종류 설정
		plot.getRangeAxis().setTickLabelFont(axisF); // Y축 눈금라벨 폰트 조정

		// 세팅된 plot을 바탕으로 chart 생성
		JFreeChart chart = pmsv.getChart();

		// 통계 종류(가동률, 음식매충, 총매출)에 따라 기간에 따른 데이터값 받아옴
		if (pmsv.getJrbOperRate().isSelected()) {
			statOperatingRate();// statOperatingRate(staticsChoice);
			chart.setTitle("가동률 통계 그래프"); // 차트 타이틀
		} else if (pmsv.getJrbFoodSell().isSelected()) {
			statFoodSell();
			chart.setTitle("음식 매출 통계 그래프"); // 차트 타이틀
		} else if (pmsv.getJrbTotalSell().isSelected()) {
			statTotalSell();
			chart.setTitle("총 매출 통계 그래프"); // 차트 타이틀
		}

		pmsv.getCp().setVisible(true);
	}// end printGraph

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == pmsv.getJbtFoodDetail()) {// 음식 상세 통계 버튼
			if (effectivenessChk()) {// 통계날짜 유효성 검사
				if (beforeDate.equals(afterDate)) {
					JOptionPane.showMessageDialog(pmsv, "하루치의 통계는 확인하실 수 없습니다.\n날짜를 조정해주세요.");
					return;
				} else {
					getTermDate(); // 스택에 날짜정보를 입력
					if (termStack.size() > 70) {
						JOptionPane.showMessageDialog(pmsv, "10주치 이상의 통계데이터를 표기할 수 없습니다.\n날짜를 조정해주세요.");
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
						pmfsv.setVisible(true);//음식 상세 통계 보이기
						//JOptionPane.showMessageDialog(pmsv, "음식 상세 통계 버튼");
					}
				}
			} else {
				JOptionPane.showMessageDialog(pmsv, "날짜입력이 잘못되었거나 해당 날짜에 통계자료가 없습니다. ");
			}
		}

		if (ae.getSource() == pmsv.getJbtCreateTable()) {// 표 생성 버튼
			if (effectivenessChk()) {// 통계날짜 유효성 검사
				if (beforeDate.equals(afterDate)) {
					JOptionPane.showMessageDialog(pmsv, "하루치의 통계는 확인하실 수 없습니다.\n날짜를 조정해주세요.");
					return;
				} else {
					getTermDate(); // 스택에 날짜정보를 입력
					if (termStack.size() > 70) {
						JOptionPane.showMessageDialog(pmsv, "10주치 이상의 통계데이터를 표기할 수 없습니다.\n날짜를 조정해주세요.");
						termStack.clear();
						return;
					} else {
						printTable();// 표 출력
					}
				}
			} else {
				JOptionPane.showMessageDialog(pmsv, "날짜입력이 잘못되었거나 해당 날짜에 통계자료가 없습니다. ");
			}
		}
		if (ae.getSource() == pmsv.getJbtCreateGraph()) {// 그래프 생성 버튼
			if (effectivenessChk()) {// 통계날짜 유효성 검사
				if (beforeDate.equals(afterDate)) {
					JOptionPane.showMessageDialog(pmsv, "하루치의 통계는 확인하실 수 없습니다.\n날짜를 조정해주세요.");
					return;
				} else {
					getTermDate(); // 스택에 날짜정보를 입력
					printGraph();// 그래프 출력
				}
			} else {
				JOptionPane.showMessageDialog(pmsv, "날짜입력이 잘못되었거나 해당 날짜에 통계자료가 없습니다. ");
			}
		}

		// 날짜 콤보박스 조작( 년월'일' (28~31)변경 )
		if (ae.getSource() == pmsv.getJcbBeforeYear() || ae.getSource() == pmsv.getJcbBeforeMonth()) {
			setDay(pmsv.getJcbBeforeYear().getSelectedItem(), pmsv.getJcbBeforeMonth().getSelectedItem(),
					pmsv.getJcbBeforeDay());
		}
		if (ae.getSource() == pmsv.getJcbAfterYear() || ae.getSource() == pmsv.getJcbAfterMonth()) {// 콤보박스 조작
			setDay(pmsv.getJcbAfterYear().getSelectedItem(), pmsv.getJcbAfterMonth().getSelectedItem(),
					pmsv.getJcbAfterDay());
		}

	}//actionPerformed
	
}
