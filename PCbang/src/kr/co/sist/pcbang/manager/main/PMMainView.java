package kr.co.sist.pcbang.manager.main;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.pcbang.manager.fare.PMFareView;
import kr.co.sist.pcbang.manager.order.PMOrderView;
import kr.co.sist.pcbang.manager.product.PMProductView;
import kr.co.sist.pcbang.manager.seat.PMSeatView;
import kr.co.sist.pcbang.manager.statics.PMStaticsView;
import kr.co.sist.pcbang.manager.user.PMUserView;


@SuppressWarnings("serial")
public class PMMainView extends JFrame{

   private JTabbedPane jtb;
   private JButton jbtLogOut, jbtAccount, jbtNoticeSave;
   private JTextArea jtaNotice, jtaBoard;
   private JLabel jlOrderNum, jlMsgNum, jlOrder, jlMsg;
   private JPanel seat, order, statistics, member, menu, price;
   private PMOrderView pmov; // 19-02-27 이재찬 추가
   private PMSeatView pmsv;
   
   public static String adminId;   
   
   public PMMainView(String adminName) {
      super("관리자 시스템 [ 로그인 계정 : "+adminName+" / "+adminId + " ]");
      System.out.println("main view : "+ adminId );
      
      jtb = new JTabbedPane();
      jbtLogOut = new JButton("로그아웃");
      jbtAccount = new JButton("계정관리");
      jbtNoticeSave = new JButton("공지사항저장");
      jtaBoard = new JTextArea();                  //현황
      jtaNotice = new JTextArea();            //공지사항
      jlOrder = new JLabel("주문");
      jlOrderNum = new JLabel();
      jlMsg = new JLabel("메세지");
      jlMsgNum = new JLabel();
      Font font = new Font("arian", Font.BOLD, 13);


      
      JScrollPane jspBoard = new JScrollPane(jtaBoard);
      JScrollPane jspNotice = new JScrollPane(jtaNotice);
      
      setLayout(null);

      jspBoard.setBounds(10,30, 120, 130);
      jbtLogOut.setBounds(10, 165, 120, 30);
      jbtAccount.setBounds(10, 200, 120, 30);
      jbtNoticeSave.setBounds(10, 235, 120, 30);
      jspNotice.setBounds(10, 270, 120, 170);
      jlOrder.setBounds(10, 460, 120, 30);
      jlOrderNum.setBounds(10, 500, 120, 30);
      jlMsg.setBounds(10, 550, 120, 30);
      jlMsgNum.setBounds(10, 590, 120, 30);
      jtb.setBounds(150, 30, 1000, 600);
      
       pmov = new PMOrderView(); // 19-02-27 이재찬 추가
       pmsv = new PMSeatView(this);
       order = pmov; // 19-02-27 이재찬 추가
       seat = pmsv;
       statistics = new PMStaticsView();// 19-03-12 이재찬 추가
       member = new PMUserView();
       menu = new PMProductView();
       price = new PMFareView();
       
       jtb.add("좌석", seat );      
       jtb.add("주문", order );      
       jtb.add("통계", statistics );      
       jtb.add("회원관리" ,member );      
       jtb.add("상품관리" , menu);      
       jtb.add("요금제관리" ,price );      

      
      add(jspBoard);
      add(jspNotice);
      add(jbtLogOut);
      add(jbtAccount);
      add(jbtNoticeSave);
      add(jlOrder);
      add(jlMsg);
      add(jlOrderNum);
      add(jlMsgNum);
      add("Center",jtb);
      
      jspBoard.setBorder(new TitledBorder("현황"));
      jspNotice.setBorder(new TitledBorder("공지사항"));
      
      jtaBoard.setBackground(Color.white);
      jlOrder.setHorizontalAlignment(JTextField.CENTER);
      jlOrder.setBackground(Color.white);
      jlMsg.setHorizontalAlignment(JTextField.CENTER);
      jlMsg.setBackground(Color.white);   
      jlOrderNum.setHorizontalAlignment(JLabel.CENTER);
      jlOrderNum.setBackground(Color.white);
      jlMsgNum.setHorizontalAlignment(JLabel.CENTER);
      jlMsgNum.setBackground(Color.white);
      jtb.setBackground(Color.white);
      
      jtaBoard.setEditable(false);
      jtaBoard.setOpaque(true);
      jlOrder.setOpaque(true);
      jlOrderNum.setOpaque(true);
      jlMsg.setOpaque(true);
      jlMsgNum.setOpaque(true);
      
      jtaBoard.setFont(font);
      
      PMMainController pmmc = new PMMainController(this);
      addWindowListener(pmmc);
      jtb.addMouseListener(pmmc); // 19-02-27 이재찬 추가
      jbtAccount.addActionListener(pmmc);
      jbtLogOut.addActionListener(pmmc);
      jbtNoticeSave.addActionListener(pmmc);
      
      
      setBounds(400, 100, 1200, 700);
      setVisible(true);
      setResizable(false);
      
   } // PMMainView

   public JTabbedPane getJtb() {
      return jtb;
   }

   public JButton getJbtLogOut() {
      return jbtLogOut;
   }

   public JButton getJbtAccount() {
      return jbtAccount;
   }

   public JButton getJbtNoticeSave() {
      return jbtNoticeSave;
   }

   public JTextArea getJtaNotice() {
      return jtaNotice;
   }

   public JTextArea getJtaBoard() {
      return jtaBoard;
   }

   public JLabel getJlOrderNum() {
      return jlOrderNum;
   }

   public JLabel getJlMsgNum() {
      return jlMsgNum;
   }

   public JLabel getJlOrder() {
      return jlOrder;
   }

   public JLabel getJlMsg() {
      return jlMsg;
   }

   public static String getAdminId() {
      return adminId;
   }

   public PMOrderView getPmov() { // 19-02-27 이재찬 추가
      return pmov;
   }

public PMSeatView getPmsv() {
	return pmsv;
}
   
   
   
}//class