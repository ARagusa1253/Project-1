package com.revature.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int productId;
	
	@Column(nullable=false)
	private String productName;
	
	@Column(nullable=false)
	private String productBrandName;
	
	@Column(nullable=false)
	private String productGroup;
	
	@Column(nullable=false)
	private double productPrice;
	
	@Column(nullable=false)
	private double productDiscountPrice;
	
	@Column(nullable=false)
	private String productSpecification;
	
	@Column(nullable=false)
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
