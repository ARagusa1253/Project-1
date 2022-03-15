package com.revature.models;

import java.util.Objects;

import com.revature.annotation.Column;
import com.revature.annotation.Entity;
import com.revature.annotation.Id;
import com.revature.annotation.JoinColumn;

@Entity(tableName="products")
public class Product {

	@Id(columnName="product_id")
	private int productId;
	
	@Column(columnName="product_name")
	private String productName;
	
	@Column(columnName="product_brand_name")
	private String productBrandName;
	
	@Column(columnName="product_group")
	private String productGroup;
	
	@Column(columnName="product_price")
	private double productPrice;
	
	@Column(columnName="product_discount_price")
	private double productDiscountPrice;
	
	@Column(columnName="product_specification")
	private String productSpecification;
	
	@Column(columnName="product_quantity")
	private int productQuantity;

	public Product() {
		super();
	}

	public Product(String productName, String productBrandName, String productGroup, double productPrice,
			double productDiscountPrice, String productSpecification, int productQuantity) {
		super();
		this.productName = productName;
		this.productBrandName = productBrandName;
		this.productGroup = productGroup;
		this.productPrice = productPrice;
		this.productDiscountPrice = productDiscountPrice;
		this.productSpecification = productSpecification;
		this.productQuantity = productQuantity;
	}

	public Product(int productId, String productName, String productBrandName, String productGroup, double productPrice,
			double productDiscountPrice, String productSpecification, int productQuantity) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productBrandName = productBrandName;
		this.productGroup = productGroup;
		this.productPrice = productPrice;
		this.productDiscountPrice = productDiscountPrice;
		this.productSpecification = productSpecification;
		this.productQuantity = productQuantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductBrandName() {
		return productBrandName;
	}

	public void setProductBrandName(String productBrandName) {
		this.productBrandName = productBrandName;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getProductDiscountPrice() {
		return productDiscountPrice;
	}

	public void setProductDiscountPrice(double productDiscountPrice) {
		this.productDiscountPrice = productDiscountPrice;
	}

	public String getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productBrandName, productDiscountPrice, productGroup, productId, productName, productPrice,
				productQuantity, productSpecification);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(productBrandName, other.productBrandName)
				&& Double.doubleToLongBits(productDiscountPrice) == Double.doubleToLongBits(other.productDiscountPrice)
				&& Objects.equals(productGroup, other.productGroup) && productId == other.productId
				&& Objects.equals(productName, other.productName)
				&& Double.doubleToLongBits(productPrice) == Double.doubleToLongBits(other.productPrice)
				&& productQuantity == other.productQuantity
				&& Objects.equals(productSpecification, other.productSpecification);
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productBrandName="
				+ productBrandName + ", productGroup=" + productGroup + ", productPrice=" + productPrice
				+ ", productDiscountPrice=" + productDiscountPrice + ", productSpecification=" + productSpecification
				+ ", productQuantity=" + productQuantity + "]";
	}

}
