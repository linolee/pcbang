package kr.co.sist.pcbang.manager.product.detail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import admin.model.PMProductDAO;
import admin.view.PMProductDetailView;

public class PMProductDetailController extends WindowAdapter implements ActionListener{

	private PMProductDetailView pmpdv;
	private PMProductController pmpc;
	private PMProductDAO pmp_dao;
	private String originImg;
	private String uploadImg;
	
	public PMProductDetailController(PMProductDetailView pmpdv, PMProductController pmpc, String orginalImg) {
		this.pmpdv=pmpdv;
		this.pmpc=pmpc;
		pmp_dao=PMProductDAO.getInstance();
		this.originImg=orginalImg;
		uploadImg="";
	}//PMProductDetailController
	
	@Override
	public void windowClosing(WindowEvent we) {
		pmpdv.dispose();
	}// windowClosing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==pmpdv.getJbUpdate()) {//수정
			
		}//end if
		if(ae.getSource()==pmpdv.getJbDelete()) {//삭제
			
		}//end if
		if(ae.getSource()==pmpdv.getJbEnd()) {//종료
			pmpdv.dispose();
		}//end if
		if(ae.getSource()==pmpdv.getJbImg()) {//이미지 변경
			
		}//end if
		
		
	}//actionPerformed

}//class
