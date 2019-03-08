package kr.co.sist.pcbang.client.ordering;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import kr.co.sist.pcbang.client.main.PUMainController;
import kr.co.sist.pcbang.client.main.PUManager;

public class PUOrderingController extends WindowAdapter implements MouseListener, ActionListener {

	private PUOrderingView puov;
	private PUOrderingDAO puo_dao;
	private PUMainController pumc;
	private JButton jb;
	private List<PUOrderVO> ovoList;
	private Map<String, Integer> productCntMap;
	private Map<String, Integer> productPriceMap;
	private int seatNum;
	private PUManager pu_manager;
    
	public static final int DBL_CLICK=2;
	
	public PUOrderingController(PUOrderingView puov,PUMainController pumc, int seatNum) {
		this.puov=puov;
		this.pumc=pumc;
		jb=new JButton("���");
		ovoList=new ArrayList<PUOrderVO>();
		puo_dao=PUOrderingDAO.getInstance();
		productCntMap=new HashMap<String,Integer>();
		productPriceMap=new HashMap<String,Integer>();
		this.seatNum=seatNum;
		pu_manager = pumc.getPu_manager();
		
		try {
			String[] fileNames=orderImageList();//Ŭ���̾�Ʈ�� ���� �̹����� üũ�Ͽ�
			orderImageSend(fileNames);//������ ���� ���� �̹����� ���� ��  
		}catch (IOException e) {
			e.printStackTrace();
		}//end catch

		setProduct();//JTable�� ��ȸ ����
		setBestProduct();//bestMenu
		setProductCategory("���");
		setProductCategory("����");
		setProductCategory("����");
	
	}//PUOrderingController
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==puov.getJbtOk()) {//�ֹ�!
			if(ovoList.size()==0) {
				JOptionPane.showMessageDialog(puov, "��ǰ�� �߰����ּ���^^");
				return;
			}else {
				JOptionPane.showMessageDialog(puov, "�� ������� �ֹ��մϴ�");
				printOrder();
			}//end else
		}//end if
		if(ae.getSource()==puov.getJbtExit()) {
			puov.dispose();
		}//end if
		if(ae.getSource()==puov.getJbtDel()) {//��ǰ ����
			if(ovoList.size()==0) {
				JOptionPane.showMessageDialog(puov, "������ �׸��� �����ϴ�.");
			}else {
				JTableRemoveRow();
			}//end else
		}//end if
	}//actionPerformed
	
//	private void orderCancle() {
//		int select = -1;
//		JTable table=puov.getJtOrderlist();
//		select = JOptionPane.showConfirmDialog(puov, orderNum+"�� �ֹ��� ����Ͻðڽ��ϱ�?");
//		if(select == JOptionPane.OK_OPTION) {
//			for(int i=0;i<ovoList.size(); i++) {
//				//System.out.println(ovoList.size());
//				//System.out.println((String)table.getValueAt(table.getSelectedRow(), 0));
//				if(ovoList.get(i).toString().contains((String)table.getValueAt(table.getSelectedRow(), 0))) {
//					JOptionPane.showMessageDialog(puov, orderNum+"�� �ֹ��� ��ҵǾ����ϴ� !");
//					ovoList.remove(i);
//
//					productCntMap.remove(table.getValueAt(table.getSelectedRow(), 0));
//				}//end if
//			}//end for
//			
//			//setOrder();
//			//setOrderComplete();
//			//flagOrder = false;
//		}//end if
//	}
	
	@Override
	public void mouseClicked(MouseEvent me) {	
		if(me.getSource()==puov.getJtMenu()) {//�޴�
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
						JOptionPane.showMessageDialog(puov, "�̹� �߰��� ��ǰ�Դϴ�.");
						return;
					}//end else
				}//end for
				addOrderList(columninfo);
				ovoList.add(puovo);
				productCntMap.put((String) jt.getValueAt(jt.getSelectedRow(), 1), 1);
				productPriceMap.put((String) jt.getValueAt(jt.getSelectedRow(), 1), Integer.parseInt((String) jt.getValueAt(jt.getSelectedRow(), 2)));
				//�Ѱ���..
				setTotalPrice();
			}//end switch
		}//end if
		if(me.getSource()==puov.getJtOrderlist()) {//�ֹ�
//			DefaultTableModel dtm=puov.getDtmOrderlist();
//			JTable jtO=puov.getJtOrderlist();
//			Object column=jtO.getValueAt(jtO.getSelectedRow(), jtO.getSelectedColumn());
//			if(me.getSource()==puov.getJtOrderlist().getColumnModel().getColumn(1)) {
//				//JOptionPane.showMessageDialog(puov, "����");
//			}else {
//				//JOptionPane.showMessageDialog(puov, column+"�Դϴ�.");
//				 jtO.setColumnExt(0).setEditable(false);
//				//dtm.isCellEditable(jtO.getSelectedRow(), jtO.getSelectedColumn());
//			}//end else
			//JTable jt = puov.getJtOrderlist();
			//System.out.println(jt.getSelectedRow());
			//orderNum = (String)jt.getValueAt(jt.getSelectedRow(), 0);
		}//end if
		
		if(me.getSource()==puov.getjtbMenu()) {//��
			if(puov.getjtbMenu().getSelectedIndex()==1) {
				setProductCategory("���");
			}//end if
			if(puov.getjtbMenu().getSelectedIndex()==2) {
				setProductCategory("����");
			}//end if
			if(puov.getjtbMenu().getSelectedIndex()==3) {
				setProductCategory("����");
			}//end if
		}//end if
		
		if(me.getSource()==puov.getJtRamen()||me.getSource()==puov.getJtSnack()||me.getSource()==puov.getJtDrink()) {//�� ī�װ����� �̺�Ʈ
			JTable jtC=(JTable) me.getSource();
			switch(me.getClickCount()) {
			case DBL_CLICK :
				String productName=(String)jtC.getValueAt(jtC.getSelectedRow(), 1);
				String productPrice=(String)jtC.getValueAt(jtC.getSelectedRow(), 2);
				String columninfo=productName+"\n"+productPrice;
				PUOrderVO puovo=new PUOrderVO((String)jtC.getValueAt(jtC.getSelectedRow(), 0).toString(), (String)jtC.getValueAt(jtC.getSelectedRow(), 3), productName, productPrice);
				for(int i=0;i<ovoList.size(); i++) {
		            //System.out.println(ovoList.get(i));
					if(ovoList.get(i).toString().contains((String)jtC.getValueAt(jtC.getSelectedRow(), 3))) {
						JOptionPane.showMessageDialog(puov, "�̹� �߰��� ��ǰ�Դϴ�.");
						return;
					}//end else
				}//end for
				addOrderList(columninfo);
				ovoList.add(puovo);
				productCntMap.put((String) jtC.getValueAt(jtC.getSelectedRow(), 1), 1);
				productPriceMap.put((String) jtC.getValueAt(jtC.getSelectedRow(), 1), Integer.parseInt((String) jtC.getValueAt(jtC.getSelectedRow(), 2)));
				//�Ѱ���..

			}//end switch
		}//end if

		if(me.getSource()==puov.getJtBestProduct()) {//����Ʈ �޴�
			switch(me.getClickCount()) {
			case DBL_CLICK ://
				JTable jtO=puov.getJtBestProduct();
				Object imgpath=jtO.getValueAt(0, jtO.getSelectedColumn());
				//System.out.println(jt.getValueAt(0, jt.getSelectedColumn()));
				try {
					String img=imgpath.toString().substring(imgpath.toString().indexOf("/")+3);
					
					PUOrderVO puovo=puo_dao.selectCode(img.toString());
					String columninfo=puovo.getProductName()+"\n"+puovo.getProductPrice();
					for(int i=0;i<ovoList.size(); i++) {
						//System.out.println(ovoList.get(i).getProductCode()+"/"+puovo.getProductCode());
						if(ovoList.get(i).getProductCode().equals(puovo.getProductCode())) {
							JOptionPane.showMessageDialog(puov, "�̹� �߰��� ��ǰ�Դϴ�.");
							return;
						}//end else
					}//end for
					addOrderList(columninfo);
					ovoList.add(puovo);
					productCntMap.put(puovo.getProductName(), 1);
					productPriceMap.put(puovo.getProductName(), Integer.parseInt(puovo.getProductPrice()));
					//�Ѱ���..

				} catch (SQLException e) {
					JOptionPane.showMessageDialog(puov, "������ ��ȸ�� ���� �߻�!");
					e.printStackTrace();
				}//end catch
			}//end switch
		}//end if
		
		if(me.getSource()==puov.getJtOrderlist()) {//�ֹ����
//			JTable jtO=puov.getJtOrderlist();
//			Object column=null;
//			try {
//			column=jtO.getValueAt(jtO.getSelectedRow(), jtO.getSelectedColumn());
//			
//			if(jtO.getValueAt(jtO.getSelectedRow(), 1)==column) {
//				//JOptionPane.showMessageDialog(puov, "����");
//				//column.addItemListener
//				
//			}else {
//			//JOptionPane.showMessageDialog(puov, column);
//			}//end if
//
//			}catch(ArrayIndexOutOfBoundsException aiobe) {
//				//JOptionPane.showMessageDialog(puov, "�ֹ���Ͽ��� Ŭ���ε��� ����");//�����ϸ� ��..
//				//aiobe.printStackTrace();
//			}
		}//end if
	}//mouseClicked
	
	/**
	 * ��ǰ �ҷ��� ���̺� ������
	 */
	private void setProduct() {
		try {
			List<PUOrderVO> listProduct=puo_dao.selectProduct();
			String path1=System.getProperty("user.dir");
			String path2="/src/kr/co/sist/pcbang/manager/product/img";
			File f = new File(path1+path2);
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
			String path1=System.getProperty("user.dir");
			String path2="/src/kr/co/sist/pcbang/manager/product/img";
			File f = new File(path1+path2);
			//File f = new File("C:/Users/owner/git/pcbang/PCbang/src/kr/co/sist/pcbang/manager/product/img");
			List<PUOrderVO> listProduct=puo_dao.selectProductCategory(category);
			DefaultTableModel dtmProduct=null;
			//JTable jtC=null;
			if(category.equals("���")){
				dtmProduct=puov.getDtmRamen();
				//jtC=puov.getJtRamen();
			}else if(category.equals("����")) {
				dtmProduct=puov.getDtmDrink();
				//jtC=puov.getJtDrink();
			}else if(category.equals("����")) {
				dtmProduct=puov.getDtmSnack();
				//jtC=puov.getJtSnack();
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
			String path1=System.getProperty("user.dir");
			String path2="/src/kr/co/sist/pcbang/manager/product/img";
			File f = new File(path1+path2);
			//File f = new File("C:/Users/owner/git/pcbang/PCbang/src/kr/co/sist/pcbang/manager/product/img");
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
					rowData=new Object[7];//5ĭ���� ������ 
					for(i=0; i<7; i++){
						//System.out.print(cntArr.get(i));
						puovo=listProduct.get(i);
						Object img=new ImageIcon(f.getCanonicalPath()+"/s_"+puovo.getImg());
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
	 * �ֹ� ��Ͽ� �߰�
	 */
	private void addOrderList(Object columninfo) {
		//����
		DefaultTableModel dtmOrder=puov.getDtmOrderlist();
		DefaultComboBoxModel<String> dcmCombo=new DefaultComboBoxModel<String>();
		
		//�޺��ڽ� ����
		String[] quan = {"1","2","3","4","5","6","7","8","9","10"};
		JComboBox<String> combo=new JComboBox<String>(dcmCombo);
		//combo.setSelectedIndex(0);
		combo.setMaximumRowCount(10);
		for(int i=0; i<quan.length; i++) {
			dcmCombo.setSelectedItem(1);
			dcmCombo.addElement(quan[i]);
		}//end for
		
		//�޺���ư(����) �̺�Ʈ
		JTable jtOrder=puov.getJtOrderlist();
		combo.addItemListener(e -> {
			try {
			String str=e.getItemSelectable().toString();
			//System.out.println(productPriceMap.get(jtOrder.getValueAt(jtOrder.getSelectedRow(), 0)));
			int price=productPriceMap.get(jtOrder.getValueAt(jtOrder.getSelectedRow(), 0));
			int cnt=0;
			if(str.substring(str.indexOf("selectedItemReminder")+21).contains("10")) {
				cnt=Integer.parseInt(str.substring(str.indexOf("selectedItemReminder")+21,str.indexOf("selectedItemReminder")+23));
			}else {
				cnt=Integer.parseInt(str.substring(str.indexOf("selectedItemReminder")+21,str.indexOf("selectedItemReminder")+22));
			}
			//System.out.println((String) jtOrder.getValueAt(jtOrder.getSelectedRow(), 0).toString());
			productCntMap.put((String) jtOrder.getValueAt(jtOrder.getSelectedRow(), 0).toString(), cnt);
			//System.out.println(cnt+"/"+price);
			
			jtOrder.setValueAt(cnt*price, jtOrder.getSelectedRow(), 2);
			//�Ѱ���..
			setTotalPrice();
			cnt=0;
			e.getItemSelectable().addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					//System.out.println("�������� �ٲ�����ϴ�"+e.getItem());
				}
			});
			}catch (ArrayIndexOutOfBoundsException aio) {
				aio.printStackTrace();
				//JOptionPane.showMessageDialog(puov, "�ε��� ����");
			}
		});

		//�޺� ��ġ
		TableColumn column=jtOrder.getColumnModel().getColumn(1);
		column.setCellEditor(new DefaultCellEditor(combo));
		
		//�޾ƿ� �����͸� �߶� �˸��� ĭ�� ���
		String dataArr=String.valueOf(columninfo);
		String[] data=dataArr.split("\n");
		Object[] rowData=null;
		rowData=new Object[4];
		rowData[0]=data[0];
		rowData[1]=1;
		rowData[2]=data[1];
		dtmOrder.addRow(rowData);
		
		jtOrder.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(combo));
	
	}//addOrderList
	
	/**
	 * ��ǰ �̹��� ����Ʈ
	 */
	private String[] orderImageList() {
		String[] fileList=null;
		String path1=System.getProperty("user.dir");
		String path2="/src/kr/co/sist/pcbang/manager/product/img/";
		//String path="F:/dev/workspace/javase_teamprj2/src/img/";
		
		File dir=new File(path1+path2);
		//s_�� ���� ���ϸ� �迭��
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
	 * ������ �̹����� ������ ���� �̹����� �޴� ��.
	 * @param fileName
	 */
	private void orderImageSend(String[] fileNames) throws IOException{
		Socket socket=null;
		DataOutputStream dos=null;
		DataInputStream dis=null;
		String path1=System.getProperty("user.dir");
		String path2="/src/kr/co/sist/pcbang/manager/product/img/";
		
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
			//System.out.println("Ŭ���̾�Ʈ"+fileCnt+"�� ����");
			String fileName="";
			int fileSize=0;
			int fileLen=0;
			
			FileOutputStream fos=null;
			
			for(int i=0;i<fileCnt; i++) {
				
				//���޹��� ���� ������ ����
				fileSize=dis.readInt();

				//���� �� �ޱ�
				fileName=dis.readUTF();
				fos=new FileOutputStream(path1+path2+fileName);
				
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
	 * �ֹ������� �����ְ�(������) �ֹ����̺� ����
	 */
	private void printOrder() {
		JTextArea jtaReceipt=new JTextArea(26,22);
		jtaReceipt.setEditable(false);
		JScrollPane jspReceipt=new JScrollPane(jtaReceipt);
		
		StringBuilder data=null;
		int totalPrice=0;
		int[] cnt=new int[ovoList.size()];
		try {
            data=new StringBuilder();
			data
			.append("----------------------------------------------------\n")
			.append("                   �ֹ� ���\n")
			.append("\t        ����(�ҵ����)\n")
			.append("������(����)\n")
			.append("��ǥ: 1�� 2011-11-11212\n")
			.append("----------------------------------------------------\n")
			.append("                   �ֹ� ��ǰ�� \n")
			.append("----------------------------------------------------\n");
			
			boolean flag=false;
			for(int i=0; i<ovoList.size(); i++) {
				data.append(ovoList.get(i).getProductName()).append("(")
				.append(ovoList.get(i).getProductCode()).append(") \n");
			
				flag = productCntMap.containsKey(ovoList.get(i).getProductName());//���� map�� Ű�� ���� �����ϸ�
	            //System.out.println(ovoList.get(i).getProductName()+"�� ���� "+flag);
				if(flag) {
					cnt[i]=productCntMap.get(ovoList.get(i).getProductName());
					data.append("���� : ").append(cnt[i]).append("��\n")
					.append("���� �ݾ� : ").append(Integer.parseInt(ovoList.get(i).getProductPrice())*cnt[i]).append("��\n")
					.append("-------------------------\n");
				}//end if
				totalPrice=(Integer.parseInt(ovoList.get(i).getProductPrice())*cnt[i])+totalPrice;
			}//end for
			
			data.append("�� ���� �ݾ� : ").append(totalPrice).append("��\n");
			data.append("----------------------------------------------------\n")
			.append("�ڸ���ȣ : ").append(seatNum).append("��\n")
			.append("----------------------------------------------------\n")
			.append("ip address : ").append(InetAddress.getLocalHost().getHostAddress()).append("\n")
			.append("----------------------------------------------------\n")
			.append("���� ������ �ֹ��Ͻðڽ��ϱ�?")
			.append("\n")
			.append("\n")
			.append("\n")
			.append("----------------------------------------------------\n");
		 //}//end for
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}//end catch
		
		jtaReceipt.setText(data.toString());//���
		
		switch(JOptionPane.showConfirmDialog(puov, jspReceipt)) {
		case JOptionPane.OK_OPTION :
			try {
				List<PUOrderAddVO> list=null;
				PUOrderAddVO puoAdd=null;
				list=new ArrayList<PUOrderAddVO>();
				for(int i=0;i<ovoList.size(); i++) {
					puoAdd=new PUOrderAddVO(ovoList.get(i).getProductCode(), ovoList.get(i).getProductName(), ovoList.get(i).getProductPrice(), cnt[i]);
					list.add(puoAdd);
				}//end for
				puo_dao.InsertOrder(list,seatNum);//DB�� ����
				puo_dao.UpdatePcStatus(seatNum);//update
				JOptionPane.showMessageDialog(puov, "�ֹ��� �Ϸ� �Ǿ����ϴ�.\n�׻� �ּ��� ���ϴ� 1��PC���� �ǰڽ��ϴ�.\n�����մϴ�.");
				
				//�ֹ� �޼��� ������(?)
				pu_manager.getWriteStream().writeUTF("[order]");
				pu_manager.getWriteStream().flush();
				
				//�ֹ��� �Ϸ�Ǿ����Ƿ� �ֹ�â�� �ݴ´�
				puov.dispose();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(puov, "SQL���� �߻�!!");
				e.printStackTrace();
			}//end catch
			catch (IOException e) {
				JOptionPane.showMessageDialog(puov, "������Ű��� �����߻�!!");
				e.printStackTrace();
			}
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
	
	/**
	 * �ֹ� ��� ����
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
			setTotalPrice();
		}catch (ArrayIndexOutOfBoundsException e) {
			//JOptionPane.showMessageDialog(puov, "�ε��� ����");
			e.printStackTrace();
			//model.removeRow(table.getSelectedRow());
		}//end catch
    } // end public void JTableRemoveRow()
	
	/**
	 * �ֹ�â�� �� ���� ����
	 */
	public void setTotalPrice() {
		int total=0;
		JLabel jl=puov.getJlProductPrice();

		JTable jtO=puov.getJtOrderlist();
		if(ovoList.size()>0) {
			for(int i=0; i<ovoList.size(); i++) {
				System.out.println(jtO.getValueAt(i,2));
				int price=Integer.parseInt((String) (jtO.getValueAt(i,2)).toString());
				total=total+price;
			}
		}//end if
		
		jl.setText(String.valueOf(total)+" ��");
	}//setTotalPrice
	
}//class