package com.revature.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customers")
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int customerId;
	
	@Column(nullable=false)
	private String customerFirstName;
	
	@Column(nullable=false)
	private String customerLastName;
	
	@Column(nullable=false)
	private double customerWallet;
	
	@Column(nullable=false)
	private double customerTotalSpent;

	public Customer() {
		super();
	}

	public Customer(String customerFirstName, String customerLastName, double customerWallet,
			double customerTotalSpent) {
		super();
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.customerWallet = customerWallet;
		this.customerTotalSpent = customerTotalSpent;
	}

	public Customer(int customerId, String customerFirstName, String customerLastName, double customerWallet,
			double customerTotalSpent) {
		super();
		this.customerId = customerId;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.customerWallet = customerWallet;
		this.customerTotalSpent = customerTotalSpent;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public double getCustomerWallet() {
		return customerWallet;
	}

	public void setCustomerWallet(double customerWallet) {
		this.customerWallet = customerWallet;
	}

	public double getCustomerTotalSpent() {
		return customerTotalSpent;
	}

	public void setCustomerTotalSpent(double customerTotalSpent) {
		this.customerTotalSpent = customerTotalSpent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerFirstName, customerId, customerLastName, customerTotalSpent, customerWallet);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(customerFirstName, other.customerFirstName) && customerId == other.customerId
				&& Objects.equals(customerLastName, other.customerLastName)
				&& Double.doubleToLongBits(customerTotalSpent) == Double.doubleToLongBits(other.customerTotalSpent)
				&& Double.doubleToLongBits(customerWallet) == Double.doubleToLongBits(other.customerWallet);
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerFirstName=" + customerFirstName + ", customerLastName="
				+ customerLastName + ", customerWallet=" + customerWallet + ", customerTotalSpent=" + customerTotalSpent
				+ "]";
	}
	
}
