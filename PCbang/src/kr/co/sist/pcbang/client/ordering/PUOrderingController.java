package kr.co.sist.pcbang.client.ordering;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import kr.co.sist.pcbang.client.main.PUMainController;

public class PUOrderingController extends WindowAdapter implements MouseListener, ActionListener {

	private PUOrderingView puov;
	private PUOrderingDAO puo_dao;
	private PUMainController pumc;
	private JButton jb;
	private List<PUOrderVO> ovoList;
	private Map<String, Integer> productCntMap;
	private Map<String, Integer> productPriceMap;
	private String orderNum;
	private boolean flagOrder;
	private int seatNum;
    
	public static final int DBL_CLICK=2;
	
	public PUOrderingController(PUOrderingView puov, int seatNum) {
		this.puov=puov;
		jb=new JButton("취소");
		ovoList=new ArrayList<PUOrderVO>();
		puo_dao=PUOrderingDAO.getInstance();
		productCntMap=new HashMap<String,Integer>();
		productPriceMap=new HashMap<String,Integer>();
		this.seatNum=seatNum;
//		try {
//			String[] fileNames=orderImageList();//클라이언트가 가진 이미지를 체크하여
//			orderImageSend(fileNames);//서버로 보내 없는 이미지를 받은 후  
//		}catch (IOException e) {
//			e.printStackTrace();
//		}//end catch

		setProduct();//JTable을 조회 갱신
		setBestProduct();//bestMenu
		setProductCategory("라면");
		setProductCategory("스낵");
		setProductCategory("음료");
		JTable jtOrder=puov.getJtOrderlist();
//		jtOrder.addItemListener
		
	}//PUOrderingController
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==puov.getJbtOk()) {
			Set<String>     allKeys=productCntMap.keySet();
			Iterator<String> ita =allKeys.iterator();
	           Integer value=0;
	           while(ita.hasNext()) { //키가 존재 한다면
	                value=productCntMap.get(ita.next());//얻어낸 키를 가지고  Map의 값을 얻는다.
	                //System.out.println(value);
	                if(value==0) {
	                	//JOptionPane.showMessageDialog(puov, "수량을 설정해 주세요.");
	                	//return;
	                }//end if
	           }//end while
			JOptionPane.showMessageDialog(puov, "이 목록으로 주문합니다");
			printOrder();
		}//end if
		if(ae.getSource()==puov.getJbtExit()) {
			//JOptionPane.showMessageDialog(puov, "닫기");
			puov.dispose();
		}//end if
		if(ae.getSource()==puov.getJbtDel()) {//상품 삭제
			System.out.println("삭제");
				JTableRemoveRow();
				//orderCancle();
		}
	}//actionPerformed
	
	private void orderCancle() {
		int select = -1;
		JTable table=puov.getJtOrderlist();
		select = JOptionPane.showConfirmDialog(puov, orderNum+"번 주문을 취소하시겠습니까?");
		if(select == JOptionPane.OK_OPTION) {
			for(int i=0;i<ovoList.size(); i++) {
				//System.out.println(ovoList.size());
				//System.out.println((String)table.getValueAt(table.getSelectedRow(), 0));
				if(ovoList.get(i).toString().contains((String)table.getValueAt(table.getSelectedRow(), 0))) {
					JOptionPane.showMessageDialog(puov, orderNum+"번 주문이 취소되었습니다 !");
					ovoList.remove(i);

					productCntMap.remove(table.getValueAt(table.getSelectedRow(), 0));
				}//end if
			}//end for
			
			//setOrder();
			//setOrderComplete();
			//flagOrder = false;
		}//end if
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {	
		//JOptionPane.showMessageDialog(puov, "마우스 이벤트가 눌렸습니다.");
		if(me.getSource()==puov.getJtMenu()) {//메뉴
			//JTable jt=puov.getJtMenu();
//			String columninfo=(String)jt.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn());
			//Object columninfo=jt.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn());
			//String product_code=columninfo.substring(columninfo.indexOf("|"), columninfo.lastIndexOf("|")+1);
			//JOptionPane.showMessageDialog(puov, columninfo);
			//addOrderList(columninfo);
			
			switch(me.getClickCount()) {
			case DBL_CLICK :
				JTable jt=puov.getJtMenu();
				String productName=(String)jt.getValueAt(jt.getSelectedRow(), 1);
				String productPrice=(String)jt.getValueAt(jt.getSelectedRow(), 2);
				String columninfo=productName+"\n"+productPrice;
				PUOrderVO puovo=new PUOrderVO((String)jt.getValueAt(jt.getSelectedRow(), 0).toString(), (String)jt.getValueAt(jt.getSelectedRow(), 3), productName, productPrice);
				for(int i=0;i<ovoList.size(); i++) {
		            //System.out.println(ovoList.get(i));
					if(ovoList.get(i).toString().contains((String)jt.getValueAt(jt.getSelectedRow(), 3))) {
						JOptionPane.showMessageDialog(puov, "이미 추가된 상품입니다.");
						return;
					}//end else
				}//end for
				addOrderList(columninfo);
				ovoList.add(puovo);
				productCntMap.put((String) jt.getValueAt(jt.getSelectedRow(), 1), 1);
				productPriceMap.put((String) jt.getValueAt(jt.getSelectedRow(), 1), Integer.parseInt((String) jt.getValueAt(jt.getSelectedRow(), 2)));
			}//end switch
		}//end if
		if(me.getSource()==puov.getJtOrderlist()) {//주문
//			JTable jtO=puov.getJtOrderlist();
//			//Object column=jtO.getValueAt(jtO.getSelectedRow(), jtO.getSelectedColumn());
//			if(me.getSource()==puov.getJtOrderlist().getColumnModel().getColumn(3)) {
//				//JOptionPane.showMessageDialog(puov, "삭제");
//				
//			}else {
//				//JOptionPane.showMessageDialog(puov, column);
//			}//end else
			flagOrder = true;
			JTable jt = puov.getJtOrderlist();
			System.out.println(jt.getSelectedRow());
			orderNum = (String)jt.getValueAt(jt.getSelectedRow(), 0);
			
		}//end if
		
		if(me.getSource()==puov.getjtbMenu()) {//탭
			JTable jt=null;
			if(puov.getjtbMenu().getSelectedIndex()==1) {
				setProductCategory("라면");
				jt=puov.getJtRamen();
			}
			if(puov.getjtbMenu().getSelectedIndex()==2) {
				setProductCategory("스낵");
				jt=puov.getJtSnack();
			}
			if(puov.getjtbMenu().getSelectedIndex()==3) {
				setProductCategory("음료");
				jt=puov.getJtDrink();
			}
			if(me.getSource()==jt) {
				switch(me.getClickCount()) {
				case DBL_CLICK :
					String product_name=(String)jt.getValueAt(jt.getSelectedRow(), 1);
					String product_price=(String)jt.getValueAt(jt.getSelectedRow(), 2);
					String columninfo=product_name+"\n"+product_price;
					addOrderList(columninfo);
				}//end switch
			}//end if
		}//end if
		
		if(me.getSource()==puov.getJtBestProduct()) {//베스트 메뉴
			switch(me.getClickCount()) {
			case DBL_CLICK ://
				JTable jt=puov.getJtBestProduct();
				Object imgpath=jt.getValueAt(0, jt.getSelectedColumn());
				//System.out.println(jt.getValueAt(0, jt.getSelectedColumn()));
				try {
					String img=imgpath.toString().substring(imgpath.toString().indexOf("/")+3);
					
					PUOrderVO puovo=puo_dao.selectCode(img.toString());
					String columninfo=puovo.getProductName()+"\n"+puovo.getProductPrice();
					
					if(puovo==null) {//코드로 조회된 결과기 없을 때
						JOptionPane.showMessageDialog(puov, img+"로 조회된 상품이 없습니다.");
					}else {//코드로 조회된 결과가 있을 때
						//System.out.println(puovo);
						//상품명+가격 담기
						addOrderList(columninfo);
					}//end else
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(puov, "상세정보 조회시 문제 발생!");
					e.printStackTrace();
				}//end catch
			}//end switch
		}//end if
		
		if(me.getSource()==puov.getJtOrderlist()) {//주문목록
			JTable jtO=puov.getJtOrderlist();
			Object column=null;
			try {
			column=jtO.getValueAt(jtO.getSelectedRow(), jtO.getSelectedColumn());
					
			if(jtO.getValueAt(jtO.getSelectedRow(), 1)==column) {
				//JOptionPane.showMessageDialog(puov, "수량");
				//column.addItemListener
				
			}else {
			//JOptionPane.showMessageDialog(puov, column);
			}//end if

			}catch(ArrayIndexOutOfBoundsException aiobe) {
				//JOptionPane.showMessageDialog(puov, "주문목록에서 클릭인덱스 실패");//삭제하면 뜸..
				//aiobe.printStackTrace();
			}
		}//end if
	}//mouseClicked
	
	/**
	 * 상품 불러와 테이블에 앉히기
	 */
	private void setProduct() {
		try {
			List<PUOrderVO> listProduct=puo_dao.selectProduct();
			File f = new File("C:/Users/owner/git/pcbang/PCbang/src/kr/co/sist/pcbang/manager/product/img");
			//C:/Users/owner/git/pcbang/PCbang/src/kr/co/sist/pcbang/manager/product/img
			//this.toString()
			
			DefaultTableModel dtmProduct=puov.getDtmMenu();
			dtmProduct.setRowCount(0);
					
			Object[] rowData=null;
			PUOrderVO puovo=null;
			
			for(int i=0;i<listProduct.size(); i++) {
				puovo=listProduct.get(i);
				//테이블에 추가할 데이터 생성
				rowData=new Object[4];
				rowData[0]=new ImageIcon(f.getCanonicalPath()+"/s_"+puovo.getImg());
				rowData[1]=puovo.getProductName();
				rowData[2]=puovo.getProductPrice();
				rowData[3]=puovo.getProductCode();
				//생성된 데이터를 테이블에 추가
				dtmProduct.addRow(rowData);
			}//end for
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(puov, "상품 목록을 조회하는 중 문제 발생");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
	}//setProduct
	
	/**
	 * 카테고리별 상품 불러와 앉히기
	 */
	private void setProductCategory(String category) {
		try {
			
			File f = new File("C:/Users/owner/git/pcbang/PCbang/src/kr/co/sist/pcbang/manager/product/img");
			List<PUOrderVO> listProduct=puo_dao.selectProductCategory(category);
			DefaultTableModel dtmProduct=null;
			JTable jtC=null;
			if(category.equals("라면")){
				dtmProduct=puov.getDtmRamen();
				jtC=puov.getJtRamen();
			}else if(category.equals("음료")) {
				dtmProduct=puov.getDtmDrink();
				jtC=puov.getJtDrink();
			}else if(category.equals("스낵")) {
				dtmProduct=puov.getDtmSnack();
				jtC=puov.getJtSnack();
			}//end else
			dtmProduct.setRowCount(0);
			
			Object[] rowData=null;
			PUOrderVO puovo=null;	
			
			for(int i=0;i<listProduct.size(); i++) {
				puovo=listProduct.get(i);
				//테이블에 추가할 데이터 생성
				rowData=new Object[4];
				rowData[0]=new ImageIcon(f.getCanonicalPath()+"/s_"+puovo.getImg());
				rowData[1]=puovo.getProductName();
				rowData[2]=puovo.getProductPrice();
				rowData[3]=puovo.getProductCode();
				//생성된 데이터를 테이블에 추가
				dtmProduct.addRow(rowData);
			}//end for
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(puov, "상품 목록을 조회하는 중 문제 발생");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
	}//setProduct
	
	/**
	 * 베스트 상품 불러와 테이블에 앉히기
	 */
	private void setBestProduct() {
		try {
			File f = new File("C:/Users/owner/git/pcbang/PCbang/src/kr/co/sist/pcbang/manager/product/img");
			List<PUOrderVO> listProduct=puo_dao.selectProductBest();
			
			DefaultTableModel dtmProduct=puov.getDtmBestProduct();
			dtmProduct.setRowCount(0);
					
			Object[] rowData=null;
			PUOrderVO puovo=null;
			int cnt=listProduct.size();
			List<Integer> cntArr=null;
			if(cnt!=0) {
				cntArr=new ArrayList<Integer>();
				for(int i=0; i<7; i++) {
					cntArr.add(i);//list에 담는다
				}//end for
			}//end if
	
			int rowNum=(int)cnt/5+1;
			for(int x=0; x<rowNum; x++) {
				int i=0;
				if(x==0) {
					rowData=new Object[7];//5카ㄴ으로 나누어 
					for(i=0; i<7; i++){
						//System.out.print(cntArr.get(i));
						puovo=listProduct.get(i);
						Object img=new ImageIcon(f.getCanonicalPath()+"/s_"+puovo.getImg());
						Object name=puovo.getProductName();
						Object price=puovo.getProductPrice();
						Object productCode=puovo.getProductCode();
						rowData[i]=img;
					}//end for
					dtmProduct.addRow(rowData);//dtm에 넣어줄꺼야
				}//end else
			}//end for
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(puov, "상품 목록을 조회하는 중 문제 발생");
			e.printStackTrace();
		}//end catch
		catch (IOException e) {
			e.printStackTrace();
		}
	}//setBestProduct
	
	/**
	 * 주문 목록에 추가
	 */
	private void addOrderList(Object columninfo) {
		//선언
		DefaultTableModel dtmOrder=puov.getDtmOrderlist();
		DefaultComboBoxModel<String> dcmCombo=new DefaultComboBoxModel<String>();
		JTable jtM=puov.getJtMenu();
		
		//콤보박스 생성
		String[] quan = {"1","2","3","4","5","6","7","8","9","10"};
		JComboBox<String> combo=new JComboBox<String>(dcmCombo);
		//combo.setSelectedIndex(0);
		combo.setMaximumRowCount(10);
		for(int i=0; i<quan.length; i++) {
			dcmCombo.setSelectedItem(1);
			dcmCombo.addElement(quan[i]);
		}//end for
		JTable jtOrder=puov.getJtOrderlist();
		combo.addItemListener(e -> {
			try {
			String str=e.getItemSelectable().toString();
			//System.out.println(str.substring(str.indexOf("selectedItemReminder")+21,str.indexOf("selectedItemReminder")+22));
			//selectedItemReminder
			System.out.println(productPriceMap.get(jtOrder.getValueAt(jtOrder.getSelectedRow(), 0)));
			int price=productPriceMap.get(jtOrder.getValueAt(jtOrder.getSelectedRow(), 0));
			int cnt=0;
			if(str.substring(str.indexOf("selectedItemReminder")+21).contains("10")) {
				cnt=Integer.parseInt(str.substring(str.indexOf("selectedItemReminder")+21,str.indexOf("selectedItemReminder")+23));
			}else {
				cnt=Integer.parseInt(str.substring(str.indexOf("selectedItemReminder")+21,str.indexOf("selectedItemReminder")+22));
			}
			//System.out.println((String) jtOrder.getValueAt(jtOrder.getSelectedRow(), 0).toString());
			productCntMap.put((String) jtOrder.getValueAt(jtOrder.getSelectedRow(), 0).toString(), cnt);
			System.out.println(cnt+"/"+price);
			jtOrder.setValueAt(cnt*price, jtOrder.getSelectedRow(), 2);
			cnt=0;
			//price=0;
			}catch (ArrayIndexOutOfBoundsException aio) {
				aio.printStackTrace();
				//JOptionPane.showMessageDialog(puov, "인덱스 오류");
			}
		});

		TableColumn column=jtOrder.getColumnModel().getColumn(1);
		column.setCellEditor(new DefaultCellEditor(combo));
		//Map<String, >
		
		//받아온 데이터를 잘라 알맞은 칸에 출력
		String dataArr=String.valueOf(columninfo);
		String[] data=dataArr.split("\n");

		Object[] rowData=null;
		rowData=new Object[4];
		rowData[0]=data[0];
		rowData[1]=1;
		//rowData[1]=column.setCellEditor(combo);
				/*new DefaultCellEditor(combo).getCellEditorValue();//1개나옴*/
		rowData[2]=data[1];
		//rowData[3]=data[3];
		dtmOrder.addRow(rowData);
		
		jtOrder.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(combo));
		//jtOrder.getColumnModel().getColumn(3).setCellRenderer(new tableCell());
		//jtOrder.getColumnModel().getColumn(3).setCellEditor(new tableCell());
	
//		if(combo.getSelectedIndex()!=-1) {
//			System.out.println(combo.getSelectedIndex());
//		}
	}//addOrderList
	
	
	
		
	/**
	 * 상품 이미지 리스트
	 */
	private String[] orderImageList() {
		String[] fileList=null;
		String path="F:/dev/workspace/javase_teamprj2/src/img/";
		
		File dir=new File(path);
		//s_가 붙은 파일명만 배열에
		List<String> list=new ArrayList<String>();
		
		for(String f_name: dir.list()) {
			if(!f_name.startsWith("m1_")) {
				list.add(f_name);
				
			}//end if
		}//end for
		
		fileList=new String[list.size()];
		list.toArray(fileList);
		
		//System.out.println(Arrays.toString(fileList));
		return fileList;
	}//orderImageList
	
	/**
	 * 서버로 이미지를 보내고 없는 이미지를 받는 일.
	 * @param fileName
	 */
	private void orderImageSend(String[] fileNames) throws IOException{
		Socket socket=null;
		DataOutputStream dos=null;
		DataInputStream dis=null;
		
		try {
			socket=new Socket("211.63.89.152", 25000);
			dos=new DataOutputStream(socket.getOutputStream());
			dos.writeInt(fileNames.length);
			
			for(int i=0; i<fileNames.length; i++ ) {
				dos.writeUTF(fileNames[i]);//서버로 전송
			}//end for
			dos.flush();
			
			dis=new DataInputStream(socket.getInputStream());
			//서버가 보내오는 파일의 갯수 저장
			int fileCnt=dis.readInt();
			System.out.println("클라이언트"+fileCnt+"개 받음");
			String fileName="";
			int fileSize=0;
			int fileLen=0;
			
			FileOutputStream fos=null;
			
			for(int i=0;i<fileCnt; i++) {
				
				//전달받을 파일 조각의 갯수
				fileSize=dis.readInt();

				//파일 명 받기
				fileName=dis.readUTF();
				fos=new FileOutputStream("F:/dev/workspace/javase_teamprj2/src/img/"+fileName);
				
				byte[] readData=new byte[512];
				
				while(fileSize>0) {
					fileLen=dis.read(readData);//서버에서 전송한 파일조각을 읽어들여
					fos.write(readData, 0, fileLen);//생성한 파일로 기록
					fos.flush();
					fileSize--;
				}//end while
			}//end for
		}finally {
			if(dos!=null) {dos.close();}//end if
			if(socket!=null) {socket.close();}//end if
		}//end finally
	}//orderImageSend
	
	   /**
		 * 주문사항을 보여주고(영수증) 주문테이블에 저장
		 */
		private void printOrder() {
			JTextArea jtaReceipt=new JTextArea(26,22);
			jtaReceipt.setEditable(false);
			JScrollPane jspReceipt=new JScrollPane(jtaReceipt);
			//PUOrderVO puovoP=null;
			StringBuilder data=null;
			int totalPrice=0;
			try {
		    //개선된 for loop
	        //for( int j=0; j<ovoList.size(); j++) {
	           // System.out.println(ovoList.get(j));
	            
		    
	            data=new StringBuilder();
			
				data
				.append("------------------------------------------------\n")
				.append("주문 목록\n")
				.append("\t현금(소득공제)\n")
				.append("역삼점(본점)\n")
				.append("대표: 1조 2011-11-11212\n")
				.append("------------------------------------------------\n")
				.append("주문 상품명 : \n")
				.append("------------------------------------------------\n");
				
				int cnt=0;
				for(int i=0; i<ovoList.size(); i++) {
				data.append(ovoList.get(i).getProductName()).append("(")
				.append(ovoList.get(i).getProductCode()).append(") \n");
				
					boolean flag = productCntMap.containsKey(ovoList.get(i).getProductName());
		            //System.out.println(ovoList.get(i).getProductName()+"가 존재 "+flag);
					if(flag) {
					cnt=productCntMap.get(ovoList.get(i).getProductName());
					data.append("수량 : ").append(cnt).append("개\n")
					.append("결제 금액 : ").append(Integer.parseInt(ovoList.get(i).getProductPrice())*cnt).append("원\n")
					.append("---------------------\n");
					}//endif
					totalPrice=(Integer.parseInt(ovoList.get(i).getProductPrice())*cnt)+totalPrice;
				}//end for
				
				data.append("------------------------------------------------\n");
				data.append("총 결제 금액 : ").append(totalPrice).append("원\n");
				data.append("------------------------------------------------\n")
				.append("자리번호 : ").append(seatNum).append("\n")
				.append("------------------------------------------------\n")
				.append("ip address : ").append(InetAddress.getLocalHost().getHostAddress()).append("\n")
				.append("------------------------------------------------\n")
				.append("위의 정보로 주문하시겠습니까?")
				.append("\n")
				.append("\n")
				.append("\n")
				.append("\n")
				.append("\n")
				.append("\n")
				.append("------------------------------------------------\n");
			 //}//end for
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}//end catch
			
			jtaReceipt.setText(data.toString());
			switch(JOptionPane.showConfirmDialog(puov, jspReceipt)) {
			case JOptionPane.OK_OPTION :
				try {
					//InsertOrder(List<PUOrderAddVO> list, int seatNum)
					List<PUOrderAddVO> list=null;
					PUOrderAddVO puoAdd=null;
					list=new ArrayList<PUOrderAddVO>();
					for(int i=0;i<ovoList.size(); i++) {
						
						puoAdd=new PUOrderAddVO(ovoList.get(i).getProductCode(), ovoList.get(i).getProductName(), ovoList.get(i).getProductPrice(), productCntMap.get(ovoList.get(i).getProductName()));
						list.add(puoAdd);
					//PUOrderAddVO puoadd=new PUOrderAddVO(
							/*puov.getJtfOrderName().getText(), puov.getJtfPhone().getText(), 
							InetAddress.getLocalHost().getHostAddress(), lunchCode, 
							puov.getJbQuan().getSelectedIndex()+1,puov.getjtaRequest().getText());*/
					
					}//end for
					puo_dao.InsertOrder(list,seatNum);//DB에 저장
					JOptionPane.showMessageDialog(puov, "주문이 완료 되었습니다.\n항상 최선을 다하는 1조PC방이 되겠습니다.\n감사합니다.");
					//주문이 완료되었으므로 주문창을 닫는다
					puov.dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(puov, "SQL문제 발생!!");
					e.printStackTrace();
				}//end catch
			}//end switch
			
		}//printOrder
	
	@Override public void mousePressed(MouseEvent e) {	}
	@Override public void mouseReleased(MouseEvent e) {	}
	@Override public void mouseEntered(MouseEvent e) {	}
	@Override public void mouseExited(MouseEvent e) { }
	
	/**
	 * 버튼 class
	 * @author owner
	 */
	@SuppressWarnings("serial")
	public class tableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		
		public tableCell() {
			jb.addActionListener(e -> {
				JTableRemoveRow();
			});
		}//tableCell
		
		@Override
		public Object getCellEditorValue() {
			return null;
		}//getCellEditorValue
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return jb;
		}//getCellEditorValue
		
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return jb;
		}//getCellEditorValue
	}//class
	
	/**
	 * 주문 목록 삭제
	 */
	public void JTableRemoveRow() {
		DefaultTableModel model=puov.getDtmOrderlist();
		JTable table=puov.getJtOrderlist();
		if(table.getSelectedRow()==-1) {
			return;
		}//end else
		//System.out.println(table.getValueAt(table.getSelectedRow(),0));
		for(int i=0;i<ovoList.size(); i++) {
			//System.out.println(ovoList.size());
			//System.out.println((String)table.getValueAt(table.getSelectedRow(), 0));
			if(ovoList.get(i).toString().contains((String)table.getValueAt(table.getSelectedRow(), 0))) {
				ovoList.remove(i);
				
				productCntMap.remove(table.getValueAt(table.getSelectedRow(), 0));
			}//end if
		}//end for
		try {
		model.removeRow(table.getSelectedRow());
		}catch (ArrayIndexOutOfBoundsException e) {
			//JOptionPane.showMessageDialog(puov, "인덱스 오류");
			//e.printStackTrace();
			model.removeRow(table.getSelectedRow());
		}//end catch
    } // end public void JTableRemoveRow()
}//class