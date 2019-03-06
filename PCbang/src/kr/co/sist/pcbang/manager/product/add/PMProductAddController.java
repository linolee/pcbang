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
	}// ������

	/**
	 * �Է°��� �̹����� ������ �� ��ȿ�� ����(�̹���, ��ǰ��, ī�װ�, ������ �ԷµǾ��ٸ�)
	 */
	private void addPrd() {
		if (uploadImg.equals("")) {
			JOptionPane.showMessageDialog(pmpav, "�̹����� �������ּ���");
			return;
		} // end if

		JTextField jtfName = pmpav.getJtfMenuName();
		JComboBox<String> jcbCategory = pmpav.getJcbPrdCategory();
		JTextField jtfPrice = pmpav.getJtfPrice();

		if (jtfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpav, "��ǰ���� �ʼ� �Է�!!!!");
			jtfName.setText("");
			jtfName.requestFocus();
			return;
		} // end if

		//��ǰ�� Ȯ��
		PMProductVO pmpvo = null;
		try {
			List<PMProductVO> listproduct = pmpdao.selectPrd();
			String[] prdName = new String[1];
			for (int i = 0; i < listproduct.size(); i++) {
				pmpvo = listproduct.get(i);
				prdName[0] = pmpvo.getMenuName();
				if(jtfName.getText().trim().equals(prdName[0])) {
					JOptionPane.showMessageDialog(pmpav, "������ ��ǰ���� �����մϴ�");
					jtfName.setText("");
					jtfName.requestFocus();
					return;
				}//end if
			}//end for
			
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		
		if (jtfPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpav, "��ǰ ������ �ʼ� �Է�!!!!");
			jtfPrice.setText("");
			jtfPrice.requestFocus();
			return;
		} // end if

		// ������ ���ڸ� �Էµǵ��� try~catch
		int price = 0;
		try {
			price = Integer.parseInt(jtfPrice.getText().trim());
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmpav, "������ ���ڸ� �Է°����մϴ�.");
			return;
		} // end catch

		File file = new File(uploadImg);

		PMProductAddVO pmpavo = new PMProductAddVO(jtfName.getText().trim(), file.getName(),
				jcbCategory.getSelectedItem().toString(), price);

		try {
			PMProductDAO.getInstance().insertPrd(pmpavo); // DB�� �߰�
			// �̹�����, ����ϴ� ������ ���� (=��Ʈ�� �ʿ�)
			uploadImg(file);
			// ���ϸ���Ʈ�� ���ο� ���ϸ��� �߰��Ѵ�.
			PMProductController.PrdImgList.add(file.getName());

			// ����Ʈ ����
			pmpc.setPrd();

			// ���� ��ǰ�� �Է��� ���ϰ� �ϱ� ���ؼ� �Է��� �ʱ�ȭ
			jtfName.setText("");
			jtfPrice.setText("");
			String imgSavePath = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = imgSavePath + filepath;
			
			pmpav.getjlImg().setIcon(new ImageIcon(imgPath+"no_img.jpg"));

			JOptionPane.showMessageDialog(pmpav, "�޴��� �߰��Ǿ����ϴ�.");
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(pmpav, "���� ���ε� ����");
			ie.printStackTrace();
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(pmpav, "DB���� ������ �߻��Ͽ����ϴ�.");
			se.printStackTrace();
		} // end catch
	}// addPrd

	/**
	 * ū �̹������� ���� File��ü�� �Է��Ͽ� ���ε� ������ ū �̹����� ���� �̹���(s_)�� ���ε��Ѵ�.
	 * 
	 * @param file
	 */
	private void uploadImg(File file) throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			// modifyū�̹���,
			byte[] readData = new byte[512];
			String path = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = path + filepath;
			String uploadPath = imgPath;

			int len = 0;
			
			// ���� �̹��� ���ε�
			fis = new FileInputStream(file.getParent() + "/s_" + file.getName());// getParent�� ���� ���������� ����
			fos = new FileOutputStream(uploadPath + "s_" + file.getName());
			while ((len = fis.read(readData)) != -1) {
				fos.write(readData, 0, len);
				fos.flush();
			} // end while

			fis.close();
			fos.close();

			// ū �̹��� ���ε�
			fis = new FileInputStream(file);
			len = 0; // len�� 0���� �ʱ�ȭ
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
		FileDialog fdOpen = new FileDialog(pmpav, "��ǰ�̹��� ����", FileDialog.LOAD);
		fdOpen.setVisible(true);

		String path = fdOpen.getDirectory();
		String name = fdOpen.getFile();

		boolean flag = false;
		if (path != null) {
			String[] extFlag = { "jpg", "gif", "jpeg", "png", "bmp" };
			for (String ext : extFlag) {
				if (name.toLowerCase().endsWith(ext)) { // ���ε� ���� Ȯ����
					flag = true;
				} // end if
			} // end for
			if (flag) {
				uploadImg = path + name;
				pmpav.getjlImg().setIcon(new ImageIcon(uploadImg));
			} else {
				JOptionPane.showMessageDialog(pmpav, name + "�� �̹����� �ƴմϴ�.");
			} // end else
			
			String imgSavePath = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = imgSavePath + filepath;
			
			File origin = new File(imgPath + name);

			// ������ �̹����� �̹� �����ϴ� ��� : �̹��� ������ �̹� �����Ѵٸ� �ٸ� �޴��� ������ �� ���� ���� �� ���� �����Ƿ�
			// �߰� �� Ȯ����
			boolean existFlag = origin.exists();
			if (existFlag) {
				JOptionPane.showMessageDialog(pmpav, "������ �̹����� ���� �޴��� �ֽ��ϴ�.");
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
				JOptionPane.showMessageDialog(pmpav, "�̹��� ������ �ùٸ��� �ʽ��ϴ�. �� �̹����� �������ּ���.");
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
