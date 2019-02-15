package kr.co.sist.pcbang.manager.product.add;

public class PMProductAddVO {
	private String menuName, img, category;
	private int price;
	
	public PMProductAddVO( String menuName, String img, String category, int price) {
		this.menuName = menuName;
		this.img = img;
		this.category=category;
		this.price = price;
	}

	public String getMenuName() {
		return menuName;
	}
	
	public String getCategory() {
		return category;
	}

	public String getImg() {
		return img;
	}

	public int getPrice() {
		return price;
	}
	
}//class
