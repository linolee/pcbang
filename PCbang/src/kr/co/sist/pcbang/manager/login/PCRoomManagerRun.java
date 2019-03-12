package kr.co.sist.pcbang.manager.login;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.pcbang.manager.product.FileServer;

public class PCRoomManagerRun {
	public static List<String> PrdImgList;
	
	public PCRoomManagerRun() {
		PrdImgList=new ArrayList<String>();
		//������ �����ϴ� �̹��� �Է�
		
//		//String path = this.getClass().getResource("/").getPath()+"kr/co/sist/pcbang/manager/product/img/";
		String path = System.getProperty("user.dir");
		String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\"; 
		String abspath = path+filepath;

		File file = new File(abspath);
		for(String tempName:file.list()) {
			if(!tempName.startsWith("s_")) {
			PrdImgList.add(tempName);
			}//end if
		}//end for
	}//������
	
	public static void main(String[] args) {
		new PCRoomManagerRun();
		FileServer fs = new FileServer();
		fs.start();
		new PMLoginView();
	}//main

}//class

