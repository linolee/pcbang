package kr.co.sist.pcbang.manager.product.add;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import kr.co.sist.pcbang.manager.product.PMProductController;
import kr.co.sist.pcbang.manager.product.PMProductDAO;
import kr.co.sist.pcbang.manager.product.PMProductVO;

public class PMProductAddController extends WindowAdapter implements ActionListener {
	private PMProductAddView pmpav;
	private String uploadImg;
	private PMProductController pmpc;
	private boolean chgImgFlag;
	private PMProductDAO pmpdao;
	
	public PMProductAddController(PMProductAddView pmpav, PMProductController pmpc) {
		this.pmpav = pmpav;
		this.pmpc = pmpc;
		uploadImg = "";
		chgImgFlag = true;
		pmpdao = PMProductDAO.getInstance();
	}// 생성자

	/**
	 * 입력값과 이미지를 선택한 후 유효성 검증(이미지, 상품명, 카테고리, 가격이 입력되었다면)
	 */
	private void addPrd() {
		if (uploadImg.equals("")) {
			JOptionPane.showMessageDialog(pmpav, "이미지를 선택해주세요");
			return;
		} // end if

		JTextField jtfName = pmpav.getJtfMenuName();
		JComboBox<String> jcbCategory = pmpav.getJcbPrdCategory();
		JTextField jtfPrice = pmpav.getJtfPrice();

		if (jtfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpav, "상품명은 필수 입력!!!!");
			jtfName.setText("");
			jtfName.requestFocus();
			return;
		} // end if

		//제품명 확인
		PMProductVO pmpvo = null;
		try {
			List<PMProductVO> listproduct = pmpdao.selectPrd();
			String[] prdName = new String[1];
			for (int i = 0; i < listproduct.size(); i++) {
				pmpvo = listproduct.get(i);
				prdName[0] = pmpvo.getMenuName();
				if(jtfName.getText().trim().equals(prdName[0])) {
					JOptionPane.showMessageDialog(pmpav, "동일한 제품명이 존재합니다");
					jtfName.setText("");
					jtfName.requestFocus();
					return;
				}//end if
			}//end for
			
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		
		if (jtfPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpav, "상품 가격은 필수 입력!!!!");
			jtfPrice.setText("");
			jtfPrice.requestFocus();
			return;
		} // end if

		// 가격은 숫자만 입력되도록 try~catch
		int price = 0;
		try {
			price = Integer.parseInt(jtfPrice.getText().trim());
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmpav, "가격은 숫자만 입력가능합니다.");
			return;
		} // end catch

		File file = new File(uploadImg);

		PMProductAddVO pmpavo = new PMProductAddVO(jtfName.getText().trim(), file.getName(),
				jcbCategory.getSelectedItem().toString(), price);

		try {
			PMProductDAO.getInstance().insertPrd(pmpavo); // DB에 추가
			// 이미지를, 사용하는 폴더로 복사 (=스트림 필요)
			uploadImg(file);
			// 파일리스트에 새로운 파일명을 추가한다.
			PMProductController.PrdImgList.add(file.getName());

			// 리스트 갱신
			pmpc.setPrd();

			// 다음 상품의 입력을 편하게 하기 위해서 입력폼 초기화
			jtfName.setText("");
			jtfPrice.setText("");
			String imgSavePath = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = imgSavePath + filepath;
			
			pmpav.getjlImg().setIcon(new ImageIcon(imgPath+"no_img.jpg"));

			JOptionPane.showMessageDialog(pmpav, "메뉴가 추가되었습니다.");
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(pmpav, "파일 업로드 실패");
			ie.printStackTrace();
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(pmpav, "DB에서 문제가 발생하였습니다.");
			se.printStackTrace();
		} // end catch
	}// addPrd

	/**
	 * 큰 이미지명을 가진 File객체를 입력하여 업로드 폴더에 큰 이미지와 작은 이미지(s_)를 업로드한다.
	 * 
	 * @param file
	 */
	private void uploadImg(File file) throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			// modify큰이미지,
			byte[] readData = new byte[512];
			String path = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = path + filepath;
			String uploadPath = imgPath;

			int len = 0;
			
			// 작은 이미지 업로드
			fis = new FileInputStream(file.getParent() + "/s_" + file.getName());// getParent를 쓰면 폴더까지가 나옴
			fos = new FileOutputStream(uploadPath + "s_" + file.getName());
			while ((len = fis.read(readData)) != -1) {
				fos.write(readData, 0, len);
				fos.flush();
			} // end while

			fis.close();
			fos.close();

			// 큰 이미지 업로드
			fis = new FileInputStream(file);
			len = 0; // len을 0으로 초기화
			fos = new FileOutputStream(uploadPath + file.getName());

			while ((len = fis.read(readData)) != -1) {
				fos.write(readData, 0, len);
				fos.flush();
			} // end while

		} finally {
			if (fis != null) {
				fis.close();
			} // end if
			if (fos != null) {
				fos.close();
			} // end if
		} // end finally

	}// uploadImg

	private void setImg() {
		FileDialog fdOpen = new FileDialog(pmpav, "상품이미지 선택", FileDialog.LOAD);
		fdOpen.setVisible(true);

		String path = fdOpen.getDirectory();
		String name = fdOpen.getFile();

		boolean flag = false;
		if (path != null) {
			String[] extFlag = { "jpg", "gif", "jpeg", "png", "bmp" };
			for (String ext : extFlag) {
				if (name.toLowerCase().endsWith(ext)) { // 업로드 가능 확장자
					flag = true;
				} // end if
			} // end for
			if (flag) {
				uploadImg = path + name;
				pmpav.getjlImg().setIcon(new ImageIcon(uploadImg));
			} else {
				JOptionPane.showMessageDialog(pmpav, name + "은 이미지가 아닙니다.");
			} // end else
			
			String imgSavePath = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = imgSavePath + filepath;
			
			File origin = new File(imgPath + name);

			// 선택한 이미지가 이미 존재하는 경우 : 이미지 파일이 이미 존재한다면 다른 메뉴를 삭제할 때 같이 삭제 될 수도 있으므로
			// 추가 시 확인함
			boolean existFlag = origin.exists();
			if (existFlag) {
				JOptionPane.showMessageDialog(pmpav, "동일한 이미지를 가진 메뉴가 있습니다.");
				pmpav.getjlImg().setIcon(new ImageIcon(imgPath+"no_img.jpg"));
				chgImgFlag = false;
				return;
			} else {
				chgImgFlag = true;
			}

		} // end if
	}// setImg

	@Override
	public void windowClosing(WindowEvent e) {
		pmpav.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmpav.getjbImg()) {
			if (chgImgFlag) {
				setImg();
			} else {
				JOptionPane.showMessageDialog(pmpav, "이미지 선택이 올바르지 않습니다. 새 이미지를 선택해주세요.");
				return;
			}
		} // end if
		if (ae.getSource() == pmpav.getJbAdd()) {
			addPrd();
		} // end if
		if (ae.getSource() == pmpav.getJbEnd()) {
			pmpav.dispose();
		} // end if
	}// actionPerformed

}// class
