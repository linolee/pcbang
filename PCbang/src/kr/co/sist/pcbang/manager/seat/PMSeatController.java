package kr.co.sist.pcbang.manager.seat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;

import kr.co.sist.pcbang.manager.seat.message.PMClient;
import kr.co.sist.pcbang.manager.seat.set.PMSeatSetView;


public class PMSeatController extends WindowAdapter implements Runnable, ActionListener{
	private PMSeatView pmsv;
	private Socket serverSocket;
	private List<PMClient> clientSocket;
	private PMSeatDAO pms_dao;
	private PMSeatVO[][] seat;
		
	public PMSeatController(PMSeatView pmsv) {
		//DAO연결
		this.pmsv = pmsv;
		pms_dao = PMSeatDAO.getInstance();
		setServer();
		seatLoad();
		setBtnSeat();
	}//constructor
	
	private void setServer() {
		
	}
	
	private void setBtnSeat() {
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				pmsv.getBtnSeat()[i][j].setText(seat[i][j].getSeatNum().toString());
				if (seat[i][j].getSeatNum() == 0) {//좌석 번호가 0이라면 검은색
					pmsv.getBtnSeat()[i][j].setBackground(Color.BLACK);
				}else if (!seat[i][j].getUser().equals("")) {//유저가 컴퓨터에 있다면 초록색
					pmsv.getBtnSeat()[i][j].setBackground(Color.GREEN);
				}else {//없다면 회색
					pmsv.getBtnSeat()[i][j].setBackground(Color.GRAY);
				}
				
//				if (seat[i][j].getPcStatus().equals("")) {
//					
//				}
				/////////////////////////////상태에 따라 색 추가//////////////////////////////////////////
			}//end inner for
		}//end outer for
	}
	
	private void seatLoad() {
		try {
			seat = pms_dao.selectSeatInfo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pmsv.getBtnSet()) {
			openSeatSet();
		}
		
		
		for (int i = 0; i < seat.length; i++) {
			for (int j = 0; j < seat[i].length; j++) {
				if (e.getSource() == pmsv.getBtnSeat()[i][j]) {
					System.out.println(i+","+j+"버튼 클릭");
				}
			}
		}
		
		
	}
	private void openSeatSet() {
		new PMSeatSetView();
	}
	private void openPopBtn() {
		
	}
	private void openSeatDetail() {
		
	}
	private void visibleMsg() {
		
	}
	
	
	public void run() {
		
	}
	
	private void recieveClient() {
		
	}
	private void dropClient() {
		
	}
	private void readSeatInfo() {
		
	}
	
}
