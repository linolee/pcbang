package kr.co.sist.pcbang.manager.seat.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

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

	public PMClient(Socket client, PMSeatController pmsc) {
//		System.out.println("�޼���â����");
		mv = new PMMsgView(this);
		mv.setBounds(100, 100, 650, 350);
		mv.setVisible(false);//ù ���������� false�� ����
		mv.getJbtSendMsg().addActionListener(this);
		mv.getJbtSendClose().addActionListener(this);
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
		
	}

	private Integer setTitle() {
		seatNum = 0;
		for (int i = 0; i < pmsc.getSeat().length; i++) {
			for (int j = 0; j < pmsc.getSeat()[i].length; j++) {
				if (client.getInetAddress().toString().equals("/"+pmsc.getSeat()[i][j].getPcIP())) {
					seatNum = pmsc.getSeat()[i][j].getSeatNum();
				}
			}
		}
		mv.setTitle(Integer.toString(seatNum)+"�� �¼�"+client.getInetAddress().toString());
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
				if (mv.isVisible()) {//�޼���â�� ���� �̹� �����ִٸ�
					//Message_Status�� 'N'�� �ٲ۴�.
					try {
						pmsc.getPms_dao().chgMsgStatusYtoN(seatNum);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				mv.getJtaMsg().setText(mv.getJtaMsg().getText()+"[���] : "+temp.substring(temp.indexOf("]") + 1)+"\n");
//				mv.setVisible(true);
//				mv.requestFocus();
				mv.getJspTalkDisplay().getVerticalScrollBar().setValue(mv.getJspTalkDisplay().getVerticalScrollBar().getMaximum());
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				break;
			case "[close]":// ���� �¼��� �α׾ƿ� �ؾ��� ��
				mv.getJtaMsg().setText(temp+"\n");
				closeOrder(Integer.parseInt(temp.substring(temp.indexOf("]") + 1)));
				mv.setVisible(true);
				break;
			case "[logout]":// ����ڰ� ������ ��
				mv.getJtaMsg().setText(temp+"\n");
				dropClient();
				pmsc.seatLoad();
				pmsc.setBtnSeat();
				mv.setVisible(true);
				break;
			case "[update time]":
				mv.getJtaMsg().setText(temp+"\n");
				String msg="[update time]";
				writeStream(msg);
				mv.setVisible(true);
				break;
//			default:
//				System.out.println("�� �� ���� ����");
//				break;
			}
		}
	}

	private void writeStream(String info) throws IOException {
		dos.writeUTF(info);
		dos.flush();
	}

	private void sendMsg(String msg) throws IOException {
		if (!msg.equals("")) {//������ �ƴ� ���
			mv.getJtaMsg().append("[������] : "+msg.substring(msg.indexOf("]") + 1)+"\n");
			mv.getJtfMsg().setText("");
			msg = "[message]" + msg;
			writeStream(msg);
			mv.getJspTalkDisplay().getVerticalScrollBar().setValue(mv.getJspTalkDisplay().getVerticalScrollBar().getMaximum());
		}else {//������ �Էµ� ���
			mv.getJtfMsg().setText("");
		}
	}

	private void closeOrder(int seatNum) throws IOException {//�ش� pc�� �������� ������ �޼ҵ�
		String ipAddr = "";
		
		PMSeatVO[][] seat = pmsc.getSeat();//���� �¼� �߿���
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (seatNum == seat[i][j].getSeatNum()) {//���� �¼���ȣ�� ������ �¼������� ã�Ƽ�
					ipAddr = seat[i][j].getPcIP();//ip�� �����ϰ�
				}
			}
		}
		
		for (Iterator<PMClient> iterator = clientSocketList.iterator(); iterator.hasNext();) {//clientSocketList����
			PMClient pmClient = (PMClient) iterator.next();
			if (pmClient.getClient().getInetAddress().toString().substring(1).equals(ipAddr)) {//�ش� ip�� ã�Ƽ�
				pmClient.getDos().writeUTF("[logout]");//�α׾ƿ� ����� ������.
				pmClient.getDos().flush();
			}
		}
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
		if (ae.getSource() == mv.getJbtSendMsg() || ae.getSource() == mv.getJtfMsg()) {//����Ű�� �޼��� ������ ��ư�� �������� ��
			try {
				sendMsg(mv.getJtfMsg().getText().trim());
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(mv, "������ ����Ǿ� �޼����� ������ �� �����ϴ�");
				ie.printStackTrace();
			}
		}
		if (ae.getSource() == mv.getJbtSendClose()) {//���� ���� ��ư�� �������� ��
			if (JOptionPane.showConfirmDialog(mv, "�ش� ����ڸ� ���� �α׾ƿ� ��Ű�ðڽ��ϱ�?", "���", JOptionPane.WARNING_MESSAGE)==JOptionPane.OK_OPTION) {
				try {
					writeStream("[logout]");
				} catch (IOException ie) {
					JOptionPane.showMessageDialog(mv, "������ ����Ǿ� �޼����� ������ �� �����ϴ�");
					ie.printStackTrace();
				}
			}
		}
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
		mv.setVisible(false);//����Ǵ� ��� visible�� false�� �ٲ�
	}
	
	
	@Override
	public String toString() {
		return "PMClient [mv=" + mv + ", client=" + client + ", dis=" + dis + ", dos=" + dos + ", clientSocketList="
				+ clientSocketList + ", thread=" + thread + "]";
	}

}
