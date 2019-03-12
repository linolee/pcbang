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
	 * ��ǰ �����
	 * 
	 * @param code
	 */
	private void removePrd(String code) {
		try {
			if (pmp_dao.deletePrd(code)) {// ��ǰ�� ����
				pmpc.setPrd();// ��ǰ ����Ʈ�� �����Ѵ�.
				/*// ������ ����
				String path = System.getProperty("user.dir");
				String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
				String imgPath = path + filepath;
				File file = new File(imgPath + originImg);
				// �ϳ������� �ϸ� ���ϸ��� �о������ ���ϴ� ������ �߻��� ���� �ֱ� ������ ���� �ΰ��� ��
				File rmFile = new File(file.getAbsolutePath());// ū ����
				File rmFile2 = new File(file.getParent() + "/s_" + file.getName());// ��������

				rmFile.delete();
				rmFile2.delete();*/

				JOptionPane.showMessageDialog(pmpdv, "��ǰ�� �����Ǿ����ϴ�.");
				pmpdv.dispose();

			} else {
				JOptionPane.showMessageDialog(pmpdv, "��ǰ�� �������� �ʽ��ϴ�.");
			} // end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(pmpdv, "DB���� ������ �߻��Ͽ����ϴ�.");
			e.printStackTrace();
		} // end catch
	}// removePrd

	/**
	 * �̹��� �ٲٱ�
	 */
	private void chgImg() {
		FileDialog fdOpen = new FileDialog(pmpdv, "��ǰ�̹��� ����", FileDialog.LOAD);
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
				pmpdv.getJlImg().setIcon(new ImageIcon(uploadImg));
			} else {
				JOptionPane.showMessageDialog(pmpdv, name + "�� �̹����� �ƴմϴ�.");
			} // end else
			
			/*			String imgSavePath = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String imgPath = imgSavePath + filepath;
			File origin = new File(imgPath+name);
			
			// ������ �̹����� �̹� �����ϴ� ��� : �̹��� ������ �̹� �����Ѵٸ� �ٸ� �޴��� ������ �� ���� ���� �� ���� �����Ƿ�
			// �߰� �� Ȯ����
			boolean existFlag = origin.exists();
			if (existFlag) {
				JOptionPane.showMessageDialog(pmpdv, "������ �̹����� ���� �޴��� �ֽ��ϴ�.");
				uploadImg="";
				chgImgFlag=false;
				return;
			}else {
				
			}*/
			
		} // end if
		
	}// chgImg

	/**
	 * ��ǰ�ڵ�, ��ǰ��, ������ �Է¹޾� �ڵ忡 �ش��ϴ� ��ǰ�� ������ ����
	 */
	private void modifyPrd() {// void�� �ϰ� DB���� T/F ������ ���� ��
		// ��ȿ�� ���� : ��ǰ��, ������ ""�� �ƴ� ��, ������ ��������,
		// �̹����� ����Ǿ��ٸ� �̹����� s_�� ���� �̹����� �����ϴ� �̹�������
		JTextField tfName = pmpdv.getJtfMenuName();
		JTextField tfPrice = pmpdv.getJtfPrice();
		JComboBox<String> jcbox = pmpdv.getJcbPrdCategory();

		// ��ĭ���� �켱 Ȯ��
		if (tfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpdv, "��ǰ���� �Է����ּ���.");
			tfName.setText("");
			tfName.requestFocus();
		} // end if
		if (tfPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpdv, "��ǰ ������ �Է����ּ���.");
			tfPrice.setText("");
			tfPrice.requestFocus();
		} // end if

		int price = 0;
		try {
			price = Integer.parseInt(tfPrice.getText().trim());
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmpdv, "��ǰ�� ������ ���ڸ� �Է°����մϴ�");
			nfe.printStackTrace();
		} // end catch

		// ������ �̹��� �տ� s_�� ���� �̹����� ���� ��� : �̹��� �۾��� �Ǿ����� ���� ��� uploadImg�� ���
		if (!uploadImg.equals("")) {// �̹��� ������ ���� �� �� ���� ���
			File tempFile = new File(uploadImg); // ū�̹���
			File smallFile = new File(tempFile.getParent() + "/s_" + tempFile.getName()); // �����̹���
				
			if (!smallFile.exists()) {
				JOptionPane.showMessageDialog(pmpdv, "�����Ͻ� ������ ���� �̹����� �������� �ʽ��ϴ�.");
				uploadImg = "";
				return;
			} // end if
		} // end if

		StringBuilder updateMsg = new StringBuilder();
		updateMsg.append("�������� \n").append("��ǰ �� : ").append(tfName.getText()).append("\n").append("���� : ")
				.append(tfPrice.getText()).append("\n").append("ī�װ� : ").append(jcbox.getSelectedItem().toString())
				.append("\n").append("���� ������ ��ǰ�� ������ �����Ͻðڽ��ϱ�?");

		switch (JOptionPane.showConfirmDialog(pmpdv, updateMsg)) {
		case JOptionPane.OK_OPTION:
			// ����� ������ VO�� �����ϰ�
			File file = new File(uploadImg);
			PMProductUpdateVO pmpuvo = new PMProductUpdateVO(pmpdv.getPrdCode(), tfName.getText().trim(),
					file.getName(), jcbox.getSelectedItem().toString(), price);
			try {
				if (pmp_dao.updatePrd(pmpuvo)) {// DB Table���� update�� ����
					JOptionPane.showMessageDialog(pmpdv, "��ǰ ������ ����Ǿ����ϴ�.");
					if (!uploadImg.equals("")) {// ������ �̹����� �����ϴ� ���
						try {
							// ���� �̹����� ������ ��.
							// ������ ����
							String path = System.getProperty("user.dir");
							String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
							String imgPath = path + filepath;

							File originfile = new File(imgPath + originImg);

							// �ϳ������� �ϸ� ���ϸ��� �о������ ���ϴ� ������ �߻��� ���� �ֱ� ������ ���� �ΰ��� ��
								File rmFile = new File(originfile.getAbsolutePath());// ū ����
								File rmFile2 = new File(originfile.getParent() + "/s_" + originfile.getName());// ��������

								rmFile.delete();
								rmFile2.delete();

								uploadImg(file);// ������ �̹����� ������ ������ ���ε��Ѵ�.
						} catch (IOException e) {
							e.printStackTrace();
						} // end catch
					} // end if
					pmpc.setPrd();// �θ�â�� ����Ʈ�� ������Ʈ �Ѵ�.
				} else {
					JOptionPane.showMessageDialog(pmpdv, "��ǰ ������ ������� �ʾҽ��ϴ�.");
				} // end else
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(pmpdv, "DB���� ���� �߻�!!");
				e.printStackTrace();
			} // end catch
			break;
		}// end switch
	}

	/**
	 * ū �̹������� ���� File��ü�� �Է��Ͽ� ���ε� ������ ū �̹���(ml_l1.gif)�� ���� �̹���(s_m1_l1.gif)�� ���ε��Ѵ�.
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
	}

	@Override
	public void windowClosing(WindowEvent we) {
		pmpdv.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == pmpdv.getJbUpdate()) {// ����
			if(chgImgFlag) {
			modifyPrd();
			}else {
				JOptionPane.showMessageDialog(pmpdv, "�̹��� ������ �ùٸ��� �ʽ��ϴ�. �� �̹����� �������ּ���.");
				return;
			}
		} // end if
		if (ae.getSource() == pmpdv.getJbDelete()) {// ����
			StringBuilder deleteMsg = new StringBuilder();
			deleteMsg.append(pmpdv.getJtfMenuName().getText()).append("�� �����Ͻðڽ��ϱ�?");

			switch (JOptionPane.showConfirmDialog(pmpdv, deleteMsg.toString())) {
			case JOptionPane.OK_OPTION:
				removePrd(pmpdv.getPrdCode());
			}// end switch
		} // end if
		if (ae.getSource() == pmpdv.getJbEnd()) {// ����
			pmpdv.dispose();
		} // end if
		if (ae.getSource() == pmpdv.getJbImg()) {// �̹��� ����
			chgImg();
		} // end if

	}// actionPerformed

}// class
