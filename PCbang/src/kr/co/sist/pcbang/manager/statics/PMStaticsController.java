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

	//��¥ �޺��ڽ� ���۽� �� (28~31)����
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
	
	private void selectStatics(int index) {//��� ���� ���� - �ϰ������ �� �ڿ� ��¥������ �Ⱥ��̰� ��
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
	
	private boolean effectivenessChk() {//��ȿ���˻� �� ����� ��¥�ν��Ͻ������� ����
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
	
	//����� �Է��� 'yy-mm-dd'���·� �ٲ���
	private String setDateForm(String year, String month, String day) {
		StringBuilder sb = new StringBuilder();
		if(month.length() == 1)
			month = "0"+month;
		if(day.length() == 1)
			day = "0"+day;
		
		sb.append(year).append("-").append(month).append("-").append(day);
		return sb.toString();
	}
	
	//�� ��¥ ������ ��¥ ������ list�� ������ �� 
	private List<String> getTermDate(){
		List<String> list = new ArrayList<String>();
		Calendar b_cal = new GregorianCalendar(Integer.parseInt(beforeDate.substring(0,4)), 
						Integer.parseInt(beforeDate.substring(5,7))-1, 
						Integer.parseInt(beforeDate.substring(8,10)));
		Calendar a_cal = new GregorianCalendar(Integer.parseInt(afterDate.substring(0,4)), 
						Integer.parseInt(afterDate.substring(5,7))-1, 
						Integer.parseInt(afterDate.substring(8,10)));
		
		//�� ��¥�� �ϼ� ��
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
	
	private void statOperatingRate(int staticsChoice) {// ������ ���
		//beforeDate afterDate
		switch(staticsChoice) {
		case 0:
			System.out.println("�ϰ���� - ������ ���");
			try {
				List<PMSOperatingTodayVO> m_listOperatingToday = pms_dao.selectMemberTodayOperating(beforeDate);
				List<PMSOperatingTodayVO> g_listOperatingToday = pms_dao.selectGuestTodayOperating(beforeDate);
				PMSOperatingTodayVO m_pmsovo = null;
				PMSOperatingTodayVO g_pmsovo = null;
				//"�ֹ��ð�","�¼���ȣ","��ǰ�ڵ�","��ǰ��","����","�ܰ�","�Ѿ�"
				Object[] rowData = null;
				for(int i=0; i<m_listOperatingToday.size(); i++) {
					m_pmsovo= m_listOperatingToday.get(i);
					g_pmsovo= g_listOperatingToday.get(i);
					rowData= new Object[2];//DTM�� �����͸� �߰��ϱ� ���� ������ �迭�� �����ϰ� ������ �߰�
					rowData[0] = m_pmsovo.getTime();
					rowData[1] = m_pmsovo.getSumTime() + g_pmsovo.getSumTime();
					//System.out.println(pmsovo);
					System.out.println(rowData[0]+"�� / "+rowData[1]+"�л��");
				}
			} catch (SQLException e) {e.printStackTrace();}
			break;
		case 1:
			System.out.println("�ְ���� - ������ ���");
			try {
				PMSOperatingTermVO otvo = null;
				List<Integer> list = new ArrayList<Integer>();//�ֱ� �� �ְ� �� 
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
					}//�ֱ� 1�ָ��� ������ ���� 
				}
				if(termList.size()<7) {
					list.add(tempSum);
				}//7�� �̸��϶� �׳� ����
				
				for(int i=0; i<list.size(); i++) {//��� ( ���߿� ǥ�� )
					if(i==0) {
						System.out.println("�ֱ� "+ (i+1) + "�� �� ������ : " + (list.get(i)/FullSeatNum/day) + "%��");
					}
					else {
						System.out.println((i+1) + "�� �� ������ : " + (list.get(i)/FullSeatNum/day) + "%��");
					}
				}
			} catch (SQLException e) {e.printStackTrace();}
			break;
		case 2:
			System.out.println("������� - ������ ���");
			break;
		}
	}//end statOperatingRate
	
	private void statFoodSell(int staticsChoice) {// ���ĸ��� ���
		//beforeDate afterDate
		switch(staticsChoice) {
		case 0:
			System.out.println("�ϰ���� - ���ĸ��� ���");
			break;
		case 1:
			System.out.println("�ְ���� - ���ĸ��� ���");
			break;
		case 2:
			System.out.println("������� - ���ĸ��� ���");
			break;
		}
	}//end statFoodSell
	
	private void statTotalSell(int staticsChoice) {// �Ѹ��� ���
		//beforeDate afterDate
		switch(staticsChoice) {
		case 0:
			System.out.println("�ϰ���� - �Ѹ��� ���");
			break;
		case 1:
			System.out.println("�ְ���� - �Ѹ��� ���");
			break;
		case 2:
			System.out.println("������� - �Ѹ��� ���");
			break;
		}
	}//end statFoodSell
	
	
	private void printTable() {//ǥ ���
		int staticsChoice = pmsv.getJcbStaticsChoice().getSelectedIndex();
		
		//��� ����(������, ���ĸ���, �Ѹ���)�� ���� �Ⱓ�� ���� �����Ͱ� �޾ƿ�
		if(pmsv.getJrbOperRate().isSelected()) {
			statOperatingRate(staticsChoice);
		}else if(pmsv.getJrbFoodSell().isSelected()) {
			statFoodSell(staticsChoice);
		}else if(pmsv.getJrbTotalSell().isSelected()) {
			statTotalSell(staticsChoice);
		}
		System.out.println("ǥ ���·� ���");
		
	}//end printTable
	
	private void printGraph() {//�׷��� ���
		int staticsChoice = pmsv.getJcbStaticsChoice().getSelectedIndex();
		
		//��� ����(������, ���ĸ���, �Ѹ���)�� ���� �Ⱓ�� ���� �����Ͱ� �޾ƿ�
		if(pmsv.getJrbOperRate().isSelected()) {
			statOperatingRate(staticsChoice);
		}else if(pmsv.getJrbFoodSell().isSelected()) {
			statFoodSell(staticsChoice);
		}else if(pmsv.getJrbTotalSell().isSelected()) {
			statTotalSell(staticsChoice);
		}
		System.out.println("�׷��� ���·� ���");
		
	}//end printGraph
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmsv.getJcbStaticsChoice()) {// ��� ���� ����
			selectStatics(pmsv.getJcbStaticsChoice().getSelectedIndex());
		}
		
		if (ae.getSource() == pmsv.getJbtFoodDetail()) {// ���� �� ��� ��ư
			JOptionPane.showMessageDialog(pmsv, "���� �� ��� ��ư");
		}
		
		
		if (ae.getSource() == pmsv.getJbtCreateTable()) {// ǥ ���� ��ư
			if (effectivenessChk()) {//��賯¥ ��ȿ�� �˻�
				printTable();//ǥ ���
			} else {
				JOptionPane.showMessageDialog(pmsv, "�ּ��Է��� �߸��Ǿ��ų� �ش� ��¥�� ����ڷᰡ �����ϴ�. ");
			}
		}
		if (ae.getSource() == pmsv.getJbtCreateGraph()) {// �׷��� ���� ��ư
			if (effectivenessChk()) {//��賯¥ ��ȿ�� �˻�
				printGraph();//�׷��� ���
			} else {
				JOptionPane.showMessageDialog(pmsv, "�ּ��Է��� �߸��Ǿ��ų� �ش� ��¥�� ����ڷᰡ �����ϴ�. ");
			}
		}
		
		
		//��¥ �޺��ڽ� ����( ���'��' (28~31)���� )
		if (ae.getSource() == pmsv.getJcbBeforeYear() || ae.getSource() == pmsv.getJcbBeforeMonth()) {
			setDay(pmsv.getJcbBeforeYear().getSelectedItem(), pmsv.getJcbBeforeMonth().getSelectedItem(), pmsv.getJcbBeforeDay());
		}
		if (ae.getSource() == pmsv.getJcbAfterYear() || ae.getSource() == pmsv.getJcbAfterMonth()) {// �޺��ڽ� ����
			setDay(pmsv.getJcbAfterYear().getSelectedItem(), pmsv.getJcbAfterMonth().getSelectedItem(), pmsv.getJcbAfterDay());
		}
		
	}

}
