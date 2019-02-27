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
import java.util.List;

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
	
	public PUOrderingController(PUOrderingView puov) {
		this.puov=puov;
		puo_dao=PUOrderingDAO.getInstance();

//		보내주는 부분과 저장되는 위치가 정확하지 않아 이부분에서 Error
//		try {
//			String[] fileNames=orderImageList();//클라이언트가 가진 이미지를 체크하여
//			orderImageSend(fileNames);//서버로 보내 없는 이미지를 받은 후  
//		}catch (IOException e) {
//			e.printStackTrace();
//		}//end catch

		setProduct();//JTable을 조회 갱신
	}//PUOrderingController
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==puov.getJbtOk()) {
			JOptionPane.showMessageDialog(puov, "이 목록으로 주문합니다");
			printOrder();
		}//end if
		if(ae.getSource()==puov.getJbtExit()) {
			//JOptionPane.showMessageDialog(puov, "닫기");
			puov.dispose();
		}//end if
	}//actionPerformed
	
	@Override
	public void mouseClicked(MouseEvent me) {	
		//JOptionPane.showMessageDialog(puov, "마우스 이벤트가 눌렸습니다.");
		if(me.getSource()==puov.getJtMenu()) {//메뉴
			JTable jt=puov.getJtMenu();
//			String columninfo=(String)jt.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn());
			Object columninfo=jt.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn());
			//String product_code=columninfo.substring(columninfo.indexOf("|"), columninfo.lastIndexOf("|")+1);
			
			JOptionPane.showMessageDialog(puov, columninfo);
			addOrderList(columninfo);
		}//end if
	}//mouseClicked
	
	/**
	 * 상품 불러와 테이블에 앉히기
	 */
	private void setProduct() {
		try {
			List<PUOrderVO> listProduct=puo_dao.selectProduct();
			
			DefaultTableModel dtmProduct=puov.getDtmMenu();
			dtmProduct.setRowCount(0);
					
			Object[] rowData=null;
			PUOrderVO puovo=null;
			int cnt=listProduct.size();
			List<Integer> cntArr=null;
			if(cnt!=0) {
				cntArr=new ArrayList<Integer>();
				for(int i=0; i<cnt; i++) {
					cntArr.add(i);//list에 담는다
				}//end for
			}//end if
	
			int rowNum=(int)cnt/5+1;
			for(int x=0; x<rowNum; x++) {
				int i=0;
				int max=5;
				if(x==0) {
					rowData=new Object[5];//5카ㄴ으로 나누어 
					for(i=0; i<5; i++){
						//System.out.print(cntArr.get(i));
						puovo=listProduct.get(i);
						Object img=new ImageIcon(puovo.getImg());
						Object name=puovo.getProductName();
						Object price=puovo.getProductPrice();
						Object productCode=puovo.getProductCode();
						rowData[i]=img+"\n"+name+"\n"+price+"\n"+productCode;//한칸에 이미지+가격+이름
					}//end for
					dtmProduct.addRow(rowData);//dtm에 넣어줄꺼야
				}else if(x>0) {
					max+=(5*x);
					rowData=new Object[5];//5카ㄴ으로 나누어 
					if(max>cnt) {
						max=cnt;
					}//end if
					int row=0;
					for(i+=(5*x); i<max; i++) {
						//System.out.print(cntArr.get(i));
						puovo=listProduct.get(i);
						//System.out.println(puovo);
						Object img=new ImageIcon(puovo.getImg());
						Object name=puovo.getProductName();
						Object price=puovo.getProductPrice();
						Object productCode=puovo.getProductCode();
						row=i-(5*x);
						rowData[row]=img+"\n"+name+"\n"+price+"\n"+productCode;//한칸에 이미지+가격+이름
					}//end for
					dtmProduct.addRow(rowData);//dtm에 넣어줄꺼야
				}//end else
			}//end for
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(puov, "상품 목록을 조회하는 중 문제 발생");
			e.printStackTrace();
		}//end catch
	}//setProduct
	
	/**
	 * 베스트 상품 불러와 테이블에 앉히기
	 */
	private void setBestProduct() {
		
	}//setBestProduct
	
	/**
	 * 주문 테이블에 추가
	 * @param list
	 * @return
	 */
	private boolean userOrdering(List<PUOrderVO> list) {
		boolean flag=false;
		
		
		return flag;
	}//userOrdering
	
	/**
	 * 주문 목록에 추가
	 */
	private void addOrderList(Object columninfo) {
		//선언
		DefaultTableModel dtmOrder=puov.getDtmOrderlist();
		DefaultComboBoxModel<String> dcmCombo=new DefaultComboBoxModel<String>();
		JTable jtOrder=puov.getJtOrderlist();
		
		//주문 목록을 저장할 list
		//PUOrderAddVO puoadd=new
		//List orderList=new ArrayList<>()
		
		//콤보박스 생성
		String[] quan = {"1","2","3","4","5","6","7","8","9","10"};
		JComboBox<String> combo=new JComboBox<String>(dcmCombo);
		combo.setMaximumRowCount(10);
		for(int i=0; i<quan.length; i++) {
			dcmCombo.addElement(quan[i]);
		}//end for

		TableColumn column=jtOrder.getColumnModel().getColumn(1);
		column.setCellEditor(new DefaultCellEditor(combo));
		
		//받아온 데이터를 잘라 알맞은 칸에 출력
		String dataArr=String.valueOf(columninfo);
		String[] data=dataArr.split("\n");

		Object[] rowData=null;
		rowData=new Object[4];
		rowData[0]=data[1];
		//rowData[1]="";
		//rowData[1]=column.setCellEditor(combo);
				/*new DefaultCellEditor(combo).getCellEditorValue();//1개나옴*/
		rowData[2]=data[2];
		//rowData[3]=data[3];
		dtmOrder.addRow(rowData);
		
		jtOrder.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(combo));
		jtOrder.getColumnModel().getColumn(3).setCellRenderer(new tableCell());
		jtOrder.getColumnModel().getColumn(3).setCellEditor(new tableCell());
//		if(combo.getSelectedIndex()!=-1) {
//			System.out.println(combo.getSelectedIndex());
//		}
	}//addOrderList
	
	/**
	 * 주문 목록에서 삭제
	 */
	private void removeOrderList() {
		
	}//removeOrderList
	
	
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
		
		System.out.println(Arrays.toString(fileList));
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
		 * 주문사항을 보여주고(영수증) 주문을 할 것인지 처리.
		 */
		private void printOrder() {
			JTextArea jtaReceipt=new JTextArea(26,22);
			jtaReceipt.setEditable(false);
			JScrollPane jspReceipt=new JScrollPane(jtaReceipt);
			
			StringBuilder data=new StringBuilder();
			
			try {
				data
				.append("------------------------------------------------\n")
				.append("주문 목록\n")
				.append("\t현금(소득공제)\n")
				.append("역삼점(본점)\n")
				.append("대표: 1조 2011-11-11212\n")
				.append("------------------------------------------------\n")
				.append("주문 상품명 ").append("상품명").append("(")
				.append("상품코드").append(")\n")
				.append("------------------------------------------------\n")
				.append("수량 : ").append("개수")
				.append("개\n")
				.append("------------------------------------------------\n")
				.append("결제 금액 : ").append("가격").append("원\n")
				.append("------------------------------------------------\n")
				.append("주문자 명 : ").append("이름").append("\n")
				.append("------------------------------------------------\n")
				.append("ip address : ").append(InetAddress.getLocalHost().getHostAddress()).append("\n")
				.append("------------------------------------------------\n")
				.append("요청사항 : ").append("이렇게 저렇게 해주세요").append("\n")
				.append("------------------------------------------------\n")
				.append("위의 정보로 주문하시겠습니까?")
				.append("\n")
				.append("\n")
				.append("\n")
				.append("\n")
				.append("\n")
				.append("\n")
				.append("------------------------------------------------\n");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}//end catch
			
			jtaReceipt.setText(data.toString());
			switch(JOptionPane.showConfirmDialog(puov, jspReceipt)) {
			case JOptionPane.OK_OPTION :
				//try {
					//PUOrderAddVO puoadd=new PUOrderAddVO(
							/*puov.getJtfOrderName().getText(), puov.getJtfPhone().getText(), 
							InetAddress.getLocalHost().getHostAddress(), lunchCode, 
							puov.getJbQuan().getSelectedIndex()+1,puov.getjtaRequest().getText());*/
					
					//PUOrderingDAO.getInstance().insertOrder(puoadd);//DB에 저장
					JOptionPane.showMessageDialog(puov, "주문이 완료 되었습니다.\n항상 최선을 다하는 1조PC방이 되겠습니다.\n감사합니다.");
					//주문이 완료되었으므로 주문창을 닫는다
					puov.dispose();
				/*} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (SQLException e) {*/
					//e.printStackTrace();
				//}//end catch
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
				//JTableRemoveRow();
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
}//class