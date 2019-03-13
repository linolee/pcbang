package kr.co.sist.pcbang.manager.product.detail;

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

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import kr.co.sist.pcbang.manager.product.PMProductController;
import kr.co.sist.pcbang.manager.product.PMProductDAO;

public class PMProductDetailController extends WindowAdapter implements ActionListener {

	private PMProductDetailView pmpdv;
	private PMProductController pmpc;
	private PMProductDAO pmp_dao;
	private String originImg;
	private String uploadImg;
	private boolean chgImgFlag;
	
	public PMProductDetailController(PMProductDetailView pmpdv, PMProductController pmpc, String orginalImg) {
		this.pmpdv = pmpdv;
		this.pmpc = pmpc;
		pmp_dao = PMProductDAO.getInstance();
		this.originImg = orginalImg;
		uploadImg = "";
		chgImgFlag= true;
	}// PMProductDetailController

	/**
	 * 상품 지우기
	 * 
	 * @param code
	 */
	private void removePrd(String code) {
		try {
			if (pmp_dao.deletePrd(code)) {// 상품을 삭제
				pmpc.setPrd();// 상품 리스트를 갱신한다.
				/*// 파일을 삭제
				String path = System.getProperty("user.dir");
				String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
				String imgPath = path + filepath;
				File file = new File(imgPath + originImg);
				// 하나가지고 하면 파일명을 읽어들이지 못하는 문제가 발생할 수도 있기 때문에 따로 두개를 줌
				File rmFile = new File(file.getAbsolutePath());// 큰 파일
				File rmFile2 = new File(file.getParent() + "/s_" + file.getName());// 작은파일

				rmFile.delete();
				rmFile2.delete();*/

				JOptionPane.showMessageDialog(pmpdv, "상품이 삭제되었습니다.");
				pmpdv.dispose();

			} else {
				JOptionPane.showMessageDialog(pmpdv, "상품이 존재하지 않습니다.");
			} // end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmpdv, "DB에서 문제가 발생하였습니다.");
			e.printStackTrace();
		} // end catch
	}// removePrd

	/**
	 * 이미지 바꾸기
	 */
	private void chgImg() {
		FileDialog fdOpen = new FileDialog(pmpdv, "상품이미지 선택", FileDialog.LOAD);
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
				pmpdv.getJlImg().setIcon(new ImageIcon(uploadImg));
			} else {
				JOptionPane.showMessageDialog(pmpdv, name + "은 이미지가 아닙니다.");
			} // end else
			
			/*			String imgSavePath = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = imgSavePath + filepath;
			File origin = new File(imgPath+name);
			
			// 선택한 이미지가 이미 존재하는 경우 : 이미지 파일이 이미 존재한다면 다른 메뉴를 삭제할 때 같이 삭제 될 수도 있으므로
			// 추가 시 확인함
			boolean existFlag = origin.exists();
			if (existFlag) {
				JOptionPane.showMessageDialog(pmpdv, "동일한 이미지를 가진 메뉴가 있습니다.");
				uploadImg="";
				chgImgFlag=false;
				return;
			}else {
				
			}*/
			
		} // end if
		
	}// chgImg

	/**
	 * 상품코드, 상품명, 가격을 입력받아 코드에 해당하는 상품의 정보를 변경
	 */
	private void modifyPrd() {// void로 하고 DB에서 T/F 가지고 오면 됨
		// 유효성 검증 : 상품명, 가격이 ""가 아닐 때, 가격은 숫자인지,
		// 이미지가 변경되었다면 이미지가 s_가 붙은 이미지가 존재하는 이미지인지
		JTextField tfName = pmpdv.getJtfMenuName();
		JTextField tfPrice = pmpdv.getJtfPrice();
		JComboBox<String> jcbox = pmpdv.getJcbPrdCategory();

		// 빈칸인지 우선 확인
		if (tfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpdv, "상품명을 입력해주세요.");
			tfName.setText("");
			tfName.requestFocus();
		} // end if
		if (tfPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpdv, "상품 가격을 입력해주세요.");
			tfPrice.setText("");
			tfPrice.requestFocus();
		} // end if

		int price = 0;
		try {
			price = Integer.parseInt(tfPrice.getText().trim());
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmpdv, "상품의 가격은 숫자만 입력가능합니다");
			nfe.printStackTrace();
		} // end catch

		// 선택한 이미지 앞에 s_가 붙은 이미지가 없는 경우 : 이미지 작업이 되어있지 않은 경우 uploadImg를 사용
		if (!uploadImg.equals("")) {// 이미지 변경을 수행 할 수 없는 경우
			File tempFile = new File(uploadImg); // 큰이미지
			File smallFile = new File(tempFile.getParent() + "/s_" + tempFile.getName()); // 작은이미지
				
			if (!smallFile.exists()) {
				JOptionPane.showMessageDialog(pmpdv, "선택하신 파일의 작은 이미지가 존재하지 않습니다.");
				uploadImg = "";
				return;
			} // end if
		} // end if

		StringBuilder updateMsg = new StringBuilder();
		updateMsg.append("수정정보 \n").append("상품 명 : ").append(tfName.getText()).append("\n").append("가격 : ")
				.append(tfPrice.getText()).append("\n").append("카테고리 : ").append(jcbox.getSelectedItem().toString())
				.append("\n").append("위의 정보로 상품의 정보가 변경하시겠습니까?");

		switch (JOptionPane.showConfirmDialog(pmpdv, updateMsg)) {
		case JOptionPane.OK_OPTION:
			// 변경된 값으로 VO를 생성하고
			File file = new File(uploadImg);
			PMProductUpdateVO pmpuvo = new PMProductUpdateVO(pmpdv.getPrdCode(), tfName.getText().trim(),
					file.getName(), jcbox.getSelectedItem().toString(), price);
			try {
				if (pmp_dao.updatePrd(pmpuvo)) {// DB Table에서 update를 수행
					JOptionPane.showMessageDialog(pmpdv, "상품 정보가 변경되었습니다.");
					if (!uploadImg.equals("")) {// 변경한 이미지가 존재하는 경우
						try {
							// 이전 이미지를 삭제한 후.
							// 파일을 삭제
							String path = System.getProperty("user.dir");
							String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
							String imgPath = path + filepath;

							File originfile = new File(imgPath + originImg);

							// 하나가지고 하면 파일명을 읽어들이지 못하는 문제가 발생할 수도 있기 때문에 따로 두개를 줌
								File rmFile = new File(originfile.getAbsolutePath());// 큰 파일
								File rmFile2 = new File(originfile.getParent() + "/s_" + originfile.getName());// 작은파일

								rmFile.delete();
								rmFile2.delete();

								uploadImg(file);// 변경한 이미지를 지정한 폴더에 업로드한다.
						} catch (IOException e) {
							e.printStackTrace();
						} // end catch
					} // end if
					pmpc.setPrd();// 부모창의 리스트를 업데이트 한다.
				} else {
					JOptionPane.showMessageDialog(pmpdv, "상품 정보가 변경되지 않았습니다.");
				} // end else
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(pmpdv, "DB에서 문제 발생!!");
				e.printStackTrace();
			} // end catch
			break;
		}// end switch
	}

	/**
	 * 큰 이미지명을 가진 File객체를 입력하여 업로드 폴더에 큰 이미지(ml_l1.gif)와 작은 이미지(s_m1_l1.gif)를 업로드한다.
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void uploadImg(File file) throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
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
	}

	@Override
	public void windowClosing(WindowEvent we) {
		pmpdv.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmpdv.getJbUpdate()) {// 수정
			if(chgImgFlag) {
			modifyPrd();
			}else {
				JOptionPane.showMessageDialog(pmpdv, "이미지 선택이 올바르지 않습니다. 새 이미지를 선택해주세요.");
				return;
			}
		} // end if
		if (ae.getSource() == pmpdv.getJbDelete()) {// 삭제
			StringBuilder deleteMsg = new StringBuilder();
			deleteMsg.append(pmpdv.getJtfMenuName().getText()).append("를 삭제하시겠습니까?");

			switch (JOptionPane.showConfirmDialog(pmpdv, deleteMsg.toString())) {
			case JOptionPane.OK_OPTION:
				removePrd(pmpdv.getPrdCode());
			}// end switch
		} // end if
		if (ae.getSource() == pmpdv.getJbEnd()) {// 종료
			pmpdv.dispose();
		} // end if
		if (ae.getSource() == pmpdv.getJbImg()) {// 이미지 변경
			chgImg();
		} // end if

	}// actionPerformed

}// class
