package kr.co.sist.pcbang.manager.order;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

public class PMOrderView extends JLabel{
	//190214 이재찬 작성
	private DefaultListModel<String> dlmOrderList, dlmOrderCompleteList;
	private JList<String> jlOderList, jlOderCompleteList;
	private JButton jbtComplete, jbtCancle;
	
	public PMOrderView() {
		dlmOrderList = new DefaultListModel<String>();
		dlmOrderCompleteList = new DefaultListModel<String>();
		jlOderList = new JList<String>();
		jlOderCompleteList = new JList<String>();
		jbtComplete = new JButton("완료");
		jbtCancle = new JButton("취소");
		
		JLabel jlbOderList = new JLabel("주문목록");
		JLabel jlbOderCompleteList = new JLabel("판매목록");
		
		setLayout(null);
		
		add(jlbOderList);
		add(jlbOderCompleteList);
		jlbOderList.setBounds(200, 50, 200, 30);
		jlbOderCompleteList.setBounds(700, 50, 200, 30);
		
		add(jlOderList);
		add(jlOderCompleteList);
		add(jbtComplete);
		add(jbtCancle);
		
		jlOderList.setBounds(50,100,400,450);
		jlOderCompleteList.setBounds(550,100,400,450);
		
		
	}

	
	
	public DefaultListModel<String> getDlmOrderList() {
		return dlmOrderList;
	}

	public DefaultListModel<String> getDlmOrderCompleteList() {
		return dlmOrderCompleteList;
	}

	public JList<String> getJlOderList() {
		return jlOderList;
	}

	public JList<String> getJlOderCompleteList() {
		return jlOderCompleteList;
	}

	public JButton getJbtComplete() {
		return jbtComplete;
	}

	public JButton getJbtCancle() {
		return jbtCancle;
	}
	
	
	
}
