package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
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

	CreateTable tableCreation = new CreateTable();
	static Configuration cfg = new Configuration();

	public void createEmployeeTable() {
		tableCreation.createTable(tableClass);
	}

	public boolean createTable() {

		cfg.addAnnotatedClasses(tableClass);

		try (Connection conn = ConnectionUtil.getConnection()) {

			for (MetaModel<?> metaModel : cfg.getMetaModels()) {
				String sql = "CREATE TABLE IF NOT EXISTS " + metaModel.getEntity() + " (\r\n" 
								+ "			" + metaModel.getPrimaryKey().getColumnName() + " SERIAL PRIMARY KEY\r\n" + ");";

				PreparedStatement stmt = conn.prepareStatement(sql);

				stmt.executeUpdate();
				logger.info("The " + metaModel.getEntity() + " table has been created!");

				List<ColumnField> columns = metaModel.getColumns();

				try (Connection conn2 = ConnectionUtil.getConnection()) {

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

						String sql2 = "ALTER TABLE " + metaModel.getEntity() + "\r\n" + "ADD COLUMN IF NOT EXISTS "
								+ cf.getColumnName() + " " + datatype + ";";

						PreparedStatement stmt2 = conn.prepareStatement(sql2);

						stmt2.executeUpdate();
						logger.info("The " + cf.getColumnName() + " table has been created!");
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

	public int addNewEmployee(Employee m) {

		cfg.addAnnotatedClasses(tableClass);

		try (Connection conn = ConnectionUtil.getConnection()) {
			for (MetaModel<?> metaModel : cfg.getMetaModels()) {
				String sql = "INSERT INTO " + metaModel.getEntity()
						+ "(first_name, last_name, Pin) VALUES (?, ?, ?) RETURNING " + metaModel.getEntity() + "."
						+ metaModel.getPrimaryKey().getColumnName();
				PreparedStatement stmt = conn.prepareStatement(sql);

				stmt.setString(1, m.getEmployeeFirstName());
				stmt.setString(2, m.getEmployeeLastName());
				stmt.setInt(3, m.getEmployeePin());

				ResultSet rs;

				if ((rs = stmt.executeQuery()) != null) {

					rs.next();

					int id = rs.getInt(1);

					logger.info("Created a new Employee! They have an ID of " + id);

					return id;
				}
			}
		} catch (SQLException e) {
			logger.warn("Could not create new Employee!");
			e.printStackTrace();
		}
		return -1;

	}

	public List<Employee> getEmployeeInfo(int id) {

		List<Employee> employee = new LinkedList<Employee>();

		cfg.addAnnotatedClasses(tableClass);

		try (Connection conn = ConnectionUtil.getConnection()) {

			for (MetaModel<?> metaModel : cfg.getMetaModels()) {

				String sql = "SELECT * FROM" + metaModel.getEntity() + " WHERE"
						+ metaModel.getPrimaryKey().getColumnName();

				PreparedStatement stmt = conn.prepareStatement(sql);

				stmt.setInt(1, id);

				ResultSet rs;

				if ((rs = stmt.executeQuery()) != null) {

					int empId = rs.getInt("id");

					String firstName = rs.getString("first_name");

					String lastName = rs.getString("last_name");

					int Pin = rs.getInt("employee_pin");

					Employee e = new Employee(empId, firstName, lastName, Pin);

					employee.add(e);

				}
			}
		} catch (SQLException e) {
			logger.warn("Could not retrieve information for employee!");
			e.printStackTrace();

		}

		return employee;

	}

	// Delete Employee from table
	
	// check employee pin (varifyPin)
	
	// Employee can add a new product to the product table (Take in an instance of the product class in an Arraylist (like on line 21))
	// (Need to enter PIN to authenticate the employee)
	
}
