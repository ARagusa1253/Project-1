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
	
	// Get All Customer Info
	
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
						
					if(rs.next() ==true) {
						int customerId = rs.getInt("customer_id");
						String firstName = rs.getString("first_name");
						String lastName = rs.getString("last_name");
						double wallet = rs.getDouble("wallet");
						double totalSpent = rs.getDouble("total_spent");
						
						Customer c = new Customer(customerId, firstName, lastName, wallet, totalSpent);
						customer.add(c);
						return customer;
					} else {
						logger.info("There is no Customer with the ID: " + id);
					}
					}
				}
			} catch (SQLException e) {
			logger.warn("Could not retrieve information on customer!");
			e.printStackTrace();
		} 
		return null;
		}

// Delete Customer

public void deleteCustomer(int id) {
	
	cfg.addAnnotatedClasses(tableClass);
	
	try(Connection conn = ConnectionUtil.getConnection()) {
		for (MetaModel<?> metaModel : cfg.getMetaModels()) {
			
			String sql = "DELETE FROM " + metaModel.getEntity() + " WHERE " + metaModel.getPrimaryKey().getColumnName() + " = ?;";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			stmt.executeUpdate();
			
			logger.info("Customer with the ID: " + id + " has been deleted");
		}
	} catch (SQLException e) {
		logger.warn("Could not delete information on customer!");
		e.printStackTrace();
	}	
}

//Read Wallet contents

public double checkWallet(int id) {
	
	cfg.addAnnotatedClasses(tableClass);
	
	try(Connection conn = ConnectionUtil.getConnection()) {
		for (MetaModel<?> metaModel : cfg.getMetaModels()) {
			
			String sql = "SELECT wallet FROM " + metaModel.getEntity() + " WHERE " + metaModel.getPrimaryKey().getColumnName() + " = ?;";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
				if(rs.next() ==true) {
					logger.info("You take a peek inside your wallet...");
					double amountHeld = rs.getDouble("wallet");
					return amountHeld;
				} else {
					logger.info("Customer with ID of (" + id + ") could not check their wallet contents");
				}
				
			}
		}
	} catch (SQLException e) {
		logger.warn("Could not retrieve wallet contents from customer!");
		e.printStackTrace();
	}
	return -1;
}

// Add to wallet

public double addToWallet(double addedAmount, int id) {
	cfg.addAnnotatedClasses(tableClass);
	
	try(Connection conn = ConnectionUtil.getConnection()) {
		for (MetaModel<?> metaModel : cfg.getMetaModels()) {
			
			String sql = "UPDATE " + metaModel.getEntity() + " SET wallet = wallet + ? WHERE " + metaModel.getPrimaryKey().getColumnName() + " = ?;";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, addedAmount);
			stmt.setInt(2, id);
			
			stmt.executeUpdate();
			logger.info("Funds have been inserted successfully!");
			return id;
			}
		}catch (SQLException e) {
		logger.warn("Could not add to wallet contents from customer!");
		e.printStackTrace();
	}
	
	return -1;
}

// Remove from wallet

public double removeFromWallet(double removedAmount, int id) {
	cfg.addAnnotatedClasses(tableClass);
	
	try(Connection conn = ConnectionUtil.getConnection()) {
		for (MetaModel<?> metaModel : cfg.getMetaModels()) {
			
			String sql = "UPDATE " + metaModel.getEntity() + " SET wallet = wallet - ? WHERE " + metaModel.getPrimaryKey().getColumnName() + " = ?;";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, removedAmount);
			stmt.setInt(2, id);
			
			stmt.executeUpdate();
			logger.info("Funds have been removed successfully!");
			return id;
			}
		}catch (SQLException e) {
		logger.warn("Could not remove wallet contents from customer!");
		e.printStackTrace();
	}
	
	return -1;
}

// Record total spent

public void spendFromWallet(double removedAmount, int id) {
	
	try(Connection conn = ConnectionUtil.getConnection()) {
		for (MetaModel<?> metaModel : cfg.getMetaModels()) {
			
			String sql = "UPDATE " + metaModel.getEntity() + " SET total_spent = total_spent + ? WHERE " + metaModel.getPrimaryKey().getColumnName() + " = ?;";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, removedAmount);
			stmt.setInt(2, id);
			
			stmt.executeUpdate();
			logger.info("Funds spent have been recorded to Customer with ID of + (id) +'s total successfully!");
			return;
			}
		} catch (SQLException e) {
		logger.warn("Could not remove wallet contents from customer!");
		e.printStackTrace();
	}
}



//Read total spent

public double checkTotalSpent(int id) {
	
	cfg.addAnnotatedClasses(tableClass);
	
	try(Connection conn = ConnectionUtil.getConnection()) {
		for (MetaModel<?> metaModel : cfg.getMetaModels()) {
			
			String sql = "SELECT total_spent FROM " + metaModel.getEntity() + " WHERE " + metaModel.getPrimaryKey().getColumnName() + " = ?;";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs;
			
			if ((rs = stmt.executeQuery()) != null) {
					
					double totalSpent = rs.getDouble("wallet");
					logger.info("You've spent a total of $" + totalSpent);
					return totalSpent;
			}
		}
	} catch (SQLException e) {
		logger.warn("Could not total spent from customer!");
		e.printStackTrace();
	}
	return -1;
}


}

	
	
	// Read Wallet contents \
	//										  > Transaction functionality (deposit into wallet, withdraw to pay, and record amount spent)
	// Read total spent			/
	
	
	
	
	
	
	
	
	

