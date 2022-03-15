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
import com.revature.models.Employee;
import com.revature.models.Product;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.ConnectionUtil;
import com.revature.util.MetaModel;


public class ProductDao {
	private static Logger logger = Logger.getLogger(ProductDao.class);
	List<Class<?>> tableClass = Arrays.asList(Product.class);

	static Configuration cfg = new Configuration();
	
	
	public int addNewProduct(Product p) {
		
		cfg.addAnnotatedClasses(tableClass);
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			for (MetaModel<?> metaModel : cfg.getMetaModels()) {
				String sql = "INSERT INTO " + metaModel.getEntity() + "(product_name, product_brand_name, product_group, product_price, product_discount_price, product_specification, product_quantity) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING " + metaModel.getEntity() + "." + metaModel.getPrimaryKey().getColumnName();
				PreparedStatement stmt = conn.prepareStatement(sql);
			
				stmt.setString(1, p.getProductName());
				stmt.setString(2, p.getProductBrandName());
				stmt.setString(3, p.getProductGroup());
				stmt.setDouble(4, p.getProductPrice());
				stmt.setDouble(5, p.getProductDiscountPrice());
				stmt.setString(6, p.getProductSpecification());
				stmt.setInt(7, p.getProductQuantity());

				
				ResultSet rs;
				
				if ((rs = stmt.executeQuery()) != null) {
					
					rs.next();
					
					int id = rs.getInt(1);
					
					logger.info("Created a new Product! It has an ID of " + id);
					
					return id;
				}
			}
		} catch (SQLException e) {
			logger.warn("Could not create new customer!");
			e.printStackTrace();
		}
		return -1;
	}
	public List<Product> getProductInfo(int id) {
		cfg.addAnnotatedClasses(tableClass);
		List<Product> product = new LinkedList<Product>();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			for (MetaModel<?> metaModel : cfg.getMetaModels()) {
				String sql = "SELECT * FROM " + metaModel.getEntity() + " WHERE " +  metaModel.getPrimaryKey().getColumnName() + "= ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, id);
				ResultSet rs;
				if((rs= stmt.executeQuery()) != null) {
						int productId = rs.getInt("product_id");
						String productName = rs.getString("product_name");
						String productBrandName = rs.getString("product_name");;
						String productGroup = rs.getString("product_name");;
						double productPrice = rs.getDouble("product_price");
						double productDiscountPrice = rs.getDouble("product_discount_price");
						String productSpecification = rs.getString("product_specification");;
						int productQuantity = rs.getInt("product_quantity");
						Product p = new Product(productId, productName, productBrandName, productGroup, productPrice, productDiscountPrice, productSpecification, productQuantity);
						product.add(p);
				}
			}
		} 
		catch (SQLException e) {
		// TODO Auto-generated catch block
		logger.info("Could not retrieve information on customer");
		e.printStackTrace();
	}
		return product;
}
}
