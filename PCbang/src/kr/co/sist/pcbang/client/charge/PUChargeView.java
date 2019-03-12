package kr.co.sist.pcbang.client.charge;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

import kr.co.sist.pcbang.client.login.PULoginController;
import kr.co.sist.pcbang.client.main.PUMainController;


@SuppressWarnings("serial")
public class PUChargeView extends JFrame{

	private JButton[] jbtPrice;
	private int[] Price;
	private int[] time;
	private boolean member;
	private PUChargeDAO puc_dao;
	private int seatNum;
	private PUMainController pumc;
	
	public PUChargeView(int seatNum, PUMainController pumc) {
		super("요금표");
		this.seatNum=seatNum;
		this.pumc=pumc;
		puc_dao=PUChargeDAO.getInstance();
		
		String[] arr=null;
		try {
			member=puc_dao.selectCheckMember(seatNum);
			arr=puc_dao.price(member).toString().replace("[", "").replace("]", "").trim().split(",");
			time=new int[(arr.length/2)];
			Price=new int[(arr.length/2)];
			int j=0;
			for(int i=0; i<arr.length; i=i+2) {
					time[j]=(int)Integer.parseInt(arr[i].trim());
					j++;
			}//end for
			int c=0;
			for(int i=1; i<arr.length; i=i+2) {
				Price[c]=Integer.parseInt(arr[i].trim());
				c++;
			}//end for
		} catch (SQLException se) {
			se.printStackTrace();
		}//end catch
		
		PUChargeController pucc=new PUChargeController(this, pumc);
		
			jbtPrice=new JButton[(arr.length/2)];
			if(member) {
				for(int i=0; i < jbtPrice.length; i++) {
					jbtPrice[i]=new JButton(String.valueOf(Price[i]+"원\n"+time[i]+":00"));//생성
					jbtPrice[i].addActionListener(pucc);//이벤트 등록
					add(jbtPrice[i]);//배치
				}//end for
			}//end if
			if(!member) {
				for(int i=0; i < jbtPrice.length; i++) {
					jbtPrice[i]=new JButton(String.valueOf(Price[i]+"원\n"+time[i]+":00"));//생성
					jbtPrice[i].addActionListener(pucc);//이벤트 등록
					add(jbtPrice[i]);//배치
				}//end for
			}//end if
		addWindowListener(pucc);
			
		setLayout(new GridLayout(5, 2));
		
		setBounds(100, 100, 600, 600);
		setVisible(true);
	}//PUChargeView
	
	
	
	public JButton[] getJbtPrice() {
		return jbtPrice;
	}

	public int[] getPrice() {
		return Price;
	}

	public int[] getTime() {
		return time;
	}

	public boolean isMember() {
		return member;
	}

	public PUChargeDAO getPuc_dao() {
		return puc_dao;
	}

	public int getSeatNum() {
		return seatNum;
	}

}//class

