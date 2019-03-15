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
		// 작성된 메세지를 가져와서
		String sendMsg = pumv.getJtfChat().getText().trim();

		if (!sendMsg.equals("")) {//공백이 입력되지 않았을 때
			try {
				pum_dao.chgMsgStatusNtoY(InetAddress.getLocalHost().getHostAddress());//DAO 사용해서 PC_Status의 Message_Status를 변경
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
			// 스트림에 기록하고
			pu_manager.getWriteStream().writeUTF("[message]" + sendMsg);
			// 스트림의 내용을 목적지로 분출
			pu_manager.getWriteStream().flush();
			// 작성된 메세지를 채팅창에 출력한다.
			pumv.getJtaChat().append("[나] : " + sendMsg + "\n");
			// T.F의 내용을 삭제한다.
			pumv.getJtfChat().setText("");
			pumv.getJspChat().getVerticalScrollBar().setValue(pumv.getJspChat().getVerticalScrollBar().getMaximum());
		}else {//공백이 입력됐을 때
			pumv.getJtfChat().setText("");// T.F의 내용을 삭제한다.
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