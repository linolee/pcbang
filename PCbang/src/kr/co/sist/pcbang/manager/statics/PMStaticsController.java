package kr.co.sist.pcbang.manager.statics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import kr.co.sist.pcbang.manager.order.PMOrderVO;

public class PMStaticsController implements ActionListener {
	private PMStaticsView pmsv;
	private PMStaticsDAO pms_dao;
	private String beforeDate, afterDate;
//	private List<String> termList;
	private Stack<String> termStack;
	public static final int FullSeatNum = 30;

	private boolean flagTableOperDay, flagTableOperMonth, flagTableFoodDay, flagTableFoodMonth, flagTableTotalDay, flagTableTotalMonth;

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
			pmsv.getJlb2().setVisible(false);
			pmsv.getJcbAfterYear().setSelectedIndex(0);
			pmsv.getJcbAfterMonth().setSelectedIndex(cal.get(Calendar.MONTH));
			pmsv.getJcbAfterDay().setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH) - 1);
		} else if (index == 1) {
			pmsv.getJcbBeforeDay().setVisible(true);
			pmsv.getJcbAfterYear().setVisible(true);
			pmsv.getJcbAfterMonth().setVisible(true);
			pmsv.getJcbAfterDay().setVisible(true);
			pmsv.getJlb2().setVisible(true);
		} else if (index == 2) {
			pmsv.getJcbAfterYear().setVisible(true);
			pmsv.getJcbAfterMonth().setVisible(true);
			pmsv.getJcbAfterDay().setVisible(false);
			pmsv.getJcbBeforeDay().setVisible(false);
			pmsv.getJlb2().setVisible(true);
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

		// if(b_year <= a_year && b_month <= a_month && b_day <= a_day) {
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

			// termList = getTermDate(); //getTermDate() 에서 바로 인스턴스 변수로 넣었음
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
	private void getTermDate() {// private List<String> getTermDate()
		// List<String> list = new ArrayList<String>();
		Calendar b_cal = new GregorianCalendar(Integer.parseInt(beforeDate.substring(0, 4)),
				Integer.parseInt(beforeDate.substring(5, 7)) - 1, Integer.parseInt(beforeDate.substring(8, 10)));
		Calendar a_cal = new GregorianCalendar(Integer.parseInt(afterDate.substring(0, 4)),
				Integer.parseInt(afterDate.substring(5, 7)) - 1, Integer.parseInt(afterDate.substring(8, 10)));

		// 두 날짜의 일수 차
		long diffSec = (a_cal.getTimeInMillis() - b_cal.getTimeInMillis()) / 1000;
		long diffDays = diffSec / (24 * 60 * 60);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		termStack.push(sdf.format(b_cal.getTime()));
		// list.add(sdf.format(b_cal.getTime()));
		for (int i = 0; i < diffDays; i++) {
			b_cal.add(Calendar.DAY_OF_MONTH, 1);
			// list.add(sdf.format(b_cal.getTime()));
			termStack.push(sdf.format(b_cal.getTime()));
		}
		// return list;
	}

	private void statOperatingRate() {// 가동률 통계 //statOperatingRate(int staticsChoice)
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
						if(pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
							pmsv.getDtmMaxData().addRow(rowData);
							tempOperation = operation;
						}
						if(tempOperation < operation) { //최대값
							pmsv.getDtmMaxData().setRowCount(0);
							pmsv.getDtmMaxData().addRow(rowData);
							tempOperation = operation;
						}
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
					if(pmsv.getDtmMaxData().getRowCount() == 0) {// 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					if(tempOperation < operation) { //최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
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
					if(pmsv.getDtmMaxData().getRowCount() == 0) { //초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					if(tempOperation < operation) { //최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempOperation = operation;
					}
					flagTableOperDay = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// end statOperatingRate

	private void statFoodSell() {// 음식매출 통계 void statFoodSell(int staticsChoice)
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
						if(pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						if(tempTotal < total) { //최대값
							pmsv.getDtmMaxData().setRowCount(0);
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
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
					if(pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if(tempTotal < total) { //최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
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
					if(pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if(tempTotal < total) { //최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
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

	private void statTotalSell() {// 총매출 통계 void statTotalSell(int staticsChoice)
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
						if(pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
						if(tempTotal < total) { //최대값
							pmsv.getDtmMaxData().setRowCount(0);
							pmsv.getDtmMaxData().addRow(rowData);
							tempTotal = total;
						}
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
					if(pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if(tempTotal < total) { //최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
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
					if(pmsv.getDtmMaxData().getRowCount() == 0) { // 초기값
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
					if(tempTotal < total) { //최대값
						pmsv.getDtmMaxData().setRowCount(0);
						pmsv.getDtmMaxData().addRow(rowData);
						tempTotal = total;
					}
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
			statOperatingRate(); // statOperatingRate(staticsChoice);
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

		// 통계 종류(가동률, 음식매충, 총매출)에 따라 기간에 따른 데이터값 받아옴
		if (pmsv.getJrbOperRate().isSelected()) {
			statOperatingRate();// statOperatingRate(staticsChoice);
		} else if (pmsv.getJrbFoodSell().isSelected()) {
			statFoodSell();
		} else if (pmsv.getJrbTotalSell().isSelected()) {
			statTotalSell();
		}
		System.out.println("그래프 형태로 출력");

	}// end printGraph

	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getSource() == pmsv.getJbtFoodDetail()) {// 음식 상세 통계 버튼
			if (beforeDate.equals(afterDate)) {
				JOptionPane.showMessageDialog(pmsv, "하루치의 통계는 확인하실 수 없습니다.\n날짜를 조정해주세요.");
				return;
			}else {
				getTermDate(); // 스택에 날짜정보를 입력
				JOptionPane.showMessageDialog(pmsv, "음식 상세 통계 버튼");
			}
		}
		
		if (ae.getSource() == pmsv.getJbtCreateTable()) {// 표 생성 버튼
			if (effectivenessChk()) {// 통계날짜 유효성 검사
				if (beforeDate.equals(afterDate)) {
					JOptionPane.showMessageDialog(pmsv, "하루치의 통계는 확인하실 수 없습니다.\n날짜를 조정해주세요.");
					return;
				}else {
					getTermDate(); // 스택에 날짜정보를 입력
					if(termStack.size()>70) {
						JOptionPane.showMessageDialog(pmsv, "10주치 이상의 통계데이터를 표기할 수 없습니다.\n날짜를 조정해주세요.");
						termStack.clear();
						return;
					}else {
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
				}else {
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

	}

}
