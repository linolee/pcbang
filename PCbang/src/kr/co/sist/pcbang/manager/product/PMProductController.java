package kr.co.sist.pcbang.manager.product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import admin.model.PMProductDAO;
import admin.view.PMProductAddView;
import admin.view.PMProductView;
import admin.vo.PMProductVO;

public class PMProductController extends MouseAdapter implements ActionListener{

	private PMProductView pmpv;
	private PMProductDAO pmp_dao;
		
	public PMProductController(PMProductView pmpv) {
		this.pmpv=pmpv;
		pmp_dao = PMProductDAO.getInstance();
		//상품 목록을 초기화한다.
		setProduct();
	}//PMProductController

	/**
	 * JTable에 DB에서 조회한 상품 정보를 보여준다.
	 */
	public void setProduct() {
		DefaultTableModel dtmPrd = pmpv.getDtmPrd();
		dtmPrd.setRowCount(0); // View창에 있는 걸 지워주고

		try {
			// DB에서 상품 정보를 조회
			List<PMProductVO> listproduct = pmp_dao.selectPrd();
			// JTable에 조회한 정보를 출력

			PMProductVO pmp_vo = null;
//			String imgPath = "C:/dev/workspace/lunch_prj/src/kr/co/sist/lunch/admin/img/s_";

			Object[] rowData = null;
			for (int i = 0; i < listproduct.size(); i++) {
				pmp_vo = listproduct.get(i);
				// DTM에 넣을 때는 1차원배열 혹은 벡터로 넣어야 함
				// DTM에 데이터를 추가하기 위한 1차원배열(Vector)을 생성하고 데이터를 추가
				rowData = new Object[6];
				rowData[0] = pmp_vo.getMenuCode();
				rowData[1] = pmp_vo.getMenuName();
//				rowData[2] = new ImageIcon(imgPath + lv.getImg());
				rowData[2] = new Integer(i+1);//이미지 넣을 곳
				rowData[3] = new Integer(pmp_vo.getPrice());
				rowData[4] = new Integer(pmp_vo.getQuan());
				rowData[5] = new Integer(pmp_vo.getTotal());

				// DTM에 추가
				dtmPrd.addRow(rowData);
			} // end for

			if (listproduct.isEmpty()) {// 입력된 상품이 없을 때
				JOptionPane.showMessageDialog(pmpv, "입력된 제품이 없습니다.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmpv, "DB에서 데이터를 받아오는 중 문제 발생");
			e.printStackTrace();
		} // end catch
	}// setProduct
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmpv.getJbtAddPrd()) { // 도시락 추가 버튼
			new PMProductAddView(pmpv, this);
		} // end if
		
		if(ae.getSource()==pmpv.getJbtSchPrd()) {//조회 버튼
			
		}
		if(ae.getSource()==pmpv.getJbtRstPrd()) {//초기화 버튼
			
		}
	}//actionPerformed
	
	@Override
	public void mouseClicked(MouseEvent me) {
//		if (me.getSource() == lmv.getJtb()) {
//			if (lmv.getJtb().getSelectedIndex() == 1) {// 두번째 탭(주문)에서 이벤트 발생
//				// 주문현황을 계속 조회하여 실시간으로 실시간으로 DB를 조회하여 갱신
//				if (threadOrdering == null) { // 이걸쓰지않으면 계속해서 객체가 만들어짐
//					threadOrdering = new Thread(this);
//					threadOrdering.start();
//				} // end if
//					// 현재까지의 주문사항을 조회 (쓰레드로 돌려야 함)
//				searchOrder();
//			} // end if
//		} // end if
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	
}//class
