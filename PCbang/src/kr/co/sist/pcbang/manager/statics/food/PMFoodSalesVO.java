package kr.co.sist.pcbang.manager.statics.food;

public class PMFoodSalesVO {
	String menuCode, category, menuName, img;
	int  menuPrice, quans ,total;
	public PMFoodSalesVO(String menuCode, String category, String menuName, String img, int menuPrice, int quans,
			int total) {
		this.menuCode = menuCode;
		this.category = category;
		this.menuName = menuName;
		this.img = img;
		this.menuPrice = menuPrice;
		this.quans = quans;
		this.total = total;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public String getCategory() {
		return category;
	}
	public String getMenuName() {
		return menuName;
	}
	public String getImg() {
		return img;
	}
	public int getMenuPrice() {
		return menuPrice;
	}
	public int getQuans() {
		return quans;
	}
	public int getTotal() {
		return total;
	}
	
	
	
}
