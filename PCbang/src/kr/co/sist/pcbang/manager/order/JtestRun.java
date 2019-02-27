package kr.co.sist.pcbang.manager.order;


import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class JtestRun extends JFrame{

	private JTabbedPane jtp;
	
	public JtestRun() {
		jtp = new JTabbedPane();
		
		PMOrderView pov = new PMOrderView();
		jtp.add("¡÷πÆ", pov);
		
		setLayout(null);
		
		add(jtp);
		jtp.setBounds(50, 50, 1000, 600);
		
		setBounds(100, 100, 1200, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new JtestRun();
	}

}
