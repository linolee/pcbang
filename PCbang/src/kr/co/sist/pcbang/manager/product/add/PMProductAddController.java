package kr.co.sist.pcbang.manager.product.add;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import kr.co.sist.lunch.admin.model.LunchAdminDAO;
import kr.co.sist.lunch.admin.run.LunchAdminMain;
import kr.co.sist.pcbang.manager.product.PMProductController;
import kr.co.sist.pcbang.manager.product.PMProductDAO;

public class PMProductAddController extends WindowAdapter implements ActionListener{
	private PMProductAddView pmpav;
	private String uploadImg;
	private PMProductController pmpc;
	
	public PMProductAddController(PMProductAddView pmpav, PMProductController pmpc) {
		this.pmpav=pmpav;
		this.pmpc=pmpc;
		uploadImg = "";
	}//생성자
	
	/**
	 * 입력값과 이미지를 선택한 후 유효성 검증(이미지, 상품명, 카테고리, 가격이 입력되었다면)
	 */
	private void addPrd() {
		if(uploadImg.equals("")) {
			JOptionPane.showMessageDialog(pmpav, "이미지를 선택해주세요");
			return;
		}//end if
		
		JTextField jtfName = pmpav.getJtfMenuName();
		JComboBox<String> jcbCategory = pmpav.getJcbPrdCategory();
		JTextField jtfPrice = pmpav.getJtfPrice();
		
		if(jtfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpav, "상품명은 필수 입력!!!!");
			jtfName.setText("");
			jtfName.requestFocus();
			return;
		}//end if
		
		if(jtfPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpav, "상품 가격은 필수 입력!!!!");
			jtfPrice.setText("");
			jtfPrice.requestFocus();
			return;
		}//end if
		
		//가격은 숫자만 입력되도록 try~catch
		int price = 0;
		try {
			price = Integer.parseInt(jtfPrice.getText().trim());
		}catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmpav, "가격은 숫자만 입력가능합니다.");
			return;
		}//end catch
		
		File file = new File(uploadImg);
		
		PMProductAddVO pmpavo= new PMProductAddVO(jtfName.getText().trim(), 
				file.getName(), jcbCategory.getSelectedItem().toString(), price);
		
		try {
			PMProductDAO.getInstance().insertPrd(pmpavo); //DB에 추가
			//이미지를, 사용하는 폴더로 복사 (=스트림 필요)
			uploadImg(file);
			//파일리스트에 새로운 파일명을 추가한다.
			LunchAdminMain.lunchImageList.add(file.getName());
			
			//LunchDetailController에  modify 큰이미지, 작은이미지에 관한 내용을 넣으면서 다시 강사님 코드로 고침
//			uploadImg(file);
//			//에러가 나면 catch로 빠져서 DB에 추가가 안됨
//			LunchAdminDAO.getInstance().insertLunch(lavo);//에러가 나지 않는 경우 DB에 추가
			
			//리스트 갱신
			lmc.setLunch();
			
			//다음 도시락의 입력을 편하게 하기 위해서 입력폼 초기화
			jtfName.setText("");
			jtfPrice.setText("");
			jtaSpec.setText("");
			
			lav.getJlLunchImg().setIcon(new ImageIcon("C:/dev/workspace/lunch_prj/src/kr/co/sist/lunch/admin/img/no_img.jpg"));
			
			JOptionPane.showMessageDialog(lav, "도시락이 추가되었습니다.");
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(lav, "파일 업로드 실패");
			ie.printStackTrace();
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(lav, "DB에서 문제가 발생하였습니다.");
			se.printStackTrace();
		}//end catch
	}//addPrd
	
	@Override
	public void windowClosing(WindowEvent e) {
		pmpav.dispose();
	}//windowClosing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==pmpav.getjbImg()) {
//			setImg();
		}//end if
		if(ae.getSource()==pmpav.getJbAdd()) {
//			addProduct();
		}//end if
		
		if(ae.getSource()==pmpav.getJbEnd()) {
			pmpav.dispose();
		}//end if
	}//actionPerformed

}//class
