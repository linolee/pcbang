package kr.co.sist.pcbang.manager.product.detail;

public class PMProductDetailVO {
	private String category, menuName, img;
	private int price;
	
	public PMProductDetailVO(String category, String menuName, String img, int price) {
		this.category = category;
		this.menuName = menuName;
		this.img = img;
		this.price = price;
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
