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
					System.out.println("서버가동");
					client = server.accept();
					System.out.println("접속자 있음");

					dis = new DataInputStream(client.getInputStream());

					cnt = dis.readInt();// 클라이언트가 보내오는 파일명의 갯수
					fileNames = new String[cnt];

					for (int i = 0; i < cnt; i++) {
						fileNames[i] = dis.readUTF();
						System.out.println(i + "번째 파일" + fileNames[i]);
					} // end for

					// 서버에 존재하는 파일명을 배열로 복사
					serverFileNames = new String[PCRoomManagerRun.PrdImgList.size()];
					PCRoomManagerRun.PrdImgList.toArray(serverFileNames);

					System.out.println("서버 " + Arrays.toString(serverFileNames));
					System.out.println("클라이언트 " + Arrays.toString(fileNames));

					// 클라이언트가 보내온 파일명과 서버의 파일명을 비교하여
					// 클라이언트가 없는 파일명을 출력
					for (String tName : PCRoomManagerRun.PrdImgList) {
						tempFileList.add(tName);
						tempFileList.add("s_" + tName);
					} // end for

					System.out.println("temp리스트" + tempFileList);

					for (String rmName : fileNames) {
						tempFileList.remove(rmName);
						tempFileList.remove("s_" + rmName);

					} // end for
					System.out.println("제거 후 temp리스트" + tempFileList);

					dos = new DataOutputStream(client.getOutputStream());
					dos.writeInt(tempFileList.size());// 전송할 파일의 갯수를 보낸다.

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
			JOptionPane.showMessageDialog(null, "파일 보내기 실패");
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
			while ((fileLen = fis.read(readData)) != -1) {// 서버에서 전송한 파일조각을 읽어들여
				dos.write(readData, 0, fileLen);// 생성한 파일로 기록
			} // end while
			dos.flush();
			System.out.println("결과 "+	dis.readUTF());
		} finally {
			if (fis != null) {
				fis.close();
			} // end if
		} // end finally

	}// fileSend

	public static void main(String[] args) {
		new FileServer().start(); // 인스턴스 +실행
	}// main
}// class
