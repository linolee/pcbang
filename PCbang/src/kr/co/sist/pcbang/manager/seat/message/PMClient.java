package kr.co.sist.pcbang.manager.seat.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PMClient extends WindowAdapter implements Runnable, ActionListener {

	private PMMsgView mv;
	private Socket client;
	private DataInputStream dis;
	private DataOutputStream dos;
	private List<PMClient> clientSocketList;
	private Thread thread;
	
	public PMClient(Socket client, DataInputStream dis, DataOutputStream dos, List<PMClient> clientSocketList) {
		mv = new PMMsgView(this);
		//actionListener �޾��ֱ�
		this.client = client;
		this.dis = dis;
		this.dos = dos;
		this.clientSocketList = clientSocketList;
		thread = new Thread(this);
		thread.start();
	}

//	@Override
//	public void run() {
//		try {
//			readStream();
//			dos.writeUTF("Ȯ��");
//			dos.flush();
//		} catch (IOException e) {
//			System.out.println("Ŭ���̾�Ʈ ����");
//			dropClient();
//		}
//	} // run

	public void run() {
		if (dis != null) {
			try {
				String revMsg = "";
				while (true) { // �������� �������� ��� �޼����� �о, ��� �����ڿ� �Ѹ���.
					revMsg = dis.readUTF();
				} // end while
			} catch (IOException ie) {
				// �����ڰ� ����ϸ� �ش� �����ڸ� ����Ʈ���� �����Ѵ�.
				clientSocketList.remove(this);
				// �޼����� �о������ ���ϴ� ���¶�� �����ڰ� ������ ������ ����
				ie.printStackTrace();
			} // end catch
		} // end if
	}// run

	private void readStream() throws IOException {
		while (true) {
			System.out.println(dis.readUTF());
		}
	}

	private void sendMsg() throws IOException {
		if (dos != null) {
			JTextField jtf = mv.getJtfMsg();
			String msg = jtf.getText().trim();

			if (!msg.equals("")) {
				dos.writeUTF(msg);
				dos.flush();
			}
			jtf.setText("");
		}
	}

	private void dropClient() {
		clientSocketList.remove(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mv.getJbtSendMsg()) {
			try {
				sendMsg();
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(mv, "������ ����Ǿ� �޼����� ������ �� �����ϴ�");
				ie.printStackTrace();
			}
		}
	}

}
