package kr.co.sist.pcbang.manager.product.detail;

public class PMProductDetailVO {
	private String code, category, menuName, img;
	private int price;
	
	public PMProductDetailVO(String code, String category, String menuName, String img, int price) {
		this.code=code;
		this.category = category;
		this.menuName = menuName;
		this.img = img;
		this.price = price;
	}

	public String getcode() {
		return code;
	}
	
	public String getcategory() {
		return category;
	}

	public String getMenuName() {
		return menuName;
	}

	public String getImg() {
		return img;
	}

	public int getPrice() {
		return price;
	}
	
}//class
