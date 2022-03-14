package com.revature.main;

import java.util.Arrays;
import java.util.List;

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

	public static void main(String[] args) {
		
		List<Class<?>> myClasses = Arrays.asList(Customer.class, Employee.class, Product.class);
		List<Class<?>> CustomerClass = Arrays.asList(Customer.class);
		
		Configuration cfg = new Configuration();
		CustomerDao customerDao = new CustomerDao();
		EmployeeDao employeeDao = new EmployeeDao();
		ProductDao productDao = new ProductDao();

		//cfg.addAnnotatedClasses(myClasses);
		ConnectionUtil.getConnection();
		
		cfg.addAnnotatedClasses(CustomerClass);
		
		System.out.println("Printing metamodels: " + cfg.getMetaModels());
		//System.out.println(cfg.metaModel.getSimpleClassName());
		
for (MetaModel<?> metaModel : cfg.getMetaModels()) {
			
			// for each one, print the information about the metaModel
			System.out.println("Printing all the MetaModel info for the class: " + metaModel.getSimpleClassName());
			System.out.println("Trying to get Entity name for Customer class: " + metaModel.getEntity());
			PrimaryKeyField pk = metaModel.getPrimaryKey();
			List<ColumnField> columns = metaModel.getColumns();
			//List<ForeignKeyField> foreignKeys = metaModel.getForeignKey();
			
			System.out.println("ID: " + pk.getColumnName());
			
			System.out.println("ATTEMPTING TO CREATE TABLE: ");
			customerDao.createTable();
			employeeDao.createTable();
			productDao.createTable();
			
			for (ColumnField cf : columns) {
				System.out.println("Found the column field named " + cf.getColumnName() + " with the type " + cf.getType());
			}
		}

	}

}
