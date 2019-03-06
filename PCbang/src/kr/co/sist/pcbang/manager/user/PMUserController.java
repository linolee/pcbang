package kr.co.sist.pcbang.manager.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.pcbang.manager.user.detail.PMUserDetailVO;
import kr.co.sist.pcbang.manager.user.detail.PMUserDetailView;

public class PMUserController implements ActionListener, MouseListener {

	private static PMUserController uc;
	private static PMUserView uv;
	private PMUserDAO u_dao;
	
	@SuppressWarnings("static-access")
	public PMUserController(PMUserView uv) {
		this.uv = uv;
		u_dao = PMUserDAO.getInstance();
	}

	public static PMUserController getInstance() {
		if(uc==null) {
			uc=new PMUserController(uv);
		}
		return uc;
	}
	
	public void selectUser() {
		DefaultTableModel dtmUser = uv.getDtmMember();
		dtmUser.setRowCount(0);
		
		try {
			
			String id = uv.getJtfId().getText().trim();
			String name = uv.getJtfName().getText().trim();
			
			List<PMUserVO> listUser = u_dao.selectUserData(id,name);
			
			PMUserVO usvo = null;
			
			Object[] rowData = null;
			
			for(int i=0; i<listUser.size();i++) {
				usvo=listUser.get(i);
				
				rowData = new Object[12];
				rowData[0] = new Integer(i+1);
				rowData[1] = usvo.getId();
				rowData[2] = usvo.getName();
				rowData[3] = usvo.getBirth();
				rowData[4] = usvo.getGender();
				rowData[5] = usvo.getTel();
				rowData[6] = usvo.getEmail();
				rowData[7] = usvo.getDetailAddress();
				rowData[8] = usvo.getMileage();
				rowData[9] = usvo.getLeftTime();
				rowData[10] = usvo.getTotalPrice();
				rowData[11] = usvo.getInputDate().substring(0, 10);
				dtmUser.addRow(rowData);
			}
			
			if(listUser.isEmpty()) {
				JOptionPane.showMessageDialog(uv, "조회된 값이 없습니다");
			}
		} catch(SQLException se) {
			JOptionPane.showMessageDialog(uv,  "아이디 혹은 이름을 입력해주세요");
			// se.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		// 조회버튼 클릭
		if(ae.getSource() == uv.getJbtnSearch()) {
			String id = uv.getJtfId().getText().trim();
			String name = uv.getJtfName().getText().trim();
			// 아이디, 이름이 빈칸이면
			if("".equals(id)&&"".equals(name)) {
				// 내용입력 메세지창 출력
				JOptionPane.showMessageDialog(uv, "아이디 혹은 이름을 입력해주세요");
			} else {
			// 유저 조회
			selectUser();
			}
		}
		
		// 초기화버튼 클릭
		if(ae.getSource() == uv.getJbtnReset()) {
			// jtf를 빈칸으로 만든 후 조회하여 전체 회원목록을 조회
			uv.getJtfId().setText("");
			uv.getJtfName().setText("");
			selectUser();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
		switch(me.getClickCount()) {
		
		case 2 : 
			
			if(me.getSource() == uv.getJtMember()) {
				
				JTable jt = uv.getJtMember();
				
				String name="";
				String id="";
				String gender="";
				String inputDate="";
				String email="";
				String tel="";
				String birth="";
				int leftTime=0;
				
				name = ((String)jt.getValueAt(jt.getSelectedRow(), 1));
				id = ((String)jt.getValueAt(jt.getSelectedRow(), 2));
				birth = ((String)jt.getValueAt(jt.getSelectedRow(), 3));
				gender = ((String)jt.getValueAt(jt.getSelectedRow(), 4));
				tel = ((String)jt.getValueAt(jt.getSelectedRow(), 5));
				email = ((String)jt.getValueAt(jt.getSelectedRow(), 6));
				leftTime = ((Integer)jt.getValueAt(jt.getSelectedRow(), 9));
				inputDate = ((String)jt.getValueAt(jt.getSelectedRow(), 11));
				
				PMUserDetailVO udvo = new PMUserDetailVO(id, name, gender, inputDate, email, tel, birth, leftTime);
				new PMUserDetailView(udvo);
				
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
