package kr.co.sist.pcbang.manager.product;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import kr.co.sist.pcbang.manager.login.PCRoomManagerRun;

public class FileServer extends Thread {
	DataInputStream dis = null;
	@Override
	public void run() {
		ServerSocket server = null;

		try {
			try {
				server = new ServerSocket(19700);
				Socket client = null;
				int cnt = 0;
				String[] fileNames = null;
				String[] serverFileNames = null;

				List<String> tempFileList = new ArrayList<String>();
				DataOutputStream dos = null;

				while (true) {
					System.out.println("��������");
					client = server.accept();
					System.out.println("������ ����");

					dis = new DataInputStream(client.getInputStream());

					cnt = dis.readInt();// Ŭ���̾�Ʈ�� �������� ���ϸ��� ����
					fileNames = new String[cnt];

					for (int i = 0; i < cnt; i++) {
						fileNames[i] = dis.readUTF();
						System.out.println(i + "��° ����" + fileNames[i]);
					} // end for

					// ������ �����ϴ� ���ϸ��� �迭�� ����
					serverFileNames = new String[PCRoomManagerRun.PrdImgList.size()];
					PCRoomManagerRun.PrdImgList.toArray(serverFileNames);

					System.out.println("���� " + Arrays.toString(serverFileNames));
					System.out.println("Ŭ���̾�Ʈ " + Arrays.toString(fileNames));

					// Ŭ���̾�Ʈ�� ������ ���ϸ�� ������ ���ϸ��� ���Ͽ�
					// Ŭ���̾�Ʈ�� ���� ���ϸ��� ���
					for (String tName : PCRoomManagerRun.PrdImgList) {
						tempFileList.add(tName);
						tempFileList.add("s_" + tName);
					} // end for

					System.out.println("temp����Ʈ" + tempFileList);

					for (String rmName : fileNames) {
						tempFileList.remove(rmName);
						tempFileList.remove("s_" + rmName);

					} // end for
					System.out.println("���� �� temp����Ʈ" + tempFileList);

					dos = new DataOutputStream(client.getOutputStream());
					dos.writeInt(tempFileList.size());// ������ ������ ������ ������.

					for (String fName : tempFileList) {
						fileSend(fName, dos);
				
//						try {
//							Thread.sleep(1000);
//						} catch (InterruptedException ie) {
//							ie.printStackTrace();
//						} // end catch

					} // end for
//					dos.flush();
				} // end while

			} finally {
				if (server != null) {
					server.close();
				} // end if
			} // end finally
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(null, "���� ������ ����");
			ie.printStackTrace();
		} // end catch
		
	}// run

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

	public static void main(String[] args) {
		new FileServer().start(); // �ν��Ͻ� +����
	}// main
}// class
