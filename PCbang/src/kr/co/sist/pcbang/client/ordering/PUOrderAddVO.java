package kr.co.sist.pcbang.client.ordering;

public class PUOrderAddVO {
	private String productCode,productName,productPrice;
	private int productQuan;
	
	public PUOrderAddVO(String productCode, String productName, String productPrice, int productQuan) {
		this.productCode = productCode;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productQuan = productQuan;
	}//PUOrderAddVO

	public String getProductCode() {
		return productCode;
	}
	public String getProductName() {
		return productName;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public int getProductQuan() {
		return productQuan;
	}

	@Override
	public String toString() {
		return "PUOrderAddVO [productCode=" + productCode + ", productName=" + productName + ", productPrice="
				+ productPrice + ", productQuan=" + productQuan + "]";
	}//toString

}//class