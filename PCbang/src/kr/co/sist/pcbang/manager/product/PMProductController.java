package kr.co.sist.pcbang.manager.product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.pcbang.manager.product.add.PMProductAddView;

public class PMProductController extends MouseAdapter implements ActionListener {

	public static List<String> PrdImgList;
	private PMProductView pmpv;
	private PMProductDAO pmpdao;

	public PMProductController(PMProductView pmpv) {
		PrdImgList=new ArrayList<String>();
		//서버에 존재하는 이미지 입력
		
		//이미지를 근데 FileServer로 돌려야 하는데 어디서 돌리지????
//		File file = new File("C:/Users/owner/git/pcbang/PCbang/src/kr/co/sist/pcbang/manager/product/img");

		String path = this.getClass().getResource("/").getPath()+"kr/co/sist/pcbang/manager/product/img"; 
		File file = new File(path);

		for(String tempName:file.list()) {
			PrdImgList.add(tempName);
		}//end for
		
		FileServer fs = new FileServer();
		fs.start();

		this.pmpv = pmpv;
		pmpdao = PMProductDAO.getInstance();
		// 상품 목록을 초기화한다.
		setPrd();
	}// PMProductController

	/**
	 * JTable에 DB에서 조회한 상품 정보를 보여준다.
	 */
	public void setPrd() {
		DefaultTableModel dtmPrd = pmpv.getDtmPrd();
		dtmPrd.setRowCount(0); // View창에 있는 걸 지워주고

		try {
			// DB에서 상품 정보를 조회
			List<PMProductVO> listproduct = pmpdao.selectPrd();

			// JTable에 조회한 정보를 출력
			PMProductVO pmpvo = null;
			String imgPath = "";

			Object[] rowData = null;
			for (int i = 0; i < listproduct.size(); i++) {
				pmpvo = listproduct.get(i);
				// DTM에 넣을 때는 1차원배열 혹은 벡터로 넣어야 함
				// DTM에 데이터를 추가하기 위한 1차원배열(Vector)을 생성하고 데이터를 추가
				rowData = new Object[6];
				rowData[0] = pmpvo.getMenuCode();
				rowData[1] = pmpvo.getMenuName();
				rowData[2] = new ImageIcon(imgPath + pmpvo.getImg());
				rowData[3] = new Integer(pmpvo.getPrice());
				rowData[4] = new Integer(pmpvo.getQuan());
				rowData[5] = new Integer(pmpvo.getTotal());

				// DTM에 추가
				dtmPrd.addRow(rowData);
			} // end for

			if (listproduct.isEmpty()) {// 입력된 상품이 없을 때

				rowData = new Object[6];
				rowData[0] = "";
				rowData[1] = "입력된";
				rowData[2] = "상품이";
				rowData[3] = "없습니다";
				rowData[4] = "";
				rowData[5] = "";

				// DTM에 추가
				dtmPrd.addRow(rowData);
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmpv, "DB에서 데이터를 받아오는 중 문제 발생");
			e.printStackTrace();
		} // end catch
	}// setProduct

	/**
	 * 카테고리와 상품명으로 찾기
	 */
	public void searchPrd() {
		DefaultTableModel dtmSearch = pmpv.getDtmPrd();
		dtmSearch.setRowCount(0);

		String category = pmpv.getJcbPrdCategory().getSelectedItem().toString();
		String menuName = pmpv.getJtfPrdName().getText();
		try {
			List<PMSchProductVO> list_pmspvo = pmpdao.searchPrd(category.toString(), menuName);
			Object[] rowData = null;
			PMSchProductVO pmspvo = null;
			for (int i = 0; i < list_pmspvo.size(); i++) {
				pmspvo = list_pmspvo.get(i);
				rowData = new Object[6];
				rowData[0] = (pmspvo.getMenuCode());
				rowData[1] = (pmspvo.getMenuName());
				rowData[2] = (pmspvo.getImg());
				rowData[3] = (pmspvo.getPrice());
				rowData[4] = (pmspvo.getQuan());
				rowData[5] = (pmspvo.getPrice() * pmspvo.getQuan());

				// DTM에 추가
				dtmSearch.addRow(rowData);
			} // end for
		} catch (SQLException e1) {
			e1.printStackTrace();
		} // end catch
	}// searchPrd

	/////////////// 초기화 내용추가 필요
	public void reset() {
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmpv.getJbtAddPrd()) { // 도시락 추가 버튼
			new PMProductAddView(pmpv, this);
		} // end if

		if (ae.getSource() == pmpv.getJbtSchPrd()) {// 조회 버튼
			searchPrd();
		}
		if (ae.getSource() == pmpv.getJbtRstPrd()) {// 초기화 버튼
			reset();
		}
	}// actionPerformed

//////////////////////////////////////////////////////////////////
// mouseCliecked만 씀	
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
	
}// class
