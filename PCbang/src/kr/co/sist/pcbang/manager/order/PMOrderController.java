package kr.co.sist.pcbang.manager.order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PMOrderController implements ActionListener, MouseListener{
	private PMOrderView pmov;
	private PMOrderDAO pmo_dao;
	private boolean flagOrder;
	private String orderNum;
	
	public PMOrderController(PMOrderView pmov) {
		this.pmov = pmov;
		pmo_dao = PMOrderDAO.getInstance();
		flagOrder = false;
		setOrder();
		setOrderComplete();
	}

	public void setOrder() {
		DefaultTableModel dtmOrder = pmov.getDtmOrder();
		dtmOrder.setRowCount(0);
		
		try {
			List<PMOrderVO> listOrder = pmo_dao.selectOrder("N");
			PMOrderVO pmovo = null;
			
			//"주문시간","좌석번호","상품코드","상품명","수량","단가","총액"
			Object[] rowData = null;
			for(int i=0; i<listOrder.size(); i++) {
				pmovo= listOrder.get(i);
				rowData= new Object[8];//DTM에 데이터를 추가하기 위한 일차원 배열을 생성하고 데이터 추가
				rowData[0] = pmovo.getOrderNum();
				rowData[1] = pmovo.getOrderDate();
				rowData[2] = new Integer(pmovo.getSeatNum());
				rowData[3] = pmovo.getMenuCode();
				rowData[4] = pmovo.getMenuName();
				rowData[5] = new Integer(pmovo.getQuan());
				rowData[6] = new Integer(pmovo.getMenuPrice());
				rowData[7] = new Integer(pmovo.getTotalPrice());
				
				//DTM에 추가
				dtmOrder.addRow(rowData);
			}//end for

			if(listOrder.isEmpty()) {//입력된 주문이 없을 때 
				//JOptionPane.showMessageDialog(pmov, "입력된 제품이 없습니다.");
				Object[] nullData = new Object[8];
				nullData[0] = "요청된";
				nullData[1] = "주문이";
				nullData[2] = "없";
				nullData[3] = "습니다.";
				dtmOrder.addRow(nullData);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmov, "DB 오류 발생");
			e.printStackTrace();
		}
	}//end setOrder
	
	public void setOrderComplete() {
		DefaultTableModel dtmOrderComplete = pmov.getDtmOrderComplete();
		dtmOrderComplete.setRowCount(0);
		
		try {
			List<PMOrderVO> listOrderComplete = pmo_dao.selectOrder("Y");
			PMOrderVO pmovo = null;
			
			//"주문시간","좌석번호","상품코드","상품명","수량","단가","총액"
			Object[] rowData = null;
			for(int i=0; i<listOrderComplete.size(); i++) {
				pmovo= listOrderComplete.get(i);
				rowData= new Object[7];//DTM에 데이터를 추가하기 위한 일차원 배열을 생성하고 데이터 추가
				rowData[0] = pmovo.getOrderNum();
				rowData[1] = new Integer(pmovo.getSeatNum());
				rowData[2] = pmovo.getMenuCode();
				rowData[3] = pmovo.getMenuName();
				rowData[4] = new Integer(pmovo.getQuan());
				rowData[5] = new Integer(pmovo.getMenuPrice());
				rowData[6] = new Integer(pmovo.getTotalPrice());
				
				//DTM에 추가
				dtmOrderComplete.addRow(rowData);
			}//end for
			
			if(listOrderComplete.isEmpty()) {//완료된 주문이 없을 때
				//JOptionPane.showMessageDialog(pmov, "입력된 제품이 없습니다.");
				Object[] nullData = new Object[8];
				nullData[0] = "완료된";
				nullData[1] = "주문이";
				nullData[2] = "없습니다.";
				dtmOrderComplete.addRow(nullData);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmov, "DB 오류 발생");
			e.printStackTrace();
		}
	}//end setOrder
	
	private void orderComplete() {
		int select = -1;
		select = JOptionPane.showConfirmDialog(pmov, orderNum+"번 주문을 완료하시겠습니까?");
		if(select == JOptionPane.OK_OPTION) {
			try {
				if(pmo_dao.updateOrder(orderNum)) {
					JOptionPane.showMessageDialog(pmov, orderNum+"번 주문이 완료되었습니다 !");
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(pmov, "DB에서 문제 발생");
				e.printStackTrace();
			}
			setOrder();
			setOrderComplete();
			flagOrder = false;
		}//end if
	}
	
	private void orderCancle() {
		int select = -1;
		select = JOptionPane.showConfirmDialog(pmov, orderNum+"번 주문을 취소하시겠습니까?");
		if(select == JOptionPane.OK_OPTION) {
			try {
				if(pmo_dao.deleteOrder(orderNum)) {
					JOptionPane.showMessageDialog(pmov, orderNum+"번 주문이 취소되었습니다 !");
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(pmov, "DB에서 문제 발생");
				e.printStackTrace();
			}
			setOrder();
			setOrderComplete();
			flagOrder = false;
		}//end if
	}

	
	
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource() == pmov.getJtOrder()) {//주문테이블이 눌렸는지 ? 
			flagOrder = true;
			JTable jt = pmov.getJtOrder();
			orderNum = (String)jt.getValueAt(jt.getSelectedRow(), 0);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(!flagOrder) {
			JOptionPane.showMessageDialog(pmov, "주문목록 테이블을 먼저 선택하십시오");
		}else {
			if(ae.getSource() == pmov.getJbtComplete()) {//주문완료 버튼
				orderComplete();
			}
			if(ae.getSource() == pmov.getJbtCancle()) {//취소 버튼
				orderCancle();
			}
		}//end else
	}
	
	@Override public void mousePressed(MouseEvent e) {}//
	@Override public void mouseReleased(MouseEvent e) {}//
	@Override public void mouseEntered(MouseEvent e) {}//
	@Override public void mouseExited(MouseEvent e) {}//
}
