package kr.co.sist.pcbang.manager.seat.detail;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PMSeatDetailController extends WindowAdapter{

	private PMSeatDetailView pmsdv;
	
	public PMSeatDetailController(PMSeatDetailView pmsdv) {
		this.pmsdv = pmsdv;
	}
	
	@Override
	public void windowDeactivated(WindowEvent e) {
		pmsdv.dispose();
	}
	
}
