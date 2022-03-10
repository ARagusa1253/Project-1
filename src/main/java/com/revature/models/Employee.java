package com.revature.models;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employees")
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int employeeId;
	
	@Column(nullable=false)
	private String employeeFirstName;
	
	@Column(nullable=false)
	private String employeeLastName;
	
	@Column(nullable=false)
	private int employeePin;
	
	public Employee() {
		super();
	}
	
	public Employee(String employeeFirstName, String employeeLastName, int employeePin) {
		super();
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.employeePin = employeePin;
	}
	
	public Employee(int employeeId, String employeeFirstName, String employeeLastName, int employeePin) {
		super();
		this.employeeId = employeeId;
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.employeePin = employeePin;
	}
	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	public int getEmployeePin() {
		return employeePin;
	}
	public void setEmployeePin(int employeePin) {
		this.employeePin = employeePin;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(employeeFirstName, employeeId, employeeLastName, employeePin);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(employeeFirstName, other.employeeFirstName) && employeeId == other.employeeId
				&& Objects.equals(employeeLastName, other.employeeLastName) && employeePin == other.employeePin;
	}
	
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeFirstName=" + employeeFirstName + ", employeeLastName="
				+ employeeLastName + ", employeePin=" + employeePin + "]";
	}

	
}
