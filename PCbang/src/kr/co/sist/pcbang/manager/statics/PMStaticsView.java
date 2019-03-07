package kr.co.sist.pcbang.manager.statics;

import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PMStaticsView extends JPanel {
	// 190304 이재찬 작성
	private JComboBox<String> jcbStaticsChoice;
	private JRadioButton jrbOperRate, jrbFoodSell, jrbTotalSell;
	private JButton jbtFoodDetail, jbtCreateTable, jbtCreateGraph;
	private JTextField jlbResult;

	private JComboBox<String> jcbBeforeYear, jcbBeforeMonth, jcbBeforeDay;
	private JComboBox<String> jcbAfterYear, jcbAfterMonth, jcbAfterDay;
	private JLabel jlb2;
	
	public PMStaticsView() {
		String[] category = {"일간 통계", "주간 통계", "월간 통계"};
		DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<String>(category);
		jcbStaticsChoice = new JComboBox<String>(dcbm);
		
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
		jlbResult = new JTextField();
		
		setLayout(null);
		
		JLabel jlb1 = new JLabel("조회기간 설정");
		jlb2 = new JLabel("~");
		add(jlb1);
		
		add(jcbBeforeYear);
		add(jcbBeforeMonth);
		add(jcbBeforeDay);
		add(jlb2);
		add(jcbAfterYear);
		add(jcbAfterMonth);
		add(jcbAfterDay);
		
		add(jcbStaticsChoice);
		add(jrbOperRate);
		add(jrbFoodSell);
		add(jrbTotalSell);
		add(jbtFoodDetail);
		add(jbtCreateTable);
		add(jbtCreateGraph);
		add(jlbResult);
		
		//1000, 600
		jcbStaticsChoice.setBounds(30,20,140,25);
		jlb1.setBounds(190,20,100,25);
		jcbBeforeYear.setBounds(280,20,60,25);
		jcbBeforeMonth.setBounds(340,20,40,25);
		jcbBeforeDay.setBounds(380,20,40,25);
		jlb2.setBounds(425,21,10,25);
		jcbAfterYear.setBounds(440,20,60,25);
		jcbAfterMonth.setBounds(500,20,40,25);
		jcbAfterDay.setBounds(540,20,40,25);
		jcbAfterYear.setVisible(false);
		jcbAfterMonth.setVisible(false);
		jcbAfterDay.setVisible(false);
		jlb2.setVisible(false);
		
		jrbOperRate.setBounds(830,150,100,25);
		jrbFoodSell.setBounds(830,180,100,25);
		jrbTotalSell.setBounds(830,210,100,25);
		jbtFoodDetail.setBounds(830,280,120,25);
		jbtCreateTable.setBounds(830,350,120,40);
		jbtCreateGraph.setBounds(830,400,120,40);
		jlbResult.setBounds(30,60,750,490);

		PMStaticsController pmsc = new PMStaticsController(this);
		jcbStaticsChoice.addActionListener(pmsc);
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
		for(int i=cal.get(Calendar.YEAR); 2017<=i ; i--) {
			year += i + ",";
		}
		for(int i=1; i<=12 ; i++) {
			month += i + ",";
		}
		for(int i=1; i<=cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
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
		jcbDay.setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH)-1);		
	}

	public JComboBox<String> getJcbStaticsChoice() {
		return jcbStaticsChoice;
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

	public JTextField getJlbResult() {
		return jlbResult;
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

	public JLabel getJlb2() {
		return jlb2;
	}
	
	
}
