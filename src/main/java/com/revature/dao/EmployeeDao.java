package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Employee;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.ConnectionUtil;
import com.revature.util.MetaModel;

public class EmployeeDao {
	private static Logger logger = Logger.getLogger(EmployeeDao.class);
	List<Class<?>> tableClass = Arrays.asList(Employee.class);

	static Configuration cfg = new Configuration();

	public boolean createTable() {
		
		cfg.addAnnotatedClasses(tableClass);
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			for (MetaModel<?> metaModel : cfg.getMetaModels()) {
				String sql = "CREATE TABLE IF NOT EXISTS " + metaModel.getEntity(/*employees*/) + " (\r\n"
					+ "			" + metaModel.getPrimaryKey().getColumnName(/*employee_id*/) +  " SERIAL PRIMARY KEY\r\n"
					+ ");			";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.executeUpdate();
				logger.info("The " + metaModel.getEntity() +" table has been created!");
				
				List<ColumnField> columns = metaModel.getColumns();
				
				try(Connection conn2 = ConnectionUtil.getConnection()) {
					
					for(ColumnField cf : columns) {
						
						String datatype = "";
						
						if(cf.getType().toString().equals("class java.lang.String")) {
							datatype = "VARCHAR(50)";
						}
						
						if(cf.getType().toString().equals("int")) {
							datatype = "INTEGER";
						}
						
						if(cf.getType().toString().equals("double")) {
							datatype = "NUMERIC(50,2)";
						}
						
						String sql2 = "ALTER TABLE " + metaModel.getEntity() + "\r\n"
								+ "ADD COLUMN IF NOT EXISTS " + cf.getColumnName() + " " + datatype + ";";
						
						PreparedStatement stmt2 = conn2.prepareStatement(sql2);
						
						stmt2.executeUpdate();
						logger.info("The " + cf.getColumnName() + " column has been created!");
					}
					
				}
				catch (SQLException e) {
					logger.warn("Unable to create " + metaModel.getSimpleClassName() + " column in the DB");
					e.printStackTrace();
				}

			}
			
			return true;
		} 
		catch (SQLException e) {
			logger.warn("Unable to create Employee table in the DB");
			e.printStackTrace();
		}
		
		return false;
	}
	
}
