package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Customer;
import com.revature.util.Configuration;
import com.revature.util.ConnectionUtil;
import com.revature.util.MetaModel;


public class CustomerDao {
	
	private static Logger logger = Logger.getLogger(CustomerDao.class);
	List<Class<?>> tableClass = Arrays.asList(Customer.class);

	static Configuration cfg = new Configuration();

	
	// Create Customer table
	public boolean createTable() {
		
		cfg.addAnnotatedClasses(tableClass);
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			for (MetaModel<?> metaModel : cfg.getMetaModels()) {
				String sql = "CREATE TABLE IF NOT EXISTS " + metaModel.getEntity(/*customers*/) + " (\r\n"
					+ "			" + metaModel.getPrimaryKey().getColumnName(/*customer_id*/) +  " SERIAL PRIMARY KEY\r\n"
					+ ");			";// +  +"\r\n"
//					+ "					ON antonior.accounts.id = antonior.users_accounts_jt.account \r\n"
//					+ "						WHERE antonior.users_accounts_jt.acc_owner = ?";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.executeUpdate();
				logger.info("The " + metaModel.getEntity() +" table has been created!");
			}
			return true;
		} catch (SQLException e) {
			logger.warn("Unable to create Customer table in the DB");
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Create new customer (Call no id constructor)
	
	// Get Customer Info
	
	// Scan Customer ID (pk)
	
	// Read Wallet contents
	
	//Read total spent
	
	// Update Wallet contents (Put funds in and pay for goods)
	
	
	
	
	
	
	
	
}
