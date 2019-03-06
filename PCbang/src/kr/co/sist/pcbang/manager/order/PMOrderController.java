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
			
			//"�ֹ��ð�","�¼���ȣ","��ǰ�ڵ�","��ǰ��","����","�ܰ�","�Ѿ�"
			Object[] rowData = null;
			for(int i=0; i<listOrder.size(); i++) {
				pmovo= listOrder.get(i);
				rowData= new Object[8];//DTM�� �����͸� �߰��ϱ� ���� ������ �迭�� �����ϰ� ������ �߰�
				rowData[0] = pmovo.getOrderNum();
				rowData[1] = pmovo.getOrderDate();
				rowData[2] = new Integer(pmovo.getSeatNum());
				rowData[3] = pmovo.getMenuCode();
				rowData[4] = pmovo.getMenuName();
				rowData[5] = new Integer(pmovo.getQuan());
				rowData[6] = new Integer(pmovo.getMenuPrice());
				rowData[7] = new Integer(pmovo.getTotalPrice());
				
				//DTM�� �߰�
				dtmOrder.addRow(rowData);
			}//end for

			if(listOrder.isEmpty()) {//�Էµ� �ֹ��� ���� �� 
				//JOptionPane.showMessageDialog(pmov, "�Էµ� ��ǰ�� �����ϴ�.");
				Object[] nullData = new Object[8];
				nullData[0] = "��û��";
				nullData[1] = "�ֹ���";
				nullData[2] = "��";
				nullData[3] = "���ϴ�.";
				dtmOrder.addRow(nullData);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmov, "DB ���� �߻�");
			e.printStackTrace();
		}
	}//end setOrder
	
	public void setOrderComplete() {
		DefaultTableModel dtmOrderComplete = pmov.getDtmOrderComplete();
		dtmOrderComplete.setRowCount(0);
		
		try {
			List<PMOrderVO> listOrderComplete = pmo_dao.selectOrder("Y");
			PMOrderVO pmovo = null;
			
			//"�ֹ��ð�","�¼���ȣ","��ǰ�ڵ�","��ǰ��","����","�ܰ�","�Ѿ�"
			Object[] rowData = null;
			for(int i=0; i<listOrderComplete.size(); i++) {
				pmovo= listOrderComplete.get(i);
				rowData= new Object[7];//DTM�� �����͸� �߰��ϱ� ���� ������ �迭�� �����ϰ� ������ �߰�
				rowData[0] = pmovo.getOrderNum();
				rowData[1] = new Integer(pmovo.getSeatNum());
				rowData[2] = pmovo.getMenuCode();
				rowData[3] = pmovo.getMenuName();
				rowData[4] = new Integer(pmovo.getQuan());
				rowData[5] = new Integer(pmovo.getMenuPrice());
				rowData[6] = new Integer(pmovo.getTotalPrice());
				
				//DTM�� �߰�
				dtmOrderComplete.addRow(rowData);
			}//end for
			
			if(listOrderComplete.isEmpty()) {//�Ϸ�� �ֹ��� ���� ��
				//JOptionPane.showMessageDialog(pmov, "�Էµ� ��ǰ�� �����ϴ�.");
				Object[] nullData = new Object[8];
				nullData[0] = "�Ϸ��";
				nullData[1] = "�ֹ���";
				nullData[2] = "�����ϴ�.";
				dtmOrderComplete.addRow(nullData);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmov, "DB ���� �߻�");
			e.printStackTrace();
		}
	}//end setOrder
	
	private void orderComplete() {
		int select = -1;
		select = JOptionPane.showConfirmDialog(pmov, orderNum+"�� �ֹ��� �Ϸ��Ͻðڽ��ϱ�?");
		if(select == JOptionPane.OK_OPTION) {
			try {
				if(pmo_dao.updateOrder(orderNum)) {
					JOptionPane.showMessageDialog(pmov, orderNum+"�� �ֹ��� �Ϸ�Ǿ����ϴ� !");
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(pmov, "DB���� ���� �߻�");
				e.printStackTrace();
			}
			setOrder();
			setOrderComplete();
			flagOrder = false;
		}//end if
	}
	
	private void orderCancle() {
		int select = -1;
		select = JOptionPane.showConfirmDialog(pmov, orderNum+"�� �ֹ��� ����Ͻðڽ��ϱ�?");
		if(select == JOptionPane.OK_OPTION) {
			try {
				if(pmo_dao.deleteOrder(orderNum)) {
					JOptionPane.showMessageDialog(pmov, orderNum+"�� �ֹ��� ��ҵǾ����ϴ� !");
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(pmov, "DB���� ���� �߻�");
				e.printStackTrace();
			}
			setOrder();
			setOrderComplete();
			flagOrder = false;
		}//end if
	}

	
	
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource() == pmov.getJtOrder()) {//�ֹ����̺��� ���ȴ��� ? 
			flagOrder = true;
			JTable jt = pmov.getJtOrder();
			orderNum = (String)jt.getValueAt(jt.getSelectedRow(), 0);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(!flagOrder) {
			JOptionPane.showMessageDialog(pmov, "�ֹ���� ���̺��� ���� �����Ͻʽÿ�");
		}else {
			if(ae.getSource() == pmov.getJbtComplete()) {//�ֹ��Ϸ� ��ư
				orderComplete();
			}
			if(ae.getSource() == pmov.getJbtCancle()) {//��� ��ư
				orderCancle();
			}
		}//end else
	}
	
	@Override public void mousePressed(MouseEvent e) {}//
	@Override public void mouseReleased(MouseEvent e) {}//
	@Override public void mouseEntered(MouseEvent e) {}//
	@Override public void mouseExited(MouseEvent e) {}//
}
