package kr.co.sist.pcbang.manager.order;

public class PMOrderVO {
	String orderNum, menuCode, menuName, orderDate;
	int seatNum, quan, menuPrice, totalPrice;
	public PMOrderVO(String orderNum, String menuCode, String menuName, String orderDate, int seatNum, int quan,
			int menuPrice, int totalPrice) {
		super();
		this.orderNum = orderNum;
		this.menuCode = menuCode;
		this.menuName = menuName;
		this.orderDate = orderDate;
		this.seatNum = seatNum;
		this.quan = quan;
		this.menuPrice = menuPrice;
		this.totalPrice = totalPrice;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public int getSeatNum() {
		return seatNum;
	}
	public int getQuan() {
		return quan;
	}
	public int getMenuPrice() {
		return menuPrice;
	}
	public int getTotalPrice() {
		return totalPrice;
	}

	
}
