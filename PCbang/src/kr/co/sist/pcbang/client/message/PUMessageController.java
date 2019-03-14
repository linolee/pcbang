package kr.co.sist.pcbang.client.message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import kr.co.sist.pcbang.client.main.PUMainController;
import kr.co.sist.pcbang.client.main.PUManager;

public class PUMessageController extends WindowAdapter implements ActionListener {
	private PUMessageView pumv;
	private PUMainController pumc;
	private PUManager pu_manager;
	private PUMessageDAO pum_dao;

	public PUMessageController(PUMessageView pumv, PUMainController pumc, PUManager pu_manager) {
		this.pumv = pumv;
		this.pumc = pumc;
		this.pu_manager = pu_manager;
		pum_dao = PUMessageDAO.getInstance();
	}//PUMessageController

	@Override
	public void windowClosing(WindowEvent we) {
		pumv.setVisible(false);
	}//windowClosing

	public void sendMsg() throws IOException {
		// �ۼ��� �޼����� �����ͼ�
		String sendMsg = pumv.getJtfChat().getText().trim();

		if (!sendMsg.equals("")) {//������ �Էµ��� �ʾ��� ��
			try {
				pum_dao.chgMsgStatusNtoY(InetAddress.getLocalHost().getHostAddress());//DAO ����ؼ� PC_Status�� Message_Status�� ����
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
			// ��Ʈ���� ����ϰ�
			pu_manager.getWriteStream().writeUTF("[message]" + sendMsg);
			// ��Ʈ���� ������ �������� ����
			pu_manager.getWriteStream().flush();
			// �ۼ��� �޼����� ä��â�� ����Ѵ�.
			pumv.getJtaChat().append("[��] : " + sendMsg + "\n");
			// T.F�� ������ �����Ѵ�.
			pumv.getJtfChat().setText("");
			pumv.getJspChat().getVerticalScrollBar().setValue(pumv.getJspChat().getVerticalScrollBar().getMaximum());
		}else {//������ �Էµ��� ��
			pumv.getJtfChat().setText("");// T.F�� ������ �����Ѵ�.
		}//end else
	}// sendMsg

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			sendMsg();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}//end catch
	}// actionPerformed
}// class