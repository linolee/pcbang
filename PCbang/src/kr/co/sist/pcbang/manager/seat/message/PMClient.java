package kr.co.sist.pcbang.manager.seat.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.manager.login.PCRoomManagerRun;
import kr.co.sist.pcbang.manager.order.PMOrderDAO;
import kr.co.sist.pcbang.manager.seat.PMSeatController;
import kr.co.sist.pcbang.manager.seat.PMSeatVO;

public class PMClient extends WindowAdapter implements Runnable, ActionListener {

	private PMMsgView mv;
	private Socket client;
	private DataInputStream dis;
	private DataOutputStream dos;
	private PMSeatController pmsc;
	private List<PMClient> clientSocketList;
	private Thread thread;
	private int seatNum;
	private PMMessageDAO pmm_dao;

	public PMClient(Socket client, PMSeatController pmsc) {
//		System.out.println("�޼���â����");
		mv = new PMMsgView(this);
		mv.setBounds(100, 100, 650, 350);
		mv.setVisible(false);// ù ���������� false�� ����
		mv.getJbtSendMsg().addActionListener(this);
		mv.getJbtSendClose().addActionListener(this);
		mv.getJbtOrderDone().addActionListener(this);
		mv.getJtfMsg().addActionListener(this);
		this.client = client;
		try {
			dis = new DataInputStream(client.getInputStream());
			dos = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.pmsc = pmsc;
		clientSocketList = pmsc.getClientSocket();
		thread = new Thread(this);
		thread.start();
		seatNum = setTitle();
		pmm_dao = PMMessageDAO.getInstance();

	}

	private Integer setTitle() {
		seatNum = 0;
		for (int i = 0; i < pmsc.getSeat().length; i++) {
			for (int j = 0; j < pmsc.getSeat()[i].length; j++) {
				if (client.getInetAddress().toString().equals("/" + pmsc.getSeat()[i][j].getPcIP())) {
					seatNum = pmsc.getSeat()[i][j].getSeatNum();
				}
			}
		}
		mv.setTitle(Integer.toString(seatNum) + "�� �¼�" + client.getInetAddress().toString());
		return seatNum;
	}

	public void run() {
		try {
			readStream();
		} catch (SocketException ie) {
			dropClient();
		} catch (IOException ie) {
			ie.printStackTrace();
		} // end catch
	}// run

	private void readStream() throws IOException, SocketException {
		String temp;
		String flag;
		while (true) {
			temp = dis.readUTF();
			flag = temp.substring(0, temp.indexOf("]") + 1);// [order]
			switch (flag) {
			case "[order]":// �ֹ��� �־��� ��
				pmsc.getPmsv().getPmov().getPmoc().setOrder();
				pmsc.getPmsv().getPmov().getPmoc().setOrderComplete();
			case "[login]":// ó�� �������� ��
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				break;
			case "[message]":// �޼��� ���� �������� ��
				if (mv.isVisible()) {// �޼���â�� ���� �̹� �����ִٸ�
					// Message_Status�� 'N'�� �ٲ۴�.
					try {
						pmsc.getPms_dao().chgMsgStatusYtoN(seatNum);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				mv.getJtaMsg()
						.setText(mv.getJtaMsg().getText() + "[���] : " + temp.substring(temp.indexOf("]") + 1) + "\n");
//				mv.setVisible(true);
//				mv.requestFocus();
				mv.getJspTalkDisplay().getVerticalScrollBar()
						.setValue(mv.getJspTalkDisplay().getVerticalScrollBar().getMaximum());
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				break;
			case "[close]":// ���� �¼��� �α׾ƿ� �ؾ��� ��
				closeOrder(Integer.parseInt(temp.substring(temp.indexOf("]") + 1)));
				break;
			case "[logout]":// ����ڰ� ������ ��
				dropClient();
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				break;
			case "[update time]":
				updateOrder(temp.substring(temp.indexOf("]") + 1));
				break;
			case "[fileList]":// Ŭ���̾�Ʈ���� ���ϸ���Ʈ�� ����
				// ���ϸ���Ʈ ���ڿ��� �迭�� �ɰ���
				List<String> clientFileList = recieveClientFileList(temp.substring(temp.indexOf("]") + 2));
				// ������ �ִ� ���� ���ڿ��� ��
				for (int i = 0; i < PCRoomManagerRun.PrdImgList.size(); i++) {
					System.out.println(PCRoomManagerRun.PrdImgList.get(i)+"�ִ�����");
					if (clientFileList.contains(PCRoomManagerRun.PrdImgList.get(i))) {
						clientFileList.remove("s_" + PCRoomManagerRun.PrdImgList.get(i));
					}
				}
				
				for (Iterator iterator = clientFileList.iterator(); iterator.hasNext();) {
					System.out.println((String) iterator.next());
					System.out.println("��������");
//					fileSend(fname, dos);
				}
				// Ŭ���̾�Ʈ�� ���� ���� ����Ʈ�� �����ش�.
				break;
//			default:
//				System.out.println("�� �� ���� ����");
//				break;
			}//end switch
		}//end while
	}

	private void fileSend(String fname, DataOutputStream dos) throws IOException {

		FileInputStream fis = null;

		try {
			int fileData = 0;

			String path = System.getProperty("user.dir");
			String filepath = "\\src\\kr\\co\\sist\\pcbang\\manager\\product\\img\\";
			String abspath = path + filepath;

			fis = new FileInputStream(abspath + fname);
			byte[] readData = new byte[512];

			int fileLen = 0;
			while ((fileLen = fis.read(readData)) != -1) {
				fileData++;
			} // end while

			fis.close();
			dos.writeInt(fileData);
			dos.flush();

			dos.writeUTF(fname);// writeUTF

			fis = new FileInputStream(abspath + fname);
			while ((fileLen = fis.read(readData)) != -1) {// �������� ������ ���������� �о�鿩
				dos.write(readData, 0, fileLen);// ������ ���Ϸ� ���
			} // end while
			dos.flush();
			System.out.println("��� "+	dis.readUTF());
		} finally {
			if (fis != null) {
				fis.close();
			} // end if
		} // end finally

	}// fileSend
	
	private List<String> recieveClientFileList(String fileList) {
		List<String> clientFileList = new ArrayList<>();
		String[] clientFile = fileList.split("/");
		for (int i = 0; i < clientFile.length; i++) {
			clientFileList.add(clientFile[i]);
		}
		return clientFileList;
	}

	private void writeStream(String info) throws IOException {
		dos.writeUTF(info);
		dos.flush();
	}

	private void sendMsg(String msg) throws IOException {
		if (!msg.equals("")) {// ������ �ƴ� ���
			mv.getJtaMsg().append("[������] : " + msg.substring(msg.indexOf("]") + 1) + "\n");
			mv.getJtfMsg().setText("");
			msg = "[message]" + msg;
			writeStream(msg);
			mv.getJspTalkDisplay().getVerticalScrollBar()
					.setValue(mv.getJspTalkDisplay().getVerticalScrollBar().getMaximum());
		} else {// ������ �Էµ� ���
			mv.getJtfMsg().setText("");
		}
	}

	private void closeOrder(int seatNum) throws IOException {// �ش� pc�� �������� ������ �޼ҵ�
		String ipAddr = "";

		PMSeatVO[][] seat = pmsc.getSeat();// ���� �¼� �߿���
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (seatNum == seat[i][j].getSeatNum()) {// ���� �¼���ȣ�� ������ �¼������� ã�Ƽ�
					ipAddr = seat[i][j].getPcIP();// ip�� �����ϰ�
				}
			}
		}

		for (Iterator<PMClient> iterator = clientSocketList.iterator(); iterator.hasNext();) {// clientSocketList����
			PMClient pmClient = (PMClient) iterator.next();
			if (pmClient.getClient().getInetAddress().toString().substring(1).equals(ipAddr)) {// �ش� ip�� ã�Ƽ�
				pmClient.getDos().writeUTF("[logout]");// �α׾ƿ� ����� ������.
				pmClient.getDos().flush();
			}
		}
	}

	private void updateOrder(String id) throws IOException {// �ش� pc�� �������� ������ �޼ҵ�
		int seatNum = 0;

		PMSeatVO[][] seat = pmsc.getSeat();// ���� �¼� �߿���
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (id == seat[i][j].getUser()) {// ���� �¼���ȣ�� ������ �¼������� ã�Ƽ�
					seatNum = seat[i][j].getSeatNum();// ip�� �����ϰ�
				}
			}
		}

		if (seatNum != 0) {
			for (Iterator<PMClient> iterator = clientSocketList.iterator(); iterator.hasNext();) {// clientSocketList����
				PMClient pmClient = (PMClient) iterator.next();
				if (pmClient.getSeatNum() == seatNum) {// �ش� ip�� ã�Ƽ�
					pmClient.getDos().writeUTF("[update time]");// �α׾ƿ� ����� ������.
					pmClient.getDos().flush();
				} // end if
			} // end for
		} // end if
	}

	private void dropClient() {
//		System.out.println("Ŭ���̾�Ʈ ��������");
		try {
			if (dis != null) {
				dis.close();
			}
			if (dos != null) {
				dos.close();
			}
			if (client != null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		clientSocketList.remove(this);
//		for (int i = 0; i < clientSocketList.size(); i++) {
//			System.out.println(clientSocketList.get(i));
//		}
		mv.dispose();
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mv.getJbtSendMsg() || ae.getSource() == mv.getJtfMsg()) {// ����Ű�� �޼��� ������ ��ư�� �������� ��
			try {
				sendMsg(mv.getJtfMsg().getText().trim());
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(mv, "������ ����Ǿ� �޼����� ������ �� �����ϴ�");
				ie.printStackTrace();
			}
		}
		if (ae.getSource() == mv.getJbtSendClose()) {// ���� ���� ��ư�� �������� ��
			if (JOptionPane.showConfirmDialog(mv, "�ش� ����ڸ� ���� �α׾ƿ� ��Ű�ðڽ��ϱ�?", "���",
					JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
				try {
					writeStream("[logout]");
				} catch (IOException ie) {
					JOptionPane.showMessageDialog(mv, "������ ����Ǿ� �޼����� ������ �� �����ϴ�");
					ie.printStackTrace();
				}
			}
		}
		if (ae.getSource() == mv.getJbtOrderDone()) {// �ֹ� �Ϸ� ��ư�� �������� ��
			if (JOptionPane.showConfirmDialog(mv, "�ش� ������� �ֹ��� ��� �Ϸ�Ǿ����ϱ�? �ش� ������� ��� �ֹ��� �Ϸ�ó���˴ϴ�!", "Ȯ��",
					JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
				try {
					orderDone(seatNum);// �¼���ȣ�� �ش��ϴ� ��� �ֹ��� STATUS�� 'Y'�� �����ϰ� �¼���ȣ�� �ش��ϴ� PC_STATUS�� ORDER_STATUS�� 'N'����
										// ����
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			pmsc.seatLoad();
			pmsc.setBtnSeat();
		}
	}

	private int orderDone(int seatNum) throws SQLException {
		int cnt = pmm_dao.orderDone(seatNum);// DB�ڷḦ �����ϰ�
		pmsc.getPmsv().getPmov().getPmoc().setOrder();
		pmsc.getPmsv().getPmov().getPmoc().setOrderComplete();// �ֹ����̺��� ����
		return cnt;
	}

	public Socket getClient() {
		return client;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public PMMsgView getMv() {
		return mv;
	}

	public PMSeatController getPmsc() {
		return pmsc;
	}

	public List<PMClient> getClientSocketList() {
		return clientSocketList;
	}

	public Thread getThread() {
		return thread;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		mv.setVisible(false);// ����Ǵ� ��� visible�� false�� �ٲ�
	}

	@Override
	public String toString() {
		return "PMClient [mv=" + mv + ", client=" + client + ", dis=" + dis + ", dos=" + dos + ", clientSocketList="
				+ clientSocketList + ", thread=" + thread + "]";
	}

	public int getSeatNum() {
		return seatNum;
	}

	public PMMessageDAO getPmm_dao() {
		return pmm_dao;
	}

}
