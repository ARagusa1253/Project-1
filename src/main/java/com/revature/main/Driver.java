package com.revature.main;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import java.util.Scanner;

import com.revature.dao.CustomerDao;
import com.revature.dao.EmployeeDao;
import com.revature.dao.ProductDao;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.Product;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.ConnectionUtil;
import com.revature.util.MetaModel;
import com.revature.util.PrimaryKeyField;

public class Driver {

	
	static Logger logger = Logger.getLogger(Driver.class);
	
	static CustomerDao customerDao = new CustomerDao();
	static EmployeeDao employeeDao = new EmployeeDao();
	static ProductDao productDao = new ProductDao();
	
	public static void main(String[] args) {
		

		ConnectionUtil.getConnection();
		
			customerDao.createTable();
			employeeDao.createTable();
			productDao.createTable();
			
			System.out.println("You've entered the store!");
			
			menu();

	}

	public static void menu() {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter a number for what you wish to login as: \n1) Customer \n2) Employee");
		
		int menuInput = scan.nextInt();
		
		while(true) {
		
			
			if (menuInput == 1) {
				
				System.out.println("Are you a new Customer (1) , or do you have an existing account (2)?");
				int customerInput = scan.nextInt();
				
				if (customerInput == 1) {
					System.out.println("What is your first name?");
					String firstName = scan.next();
					
					System.out.println("What is your last name?");
					String lastName = scan.next();
					
					Customer c = new Customer(firstName, lastName, 0.0, 0.0);
					customerDao.addNewCustomer(c);
					
				} else if (customerInput == 2) {
					
				System.out.println("Please enter your customer ID");
				int loggedInId = scan.nextInt();
				
				if (customerDao.getCustomerInfo(loggedInId) == null) {
					System.out.println("ID scan failed. Please enter ID again.");
					menu();
				} else {
					logger.info("Customer with ID of (" + loggedInId + ") successfully logged");
				}
				
				int choice;
				double addedAmount;
				double removedAmount;
				
				while(true) { // allows for the user menu to be accessed continuously until exit
					
					System.out.println("Enter in a number for what you would like to do:\n"
							+ "1) Check funds in your wallet\n"
							+ "2) Add funds to your wallet\n"
							+ "3) Remove funds from your wallet\n"
							+ "4) Spend funds in wallet\n"
							+ "5) View your Profile\n"
							+ "6) Exit back to the main menu\n"
							+ "7) Close the program");
											
					switch (choice = scan.nextInt()) {
				
		case 1: System.out.println("Your wallet currently has $" + customerDao.checkWallet(loggedInId) + " in it.");
					break;
					
		case 2: System.out.println("How much would you like to add?");
					addedAmount = scan.nextDouble();
					customerDao.addToWallet(addedAmount, loggedInId);
					break;
					
		case 3: System.out.println("How much would you like to take out?");
					removedAmount = scan.nextDouble();
					customerDao.removeFromWallet(removedAmount, loggedInId);
					break;
					
		case 4: System.out.println("How much are the products you are paying for?");
					removedAmount = scan.nextDouble();
					customerDao.removeFromWallet(removedAmount, loggedInId);
					System.out.println("Paying for products with funds");
					customerDao.spendFromWallet(removedAmount, loggedInId);
					break;			
					
		case 5: System.out.println("Here is your account status: \n");
					System.out.println(customerDao.getCustomerInfo(loggedInId));
					System.out.println();
					break;
					
		case 6: menu();
					break;
					
		case 7: System.out.println("Have a good day! :D");
					System.exit(0);
					break;
					
		default: System.out.println("Input invalid, try again.");
					break;
					}
					
				} 
			} else {
				logger.info("Customer input invalid, try again");
			}
		}
			
		if (menuInput == 2) {
				
			System.out.println("Are you a new Employee (1) , or do you have an existing account (2)?");
			int employeeInput = scan.nextInt();
			
			if (employeeInput == 1) {
				System.out.println("What is your first name?");
				String firstName = scan.next();
				
				System.out.println("What is your last name?");
				String lastName = scan.next();
				
				System.out.println("What would you like your PIN to be?");
				int pin = scan.nextInt();
				
				Employee e = new Employee(firstName, lastName, pin);
				employeeDao.addNewEmployee(e);
				
			} else if (employeeInput == 2) {
				
			System.out.println("Please enter your Employee ID");
			int loggedInId = scan.nextInt();
			
			if (customerDao.getCustomerInfo(loggedInId) == null) {
				System.out.println("ID scan failed. Please enter ID again.");
				menu();
			} else {
				logger.info("Employee with ID of (" + loggedInId + ") successfully logged");
			}
			
			int choice;
			int productId;
			double changedPrice;
			int quantity;
			
			while(true) { // allows for the user menu to be accessed continuously until exit
				
				System.out.println("Enter in a number for what you would like to do:\n"
						+ "1) Add a new product to the store\n"
						+ "2) Remove a product from the store\n"
						+ "3) Update the price of a product\n"
						+ "4) Update the discounted price of a product\n"
						+ "5) Update the quantity of a product in the store\n"
						+ "6) Check your Employee info\n"
						+ "7) Remove an employee\n"
						+ "8) Exit back to the main menu\n"
						+ "9) Close the program");
										
				switch (choice = scan.nextInt()) {
			
	case 1: System.out.println("Enter in the information for the new product being added:\n");
				System.out.println("Enter the product name: ");
				String productName = scan.next();
				
				System.out.println("Enter the product's brand's name: ");
				
				String productBrandName = scan.next();
				
				System.out.println("Enter the product group: ");
				String productGroup = scan.next();
				
				System.out.println("Enter the product price: ");
				Double productPrice = scan.nextDouble();
				
				System.out.println("Enter the product discount price: ");
				Double productDiscountPrice = scan.nextDouble();				
				
				System.out.println("Enter the product Specification: ");
				String productSpecification = scan.next();
				
				System.out.println("Enter the product quantity: ");
				int productQuantity = scan.nextInt();
				
				Product p = new Product(productName, productBrandName, productGroup, productPrice, productDiscountPrice, productSpecification, productQuantity);
				productDao.addNewProduct(p);
				
				break;
				
	case 2: System.out.println("Enter the id of the product you want to remove from the store: ");
				productId = scan.nextInt();
				employeeDao.deleteProduct(productId);
				break;
				
	case 3: System.out.println("Enter the ID of the product that is having it's price changed: ");
				productId = scan.nextInt();
				System.out.println("Enter the updated price: ");
				changedPrice = scan.nextDouble();
				employeeDao.changePrice(changedPrice, productId);
				break;
				
	case 4: System.out.println("Enter the ID of the product that is having it's discounted price changed: ");
				productId = scan.nextInt();
				System.out.println("Enter the updated price: ");
				changedPrice = scan.nextDouble();
				employeeDao.changeDiscountPrice(changedPrice, productId);
				break;
				
	case 5: System.out.println("Enter the ID of the product that is having it's quantity changed: ");
				productId = scan.nextInt();
				System.out.println("Enter the updated quantity: ");
				quantity = scan.nextInt();
				employeeDao.changeProductQuantity(quantity, productId);
				break;
				
	case 6: System.out.println("Retrieving your employee account status: \n");
				System.out.println(employeeDao.getEmployeeInfo(loggedInId));
				System.out.println();
				break;
	
	case 7: System.out.println("Enter the ID of the Employee you wish to remove: ");
				int empId = scan.nextInt();
				employeeDao.deleteEmployee(empId);
				break;
	
	case 8: menu();
				break;
				
	case 9: System.out.println("Have a good day! :D");
				System.exit(0);
				break;
				
	default: System.out.println("Input invalid, try again.");
				break;
				}
				
			} 
			
			}
			} else {
				logger.info("Customer input invalid, try again");
			}
				
				
				
				
				
			}
			
			
		}
		// 1
		
		
		
		
		
		
		
		// 2
		
	}
	
	
	
	


		
			
			
			
			
			
			
			//List<Class<?>> myClasses = Arrays.asList(Customer.class, Employee.class, Product.class);
			//List<Class<?>> CustomerClass = Arrays.asList(Customer.class);

			//Configuration cfg = new Configuration();
			//cfg.addAnnotatedClasses(myClasses);
			//cfg.addAnnotatedClasses(CustomerClass);
			
			//System.out.println("Printing metamodels: " + cfg.getMetaModels());
			//System.out.println(cfg.metaModel.getSimpleClassName());
			
	//for (MetaModel<?> metaModel : cfg.getMetaModels()) {
//				
//				// for each one, print the information about the metaModel
//				System.out.println("Printing all the MetaModel info for the class: " + metaModel.getSimpleClassName());
//				System.out.println("Trying to get Entity name for Customer class: " + metaModel.getEntity());
//				PrimaryKeyField pk = metaModel.getPrimaryKey();
//				List<ColumnField> columns = metaModel.getColumns();
//				//List<ForeignKeyField> foreignKeys = metaModel.getForeignKey();
//				
//				System.out.println("ID: " + pk.getColumnName());
				
				//System.out.println("ATTEMPTING TO CREATE TABLE: ");
				//customerDao.createCustomerTable();
				//employeeDao.createEmployeeTable();
				//productDao.createProductTable();
			
		//			for (ColumnField cf : columns) {
//					System.out.println("Found the column field named " + cf.getColumnName() + " with the type " + cf.getType());
//				}
//			}
			
