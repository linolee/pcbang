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
//		super(pmpv, "상품 정보 추가", true);  
		
		ImageIcon iiProduct = new ImageIcon("C:/dev/workspace/lunch_prj/src/kr/co/sist/lunch/admin/img/no_img.jpg");
		jlImg = new JLabel(iiProduct);
		jtfMenuName = new JTextField();
		jtfPrice = new JTextField();
		
		jcbPrdCategory = new JComboBox<String>();
		
		jbImg = new JButton("이미지 선택");
		jbAdd = new JButton("추가");
		jbEnd = new JButton("창 닫기");
		
		setLayout(null);
		
		JLabel jlDetailTitle = new JLabel("상품 정보 추가");
		jlDetailTitle.setFont(new Font("나눔고딕", Font.BOLD, 25));
		
		//배치
		jlDetailTitle.setBounds(10,25,250,30);
		jlImg.setBounds(10,60,244,220);
		jbImg.setBounds(80, 290, 120, 25);
		
		JLabel jlProductName  = new JLabel("상품명");
		JLabel jlCategory  = new JLabel("카테고리");
		JLabel jlPrice  = new JLabel("가격");		
		
		jlProductName.setBounds(270,65,80,25);
		jlCategory.setBounds(270,95,80,25);
		jlPrice.setBounds(270,125,80,25);
		
		jtfMenuName.setBounds(340,65,185,25);
		jcbPrdCategory.setBounds(340,95,185,25);
		jtfPrice.setBounds(340,125,185,100);
		
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
		
		//이벤트 등록
		PMProductAddController pmpac = new PMProductAddController(this, pmpc);
		addWindowListener(pmpac);
		jbImg.addActionListener(pmpac);
		jbAdd.addActionListener(pmpac);
		jbEnd.addActionListener(pmpac);
		
		setBounds(pmpv.getX()+100, pmpv.getY()+50, 550, 380);
		setVisible(true);
		setResizable(false);
	}

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
