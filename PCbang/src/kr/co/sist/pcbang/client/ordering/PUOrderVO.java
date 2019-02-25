package kr.co.sist.pcbang.client.ordering;

public class PUOrderVO {
	private String img,productCode,productName,productPrice;

	public PUOrderVO(String img, String productCode, String productName, String productPrice) {
		this.img = img;
		this.productCode = productCode;
		this.productName = productName;
		this.productPrice = productPrice;
	}//PUOrderVO

	public String getImg() {
		return img;
	}
	public String getProductCode() {
		return productCode;
	}
	public String getProductName() {
		return productName;
	}
	public String getProductPrice() {
		return productPrice;
	}

	@Override
	public String toString() {
		return "PUOrderVO [img=" + img + ", productCode=" + productCode + ", productName=" + productName
				+ ", productPrice=" + productPrice + "]";
	}//toString
	
}//class