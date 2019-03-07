package kr.co.sist.pcbang.manager.statics;


import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class JtestRun2 extends JFrame{

	private JTabbedPane jtp;
	
	public JtestRun2() {
		jtp = new JTabbedPane();
		
		PMStaticsView psv = new PMStaticsView();
		jtp.add("Ελ°θ", psv);
		
		setLayout(null);
		
		add(jtp);
		jtp.setBounds(50, 50, 1000, 600);
		
		setBounds(100, 100, 1200, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new JtestRun2();
	}

}
