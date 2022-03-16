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
		
		System.out.println("Enter a number for what you wish to login as: \n1) Customer \n2)Employee");
		
		int input = scan.nextInt();
		
		while(true) {
			
			if (input == 1) {
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
					
					System.out.println("Enter in a number for what you would like to do:\n "
							+ "1) Check funds in your wallet\n"
							+ "2) Add funds to your wallet\n"
							+ "3) Remove funds from your wallet\n"
							+ "4) View your Profile\n"
							+ "5) Exit back to the main menu\n"
							+ "6) Close the program");
											
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
					
		case 4: System.out.println("Here is your account status: \n");
					customerDao.getCustomerInfo(loggedInId);
					System.out.println();
					break;
					
		case 5: menu();
					break;
					
		case 6: System.out.println("Have a good day! :D");
					System.exit(0);
					break;
					
		default: System.out.println("Input invalid, try again.");
					break;
					}
				}
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
			
