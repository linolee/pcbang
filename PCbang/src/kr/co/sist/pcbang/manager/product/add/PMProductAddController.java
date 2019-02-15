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
	}//������
	
	/**
	 * �Է°��� �̹����� ������ �� ��ȿ�� ����(�̹���, ��ǰ��, ī�װ�, ������ �ԷµǾ��ٸ�)
	 */
	private void addPrd() {
		if(uploadImg.equals("")) {
			JOptionPane.showMessageDialog(pmpav, "�̹����� �������ּ���");
			return;
		}//end if
		
		JTextField jtfName = pmpav.getJtfMenuName();
		JComboBox<String> jcbCategory = pmpav.getJcbPrdCategory();
		JTextField jtfPrice = pmpav.getJtfPrice();
		
		if(jtfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpav, "��ǰ���� �ʼ� �Է�!!!!");
			jtfName.setText("");
			jtfName.requestFocus();
			return;
		}//end if
		
		if(jtfPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(pmpav, "��ǰ ������ �ʼ� �Է�!!!!");
			jtfPrice.setText("");
			jtfPrice.requestFocus();
			return;
		}//end if
		
		//������ ���ڸ� �Էµǵ��� try~catch
		int price = 0;
		try {
			price = Integer.parseInt(jtfPrice.getText().trim());
		}catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(pmpav, "������ ���ڸ� �Է°����մϴ�.");
			return;
		}//end catch
		
		File file = new File(uploadImg);
		
		PMProductAddVO pmpavo= new PMProductAddVO(jtfName.getText().trim(), 
				file.getName(), jcbCategory.getSelectedItem().toString(), price);
		
		try {
			PMProductDAO.getInstance().insertPrd(pmpavo); //DB�� �߰�
			//�̹�����, ����ϴ� ������ ���� (=��Ʈ�� �ʿ�)
			uploadImg(file);
			//���ϸ���Ʈ�� ���ο� ���ϸ��� �߰��Ѵ�.
			LunchAdminMain.lunchImageList.add(file.getName());
			
			//LunchDetailController��  modify ū�̹���, �����̹����� ���� ������ �����鼭 �ٽ� ����� �ڵ�� ��ħ
//			uploadImg(file);
//			//������ ���� catch�� ������ DB�� �߰��� �ȵ�
//			LunchAdminDAO.getInstance().insertLunch(lavo);//������ ���� �ʴ� ��� DB�� �߰�
			
			//����Ʈ ����
			lmc.setLunch();
			
			//���� ���ö��� �Է��� ���ϰ� �ϱ� ���ؼ� �Է��� �ʱ�ȭ
			jtfName.setText("");
			jtfPrice.setText("");
			jtaSpec.setText("");
			
			lav.getJlLunchImg().setIcon(new ImageIcon("C:/dev/workspace/lunch_prj/src/kr/co/sist/lunch/admin/img/no_img.jpg"));
			
			JOptionPane.showMessageDialog(lav, "���ö��� �߰��Ǿ����ϴ�.");
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(lav, "���� ���ε� ����");
			ie.printStackTrace();
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(lav, "DB���� ������ �߻��Ͽ����ϴ�.");
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
