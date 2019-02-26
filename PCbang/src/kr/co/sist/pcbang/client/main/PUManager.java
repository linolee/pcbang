package kr.co.sist.pcbang.client.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.message.PUMessageController;
import kr.co.sist.pcbang.client.message.PUMessageView;

public class PUManager extends Thread {

	private DataInputStream readStream;
	private DataOutputStream writeStream;
	private Socket client;
	private PUMessageView pumsgv;

	public PUManager(PUMainController pumc) {
		try {
			connectToServer();// �Ŵ����� �����ϰ�
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// �޼���â�� ����
		pumsgv = new PUMessageView();// �� ����
		PUMessageController pumsgc = new PUMessageController(pumsgv, pumc, this);// ��Ʈ�ѷ� ����
		pumsgv.setVisible(false);

		// �信 �׼Ǹ����� �ޱ�
		pumsgv.getJtfChat().addActionListener(pumsgc);
		pumsgv.addWindowListener(pumsgc);
//		pumsgv.getJbtSend().addActionListener(pumsgc);

		this.start();
	}

	public void connectToServer() throws UnknownHostException, IOException {
		try {
			if (client == null) {
				String serverIp = "211.63.89.152"; // ���� ���� IP
				client = new Socket(serverIp, 55000); // �Է��� ip address�� ��ǻ�Ϳ� ����.

				readStream = new DataInputStream(client.getInputStream());
				writeStream = new DataOutputStream(client.getOutputStream());
//				writeStream.writeUTF("[message]:fffffffff");
//				writeStream.flush();
			} else {
				JOptionPane.showMessageDialog(null, "error code #9980");
			}

		} catch (ConnectException ce) {
			JOptionPane.showMessageDialog(null, "������ ������ �� �����ϴ�");
		}
	}

	public void run() {
		try {
			readStream();
		} catch (SocketException ie) {
			ie.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} // end catch
	}// run

	private void readStream() throws IOException, SocketException {
		String temp;
		String flag;
		while (true) {
			temp = readStream.readUTF();
			flag = temp.substring(0, temp.indexOf("]") + 1);// [order]
			switch (flag) {
			case "[message]":// �޼��� ���� �������� ��
				System.out.println("�޽��� ����");
				temp = pumsgv.getJtfChat().getText()+"[������] : "+temp.substring(temp.indexOf("]") + 1)+"\n";
				pumsgv.getJtaChat().setText(temp);
				pumsgv.setVisible(true);
				pumsgv.requestFocus();
				break;
			case "[close]":// ���� �¼��� �α׾ƿ� �ؾ��� ��
				// closeOrder(Integer.parseInt(temp.substring(temp.indexOf("]") + 1)));
				break;

			default:
				System.out.println("�� �� ���� ����");
				break;
			}
		}
	}
	
	

	public DataInputStream getReadStream() {
		return readStream;
	}

	public DataOutputStream getWriteStream() {
		return writeStream;
	}

	public Socket getClient() {
		return client;
	}

	public PUMessageView getPumsgv() {
		return pumsgv;
	}

}
