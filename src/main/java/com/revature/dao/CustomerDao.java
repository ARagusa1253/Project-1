package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Customer;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.ConnectionUtil;
import com.revature.util.MetaModel;


public class CustomerDao {
	
	private static Logger logger = Logger.getLogger(CustomerDao.class);
	List<Class<?>> tableClass = Arrays.asList(Customer.class);

	CreateTable tableCreation = new CreateTable();
	static Configuration cfg = new Configuration();

	
	// Create Customer table
	public void createCustomerTable() {
		tableCreation.createTable(tableClass);
	}
	
	public boolean createTable() {
		
		cfg.addAnnotatedClasses(tableClass);
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			for (MetaModel<?> metaModel : cfg.getMetaModels()) {
				String sql = "CREATE TABLE IF NOT EXISTS " + metaModel.getEntity() + " (\r\n"
					+ "			" + metaModel.getPrimaryKey().getColumnName() +  " SERIAL PRIMARY KEY\r\n"
					+ ");";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.executeUpdate();
				logger.info("The " + metaModel.getEntity() + " table has been created!");
				
				List<ColumnField> columns = metaModel.getColumns();
				
				try(Connection conn2 = ConnectionUtil.getConnection()) {
					
					for (ColumnField cf : columns) {
						
						String datatype = "";
						
						if (cf.getType().toString().equals("class java.lang.String")) {
							datatype = "VARCHAR(50)";
						}
						
						if (cf.getType().toString().equals("double")) {
							datatype = "NUMERIC(50,2)";
						}
						
						if (cf.getType().toString().equals("int")) {
							datatype = "INTEGER";
						}
						
						String sql2 = "ALTER TABLE " + metaModel.getEntity() + "\r\n"
									+    "ADD COLUMN IF NOT EXISTS " + cf.getColumnName() + " " + datatype + ";";
						
					PreparedStatement stmt2 = conn.prepareStatement(sql2);
					
					stmt2.executeUpdate();
					logger.info("The " + cf.getColumnName() +" table has been created!");	
						
					}
					
				} catch (SQLException e) {
					logger.warn("Unable to create " + metaModel.getSimpleClassName() + " column in the DB");
					e.printStackTrace();
				}
				
			}
			return true;
		} catch (SQLException e) {
			logger.warn("Unable to create table in the DB");
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Create new customer (Call no id constructor)
	
	public int addNewCustomer(Customer c) {
		
		cfg.addAnnotatedClasses(tableClass);
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			for (MetaModel<?> metaModel : cfg.getMetaModels()) {
				
//				List<ColumnField> columns = metaModel.getColumns();
//				
//				String tempQuery = "";
//				
//				for (ColumnField cf : columns) {
//					
//					tempQuery += cf.getColumnName() + ", ";
//				}
//				
//				if (tempQuery != null) {
//					.filter (tempQuery -> tempQuery.length() != 0)
//					.map (tempQuery -> tempQuery.substring(0, tempQuery.length() - 1))
//					.findFirst();
//				}
				
				
				
				String sql = "INSERT INTO " + metaModel.getEntity() + "(first_name, last_name, wallet, total_spent) VALUES (?, ?, ?, ?) RETURNING " + metaModel.getEntity() + "." + metaModel.getPrimaryKey().getColumnName();
				
				
				
				PreparedStatement stmt = conn.prepareStatement(sql);
			
				stmt.setString(1, c.getCustomerFirstName());
				stmt.setString(2, c.getCustomerLastName());
				stmt.setDouble(3, c.getCustomerWallet());
				stmt.setDouble(4, c.getCustomerTotalSpent());
				
				ResultSet rs;
				
				if ((rs = stmt.executeQuery()) != null) {
					
					rs.next();
					
					int id = rs.getInt(1);
					
					logger.info("Created a new Customer! They have an ID of " + id);
					
					return id;
				}
			}
		} catch (SQLException e) {
			logger.warn("Could not create new customer!");
			e.printStackTrace();
		}
		return -1;
		
	}
	
	// Get Customer Info
	
public List<Customer> getCustomerInfo(int id) {
	
		List<Customer> customer = new LinkedList<Customer>();
		
		cfg.addAnnotatedClasses(tableClass);
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			for (MetaModel<?> metaModel : cfg.getMetaModels()) {
				
				String sql = "SELECT * FROM " + metaModel.getEntity() + " WHERE " + metaModel.getPrimaryKey().getColumnName() + " = ?;";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setInt(1, id);
				
				ResultSet rs;
				
				if ((rs = stmt.executeQuery()) != null) {
						
						int customerId = rs.getInt("id");
						String firstName = rs.getString("first_name");
						String lastName = rs.getString("last_name");
						double wallet = rs.getDouble("wallet");
						double totalSpent = rs.getDouble("total_spent");
						
						Customer c = new Customer(customerId, firstName, lastName, wallet, totalSpent);
						customer.add(c);
					}
				}
			}catch (SQLException e) {
			logger.warn("Could not retrieve information on customer!");
			e.printStackTrace();
		} 
		return customer;
		}





}

	// Delete Customer
	
	// Read Wallet contents \
	//										  > Transaction functionality (deposit into wallet, withdraw to pay, and record amount spent)
	// Read total spent			/
	
	
	
	
	
	
	
	
	

