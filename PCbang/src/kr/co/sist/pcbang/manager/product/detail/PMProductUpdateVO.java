package kr.co.sist.pcbang.manager.product.detail;

public class PMProductUpdateVO {
	private String menuCode, menuName, img, category;
	private int price;
	
	public PMProductUpdateVO(String menuCode, String menuName, String img, String category, int price) {
		this.menuCode = menuCode;
		this.menuName = menuName;
		this.img = img;
		this.category = category;
		this.price = price;
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

	public String getCategory() {
		return category;
	}

	public int getPrice() {
		return price;
	}
	
}//class
