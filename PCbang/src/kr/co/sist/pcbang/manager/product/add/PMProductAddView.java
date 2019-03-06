package kr.co.sist.pcbang.manager.product.add;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import kr.co.sist.pcbang.manager.product.PMProductController;
import kr.co.sist.pcbang.manager.product.PMProductView;

@SuppressWarnings("serial")
public class PMProductAddView extends JDialog {

	private JLabel jlImg;
	private JTextField jtfMenuName, jtfPrice;
	private JComboBox<String> jcbPrdCategory;
	private DefaultComboBoxModel<String> dcCategory;
	private JButton jbImg, jbAdd, jbEnd;
	
	public PMProductAddView(PMProductView pmpv, PMProductController pmpc) {
		String imgSavePath = System.getProperty("user.dir");
		String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
		String imgPath = imgSavePath + filepath;
		ImageIcon iiProduct = new ImageIcon(imgPath+"no_img.jpg");
		jlImg = new JLabel(iiProduct);
		jtfMenuName = new JTextField();
		jtfPrice = new JTextField();
		
		dcCategory = new DefaultComboBoxModel<String>();
		jcbPrdCategory = new JComboBox<String>(dcCategory);
		
		jbImg = new JButton("�̹��� ����");
		jbAdd = new JButton("�߰�");
		jbEnd = new JButton("â �ݱ�");
		
		setLayout(null);
		
		JLabel jlDetailTitle = new JLabel("��ǰ ���� �߰�");
		jlDetailTitle.setFont(new Font("�������", Font.BOLD, 25));
		
		//��ġ
		jlDetailTitle.setBounds(20,25,250,30);
		jlImg.setBounds(10,60,244,220);
		jbImg.setBounds(80, 290, 120, 25);
		
		JLabel jlProductName  = new JLabel("��ǰ��");
		JLabel jlCategory  = new JLabel("ī�װ�");
		JLabel jlPrice  = new JLabel("����");		
		
		jlProductName.setBounds(270,65,80,25);
		jlCategory.setBounds(270,95,80,25);
		jlPrice.setBounds(270,125,80,25);
		
		jtfMenuName.setBounds(340,65,185,25);
		jcbPrdCategory.setBounds(340,95,185,25);
		jtfPrice.setBounds(340,125,185,25);
		
		jbAdd.setBounds(320,300,80,30);
		jbEnd.setBounds(410,300,80,30);
		
		add(jlDetailTitle);
		add(jlImg);
		add(jbImg);
		add(jlProductName);
		add(jlCategory);
		add(jlPrice);
		add(jtfMenuName);
		add(jcbPrdCategory);
		add(jtfPrice);
		add(jbAdd);
		add(jbEnd);
		
		setCategory(); //Category ���� ->�ϴ� �޼ҵ�� ���������� DB���� ������ �� �ֵ��� �ٲ������
		
		//�̺�Ʈ ���
		PMProductAddController pmpac = new PMProductAddController(this, pmpc);
		addWindowListener(pmpac);
		jbImg.addActionListener(pmpac);
		jbAdd.addActionListener(pmpac);
		jbEnd.addActionListener(pmpac);
		
		setBounds(pmpv.getX()+100, pmpv.getY()+50, 550, 380);
		setVisible(true);
		setResizable(false);
	}

	////////////////////ī�װ� ���߿� DB���� �ҷ��� �� �ֵ��� ����//////////////////////
	private void setCategory() {
		String[] category = {"����", "����", "���"};
		for(int i=0; i<category.length; i++) {
		dcCategory.addElement(category[i]);
		}//end for
	}//setCategory

	
	public JLabel getjlImg() {
		return jlImg;
	}

	public JTextField getJtfMenuName() {
		return jtfMenuName;
	}

	public JTextField getJtfPrice() {
		return jtfPrice;
	}

	public JComboBox<String> getJcbPrdCategory() {
		return jcbPrdCategory;
	}

	public DefaultComboBoxModel<String> getDcCategory() {
		return dcCategory;
	}

	public JButton getjbImg() {
		return jbImg;
	}

	public JButton getJbAdd() {
		return jbAdd;
	}

	public JButton getJbEnd() {
		return jbEnd;
	}
	
}
