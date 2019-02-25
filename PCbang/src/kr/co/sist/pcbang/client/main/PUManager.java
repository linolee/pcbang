package kr.co.sist.pcbang.client.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.client.message.PUMessageController;
import kr.co.sist.pcbang.client.message.PUMessageView;

public class PUManager {

	private DataInputStream readStram;
	private DataOutputStream writeStream;
	private Socket client;

	public PUManager(PUMainController pumc) {
		try {
			connectToServer();//�Ŵ����� �����ϰ�
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//�޼���â�� ����
		PUMessageView pumsgv = new PUMessageView();//�� ����
		PUMessageController pumsgc = new PUMessageController(pumsgv, pumc, this);//��Ʈ�ѷ� ����
		
		//�信 �׼Ǹ����� �ޱ�
		pumsgv.getJtfChat().addActionListener(pumsgc);
//		pumsgv.getJbtSend().addActionListener(pumsgc);
		
	}

	public void connectToServer() throws UnknownHostException, IOException {
		try {
			if (client == null) {
				String serverIp = "211.63.89.152"; // ���� ���� IP
				client = new Socket(serverIp, 55000); // �Է��� ip address�� ��ǻ�Ϳ� ����.

				readStram = new DataInputStream(client.getInputStream());
				writeStream = new DataOutputStream(client.getOutputStream());
				writeStream.writeUTF("[message]:fffffffff");
				writeStream.flush();
			} else {
				JOptionPane.showMessageDialog(null, "error code #9980");
			}

		} catch (ConnectException ce) {
			JOptionPane.showMessageDialog(null, "������ ������ �� �����ϴ�");
		}
	}

	public DataInputStream getReadStram() {
		return readStram;
	}

	public DataOutputStream getWriteStream() {
		return writeStream;
	}

	public Socket getClient() {
		return client;
	}
	
}
