package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.ConnectionUtil;
import com.revature.util.MetaModel;

public class CreateTable {

	private static Logger logger = Logger.getLogger(CreateTable.class);
	static Configuration cfg = new Configuration();
	
public boolean createTable(List<Class<?>> tableClass) {
		
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
}
