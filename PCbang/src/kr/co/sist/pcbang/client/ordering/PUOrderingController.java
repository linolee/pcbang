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
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableCell;

import kr.co.sist.pcbang.client.main.PUMainController;

public class PUOrderingController extends WindowAdapter implements MouseListener, ActionListener {

	private PUOrderingView puov;
	private PUOrderingDAO puo_dao;
	private PUMainController pumc;
	public static final int DBL_CLICK=2;
	private JButton jb;
    
	
	public PUOrderingController(PUOrderingView puov) {
		this.puov=puov;
		jb=new JButton("���");
		puo_dao=PUOrderingDAO.getInstance();

//		�����ִ� �κа� ����Ǵ� ��ġ�� ��Ȯ���� �ʾ� �̺κп��� Error
//		try {
//			String[] fileNames=orderImageList();//Ŭ���̾�Ʈ�� ���� �̹����� üũ�Ͽ�
//			orderImageSend(fileNames);//������ ���� ���� �̹����� ���� ��  
//		}catch (IOException e) {
//			e.printStackTrace();
//		}//end catch

		setProduct();//JTable�� ��ȸ ����
		setBestProduct();//bestMenu
		setProductCategory("���");
		setProductCategory("����");
		setProductCategory("����");
		
	}//PUOrderingController
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==puov.getJbtOk()) {
			JOptionPane.showMessageDialog(puov, "�� ������� �ֹ��մϴ�");
			printOrder();
		}//end if
		if(ae.getSource()==puov.getJbtExit()) {
			//JOptionPane.showMessageDialog(puov, "�ݱ�");
			puov.dispose();
		}//end if
	}//actionPerformed
	
	@Override
	public void mouseClicked(MouseEvent me) {	
		//JOptionPane.showMessageDialog(puov, "���콺 �̺�Ʈ�� ���Ƚ��ϴ�.");
		if(me.getSource()==puov.getJtMenu()) {//�޴�
			//JTable jt=puov.getJtMenu();
//			String columninfo=(String)jt.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn());
			//Object columninfo=jt.getValueAt(jt.getSelectedRow(), jt.getSelectedColumn());
			//String product_code=columninfo.substring(columninfo.indexOf("|"), columninfo.lastIndexOf("|")+1);
			//JOptionPane.showMessageDialog(puov, columninfo);
			//addOrderList(columninfo);
			
			switch(me.getClickCount()) {
			case DBL_CLICK :
				JTable jt=puov.getJtMenu();
				String product_name=(String)jt.getValueAt(jt.getSelectedRow(), 1);
				String product_price=(String)jt.getValueAt(jt.getSelectedRow(), 2);
				String columninfo=product_name+"\n"+product_price;
				addOrderList(columninfo);
			}//end switch
		}//end if
		if(me.getSource()==puov.getJtOrderlist()) {//�ֹ�
			JTable jtO=puov.getJtOrderlist();
			//Object column=jtO.getValueAt(jtO.getSelectedRow(), jtO.getSelectedColumn());
			if(me.getSource()==puov.getJtOrderlist().getColumnModel().getColumn(3)) {
				//JOptionPane.showMessageDialog(puov, "����");
				
			}else {
				//JOptionPane.showMessageDialog(puov, column);
			}//end else
		}//end if
		
		if(me.getSource()==puov.getjtbMenu()) {//��
			if(puov.getjtbMenu().getSelectedIndex()==1) {
				setProductCategory("���");
			}
			if(puov.getjtbMenu().getSelectedIndex()==2) {
				setProductCategory("����");
			}
			if(puov.getjtbMenu().getSelectedIndex()==3) {
				setProductCategory("����");
			}
		}//end if
		
		if(me.getSource()==puov.getJtBestProduct()) {//����Ʈ �޴�
			switch(me.getClickCount()) {
			case DBL_CLICK :
				JTable jt=puov.getJtBestProduct();
				Object p_code=jt.getValueAt(0, jt.getSelectedColumn());
				System.out.println(jt.getValueAt(0, jt.getSelectedColumn()));
				//���ö��� ������ ��ȸ
	//			System.out.println(lunch_code);
				try {
					PUOrderVO puovo=puo_dao.selectCode(p_code.toString());
					
					if(puovo==null) {//�ڵ�� ��ȸ�� ����� ���� ��
						JOptionPane.showMessageDialog(puov, p_code+"�� ��ȸ�� ���ö��� �����ϴ�.");
					}else {//�ڵ�� ��ȸ�� ����� ���� ��
						System.out.println(puovo);
						//��ǰ��+���� ���
						//addOrderList(columninfo);
					}//end else
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(puov, "������ ��ȸ�� ���� �߻�!");
					e.printStackTrace();
				}//end catch
			}//end switch
		}//end if
	}//mouseClicked
	
	/**
	 * ��ǰ �ҷ��� ���̺� ������
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
				//���̺� �߰��� ������ ����
				rowData=new Object[4];
				rowData[0]=new ImageIcon(f.getCanonicalPath()+"/s_"+puovo.getImg());
				rowData[1]=puovo.getProductName();
				rowData[2]=puovo.getProductPrice();
				rowData[3]=puovo.getProductCode();
				//������ �����͸� ���̺� �߰�
				dtmProduct.addRow(rowData);
			}//end for
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(puov, "��ǰ ����� ��ȸ�ϴ� �� ���� �߻�");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
	}//setProduct
	
	/**
	 * ī�װ��� ��ǰ �ҷ��� ������
	 */
	private void setProductCategory(String category) {
		try {
			
			File f = new File("C:/Users/owner/git/pcbang/PCbang/src/kr/co/sist/pcbang/manager/product/img");
			List<PUOrderVO> listProduct=puo_dao.selectProductCategory(category);
			DefaultTableModel dtmProduct=null;
			JTable jtC=null;
			if(category.equals("���")){
				dtmProduct=puov.getDtmRamen();
				jtC=puov.getJtRamen();
			}else if(category.equals("����")) {
				dtmProduct=puov.getDtmDrink();
				jtC=puov.getJtDrink();
			}else if(category.equals("����")) {
				dtmProduct=puov.getDtmSnack();
				jtC=puov.getJtSnack();
			}//end else
			dtmProduct.setRowCount(0);
			
			Object[] rowData=null;
			PUOrderVO puovo=null;	
			
			for(int i=0;i<listProduct.size(); i++) {
				puovo=listProduct.get(i);
				//���̺� �߰��� ������ ����
				rowData=new Object[4];
				rowData[0]=new ImageIcon(f.getCanonicalPath()+"/s_"+puovo.getImg());
				rowData[1]=puovo.getProductName();
				rowData[2]=puovo.getProductPrice();
				rowData[3]=puovo.getProductCode();
				//������ �����͸� ���̺� �߰�
				dtmProduct.addRow(rowData);
			}//end for
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(puov, "��ǰ ����� ��ȸ�ϴ� �� ���� �߻�");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
	}//setProduct
	
	/**
	 * ����Ʈ ��ǰ �ҷ��� ���̺� ������
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
					cntArr.add(i);//list�� ��´�
				}//end for
			}//end if
	
			int rowNum=(int)cnt/5+1;
			for(int x=0; x<rowNum; x++) {
				int i=0;
				if(x==0) {
					rowData=new Object[7];//5ī������ ������ 
					for(i=0; i<7; i++){
						//System.out.print(cntArr.get(i));
						puovo=listProduct.get(i);
						Object img=new ImageIcon(f.getCanonicalPath()+"/s_"+puovo.getImg());
						Object name=puovo.getProductName();
						Object price=puovo.getProductPrice();
						Object productCode=puovo.getProductCode();
						rowData[i]=img;
					}//end for
					dtmProduct.addRow(rowData);//dtm�� �־��ٲ���
				}//end else
			}//end for
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(puov, "��ǰ ����� ��ȸ�ϴ� �� ���� �߻�");
			e.printStackTrace();
		}//end catch
		catch (IOException e) {
			e.printStackTrace();
		}
	}//setBestProduct
	
	/**
	 * �ֹ� ���̺� �߰�
	 * @param list
	 * @return
	 */
	private boolean userOrdering(List<PUOrderVO> list) {
		boolean flag=false;
		
		
		return flag;
	}//userOrdering
	
	/**
	 * �ֹ� ��Ͽ� �߰�
	 */
	private void addOrderList(Object columninfo) {
		//����
		DefaultTableModel dtmOrder=puov.getDtmOrderlist();
		DefaultComboBoxModel<String> dcmCombo=new DefaultComboBoxModel<String>();
		JTable jtOrder=puov.getJtOrderlist();
		
		//�޺��ڽ� ����
		String[] quan = {"1","2","3","4","5","6","7","8","9","10"};
		JComboBox<String> combo=new JComboBox<String>(dcmCombo);
		combo.setMaximumRowCount(10);
		for(int i=0; i<quan.length; i++) {
			dcmCombo.addElement(quan[i]);
		}//end for

		TableColumn column=jtOrder.getColumnModel().getColumn(1);
		column.setCellEditor(new DefaultCellEditor(combo));
		
		//�޾ƿ� �����͸� �߶� �˸��� ĭ�� ���
		String dataArr=String.valueOf(columninfo);
		String[] data=dataArr.split("\n");

		Object[] rowData=null;
		rowData=new Object[4];
		rowData[0]=data[0];
		//rowData[1]="";
		//rowData[1]=column.setCellEditor(combo);
				/*new DefaultCellEditor(combo).getCellEditorValue();//1������*/
		rowData[2]=data[1];
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
	 * �ֹ� ��Ͽ��� ����
	 */
	private void removeOrderList() {
		
	}//removeOrderList
	
	
	/**
	 * ��ǰ �̹��� ����Ʈ
	 */
	private String[] orderImageList() {
		String[] fileList=null;
		String path="F:/dev/workspace/javase_teamprj2/src/img/";
		
		File dir=new File(path);
		//s_�� ���� ���ϸ� �迭��
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
	 * ������ �̹����� ������ ���� �̹����� �޴� ��.
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
				dos.writeUTF(fileNames[i]);//������ ����
			}//end for
			dos.flush();
			
			dis=new DataInputStream(socket.getInputStream());
			//������ �������� ������ ���� ����
			int fileCnt=dis.readInt();
			System.out.println("Ŭ���̾�Ʈ"+fileCnt+"�� ����");
			String fileName="";
			int fileSize=0;
			int fileLen=0;
			
			FileOutputStream fos=null;
			
			for(int i=0;i<fileCnt; i++) {
				
				//���޹��� ���� ������ ����
				fileSize=dis.readInt();

				//���� �� �ޱ�
				fileName=dis.readUTF();
				fos=new FileOutputStream("F:/dev/workspace/javase_teamprj2/src/img/"+fileName);
				
				byte[] readData=new byte[512];
				
				while(fileSize>0) {
					fileLen=dis.read(readData);//�������� ������ ���������� �о�鿩
					fos.write(readData, 0, fileLen);//������ ���Ϸ� ���
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
		 * �ֹ������� �����ְ�(������) �ֹ��� �� ������ ó��.
		 */
		private void printOrder() {
			JTextArea jtaReceipt=new JTextArea(26,22);
			jtaReceipt.setEditable(false);
			JScrollPane jspReceipt=new JScrollPane(jtaReceipt);
			
			StringBuilder data=new StringBuilder();
			
			try {
				data
				.append("------------------------------------------------\n")
				.append("�ֹ� ���\n")
				.append("\t����(�ҵ����)\n")
				.append("������(����)\n")
				.append("��ǥ: 1�� 2011-11-11212\n")
				.append("------------------------------------------------\n")
				.append("�ֹ� ��ǰ�� ").append("��ǰ��").append("(")
				.append("��ǰ�ڵ�").append(")\n")
				.append("------------------------------------------------\n")
				.append("���� : ").append("����")
				.append("��\n")
				.append("------------------------------------------------\n")
				.append("���� �ݾ� : ").append("����").append("��\n")
				.append("------------------------------------------------\n")
				.append("�ֹ��� �� : ").append("�̸�").append("\n")
				.append("------------------------------------------------\n")
				.append("ip address : ").append(InetAddress.getLocalHost().getHostAddress()).append("\n")
				.append("------------------------------------------------\n")
				.append("��û���� : ").append("�̷��� ������ ���ּ���").append("\n")
				.append("------------------------------------------------\n")
				.append("���� ������ �ֹ��Ͻðڽ��ϱ�?")
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
					
					//PUOrderingDAO.getInstance().insertOrder(puoadd);//DB�� ����
					JOptionPane.showMessageDialog(puov, "�ֹ��� �Ϸ� �Ǿ����ϴ�.\n�׻� �ּ��� ���ϴ� 1��PC���� �ǰڽ��ϴ�.\n�����մϴ�.");
					//�ֹ��� �Ϸ�Ǿ����Ƿ� �ֹ�â�� �ݴ´�
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
	 * ��ư class
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
	
	public void JTableRemoveRow() {
		DefaultTableModel model=puov.getDtmOrderlist();
		JTable table=puov.getJtOrderlist();
		if(table.getSelectedRow()==-1) {
			return;
		}else {
			model.removeRow(table.getSelectedRow());
		}//end else

    } // end public void JTableRemoveRow()
}//class