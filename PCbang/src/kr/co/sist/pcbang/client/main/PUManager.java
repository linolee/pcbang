package kr.co.sist.pcbang.client.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class PUManager extends Thread {
	
	private DataInputStream readStram;
	private DataOutputStream writeStream;
	private Socket client;
	
	public PUManager() {
		try {
			connectToServer();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		} catch(ConnectException ce) {
			JOptionPane.showMessageDialog(null, "������ ������ �� �����ϴ�");
		}
}
	
}
