package kr.co.sist.pcbang.manager.order;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PMOrderView extends JPanel{
	//190214 ������ �ۼ�
	private DefaultTableModel dtmOrder, dtmOrderComplete;
	private JTable jtOrder, jtOderComplete;
	private JButton jbtComplete, jbtCancle;
	private PMOrderController pmoc;
	
	public PMOrderView() {
		String[] orderColumns = {"�ֹ���ȣ","�ֹ��ð�","�¼�","��ǰ�ڵ�","��ǰ��","����","�ܰ�","�Ѿ�"};
		String[] completeColumns = {"�ֹ���ȣ","�¼�","��ǰ�ڵ�","��ǰ��","����","�ܰ�","�Ѿ�"};
		dtmOrder = new DefaultTableModel(orderColumns,4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		dtmOrderComplete = new DefaultTableModel(completeColumns,4) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		jtOrder = new JTable(dtmOrder);
		jtOderComplete = new JTable(dtmOrderComplete);
		jbtComplete = new JButton("�Ϸ�");
		jbtCancle = new JButton("�ֹ����");
		
		//�ֹ� ���̺� ũ�� ���� : ��ü 490
		jtOrder.getColumnModel().getColumn(0).setPreferredWidth(95);
		jtOrder.getColumnModel().getColumn(1).setPreferredWidth(95);
		jtOrder.getColumnModel().getColumn(2).setPreferredWidth(30);
		jtOrder.getColumnModel().getColumn(3).setPreferredWidth(80);
		jtOrder.getColumnModel().getColumn(4).setPreferredWidth(80);
		jtOrder.getColumnModel().getColumn(5).setPreferredWidth(30);
		jtOrder.getColumnModel().getColumn(6).setPreferredWidth(40);
		jtOrder.getColumnModel().getColumn(7).setPreferredWidth(40);
		jtOrder.setRowHeight(25);
		
		//�ֹ��Ϸ� ���̺� ũ�� ���� : ��ü 490
		jtOderComplete.getColumnModel().getColumn(0).setPreferredWidth(110);
		jtOderComplete.getColumnModel().getColumn(1).setPreferredWidth(50);
		jtOderComplete.getColumnModel().getColumn(2).setPreferredWidth(90);
		jtOderComplete.getColumnModel().getColumn(3).setPreferredWidth(90);
		jtOderComplete.getColumnModel().getColumn(4).setPreferredWidth(50);
		jtOderComplete.getColumnModel().getColumn(5).setPreferredWidth(50);
		jtOderComplete.getColumnModel().getColumn(6).setPreferredWidth(50);
		jtOderComplete.setRowHeight(25);
		JScrollPane jspOder = new JScrollPane(jtOrder);
		JScrollPane jspOderComplete = new JScrollPane(jtOderComplete);
		jspOder.setBorder(new TitledBorder("�ֹ����"));
		jspOderComplete.setBorder(new TitledBorder("�ǸŸ��"));
		
		//JLabel jlbOderList = new JLabel("�ֹ����");
		//JLabel jlbOderCompleteList = new JLabel("�ǸŸ��");
		
		setLayout(null);
		
		/*add(jlbOderList);
		add(jlbOderCompleteList);
		jlbOderList.setBounds(200, 20, 200, 30);
		jlbOderCompleteList.setBounds(700, 20, 200, 30);*/
		
		add(jspOder);
		add(jspOderComplete);
		add(jbtComplete);
		add(jbtCancle);
		
		jspOder.setBounds(10,20,490,500);
		jspOderComplete.setBounds(500,20,490,500);
		jbtComplete.setBounds(120,530,100,30);
		jbtCancle.setBounds(280,530,100,30);
		
		pmoc = new PMOrderController(this);
		jtOrder.addMouseListener(pmoc);
		jbtComplete.addActionListener(pmoc);
		jbtCancle.addActionListener(pmoc);
		
	}

	
	public DefaultTableModel getDtmOrder() {
		return dtmOrder;
	}

	public DefaultTableModel getDtmOrderComplete() {
		return dtmOrderComplete;
	}

	public JTable getJtOrder() {
		return jtOrder;
	}

	public JTable getJtOderComplete() {
		return jtOderComplete;
	}

	public JButton getJbtComplete() {
		return jbtComplete;
	}

	public JButton getJbtCancle() {
		return jbtCancle;
	}

	public PMOrderController getPmoc() {
		return pmoc;
	}

	
	

	
	
}
