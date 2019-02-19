package kr.co.sist.pcbang.manager.product;

public class PMProductVO {
	private String menuCode, menuName, img;
	private int quan, price, total;
	
	public PMProductVO(String menuCode, String menuName, String img, int quan, int price, int total) {
		this.menuCode = menuCode;
		this.menuName = menuName;
		this.img = img;
		this.quan = quan;
		this.price = price;
		this.total=total;
	}
	
	public String getMenuCode() {
		return menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public String getImg() {
		return img;
	}
	public int getQuan() {
		return quan;
	}
	public int getPrice() {
		return price;
	}
	public int getTotal() {
		return total;
	}
	
}//class
