package kr.co.sist.pcbang.client.ordering;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.pcbang.client.main.PUMainController;

@SuppressWarnings("serial")
public class PUOrderingView extends JFrame {
	
	private JTabbedPane jtbMenu;
	private DefaultTableModel dtmBestProduct,dtmOrderlist,
						dtmMenu,dtmRamen,dtmSnack,dtmDrink;
	private JButton jbtOk,jbtExit,jbtDel;
	private JTable jtBestProduct,jtMenu,jtRamen,jtSnack,jtDrink,jtOrderlist;
	private JLabel jlProductPrice;

	public PUOrderingView(PUMainController pumc,int seatNum) {
		super("상품주문창");
		
		//1.컴포넌트 생성
		/////베스트 테이블///////
		String[] columnsRank= {"1","2","3","4","5","6","7"};
		dtmBestProduct=new DefaultTableModel(columnsRank, 1) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		jtBestProduct=new JTable(dtmBestProduct) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {//이미지
				return getValueAt(0, column).getClass();
			}//getColumnClass
		};
		jtBestProduct.getTableHeader().setResizingAllowed(false);//컬럼의 크기 변경 막기
		jtBestProduct.getTableHeader().setReorderingAllowed(false);//컬럼의 이동 막기
		jtBestProduct.setRowHeight(95);
		/////메뉴 테이블///////
		String[] columns= {"이미지","이름","가격","상품코드"};
		dtmMenu=new DefaultTableModel(columns,4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		jtMenu=new JTable(dtmMenu) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {//이미지
				return getValueAt(0, column).getClass();
			}//getColumnClass
		};
		jtMenu.getTableHeader().setResizingAllowed(false);//컬럼의 크기 변경 막기
		jtMenu.getTableHeader().setReorderingAllowed(false);//컬럼의 이동 막기
		jtMenu.setRowHeight(100);
		
		dtmRamen=new DefaultTableModel(columns,4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		jtRamen=new JTable(dtmRamen) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {//이미지
				return getValueAt(0, column).getClass();
		}//getColumnClass
		};
		jtRamen.getTableHeader().setResizingAllowed(false);//컬럼의 크기 변경 막기
		jtRamen.getTableHeader().setReorderingAllowed(false);//컬럼의 이동 막기
		jtRamen.setRowHeight(100);

		dtmSnack=new DefaultTableModel(columns,4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		jtSnack=new JTable(dtmSnack) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {//이미지
				return getValueAt(0, column).getClass();
			}//getColumnClass
		};
		jtSnack.getTableHeader().setResizingAllowed(false);//컬럼의 크기 변경 막기
		jtSnack.getTableHeader().setReorderingAllowed(false);//컬럼의 이동 막기
		jtSnack.setRowHeight(100);
		
		dtmDrink=new DefaultTableModel(columns,4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		jtDrink=new JTable(dtmDrink) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {//이미지
				return getValueAt(0, column).getClass();
			}//getColumnClass
		};
		jtDrink.getTableHeader().setResizingAllowed(false);//컬럼의 크기 변경 막기
		jtDrink.getTableHeader().setReorderingAllowed(false);//컬럼의 이동 막기
		jtDrink.setRowHeight(100);
		
		//////주문목록테이블//////
		JLabel jlOrderTitle =new JLabel("주문 목록");
		jlOrderTitle.setHorizontalAlignment(SwingConstants.CENTER);
		String[] columnsName= {"이름","수량","가격"};
		dtmOrderlist=new DefaultTableModel(columnsName,0){};
		jtOrderlist=new JTable(dtmOrderlist) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return super.isCellEditable(row, 2);
			}//isCellEditable
		};
		jtOrderlist.getTableHeader().setResizingAllowed(false);//컬럼의 크기 변경 막기
		jtOrderlist.getTableHeader().setReorderingAllowed(false);//컬럼의 이동 막기
		jtOrderlist.getColumnModel().getColumn(0).setPreferredWidth(120);
		jtOrderlist.getColumnModel().getColumn(1).setPreferredWidth(40);
		jtOrderlist.getColumnModel().getColumn(2).setPreferredWidth(90);
		//jtOrderlist.getColumnModel().getColumn(3).setPreferredWidth(80);
		jtOrderlist.setRowHeight(23);
		
		JLabel jlPrice =new JLabel("총 가격 : ");
		jlProductPrice =new JLabel("0원");
		jbtOk=new JButton("상품 주문");
		jbtExit=new JButton("닫기");
		jbtDel=new JButton("선택 상품 삭제");
		
		//4.배치
		JLabel jlBest=new JLabel("Best 7");
		jlBest.setHorizontalAlignment(SwingConstants.CENTER);//가운데 정렬
		JScrollPane jspBest=new JScrollPane(jtBestProduct);
		setLocationRelativeTo(null);//JTable 크기 정해주기
		jspBest.setPreferredSize(new Dimension(850, 120));//JTable 크기 정해주기
		///////스크롤 바 할당//////////
		JScrollPane jspMenu=new JScrollPane(jtMenu);
		JScrollPane jspRamen=new JScrollPane(jtRamen);
		JScrollPane jspSnack=new JScrollPane(jtSnack);
		JScrollPane jspDrink=new JScrollPane(jtDrink);
		//////메뉴 탭 배치///////
		JPanel jpanelTotal=new JPanel();
		jpanelTotal.setLayout(new BorderLayout());
		jpanelTotal.add("Center",jspMenu);
		
		jtbMenu= new JTabbedPane();
		jtbMenu.add("전체 메뉴", jpanelTotal);
		//2
		JPanel jpanelRamen=new JPanel();
		jpanelRamen.setLayout(new BorderLayout());
		jpanelRamen.add("Center",jspRamen);
		jtbMenu.add("라면", jpanelRamen);
		//3
		JPanel jpanelSnack=new JPanel();
		jpanelSnack.setLayout(new BorderLayout());
		jpanelSnack.add("Center",jspSnack);
		jtbMenu.add("스낵", jpanelSnack);
		//4
		JPanel jpanelDrink=new JPanel();
		jpanelDrink.setLayout(new BorderLayout());
		jpanelDrink.add("Center",jspDrink);
		jtbMenu.add("음료", jpanelDrink);
		///////주문 배치//////
		JScrollPane jspOrder=new JScrollPane(jtOrderlist);
		jspOrder.setPreferredSize(new Dimension(200, 365));//JTable 크기 정해주기
		JPanel jpOrderL=new JPanel();
		jpOrderL.add(jspOrder);
		
		JPanel jpOrderP=new JPanel();
		jpOrderP.setLayout(new BorderLayout());
		jpOrderP.add("West",jlPrice);
		jpOrderP.add("East",jlProductPrice);
		
		JPanel jpOrderB=new JPanel();
		jpOrderB.setLayout(new GridLayout(1,2));
		jpOrderB.add("West",jbtOk);
		jpOrderB.add("East",jbtExit);
		
		JPanel jpOrderSouth=new JPanel();
		jpOrderSouth.setLayout(new GridLayout(3,1));
		jpOrderSouth.add("North",jbtDel);
		jpOrderSouth.add("Center",jpOrderP);
		jpOrderSouth.add("South",jpOrderB);
		
		JPanel jpOrderWrap=new JPanel();
		jpOrderWrap.setLayout(new BorderLayout());
		jpOrderWrap.add("North",jlOrderTitle);
		jpOrderWrap.add("Center",jpOrderL);
		jpOrderWrap.add("South",jpOrderSouth);
		
		JPanel jporderCenter=new JPanel();
		jporderCenter.setLayout(new BorderLayout());
		jporderCenter.add("East",jpOrderWrap);
		jporderCenter.add("Center",jtbMenu);
		
		//4-2.배치
		JPanel jPanel=new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.add("North", jlBest);
		jPanel.add("Center", jspBest);
		add("North",jPanel);
		add("Center",jporderCenter);
		
		//5.이벤트 등록
		PUOrderingController puoc=new PUOrderingController(this,pumc,seatNum);
		addWindowListener(puoc);
		jtbMenu.addMouseListener(puoc);
		jtBestProduct.addMouseListener(puoc);
		jtMenu.addMouseListener(puoc);
		jtRamen.addMouseListener(puoc);
		jtSnack.addMouseListener(puoc);
		jtDrink.addMouseListener(puoc);
		jtOrderlist.addMouseListener(puoc);
		
		jbtDel.addActionListener(puoc);
		jbtOk.addActionListener(puoc);
		jbtExit.addActionListener(puoc);
		
		setBounds(100, 100, 850, 650);
		setVisible(true);
	}//PUOrderingView

	public JTabbedPane getjtbMenu() {
		return jtbMenu;
	}
	public DefaultTableModel getDtmBestProduct() {
		return dtmBestProduct;
	}
	public DefaultTableModel getDtmOrderlist() {
		return dtmOrderlist;
	}
	public DefaultTableModel getDtmMenu() {
		return dtmMenu;
	}
	public DefaultTableModel getDtmRamen() {
		return dtmRamen;
	}
	public DefaultTableModel getDtmSnack() {
		return dtmSnack;
	}
	public DefaultTableModel getDtmDrink() {
		return dtmDrink;
	}
	public JButton getJbtOk() {
		return jbtOk;
	}
	public JButton getJbtExit() {
		return jbtExit;
	}
	public JTable getJtBestProduct() {
		return jtBestProduct;
	}
	public JTable getJtMenu() {
		return jtMenu;
	}
	public JTable getJtRamen() {
		return jtRamen;
	}
	public JTable getJtSnack() {
		return jtSnack;
	}
	public JTable getJtDrink() {
		return jtDrink;
	}
	public JTable getJtOrderlist() {
		return jtOrderlist;
	}
	public JLabel getJlProductPrice() {
		return jlProductPrice;
	}
	public JButton getJbtDel() {
		return jbtDel;
	}
	public void setJbtDel(JButton jbtDel) {
		this.jbtDel = jbtDel;
	}
	
//	public static void main(String[] args) {
//		new PUOrderingView(100);
//	}//main///	
	
}//class