package kr.co.sist.pcbang.manager.statics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import kr.co.sist.pcbang.manager.order.PMOrderVO;

public class PMStaticsController implements ActionListener {
	private PMStaticsView pmsv;
	private PMStaticsDAO pms_dao;
	private String beforeDate, afterDate;
	private List<String> termList;
	public static final int FullSeatNum = 30;

	public PMStaticsController(PMStaticsView pmsv) {
		this.pmsv = pmsv;
		pms_dao = PMStaticsDAO.getInstance();
		beforeDate = "";
		afterDate = "";
	}

	//날짜 콤보박스 조작시 일 (28~31)변경
	private void setDay(Object year, Object month, JComboBox<String> jcbDay) {
		DefaultComboBoxModel<String> dcbSetDay;
		Calendar cal = Calendar.getInstance();
		int m_year = Integer.valueOf((String) year);
		int m_month = Integer.valueOf((String) month);
		cal.set(m_year, m_month-1, 1);
		
		String day = "";
		for(int i=1; i<=cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			day += i + ",";
		}
		dcbSetDay = new DefaultComboBoxModel<String>(day.split(","));
		jcbDay.setModel(dcbSetDay);
	}
	
	private void selectStatics(int index) {//통계 종류 선택 - 일간통계일 때 뒤에 날짜선택을 안보이게 함
		Calendar cal = Calendar.getInstance();
		if(index == 0) {
			pmsv.getJcbBeforeDay().setVisible(true);
			pmsv.getJcbAfterYear().setVisible(false);
			pmsv.getJcbAfterMonth().setVisible(false);
			pmsv.getJcbAfterDay().setVisible(false);
			pmsv.getJlb2().setVisible(false);
			pmsv.getJcbAfterYear().setSelectedIndex(0);
			pmsv.getJcbAfterMonth().setSelectedIndex(cal.get(Calendar.MONTH));
			pmsv.getJcbAfterDay().setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH)-1);
		}else if(index == 1){
			pmsv.getJcbBeforeDay().setVisible(true);
			pmsv.getJcbAfterYear().setVisible(true);
			pmsv.getJcbAfterMonth().setVisible(true);
			pmsv.getJcbAfterDay().setVisible(true);
			pmsv.getJlb2().setVisible(true);
		}else if(index == 2){
			pmsv.getJcbAfterYear().setVisible(true);
			pmsv.getJcbAfterMonth().setVisible(true);
			pmsv.getJcbAfterDay().setVisible(false);
			pmsv.getJcbBeforeDay().setVisible(false);
			pmsv.getJlb2().setVisible(true);
		}
	}
	
	private boolean effectivenessChk() {//유효성검사 및 결과를 날짜인스턴스변수에 저장
		boolean flag = false;
		int b_year = Integer.valueOf((String) pmsv.getJcbBeforeYear().getSelectedItem());
		int b_month = Integer.valueOf((String) pmsv.getJcbBeforeMonth().getSelectedItem());
		int b_day = Integer.valueOf((String) pmsv.getJcbBeforeDay().getSelectedItem());
		int a_year = Integer.valueOf((String) pmsv.getJcbAfterYear().getSelectedItem());
		int a_month = Integer.valueOf((String) pmsv.getJcbAfterMonth().getSelectedItem());
		int a_day = Integer.valueOf((String) pmsv.getJcbAfterDay().getSelectedItem());
		
		//if(b_year <= a_year && b_month <= a_month && b_day <= a_day) {
		if(b_year == a_year && b_month == a_month && b_day <= a_day) {
			flag = true;
		}else if(b_year == a_year && b_month < a_month) {
			flag = true;
		}else if(b_year < a_year) {
			flag = true;
		}else {
			flag = false;
		}
		
		if(flag) {
			beforeDate = setDateForm(String.valueOf(b_year), String.valueOf(b_month), String.valueOf(b_day));
			afterDate = setDateForm(String.valueOf(a_year), String.valueOf(a_month), String.valueOf(a_day));
			
			termList = getTermDate();		
		}
		return flag;
	}
	
	//년월일 입력을 'yy-mm-dd'형태로 바꿔줌
	private String setDateForm(String year, String month, String day) {
		StringBuilder sb = new StringBuilder();
		if(month.length() == 1)
			month = "0"+month;
		if(day.length() == 1)
			day = "0"+day;
		
		sb.append(year).append("-").append(month).append("-").append(day);
		return sb.toString();
	}
	
	//두 날짜 사이의 날짜 값들을 list에 저장한 것 
	private List<String> getTermDate(){
		List<String> list = new ArrayList<String>();
		Calendar b_cal = new GregorianCalendar(Integer.parseInt(beforeDate.substring(0,4)), 
						Integer.parseInt(beforeDate.substring(5,7))-1, 
						Integer.parseInt(beforeDate.substring(8,10)));
		Calendar a_cal = new GregorianCalendar(Integer.parseInt(afterDate.substring(0,4)), 
						Integer.parseInt(afterDate.substring(5,7))-1, 
						Integer.parseInt(afterDate.substring(8,10)));
		
		//두 날짜의 일수 차
		long diffSec = (a_cal.getTimeInMillis() - b_cal.getTimeInMillis()) / 1000;
		long diffDays = diffSec / (24*60*60);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		list.add(sdf.format(b_cal.getTime()));
		for(int i=0; i<diffDays; i++) {
			b_cal.add(Calendar.DAY_OF_MONTH, 1);
			list.add(sdf.format(b_cal.getTime()));
		}

		return list;
	}
	
	private void statOperatingRate(int staticsChoice) {// 가동률 통계
		//beforeDate afterDate
		switch(staticsChoice) {
		case 0:
			System.out.println("일간통계 - 가동률 통계");
			try {
				List<PMSOperatingTodayVO> m_listOperatingToday = pms_dao.selectMemberTodayOperating(beforeDate);
				List<PMSOperatingTodayVO> g_listOperatingToday = pms_dao.selectGuestTodayOperating(beforeDate);
				PMSOperatingTodayVO m_pmsovo = null;
				PMSOperatingTodayVO g_pmsovo = null;
				//"주문시간","좌석번호","상품코드","상품명","수량","단가","총액"
				Object[] rowData = null;
				for(int i=0; i<m_listOperatingToday.size(); i++) {
					m_pmsovo= m_listOperatingToday.get(i);
					g_pmsovo= g_listOperatingToday.get(i);
					rowData= new Object[2];//DTM에 데이터를 추가하기 위한 일차원 배열을 생성하고 데이터 추가
					rowData[0] = m_pmsovo.getTime();
					rowData[1] = m_pmsovo.getSumTime() + g_pmsovo.getSumTime();
					//System.out.println(pmsovo);
					System.out.println(rowData[0]+"시 / "+rowData[1]+"분사용");
				}
			} catch (SQLException e) {e.printStackTrace();}
			break;
		case 1:
			System.out.println("주간통계 - 가동률 통계");
			try {
				PMSOperatingTermVO otvo = null;
				List<Integer> list = new ArrayList<Integer>();//최근 한 주간 합 
				int tempSum = 0;
				int day = 0;
				for(int i=0; i<termList.size(); i++) {
					otvo = pms_dao.selectTermOperating(termList.get(i));
					tempSum += otvo.getSumTime();
					day += 1;
					//System.out.println(otvo);
					if(termList.size()>6 && i%7 == 0) {
						list.add(tempSum);
						tempSum = 0;
					}//최근 1주마다 누적합 저장 
				}
				if(termList.size()<7) {
					list.add(tempSum);
				}//7일 미만일때 그냥 저장
				
				for(int i=0; i<list.size(); i++) {//출력 ( 나중에 표로 )
					if(i==0) {
						System.out.println("최근 "+ (i+1) + "주 전 가동률 : " + (list.get(i)/FullSeatNum/day) + "%대");
					}
					else {
						System.out.println((i+1) + "주 전 가동률 : " + (list.get(i)/FullSeatNum/day) + "%대");
					}
				}
			} catch (SQLException e) {e.printStackTrace();}
			break;
		case 2:
			System.out.println("월간통계 - 가동률 통계");
			break;
		}
	}//end statOperatingRate
	
	private void statFoodSell(int staticsChoice) {// 음식매출 통계
		//beforeDate afterDate
		switch(staticsChoice) {
		case 0:
			System.out.println("일간통계 - 음식매출 통계");
			break;
		case 1:
			System.out.println("주간통계 - 음식매출 통계");
			break;
		case 2:
			System.out.println("월간통계 - 음식매출 통계");
			break;
		}
	}//end statFoodSell
	
	private void statTotalSell(int staticsChoice) {// 총매출 통계
		//beforeDate afterDate
		switch(staticsChoice) {
		case 0:
			System.out.println("일간통계 - 총매출 통계");
			break;
		case 1:
			System.out.println("주간통계 - 총매출 통계");
			break;
		case 2:
			System.out.println("월간통계 - 총매출 통계");
			break;
		}
	}//end statFoodSell
	
	
	private void printTable() {//표 출력
		int staticsChoice = pmsv.getJcbStaticsChoice().getSelectedIndex();
		
		//통계 종류(가동률, 음식매충, 총매출)에 따라 기간에 따른 데이터값 받아옴
		if(pmsv.getJrbOperRate().isSelected()) {
			statOperatingRate(staticsChoice);
		}else if(pmsv.getJrbFoodSell().isSelected()) {
			statFoodSell(staticsChoice);
		}else if(pmsv.getJrbTotalSell().isSelected()) {
			statTotalSell(staticsChoice);
		}
		System.out.println("표 형태로 출력");
		
	}//end printTable
	
	private void printGraph() {//그래프 출력
		int staticsChoice = pmsv.getJcbStaticsChoice().getSelectedIndex();
		
		//통계 종류(가동률, 음식매충, 총매출)에 따라 기간에 따른 데이터값 받아옴
		if(pmsv.getJrbOperRate().isSelected()) {
			statOperatingRate(staticsChoice);
		}else if(pmsv.getJrbFoodSell().isSelected()) {
			statFoodSell(staticsChoice);
		}else if(pmsv.getJrbTotalSell().isSelected()) {
			statTotalSell(staticsChoice);
		}
		System.out.println("그래프 형태로 출력");
		
	}//end printGraph
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmsv.getJcbStaticsChoice()) {// 통계 종류 선택
			selectStatics(pmsv.getJcbStaticsChoice().getSelectedIndex());
		}
		
		if (ae.getSource() == pmsv.getJbtFoodDetail()) {// 음식 상세 통계 버튼
			JOptionPane.showMessageDialog(pmsv, "음식 상세 통계 버튼");
		}
		
		
		if (ae.getSource() == pmsv.getJbtCreateTable()) {// 표 생성 버튼
			if (effectivenessChk()) {//통계날짜 유효성 검사
				printTable();//표 출력
			} else {
				JOptionPane.showMessageDialog(pmsv, "주소입력이 잘못되었거나 해당 날짜에 통계자료가 없습니다. ");
			}
		}
		if (ae.getSource() == pmsv.getJbtCreateGraph()) {// 그래프 생성 버튼
			if (effectivenessChk()) {//통계날짜 유효성 검사
				printGraph();//그래프 출력
			} else {
				JOptionPane.showMessageDialog(pmsv, "주소입력이 잘못되었거나 해당 날짜에 통계자료가 없습니다. ");
			}
		}
		
		
		//날짜 콤보박스 조작( 년월'일' (28~31)변경 )
		if (ae.getSource() == pmsv.getJcbBeforeYear() || ae.getSource() == pmsv.getJcbBeforeMonth()) {
			setDay(pmsv.getJcbBeforeYear().getSelectedItem(), pmsv.getJcbBeforeMonth().getSelectedItem(), pmsv.getJcbBeforeDay());
		}
		if (ae.getSource() == pmsv.getJcbAfterYear() || ae.getSource() == pmsv.getJcbAfterMonth()) {// 콤보박스 조작
			setDay(pmsv.getJcbAfterYear().getSelectedItem(), pmsv.getJcbAfterMonth().getSelectedItem(), pmsv.getJcbAfterDay());
		}
		
	}

}
